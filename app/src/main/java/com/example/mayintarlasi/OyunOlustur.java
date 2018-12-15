package com.example.mayintarlasi;

import java.util.Random;

import  com.example.mayintarlasi.Block;
import  com.example.mayintarlasi.VeriTabani;
import  com.example.mayintarlasi.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OyunOlustur extends Activity {

	int oyunTipi;

	private EditText isimText;

	private Button yCikisBtn;

	private TextView textViewMayinSayisi;
	private TextView textViewZaman;
	private ImageButton smileBtn;

	private TableLayout mineField;

	private Block blocks[][];
	private int blockBoyutu = AnaMenu.kG; // Blok yükseklikleri
	private int blockBoslugu = 2; // Bloklar arası boşluklar
	private VeriTabani veri;
	private int satirSayisi = AnaMenu.yukseklik;
	private int sutunSayisi = AnaMenu.genislik;
	private int toplamMayinSayisi = AnaMenu.mayin;

	private MediaPlayer ses;
	static MediaPlayer ses2;
	private MediaPlayer ses3;

	private Handler timer = new Handler();
	private int secondsPassed = 0;

	//Zamanın başlayıp başlamadığını kontrol etmek için
	private boolean isZamanKontrol;
	//Mayın yerleştirilmişmi
	private boolean areMinesSet;
	private boolean isOyunBitti;
	private int minesToFind; // number of mines yet to be discovered




	boolean kontrol=false;
	String kod="";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		veri=new VeriTabani(this);
		setContentView(R.layout.oyunolusturl);
		if(satirSayisi==9&&sutunSayisi==9)
			oyunTipi=0;
		else if(satirSayisi==16&&sutunSayisi==16)
			oyunTipi=1;
		else if(satirSayisi==30&&sutunSayisi==16)
			oyunTipi=2;







		yCikisBtn = (Button) findViewById(R.id.yCikisBtn);

		kontrol=false;

		textViewMayinSayisi = (TextView) findViewById(R.id.MineCount);
		textViewZaman = (TextView) findViewById(R.id.Timer);

		ses=MediaPlayer.create(this, R.raw.bomb);
		ses2=MediaPlayer.create(this, R.raw.oh);
		ses3=MediaPlayer.create(this, R.raw.alkis);

		showDialog("Oyunu başlatmak için surata tıklatınız!", 5000, true, false);

		smileBtn = (ImageButton) findViewById(R.id.Smiley);
		smileBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{


				oyundanCikis();
				startNewGame();
				kontrol=true;



				degistir();

			}
		});

		mineField = (TableLayout)findViewById(R.id.MineField);

		isimText = (EditText) findViewById(R.id.isimText);



		yCikisBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				for(int x=0;x<satirSayisi;x++)
				{
					for(int y=0;y<sutunSayisi;y++)
					{
						if(blocks[x][y].hasMine())
							kod+=1;
						else
							kod+=0;
					}
				}

				SQLiteDatabase db = veri.getWritableDatabase();
				ContentValues veriler = new ContentValues();

				veriler.put("isim", isimText.getText().toString());
				veriler.put("dizilis", kod);
				veriler.put("satir", satirSayisi);
				veriler.put("sutun", sutunSayisi);
				db.insertOrThrow("kayitli", "kullanici", veriler);
				veri.close();

				Intent intent = new Intent(getBaseContext(), AnaMenu.class);
				startActivity(intent);
			}
		});




	}

	private String[] SELECT = {"kullanici", "sifre"};
	private Cursor KayitGetir() {
		SQLiteDatabase db = veri.getReadableDatabase();
		Cursor cursor = db.query("devamOyun", SELECT, null, null, null, null, null);

		startManagingCursor(cursor);
		return cursor;
	}





	private void degistir() {
		for(int x=0;x<satirSayisi;x++)
		{
			for(int y=0;y<sutunSayisi;y++)
			{

				blocks[x][y].deleteMine();
			}
		}


	}




	private void kayitEkleme(String a,String b,String c,int d,int e){
		SQLiteDatabase db = veri.getWritableDatabase();
		ContentValues veriler = new ContentValues();

		veriler.put("kullanici", a);
		veriler.put("sifre", b);
		veriler.put("dizilis", c);

		veriler.put("satir", d);
		veriler.put("sutun", e);
		db.insertOrThrow("devamOyun", "kullanici", veriler);
	}

	private void startNewGame()
	{

		mayinAlaniniOlustur();

		mayinAlaniniGoster();

		minesToFind = toplamMayinSayisi;
		isOyunBitti = false;
		secondsPassed = 0;
	}

	private void mayinAlaniniGoster()
	{

		for (int satir = 1; satir < satirSayisi + 1; satir++)
		{
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams((blockBoyutu + 2 * blockBoslugu) * sutunSayisi, blockBoyutu + 2 * blockBoslugu));

			for (int sutun = 1; sutun < sutunSayisi + 1; sutun++)
			{
				blocks[satir][sutun].setLayoutParams(new LayoutParams(
						blockBoyutu + 2 * blockBoslugu,
						blockBoyutu + 2 * blockBoslugu));
				blocks[satir][sutun].setPadding(blockBoslugu, blockBoslugu, blockBoslugu, blockBoslugu);
				tableRow.addView(blocks[satir][sutun]);
			}
			mineField.addView(tableRow,new TableLayout.LayoutParams(
					(blockBoyutu + 2 * blockBoslugu) * sutunSayisi, blockBoyutu + 2 * blockBoslugu));
		}
	}

	private void oyundanCikis()
	{
		zamaniDurdur();
		textViewZaman.setText("000");
		textViewMayinSayisi.setText("000");
		smileBtn.setBackgroundResource(R.drawable.smile);


		mineField.removeAllViews();


		isZamanKontrol = false;
		areMinesSet = false;
		isOyunBitti = false;
		minesToFind = 0;
	}

	private void mayinAlaniniOlustur()
	{

		blocks = new Block[satirSayisi + 2][sutunSayisi + 2];

		for (int satir = 0; satir < satirSayisi + 2; satir++)
		{
			for (int sutun = 0; sutun < sutunSayisi + 2; sutun++)
			{
				blocks[satir][sutun] = new Block(this);
				blocks[satir][sutun].setDefaults();


				final int gecerliSatir = satir;
				final int gecerliSutun = sutun;


				blocks[satir][sutun].setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						if(!blocks[gecerliSatir][gecerliSutun].hasMine())
						{
							blocks[gecerliSatir][gecerliSutun].plantMine();

							blocks[gecerliSatir][gecerliSutun].setMineIcon(false);
						}
						else
						{
							blocks[gecerliSatir][gecerliSutun].deleteMine();

							blocks[gecerliSatir][gecerliSutun].setBlockAsDisabled(true);
						}



					}
				});

				blocks[satir][sutun].setOnLongClickListener(new OnLongClickListener()
				{
					public boolean onLongClick(View view)
					{
						return true;

					}
				});
			}
		}
	}

	private boolean checkGameWin()
	{
		for (int satir = 1; satir < satirSayisi + 1; satir++)
		{
			for (int sutun = 1; sutun < sutunSayisi + 1; sutun++)
			{
				if (!blocks[satir][sutun].hasMine() && blocks[satir][sutun].isCovered())
				{
					return false;
				}
			}
		}
		return true;
	}

	private void mayinSayisiGostergesiniGuncelle()
	{
		if (minesToFind < 0)
		{
			textViewMayinSayisi.setText(Integer.toString(minesToFind));
		}
		else if (minesToFind < 10)
		{
			textViewMayinSayisi.setText("00" + Integer.toString(minesToFind));
		}
		else if (minesToFind < 100)
		{
			textViewMayinSayisi.setText("0" + Integer.toString(minesToFind));
		}
		else
		{
			textViewMayinSayisi.setText(Integer.toString(minesToFind));
		}
	}

	private void winGame()
	{
		kontrol=false;
		zamaniDurdur();
		isZamanKontrol = false;
		isOyunBitti = true;
		minesToFind = 0;


		smileBtn.setBackgroundResource(R.drawable.cool);

		mayinSayisiGostergesiniGuncelle();


		for (int satir = 1; satir < satirSayisi + 1; satir++)
		{
			for (int sutun = 1; sutun < sutunSayisi + 1; sutun++)
			{
				blocks[satir][sutun].setClickable(false);
				if (blocks[satir][sutun].hasMine())
				{
					blocks[satir][sutun].setBlockAsDisabled(false);
					blocks[satir][sutun].setFlagIcon(true);
				}
			}
		}

		if(AnaMenu.sesCal)
			ses3.start();

		AnaMenu.oyuns[oyunTipi]++;
		AnaMenu.ukazanma[oyunTipi]++;
		if(AnaMenu.ukaybetme[oyunTipi]>AnaMenu.bkaybetme[oyunTipi])
			AnaMenu.bkaybetme[oyunTipi]=AnaMenu.ukaybetme[oyunTipi];
		if(AnaMenu.ukazanma[oyunTipi]>AnaMenu.bkazanma[oyunTipi])
			AnaMenu.bkazanma[oyunTipi]=AnaMenu.ukazanma[oyunTipi];

		AnaMenu.ukaybetme[oyunTipi]=0;

		int puan = 1000000 - secondsPassed*(-secondsPassed);
		if(AnaMenu.enyuksek[oyunTipi]<puan)
			AnaMenu.enyuksek[oyunTipi]=puan;
		showDialog("Kazandın " + Integer.toString(secondsPassed) + " saniyede!", 1000, false, true);

	}

	private void oyunuBitir(int gecerliSatir, int gecerliSutun)
	{
		kontrol=false;
		isOyunBitti = true;
		zamaniDurdur();
		isZamanKontrol = false;
		smileBtn.setBackgroundResource(R.drawable.sad);


		for (int satir = 1; satir < satirSayisi + 1; satir++)
		{
			for (int sutun = 1; sutun < sutunSayisi + 1; sutun++)
			{

				blocks[satir][sutun].setBlockAsDisabled(false);


				if (blocks[satir][sutun].hasMine() && !blocks[satir][sutun].isFlagged())
				{

					blocks[satir][sutun].setMineIcon(false);
				}


				if (!blocks[satir][sutun].hasMine() && blocks[satir][sutun].isFlagged())
				{

					blocks[satir][sutun].setFlagIcon(false);
				}


				if (blocks[satir][sutun].isFlagged())
				{

					blocks[satir][sutun].setClickable(false);
				}
			}
		}


		blocks[gecerliSatir][gecerliSutun].triggerMine();
		if(AnaMenu.sesCal)
			ses.start();

		/*
		//İstatistikler için
		SQLiteDatabase db2 = veri.getWritableDatabase();
		ContentValues Degerler = new ContentValues();
		String[] Array = { String.valueOf(oyunTipi) };
		Degerler.put("oyuns", (oyuns[oyunTipi]+1));
		try { db2.update("skor", Degerler, "duzey" + "=?",Array);
		}catch(Exception e){  }
		*/
		AnaMenu.oyuns[oyunTipi]++;
		AnaMenu.ukaybetme[oyunTipi]++;
		if(AnaMenu.ukazanma[oyunTipi]>AnaMenu.bkazanma[oyunTipi])
			AnaMenu.bkazanma[oyunTipi]=AnaMenu.ukazanma[oyunTipi];
		if(AnaMenu.ukaybetme[oyunTipi]>AnaMenu.bkaybetme[oyunTipi])
			AnaMenu.bkaybetme[oyunTipi]=AnaMenu.ukaybetme[oyunTipi];
		AnaMenu.ukazanma[oyunTipi]=0;

		showDialog("Tekrar Dene " + Integer.toString(secondsPassed) + " saniye!", 1000, false, false);
	}


	private String[] SELECT2 = {"oyuns", "koyun", "ukazanma", "ukaybetme", "duzey", "enyuksek"};
	private Cursor KayitGetira() {
		SQLiteDatabase db = veri.getReadableDatabase();
		Cursor cursor2 = db.query("skor", SELECT2, null, null, null, null, null);

		startManagingCursor(cursor2);
		return cursor2;
	}

	private void KayitKontrola(Cursor cursor2) {

		int i=0;
		String de="";
		boolean kontrol=false;
		while(cursor2.moveToNext()){

			AnaMenu.oyuns[i] = cursor2.getInt((cursor2.getColumnIndex("oyuns")));
			AnaMenu.koyun[i] = cursor2.getInt((cursor2.getColumnIndex("koyun")));
			AnaMenu.ukazanma[i] = cursor2.getInt((cursor2.getColumnIndex("ukazanma")));
			AnaMenu.ukaybetme[i] = cursor2.getInt((cursor2.getColumnIndex("ukaybetme")));
			AnaMenu.duzey[i] = cursor2.getInt((cursor2.getColumnIndex("duzey")));
			AnaMenu.enyuksek[i] = cursor2.getInt((cursor2.getColumnIndex("enyuksek")));
			if(AnaMenu.duzey[i]!=1&&AnaMenu.duzey[i]!=2&&AnaMenu.duzey[i]!=3)
				kontrol=true;

			i++;
		}

		if(kontrol)
		{
			int x=1;
			while(x<4)
			{
				kayitEkleme2(x);
				x++;
			}
		}

	}

	private void kayitEkleme2(int x){
		SQLiteDatabase db = veri.getWritableDatabase();
		ContentValues veriler = new ContentValues();
		veriler.put("oyuns", 0);
		veriler.put("koyun", 0);
		veriler.put("ukazanma", 0);
		veriler.put("ukaybetme", 0);
		veriler.put("duzey", x);
		veriler.put("enyuksek", 0);
		db.insertOrThrow("skor", "kullanici", veriler);
	}

	private void setMines(int gecerliSatir, int gecerliSutun)
	{

		Random rand = new Random();
		int mayinSatir, mayinSutun;

		for (int satir = 0; satir < toplamMayinSayisi; satir++)
		{
			mayinSatir = rand.nextInt(sutunSayisi);
			mayinSutun = rand.nextInt(satirSayisi);
			if ((mayinSatir + 1 != gecerliSutun) || (mayinSutun + 1 != gecerliSatir))
			{
				if (blocks[mayinSutun + 1][mayinSatir + 1].hasMine())
				{
					satir--;
				}

				blocks[mayinSutun + 1][mayinSatir + 1].plantMine();
			}

			else
			{
				satir--;
			}
		}

		int nearByMineCount;


		for (int satir = 0; satir < satirSayisi + 2; satir++)
		{
			for (int sutun = 0; sutun < sutunSayisi + 2; sutun++)
			{

				nearByMineCount = 0;
				if ((satir != 0) && (satir != (satirSayisi + 1)) && (sutun != 0) && (sutun != (sutunSayisi + 1)))
				{

					for (int ilerikiSatir = -1; ilerikiSatir < 2; ilerikiSatir++)
					{
						for (int ilerikiSutun = -1; ilerikiSutun < 2; ilerikiSutun++)
						{
							if (blocks[satir + ilerikiSatir][sutun + ilerikiSutun].hasMine())
							{

								nearByMineCount++;
							}
						}
					}

					blocks[satir][sutun].setNumberOfMinesInSurrounding(nearByMineCount);
				}

				else
				{
					blocks[satir][sutun].setNumberOfMinesInSurrounding(9);
					blocks[satir][sutun].OpenBlock();
				}
			}
		}
	}

	private void rippleUncover(int rowClicked, int columnClicked)
	{

		if (blocks[rowClicked][columnClicked].hasMine() || blocks[rowClicked][columnClicked].isFlagged())
		{
			return;
		}


		blocks[rowClicked][columnClicked].OpenBlock();


		if (blocks[rowClicked][columnClicked].getNumberOfMinesInSorrounding() != 0 )
		{
			return;
		}


		for (int satir = 0; satir < 3; satir++)
		{
			for (int sutun = 0; sutun < 3; sutun++)
			{

				if (blocks[rowClicked + satir - 1][columnClicked + sutun - 1].isCovered()
						&& (rowClicked + satir - 1 > 0) && (columnClicked + sutun - 1 > 0)
						&& (rowClicked + satir - 1 < satirSayisi + 1) && (columnClicked + sutun - 1 < sutunSayisi + 1))
				{
					rippleUncover(rowClicked + satir - 1, columnClicked + sutun - 1 );
				}
			}
		}
		return;
	}

	public void zamanaiBaslat()
	{
		if (secondsPassed == 0)
		{
			timer.removeCallbacks(updateTimeElasped);

			timer.postDelayed(updateTimeElasped, 1000);
		}
	}

	public void zamaniDurdur()
	{

		timer.removeCallbacks(updateTimeElasped);
	}


	private Runnable updateTimeElasped = new Runnable()
	{
		public void run()
		{
			long currentMilliseconds = System.currentTimeMillis();
			++secondsPassed;

			if (secondsPassed < 10)
			{
				textViewZaman.setText("00" + Integer.toString(secondsPassed));
			}
			else if (secondsPassed < 100)
			{
				textViewZaman.setText("0" + Integer.toString(secondsPassed));
			}
			else
			{
				textViewZaman.setText(Integer.toString(secondsPassed));
			}


			timer.postAtTime(this, currentMilliseconds);

			timer.postDelayed(updateTimeElasped, 1000);
		}
	};

	private void showDialog(String message, int milliseconds, boolean useSmileImage, boolean useCoolImage)
	{

		Toast dialog = Toast.makeText(
				getApplicationContext(),
				message,
				Toast.LENGTH_LONG);

		dialog.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout dialogView = (LinearLayout) dialog.getView();
		ImageView coolImage = new ImageView(getApplicationContext());
		if (useSmileImage)
		{
			coolImage.setImageResource(R.drawable.smile);
		}
		else if (useCoolImage)
		{
			coolImage.setImageResource(R.drawable.cool);
		}
		else
		{
			coolImage.setImageResource(R.drawable.sad);
		}
		dialogView.addView(coolImage, 0);
		dialog.setDuration(milliseconds);
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.yeni_oyun, menu);
		return true;
	}

}
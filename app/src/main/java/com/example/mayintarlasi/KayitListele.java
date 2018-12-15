package com.example.mayintarlasi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;



public class KayitListele extends Activity {
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	private Button b6;
	private Button b7;
	private Button b8;
	private Button b9;
	private Button b10;

	static int yukseklik;
	static int genislik;
	static int[] satirDizi=new int[5];
	static int[] sutunDizi=new int[5];
	static int kG;
	static int kY;
	static int[][] tablo=new int[30][16];

	private VeriTabani veri;

	String[] tablos = new String[10];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kayitlistele);

		veri = new VeriTabani(this);

		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b3 = (Button) findViewById(R.id.b3);
		b4 = (Button) findViewById(R.id.b4);
		b5 = (Button) findViewById(R.id.b5);
		b6 = (Button) findViewById(R.id.b6);
		b7 = (Button) findViewById(R.id.b7);
		b8 = (Button) findViewById(R.id.b8);
		b9 = (Button) findViewById(R.id.b9);
		b10 = (Button) findViewById(R.id.b10);
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		b8.setEnabled(false);
		b9.setEnabled(false);
		b10.setEnabled(false);

		Cursor cursor = KayitGetir();
		KayitKontrol(cursor);


		b1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(1);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(2);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(3);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(4);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b5.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(5);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b6.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(6);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b7.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(7);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b8.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(8);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b9.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(9);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

		b10.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				devametFonksiyon(10);
				startActivity(new Intent("android.intent.action.KayitliOyna"));

			}
		});

	}

	private void devametFonksiyon(int j) {


		int sayi=0;

		for(int x=0;x<satirDizi[j-1];x++)
		{
			for(int y=0;y<sutunDizi[j-1];y++)
			{
				tablo[x][y]=Integer.parseInt(Character.toString(tablos[j-1].charAt(sayi)));
				sayi++;
			}

		}

		yukseklik=satirDizi[j-1];
		genislik=sutunDizi[j-1];
		double kenar =Math.floor(((290-((genislik)*2))/genislik));
		kG=(int) kenar;
	}


	private String[] SELECT = {"isim","dizilis","satir","sutun"};
	private Cursor KayitGetir() {
		SQLiteDatabase db = veri.getReadableDatabase();
		Cursor cursor = db.query("kayitli", SELECT, null, null, null, null, null);

		startManagingCursor(cursor);
		return cursor;
	}

	private void KayitKontrol(Cursor cursor) {


		int i=0;
		while(cursor.moveToNext()){


			String isim = cursor.getString((cursor.getColumnIndex("isim")));
			String dizilis = cursor.getString((cursor.getColumnIndex("dizilis")));
			int satirS = cursor.getInt((cursor.getColumnIndex("satir")));
			int sutunS = cursor.getInt((cursor.getColumnIndex("sutun")));

			tablos[i]=dizilis;
			satirDizi[i]=satirS;
			sutunDizi[i]=sutunS;
			i++;
			if(i>11)
			{
				i=1;
			}

			if(i==1)
			{

				b1.setEnabled(true);
				b1.setText(isim);
			}
			else if(i==2)
			{
				b2.setEnabled(true);
				b2.setText(isim);
			}
			else if(i==3)
			{
				b3.setEnabled(true);
				b3.setText(isim);
			}
			else if(i==4)
			{
				b4.setEnabled(true);
				b4.setText(isim);
			}
			else if(i==5)
			{
				b5.setEnabled(true);
				b5.setText(isim);
			}
			else if(i==6)
			{
				b6.setEnabled(true);
				b6.setText(isim);
			}
			else if(i==7)
			{
				b7.setEnabled(true);
				b7.setText(isim);
			}
			else if(i==8)
			{
				b8.setEnabled(true);
				b8.setText(isim);
			}
			else if(i==9)
			{
				b9.setEnabled(true);
				b9.setText(isim);
			}
			else if(i==10)
			{
				b10.setEnabled(true);
				b10.setText(isim);
			}


		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kayit_listele, menu);
		return true;
	}

}
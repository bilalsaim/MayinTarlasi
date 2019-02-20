package com.example.mayintarlasi.control;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayintarlasi.R;
import com.example.mayintarlasi.model.Kare;
import com.example.mayintarlasi.model.Secenek;
import com.otaliastudios.zoom.ZoomApi;
import com.otaliastudios.zoom.ZoomLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.N)
public class YeniOyun extends Activity {

    private ImageButton btnBaslat;
    private TextView textMayinSayisi;
    private TextView textZaman;
    private MediaPlayer sesKaybetme;
    private MediaPlayer sesKazanma;
    private Kare kareler[][];
    private Handler zaman = new Handler();
    private int gecenSure = 0;
    private boolean isZamanBasladi;
    private boolean isMayinlarYerlestirildi;
    private boolean isOyunBitti;
    private int kalanMayin; //Bulunmamış mayın sayısı
    private Secenek secenek;

    private int kareBoyutu = 128; // Blok yükseklikleri
    private int kareBoslugu = 0; // Kareler arası boşluklar
    private TableLayout mayinAlani;
    private ZoomLayout mayinYakinlastirma;
    GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yenioyunl);
        gestureDetector = new GestureDetector(this.getBaseContext(), new GestureListener());

        secenek = Secenek.getInstance();
        mayinYakinlastirma = (ZoomLayout) findViewById(R.id.mayinYakinlastirma);

		textMayinSayisi = (TextView) findViewById(R.id.yoTextMayinSayisi);
		textZaman = (TextView) findViewById(R.id.yoTextSure);

		sesKaybetme = MediaPlayer.create(this, R.raw.oyunkaybedildi);
		sesKazanma = MediaPlayer.create(this, R.raw.alkis);

		showDialog(getString(R.string.oyun_baslat), 5000, true, false);

        btnBaslat = (ImageButton) findViewById(R.id.yoBtnBaslat);
        btnBaslat.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                oyunuBaslat();
            }
        });

        mayinAlani = (TableLayout)findViewById(R.id.MayinAlani);

        mayinAlani.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        mayinYakinlastirma.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
	}

    private void oyunuSifirla()
    {
        btnBaslat.setBackgroundResource(R.drawable.smiley_button_states);
        int buyukKenar = Math.max(secenek.getX(), secenek.getY());
        mayinYakinlastirma.setMaxZoom((float) (((buyukKenar-9.0)*0.1)+3.0), ZoomApi.TYPE_ZOOM);
        mayinYakinlastirma.zoomTo(1f, true);
        zamaniDurdur();
        textZaman.setText("000");
        textMayinSayisi.setText("000");
        mayinAlani.removeAllViews();

        isZamanBasladi = false;
        isMayinlarYerlestirildi = false;
        isOyunBitti = false;
        kalanMayin = 0;
    }

    public void zamanaiBaslat()
    {
        if (gecenSure == 0)
        {
            zaman.removeCallbacks(updateTimeElasped);
            zaman.postDelayed(updateTimeElasped, 1000);
        }
    }

    public void zamaniDurdur()
    {
        zaman.removeCallbacks(updateTimeElasped);
    }

	private void oyunuBaslat()
	{
        oyunuSifirla();
		mayinAlaniniOlustur();
		mayinAlaniniGoster();

		kalanMayin = secenek.getMayin();
		isOyunBitti = false;
		gecenSure = 0;
	}

    private void mayinAlaniniOlustur()
    {
        kareler = new Kare[secenek.getX() + 2][secenek.getY() + 2];

        for (int satir = 0; satir < secenek.getX() + 2; satir++)
        {
            for (int sutun = 0; sutun < secenek.getY() + 2; sutun++)
            {
                kareler[satir][sutun] = new Kare(this);
                kareler[satir][sutun].varsayilanAyarlar();

                final int gecerliSatir = satir;
                final int gecerliSutun = sutun;

                kareler[satir][sutun].setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return gestureDetector.onTouchEvent(event);
                    }
                });

                kareler[satir][sutun].setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if(isOyunBitti) return;

                        if (!isZamanBasladi)
                        {
                            zamanaiBaslat();
                            mayinSayisiGostergesiniGuncelle();
                            isZamanBasladi = true;
                        }
                        
                        if (!isMayinlarYerlestirildi)
                        {
                            isMayinlarYerlestirildi = true;
                            mayinlariYerlestir(gecerliSatir, gecerliSutun);
                        }

                        if (!kareler[gecerliSatir][gecerliSutun].isBayrak() && !kareler[gecerliSatir][gecerliSutun].isSoruIsareti())
                        {
                            kareAc(gecerliSatir, gecerliSutun);

                            //Mayına basılıysa oyunu kaybet
                            if (kareler[gecerliSatir][gecerliSutun].isMayin())
                            {
                                oyunKaybedildi(gecerliSatir,gecerliSutun);
                            }
                            else if (kazanmaKontrol())
                            {
                                oyunKazanildi();
                            }
                        }
                    }
                });

                kareler[satir][sutun].setOnLongClickListener(new OnLongClickListener()
                {
                    public boolean onLongClick(View view)
                    {
                        if(isOyunBitti || !kareler[gecerliSatir][gecerliSutun].isKapali()) return true;

                        if (kareler[gecerliSatir][gecerliSutun].isClickable() &&
                                (kareler[gecerliSatir][gecerliSutun].isEnabled() || kareler[gecerliSatir][gecerliSutun].isBayrak()))
                        {
                            if (!kareler[gecerliSatir][gecerliSutun].isBayrak() && !kareler[gecerliSatir][gecerliSutun].isSoruIsareti())
                            {
                                kareler[gecerliSatir][gecerliSutun].setDisabledKare(false);
                                kareler[gecerliSatir][gecerliSutun].setBayrakSimgesi(true);
                                kareler[gecerliSatir][gecerliSutun].setBayrak(true);
                                kalanMayin--;
                                mayinSayisiGostergesiniGuncelle();
                            }
                            else if (!kareler[gecerliSatir][gecerliSutun].isSoruIsareti() && secenek.isSoruIsareti())
                            {
                                kareler[gecerliSatir][gecerliSutun].setDisabledKare(true);
                                kareler[gecerliSatir][gecerliSutun].setSoruIsaretiSimgesi(true);
                                kareler[gecerliSatir][gecerliSutun].setBayrak(false);
                                kareler[gecerliSatir][gecerliSutun].setSoruIsareti(true);
                                kalanMayin++;
                                mayinSayisiGostergesiniGuncelle();
                            }
                            else
                            {
                                kareler[gecerliSatir][gecerliSutun].setDisabledKare(true);
                                kareler[gecerliSatir][gecerliSutun].tumSimgeleriTemizle();
                                kareler[gecerliSatir][gecerliSutun].setSoruIsareti(false);

                                if (kareler[gecerliSatir][gecerliSutun].isBayrak())
                                {
                                    kalanMayin++;
                                    mayinSayisiGostergesiniGuncelle();
                                }

                                kareler[gecerliSatir][gecerliSutun].setBayrak(false);
                            }

                            mayinSayisiGostergesiniGuncelle();
                        }

                        return true;
                    }
                });
            }
        }
    }

    private void mayinlariYerlestir(int gecerliSatir, int gecerliSutun)
    {
        Random rand = new Random();
        int mayinSatir, mayinSutun;

        int satir = 0;
        while(satir < secenek.getMayin()){
            mayinSatir = rand.nextInt(secenek.getY());
            mayinSutun = rand.nextInt(secenek.getX());
            if ((mayinSatir + 1 != gecerliSutun) || (mayinSutun + 1 != gecerliSatir))
            {
                if (!kareler[mayinSutun + 1][mayinSatir + 1].isMayin())
                {
                    kareler[mayinSutun + 1][mayinSatir + 1].mayinEkle();
                    satir++;
                }
            }
        }

        int komsuMayinSayisi;
        for (satir = 0; satir < secenek.getX() + 2; satir++)
        {
            for (int sutun = 0; sutun < secenek.getY() + 2; sutun++)
            {
                komsuMayinSayisi = 0;
                if ((satir != 0) && (satir != (secenek.getX() + 1)) && (sutun != 0) && (sutun != (secenek.getY() + 1)))
                {
                    for (int ilerikiSatir = -1; ilerikiSatir < 2; ilerikiSatir++)
                    {
                        for (int ilerikiSutun = -1; ilerikiSutun < 2; ilerikiSutun++)
                        {
                            if (kareler[satir + ilerikiSatir][sutun + ilerikiSutun].isMayin())
                            {
                                komsuMayinSayisi++;
                            }
                        }
                    }

                    kareler[satir][sutun].setKomsuMayinSayisi(komsuMayinSayisi);
                }
                else
                {
                    kareler[satir][sutun].setKomsuMayinSayisi(9);
                    kareler[satir][sutun].kareAc();
                }
            }
        }
    }

    private void mayinAlaniniGoster()
	{
		for (int satir = 1; satir < secenek.getX() + 1; satir++)
		{
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams((kareBoyutu + 2 * kareBoslugu) * secenek.getY(), kareBoyutu + 2 * kareBoslugu));

			for (int sutun = 1; sutun < secenek.getY() + 1; sutun++)
			{
                LayoutParams params = new LayoutParams(kareBoyutu + 2 * kareBoslugu, kareBoyutu + 2 * kareBoslugu);
                int kareMargin=5;
                params.setMargins(kareMargin, kareMargin, kareMargin, kareMargin);

				kareler[satir][sutun].setLayoutParams(params);
				kareler[satir][sutun].setPadding(kareBoslugu, kareBoslugu, kareBoslugu, kareBoslugu);
				tableRow.addView(kareler[satir][sutun]);
			}
			mayinAlani.addView(tableRow,new TableLayout.LayoutParams(
					(kareBoyutu + 2 * kareBoslugu) * secenek.getY(), kareBoyutu + 2 * kareBoslugu));
		}
	}

	private boolean kazanmaKontrol()
	{
		for (int satir = 1; satir < secenek.getX() + 1; satir++)
		{
			for (int sutun = 1; sutun < secenek.getY() + 1; sutun++)
			{
				if (!kareler[satir][sutun].isMayin() && kareler[satir][sutun].isKapali()) return false;
			}
		}
		return true;
	}

	private void mayinSayisiGostergesiniGuncelle()
	{
	    String onEk = (kalanMayin < 100 && kalanMayin >= 0)? StringUtils.repeat("0", 3-String.valueOf(kalanMayin).length()) : "";
		textMayinSayisi.setText(onEk + Integer.toString(kalanMayin));
	}

	private void oyunKazanildi()
	{
		zamaniDurdur();
		isZamanBasladi = false;
		isOyunBitti = true;
		kalanMayin = 0;

		btnBaslat.setBackgroundResource(R.drawable.cool);
		mayinSayisiGostergesiniGuncelle();

		for (int satir = 1; satir < secenek.getX() + 1; satir++)
		{
			for (int sutun = 1; sutun < secenek.getY() + 1; sutun++)
			{
				kareler[satir][sutun].setClickable(false);
				if (kareler[satir][sutun].isMayin())
				{
					kareler[satir][sutun].setDisabledKare(false);
					kareler[satir][sutun].setBayrakSimgesi(true);
				}
			}
		}

		if(secenek.isSes()) sesKazanma.start();
        
		showDialog(String.format(getString(R.string.oyun_kazandin), gecenSure), 1000, false, true);
	}

	private void oyunKaybedildi(int gecerliSatir, int gecerliSutun)
	{
		isOyunBitti = true;
		zamaniDurdur();
		isZamanBasladi = false;
		btnBaslat.setBackgroundResource(R.drawable.sad);

		for (int satir = 1; satir < secenek.getX() + 1; satir++)
		{
			for (int sutun = 1; sutun < secenek.getY() + 1; sutun++)
			{
                kareler[satir][sutun].setClickable(false);

				if (kareler[satir][sutun].isMayin() && !kareler[satir][sutun].isBayrak())
				{
					kareler[satir][sutun].setMineIcon(false);
				}
				else if (!kareler[satir][sutun].isMayin() && kareler[satir][sutun].isBayrak())
				{
					kareler[satir][sutun].setBayrakSimgesi(false);
				}
				else if (!(kareler[satir][sutun].isMayin() && kareler[satir][sutun].isBayrak()))
                {
                    //kareler[satir][sutun].kareAc();
                }
			}
		}

		kareler[gecerliSatir][gecerliSutun].triggerMine();
		if(secenek.isSes()) sesKaybetme.start();

		showDialog(String.format(getString(R.string.oyun_kaybettin), gecenSure), 1000, false, false);
	}

	private void kareAc(int tiklananSatir, int tiklananSutun)
	{
		if (kareler[tiklananSatir][tiklananSutun].isMayin() || kareler[tiklananSatir][tiklananSutun].isBayrak()) return;

		kareler[tiklananSatir][tiklananSutun].kareAc();

		if (kareler[tiklananSatir][tiklananSutun].getKomsuMayinSayisi() != 0 ) return;

		for (int satir = 0; satir < 3; satir++)
		{
			for (int sutun = 0; sutun < 3; sutun++)
			{
				if (kareler[tiklananSatir + satir - 1][tiklananSutun + sutun - 1].isKapali()
						&& (tiklananSatir + satir - 1 > 0) && (tiklananSutun + sutun - 1 > 0)
						&& (tiklananSatir + satir - 1 < secenek.getX() + 1) && (tiklananSutun + sutun - 1 < secenek.getY() + 1))
				{
					kareAc(tiklananSatir + satir - 1, tiklananSutun + sutun - 1 );
				}
			}
		}
		return;
	}

	private Runnable updateTimeElasped = new Runnable()
	{
		public void run()
		{
			long currentMilliseconds = System.currentTimeMillis();
			++gecenSure;

			String onEk = (gecenSure < 100)? StringUtils.repeat("0", 3-String.valueOf(gecenSure).length()) : "";
			textZaman.setText(onEk + Integer.toString(gecenSure));

			zaman.postAtTime(this, currentMilliseconds);
			zaman.postDelayed(updateTimeElasped, 1000);
		}
	};

	private void showDialog(String message, int milliseconds, boolean isBaslatResim, boolean isKazanildiResim)
	{
		Toast dialog = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
		dialog.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout dialogView = (LinearLayout) dialog.getView();
		ImageView uyariResim = new ImageView(getApplicationContext());

		if (isBaslatResim) uyariResim.setImageResource(R.drawable.smile);
		else if (isKazanildiResim) uyariResim.setImageResource(R.drawable.cool);
		else uyariResim.setImageResource(R.drawable.sad);

		dialogView.addView(uyariResim, 0);
		dialog.setDuration(milliseconds);
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.yeni_oyun, menu);
		return true;
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(mayinYakinlastirma.getZoom() == 1f) {

            }
            else
                mayinYakinlastirma.zoomTo(1f, true);
            return true;
        }
    }
}
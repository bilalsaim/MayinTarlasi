package com.example.mayintarlasi.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mayintarlasi.R;
import com.example.mayintarlasi.model.Secenek;
import com.example.mayintarlasi.model.Zorluk;

public class Secenekler extends Activity {
	private Secenek secenek;

	//Seçenekler.xml değişkenleri
	private RadioGroup sRadioGroupZorluk;
	private Button sBtnKaydet;
	private Button sBtnGeri;
	private CheckBox sCheckSoruIsareti;
	private CheckBox sCheckSes;
	private static final int ALERT_DIALOG1 = 1;
	private static final int ALERT_DIALOG2 = 2;
	private NestedScrollView nestedScrollView;
	private boolean zorlukOzel;
	private SeekBar[] zorlukOzelSeek;
	private TextView[] zorlukOzelText;
	private String[] zorlukOzelMetin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seceneklerl);

		secenek = Secenek.getInstance();

		sCheckSoruIsareti = (CheckBox)findViewById(R.id.sCheckSoruIsareti);
		if(secenek.isSoruIsareti()){
			sCheckSoruIsareti.setChecked(true);
		}

		sCheckSes = (CheckBox)findViewById(R.id.sCheckSes);
		if(secenek.isSes()){
			sCheckSes.setChecked(true);
		}


		sRadioGroupZorluk = (RadioGroup)findViewById(R.id.sRadioGroupZorluk);
		((RadioButton)sRadioGroupZorluk.getChildAt(secenek.getZorluk().getI())).setChecked(true);

		zorlukOzelSeek = new SeekBar[]{(SeekBar)findViewById(R.id.sSeekGenislik),
				(SeekBar)findViewById(R.id.sSeekYukseklik),
				(SeekBar)findViewById(R.id.sSeekMayin)};

		zorlukOzelText = new TextView[]{(TextView)findViewById(R.id.sTextGenislik),
				(TextView)findViewById(R.id.sTextYukseklik),
				(TextView)findViewById(R.id.sTextMayin)};

		zorlukOzelMetin = new String[]{getString(R.string.secenekler_ozel_genislik_f),
                getString(R.string.secenekler_ozel_yukseklik_f), getString(R.string.secenekler_ozel_mayin_f)};
		zorlukOzel = secenek.getZorluk().equals(Zorluk.OZEL);

		if(zorlukOzel){
			zorlukOzelSeek[0].setProgress(secenek.getZorluk().getX()-9);
			zorlukOzelSeek[1].setProgress(secenek.getZorluk().getY()-9);
            zorlukOzelSeek[2].setMax(secenek.getZorluk().getX() * secenek.getZorluk().getY() - 30);
			zorlukOzelSeek[2].setProgress(secenek.getZorluk().getMayin()-10);
		}

		//Zorluk özel ise özel bölümünü aktif et değilse kapa
		for(int i=0; i < zorlukOzelSeek.length; i++){
			zorlukOzelSeek[i].setEnabled(zorlukOzel);
			int artis = (i == 2) ? 10 : 9;
			zorlukOzelText[i].setText(String.format(zorlukOzelMetin[i], zorlukOzelSeek[i].getProgress() + artis));
		}

        nestedScrollView = (NestedScrollView) findViewById(R.id.sNestedScrollView);
        nestedScrollView.postDelayed(new Runnable() {
            public void run() {
                if(!zorlukOzel){
                    nestedScrollView.fullScroll(View.FOCUS_UP);
                    nestedScrollView.scrollTo(0,0);
                }
            }
        }, 500);

		sBtnGeri = (Button) findViewById(R.id.sBtnGeri);
		sBtnGeri.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Secenekler.this, AnaMenu.class);
				startActivity(intent);
			}
		});

		for(int i=0;  i < zorlukOzelSeek.length; i++){
			final int zorlukIndeks = i;
			zorlukOzelSeek[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int barDegeri, boolean kullanicidan) {
                    zorlukOzelSeek[2].setMax((zorlukOzelSeek[0].getProgress()+9) * (zorlukOzelSeek[1].getProgress()+9) - 30);
					int artis = (zorlukIndeks == 2) ? 10 : 9;
					zorlukOzelText[zorlukIndeks].setText(String.format(zorlukOzelMetin[zorlukIndeks], barDegeri + artis));
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});
		}

		sBtnKaydet = (Button) findViewById(R.id.sBtnKaydet);
		sBtnKaydet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				boolean dKaydet = false;
				int selectedZorluk = getRadioSelectedIndex(sRadioGroupZorluk);
				//Zorluk derecesi özel ise
				if(selectedZorluk == 3)
				{
					Zorluk ozelZorluk = Zorluk.OZEL;
					ozelZorluk.setX(zorlukOzelSeek[0].getProgress()+9);
					ozelZorluk.setY(zorlukOzelSeek[1].getProgress()+9);
					ozelZorluk.setMayin(zorlukOzelSeek[2].getProgress()+10);
					secenek.setZorluk(ozelZorluk);
				}
				else
				{
					secenek.setZorluk(Zorluk.bul(selectedZorluk));
				}

				secenek.setSes(sCheckSes.isChecked());
				secenek.setSoruIsareti(sCheckSoruIsareti.isChecked());

				Intent intent = new Intent(Secenekler.this, AnaMenu.class);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.secenekler, menu);
		return true;
	}

	public void onRadioButtonClicked(View view) {
		boolean zorlukOzel = (getRadioSelectedIndex((RadioGroup)findViewById(R.id.sRadioGroupZorluk)) == 3);
		if(zorlukOzel) nestedScrollView.fullScroll(View.FOCUS_DOWN);

		for(SeekBar tmpSeek : zorlukOzelSeek) tmpSeek.setEnabled(zorlukOzel);
	}

	public int getRadioSelectedIndex(RadioGroup radioGroup){
		return radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

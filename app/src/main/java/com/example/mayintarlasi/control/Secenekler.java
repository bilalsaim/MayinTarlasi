package com.example.mayintarlasi.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mayintarlasi.R;
import com.example.mayintarlasi.model.Secenek;
import com.example.mayintarlasi.model.Zorluk;

public class Secenekler extends Activity {
	private Secenek secenek;

	//Seçenekler.xml değişkenleri
	private RadioGroup sRadioGroupZorluk;
	private Button sBtnKaydet;
	private Button sBtnGeri;
	private EditText sBoxMayin;
	private EditText sBoxGenislik;
	private EditText sBoxYukseklik;
	private CheckBox sCheckSoruIsareti;
	private CheckBox sCheckSes;
	private static final int ALERT_DIALOG1 = 1;
	private static final int ALERT_DIALOG2 = 2;
	private NestedScrollView nestedScrollView;
	private boolean zorlukOzel;

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

		sBoxMayin = (EditText)findViewById(R.id.sBoxMayin);
		sBoxGenislik = (EditText)findViewById(R.id.sBoxGenislik);
		sBoxYukseklik = (EditText)findViewById(R.id.sBoxYukseklik);

		sRadioGroupZorluk = (RadioGroup)findViewById(R.id.sRadioGroupZorluk);
		((RadioButton)sRadioGroupZorluk.getChildAt(secenek.getZorluk().getI())).setChecked(true);

		zorlukOzel = secenek.getZorluk().equals(Zorluk.OZEL);
		//Zorluk özel ise özel bölümünü aktif et değilse kapa
		sBoxMayin.setEnabled(zorlukOzel);
		sBoxGenislik.setEnabled(zorlukOzel);
		sBoxYukseklik.setEnabled(zorlukOzel);

		if(zorlukOzel){
			sBoxMayin.setText(Integer.toString(secenek.getZorluk().getMayin()));
			sBoxGenislik.setText(Integer.toString(secenek.getZorluk().getX()));
			sBoxYukseklik.setText(Integer.toString(secenek.getZorluk().getY()));
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
				startActivity(new Intent("android.intent.action.OyunMenu"));
			}
		});

		sBtnKaydet = (Button) findViewById(R.id.sBtnKaydet);
		sBtnKaydet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				boolean dKaydet = false;
				int selectedZorluk = getRadioSelectedIndex(sRadioGroupZorluk);
				//Zorluk derecesi özel ise
				if(selectedZorluk == 3)
				{
					int mayinS = Integer.parseInt(sBoxMayin.getText().toString());
					int genisS = Integer.parseInt(sBoxGenislik.getText().toString());
					int yuksekS = Integer.parseInt(sBoxYukseklik.getText().toString());

					if(mayinS<10||mayinS>400) {
						showDialog(ALERT_DIALOG1);
					}
					else if(yuksekS<9||yuksekS>30) {
						showDialog(ALERT_DIALOG1);
					}
					else if(genisS<9||genisS>16) {
						showDialog(ALERT_DIALOG1);
					}
					else {
						Zorluk ozelZorluk = Zorluk.OZEL;
						ozelZorluk.setMayin(mayinS);
						ozelZorluk.setX(genisS);
						ozelZorluk.setY(yuksekS);
						secenek.setZorluk(ozelZorluk);

						dKaydet=true;
					}
				}
				else
				{
					secenek.setZorluk(Zorluk.bul(selectedZorluk));
					dKaydet=true;
				}

				if(dKaydet) {
					secenek.setSes(sCheckSes.isChecked());
					secenek.setSoruIsareti(sCheckSoruIsareti.isChecked());
					startActivity(new Intent("android.intent.action.OyunMenu"));
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.secenekler, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;

		AlertDialog.Builder builder;
		switch(id) {
			case ALERT_DIALOG1:
				builder = new AlertDialog.Builder(this);
				builder.setMessage("Lütfen girdiğiniz sayıların verilen aralıklarda olmasına dikkat ediniz!")
						.setCancelable(false)
						.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//Do something here
							}
						});
				dialog = builder.create();
				break;
			case ALERT_DIALOG2:
				builder = new AlertDialog.Builder(this);
				builder.setMessage("Kullanıcı adı veya Şifre alanı boş bırakılamaz!")
						.setCancelable(false)
						.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//Do something here
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//Do something here
							}
						});

				dialog = builder.create();
				break;
			default:
				dialog = null;
		}
		return dialog;

	}

	public void onRadioButtonClicked(View view) {
		boolean zorlukOzel = (getRadioSelectedIndex((RadioGroup)findViewById(R.id.sRadioGroupZorluk)) == 3);
		if(zorlukOzel)
        {
            nestedScrollView.fullScroll(View.FOCUS_DOWN);
        }
		findViewById(R.id.sBoxMayin).setEnabled(zorlukOzel);
		findViewById(R.id.sBoxGenislik).setEnabled(zorlukOzel);
		findViewById(R.id.sBoxYukseklik).setEnabled(zorlukOzel);
	}

	public int getRadioSelectedIndex(RadioGroup radioGroup){
		return radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
	}
}

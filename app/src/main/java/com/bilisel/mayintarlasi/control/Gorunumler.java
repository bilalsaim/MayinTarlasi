package com.bilisel.mayintarlasi.control;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bilisel.mayintarlasi.R;
import com.bilisel.mayintarlasi.model.Gorunum;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Gorunumler extends Activity {

	//gorunuml.xml değişkenleri
	private RadioGroup gRadioGroupMayin;
	private RadioGroup gRadioGroupKare;

	Gorunum gorunum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gorunuml);

		gorunum = Gorunum.getSingleton();

		gRadioGroupKare = (RadioGroup) findViewById(R.id.gRadioGroupKare);
		gRadioGroupMayin = (RadioGroup) findViewById(R.id.gRadioGroupMayin);

		int indeksKare = gorunum.getKareKapali().getIndeks();
		int indeksMayin = gorunum.getMayin().getIndeks();
		if (savedInstanceState != null) {
			indeksKare = savedInstanceState.getInt("radioGroupKare", 0);
			indeksMayin = savedInstanceState.getInt("radioGroupMayin", 0);
		}

		((RadioButton)gRadioGroupKare.getChildAt(indeksKare)).setChecked(true);
		((RadioButton)gRadioGroupMayin.getChildAt(indeksMayin)).setChecked(true);

		Button gBtnKaydet = (Button) findViewById(R.id.gBtnKaydet);
		gBtnKaydet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				gorunum.getKareKapali().setIndeks(getRadioSelectedIndex(gRadioGroupKare));
				gorunum.getMayin().setIndeks(getRadioSelectedIndex(gRadioGroupMayin));

				Intent intent = new Intent(Gorunumler.this, AnaMenu.class);
				startActivity(intent);
			}
		});

		Button gBtnGeri = (Button) findViewById(R.id.gBtnGeri);
		gBtnGeri.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Gorunumler.this, AnaMenu.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gorunum, menu);
		return true;
	}

	public int getRadioSelectedIndex(RadioGroup radioGroup){
		return radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Ekran çevrilince hafızada tutmak istediklerini ekle
		// Bundle.putStringArray() da kullanılabilir
		outState.putInt("radioGroupKare", getRadioSelectedIndex(gRadioGroupKare));
		outState.putInt("radioGroupMayin", getRadioSelectedIndex(gRadioGroupMayin));
	}
}

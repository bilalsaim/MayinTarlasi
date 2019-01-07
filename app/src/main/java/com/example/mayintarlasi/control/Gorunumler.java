package com.example.mayintarlasi.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mayintarlasi.R;
import com.example.mayintarlasi.model.Gorunum;

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
		((RadioButton)gRadioGroupKare.getChildAt(gorunum.getKareKapali().getIndeks())).setChecked(true);

		gRadioGroupMayin = (RadioGroup) findViewById(R.id.gRadioGroupMayin);
		((RadioButton)gRadioGroupMayin.getChildAt(gorunum.getMayin().getIndeks())).setChecked(true);

		Button gBtnKaydet = (Button) findViewById(R.id.gBtnKaydet);
		gBtnKaydet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				gorunum.getKareKapali().setIndeks(getRadioSelectedIndex(gRadioGroupKare));
				gorunum.getMayin().setIndeks(getRadioSelectedIndex(gRadioGroupMayin));

				startActivity(new Intent("android.intent.action.OyunMenu"));
			}
		});

		Button gBtnGeri = (Button) findViewById(R.id.gBtnGeri);
		gBtnGeri.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.OyunMenu"));
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
}

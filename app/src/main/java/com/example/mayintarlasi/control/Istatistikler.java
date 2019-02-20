package com.example.mayintarlasi.control;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mayintarlasi.R;

public class Istatistikler extends Activity {

	//Istatistiklerl.xml değişkenleri
	private Button iBtnGeri;
	private Button iBtnAcemi;
	private Button iBtnDeneyimli;
	private Button iBtnUzman;
	private TextView iTextMulti;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.istatistiklerl);

		iTextMulti = (TextView)findViewById(R.id.iTextMulti);
		iBtnAcemi = (Button) findViewById(R.id.iBtnAcemi);
		iBtnDeneyimli = (Button) findViewById(R.id.iBtnDeneyimli);
		iBtnUzman = (Button) findViewById(R.id.iBtnUzman);

		setIstatistikText(0);

		iBtnAcemi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setIstatistikText(0);
			}
		});

		iBtnDeneyimli.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setIstatistikText(1);
			}
		});

		iBtnUzman.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setIstatistikText(2);
			}
		});

		iBtnGeri = (Button) findViewById(R.id.iBtnGeri);
		iBtnGeri.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.OyunMenu"));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.istatistikler, menu);
		return true;
	}

	private void setIstatistikText(int index){
		//iTextMulti.setText(istatistik.getText(index));
		iBtnAcemi.setEnabled(index != 0);
		iBtnDeneyimli.setEnabled(index != 1);
		iBtnUzman.setEnabled(index != 2);
	}
}

package com.example.mayintarlasi.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.mayintarlasi.R;

public class OyunMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oyunmenul);

		Button mBtnGorunum = (Button) findViewById(R.id.mBtnGorunum);
		mBtnGorunum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.Gorunumler"));
			}
		});

		Button mBtnGeri = (Button) findViewById(R.id.mBtnGeri);
		mBtnGeri.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getBaseContext(), AnaMenu.class);
				startActivity(intent);
			}
		});

		Button mBtnIstatistik = (Button) findViewById(R.id.mBtnIstatistik);
		mBtnIstatistik.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.Istatistikler"));
			}
		});

		Button mBtnYeni = (Button) findViewById(R.id.mBtnYeni);
		mBtnYeni.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.YeniOyun"));
			}
		});

		Button mBtnSecenek = (Button) findViewById(R.id.mBtnSecenek);
		mBtnSecenek.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.Secenekler"));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.oyun_menu, menu);
		return true;
	}

}
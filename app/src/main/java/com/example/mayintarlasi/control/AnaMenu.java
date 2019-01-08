package com.example.mayintarlasi.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.mayintarlasi.R;
import com.example.mayintarlasi.model.Gorunum;

public class AnaMenu extends Activity {

    Gorunum gorunum;

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anamenul);

        gorunum = Gorunum.getSingleton();
        if(gorunum.getKareKapali() == null){
            gorunum.setKareKapali(getResources().obtainTypedArray(R.array.kareKapali));
            gorunum.setKareAcik(getResources().obtainTypedArray(R.array.kareAcik));
            gorunum.setMayin(getResources().obtainTypedArray(R.array.mayin));
            gorunum.setSoruIsareti(getResources().obtainTypedArray(R.array.soruIsareti));
            gorunum.setBayrak(getResources().obtainTypedArray(R.array.bayrak));
        }

		Button mBtnYeni = (Button) findViewById(R.id.aBtnYeni);
		mBtnYeni.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.YeniOyun"));
			}
		});

		Button mBtnGorunum = (Button) findViewById(R.id.aBtnGorunum);
		mBtnGorunum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.Gorunumler"));
			}
		});

//		Button mBtnIstatistik = (Button) findViewById(R.id.aBtnIstatistik);
//		mBtnIstatistik.setOnClickListener(new OnClickListener() {
//			public void onClick(View view) {
//				startActivity(new Intent("android.intent.action.Istatistikler"));
//			}
//		});

		Button mBtnSecenek = (Button) findViewById(R.id.aBtnSecenek);
		mBtnSecenek.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent("android.intent.action.Secenekler"));
			}
		});

		Button aBtnYardim = (Button) findViewById(R.id.aBtnYardim);
		aBtnYardim.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				setContentView(R.layout.yardiml);
				Button yBtnGeri = (Button) findViewById(R.id.yBtnGeri);
				yBtnGeri.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						Intent intent = new Intent(AnaMenu.this, AnaMenu.class);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ana_menu, menu);
		return true;
	}
}
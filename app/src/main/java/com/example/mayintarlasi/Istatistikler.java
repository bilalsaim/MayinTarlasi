package com.example.mayintarlasi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Istatistikler extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.istatistiklerl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.istatistikler, menu);
		return true;
	}

}

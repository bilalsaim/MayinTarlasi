package com.example.mayintarlasi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OyunMenu extends Activity {
	 private Button oyunMenuBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AnaMenu.a=7;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oyunmenul);
		
		oyunMenuBtn = (Button) findViewById(R.id.gerib);

	    // Setup event handlers
		oyunMenuBtn.setOnClickListener(new OnClickListener() {
	      public void onClick(View view) {
	    	  setContentView(R.layout.anamenul);
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

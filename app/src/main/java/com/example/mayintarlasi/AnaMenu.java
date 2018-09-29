package com.example.mayintarlasi;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnaMenu extends Activity {
	  private Button cikisBtn;
	  private Button oyunMenuBtn;

	  //YeniOyun Değişkenleri
	  static int yukseklik=9;
	  static int genislik=9;
	  static int mayin=10;
	  static int kare=1;
	  static int mayinSekli=1;
	  static int kG;
	  static int kY;

	  //Seçenekler xml seçenekleri
	  private Button skaydetBtn;
	  private Button geriBtn;
	  private EditText mayinTBox;
	  private EditText genislikTextBox;
	  private EditText yukseklikTextBox;
	  private RadioButton radio1;
	  private RadioButton radio2;
	  private RadioButton radio3;
	  private RadioButton radio4;
	  private static final int ALERT_DIALOG1 = 1;
	  private static final int ALERT_DIALOG2 = 2;

	  //oyunmenul.xml değişkenleri
	  private Button gerib;
	  private Button mYeniBtn;
	  private Button mIstatistikBtn;
	  private Button mOlusturBtn;
	  private Button mKayitliBtn;
	  private Button mDevamBtn;
	  private Button mSecenekBtn;
	  private Button mGorunumBtn;

	  //gorunuml.xml değişkenleri
	  private Button gkaydetBtn;
	  private Button ggeriBtn;
	  private RadioButton rKare1;
	  private RadioButton rKare2;
	  private RadioButton rKare3;
	  private RadioButton rKare4;
	  private RadioButton rKare5;
	  private RadioButton rMayin1;
	  private RadioButton rMayin2;
	  private RadioButton rMayin3;

	  static int a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anamenul);



		cikisBtn = (Button) findViewById(R.id.cikisb);


		cikisBtn.setOnClickListener(new OnClickListener() {
	      public void onClick(View view) {
	    	  finish();
	            System.exit(0);
	      }
	    });

		oyunMenuBtn = (Button) findViewById(R.id.oyunb);


		oyunMenuBtn.setOnClickListener(new OnClickListener() {
	      public void onClick(View view) {

	    	  setContentView(R.layout.oyunmenul);


	    	  gerib = (Button) findViewById(R.id.gerib);

	    	  gerib.setOnClickListener(new OnClickListener() {
		      public void onClick(View view) {

		    	  Intent intent = new Intent(AnaMenu.this, AnaMenu.class);

		    	  startActivity(intent);
		      }
		    });

	    	  mYeniBtn = (Button) findViewById(R.id.mYeniBtn);

	    	  mYeniBtn.setOnClickListener(new OnClickListener() {
		      public void onClick(View view) {
				  DisplayMetrics displayMetrics = new DisplayMetrics();
				  getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
				  int height = displayMetrics.heightPixels;
				  int width = displayMetrics.widthPixels;
				  double deger=0.0;
				  if(height<width)
					  deger = height/yukseklik;
				  else
				  		deger = width /genislik;

				  kG = (int) deger;
				  kY = (int) deger;
		    	  startActivity(new Intent("android.intent.action.YeniOyun"));
		      }
			    });

	    	  mGorunumBtn = (Button) findViewById(R.id.mGorunum);

	    	  mGorunumBtn.setOnClickListener(new OnClickListener() {
		      public void onClick(View view) {
		    	  setContentView(R.layout.gorunuml);

		    	  ggeriBtn = (Button) findViewById(R.id.ggeriBtn);

		    	  ggeriBtn.setOnClickListener(new OnClickListener() {
			      public void onClick(View view) {
                      List<RadioButton> rKare = Arrays.asList(
                              (RadioButton)findViewById(R.id.rKare1),
                              (RadioButton)findViewById(R.id.rKare2),
                              (RadioButton)findViewById(R.id.rKare3),
                              (RadioButton)findViewById(R.id.rKare4),
                              (RadioButton)findViewById(R.id.rKare5)
                      );

                      for(RadioButton rb : rKare){
                          if(rb.isChecked()){
                              kare = rKare.indexOf(rb) + 1;
                          }
                      }

                      List<RadioButton> rMayin = Arrays.asList(
                              (RadioButton)findViewById(R.id.rMayin1),
                              (RadioButton)findViewById(R.id.rMayin2),
                              (RadioButton)findViewById(R.id.rMayin3)
                      );

                      for(RadioButton rb : rMayin){
                          if(rb.isChecked()){
                              mayinSekli = rMayin.indexOf(rb) + 1;
                          }
                      }

			    	  Intent intent = new Intent(AnaMenu.this, AnaMenu.class);

			    	  startActivity(intent);
			      }
			    });
		      }
			    });

	    	  mSecenekBtn = (Button) findViewById(R.id.mSecenekBtn);

	    	  mSecenekBtn.setOnClickListener(new OnClickListener() {
		      public void onClick(View view) {
		    	  setContentView(R.layout.seceneklerl);

		    	  radio1 = (RadioButton)findViewById(R.id.radioButton1);
		    	  radio2 = (RadioButton)findViewById(R.id.radioButton2);
		    	  radio3 = (RadioButton)findViewById(R.id.radioButton3);
		    	  radio4 = (RadioButton)findViewById(R.id.radioButton4);

		    	 mayinTBox = (EditText)findViewById(R.id.mayinTBox);
		  	     genislikTextBox = (EditText)findViewById(R.id.genislikTextBox);
		  	     yukseklikTextBox = (EditText)findViewById(R.id.yukseklikTextBox);
		  	    	mayinTBox.setEnabled(false);
		  	    	genislikTextBox.setEnabled(false);
		  	    	yukseklikTextBox.setEnabled(false);

		  	    	skaydetBtn = (Button) findViewById(R.id.skaydetBtn);


		  	    	skaydetBtn.setOnClickListener(new OnClickListener() {
		  		      public void onClick(View view) {
		  		    	  boolean dKaydet=false;
		  		    	int mayinS= Integer.parseInt(mayinTBox.getText().toString());
		  		    	int genisS= Integer.parseInt(genislikTextBox.getText().toString());
		  		    	int yuksekS= Integer.parseInt(yukseklikTextBox.getText().toString());
		  		    	if(radio4.isChecked())
		  		    	{
		  		    	  if(mayinS<10||mayinS>400)
		  		    	  {
		  		    	 showDialog(ALERT_DIALOG1);
		  		    	  }
		  		    	  else if(yuksekS<9||yuksekS>30)
		  		    	  {
		  		    		showDialog(ALERT_DIALOG1);
		  		    	  }
		  		    	  else if(genisS<9||genisS>16)
		  		    	  {
		  		    		showDialog(ALERT_DIALOG1);
		  		    	  }
		  		    	  else
		  		    	  {
		  		    		yukseklik=yuksekS;
		  		    		genislik=genisS;
		  		    		mayin=mayinS;
		  		    		dKaydet=true;
		  		    	  }
		  		    	}
		  		    	else if(radio1.isChecked())
		  		    	{
		  		    		yukseklik=9;
		  		    		genislik=9;
		  		    		mayin=10;
		  		    		dKaydet=true;
		  		    	}
		  		    	else if(radio2.isChecked())
		  		    	{
		  		    		yukseklik=16;
		  		    		genislik=16;
		  		    		mayin=40;
		  		    		dKaydet=true;
		  		    	}
		  		    	else if(radio3.isChecked())
		  		    	{
		  		    		yukseklik=30;
		  		    		genislik=16;
		  		    		mayin=99;
		  		    		dKaydet=true;
		  		    	}

		  		    	if(dKaydet)
		  		    	{
		  		    	Intent intent = new Intent(AnaMenu.this, AnaMenu.class);
				    	  startActivity(intent);
		  		    	}
		  		    	}

		  		    });
		      }
		    });
	    	  /*

	  		*/
	      }
	    });



	}

	@Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;

        AlertDialog.Builder builder;
        switch(id) {
        case ALERT_DIALOG1:
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Lütfen girdiğiniz sayı verilen aralıkta olsun!")
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
            builder.setMessage("My second alert dialog example!")
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

		mayinTBox = (EditText)findViewById(R.id.mayinTBox);
	    genislikTextBox = (EditText)findViewById(R.id.genislikTextBox);
	    yukseklikTextBox = (EditText)findViewById(R.id.yukseklikTextBox);

	    boolean checked = ((RadioButton) view).isChecked();


	    switch(view.getId()) {
	        case R.id.radioButton1:
	            if (checked)
	            	mayinTBox.setEnabled(false);
	            	genislikTextBox.setEnabled(false);
	            	yukseklikTextBox.setEnabled(false);
	            break;
	        case R.id.radioButton2:
	            if (checked)
	            	mayinTBox.setEnabled(false);
	            	genislikTextBox.setEnabled(false);
	            	yukseklikTextBox.setEnabled(false);
	            break;
	        case R.id.radioButton3:
	            if (checked)
	            	mayinTBox.setEnabled(false);
	            	genislikTextBox.setEnabled(false);
	            	yukseklikTextBox.setEnabled(false);
	            break;
	        case R.id.radioButton4:
	            if (checked)
	            	 mayinTBox.setEnabled(true);
	            	genislikTextBox.setEnabled(true);
	            	yukseklikTextBox.setEnabled(true);
	            break;
	    }
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ana_menu, menu);
		return true;
	}

}

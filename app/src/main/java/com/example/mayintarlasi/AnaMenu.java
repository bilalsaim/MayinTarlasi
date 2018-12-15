package com.example.mayintarlasi;

import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Dialog;

public class AnaMenu extends Activity {
	private Button cikisBtn;
	private Button oyunMenuBtn;
	private Button yardimb;

	//YeniOyun Değişkenleri
	static int yukseklik=9;
	static int genislik=9;
	static int mayin=10;
	static int kare=1;
	static int mayinSekli=1;
	static int kG;
	static int kY;
	static int[][] tablo=new int[30][16];
	static String[] tablos=new String[5];
	static int[] satirDizi=new int[5];
	static int[] sutunDizi=new int[5];
	static boolean devamKontrol=false;

	//Seçenekler.xml değişkenleri
	private Button skaydetBtn;
	private Button sgeriBtn;
	private EditText mayinTBox;
	private EditText genislikTextBox;
	private EditText yukseklikTextBox;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioButton radio4;
	private CheckBox soruIsaretiBox;
	private CheckBox sesCalBox;
	private static final int ALERT_DIALOG1 = 1;
	private static final int ALERT_DIALOG2 = 2;
	static boolean sesCal=true;
	static boolean soruI=true;


	//Istatistiklerl.xml değişkenleri
	private Button iGeriBtn;
	private Button acemiBtn;
	private Button denBtn;
	private Button uzmanBtn;
	private TextView t1 ;
	private TextView t2 ;
	private TextView t3 ;
	private TextView t4 ;
	private TextView t5 ;
	private TextView t6 ;
	private TextView t7 ;


	//yardiml.xml değişkenleri
	private Button yGeriBtn;

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

	//devamkaydetl.xml değişkenleri
	private Button dkKaydetBtn;
	private EditText dkKullaniciEditText;
	private EditText dkSifreEditText;
	private Button dkCikisBtn;
	private Button dkKayitBtn;
	private Button dOyun1;
	private Button dOyun2;
	private Button dOyun3;
	private Button dOyun4;
	private Button dOyun5;

	static int[] oyuns={0,0,0}, koyun={0,0,0}, ukazanma={0,0,0}, bkazanma={0,0,0}, ukaybetme={0,0,0}, bkaybetme={0,0,0}, duzey={1,2,3}, enyuksek={0,0,0};
	private VeriTabani veri;

	static int a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anamenul);

		veri = new VeriTabani(this);

		cikisBtn = (Button) findViewById(R.id.cikisb);

		cikisBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				finish();
				System.exit(0);
			}
		});

		yardimb = (Button) findViewById(R.id.yardimb);

		yardimb.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				setContentView(R.layout.yardiml);
				yGeriBtn = (Button) findViewById(R.id.yGeriBtn);

				yGeriBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						Intent intent = new Intent(AnaMenu.this, AnaMenu.class);

						startActivity(intent);
					}
				});
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

				mOlusturBtn = (Button) findViewById(R.id.mOlusturBtn);

				mOlusturBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						double kenar =Math.floor(((290-((genislik)*2))/genislik));
						kG=(int) kenar;
						startActivity(new Intent("android.intent.action.OyunOlustur"));
					}
				});

				mKayitliBtn = (Button) findViewById(R.id.mKayitliBtn);

				mKayitliBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {

						startActivity(new Intent("android.intent.action.KayitListele"));
					}
				});

				mIstatistikBtn = (Button) findViewById(R.id.mIstatistikBtn);
				mIstatistikBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						setContentView(R.layout.istatistiklerl);

						acemiBtn = (Button) findViewById(R.id.acemiBtn);
						denBtn = (Button) findViewById(R.id.denBtn);
						uzmanBtn = (Button) findViewById(R.id.uzmanBtn);
						t1 = (TextView)findViewById(R.id.textV1);
						t2 = (TextView)findViewById(R.id.textV2);
						t3 = (TextView)findViewById(R.id.textV3);
						t4 = (TextView)findViewById(R.id.textV4);
						t5 = (TextView)findViewById(R.id.textV5);
						t6 = (TextView)findViewById(R.id.textV7);
						t7 = (TextView)findViewById(R.id.textV10);

						t1.setText("Oynanan oyun= "+ oyuns[0]);
						t2.setText("Kazanılan oyun= "+ koyun[0]);
						t3.setText("Kazanma yüzdesi= "+((int) oyuns[0]/100)* koyun[0]);
						t4.setText("En uzun üst üste kazanma sayısı= "+ bkazanma[0]);
						t5.setText("En uzun üst üste kaybetme sayısı= "+ bkaybetme[0]);
						t6.setText("Geçerkli seri= "+( bkazanma[0]- bkaybetme[0]));
						t7.setText("En yüksek puan = "+ enyuksek[0]);
						acemiBtn.setEnabled(false);
						denBtn.setEnabled(true);
						uzmanBtn.setEnabled(true);



						acemiBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								t1.setText("Oynanan oyun= "+ oyuns[0]);
								t2.setText("Kazanılan oyun= "+ koyun[0]);
								t3.setText("Kazanma yüzdesi= "+((int) oyuns[0]/100)* koyun[0]);
								t4.setText("En uzun üst üste kazanma sayısı= "+ bkazanma[0]);
								t5.setText("En uzun üst üste kaybetme sayısı= "+ bkaybetme[0]);
								t6.setText("Geçerkli seri= "+( bkazanma[0]- bkaybetme[0]));
								t7.setText("En yüksek puan = "+ enyuksek[0]);
								acemiBtn.setEnabled(false);
								denBtn.setEnabled(true);
								uzmanBtn.setEnabled(true);
							}
						});

						denBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								t1.setText("Oynanan oyun= "+ oyuns[1]);
								t2.setText("Kazanılan oyun= "+ koyun[1]);
								t3.setText("Kazanma yüzdesi= "+( (int)oyuns[1]/100)* koyun[1]);
								t4.setText("En uzun üst üste kazanma sayısı= "+ bkazanma[1]);
								t5.setText("En uzun üst üste kaybetme sayısı= "+ bkaybetme[1]);
								t6.setText("Geçerkli seri= "+( bkazanma[1]- bkaybetme[1]));
								t7.setText("En yüksek puan = "+ enyuksek[1]);
								acemiBtn.setEnabled(true);
								denBtn.setEnabled(false);
								uzmanBtn.setEnabled(true);
							}
						});

						uzmanBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								t1.setText("Oynanan oyun= "+ oyuns[2]);
								t2.setText("Kazanılan oyun= "+ koyun[2]);
								t3.setText("Kazanma yüzdesi= "+( (int)oyuns[2]/100)* koyun[2]);
								t4.setText("En uzun üst üste kazanma sayısı= "+ bkazanma[2]);
								t5.setText("En uzun üst üste kaybetme sayısı= "+ bkaybetme[2]);
								t6.setText("Geçerkli seri= "+( bkazanma[2]- bkaybetme[2]));
								t7.setText("En yüksek puan = "+ enyuksek[2]);
								acemiBtn.setEnabled(true);
								denBtn.setEnabled(true);
								uzmanBtn.setEnabled(false);
							}
						});

						iGeriBtn = (Button) findViewById(R.id.iGeriBtn);

						iGeriBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								Intent intent = new Intent(AnaMenu.this, AnaMenu.class);

								startActivity(intent);
							}
						});
					}
				});


				mDevamBtn = (Button) findViewById(R.id.mDevamBtn);

				mDevamBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						setContentView(R.layout.devamkaydetl);

						TextView t = (TextView)findViewById(R.id.textView);
						t.setText("Kayıtlı kullanıcı adı ve şifrenizi giriniz!");

						dkKullaniciEditText = (EditText)findViewById(R.id.dkKullaniciEditText);
						dkSifreEditText =(EditText) findViewById(R.id.dkSifreEditText);

						dOyun1 = (Button) findViewById(R.id.dOyun1);
						dOyun2 = (Button) findViewById(R.id.dOyun2);
						dOyun3 = (Button) findViewById(R.id.dOyun3);
						dOyun4 = (Button) findViewById(R.id.dOyun4);
						dOyun5 = (Button) findViewById(R.id.dOyun5);

						dOyun1.setEnabled(false);
						dOyun2.setEnabled(false);
						dOyun3.setEnabled(false);
						dOyun4.setEnabled(false);
						dOyun5.setEnabled(false);


						dkKaydetBtn = (Button) findViewById(R.id.dkKaydetBtn);
						dkKayitBtn = (Button) findViewById(R.id.dkKayitBtn);
						dkKaydetBtn.setText("Giriş");
						dkKayitBtn.setVisibility(View.INVISIBLE);

						dkKaydetBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {


								if(!dkKullaniciEditText.getText().toString().equals("") && !dkSifreEditText.getText().toString().equals("")   )
								{
									Cursor cursor = KayitGetir();
									KayitKontrol(cursor,dkKullaniciEditText.getText().toString(),dkSifreEditText.getText().toString());

								}
								else
								{
									showDialog(ALERT_DIALOG2);
								}
							}
						});

						dOyun1 = (Button) findViewById(R.id.dOyun1);
						dOyun1.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								devametFonksiyon(1);
								devamKontrol=true;
								startActivity(new Intent("android.intent.action.YeniOyun"));
							}
						});

						dOyun2 = (Button) findViewById(R.id.dOyun2);
						dOyun2.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								devametFonksiyon(2);
								devamKontrol=true;
								startActivity(new Intent("android.intent.action.YeniOyun"));
							}
						});

						dOyun3 = (Button) findViewById(R.id.dOyun3);
						dOyun3.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								devametFonksiyon(3);
								devamKontrol=true;
								startActivity(new Intent("android.intent.action.YeniOyun"));
							}
						});

						dOyun4 = (Button) findViewById(R.id.dOyun4);
						dOyun4.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								devametFonksiyon(4);
								devamKontrol=true;
								startActivity(new Intent("android.intent.action.YeniOyun"));
							}
						});

						dOyun5 = (Button) findViewById(R.id.dOyun5);
						dOyun5.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								devametFonksiyon(5);
								devamKontrol=true;
								startActivity(new Intent("android.intent.action.YeniOyun"));
							}
						});


						dkCikisBtn = (Button) findViewById(R.id.dkCikisBtn);

						dkCikisBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								Intent intent = new Intent(AnaMenu.this, AnaMenu.class);
								startActivity(intent);
							}
						});
					}
				});

				mYeniBtn = (Button) findViewById(R.id.mYeniBtn);

				mYeniBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						double kenar =Math.floor(((290-((genislik)*2))/genislik));
						kG=(int) kenar;

						startActivity(new Intent("android.intent.action.YeniOyun"));
					}
				});

				mGorunumBtn = (Button) findViewById(R.id.mGorunum);

				mGorunumBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						setContentView(R.layout.gorunuml);
						//Kare şekli için radio buton tanımlamaları
						rKare1 = (RadioButton)findViewById(R.id.rKare1);
						rKare2 = (RadioButton)findViewById(R.id.rKare2);
						rKare3 = (RadioButton)findViewById(R.id.rKare3);
						rKare4 = (RadioButton)findViewById(R.id.rKare4);

						//Mayın şekli için radio buton tanımlamaları
						rMayin1 = (RadioButton)findViewById(R.id.rMayin1);
						rMayin2 = (RadioButton)findViewById(R.id.rMayin2);
						rMayin3 = (RadioButton)findViewById(R.id.rMayin3);

						if(kare==1)
							rKare1.setChecked(true);
						else if(kare==2)
							rKare2.setChecked(true);
						else if(kare==3)
							rKare3.setChecked(true);
						else if(kare==4)
							rKare4.setChecked(true);


						if(mayinSekli==1)
							rMayin1.setChecked(true);
						else if(mayinSekli==2)
							rMayin2.setChecked(true);
						else if(mayinSekli==3)
							rMayin3.setChecked(true);

						gkaydetBtn = (Button) findViewById(R.id.gkaydetBtn);

						gkaydetBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {

								if(rKare1.isChecked())
									kare=1;
								else if(rKare2.isChecked())
									kare=2;
								else if(rKare3.isChecked())
									kare=3;
								else if(rKare4.isChecked())
									kare=4;




								if(rMayin1.isChecked())
									mayinSekli=1;
								else if(rMayin2.isChecked())
									mayinSekli=2;
								else if(rMayin3.isChecked())
									mayinSekli=3;

								Intent intent = new Intent(AnaMenu.this, AnaMenu.class);

								startActivity(intent);
							}
						});

						ggeriBtn = (Button) findViewById(R.id.ggeriBtn);

						ggeriBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
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

						soruIsaretiBox = (CheckBox)findViewById(R.id.soruIsaretiBox);
						sesCalBox = (CheckBox)findViewById(R.id.sesCalBox);
						if(sesCal)
							sesCalBox.setChecked(true);
						if(soruI)
							soruIsaretiBox.setChecked(true);
						mayinTBox = (EditText)findViewById(R.id.mayinTBox);
						genislikTextBox = (EditText)findViewById(R.id.genislikTextBox);
						yukseklikTextBox = (EditText)findViewById(R.id.yukseklikTextBox);
						mayinTBox.setEnabled(false);
						genislikTextBox.setEnabled(false);
						yukseklikTextBox.setEnabled(false);

						if(yukseklik==9&&genislik==9)
							radio1.setChecked(true);
						else if(yukseklik==16&&genislik==16)
							radio2.setChecked(true);
						else if(yukseklik==30&&genislik==16)
							radio3.setChecked(true);
						else
						{

							radio4.setChecked(true);
							mayinTBox.setText(Integer.toString(mayin));
							mayinTBox.setEnabled(true);
							genislikTextBox.setEnabled(true);
							genislikTextBox.setText(Integer.toString(genislik));
							yukseklikTextBox.setEnabled(true);
							yukseklikTextBox.setText(Integer.toString(yukseklik));
						}


						sgeriBtn = (Button) findViewById(R.id.sgeriBtn);

						sgeriBtn.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								Intent intent = new Intent(AnaMenu.this, AnaMenu.class);
								startActivity(intent);

							}
						});

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
									sesCal=sesCalBox.isChecked();
									soruI=soruIsaretiBox.isChecked();
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


	private String[] SELECT = {"kullanici", "sifre","dizilis","satir","sutun"};
	private Cursor KayitGetir() {
		SQLiteDatabase db = veri.getReadableDatabase();
		Cursor cursor = db.query("devamOyun", SELECT, null, null, null, null, null);

		startManagingCursor(cursor);
		return cursor;
	}

	private void KayitKontrol(Cursor cursor,String gKullanici,String gSifre) {

		boolean deger=true;
		String a="";
		int i=0;
		int j=0;
		//t.setEnabled(false);
		// t.setVisibility(View.INVISIBLE);
		while(cursor.moveToNext()){


			String kullanici = cursor.getString((cursor.getColumnIndex("kullanici")));
			String sifre = cursor.getString((cursor.getColumnIndex("sifre")));
			String dizilis = cursor.getString((cursor.getColumnIndex("dizilis")));
			int satirS = cursor.getInt((cursor.getColumnIndex("satir")));
			int sutunS = cursor.getInt((cursor.getColumnIndex("sutun")));
			a+=kullanici+" "+sifre+" "+"satir= "+"dizilis= "+dizilis;
			if(gKullanici.equals(kullanici)&&gSifre.equals(sifre))
			{
				tablos[i]=dizilis;
				satirDizi[i]=satirS;
				sutunDizi[i]=sutunS;
				i++;
				j++;
				if(i>5)
				{
					i=1;
				}


				deger=false;
				if(i==1)
					dOyun1.setEnabled(true);
				else if(i==2)
					dOyun2.setEnabled(true);
				else if(i==3)
					dOyun3.setEnabled(true);
				else if(i==4)
					dOyun4.setEnabled(true);
				else if(i==5)
					dOyun5.setEnabled(true);


			}
		}


	}

	private void devametFonksiyon(int j) {
		String b="";
		String gecici;
		int geciciI;
		int sayi=0;

		for(int x=0;x<satirDizi[j-1];x++)
		{
			for(int y=0;y<sutunDizi[j-1];y++)
			{
				tablo[x][y]=Integer.parseInt(Character.toString(tablos[j-1].charAt(sayi)));
				sayi++;
			}

		}

		yukseklik=satirDizi[j-1];
		genislik=sutunDizi[j-1];
		double kenar =Math.floor(((290-((genislik)*2))/genislik));
		kG=(int) kenar;
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
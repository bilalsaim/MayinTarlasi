package com.example.mayintarlasi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class VeriTabani extends SQLiteOpenHelper
{
	
	private static final String Veritabani_Adi = "Database";
	private static final int Veritabani_Version = 1;
	
	public VeriTabani(Context context) {
	super(context, Veritabani_Adi, null, Veritabani_Version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	db.execSQL("CREATE TABLE Devam(_id INTEGER primary key AUTOINCREMENT,"
	+ " kullanici String , sifre String, dizilis String);");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("DROP TABLE IF EXIST OperatorTablosu");
	onCreate(db);
	}
	
}

package com.bilisel.mayintarlasi.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class VeriTabani extends SQLiteOpenHelper
{
	
	private static final String Veritabani_Adi = "Verison";
	private static final int Veritabani_Version = 1;
	
	public VeriTabani(Context context) {
	super(context, Veritabani_Adi, null, Veritabani_Version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE devamOyun(_id INTEGER PRIMARY KEY AUTOINCREMENT,kullanici TEXT, sifre TEXT, dizilis TEXT, satir INTEGER,sutun INTEGER);");
		db.execSQL("CREATE TABLE kayitli(_id INTEGER PRIMARY KEY AUTOINCREMENT,isim TEXT, dizilis TEXT, satir INTEGER, sutun INTEGER);");
		db.execSQL("CREATE TABLE skor(_id INTEGER PRIMARY KEY AUTOINCREMENT,oyuns INTEGER, koyun INTEGER, ukazanma INTEGER, ukaybetme INTEGER,duzey INTEGER,enyuksek INTEGER);");

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("DROP TABLE IF EXIST devamOyun");
	db.execSQL("DROP TABLE IF EXIST skor");
	onCreate(db);
	}
	
}

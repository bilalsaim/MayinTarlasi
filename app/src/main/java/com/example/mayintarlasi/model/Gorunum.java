package com.example.mayintarlasi.model;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Gorunum {
    private static Gorunum singleton = new Gorunum();

    public SelectedList kareKapali;
    public SelectedList kareAcik;
    public SelectedList mayin;
    public SelectedList soruIsareti;
    public SelectedList bayrak;

    public SelectedList getKareKapali() {
        return kareKapali;
    }

    public void setKareKapali(TypedArray kareKapaliList) {
        this.kareKapali = new SelectedList(0, kareKapaliList);
    }

    public SelectedList getKareAcik() {
        return kareAcik;
    }

    public void setKareAcik(TypedArray kareAcikList) {
        this.kareAcik = new SelectedList(0, kareAcikList);
    }

    public SelectedList getMayin() {
        return mayin;
    }

    public void setMayin(TypedArray mayinList) {
        this.mayin = new SelectedList(0, mayinList);
    }

    public SelectedList getSoruIsareti() {
        return soruIsareti;
    }

    public void setSoruIsareti(TypedArray soruIsaretiList) {
        this.soruIsareti = new SelectedList(0, soruIsaretiList);
    }

    public SelectedList getBayrak() {
        return bayrak;
    }

    public void setBayrak(TypedArray bayrakList) {
        this.bayrak = new SelectedList(0, bayrakList);
    }

    public static Gorunum getSingleton() {
        return singleton;
    }
}

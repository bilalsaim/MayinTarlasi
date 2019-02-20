package com.bilisel.mayintarlasi.model;

public class Secenek {
    private static Secenek singleton = new Secenek();

    private Zorluk zorluk;
    private int x; //Genişlik
    private int y; //Yükseklik
    private int mayin;
    private boolean ses; //Ses çal
    private boolean soruIsareti;

    public Secenek() {
        setZorluk(Zorluk.ACEMI);
        this.ses = true;
        this.soruIsareti = true;
    }

    public Zorluk getZorluk() {
        return zorluk;
    }

    public void setZorluk(Zorluk zorluk) {
        this.zorluk = zorluk;
        this.x = zorluk.getX();
        this.y = zorluk.getY();
        this.mayin = zorluk.getMayin();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMayin() {
        return mayin;
    }

    public void setMayin(int mayin) {
        this.mayin = mayin;
    }

    public boolean isSes() {
        return ses;
    }

    public void setSes(boolean ses) {
        this.ses = ses;
    }

    public boolean isSoruIsareti() {
        return soruIsareti;
    }

    public void setSoruIsareti(boolean soruIsareti) {
        this.soruIsareti = soruIsareti;
    }

    public static Secenek getInstance()
    {
        return singleton;
    }
}

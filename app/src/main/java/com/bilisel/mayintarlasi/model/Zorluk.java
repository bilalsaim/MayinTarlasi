package com.bilisel.mayintarlasi.model;

public enum Zorluk {
    ACEMI(0, 9, 9, 10),
    DENEYIMLI(1, 16, 16, 40),
    UZMAN(2, 30, 16, 99),
    OZEL(3, 0, 0, 0);

    private int i;  //indeks
    private int x;  //genişlik
    private int y;  //yükseklik
    private int mayin; //mayın sayısı

    Zorluk(int i, int x, int y, int mayin) {
        this.i = i;
        this.x = x;
        this.y = y;
        this.mayin = mayin;
    }

    public static Zorluk bul(int i){
        for(Zorluk z : values()){
            if(z.getI() == i){
                return z;
            }
        }

        return ACEMI;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMayin() {
        return mayin;
    }

    public void setMayin(int mayin) {
        this.mayin = mayin;
    }
}

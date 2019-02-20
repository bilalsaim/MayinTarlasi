package com.example.mayintarlasi.model;

import org.apache.commons.lang3.text.StrBuilder;

public class Istatistik {
    int[] oyuns={0,0,0}, koyun={0,0,0}, ukazanma={0,0,0}, bkazanma={0,0,0}, ukaybetme={0,0,0}, bkaybetme={0,0,0}, duzey={1,2,3}, enyuksek={0,0,0};

    public String getText(int index){
        StrBuilder sb = new StrBuilder();
        sb.appendln("Oynanan oyun= "+ oyuns[index]);
        sb.appendln("Kazanılan oyun= "+ koyun[index]);
        sb.appendln("Kazanma yüzdesi= "+((int) oyuns[index]/100)* koyun[index]);
        sb.appendln("En uzun üst üste kazanma sayısı= "+ bkazanma[index]);
        sb.appendln("En uzun üst üste kaybetme sayısı= "+ bkaybetme[index]);
        sb.appendln("Geçerkli seri= "+( bkazanma[index]- bkaybetme[index]));
        sb.appendln("En yüksek puan = "+ enyuksek[index]);
        return sb.toString();
    }
}

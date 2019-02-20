package com.bilisel.mayintarlasi.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import com.bilisel.mayintarlasi.R;

import java.util.Arrays;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Kare extends Button
{
	private boolean isKapali;
	private boolean isMayin;
	private boolean isBayrak;
	private boolean isSoruIsareti;
	private boolean isTiklanabilir;
	private int komsuMayinSayisi;

	Gorunum gorunum;

	public Kare(Context context)
	{
		super(context);
		varsayilanAyarlar();
	}

	public Kare(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		varsayilanAyarlar();
	}

	public Kare(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		varsayilanAyarlar();
	}

	public void varsayilanAyarlar()
	{
		gorunum = Gorunum.getSingleton();
		isKapali = true;
		isMayin = false;
		isBayrak = false;
		isSoruIsareti = false;
		isTiklanabilir = true;
		komsuMayinSayisi = 0;

		setDisabledKare(true);
		setKalinYaziTipi();
	}

	public void komsuMayinSayisiniAyarla(int number)
	{
		this.setBackgroundResource(gorunum.getKareAcik().getSecilen());
		numaraGuncelle(number);
	}

	public void setMineIcon(boolean enabled)
	{
		setResim(new int[]{0xFFB8B6FF, 0xffffffff}, gorunum.getMayin().getSecilen());

		if (!enabled)
		{
			this.setTextColor(Color.RED);
		}
		else
		{
			this.setTextColor(Color.BLACK);
			this.setText("X");
		}
	}

	public void setBayrakSimgesi(boolean enabled)
	{
		setResim(new int[]{0xFFFF7171, 0xffffffff}, gorunum.getBayrak().getSecilen());

		if(!enabled)
		{
            this.setTextColor(Color.RED);
            this.setText("X");
        }
	}

    public void setSoruIsaretiSimgesi(boolean enabled)
	{
        setResim(new int[]{0xFFFFFD97, 0xffffffff}, gorunum.getSoruIsareti().getSecilen());

        if(!enabled)
        {
            this.setTextColor(Color.RED);
            this.setText("X");
        }
	}

    private void setResim(int[] colors, int resim) {
        LayerDrawable layerDrawable = (LayerDrawable) getResources()
                .getDrawable(R.drawable.karerenk);
        layerDrawable.mutate();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                .findDrawableByLayerId(R.id.karerenk);

        if(resim == 0) resim = R.drawable.bos;

        gradientDrawable.setColors(colors);
        Drawable degistir = (Drawable) getResources().getDrawable(resim);
        layerDrawable.setDrawableByLayerId(R.id.kareresim, degistir);
        layerDrawable.invalidateSelf();
        this.setBackground(layerDrawable);
    }

	public void setDisabledKare(boolean enabled)
	{
		int kareKapaliRenk = ContextCompat.getColor(getContext(), gorunum.getKareKapali().getSecilen());
		if(!enabled) this.setBackgroundResource(gorunum.getKareAcik().getSecilen());
		else setResim(new int[]{kareKapaliRenk, 0xffffffff}, 0);
	}

	public void tumSimgeleriTemizle()
	{
		this.setText("");
	}

	private void setKalinYaziTipi()
	{
		this.setTextSize(26);
		this.setTypeface(null, Typeface.BOLD);
	}

	public void kareAc()
	{
		if (!isKapali)
			return;

		setDisabledKare(false);
		isKapali = false;

		if (isMayin()) setMineIcon(false);
		else komsuMayinSayisiniAyarla(komsuMayinSayisi);
	}


	public void numaraGuncelle(int indeks)
	{
		if (indeks != 0)
		{
			this.setText(Integer.toString(indeks));
			this.setGravity(Gravity.CENTER);

			//TODO: Colors.xml e taşı
			List<Integer> metinRenkleri = Arrays.asList(Color.BLUE,
					Color.rgb(0, 100, 0),
					Color.RED,
					Color.rgb(85, 26, 139),
					Color.rgb(139, 28, 98),
					Color.rgb(238, 173, 14),
					Color.rgb(47, 79, 79),
					Color.rgb(71, 71, 71),
					Color.rgb(205, 205, 0)
			);

			this.setTextColor(metinRenkleri.get(indeks-1));
		}
	}


	public void mayinEkle()
	{
		isMayin = true;
	}

	public void mayinSil()
	{
		isMayin = false;
	}

	public void triggerMine()
	{
		setMineIcon(true);
		this.setTextColor(Color.RED);
	}

	public boolean isKapali()
	{
		return isKapali;
	}

	public void setKapali(boolean cover)
	{
		isKapali = cover;
	}

	public boolean isMayin()
	{
		return isMayin;
	}


	public void setKomsuMayinSayisi(int number)
	{
		komsuMayinSayisi = number;
	}


	public int getKomsuMayinSayisi()
	{
		return komsuMayinSayisi;
	}


	public boolean isBayrak()
	{
		return isBayrak;
	}


	public void setBayrak(boolean flagged)
	{
		isBayrak = flagged;
	}


	public boolean isSoruIsareti()
	{
		return isSoruIsareti;
	}


	public void setSoruIsareti(boolean questionMarked)
	{
		isSoruIsareti = questionMarked;
	}


	public boolean isTiklanabilir()
	{
		return isTiklanabilir;
	}


	public void setClickable(boolean clickable)
	{
		isTiklanabilir = clickable;
	}
}
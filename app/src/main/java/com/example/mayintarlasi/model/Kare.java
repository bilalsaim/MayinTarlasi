package com.example.mayintarlasi.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

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
		this.setBackgroundResource(gorunum.getMayin().getSecilen());

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
		this.setBackgroundResource(gorunum.getBayrak().getSecilen());
		this.setTextColor((enabled)?Color.BLACK:Color.RED);
	}

	public void setSoruIsaretiSimgesi(boolean enabled)
	{
		this.setBackgroundResource(gorunum.getSoruIsareti().getSecilen());
		this.setTextColor((enabled)?Color.BLACK:Color.RED);
	}

	public void setDisabledKare(boolean enabled)
	{
		this.setBackgroundResource((!enabled)?gorunum.getKareAcik().getSecilen():gorunum.getKareKapali().getSecilen());
	}

	public void tumSimgeleriTemizle()
	{
		this.setText("");
	}

	private void setKalinYaziTipi()
	{
		this.setTypeface(null, Typeface.BOLD);
	}

	public void kareAc()
	{
		if (!isKapali)
			return;

		setDisabledKare(false);
		isKapali = false;

		if (isMayin())
		{
			setMineIcon(false);
		}
		else
		{
			komsuMayinSayisiniAyarla(komsuMayinSayisi);
		}
	}


	public void numaraGuncelle(int indeks)
	{
		if (indeks != 0)
		{
			this.setText(Integer.toString(indeks));
			this.setGravity(Gravity.CENTER);

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
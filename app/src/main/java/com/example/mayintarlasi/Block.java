package com.example.mayintarlasi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class Block extends Button
{
	private boolean isCovered;
	private boolean isMined;
	private boolean isFlagged;
	private boolean isQuestionMarked;
	private boolean isClickable;
	private int numberOfMinesInSurrounding;


	public Block(Context context)
	{
		super(context);
	}

	public Block(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public Block(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	public void setDefaults()
	{
		isCovered = true;
		isMined = false;
		isFlagged = false;
		isQuestionMarked = false;
		isClickable = true;
		numberOfMinesInSurrounding = 0;

		if(AnaMenu.kare==1)
			this.setBackgroundResource(R.drawable.kare1);
		if(AnaMenu.kare==2)
			this.setBackgroundResource(R.drawable.kare2);
		if(AnaMenu.kare==3)
			this.setBackgroundResource(R.drawable.kare3);
		if(AnaMenu.kare==4)
			this.setBackgroundResource(R.drawable.kare4);
		if(AnaMenu.kare==5)
			this.setBackgroundResource(R.drawable.kare5);
		setBoldFont();
	}


	public void setNumberOfSurroundingMines(int number)
	{
		this.setBackgroundResource(R.drawable.kares);

		updateNumber(number);
	}


	public void setMineIcon(boolean enabled)
	{


		if (!enabled)
		{
			if(AnaMenu.mayinSekli==1)
				this.setBackgroundResource(R.drawable.mayinsekli1);
			else if(AnaMenu.mayinSekli==2)
				this.setBackgroundResource(R.drawable.mayinsekli2);
			else if(AnaMenu.mayinSekli==3)
				this.setBackgroundResource(R.drawable.mayinsekli3);

			this.setTextColor(Color.RED);

		}
		else
		{
			if(AnaMenu.mayinSekli==1)
				this.setBackgroundResource(R.drawable.mayinsekli1);
			else if(AnaMenu.mayinSekli==2)
				this.setBackgroundResource(R.drawable.mayinsekli2);
			else if(AnaMenu.mayinSekli==3)
				this.setBackgroundResource(R.drawable.mayinsekli3);

			this.setTextColor(Color.BLACK);
			this.setText("X");
		}
	}


	public void setFlagIcon(boolean enabled)
	{


		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.flag);
			this.setTextColor(Color.RED);
		}
		else
		{
			this.setBackgroundResource(R.drawable.flag);
			this.setTextColor(Color.BLACK);
		}
	}


	public void setQuestionMarkIcon(boolean enabled)
	{

		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.soruisareti);
			this.setTextColor(Color.RED);
		}
		else
		{
			this.setBackgroundResource(R.drawable.soruisareti);
			this.setTextColor(Color.BLACK);
		}
	}


	public void setBlockAsDisabled(boolean enabled)
	{
		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.kares);
		}
		else
		{
			if(AnaMenu.kare==1)
				this.setBackgroundResource(R.drawable.kare1);
			if(AnaMenu.kare==2)
				this.setBackgroundResource(R.drawable.kare2);
			if(AnaMenu.kare==3)
				this.setBackgroundResource(R.drawable.kare3);
			if(AnaMenu.kare==4)
				this.setBackgroundResource(R.drawable.kare4);
			if(AnaMenu.kare==5)
				this.setBackgroundResource(R.drawable.kare5);
		}
	}


	public void clearAllIcons()
	{
		this.setText("");
	}


	private void setBoldFont()
	{
		this.setTypeface(null, Typeface.BOLD);
	}


	public void OpenBlock()
	{

		if (!isCovered)
			return;

		setBlockAsDisabled(false);
		isCovered = false;


		if (hasMine())
		{
			setMineIcon(false);
		}

		else
		{
			if(AnaMenu.sesCal)

				setNumberOfSurroundingMines(numberOfMinesInSurrounding);
		}
	}


	public void updateNumber(int text)
	{
		if (text != 0)
		{
			this.setText(Integer.toString(text));

			this.setTextSize(AnaMenu.kG/2);
			this.setGravity(Gravity.CENTER);

			switch (text)
			{
				case 1:
					this.setTextColor(Color.BLUE);
					break;
				case 2:
					this.setTextColor(Color.rgb(0, 100, 0));
					break;
				case 3:
					this.setTextColor(Color.RED);
					break;
				case 4:
					this.setTextColor(Color.rgb(85, 26, 139));
					break;
				case 5:
					this.setTextColor(Color.rgb(139, 28, 98));
					break;
				case 6:
					this.setTextColor(Color.rgb(238, 173, 14));
					break;
				case 7:
					this.setTextColor(Color.rgb(47, 79, 79));
					break;
				case 8:
					this.setTextColor(Color.rgb(71, 71, 71));
					break;
				case 9:
					this.setTextColor(Color.rgb(205, 205, 0));
					break;
			}
		}
	}


	public void plantMine()
	{
		isMined = true;
	}

	public void deleteMine()
	{
		isMined = false;
	}

	public void triggerMine()
	{
		setMineIcon(true);
		this.setTextColor(Color.RED);
	}


	public boolean isCovered()
	{
		return isCovered;
	}

	public void setCovered(boolean cover)
	{
		isCovered = cover;
	}

	public boolean hasMine()
	{
		return isMined;
	}


	public void setNumberOfMinesInSurrounding(int number)
	{
		numberOfMinesInSurrounding = number;
	}


	public int getNumberOfMinesInSorrounding()
	{
		return numberOfMinesInSurrounding;
	}


	public boolean isFlagged()
	{
		return isFlagged;
	}


	public void setFlagged(boolean flagged)
	{
		isFlagged = flagged;
	}


	public boolean isQuestionMarked()
	{
		return isQuestionMarked;
	}


	public void setQuestionMarked(boolean questionMarked)
	{
		isQuestionMarked = questionMarked;
	}


	public boolean isClickable()
	{
		return isClickable;
	}


	public void setClickable(boolean clickable)
	{
		isClickable = clickable;
	}
}
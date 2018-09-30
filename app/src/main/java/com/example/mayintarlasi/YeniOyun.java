package com.example.mayintarlasi;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import pl.polidea.view.ZoomView;

public class YeniOyun extends FragmentActivity {

    private ZoomView zoomView;
	private TextView txtMineCount;
	private TextView txtTimer;
	private ImageButton smileBtn;

	private TableLayout mineField; // table layout to add mines to

	private Block blocks[][]; // blocks for mine field	
	private int blockBoyutu = AnaMenu.kG; // width of each block
	private int blockBoslugu = 2; // padding between blocks
	private VeriTabani veri;
	private int satirSayisi = AnaMenu.yukseklik;
	private int sutunSayisi = AnaMenu.genislik;
	private int totalNumberOfMines = AnaMenu.mayin;

	// timer to keep track of time elapsed
	private Handler timer = new Handler();
	private int secondsPassed = 0;

	private boolean isZamanKontrol; // check if timer already started or not
	private boolean areMinesSet; // check if mines are planted in blocks
	private boolean isOyunBitti;
	private int minesToFind; // number of mines yet to be discovered

	
	private Button yCikisBtn;
	private Button dkKaydetBtn;
	private EditText dkKullaniciEditText;
	private EditText dkSifreEditText;
	
	boolean kontrol=false;
	String kod="";
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("ClickableViewAccessibility")
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.yenioyunl);


		veri=new VeriTabani(this);
		
		yCikisBtn = (Button) findViewById(R.id.yCikisBtn);
		
		kontrol=false;
		
		txtMineCount = (TextView) findViewById(R.id.MineCount);
		txtTimer = (TextView) findViewById(R.id.Timer);
		
		// set font style for timer and mine count to LCD style
		
		showDialog("Oyunu başlatmak için surata tıklatınız!", 5000, true, false);
		
		smileBtn = (ImageButton) findViewById(R.id.Smiley);
		smileBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				endExistingGame();
				startNewGame();
				kontrol=true;
			}
		});


		yCikisBtn.setOnClickListener(new OnClickListener() {
	      public void onClick(View view) {
          if(kontrol)
          {
            oyunSablonu();
            setContentView(R.layout.devamkaydetl);
            dkKullaniciEditText = (EditText)findViewById(R.id.dkKullaniciEditText);
            dkSifreEditText =(EditText) findViewById(R.id.dkSifreEditText);
            dkKullaniciEditText.setText(kod);
            dkKaydetBtn = (Button) findViewById(R.id.dkKaydetBtn);

            dkKaydetBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                if(dkKullaniciEditText.getText().toString()!=""||dkKullaniciEditText.getText().toString()==null
                    || dkSifreEditText.getText().toString()!="" ||dkSifreEditText.getText().toString()==null  )
                {

                    kayitEkleme(dkKullaniciEditText.getText().toString(),dkSifreEditText.getText().toString(),"ddd");
                    veri.close();

                    Intent intent = new Intent(getBaseContext(), AnaMenu.class);
                    startActivity(intent);
                }
                else
                {
                    showDialog("Kullanıcı Adı ve Şifre Boş Bırakılamaz!", 2000, true, false);
                }

              }
            });

          }
          else
          {
            Intent intent = new Intent(getBaseContext(), AnaMenu.class);
            startActivity(intent);
          }
	    	  
	  	      
	  	    
	      }
	    });


        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mayinalani, null, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));


        mineField = (TableLayout) v.findViewById(R.id.MineField);

        zoomView = new ZoomView(this);
        zoomView.addView(v);

        RelativeLayout main_container = (RelativeLayout) findViewById(R.id.MineField2);
        main_container.addView(zoomView);

    }

	private void oyunSablonu()
	{

		for(int i=1;i<satirSayisi+ 1;i++)
		{
			for(int j=1;j<sutunSayisi+ 1;j++)
			{
				if(!blocks[i][j].hasMine()&&!blocks[i][j].isFlagged()&&!blocks[i][j].isQuestionMarked()&&!blocks[i][j].isCovered())
				{
					kod+="0";
				}
				else if(!blocks[i][j].hasMine()&&!blocks[i][j].isFlagged()&&!blocks[i][j].isQuestionMarked()&&blocks[i][j].isCovered())
				{
					kod+="1";
				}
				else if(blocks[i][j].hasMine()&&!blocks[i][j].isFlagged()&&!blocks[i][j].isQuestionMarked()&&blocks[i][j].isCovered())
				{
					kod+="2";
				}
				else if(!blocks[i][j].hasMine()&&blocks[i][j].isFlagged()&&!blocks[i][j].isQuestionMarked())
				{
					kod+="3";
				}
				else if(blocks[i][j].hasMine()&&blocks[i][j].isFlagged()&&!blocks[i][j].isQuestionMarked())
				{
					kod+="4";
				}
				else if(!blocks[i][j].hasMine()&&!blocks[i][j].isFlagged()&&blocks[i][j].isQuestionMarked())
				{
					kod+="5";
				}
				else if(blocks[i][j].hasMine()&&!blocks[i][j].isFlagged()&&blocks[i][j].isQuestionMarked())
				{
					kod+="6";
				}
			}

		}

	}

	private void kayitEkleme(String a,String b,String c){
		SQLiteDatabase db = veri.getWritableDatabase();
		ContentValues veriler = new ContentValues();
		veriler.put("kullanici", a);
		veriler.put("sifre", b);
		veriler.put("dizilis", c);
		db.insertOrThrow("Devam", null, veriler);
	}

	private void startNewGame()
	{
		// plant mines and do rest of the calculations
		createMineField();
		// display all blocks in UI
		showMineField();

		minesToFind = totalNumberOfMines;
		isOyunBitti = false;
		secondsPassed = 0;
	}

	private void showMineField()
	{
		// remember we will not show 0th and last Row and Columns
		// they are used for calculation purposes only
		for (int row = 1; row < satirSayisi + 1; row++)
		{
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams(blockBoyutu + 2 * blockBoslugu, blockBoyutu + 2 * blockBoslugu*10));

			for (int column = 1; column < sutunSayisi + 1; column++)
			{
				blocks[row][column].setLayoutParams(new LayoutParams(
						blockBoyutu + 2 * blockBoslugu,
						blockBoyutu + 2 * blockBoslugu*10));
				blocks[row][column].setPadding(blockBoslugu, blockBoslugu, blockBoslugu, blockBoslugu);
				tableRow.addView(blocks[row][column]);
			}
			mineField.addView(tableRow,new TableLayout.LayoutParams(
					(blockBoyutu + 2 * blockBoslugu), blockBoyutu + 2 * blockBoslugu*10));
		}
	}

	private void endExistingGame()
	{
		stopTimer(); // stop if timer is running
		txtTimer.setText("000"); // revert all text
		txtMineCount.setText("000"); // revert mines count
		smileBtn.setBackgroundResource(R.drawable.smile);

		// remove all rows from mineField TableLayout
		mineField.removeAllViews();

		// set all variables to support end of game
		isZamanKontrol = false;
		areMinesSet = false;
		isOyunBitti = false;
		minesToFind = 0;
	}

	private void createMineField()
	{
		// we take one row extra row for each side
		// overall two extra rows and two extra columns
		// first and last row/column are used for calculations purposes only
		//	 x|xxxxxxxxxxxxxx|x
		//	 ------------------
		//	 x|              |x
		//	 x|              |x
		//	 ------------------
		//	 x|xxxxxxxxxxxxxx|x
		// the row and columns marked as x are just used to keep counts of near by mines

		blocks = new Block[satirSayisi + 2][sutunSayisi + 2];

		for (int row = 0; row < satirSayisi + 2; row++)
		{
			for (int column = 0; column < sutunSayisi + 2; column++)
			{
				blocks[row][column] = new Block(this);
				blocks[row][column].setDefaults();

				// pass current row and column number as final int's to event listeners
				// this way we can ensure that each event listener is associated to
				// particular instance of block only
				final int currentRow = row;
				final int currentColumn = column;

				// add Click Listener
				// this is treated as Left Mouse click
				blocks[row][column].setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						// start timer on first click
						if (!isZamanKontrol)
						{
							startTimer();
							isZamanKontrol = true;
						}

						// set mines on first click
						if (!areMinesSet)
						{
							areMinesSet = true;
							setMines(currentRow, currentColumn);
						}

						// this is not first click
						// check if current block is flagged
						// if flagged the don't do anything
						// as that operation is handled by LongClick
						// if block is not flagged then uncover nearby blocks
						// till we get numbered mines
						if (!blocks[currentRow][currentColumn].isFlagged())
						{
							// open nearby blocks till we get numbered blocks
							rippleUncover(currentRow, currentColumn);

							// did we clicked a mine
							if (blocks[currentRow][currentColumn].hasMine())
							{
								// Oops, game over
								finishGame(currentRow,currentColumn);
							}

							// check if we win the game
							if (checkGameWin())
							{
								// mark game as win
								winGame();
							}
						}
					}
				});

				// add Long Click listener
				// this is treated as right mouse click listener
				blocks[row][column].setOnLongClickListener(new OnLongClickListener()
				{
					public boolean onLongClick(View view)
					{
						// simulate a left-right (middle) click
						// if it is a long click on an opened mine then
						// open all surrounding blocks
						if (!blocks[currentRow][currentColumn].isCovered() && (blocks[currentRow][currentColumn].getNumberOfMinesInSorrounding() > 0) && !isOyunBitti)
						{
							int nearbyFlaggedBlocks = 0;
							for (int previousRow = -1; previousRow < 2; previousRow++)
							{
								for (int previousColumn = -1; previousColumn < 2; previousColumn++)
								{
									if (blocks[currentRow + previousRow][currentColumn + previousColumn].isFlagged())
									{
										nearbyFlaggedBlocks++;
									}
								}
							}

							// if flagged block count is equal to nearby mine count
							// then open nearby blocks
							if (nearbyFlaggedBlocks == blocks[currentRow][currentColumn].getNumberOfMinesInSorrounding())
							{
								for (int previousRow = -1; previousRow < 2; previousRow++)
								{
									for (int previousColumn = -1; previousColumn < 2; previousColumn++)
									{
										// don't open flagged blocks
										if (!blocks[currentRow + previousRow][currentColumn + previousColumn].isFlagged())
										{
											// open blocks till we get numbered block
											rippleUncover(currentRow + previousRow, currentColumn + previousColumn);

											// did we clicked a mine
											if (blocks[currentRow + previousRow][currentColumn + previousColumn].hasMine())
											{
												// oops game over
												finishGame(currentRow + previousRow, currentColumn + previousColumn);
											}

											// did we win the game
											if (checkGameWin())
											{
												// mark game as win
												winGame();
											}
										}
									}
								}
							}

							// as we no longer want to judge this gesture so return
							// not returning from here will actually trigger other action
							// which can be marking as a flag or question mark or blank
							return true;
						}

						// if clicked block is enabled, clickable or flagged
						if (blocks[currentRow][currentColumn].isClickable() &&
								(blocks[currentRow][currentColumn].isEnabled() || blocks[currentRow][currentColumn].isFlagged()))
						{

							// for long clicks set:
							// 1. empty blocks to flagged
							// 2. flagged to question mark
							// 3. question mark to blank

							// case 1. set blank block to flagged
							if (!blocks[currentRow][currentColumn].isFlagged() && !blocks[currentRow][currentColumn].isQuestionMarked())
							{
								blocks[currentRow][currentColumn].setBlockAsDisabled(false);
								blocks[currentRow][currentColumn].setFlagIcon(true);
								blocks[currentRow][currentColumn].setFlagged(true);
								minesToFind--; //reduce mine count
								updateMineCountDisplay();
							}
							// case 2. set flagged to question mark
							else if (!blocks[currentRow][currentColumn].isQuestionMarked())
							{
								blocks[currentRow][currentColumn].setBlockAsDisabled(true);
								blocks[currentRow][currentColumn].setQuestionMarkIcon(true);
								blocks[currentRow][currentColumn].setFlagged(false);
								blocks[currentRow][currentColumn].setQuestionMarked(true);
								minesToFind++; // increase mine count
								updateMineCountDisplay();
							}
							// case 3. change to blank square
							else
							{
								blocks[currentRow][currentColumn].setBlockAsDisabled(true);
								blocks[currentRow][currentColumn].clearAllIcons();
								blocks[currentRow][currentColumn].setQuestionMarked(false);
								// if it is flagged then increment mine count
								if (blocks[currentRow][currentColumn].isFlagged())
								{
									minesToFind++; // increase mine count
									updateMineCountDisplay();
								}
								// remove flagged status
								blocks[currentRow][currentColumn].setFlagged(false);
							}

							updateMineCountDisplay(); // update mine display
						}

						return true;
					}
				});
			}
		}
	}

	private boolean checkGameWin()
	{
		for (int row = 1; row < satirSayisi + 1; row++)
		{
			for (int column = 1; column < sutunSayisi + 1; column++)
			{
				if (!blocks[row][column].hasMine() && blocks[row][column].isCovered())
				{
					return false;
				}
			}
		}
		return true;
	}

	private void updateMineCountDisplay()
	{
		if (minesToFind < 0)
		{
			txtMineCount.setText(Integer.toString(minesToFind));
		}
		else if (minesToFind < 10)
		{
			txtMineCount.setText("00" + Integer.toString(minesToFind));
		}
		else if (minesToFind < 100)
		{
			txtMineCount.setText("0" + Integer.toString(minesToFind));
		}
		else
		{
			txtMineCount.setText(Integer.toString(minesToFind));
		}
	}

	private void winGame()
	{
		kontrol=false;
		stopTimer();
		isZamanKontrol = false;
		isOyunBitti = true;
		minesToFind = 0; //set mine count to 0

		//set icon to cool dude
		smileBtn.setBackgroundResource(R.drawable.cool);

		updateMineCountDisplay(); // update mine count

		// disable all buttons
		// set flagged all un-flagged blocks
		for (int row = 1; row < satirSayisi + 1; row++)
		{
			for (int column = 1; column < sutunSayisi + 1; column++)
			{
				blocks[row][column].setClickable(false);
				if (blocks[row][column].hasMine())
				{
					blocks[row][column].setBlockAsDisabled(false);
					blocks[row][column].setFlagIcon(true);
				}
			}
		}

		// show message
		showDialog("Kazandın " + Integer.toString(secondsPassed) + " saniyede!", 1000, false, true);
	}

	private void finishGame(int currentRow, int currentColumn)
	{
		kontrol=false;
		isOyunBitti = true; // mark game as over
		stopTimer(); // stop timer
		isZamanKontrol = false;
		smileBtn.setBackgroundResource(R.drawable.sad);

		// show all mines
		// disable all blocks
		for (int row = 1; row < satirSayisi + 1; row++)
		{
			for (int column = 1; column < sutunSayisi + 1; column++)
			{
				// disable block
				blocks[row][column].setBlockAsDisabled(false);

				// block has mine and is not flagged
				if (blocks[row][column].hasMine() && !blocks[row][column].isFlagged())
				{
					// set mine icon
					blocks[row][column].setMineIcon(false);
				}

				// block is flagged and doesn't not have mine
				if (!blocks[row][column].hasMine() && blocks[row][column].isFlagged())
				{
					// set flag icon
					blocks[row][column].setFlagIcon(false);
				}

				// block is flagged
				if (blocks[row][column].isFlagged())
				{
					// disable the block
					blocks[row][column].setClickable(false);
				}
			}
		}

		// trigger mine
		blocks[currentRow][currentColumn].triggerMine();

		// show message
		showDialog("Tekrar Dene " + Integer.toString(secondsPassed) + " seconds!", 1000, false, false);
	}


	private void setMines(int currentRow, int currentColumn)
	{
		// set mines excluding the location where user clicked
		Random rand = new Random();
		int mineRow, mineColumn;

		for (int row = 0; row < totalNumberOfMines; row++)
		{
			mineRow = rand.nextInt(sutunSayisi);
			mineColumn = rand.nextInt(satirSayisi);
			if ((mineRow + 1 != currentColumn) || (mineColumn + 1 != currentRow))
			{
				if (blocks[mineColumn + 1][mineRow + 1].hasMine())
				{
					row--; // mine is already there, don't repeat for same block
				}
				// plant mine at this location
				blocks[mineColumn + 1][mineRow + 1].plantMine();
			}
			// exclude the user clicked location
			else
			{
				row--;
			}
		}

		int nearByMineCount;

		// count number of mines in surrounding blocks
		for (int row = 0; row < satirSayisi + 2; row++)
		{
			for (int column = 0; column < sutunSayisi + 2; column++)
			{
				// for each block find nearby mine count
				nearByMineCount = 0;
				if ((row != 0) && (row != (satirSayisi + 1)) && (column != 0) && (column != (sutunSayisi + 1)))
				{
					// check in all nearby blocks
					for (int previousRow = -1; previousRow < 2; previousRow++)
					{
						for (int previousColumn = -1; previousColumn < 2; previousColumn++)
						{
							if (blocks[row + previousRow][column + previousColumn].hasMine())
							{
								// a mine was found so increment the counter
								nearByMineCount++;
							}
						}
					}

					blocks[row][column].setNumberOfMinesInSurrounding(nearByMineCount);
				}
				// for side rows (0th and last row/column)
				// set count as 9 and mark it as opened
				else
				{
					blocks[row][column].setNumberOfMinesInSurrounding(9);
					blocks[row][column].OpenBlock();
				}
			}
		}
	}

	private void rippleUncover(int rowClicked, int columnClicked)
	{
		// don't open flagged or mined rows
		if (blocks[rowClicked][columnClicked].hasMine() || blocks[rowClicked][columnClicked].isFlagged())
		{
			return;
		}

		// open clicked block
		blocks[rowClicked][columnClicked].OpenBlock();

		// if clicked block have nearby mines then don't open further
		if (blocks[rowClicked][columnClicked].getNumberOfMinesInSorrounding() != 0 )
		{
			return;
		}

		// open next 3 rows and 3 columns recursively
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				// check all the above checked conditions
				// if met then open subsequent blocks
				if (blocks[rowClicked + row - 1][columnClicked + column - 1].isCovered()
						&& (rowClicked + row - 1 > 0) && (columnClicked + column - 1 > 0)
						&& (rowClicked + row - 1 < satirSayisi + 1) && (columnClicked + column - 1 < sutunSayisi + 1))
				{
					rippleUncover(rowClicked + row - 1, columnClicked + column - 1 );
				}
			}
		}
		return;
	}

	public void startTimer()
	{
		if (secondsPassed == 0)
		{
			timer.removeCallbacks(updateTimeElasped);
			// tell timer to run call back after 1 second
			timer.postDelayed(updateTimeElasped, 1000);
		}
	}

	public void stopTimer()
	{
		// disable call backs
		timer.removeCallbacks(updateTimeElasped);
	}

	// timer call back when timer is ticked
	private Runnable updateTimeElasped = new Runnable()
	{
		public void run()
		{
			long currentMilliseconds = System.currentTimeMillis();
			++secondsPassed;

			if (secondsPassed < 10)
			{
				txtTimer.setText("00" + Integer.toString(secondsPassed));
			}
			else if (secondsPassed < 100)
			{
				txtTimer.setText("0" + Integer.toString(secondsPassed));
			}
			else
			{
				txtTimer.setText(Integer.toString(secondsPassed));
			}

			// add notification
			timer.postAtTime(this, currentMilliseconds);
			// notify to call back after 1 seconds
			// basically to remain in the timer loop
			timer.postDelayed(updateTimeElasped, 1000);
		}
	};

	private void showDialog(String message, int milliseconds, boolean useSmileImage, boolean useCoolImage)
	{
		// show message
		Toast dialog = Toast.makeText(
				getApplicationContext(),
				message,
				Toast.LENGTH_LONG);

		dialog.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout dialogView = (LinearLayout) dialog.getView();
		ImageView coolImage = new ImageView(getApplicationContext());
		if (useSmileImage)
		{
			coolImage.setImageResource(R.drawable.smile);
		}
		else if (useCoolImage)
		{
			coolImage.setImageResource(R.drawable.cool);
		}
		else
		{
			coolImage.setImageResource(R.drawable.sad);
		}
		dialogView.addView(coolImage, 0);
		dialog.setDuration(milliseconds);
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yeni_oyun, menu);
		return true;
	}

}

package com.mobiledevelopent.gurcharnsinghsikka.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;

/**
 * Class to create custom view of game board
 *
 * @author Gurcharn Singh Sikka
 * @version 1.0
 */
public class CustomView extends View {
    private final int clicksLimit = 2;
    private int noOfColumns, noOfRows;
    private int cardWidth, cardHeight;
    private PlayerManager playerManager;
    private Paint freezeCardPaint;
    private Paint linePaint;
    private Card[][] cards;
    private Card[] cardsClicked;
    private int[] colors;

    /**
     * Constructor to be used to create instance of this class and to initialize view in activity
     */
    public CustomView(Context context) {
        this(context, null);
        init();
    }

    /**
     * Constructor to be used to create instance of this class and to initialize view in activity
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor to be used to create instance of this class and to initialize view in activity
     */
    public CustomView(Context c, AttributeSet as, int default_style){
        super(c, as, default_style);
        init();
    }

    /**
     * Method to initialize all the variable, objects, array etc declared in this class
     */
    private void init() {
        colors = new int[] {Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA};
        cardsClicked = new Card[clicksLimit];
        playerManager = new PlayerManager();
        freezeCardPaint = new Paint();
        linePaint = new Paint();
        freezeCardPaint.setColor(Color.GRAY);
        linePaint.setColor(Color.BLACK);
        setNoOfColumns(4);
        setNoOfRows(4);
        setGameCards();
    }

    /**
     * Method to set the number of column in game board
     * @param noOfColumns
     */
    private void setNoOfColumns(int noOfColumns) {
        this.noOfColumns = noOfColumns;
    }

    /**
     *  Method to set number of rows in the game board
     *  @param noOfRows
     */
    private void setNoOfRows(int noOfRows) {
        this.noOfRows = noOfRows;
    }

    /**
     *  Method to set cards sizes and initializing cards array
     */
    private void setGameCards() {
        if (noOfColumns < 1 || noOfRows < 1) {
            setNoOfColumns(4);
            setNoOfRows(4);
        }

        cardWidth = getWidth() / noOfColumns;
        cardHeight = getWidth() / noOfRows;

        cards = new Card[noOfColumns][noOfRows];

        initCards();
        invalidate();
    }

    /**
     *  Method to initialize new card in each array index of cards
     */
    private void initCards() {
        for(int i = 0; i< noOfColumns; i++) {
            for (int j = 0; j < noOfRows; j++) {
                cards[i][j] = new Card(new Paint(), CardState.FACE_DOWN);
            }
        }
        setRandomColors();
    }

    /**
     *  Method to set random colors of all card in array
     */
    private void setRandomColors() {
        for(int i = 0; i< noOfColumns; i++) {
            for (int j = 0; j< noOfRows; j++) {
                if(cards[i][j].getCardState() != CardState.MATCHED){
                    int randomNumber = (int) (Math.random()*4);
                    cards[i][j].setColor(colors[randomNumber]);
                }
            }
        }
    }

    /**
     *  Method to update score board in real-time while playing game
     */
    public void updateScoreBoard() {
        Player player1 = playerManager.getPlayer(1);
        Player player2 = playerManager.getPlayer(2);
        MainActivity mainActivity = (MainActivity) this.getContext();

        mainActivity.updateScoreBoard(player1, player2);
    }

    /**
     *  Method called when size of the screen will be changed to set new dimensions of cards in view
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setGameCards();
    }

    /**
     *  Method to draw view of board, card and colors
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (noOfColumns == 0 || noOfRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getWidth();

        for (int i = 0; i < noOfColumns; i++) {
            for (int j = 0; j < noOfRows; j++) {
                if (cards[i][j].getCardState() == CardState.FACE_UP) {
                    canvas.drawRect(i * cardWidth, j * cardHeight,
                            (i + 1) * cardWidth, (j + 1) * cardHeight,
                            cards[i][j].getColor());
                }
                else if (cards[i][j].getCardState() == CardState.MATCHED) {
                    canvas.drawRect(i * cardWidth, j * cardHeight,
                            (i + 1) * cardWidth, (j + 1) * cardHeight,
                            freezeCardPaint);
                }
            }
        }

        for (int i = 1; i < noOfColumns; i++) {
            canvas.drawLine(i * cardWidth, 0, i * cardWidth, height, linePaint);
        }

        for (int i = 1; i <= noOfRows; i++) {
            canvas.drawLine(0, i * cardHeight, width, i * cardHeight, linePaint);
        }
    }

    /**
     * Method to handle touch events on game board and apply logic and rules accordingly
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            final int column = (int)(event.getX() / cardWidth);
            final int row = (int)(event.getY() / cardHeight);

            if(column<4 && row <4) {
                if(cards[column][row].getCardState() == CardState.FACE_DOWN && !(playerManager.getPlayerClicks()<=0)){
                    cards[column][row].flipCard();
                    invalidate();

                    playerManager.reduceClicks();
                    updateScoreBoard();

                    if(playerManager.getPlayerClicks() == 0){
                        cardsClicked[0] = cards[column][row];

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkCardMatch(cardsClicked[0], cardsClicked[1]);
                                updateScoreBoard();
                                invalidate();
                            }
                        }, 1000);
                    } else{
                        cardsClicked[1] = cards[column][row];
                        updateScoreBoard();
                        invalidate();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Touch", Toast.LENGTH_LONG).show();
                }
            }
        }

        return true;
    }

    /**
     *  Method to check if 2 clicked cards matched or not
     *  and update score board and turns accordingly
     */
    private void checkCardMatch(Card card1, Card card2) {
        if(card1.getColor().getColor() == card2.getColor().getColor()){
            card1.setCardState(CardState.MATCHED);
            card2.setCardState(CardState.MATCHED);

            playerManager.incrementScore();
        } else{
            card1.flipCard();
            card2.flipCard();
        }
        cardsClicked = new Card[2];
        switchPlayer();
        setRandomColors();
        checkGameOver();
    }

    /**
     *  Method to switch player turn and update it in live comments
     */
    private void switchPlayer() {
        Player player;
        playerManager.switchPlayer();

        if(playerManager.getCurrentPlayer() == 1)
            player = playerManager.getPlayer(1);
        else if(playerManager.getCurrentPlayer() == 2)
            player = playerManager.getPlayer(2);
        else
            player = new Player("No Player exists");

        updateLiveComment("Turn : " + player.getName());
    }

    /**
     *  Method to check if the game is finished or not
     *  and declare winner when finished
     */
    private void checkGameOver() {
        boolean isGameOver = true;
        for(int i = 0; i< noOfColumns; i++)
            for(int j = 0; i< noOfRows; i++)
                if(cards[i][j].getCardState() != CardState.MATCHED){
                    isGameOver = false;
                    break;
                }
        if(isGameOver){
            endGame();
        }
    }

    /**
     *  Method to start a new game and re-initialize all variables and cards
     */
    public void newGame() {
        init();
        updateScoreBoard();
        Toast.makeText(getContext(), "New Game started", Toast.LENGTH_LONG).show();
    }

    /**
     *  Method to finish / quit game game and declare winner by opening dialog box
     */
    public void endGame() {
        Player player = playerManager.getWinner();
        MainActivity mainActivity = (MainActivity) this.getContext();
        updateLiveComment("Winner : " + player.getName());
        mainActivity.setAlertDialog(player.getName()+" Wins !", "Congratulations", true);
    }

    /**
     * Method to update live comments in main activity
     */
    private void updateLiveComment(String comment) {
        MainActivity mainActivity = (MainActivity) this.getContext();
        mainActivity.setLiveComment(comment);
    }
}

package com.mobiledevelopent.gurcharnsinghsikka.assignment2;

import android.graphics.Paint;

/**
 * Card class to store color and state of a card.
 *
 * @author Gurcharn Singh Sikka
 * @version 1.0
 */
public class Card {
    private Paint color;
    private CardState cardState;

    /**
     * Constructor method used to create instance of class
     */
    public Card(Paint color, CardState cardState) {
        this.color = color;
        this.cardState = cardState;
    }

    /**
     *  Method to get color of the card
     *  @return Paint
     */
    public Paint getColor() {
        return color;
    }

    /**
     * Method to set / change color of card
     * @param color
     */
    public void setColor(int color) {
        this.color.setColor(color);
    }

    /**
     *  Method to get the state of card
     *  @return CardState
     */
    public CardState getCardState() {
        return cardState;
    }

    /**
     *  Method to set / change card state
     *  @param cardState
     */
    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    /**
     *  Method to to flip card from face up to down or vice versa
     */
    public void flipCard() {
        if(cardState == CardState.FACE_DOWN)
            setCardState(CardState.FACE_UP);
        else if (cardState == CardState.FACE_UP)
            setCardState(CardState.FACE_DOWN);
    }
}

/**
 *  This class have three values FACE_UP, FACE_DOWN, MATCHED which is used in class Card to fix the type and number states possible in a cardThis class have three values FACE_UP, FACE_DOWN, MATCHED which is used in class Card to fix the type and number of states possible in a card.
 */
enum CardState{
    FACE_DOWN,
    FACE_UP,
    MATCHED
}

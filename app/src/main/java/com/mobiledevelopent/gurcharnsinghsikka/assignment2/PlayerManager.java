package com.mobiledevelopent.gurcharnsinghsikka.assignment2;

/**
 * Class to manage players while playing game
 * This will update and sets players information in real-time while app and game is running
 *
 * @author Gurcharn Singh Sikka
 * @version 1.0
 */
public class PlayerManager {
    private int currentPlayer;
    private Player player1;
    private Player player2;

    /**
     * Constructor to be used to create instance of this class
     */
    public PlayerManager() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        this.currentPlayer = 1;
    }

    /**
     * Method to retrieve current player number
     * @return int
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *  Method to retrieve player object
     *  @param player
     *  @return Player
     */
    public Player getPlayer(int player) {
        if(player == 1)
            return player1;
        else if(player == 2)
            return player2;
        else
            return new Player("N/A");
    }

    /**
     * Method to switch player turn
     */
    public void switchPlayer() {
        resetClicks();

        if(currentPlayer == 1)
            currentPlayer = 2;
        else if(currentPlayer == 2)
            currentPlayer = 1;
    }

    /**
     * Method to retrieve remaining clicks of current player
     * @return int
     */
    public int getPlayerClicks() {
        if(currentPlayer == 1)
            return player1.getClicks();
        else if(currentPlayer == 2)
            return player2.getClicks();
        else
            return -1;
    }

    /**
     *  Method to reduce clicks of current player by 1
     */
    public void reduceClicks() {
        if(currentPlayer == 1)
            player1.setClicks(player1.getClicks() - 1);
        else if(currentPlayer == 2)
            player2.setClicks(player2.getClicks() - 1);
    }

    /**
     *  Method to reset clicks to 2 for current player
     */
    private void resetClicks() {
        if(currentPlayer == 1)
            player1.setClicks(2);
        if(currentPlayer == 2)
            player2.setClicks(2);
    }

    /**
     * Method to increment score of current player by 1
     */
    public void incrementScore() {
        if(currentPlayer == 1)
            player1.setScore(player1.getScore() + 1);
        if(currentPlayer == 2)
            player2.setScore(player2.getScore() + 1);
    }

    /**
     *  Method to retrieve Player object of winner
     *  @return Player
     */
    public Player getWinner() {
        if(player1.getScore() > player2.getScore())
            return player1;
        else if(player2.getScore() > player1.getScore())
            return player2;
        else if(player2.getScore() == player1.getScore() && player1.getScore()!= 0)
            return new Player("Match Draw");
        else
            return new Player("No One");
    }
}

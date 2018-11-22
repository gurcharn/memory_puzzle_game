package com.mobiledevelopent.gurcharnsinghsikka.assignment2;

/**
 * Class to store player's information like name, clicks and score
 *
 * @author Gurcharn Singh Sikka
 * @version 1.0
 */
public class Player {
    private String name;
    private int clicks;
    private int score;

    /**
     *  Constructor to be used to create instance of the class
     */
    public Player(String name) {
        this.name = name;
        this.clicks = 2;
        this.score = 0;
    }

    /**
     *  Method to retrieve name of player
     *  @return Sring
     */
    public String getName() {
        return name;
    }

    /**
     *  Method to set player name
     *  @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Method to retrieve player's available clicks
     *  @return int
     */
    public int getClicks() {
        return clicks;
    }

    /**
     *  Method to set player's clicks
     *  @param clicks
     */
    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    /**
     * Method to retrieve score of player
     * @return int
     */
    public int getScore() {
        return score;
    }

    /**
     *  Method to set player's score
     *  @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
}

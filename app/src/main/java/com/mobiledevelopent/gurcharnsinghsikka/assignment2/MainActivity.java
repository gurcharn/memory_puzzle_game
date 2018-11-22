package com.mobiledevelopent.gurcharnsinghsikka.assignment2;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * First activity to be shown when app starts.
 *
 * @author Gurcharn Singh Sikka
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private CustomView customView;
    private TextView liveComment;
    private TextView player1Name;
    private TextView player2Name;
    private TextView player1Clicks;
    private TextView player2Clicks;
    private TextView player1Score;
    private TextView player2Score;

    /**
     * Method to set the content of view when activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTextView();
    }

    /**
     * Inflates menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handle selected options from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.menu_new_game){
            customView.newGame();
            return true;
        } else if(itemId == R.id.menu_end_game){
            customView.endGame();
            return true;
        } else if(itemId == R.id.menu_how_to_play){
            setAlertDialog("How to play ?", getGameRules(), false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize Text Views in the activity
     */
    private void initTextView() {
        customView = (CustomView) findViewById(R.id.custom_view);
        liveComment = (TextView) findViewById(R.id.live_comment);
        player1Name = (TextView) findViewById(R.id.player1_name);
        player2Name = (TextView) findViewById(R.id.player2_name);
        player1Clicks = (TextView) findViewById(R.id.player1_clicks);
        player2Clicks = (TextView) findViewById(R.id.player2_clicks);
        player1Score = (TextView) findViewById(R.id.player1_score);
        player2Score = (TextView) findViewById(R.id.player2_score);
    }

    /**
     * Method to create a dialog box
     */
    public void setAlertDialog(String title, String message, boolean winnerAlert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(customView.getContext());

        if(winnerAlert) {
            LayoutInflater factory = LayoutInflater.from(customView.getContext());
            final View view = factory.inflate(R.layout.trophy_image_layout, null);
            builder.setView(view);
        }

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     *  Method to update score board
     */
    public void updateScoreBoard(Player player1, Player player2) {
        player1Name.setText(player1.getName());
        player1Clicks.setText(player1.getClicks()+"");
        player1Score.setText(player1.getScore()+"");

        player2Name.setText(player2.getName());
        player2Clicks.setText(player2.getClicks()+"");
        player2Score.setText(player2.getScore()+"");
    }

    /**
     * Method to update/set comments while playing game
     */
    public void setLiveComment(String comment) {
        liveComment.setText(comment);
    }

    /**
     *  Method to return a string of game rules
     */
    private String getGameRules() {
        String rules = "";
        Resources resources = getResources();
        TypedArray gameRules = resources.obtainTypedArray(R.array.how_to_play_rules);

        for(int i=0 ; i<gameRules.length() ; i++)
            rules = rules + gameRules.getString(i) +"\n\n";

        return rules;
    }

    /**
     *  Method to be called on start of the activity
     */
    @Override
    protected void onStart() {
        super.onStart();
        customView.updateScoreBoard();
    }
}

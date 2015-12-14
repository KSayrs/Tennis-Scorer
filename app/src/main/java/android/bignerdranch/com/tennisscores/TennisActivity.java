package android.bignerdranch.com.tennisscores;
//import
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TennisActivity extends AppCompatActivity {

    private int count1;
    private int count2;
    private int gamecount1;
    private int gamecount2;
    private int set1;
    private int set2;
    private int match1;
    private int match2;
    private int tie1;
    private int tie2;
    private int first_tie;
    private Toast game1toast;
    private Toast game2toast;
    private Toast advantage1;
    private Toast advantage2;
    private Toast deuce;
    private Toast set1toast;
    private Toast set2toast;
    private Toast match1toast;
    private Toast match2toast;
    private Toast tiebreakertoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis);

        count1 = 0;
        count2 = 0;
        gamecount1 = 0;
        gamecount2 = 0;
        set1 = 0;
        set2 = 0;
        match1 = 0;
        match2 = 0;
        tie1 = 0;
        tie2 = 0;
        first_tie = 0;
        game1toast = Toast.makeText(this, R.string.player_one_game_victory, Toast.LENGTH_SHORT);
        game2toast = Toast.makeText(this, R.string.player_two_game_victory, Toast.LENGTH_SHORT);
        deuce = Toast.makeText(this, R.string.deuce, Toast.LENGTH_SHORT);
        advantage1 = Toast.makeText(this, R.string.advantage1, Toast.LENGTH_SHORT);
        advantage2 = Toast.makeText(this, R.string.advantage2, Toast.LENGTH_SHORT);
        set1toast = Toast.makeText(this, R.string.player_one_set_victory, Toast.LENGTH_SHORT);
        set2toast = Toast.makeText(this, R.string.player_two_set_victory, Toast.LENGTH_SHORT);
        match1toast = Toast.makeText(this, R.string.player_one_match_victory, Toast.LENGTH_SHORT);
        match2toast = Toast.makeText(this, R.string.player_two_match_victory, Toast.LENGTH_SHORT);
        tiebreakertoast = Toast.makeText(this, R.string.tiebreaker, Toast.LENGTH_SHORT);

        if(savedInstanceState != null){
            count1 = savedInstanceState.getInt("count1");
            TextView v1 = (TextView) findViewById(R.id.game_points_player_1);
            score_points(v1, count1);

            count2 = savedInstanceState.getInt("count2");
            TextView v2 = (TextView) findViewById(R.id.game_points_player_2);
            score_points(v2, count2);

            gamecount1 = savedInstanceState.getInt("count3");
            TextView v3 = (TextView) findViewById(R.id.games_won_player_1);
            showScore(gamecount1, v3);

            gamecount2 = savedInstanceState.getInt("count4");
            TextView v4 = (TextView) findViewById(R.id.games_won_player_2);
            showScore(gamecount2, v4);

            set1 = savedInstanceState.getInt("count5");
            TextView v5 = (TextView) findViewById(R.id.set_points_player_1);
            showScore(set1, v5);

            set2 = savedInstanceState.getInt("count6");
            TextView v6 = (TextView) findViewById(R.id.set_points_player_2);
            showScore(set2, v6);

            match1 = savedInstanceState.getInt("count7");
            TextView v7 = (TextView) findViewById(R.id.match_points_player_1);
            showScore(match1, v7);

            match2 = savedInstanceState.getInt("count8");
            TextView v8 = (TextView) findViewById(R.id.match_points_player_2);
            showScore(match2, v8);

            tie1 = savedInstanceState.getInt("count9");
            tie2 = savedInstanceState.getInt("count10");

            first_tie = savedInstanceState.getInt("count11");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("count1", count1);
        savedInstanceState.putInt("count2", count2);
        savedInstanceState.putInt("count3", gamecount1);
        savedInstanceState.putInt("count4", gamecount2);
        savedInstanceState.putInt("count5", set1);
        savedInstanceState.putInt("count6", set2);
        savedInstanceState.putInt("count7", match1);
        savedInstanceState.putInt("count8", match2);
        savedInstanceState.putInt("count9", tie1);
        savedInstanceState.putInt("count10", tie2);
        savedInstanceState.putInt("count11", first_tie);
    }

    // This is the score button onClick for player 1's side
    public void score_player_1(View view) {
        if(gamecount1 == 6 && gamecount2 == 6 && first_tie != 0){
            tie1++;
            tie_breaker((findViewById(R.id.tiebreaker1)), tie1);
            return;
        }
        count1++;
        score_points(findViewById(R.id.game_points_player_1), count1);
    }

    // This is the score button onClick for player 2's side
    public void score_player_2(View view) {
        if(gamecount1 == 6 && gamecount2 == 6 && first_tie != 0){
            tie2++;
            tie_breaker(findViewById(R.id.tiebreaker2), tie2);
            return;
        }
        count2++;
        score_points(findViewById(R.id.game_points_player_2), count2);
    }


    public void score_points(View view, int count){
        //This block is for game points
        if(count == 1){ showScore(15, findViewById(view.getId())); }
        if(count == 2) {
            showScore(30, findViewById(view.getId())); }
        if(count == 3){ showScore(40, findViewById(view.getId())); }

        // Deuce popup
        if (count1 == count2 && count > 2){
            deuce.show();
        }

        // Advantage blocks
        if ((count > 3) && ((count - count2) == 1)){
            advantage1.show();
        }
        else if ((count > 3) && ((count - count1) == 1)){
            advantage2.show();
        }

        // Game victory condition
        if(count1-count2 >= 2 && count > 3){
            gamecount1++;
            showScore(gamecount1, findViewById(R.id.games_won_player_1));
            //Set victory condition
            if (gamecount1 >= 6 && (gamecount1-gamecount2) >= 2){
                set1++;
                if(set1 == 2) {
                    match1toast.show();
                    match1++;
                    showScore(match1, findViewById(R.id.match_points_player_1));
                    gameset2reset();
                } else {
                    //Toast.makeText(this, R.string.player_one_set_victory, Toast.LENGTH_SHORT).show();
                    set1toast.show();
                    showScore(set1, findViewById(R.id.set_points_player_1));
                    gamesetreset();
                }
            }
            else if(gamecount1 == 6 && gamecount2 == 6 && first_tie == 0){
                tie_breaker(findViewById(R.id.tiebreaker1), tie1);
                first_tie++;
                return;
            } else { //resets game points score back to zero
                //Toast.makeText(this, R.string.player_one_game_victory, Toast.LENGTH_SHORT).show();
                game1toast.show();
                score_reset();
            }
        }
        // Game victory condition
        if (count2-count1 >= 2 && count >3){
            gamecount2++;
            showScore(gamecount2, findViewById(R.id.games_won_player_2));
            //Set victory condition
            if (gamecount2 >= 6 && (gamecount2-gamecount1) >= 2){
                set2++;
                if(set2 == 2) {
                    //Toast.makeText(this, R.string.player_two_match_victory, Toast.LENGTH_SHORT).show();
                    match2toast.show();
                    match2++;
                    showScore(match2, findViewById(R.id.match_points_player_2));
                    gameset2reset();
                } else {
                    //Toast.makeText(this, R.string.player_two_set_victory, Toast.LENGTH_SHORT).show();
                    set2toast.show();
                    showScore(set2, findViewById(R.id.set_points_player_2));
                    gamesetreset();
                }
            }
            else if(gamecount1 == 6 && gamecount2 == 6 && first_tie == 0){
                tie_breaker((findViewById(R.id.tiebreaker2)), tie2);
                first_tie++;
            } else { //resets game points score back to zero
                //Toast.makeText(this, R.string.player_two_game_victory, Toast.LENGTH_SHORT).show();
                game2toast.show();
                score_reset();
            }
        }
    }

    // handles the tiebreaker points
    public void tie_breaker(View view, int tie){
        TextView tiebreaker1 = (TextView) findViewById(R.id.tiebreaker1);
        TextView tiebreaker2 = (TextView) findViewById(R.id.tiebreaker2);
        TextView v = (TextView) view;
        if(first_tie == 0) {
            //Toast.makeText(this, R.string.tiebreaker, Toast.LENGTH_SHORT).show();
            tiebreakertoast.show();
            tiebreaker1.setText(getText(R.string.tiebreaker) + ": " + tie);
            tiebreaker2.setText(getText(R.string.tiebreaker) + ": " + tie);
            score_reset();
        }
        else if (tie >=7 && (tie1 - tie2) >= 2){
            v.setText(getText(R.string.tiebreaker) + ": " + tie);
           // Toast.makeText(this, R.string.player_one_set_victory, Toast.LENGTH_SHORT).show();
            set1toast.show();
            TextView setView = (TextView) findViewById(R.id.set_points_player_1);
            showScore((set1 + 1), setView);
            gamesettiereset();
        }
        else if (tie >=7 && (tie2 - tie1) >= 2){
            v.setText(getText(R.string.tiebreaker) + ": " + tie);
            //Toast.makeText(this, R.string.player_two_set_victory, Toast.LENGTH_SHORT).show();
            set2toast.show();
            TextView setView = (TextView) findViewById(R.id.set_points_player_2);
            showScore((set1 + 1), setView);
            gamesettiereset();
        }
        else {
            v.setText(getText(R.string.tiebreaker) + ": " + tie);
        }
    }

    // This resets for the game points after a game has been won
    public void score_reset(){
        TextView score11 = (TextView) findViewById(R.id.game_points_player_1);
        TextView score12 = (TextView) findViewById(R.id.game_points_player_2);
        score11.setText(R.string.love_init);
        score12.setText(R.string.love_init);
        count1 = 0;
        count2 = 0;
    }

    // Resets the game points and game counts after a set has been won
    public void gamesetreset(){
        score_reset();
        TextView score11 = (TextView) findViewById(R.id.games_won_player_1);
        TextView score12 = (TextView) findViewById(R.id.games_won_player_2);
        score11.setText(R.string.placeholder_number);
        score12.setText(R.string.placeholder_number);
        gamecount1 = 0;
        gamecount2 = 0;
    }

    public void gameset2reset(){
        gamesetreset();
        TextView score11 = (TextView) findViewById(R.id.set_points_player_1);
        TextView score12 = (TextView) findViewById(R.id.set_points_player_2);
        score11.setText(R.string.placeholder_number);
        score12.setText(R.string.placeholder_number);
        set1 = 0;
        set2 = 0;
    }

    public void gamesettiereset(){
        gamesetreset();
        TextView tiebreaker1 = (TextView) findViewById(R.id.tiebreaker1);
        TextView tiebreaker2 = (TextView) findViewById(R.id.tiebreaker2);
        tiebreaker1.setText("");
        tiebreaker2.setText("");
        tie1 = 0;
        tie2 = 0;
        first_tie = 0;
    }

    // This resets everything - reset button onClick
    public void reset(View view) {
        TextView score11 = (TextView) findViewById(R.id.game_points_player_1);
        TextView score21 = (TextView) findViewById(R.id.games_won_player_1);
        TextView score31 = (TextView) findViewById(R.id.set_points_player_1);
        TextView score41 = (TextView) findViewById(R.id.match_points_player_1);
        TextView score12 = (TextView) findViewById(R.id.game_points_player_2);
        TextView score22 = (TextView) findViewById(R.id.games_won_player_2);
        TextView score32 = (TextView) findViewById(R.id.set_points_player_2);
        TextView score42 = (TextView) findViewById(R.id.match_points_player_2);
        TextView tiebreaker1 = (TextView) findViewById(R.id.tiebreaker1);
        TextView tiebreaker2 = (TextView) findViewById(R.id.tiebreaker2);

        score11.setText(R.string.love_init);
        score21.setText(R.string.placeholder_number);
        score31.setText(R.string.placeholder_number);
        score41.setText(R.string.placeholder_number);
        score12.setText(R.string.love_init);
        score22.setText(R.string.placeholder_number);
        score32.setText(R.string.placeholder_number);
        score42.setText(R.string.placeholder_number);
        tiebreaker1.setText("");
        tiebreaker2.setText("");

        count1 = 0;
        count2 = 0;
        gamecount1 = 0;
        gamecount2 = 0;
        set1 = 0;
        set2 = 0;
        match1 = 0;
        match2 = 0;
        tie1 = 0;
        tie2 = 0;
        first_tie = 0;

        game1toast.cancel();
        game2toast.cancel();
        deuce.cancel();
        advantage1.cancel();
        advantage2.cancel();
        set1toast.cancel();
        set2toast.cancel();
        match1toast.cancel();
        match2toast.cancel();
        tiebreakertoast.cancel();
    }

    private void showScore (int number, View view){
        TextView v = (TextView) view;
        v.setText("" + number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tennis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

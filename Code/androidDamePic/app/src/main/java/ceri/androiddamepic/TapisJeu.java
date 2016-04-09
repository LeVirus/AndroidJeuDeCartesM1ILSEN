package ceri.androiddamepic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import Game.IPlayer;
import Game.Partie;
import Game.structGameStat;
import Player.IAPlayer;

public class TapisJeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis_jeu);
        /*ImageButton buttonLaunch = (ImageButton) findViewById(R.id.imageButton);
        buttonLaunch.setVisibility(ImageButton.VISIBLE);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });*/
        IPlayer playerss[] = new IPlayer[4];
        for( int i = 0; i < 4; i++ )
        {
            playerss[i] = new IAPlayer("J" + i);
        }

       // playerss[3] = new InteractePlayer();
        Partie pa = new Partie(playerss);
        structGameStat str = pa.newGame();
    }
}

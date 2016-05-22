package ceri.androiddamepic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);
        //Récupération des informations
        String[] n = DataResult.getNames();
        int[] s = DataResult.getScores();

        //Moddification des labels
        int id[] = {R.id.player1,R.id.player2,R.id.player3,R.id.player4},i=0;

        for(TextView tv;i<4;i++)
        {
            tv=(TextView)findViewById(id[i]);
            tv.setText(n[i]+" : "+String.valueOf(s[i])+" points");
        }

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

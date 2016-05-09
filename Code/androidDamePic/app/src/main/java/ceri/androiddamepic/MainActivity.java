package ceri.androiddamepic;

/*import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLaunch = (Button) findViewById(R.id.button);

        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TapisJeu.class);
                startActivity(intent);
            }
        });
    }
}*/


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener

{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

            case R.id.nouvelle_partie:  // Lancer une nouvelle partie

                Toast.makeText(getApplicationContext(), "Lancement de la partie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, TapisJeu.class));

                break;
            case R.id.parametre:        // Régler les paramètres
                startActivity(new Intent(MainActivity.this, TapisJeu.class));
                //setContentView(R.layout.main_menu);
                break;

            case R.id.exit:             // Quitter l'application
                finish();
                break;
        }
    }
}
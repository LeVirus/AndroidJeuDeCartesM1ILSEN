package ceri.androiddamepic;

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
        CarteUI.sonactif = true;
        TapisJeu.sonactif = true;
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
                startActivity(new Intent(MainActivity.this, ParamMenuActivity.class));
                //setContentView(R.layout.main_menu);
                break;

            case R.id.exit:             // Quitter l'application
                finish();
                break;
        }
    }
}
package ceri.androiddamepic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ParamMenuActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_menu);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.checkSound:        // Activer/Désactiver le son


                break;
            case R.id.retour:             // Retourner en arrière
                finish();
                break;
        }
    }
}

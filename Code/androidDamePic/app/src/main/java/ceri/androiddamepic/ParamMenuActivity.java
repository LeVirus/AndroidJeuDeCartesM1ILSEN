package ceri.androiddamepic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class ParamMenuActivity extends AppCompatActivity implements View.OnClickListener {
    CheckBox cb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_menu);
        cb = (CheckBox) findViewById(R.id.checkSound);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.checkSound:        // Activer/Désactiver le son
                CarteUI.sonactif = cb.isChecked();


                break;
            case R.id.retour:             // Retourner en arrière
                finish();
                break;
        }
    }
}

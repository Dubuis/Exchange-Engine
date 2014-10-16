package fr.xmlstyle.exchangeengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class AccueilActivity extends Activity {

	final String EXTRA_LOGIN = "user_login";
	final String EXTRA_PASSWORD = "user_password";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        
        Intent intent = getIntent();
        TextView phraseAccueil = (TextView) findViewById(R.id.phraseAccueil);
        if (intent != null){
        	phraseAccueil.setText("Bonjour "+intent.getStringExtra(EXTRA_LOGIN));
        }
    }
}
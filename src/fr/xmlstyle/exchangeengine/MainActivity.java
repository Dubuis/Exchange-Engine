package fr.xmlstyle.exchangeengine;

import java.io.*;

import org.xmlpull.v1.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	final String EXTRA_LOGIN = "user_login";
	final String EXTRA_PASSWORD = "user_password";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		// Récupération des valeurs des zones de texte "identifiant" et "motdepasse"
	    final Button bouton1 = (Button) findViewById(R.id.buttonconnexion);
		final EditText login = (EditText) findViewById(R.id.identifiant);
	    final EditText pass = (EditText) findViewById(R.id.motdepasse);
		bouton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
				intent.putExtra(EXTRA_LOGIN, login.getText().toString());
				intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());
				startActivity(intent);
			}
	    });
	}
	
}

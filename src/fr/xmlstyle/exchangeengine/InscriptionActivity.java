package fr.xmlstyle.exchangeengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InscriptionActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		/** BOUTON POUR RETOURNER A LA PAGE PRINCIPALE SI ON A DEJA UN COMPTE ! **/
		final Button bouton1 = (Button) findViewById(R.id.buttonretour);
		bouton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
				startActivity(intent);
			}
	    });
	}
}
package fr.xmlstyle.exchangeengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class AccueilActivity extends Activity {

	final String EXTRA_MAIL = "user_mail";
	final String EXTRA_PASSWORD = "user_password";
	final String EXTRA_NOM = "user_name";
	final String EXTRA_PRENOM = "user_prenom";
	final PersonInfo currentUser = new PersonInfo();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        final Button bouton1 = (Button) findViewById(R.id.boutonrecherche);
        bouton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent intent = new Intent(AccueilActivity.this, ParcourirActivity.class);
				startActivity(intent);
			}
	    });
        //Bouton 2
        final Button bouton2 = (Button) findViewById(R.id.boutonmesobjets);
        bouton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccueilActivity.this, MesObjetsActivity.class);
				intent.putExtra(EXTRA_MAIL, currentUser.getmel());
				intent.putExtra(EXTRA_NOM, currentUser.getnom());
				intent.putExtra(EXTRA_PRENOM, currentUser.getprenom());
				intent.putExtra(EXTRA_PASSWORD, currentUser.getmdp());
				startActivity(intent);
			}
	    });
        // Récupération de l'utilisateur courant
        Intent intent = getIntent();
        if(intent != null){
	        currentUser.setnom(intent.getStringExtra(EXTRA_NOM));
	    	currentUser.setprenom(intent.getStringExtra(EXTRA_PRENOM));
	    	currentUser.setmdp(intent.getStringExtra(EXTRA_PASSWORD));
	    	currentUser.setmel(intent.getStringExtra(EXTRA_MAIL));
        }
    	// Affichage de la phrase de bonjour personnalisée 
        TextView phraseAccueil = (TextView) findViewById(R.id.phraseAccueil);
        if (currentUser != null){
        	phraseAccueil.setText("Bonjour "+currentUser.getnom()+" "+currentUser.getprenom());
        }
    }
}
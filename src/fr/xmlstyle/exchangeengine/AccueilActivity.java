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
	PersonInfo currentUser;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        //Bouton 1
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
				Bundle extra = new Bundle();
				extra.putSerializable("User", currentUser);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
	    });
        //Bouton 3
        final Button bouton3 = (Button) findViewById(R.id.boutonajouter);
        bouton3.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				Intent intent = new Intent(AccueilActivity.this, AjouterObjetsActivity.class);
				Bundle extra = new Bundle();
				extra.putSerializable("User", currentUser);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
        });

        //Bouton 4
        final Button bouton4 = (Button) findViewById(R.id.boutoncontrat);
        bouton4.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				Intent intent = new Intent(AccueilActivity.this, ContratActivity.class);
				Bundle extra = new Bundle();
				extra.putSerializable("User", currentUser);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
        });
        
        /** Récupération de l'utilisateur courant **/
		Bundle extra = getIntent().getBundleExtra("extra");
		if(extra != null) currentUser = (PersonInfo) extra.getSerializable("Person");
    	/** Affichage de la phrase de bonjour personnalisée **/ 
        TextView phraseAccueil = (TextView) findViewById(R.id.phraseAccueil);
        if (currentUser != null){
        	phraseAccueil.setText("Bonjour "+currentUser.getnom()+" "+currentUser.getprenom());
        }
    }
}
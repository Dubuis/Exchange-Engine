package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	final String EXTRA_MAIL = "user_mail";
	final String EXTRA_PASSWORD = "user_password";
	final String EXTRA_NOM = "user_name";
	final String EXTRA_PRENOM = "user_prenom";
	private ArrayList<PersonInfo> userList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Récupération des Clients existants
		Bundle extra = getIntent().getBundleExtra("extra");
        if(extra != null){
        	PersonInfo nouveau = (PersonInfo) extra.getSerializable("nouveau");
        	try{
        		if(!PersonXMLHandler.ajouter(nouveau)){
					Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
					Bundle env = new Bundle();
					extra.putSerializable("Person", userList);
					intent.putExtra("extra", env);
					startActivity(intent);
        		}
        	}
        	catch(Exception e){
    			e.printStackTrace();
        	}
        }
        userList = PersonXMLHandler.lecture();
        
		// Fin de la récupération
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		// Récupération des zones de texte "identifiant" et "motdepasse"
		final EditText login = (EditText) findViewById(R.id.identifiant);
	    final EditText pass = (EditText) findViewById(R.id.motdepasse);
	    
	    // Création de la fonction Onclick sur le bouton1 (Connexion)
	    final Button bouton1 = (Button) findViewById(R.id.buttonconnexion);
		bouton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean exist = false;
				for(int i=0; i<userList.size();i++){
					Toast.makeText(MainActivity.this, userList.get(i).toString(), Toast.LENGTH_SHORT);
					if(userList.get(i).getmel().toString().equals(login.getText().toString())){
						exist = true;
						if(userList.get(i).getmdp().toString().equals(pass.getText().toString())){
							Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
							intent.putExtra(EXTRA_MAIL, userList.get(i).getmel());
							intent.putExtra(EXTRA_NOM, userList.get(i).getnom());
							intent.putExtra(EXTRA_PRENOM, userList.get(i).getprenom());
							intent.putExtra(EXTRA_PASSWORD, userList.get(i).getmdp());
							Bundle passa = new Bundle();
							passa.putSerializable("Person", userList.get(i));
							intent.putExtra("extra", passa);
							startActivity(intent);
						}
						else{
							Toast.makeText(MainActivity.this, "MOT DE PASSE ERRONE !", Toast.LENGTH_LONG).show();
						}
					}
				}
				if(!exist) Toast.makeText(MainActivity.this, "Utilisateur inconnu !", Toast.LENGTH_LONG).show();
			}
	    });
		
	    // Création de la fonction Onclick sur le bouton2 (Inscription)
	    final Button bouton2 = (Button) findViewById(R.id.buttoninscription);
		bouton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
				Bundle extra = new Bundle();
				extra.putSerializable("Person", userList);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
	    });
	}
}

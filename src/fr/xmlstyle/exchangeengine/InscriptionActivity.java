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

public class InscriptionActivity extends Activity {
	private ArrayList<PersonInfo> userList;
	/** Création de la page **/
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		/** RECUPERATION DE LA LISTE D'UTILISATEUR **/
        Bundle extra = getIntent().getBundleExtra("extra");
        if(extra != null) userList = (ArrayList<PersonInfo>) extra.getSerializable("Person");
		
		/** BOUTON POUR RETOURNER A LA PAGE PRINCIPALE SI ON A DEJA UN COMPTE ! **/
		final Button bouton1 = (Button) findViewById(R.id.buttonretour);
		bouton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
				startActivity(intent);
			}
	    });
		
		/** BOUTON POUR VALIDER L'INSCRIPTION **/
		final Button bouton2 = (Button) findViewById(R.id.buttonsinscrire);
		bouton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Récupération des champs
				boolean ok = true;
				final EditText nom = (EditText) findViewById(R.id.nomUser);
			    final EditText prenom = (EditText) findViewById(R.id.prenomUser);
			    final EditText mail = (EditText) findViewById(R.id.mailUser);
			    final EditText mdp = (EditText) findViewById(R.id.mdpUser);
			    final EditText mdp2 = (EditText) findViewById(R.id.verifMdp);
			    if(mdp.getText().toString().isEmpty() || mdp2.getText().toString().isEmpty() || nom.getText().toString().isEmpty() || prenom.getText().toString().isEmpty() || mail.getText().toString().isEmpty() ){
			    	// tous les champs ne sont pas remplis !
			    	Toast.makeText(InscriptionActivity.this, "Tous les champs doivent être remplis !!!", Toast.LENGTH_SHORT).show();
			    	ok = false;
			    }
			    if(ok && !(mdp.getText().toString().equals(mdp2.getText().toString()))){ // mots de passe differents
			    	Toast.makeText(InscriptionActivity.this, "Attention mots de passe différents !!!", Toast.LENGTH_SHORT).show();
			    	mdp.setText("");
			    	mdp2.setText("");
			    	ok = false;
			    }
			    if(ok && (mdp.getText().length() < 6)){ // mot de passe invalide
			    	Toast.makeText(InscriptionActivity.this, "Le mot de passe doit contenir au moins 6 caractères !!!", Toast.LENGTH_SHORT).show();
			    	mdp.setText("");
			    	mdp2.setText("");
			    	ok = false;
			    }
			    if(ok){
				    for(int i = 0; i < userList.size(); i++){
				    	if(userList.get(i).getmel().toString().equals(mail.getText().toString())){
				    		Toast.makeText(InscriptionActivity.this, "Mail déjà existant !!!", Toast.LENGTH_SHORT).show();
				    		mail.setText("");
				    		ok = false;
				    	}
				    }
			    }
			    if(ok){ // Si tout est bon avant, on peut envoyer les données et retourner à la page d'accueil
			    	PersonInfo newUser = new PersonInfo();
			    	newUser.setmdp(mdp.getText().toString());
			    	newUser.setnom(nom.getText().toString());
			    	newUser.setprenom(prenom.getText().toString());
			    	newUser.setmel(mail.getText().toString());
			    	userList.add(newUser);
			    	Bundle extra = new Bundle();
			    	extra.putSerializable("Person", userList);
			    	extra.putSerializable("nouveau", newUser);
			    	Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
			    	intent.putExtra("extra", extra);
					startActivity(intent);
			    }
			}
	    });
	}
}
package fr.xmlstyle.exchangeengine;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
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
		userList = parseXML();
		// Fin de la récupération
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		// Récupération des valeurs des zones de texte "identifiant" et "motdepasse"
		final EditText login = (EditText) findViewById(R.id.identifiant);
	    final EditText pass = (EditText) findViewById(R.id.motdepasse);
	    // Création de la fonction Onclick sur le bouton1 (Connexion)
	    final Button bouton1 = (Button) findViewById(R.id.buttonconnexion);
		bouton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean exist = false;
				for(int i=0; i<userList.size();i++){
					if(userList.get(i).getmel().toString().equals(login.getText().toString())){
						exist = true;
						if(userList.get(i).getmdp().toString().equals(pass.getText().toString())){
							Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
							intent.putExtra(EXTRA_MAIL, userList.get(i).getmel());
							intent.putExtra(EXTRA_NOM, userList.get(i).getnom());
							intent.putExtra(EXTRA_PRENOM, userList.get(i).getprenom());
							intent.putExtra(EXTRA_PASSWORD, userList.get(i).getmdp());
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
				startActivity(intent);
			}
	    });
	}
	
	public ArrayList<PersonInfo> parseXML(){
		ArrayList<PersonInfo> personList = null;
		AssetManager assetManager = getBaseContext().getAssets();
		try {
			InputStream is = assetManager.open("Clients.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			PersonXMLHandler myXMLHandler = new PersonXMLHandler();
			xr.setContentHandler(myXMLHandler);
			InputSource inStream = new InputSource(is);
			xr.parse(inStream);
			
			personList = myXMLHandler.getPersonList();
		}
		catch(Exception e){
			
		}
		return personList;
	}
	
}

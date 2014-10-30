package fr.xmlstyle.exchangeengine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
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
        	//userList = (ArrayList<PersonInfo>) extra.getSerializable("Person");
        	PersonInfo nouveau = (PersonInfo) extra.getSerializable("nouveau");
        	try{
        		//ajouterElement(nouveau);
        	}
        	catch(Exception e){
        		Toast.makeText(this, "Erreur d'ajout !", Toast.LENGTH_SHORT);
        	}
        }
        else userList = parseXML();
        
        	/**   /!\ PENSER A RAJOUTER L'ECRITURE DANS LE FICHIER !!! /!\  **/
        
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
					Toast.makeText(MainActivity.this, userList.get(i).toString(), Toast.LENGTH_SHORT);
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
				Bundle extra = new Bundle();
				extra.putSerializable("Person", userList);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
	    });
	}
	
	public ArrayList<PersonInfo> parseXML(){
		AssetManager assetManager = getBaseContext().getAssets();
		ArrayList<PersonInfo> personList = null;
		///
		boolean stockageExiste = false;
		boolean stockageEcriture = false;
		String etat = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(etat)) stockageExiste = stockageEcriture = true;
		else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(etat)) stockageExiste = true;
		File directory = Environment.getExternalStorageDirectory();
		File file = new File(directory+"/clients.xml");
		if(!file.exists())
			Toast.makeText(this, "Fichier clients absent !", Toast.LENGTH_SHORT).show();
		else{
			try{
				InputStream is = openFileInput(file.getPath());
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				PersonXMLHandler myXMLHandler = new PersonXMLHandler();
				xr.setContentHandler(myXMLHandler);
				InputSource inStream = new InputSource(is);
				xr.parse(inStream);
				
				personList = myXMLHandler.getPersonList();
			}
			catch(Exception e){}
		}
		return personList;
	}
	
	@SuppressWarnings("null")
	void ajouterElement(PersonInfo user) throws Exception {
		org.jdom2.Document document;
		Element racine;
		
		/** OUVERTURE FICHIER **/
		File directory = Environment.getExternalStorageDirectory();
		File file = new File(directory+"/clients.xml");
		InputStream is = openFileInput(file.getPath());
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(is);
		racine = document.getRootElement();
		
		/** AJOUT D'UN UTILISATEUR DANS LA RACINE **/
		Element utilisateur = null;
		utilisateur.setName("utilisateur");
		Element mel = null;
		mel.setName("mel");
		mel.addContent(user.getmel().toString());
		utilisateur.addContent(mel);
		Element information = null;
		information.setName("information");
		Element nom = null;
		nom.setName("nom");
		nom.addContent(user.getnom().toString());
		information.addContent(nom);
		Element prenom = null;
		prenom.setName("prenom");
		prenom.addContent(user.getprenom().toString());
		information.addContent(prenom);
		Element mdp = null;
		mdp.setName("mdp");
		mdp.addContent(user.getmdp().toString());
		information.addContent(mdp);
		utilisateur.addContent(information);
		racine.addContent(utilisateur);
		
		/** ENREGISTREMENT **/
		FileOutputStream fOut = null;
		OutputStreamWriter osw = null;
		try{
			fOut = this.openFileOutput(file.getPath(), MODE_APPEND);
			osw = new OutputStreamWriter(fOut);
			osw.write(racine.toString());
			osw.flush();
			Toast.makeText(this, "J'ai écrit", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			Toast.makeText(this, "J'ai pas écrit", Toast.LENGTH_SHORT).show();}
		finally{
			try{
				osw.close();
				fOut.close();
			}
			catch(IOException e){}
		}
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		sortie.output(document, new FileOutputStream("clients.xml"));
	}
	
}

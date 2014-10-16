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
	StringReader stream;
	// Parser
    public void Xml() throws XmlPullParserException, IOException {
		XmlPullParserFactory Factory = XmlPullParserFactory.newInstance();
		XmlPullParser monParser = Factory.newPullParser();
		Factory.setNamespaceAware(true);
		monParser.setInput(stream);
		int event = monParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) 
		{
			String name=monParser.getName();
			switch (event){
			case XmlPullParser.START_TAG:
			break;
			case XmlPullParser.END_TAG:
			if(name.equals("objet")){
				String objet = monParser.getAttributeValue(null,"value");
				
				/* Clients.xml de la forme :
				 * 
				 * <diagramme>
				 * 	<objet>
	  			 *		<propritaire>propritaire</propritaire>
	  			 *		<information>
	    		 *			<mel>mel</mel>
	    		 *			<titre>titre</titre>
	    		 * 			<categorie>vehicule</categorie>
	    		 *			<couleur>couleur</couleur>
	    		 *			<echange>echange</echange>
	    		 *			<zone>zone</zone>
	    		 *			<numero>numero</numero>
	    		 *			<url>voiture1.png</url>
	  			 *		</information>
				 *	</objet>
				 *
				 * 	<objet>
				 * 		...
				 * 	</objet>
				 *</diagramme>
				 * 
				 */
			
			}
			break;
			}		 
			event = monParser.next(); 					
		}
		// Parser
    }
    
	final String EXTRA_LOGIN = "user_login";
	final String EXTRA_PASSWORD = "user_password";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			  // Le périphérique est bien monté
			File mFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ " + getPackageName() + "/files/");
			try{
				FileInputStream input = openFileInput("client.xml");
				int value;
				StringBuffer lu = new StringBuffer();
				while((value = input.read()) != -1) {
					// On écrit dans le fichier le caractère lu
					lu.append((char)value);
				}
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
		    }
			catch (IOException e) {
				e.printStackTrace();
	        }
		}
		else{
			  // Le périphérique n'est pas bien monté ou on ne peut écrire dessus
		}
		
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

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
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MesObjetsActivity extends Activity{
	final String EXTRA_MAIL = "user_mail";
	final String EXTRA_PASSWORD = "user_password";
	final String EXTRA_NOM = "user_name";
	final String EXTRA_PRENOM = "user_prenom";
	final PersonInfo currentUser = new PersonInfo();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mes_objets);
		/** Récupération de l'utilisateur courant **/
        Intent intent = getIntent();
        if(intent != null){
	        currentUser.setnom(intent.getStringExtra(EXTRA_NOM));
	    	currentUser.setprenom(intent.getStringExtra(EXTRA_PRENOM));
	    	currentUser.setmdp(intent.getStringExtra(EXTRA_PASSWORD));
	    	currentUser.setmel(intent.getStringExtra(EXTRA_MAIL));
	    /** Parse et affiche **/
		parseXML();
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInflater().inflate(R.menu.menu_mesobjets, menu);
		return true;
	}
	
	public void parseXML(){
		AssetManager assetManager = getBaseContext().getAssets();
		try {
			InputStream is = assetManager.open("Objets.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			
			ObjectXMLHandler myXMLHandler = new ObjectXMLHandler();
			xr.setContentHandler(myXMLHandler);
			InputSource inStream = new InputSource(is);
			xr.parse(inStream);
			
			ArrayList<ObjectInfo> objectList = myXMLHandler.getObjectList();
			
		
			
			LinearLayout layoutParent = (LinearLayout)findViewById(R.id.mesobjets_layout);
			LinearLayout layoutContent;
			TextView tv;
			//ImageView img;
			 /** Boucle sur tous les objets du fichier **/
			for(ObjectInfo objectinfo: objectList){
				 /** filtre ceux qui appartiennent à l'utilisateur courant **/
				if( (objectinfo.getproprietaire().toString()).equals(currentUser.getmel())){
					layoutContent = new LinearLayout(this);
					layoutContent.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					par.setMargins(15, 15, 15, 15);
					layoutContent.setLayoutParams(par);
					layoutContent.setBackgroundColor(Color.LTGRAY);
					
					tv = new TextView(this);
					tv.setText("Titre : "+objectinfo.gettitre());
					tv.setBackgroundColor(Color.RED);
					tv.setTextSize(15);
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tCategorie : "+objectinfo.getcategorie());
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tInformations : "+objectinfo.getechange());
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tCouleur : "+objectinfo.getcouleur());
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tLieu : "+objectinfo.getzone());
					layoutContent.addView(tv);
					//img = new ImageView(this);
					//img.setImageURI(Uri.parse(objectinfo.geturl()));
					//layoutContent.addView(img);
					tv = new TextView(this);
					tv.setText("Contact :");
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tMail : "+objectinfo.getmel());
					layoutContent.addView(tv);
					tv = new TextView(this);
					tv.setText("\tTel : "+objectinfo.getnumero());
					layoutContent.addView(tv);
					layoutParent.addView(layoutContent);
				}
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
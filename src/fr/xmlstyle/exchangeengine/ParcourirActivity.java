package fr.xmlstyle.exchangeengine;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParcourirActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		parseXML();
		setContentView(R.layout.parcourir);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInflater().inflate(R.menu.parcourir, menu);
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
			
			String proprietaire = myXMLHandler.getproprietaire();
			ArrayList<ObjectInfo> objectList = myXMLHandler.getObjectList();
			
			TextView tv;
			//ImageView img;
			for(ObjectInfo objectinfo: objectList){
				// A FAIRE : RENDRE CA PLUS DYNAMIQUE EN CACHANT DES INFOS ET EN LES FAISANT APPARAITRE
				//			 EN CLIQUANT SUR L'IMAGE (PAR EXEMPLE)
				LinearLayout layoutContent = (LinearLayout) findViewById(R.layout.parcourir);
				layoutContent.removeAllViewsInLayout();
				tv = new TextView(this);
				tv.setText("Titre : "+objectinfo.gettitre());
				tv = new TextView(this);
				tv.setText("proposé par : "+proprietaire);
				tv = new TextView(this);
				tv.setText("\tCategorie : "+objectinfo.getcategorie());
				tv = new TextView(this);
				tv.setText("\tInformations : "+objectinfo.getechange());
				tv = new TextView(this);
				tv.setText("\tCouleur : "+objectinfo.getcouleur());
				tv = new TextView(this);
				tv.setText("\tLieu : "+objectinfo.getzone());
				//img = new ImageView(this);
				//img.setImageURI(Uri.parse(objectinfo.geturl()));
				tv = new TextView(this);
				tv.setText("Contact :");
				tv = new TextView(this);
				tv.setText("\tMail : "+objectinfo.getmel());
				tv = new TextView(this);
				tv.setText("\tTel : "+objectinfo.getnumero());
			}
			
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
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
import android.widget.Toast;

public class ParcourirActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parcourir);
		parseXML();
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
		
			LinearLayout layoutContent = (LinearLayout)findViewById(R.id.parcourir_layout);
			TextView tv;
			ImageView img;
			for(ObjectInfo objectinfo: objectList){
				// A FAIRE : RENDRE CA PLUS DYNAMIQUE EN CACHANT DES INFOS ET EN LES FAISANT APPARAITRE
				//			 EN CLIQUANT SUR L'IMAGE (PAR EXEMPLE)
				tv = new TextView(this);
				tv.setText("Titre : "+objectinfo.gettitre());
				layoutContent.addView(tv);
				tv = new TextView(this);
				tv.setText("proposé par : "+proprietaire);
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
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
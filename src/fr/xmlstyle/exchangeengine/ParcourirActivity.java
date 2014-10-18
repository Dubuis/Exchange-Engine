package fr.xmlstyle.exchangeengine;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
			
			ArrayList<ObjectInfo> objectList = myXMLHandler.getObjectList();
			
			//Toast.makeText(this, objectList.toString(), Toast.LENGTH_LONG).show();
			
			LinearLayout layoutParent = (LinearLayout)findViewById(R.id.parcourir_layout);
			LinearLayout layoutContent;
			TextView tv;
			ImageView img;
			for(ObjectInfo objectinfo: objectList){
				// A FAIRE : RENDRE CA PLUS DYNAMIQUE EN CACHANT DES INFOS ET EN LES FAISANT APPARAITRE
				//			 EN CLIQUANT SUR L'IMAGE (PAR EXEMPLE)
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
				tv.setText("proposé par : "+objectinfo.getproprietaire());
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
				img = new ImageView(this);
				img.setImageURI(Uri.parse(objectinfo.geturl()));
				layoutContent.addView(img);
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
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
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
		//getMenuInflater().inflate(R.menu.menu_parcourir, menu);
		return true;
	}
	
	public void parseXML(){
		try {			
			ArrayList<ObjectInfo> objectList = ObjectXMLHandler.lecture();
			
			//Toast.makeText(this, objectList.toString(), Toast.LENGTH_LONG).show();
			
			LinearLayout layoutParent = (LinearLayout)findViewById(R.id.parcourir_layout);
			LinearLayout layoutContent;
			TextView tv;
			//ImageView img;
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
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
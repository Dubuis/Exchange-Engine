package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ParcourirActivity extends Activity{
	private boolean dejaClique = false;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parcourir);
		affichage();
		//Bouton 1
        final ImageButton bouton1 = (ImageButton) findViewById(R.id.boutonCherche);
        bouton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				affichage();
			}
	    });
        final EditText edit = (EditText)findViewById(R.id.zoneCherche);
        edit.setOnKeyListener(new OnKeyListener() {
        	
        	@Override
        	public boolean onKey(View v, int keyCode, KeyEvent event) {
        		// Si "enter" est pressé
        		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
        			affichage();
        			return true;
        		}
        		return false;
        	}
        });
        final CheckBox box = (CheckBox)findViewById(R.id.checkcategorie);
        box.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				affichage();
			}
        });
        final Spinner spin = (Spinner)findViewById(R.id.categorieRecherche);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(dejaClique){
					box.setChecked(true);
					affichage();
				}
				else dejaClique = true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInflater().inflate(R.menu.menu_parcourir, menu);
		return true;
	}
	
	public void affichage(){
		try {			
			ArrayList<ObjectInfo> objectList = ObjectXMLHandler.lecture();
			ArrayList<PersonInfo> userList = PersonXMLHandler.lecture();
			CheckBox check = (CheckBox)findViewById(R.id.checkcategorie);
			Boolean checked = !check.isChecked();
			Spinner categorie = (Spinner)findViewById(R.id.categorieRecherche);
			EditText recherche = (EditText) findViewById(R.id.zoneCherche);
			
			LinearLayout layoutParent = (LinearLayout)findViewById(R.id.parcourir_layout);
			layoutParent.removeAllViewsInLayout();
			LinearLayout layoutContent;
			TextView tv;
			for(ObjectInfo objectinfo: objectList){
				if((recherche.getText().toString().isEmpty() || objectinfo.gettitre().toLowerCase().contains(recherche.getText().toString().toLowerCase()) ) && ( checked || objectinfo.getcategorie().equals(categorie.getSelectedItem().toString()) )){
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
					tv.setText("proposé par : "+RecuperationDuProprio(userList, objectinfo.getproprietaire()));
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

	private String RecuperationDuProprio(ArrayList<PersonInfo> listUser, String mail){
		for(int i=0; i<listUser.size(); i++){
			if(listUser.get(i).getmel().equals(mail)) {
				return listUser.get(i).getnom() + " " + listUser.get(i).getprenom();
			}
		}
		return "#Anonymous";
	}
}
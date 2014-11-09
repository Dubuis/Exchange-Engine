package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MesObjetsActivity extends Activity{
	final String EXTRA_MAIL = "user_mail";
	final String EXTRA_PASSWORD = "user_password";
	final String EXTRA_NOM = "user_name";
	final String EXTRA_PRENOM = "user_prenom";
	PersonInfo currentUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mes_objets);
		/** Récupération de l'utilisateur courant **/
		Bundle extra = getIntent().getBundleExtra("extra");
		if(extra != null){
			currentUser = (PersonInfo) extra.getSerializable("Person"); 
			/** Parse et affiche **/
			afficheur();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInflater().inflate(R.menu.menu_mesobjets, menu);
		return true;
	}
	
	public void afficheur(){
		try {			
			ArrayList<ObjectInfo> objectList = ObjectXMLHandler.lecture();
			
		
			
			LinearLayout layoutParent = (LinearLayout)findViewById(R.id.mesobjets_layout);
			LinearLayout layoutContent;
			TextView tv;
			Button b;
			//ImageView img;
			 /** Boucle sur tous les objets du fichier **/
			for(int i=0;i<objectList.size();i++){
				final int j = i;
				ObjectInfo objectinfo = objectList.get(i);
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
					
					// Bouton modifier
					b = new Button(this);
					b.setText("Modifier");
					b.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(MesObjetsActivity.this, ObjetActivity.class);
							Bundle extra = new Bundle();
							extra.putInt("indice", j);
							extra.putSerializable("person", currentUser);
							intent.putExtra("extra", extra);
							startActivity(intent);
						}
					});
					layoutContent.addView(b);
					
					// Bouton supprimer
					b = new Button(this);
					b.setText("Supprimer");
					b.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							ObjectXMLHandler.supprimer(j);
							Intent intent = new Intent(MesObjetsActivity.this, MesObjetsActivity.class);
							Bundle passa = new Bundle();
							passa.putSerializable("Person", currentUser);
							intent.putExtra("extra", passa);
							startActivity(intent);
						}
					});
					layoutContent.addView(b);
					
					layoutParent.addView(layoutContent);
				}
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
}
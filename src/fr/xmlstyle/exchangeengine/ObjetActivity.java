package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ObjetActivity extends Activity {
	private int indice = 0;
	private PersonInfo currentUser;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_objet);
		Bundle extra = getIntent().getBundleExtra("extra");
		if(extra != null){
			indice = (int) extra.getInt("indice");
			currentUser = (PersonInfo) extra.getSerializable("person");
		}
		ArrayList<ObjectInfo> ObjectsList = ObjectXMLHandler.lecture();
		// Récupération des zones de texte
		final EditText titre = (EditText)findViewById(R.id.AjoutObjet_Titre);
		final EditText mail = (EditText)findViewById(R.id.AjoutObjet_Mail);
		final Spinner categorie = (Spinner)findViewById(R.id.AjoutObjet_Categorie);
		final EditText couleur = (EditText)findViewById(R.id.AjoutObjet_Couleur);
		final EditText echange = (EditText)findViewById(R.id.AjoutObjet_Description);
		final EditText telephone = (EditText)findViewById(R.id.AjoutObjet_Tel);
		final EditText url = (EditText)findViewById(R.id.AjoutObjet_Url);
		final EditText zone = (EditText)findViewById(R.id.AjoutObjet_Zone);
		// Remplissage des zones de texte
		titre.setText(ObjectsList.get(indice).gettitre());
		mail.setText(ObjectsList.get(indice).getmel());
		categorie.setSelection(trouverPosition(ObjectsList.get(indice).getcategorie()));
		couleur.setText(ObjectsList.get(indice).getcouleur());
		echange.setText(ObjectsList.get(indice).getechange());
		telephone.setText(ObjectsList.get(indice).getnumero());
		url.setText(ObjectsList.get(indice).geturl());
		zone.setText(ObjectsList.get(indice).getzone());
		
		// Bouton Enregistrer
		Button bouton = (Button)findViewById(R.id.boutonenvoyer);
		bouton.setText("Enregistrer les modifications");
		bouton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean ok = true;
				if(titre.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || categorie.getSelectedItem().toString().isEmpty() || echange.getText().toString().isEmpty() || couleur.getText().toString().isEmpty() || telephone.getText().toString().isEmpty() || zone.getText().toString().isEmpty()){
					// Tous les champs doivent être remplis
			    	Toast.makeText(ObjetActivity.this, "Tous les champs doivent être remplis !!!", Toast.LENGTH_SHORT).show();
			    	ok = false;
				}
				if(ok){
					ObjectInfo newObject = new ObjectInfo();
					newObject.setmel(mail.getText().toString());
					newObject.setcategorie(categorie.getSelectedItem().toString());
					newObject.setcouleur(couleur.getText().toString());
					newObject.setechange(echange.getText().toString());
					newObject.setnumero(telephone.getText().toString());
					newObject.setproprietaire(currentUser.getmel());
					newObject.settitre(titre.getText().toString());
					newObject.seturl(url.getText().toString());
					newObject.setzone(zone.getText().toString());
					ObjectXMLHandler.modifier(indice, newObject);
					Intent intent = new Intent(ObjetActivity.this, MesObjetsActivity.class);
					Bundle passa = new Bundle();
					passa.putSerializable("Person", currentUser);
					intent.putExtra("extra", passa);
					startActivity(intent);
				}
			}
		});
		// Ajout d'un bouton Supprimer
		/*Button bouton2 = new Button(this);
		bouton2.setText("Supprimer");
		RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		para.setLayoutDirection(LinearLayout.VERTICAL);
		para.addRule(RelativeLayout.BELOW,bouton.getId());
		bouton2.setLayoutParams(para);
		bouton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ObjectXMLHandler.supprimer(indice);
				Intent intent = new Intent(ObjetActivity.this, AccueilActivity.class);
				Bundle passa = new Bundle();
				passa.putSerializable("Person", currentUser);
				intent.putExtra("extra", passa);
				startActivity(intent);
			}
		});
		RelativeLayout parent = (RelativeLayout)findViewById(R.id.AjoutObjet_Layout);
		parent.addView(bouton2);*/
	}
	
	private int trouverPosition(String cat){
		Spinner categorie = (Spinner)findViewById(R.id.AjoutObjet_Categorie);
		for(int i = 0; i < categorie.getCount(); i++){
			 if(categorie.getItemAtPosition(i).toString().equals(cat)) return i;
		}
		return 0;
	}
}
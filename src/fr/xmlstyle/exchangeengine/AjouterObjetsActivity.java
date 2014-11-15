package fr.xmlstyle.exchangeengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AjouterObjetsActivity extends Activity{
	private PersonInfo currentUser; // Utilisateur actuellement connecté
	/** Création de la page **/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_objet);
		// Récupération de l'utilisateur courant lors de la création de l'activité
        Bundle extra = getIntent().getBundleExtra("extra");
		if(extra != null){
			currentUser = (PersonInfo) extra.getSerializable("User");
			EditText edit = (EditText)findViewById(R.id.AjoutObjet_Mail);
			edit.setText(currentUser.getmel().toString());
		}
        //Bouton "Envoyer"
        final Button bouton1 = (Button) findViewById(R.id.boutonenvoyer);
        bouton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean ok = true;
				final EditText titre = (EditText)findViewById(R.id.AjoutObjet_Titre);
				final EditText mail = (EditText)findViewById(R.id.AjoutObjet_Mail);
				final Spinner categorie = (Spinner)findViewById(R.id.AjoutObjet_Categorie);
				final EditText echange = (EditText)findViewById(R.id.AjoutObjet_Description);
				final EditText couleur = (EditText)findViewById(R.id.AjoutObjet_Couleur);
				final EditText telephone = (EditText)findViewById(R.id.AjoutObjet_Tel);
				final EditText zone = (EditText)findViewById(R.id.AjoutObjet_Zone);
				final EditText url = (EditText)findViewById(R.id.AjoutObjet_Url);
				if(titre.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || categorie.getSelectedItem().toString().isEmpty() || echange.getText().toString().isEmpty() || couleur.getText().toString().isEmpty() || telephone.getText().toString().isEmpty() || zone.getText().toString().isEmpty()){
					// Tous les champs doivent être remplis
			    	Toast.makeText(AjouterObjetsActivity.this, "Tous les champs doivent être remplis !!!", Toast.LENGTH_SHORT).show();
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
					ObjectXMLHandler.ajouter(newObject);
					// Transmission à l'activité suivante de l'utilisateur courant
					Intent intent = new Intent(AjouterObjetsActivity.this, AccueilActivity.class);
					Bundle passa = new Bundle();
					passa.putSerializable("Person", currentUser);
					intent.putExtra("extra", passa);
					// Lancement de l'activité suivante
					startActivity(intent);
				}
			}
	    });
	}
}
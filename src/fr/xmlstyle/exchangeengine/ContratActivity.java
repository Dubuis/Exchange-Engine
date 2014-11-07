package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ContratActivity extends Activity{
	private PersonInfo currentUser;
	private int nbloop = 0;
	private String rep[] = new String[8];

	/**
	 * Rep[0] -> Permier Utilisateur
	 * Rep[1] -> Adresse du Permier Utilisateur
	 * Rep[2] -> Deuxième Ustilisateur
	 * Rep[3] -> Adresse du Deuxième Utilisateur
	 * Rep[4] -> Objet du Premier Utilisateur
	 * Rep[5] -> Objet du Deuxième Utilisateur
	 * Rep[6] -> Personne Payant la Soulte (TVA)
	 * Rep[7] -> Montant de la Soulte
	 * Rep[8] -> ....**/
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creation_contrat);
		final LinearLayout parent;
		final EditText reponse = new EditText(this);
		final Button valider = new Button(this);
		final Spinner choix = new Spinner(this);
		final TextView consigne = new TextView(this);
		/** RECUPERATION DE L'UTILISATEUR COURANT **/
		Bundle extra = getIntent().getBundleExtra("extra");
		if(extra != null){
			currentUser = (PersonInfo) extra.getSerializable("User");
		}
		/** RECUPERATION DE TOUS LES UTILISATEURS **/
		final ArrayList<PersonInfo> userList = PersonXMLHandler.lecture();
		/** RECUPERATION DES OBJETS COURANTS **/
		final ArrayList<ObjectInfo> objectList = ObjectXMLHandler.lecture();
		
		/** CONSTRUCTION DE LA PAGE **/
		parent = (LinearLayout)findViewById(R.id.question_layout);
		consigne.setText("Indiquez les Nom et Prénom de la permière personne ?");
		parent.addView(consigne);
		reponse.setText(currentUser.getnom() +" "+ currentUser.getprenom());
		parent.addView(reponse);
		choix.setEnabled(false);
		choix.setVisibility(Spinner.INVISIBLE);
		choix.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				reponse.setText(choix.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		parent.addView(choix);
		valider.setText("Valider");
		valider.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(nbloop){
				case 0:
					rep[nbloop] = reponse.getText().toString(); // -> Nom 1
					/** Préparation pour récupération Adresse 1 **/
					choix.setEnabled(false);
					choix.setVisibility(Spinner.INVISIBLE);
					consigne.setText("Indiquez l'adresse de "+rep[nbloop]+".");
					reponse.setText("");
					break;
					
				case 1:
					rep[nbloop] = reponse.getText().toString(); // -> Adresse 1
					/** Préparation pour Nom 2 **/
					consigne.setText("Indiquez les Nom et Prénom de la deuxième personne ?");
					choix.setEnabled(true);
					choix.setVisibility(Spinner.VISIBLE);
					String array_spinner[] = new String[userList.size()];
					for(int i=0;i<userList.size();i++){
						array_spinner[i] = userList.get(i).getnom()+" "+userList.get(i).getprenom();
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(ContratActivity.this, android.R.layout.simple_spinner_item, array_spinner);
					choix.setAdapter(adapter);
					reponse.setText("");
					break;
					
				case 2:
					rep[nbloop] = reponse.getText().toString(); // -> Nom 2
					/** Préparation pour Adresse 2 **/
					choix.setEnabled(false);
					choix.setVisibility(Spinner.INVISIBLE);
					consigne.setText("Indiquez l'adresse de "+rep[nbloop]+".");
					reponse.setText("");
					break;
					
				case 3:
					rep[nbloop] = reponse.getText().toString(); // -> Adresse 2
					/** Préparation pour Objet 1 **/
					choix.setEnabled(true);
					choix.setVisibility(Spinner.VISIBLE);
					consigne.setText("Définissez l'objet échangé par "+rep[0]+".");
					String array_spinner1[] = new String[objectList.size()];
					for(int i=0;i<objectList.size();i++){
						array_spinner1[i] = objectList.get(i).gettitre();
					}
					ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ContratActivity.this, android.R.layout.simple_spinner_item, array_spinner1);
					choix.setAdapter(adapter1);
					reponse.setText("");
					break;
					
				case 4 :
					rep[nbloop] = reponse.getText().toString(); // -> Objet 1
					/** Préparation pour Objet 2 **/
					choix.setEnabled(true);
					choix.setVisibility(Spinner.VISIBLE);
					consigne.setText("Définissez l'objet échangé par "+rep[2]+".");
					reponse.setText("");
					break;
					
				case 5 :
					rep[nbloop] = reponse.getText().toString(); // -> Objet 2
					/** Préparation Qui paie la Soulte ? **/
					reponse.setText("");
					reponse.setEnabled(false);
					consigne.setText("Qui paie la soulte ? (somme d’argent permettant de compenser l'excédent de valeur)");
					choix.setEnabled(true);
					choix.setVisibility(Spinner.VISIBLE);
					String array_spinner2[] = new String[3];
					array_spinner2[0] = "Pas de Soulte";
					array_spinner2[1] = rep[0];
					array_spinner2[2] = rep[1];
					ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ContratActivity.this, android.R.layout.simple_spinner_item, array_spinner2);
					choix.setAdapter(adapter2);
					choix.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							reponse.setText(choix.getSelectedItem().toString());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
						}
					});
					break;
				case 6 :
					rep[nbloop] = choix.getSelectedItem().toString(); // -> SoultePayeur
					/** Préparation récupération prixSoulte **/
					if(!rep[nbloop-1].toString().equals("Pas de Soulte")){
						consigne.setText("Montant de la Soulte ?");
						reponse.setEnabled(true);
						choix.setEnabled(false);
						choix.setVisibility(Spinner.INVISIBLE);
						reponse.setText("0 €");
						break;
					}
					else {
						rep[nbloop] = "";
						nbloop++;
					}
				case 7 :
					rep[nbloop] = reponse.getText().toString(); // -> SoultePrix
					String fichier = currentUser.getnom()+Math.random();
					if(CreatorPDF.CreationContrat(fichier, rep)){
						Toast.makeText(ContratActivity.this, "Fichier : "+fichier+".pdf", Toast.LENGTH_LONG).show();
						/* On tente d'afficher le PDF.
						 * S'il n'existe pas d'activité permettant de l'afficher,
						 * on retroune à l'accueil. */
						Uri path = Uri.fromFile(CreatorPDF.getFile()); 
						Intent intent = new Intent(Intent.ACTION_VIEW); intent.setDataAndType(path, "application/pdf"); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						try { 
							startActivity(intent);
						}
						catch (ActivityNotFoundException e) { 
							Toast.makeText(ContratActivity.this, "Aucune Application Disponible pour Afficher un PDF", Toast.LENGTH_SHORT).show();
							intent = new Intent(ContratActivity.this, AccueilActivity.class);
							Bundle passa = new Bundle();
							passa.putSerializable("Person", currentUser);
							intent.putExtra("extra", passa);
							startActivity(intent);
						}
					}
					break;
				default:
					break;
				}
				nbloop++;
			}
		});
		parent.addView(valider);
	}
}
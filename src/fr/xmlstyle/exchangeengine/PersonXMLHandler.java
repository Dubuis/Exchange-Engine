package fr.xmlstyle.exchangeengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import android.os.Environment;

public class PersonXMLHandler{
	static org.jdom2.Document document;
	static Element racine;
	static String etat;
	public static void ouverture(){
		etat = Environment.getExternalStorageState();
		SAXBuilder sxb = new SAXBuilder();
		File fichier = Environment.getExternalStorageDirectory();
		fichier = new File(fichier+"/Clients.xml");
		try{
			document = sxb.build(fichier);
		}
		catch(Exception e){
		}
		racine = document.getRootElement();
	}
	
	public static ArrayList<PersonInfo> lecture(){
		ouverture();
		ArrayList<PersonInfo> listPerson = new ArrayList<PersonInfo>();
		List<Element> list = racine.getChildren("utilisateur");
		Iterator<Element> i = list.iterator();
		Element courant = null;
		while(i.hasNext()){
			courant = i.next();
			PersonInfo personC = new PersonInfo();
			personC.setmel(courant.getChild("mel").getText());
			personC.setmdp(courant.getChild("information").getChild("mdp").getText());
			personC.setnom(courant.getChild("information").getChild("nom").getText());
			personC.setprenom(courant.getChild("information").getChild("prenom").getText());
			listPerson.add(personC);
		}
		return listPerson;
	}
	
	public static boolean ajouter(PersonInfo newUser){
		ouverture();
		List<Element> list = racine.getChildren("utilisateur");
		Iterator<Element> i = list.iterator();
		Element courant = null;
		boolean exist = false;
		while(i.hasNext()){
			courant = i.next();
			if(courant.getChild("mel").getText().equals(newUser.getmel())){
				// On parcourt le fichier pour vérifier que l'adresse mail n'est pas déjà utilisée.
				exist = true;
			}
		}
		if(exist) return false;
		else{
			/** Si l'adresse mail n'est pas déjà utilisée, on rajoute l'utilisateur : **/
			//Utilisateur :
			Element utilisateur = new Element("utilisateur");
			//Mail :
			Element mail = new Element("mel");
			mail.addContent(newUser.getmel());
			utilisateur.addContent(mail);
			//Information :
			Element information = new Element("information");
			//Nom :
			Element nom = new Element("nom");
			nom.addContent(newUser.getnom());
			information.addContent(nom);
			//Prenom :
			Element prenom = new Element("prenom");
			prenom.addContent(newUser.getprenom());
			information.addContent(prenom);
			//Mot de passe :
			Element mdp = new Element("mdp");
			mdp.addContent(newUser.getmdp());
			information.addContent(mdp);
			utilisateur.addContent(information);
			racine.addContent(utilisateur);
			enregistrer();
		}
		return true;
	}
	
	private static void enregistrer(){
		File fichier = Environment.getExternalStorageDirectory();
		fichier = new File(fichier+"/Clients.xml");
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		try {
			sortie.output(document,  new FileOutputStream(fichier));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
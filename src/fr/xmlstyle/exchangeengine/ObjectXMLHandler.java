/** Cette fonction a pour but de traiter le fichier XML
 * objets.xml. On trouvera ici les fonctions d'ouverture,
 * fermetuer du fichier. La gestion du parser pour l'ajout,
 * la modification et la suppression des objets dans le
 * fichier sus-nommé.
 * **/
package fr.xmlstyle.exchangeengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import android.os.Environment;

public class ObjectXMLHandler{
	static org.jdom2.Document document;
	static String etat;
	static Element racine;
	public static void ouverture(){
		SAXBuilder sxb = new SAXBuilder();
		File fichier = Environment.getExternalStorageDirectory();
		// A AJOUTER : VERIFICATION DE L'EXISTANCE D'UN STOCKAGE EXTERNE. EN CAS D'ABSENCE DE
		// STOCKAGE EXTERNE, PREVOIR UN ENREGISTREMENT DANS LA MEMOIRE INTERNE OU UNE
		// FERMETURE DU PROGRAMME.
		fichier = new File(fichier+"/Objets.xml");
		if(!fichier.exists()){
			creerDocument();
		}
		try{
			document = sxb.build(fichier);
		}
		catch(Exception e){}
		racine = document.getRootElement();
	}
	
	public static ArrayList<ObjectInfo> lecture(){
		// On commence par ouvrir le fichier
		ouverture();
		// Puis on commence à extraire les informations
		ArrayList<ObjectInfo> listObjets = new ArrayList<ObjectInfo>();
		// la racine est la première balise (ici <diagramme></diagrammme>).
		// racine contient donc tous les objets
		List<Element> list = racine.getChildren("objet");
		Iterator<Element> i = list.iterator();
		while(i.hasNext()){
			Element courant = i.next();
			String proprietaire = courant.getChildText("proprietaire");
			List<Element> listO = courant.getChildren("information");
			Iterator<Element> j = listO.iterator();
			while(j.hasNext()){
				Element courantO = j.next();
				ObjectInfo objetC = new ObjectInfo();
				objetC.setproprietaire(proprietaire);
				objetC.setcategorie(courantO.getChildText("categorie"));
				objetC.setcouleur(courantO.getChildText("couleur"));
				objetC.setechange(courantO.getChildText("echange"));
				objetC.setmel(courantO.getChildText("mel"));
				objetC.setnumero(courantO.getChildText("numero"));
				objetC.settitre(courantO.getChildText("titre"));
				objetC.setzone(courantO.getChildText("zone"));
				objetC.seturl(courantO.getChildText("url"));
				listObjets.add(objetC);
			}
		}
		return listObjets;
	}
	
	public static boolean creerDocument(){
		// Création d'un fichier objets.xml vide (en cas d'absence du fichier)
		Element base = new Element("diagramme");
		document = new Document(base);
		enregistrer();
		return true;
	}
	
	public static boolean ajouter(ObjectInfo newObject){
		// Ajout d'un objet dans le fichier
		ouverture();
		List<Element> list = racine.getChildren("objet");
		Iterator<Element> i = list.iterator();
		Element courant = null;
		boolean proprioExist = false;
		
		Element proprietaire = new Element("proprietaire");
		proprietaire.addContent(newObject.getproprietaire());
		Element information = new Element("information");
		Element mail = new Element("mel");
		mail.addContent(newObject.getmel());
		information.addContent(mail);
		Element titre = new Element("titre");
		titre.addContent(newObject.gettitre());
		information.addContent(titre);
		Element categorie = new Element("categorie");
		categorie.addContent(newObject.getcategorie());
		information.addContent(categorie);
		Element couleur = new Element("couleur");
		couleur.addContent(newObject.getcouleur());
		information.addContent(couleur);
		Element echange = new Element("echange");
		echange.addContent(newObject.getechange());
		information.addContent(echange);
		Element zone = new Element("zone");
		zone.addContent(newObject.getzone());
		information.addContent(zone);
		Element numero = new Element("numero");
		numero.addContent(newObject.getnumero());
		information.addContent(numero);
		Element url = new Element("url");
		url.addContent(newObject.geturl());
		information.addContent(url);
		
		while(i.hasNext()){
			courant = i.next();
			if(courant.getChild("proprietaire").getText().equals(newObject.getproprietaire())){
				proprioExist = true;
				courant.addContent(information);
			}
		}
		if(!proprioExist){
			Element objet = new Element("objet");
			objet.addContent(proprietaire);
			objet.addContent(information);
			racine.addContent(objet);
		}
		enregistrer();
		return true;
	}
	
	public static boolean supprimer(int indice){
		// Suppression d'un objet dans le fichier xml à la position indice
		ouverture();
		List<Element> list = racine.getChildren("objet");
		Iterator<Element> i = list.iterator();
		int k = 0;
		while(i.hasNext()){
			Element courant = i.next();
			List<Element> listO = courant.getChildren("information");
			Iterator<Element> j = listO.iterator();
			while(j.hasNext()){
				Element courantO = j.next();
				if(k==indice){
					courantO.setName("S");
					courant.removeChild("S");
					enregistrer();
					return true;
				}
				k++;
			}
		}
		
		return false;
	}
	
	public static boolean modifier(int indice, ObjectInfo object){
		// La modification d'un objet est en fait une suppression de l'ancien objet
		// et un ajout de l'objet une fois modifié
		if(supprimer(indice))
			ajouter(object);
		return false;
	}
	
	private static void enregistrer(){
		File fichier = Environment.getExternalStorageDirectory();
		fichier = new File(fichier+"/Objets.xml");
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
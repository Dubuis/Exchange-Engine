package fr.xmlstyle.exchangeengine;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatorPDF{
	private static File fichier;
	private static Document document = new Document();
	
	/** MAIN **/
	public static boolean CreationContrat(String nomFichier, String[] contenu) {
		fichier = new File(Environment.getExternalStorageDirectory()+"/"+nomFichier+".pdf");
		try{
			ouverture();
			addContrat(contenu);
			fermeture();
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
/** BOITE A OUTILS **/
	public static File getFile(){
		return fichier;
	}
	public static Document getDocument(String f){
		if(fichier.toString().isEmpty())
			return null;
		return document;
	}
	/** OUVERTURE ET FERMETURE DU DOCUMENT **/
	public static void ouverture(){
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fichier));
			document.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void fermeture(){
		document.close();
	}
/** FONCTIONS PRINCIPALES **/
	public static void addContrat(String[] contenu){
		/**
		 * contenu[0] -> Permier Utilisateur
		 * contenu[1] -> Adresse du Permier Utilisateur
		 * contenu[2] -> Deuxième Utilisateur
		 * contenu[3] -> Adresse du Deuxième Utilisateur
		 * contenu[4] -> Objet du Premier Utilisateur
		 * contenu[5] -> Objet du Deuxième Utilisateur
		 * contenu[6] -> Personne Payant la Soulte (TVA)
		 * contenu[7] -> Montant de la Soulte
		 * contenu[8] -> ....**/
		Paragraph page = new Paragraph();
		addIntroLegale(page, contenu);
		page.add(new Paragraph("Article 1 :"));
		addArticleEchange(page, contenu);
		page.add(new Paragraph("Article 2 :"));
		if(!contenu[6].equals("Pas de Soulte")){
			addArticleSoulte(page, contenu);
			page.add(new Paragraph("Article 3 :"));
		}
		addArticleTest(page, contenu);
		addEmptyLine(page,2);
		addConclusionLegale(page, contenu);
		try {
			document.add(page);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
/** LES DIFFRENTS ARTICLES A AJOUTER DANS LE CONTRAT SOUS REGROUPER ICI PAR SOUCIS DE MODULARITE **/
	private static void addIntroLegale(Paragraph page, String[] contenu){
		page.add(new Paragraph("Objet : Contrat d'échange"));
		addEmptyLine(page, 5);
		page.add(new Paragraph("Ceci est le contrat d'échange entre "+contenu[0]+", demeurant "));
		page.add(new Paragraph("-"+contenu[1]));
		page.add(new Paragraph("Et "+contenu[2]+", demeurant "));
		page.add(new Paragraph("-"+contenu[3]));
		addEmptyLine(page,1);
		page.add(new Paragraph("Il a été convenu et arrêté ce qui suit :"));
		addEmptyLine(page,1);
	}
	private static void addArticleEchange(Paragraph page, String[] contenu){
		page.add(new Paragraph(contenu[0]+" échange l'objet suivant lui appartenant : "+contenu[4]));
		page.add(new Paragraph("contre l'objet suivant appartenant a "+contenu[2]+" : "+contenu[5]));
	}
	private static void addArticleSoulte(Paragraph page, String[] contenu){
		page.add(new Paragraph(contenu[6]+" effectue, en plus du don du susnommé objet, un paiement supplémentaire de "+contenu[7]+"."));
	}
	private static void addArticleTest(Paragraph page, String[] contenu){
		page.add(new Paragraph("Chaque partie au contrat a examiné préalablement l'objet de l'autre et a considéré que les termes du contrat étaient valables."));
		page.add(new Paragraph("L'échange a donc lieu à l'exclusion de toute garantie pour les deux parties au contrat."));
	}
	private static void addConclusionLegale(Paragraph page, String[] contenu){
		page.add(new Paragraph("A                    , Le      /      /        ."));
		addEmptyLine(page, 2);
		page.add(new Paragraph("Signatures précédé de la mention \"lu et approuvé\" : "));
		page.add(new Paragraph("       "+contenu[0]+"                            "+contenu[2]));
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
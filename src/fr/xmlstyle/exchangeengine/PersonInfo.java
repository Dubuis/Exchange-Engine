package fr.xmlstyle.exchangeengine;

import java.io.Serializable;


public class PersonInfo implements Serializable {
	private static final long serialVersionUID = -5037004815070274329L;
	private String mel;
	private String prenom;
	private String nom;
	private String mdp;
	
	/************* Definition Setter Methods *********/
	public void setprenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void setnom(String nom){
		this.nom = nom;
	}
	
	public void setmel(String mel) {
		this.mel = mel;
	}
	
	public void setmdp(String mdp) {
		this.mdp = mdp;
	}
	
	/************* Definition Getter Methods *********/
	public String getprenom() {
		return this.prenom;
	}
	
	public String getnom(){
		return this.nom;
	}
	
	public String getmel() {
		return this.mel;
	}
	
	public String getmdp() {
		return this.mdp;
	}
	
	/************* Definition toString Methods ******/
	@Override
	public String toString() {
		// A modifier ultérieurement
		return "XmlModel ["+ "\nNom="
				+ nom +"\nPrenom=" + prenom + "\nmel="
				+ mel + "\n]";
	}
}
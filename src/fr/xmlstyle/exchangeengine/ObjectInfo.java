package fr.xmlstyle.exchangeengine;

public class ObjectInfo {
	
	/* objets.xml de la forme :
	 * 
	 * <diagramme>
	 * 	<objet>
	 *		<proprietaire>propritaire</proprietaire>
	 *		<information>
	 *			<mel>mel</mel>
	 *			<titre>titre</titre>
	 * 			<categorie>vehicule</categorie>
	 *			<couleur>couleur</couleur>
	 *			<echange>echange</echange>
	 *			<zone>zone</zone>
	 *			<numero>numero</numero>
	 *			<url>voiture1.png</url>
	 *		</information>
	 *		<information>
	 *			<mel>mel</mel>
	 *			<titre>titre</titre>
	 * 			<categorie>vehicule</categorie>
	 *			<couleur>couleur</couleur>
	 *			<echange>echange</echange>
	 *			<zone>zone</zone>
	 *			<numero>numero</numero>
	 *			<url>voiture1.png</url>
	 *		</information>
	 *	</objet>
	 *
	 * 	<objet>
	 * 		...
	 * 	</objet>
	 *</diagramme>
	 * 
	 */
	private String proprietaire = "";
	private String mel = "";
	private String titre = "";
	private String categorie = "";
	private String couleur = "";
	private String echange = "";
	private String zone = "";
	private String numero = "";
	private String url = "";

	/************* Definition Setter Methods *********/
	public void setproprietaire(String proprietaire){
		this.proprietaire = proprietaire;
	}
	public void setmel(String mel) {
		this.mel = mel;
	}

	public void settitre(String titre) {
		this.titre = titre;
	}

	public void setcategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setcouleur(String couleur) {
		this.couleur = couleur;
	}

	public void setechange(String echange) {
		this.echange = echange;
	}

	public void setzone(String zone) {
		this.zone = zone;
	}

	public void setnumero(String numero) {
		this.numero = numero;
	}

	public void seturl(String url) {
		this.url = url;
	}

	/************* Definition Getter Methods *********/
	public String getproprietaire(){
		return this.proprietaire;
	}
	public String getmel() {
		return this.mel;
	}

	public String gettitre() {
		return this.titre;
	}

	public String getcategorie() {
		return this.categorie;
	}

	public String getcouleur() {
		return this.couleur;
	}

	public String getechange() {
		return this.echange;
	}

	public String getzone() {
		return this.zone;
	}

	public String getnumero() {
		return this.numero;
	}

	public String geturl() {
		return this.url;
	}

	
	/************* Definition toString Methods ******/
	@Override
	public String toString() {
		// A modifier ultérieurement
		return "XmlModel ["+ "\ntitre="
				+ titre +"\nmel=" + mel + "\ntitre="
				+ titre + "\ncategorie=" + categorie + "\ncouleur=" + couleur
				+ "\nechange=" + echange + "\nzone=" + zone + "\nnumero="
				+ numero + "\nurl=" + url + "\n]";
	}

}
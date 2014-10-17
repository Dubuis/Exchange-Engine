package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ObjectXMLHandler extends DefaultHandler{
	boolean currentElement = false;
	String currentValue = "";
	
	String proprietaire ="";
	ObjectInfo objectInfo;
	ArrayList<ObjectInfo> objectList;
	
	public String getproprietaire(){
		return proprietaire;
	}
	public ArrayList<ObjectInfo> getObjectList(){
		return objectList;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		currentElement = true;
		
		if(localName.equals("diagramme")){
        }
        else if(localName.equals("object")){
        }
        else if(localName.equals("proprietaire")){
       	 objectList = new ArrayList<ObjectInfo>();
        }
        else if(localName.equals("information")){
       	 objectInfo = new ObjectInfo();
        }
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		currentElement = false;
		
		if(qName.equalsIgnoreCase("proprietaire"))
			proprietaire = currentValue.trim();
		else if(qName.equals("mel"))
			objectInfo.setmel(currentValue.trim());
		else if(qName.equals("titre"))
			objectInfo.settitre(currentValue.trim());
		else if(qName.equals("categorie"))
			objectInfo.setcategorie(currentValue.trim());
		else if(qName.equals("couleur"))
			objectInfo.setcouleur(currentValue.trim());
		else if(qName.equals("echange"))
			objectInfo.setechange(currentValue.trim());
		else if(qName.equals("zone"))
			objectInfo.setzone(currentValue.trim());
		else if(qName.equals("numero"))
			objectInfo.setnumero(currentValue.trim());
		else if(qName.equals("url"))
			objectInfo.seturl(currentValue.trim());
		else if(qName.equals("information"))
			objectList.add(objectInfo);
		
		currentValue = "";
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		if(currentElement){
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
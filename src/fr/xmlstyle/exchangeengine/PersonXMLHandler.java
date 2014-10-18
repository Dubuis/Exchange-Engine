package fr.xmlstyle.exchangeengine;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonXMLHandler extends DefaultHandler{
	boolean currentElement = false;
	String currentValue = "";
	
	PersonInfo personInfo;
	ArrayList<PersonInfo> personList;
	
	public ArrayList<PersonInfo> getPersonList(){
		return personList;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		currentElement = true;
		
		if(localName.equals("diagramme")){
         	 personList = new ArrayList<PersonInfo>();
        }
        else if(localName.equals("utilisateur")){
       	 	personInfo = new PersonInfo();
        }
        else if(localName.equals("information")){
        	
        }
	}
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		currentElement = false;
		
		if(qName.equals("mel"))
			personInfo.setmel(currentValue.trim());
		else if(qName.equals("nom"))
			personInfo.setnom(currentValue.trim());
		else if(qName.equals("prenom"))
			personInfo.setprenom(currentValue.trim());
		else if(qName.equals("mdp"))
			personInfo.setmdp(currentValue.trim());
		else if(qName.equals("information"))
			personList.add(personInfo);
		currentValue = "";
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		if(currentElement){
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
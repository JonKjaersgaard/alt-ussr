package ussr.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import java.io.*;

import java.io.*;
import java.util.ArrayList;
//SAX classes.
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
//JAXP 1.1
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*; 

//TODO 1)CONSIDER ADDING MODULE TYPE INTO XML FORMAT, FOR EXAMPLE ATRON, MTRAN AND SO ON
//2) REFACTOR
/**
 * @author Konstantinas
 *
 */
public class SaveLoad {

	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;

	/**
	 * @param simulation
	 */
	public SaveLoad(JMESimulation simulation){
		this.simulation = simulation;
	}

	/*	  public static void main (String args[]) {

		  writeXMLfile("module1s");

}*/




	public  void saveXMLfile(String fileDirectoryName){
		File newFile = new File (fileDirectoryName +".xml");
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(newFile, true));
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		StreamResult streamResult = new StreamResult(out);
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		// SAX2.0 ContentHandler.
		TransformerHandler hd = null;
		try {
			hd = tf.newTransformerHandler();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		Transformer serializer = hd.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd"); //DTD is not used here
		serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		hd.setResult(streamResult);

		//AttributesImpl moduleAtt = new AttributesImpl();
		AttributesImpl emptyAtt = new AttributesImpl();//indicate that there is no attributes 
		try {
			hd.startDocument();			
			// MODULES first tag
			hd.startElement("","","MODULES",emptyAtt);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		int amountModules = simulation.getModules().size();
		for (int module=0; module<amountModules;module++){

			Module curentModule = simulation.getModules().get(module);
			char[] moduleName = curentModule.getProperty("name").toCharArray();
			//System.out.println("moduleName: "+curentModule.getProperty("name"));//For debugging

			String moduleID =String.valueOf(curentModule.getID());
			char[] moduleRotation = curentModule.getPhysics().get(0).getRotation().toString().toCharArray();
			char[] modulePosition = curentModule.getPhysics().get(0).getPosition().toString().toCharArray();

			int amountConnectors = curentModule.getConnectors().size();

			ArrayList<String> statesConnectors = new ArrayList();
			ArrayList<String> numbersConnectors = new ArrayList();	
			for (int connector=0; connector<amountConnectors;connector++){
				boolean connectorState = curentModule.getConnectors().get(connector).isConnected();
				String connectorNumber = curentModule.getConnectors().get(connector).getProperty("ussr.connector_number");
				numbersConnectors.add(connectorNumber);
				//System.out.println("conectorNumber: "+connectorNumber);//For debugging
				if (connectorState){
					statesConnectors.add("connected");
				}else {
					statesConnectors.add("disconnected");	
				}			    	
				//System.out.println("stateConnector: "+stateConnector);//For debugging
			}

			//atts.clear();
			//atts.addAttribute("","","ID","CDATA",id[i]);
			//atts.addAttribute("","","TYPE","CDATA",type[i]);
			AttributesImpl atts1 = new AttributesImpl();
			try {
				//moduleAtt.addAttribute("","","ID","SOME",moduleID);
				hd.startElement("","","MODULE",emptyAtt);

				hd.startElement("","","ID",emptyAtt);
				hd.characters(moduleID.toCharArray(),0,moduleID.toCharArray().length);
				hd.endElement("","","ID");

				hd.startElement("","","NAME",emptyAtt);
				hd.characters(moduleName,0,moduleName.length);
				hd.endElement("","","NAME");

				//hd.startElement("","","ID",atts);
				//hd.characters(moduleID,0,moduleID.length);
				//hd.endElement("","","ID");
				hd.startElement("","","ROTATION",emptyAtt);
				hd.characters(moduleRotation,0,moduleRotation.length);
				hd.endElement("","","ROTATION");
				hd.startElement("","","POSITION",emptyAtt);			
				hd.characters(modulePosition,0,modulePosition.length);
				hd.endElement("","","POSITION");
				//hd.startElement("","","CONNECTORS",emptyAtt);
				for (int index=0; index<statesConnectors.size();index++){				
					atts1.addAttribute("","","NUMBER","SOME",numbersConnectors.get(index));
					hd.startElement("","","CONNECTOR",atts1);
					char[] state = statesConnectors.get(index).toCharArray(); 
					hd.characters(state,0,state.length);
					hd.endElement("","","CONNECTOR");
				}	
				//hd.endElement("","","CONNECTORS");
				hd.endElement("","","MODULE");
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		  
		}
		try {
			//MODULES end tag
			hd.endElement("","","MODULES");
			hd.endDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public  void loadXMLfile(String fileDirectoryName){

		try {
			File file = new File(fileDirectoryName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
			NodeList nodeLst = doc.getElementsByTagName("MODULE");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;

					NodeList firstNmElmntLst = fstElmnt.getElementsByTagName("ID");		      
					Element firstNmElmnt = (Element) firstNmElmntLst.item(0);
					NodeList firstNm = firstNmElmnt.getChildNodes();
					System.out.println("ID"  + ((Node) firstNm.item(0)).getNodeValue()); 

					NodeList secondNmElmntLst = fstElmnt.getElementsByTagName("NAME");//Name of the module			      
					Element secondNmElmnt = (Element) secondNmElmntLst.item(0);
					NodeList secondNm = secondNmElmnt.getChildNodes();
					System.out.println("NAME: "  + ((Node) secondNm.item(0)).getNodeValue());

					NodeList thirdNmElmntLst = fstElmnt.getElementsByTagName("ROTATION");
					Element thirdNmElmnt = (Element) thirdNmElmntLst.item(0);
					NodeList thirdNm = thirdNmElmnt.getChildNodes();
					System.out.println("ROTATION:" + ((Node) thirdNm.item(0)).getNodeValue());

					NodeList fourthNmElmntLst = fstElmnt.getElementsByTagName("POSITION");
					Element fourthNmElmnt = (Element) fourthNmElmntLst.item(0);
					NodeList fourthNm = fourthNmElmnt.getChildNodes();
					System.out.println("POSITION" + ((Node) fourthNm.item(0)).getNodeValue());



					NodeList fifthNmElmntLst = fstElmnt.getElementsByTagName("CONNECTOR");
					int amountConnectorNodes = fifthNmElmntLst.getLength();
					System.out.println("amountConnectorNodes:"+amountConnectorNodes );

					for(int con=0; con<amountConnectorNodes; con++){

						Element currentElement =(Element)fifthNmElmntLst.item(con);
						NodeList currentNumber = currentElement.getChildNodes();
						System.out.println("CONNECTOR NAME=" +currentElement.getAttributes().item(0).getNodeValue()+" state:"+ ((Node) currentNumber.item(0)).getNodeValue());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

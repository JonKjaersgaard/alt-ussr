package ussr.builder;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

import java.util.ArrayList;
import java.util.LinkedList;
//SAX classes.
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.jme.math.Quaternion;
//JAXP 1.1
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*; 

//TODO 1) REFACTOR
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
	 * Constructor
	 * @param simulation, the physical simulation
	 */
	public SaveLoad(JMESimulation simulation){
		this.simulation = simulation;
	}

	public void saveXMLfile(String fileDirectoryName){
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
           
			Module currentModule = simulation.getModules().get(module);
			if (currentModule.getProperty("ussr.module.deletionFlag")==null){//means it is not deleted			
			
				String typeOfModule = currentModule.getProperty("ussr.module.type");
			char[] moduleType = typeOfModule.toCharArray();
			char[] moduleName = currentModule.getProperty("name").toCharArray();
			//System.out.println("moduleName: "+curentModule.getProperty("name"));//For debugging			
				
			String moduleID =String.valueOf(currentModule.getID());
			char[] moduleRotation = currentModule.getPhysics().get(0).getRotation().toString().toCharArray();
			
			String moduleRotationQuat = currentModule.getPhysics().get(0).getRotation().getRotation().toString();
			moduleRotationQuat = moduleRotationQuat.replace("com.jme.math.Quaternion:", "");			
			char[] moduleRotationQuaternion= moduleRotationQuat.toCharArray();			

//TODO STILL PRECISION PROBLEM WITH MTRAN
			char[] modulePosition;
			if(typeOfModule.equalsIgnoreCase("MTRAN")){
			modulePosition = currentModule.getPhysics().get(1).getPosition().toString().toCharArray();			
			}else {
				modulePosition = currentModule.getPhysics().get(0).getPosition().toString().toCharArray();			
			}			
			
			/*String modulePositionVect = null; 
			if(typeOfModule.equalsIgnoreCase("MTRAN")){
				modulePositionVect = currentModule.getPhysics().get(1).getPosition().getVector().toString();			
				}else {
					modulePosition = currentModule.getPhysics().get(0).getPosition().toString().toCharArray();			
				}			
			modulePositionVect = modulePositionVect.replace("com.jme.math.Vector3f", "");						
			char[] modulePositionVector = modulePositionVect.toCharArray();*/
			
			
           	int amountComponents = currentModule.getNumberOfComponents();            
           	char[] nrComponents =(""+amountComponents).toCharArray(); 
           	    
           	String colorsComponents= "";
           	
			for (int component=0; component<amountComponents;component++){
				colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getRed()+",";//For debugging
				colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getGreen()+",";
				colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getBlue()+";";
			}	
			
			char[] colorsOfComponents = colorsComponents.toCharArray();
			//System.out.println("colors: "+ colorsComponents);//For debugging		    			
			
			int amountConnectors = currentModule.getConnectors().size();
			char[] nrConnectors = (""+amountConnectors).toCharArray(); 
			
			String colorsConnectors = "";
			
			for (int connector=0; connector<amountConnectors;connector++){
				colorsConnectors= colorsConnectors + currentModule.getConnectors().get(connector).getColor().getRed()+",";//For debugging
				colorsConnectors = colorsConnectors + currentModule.getConnectors().get(connector).getColor().getGreen()+",";
				colorsConnectors = colorsConnectors + currentModule.getConnectors().get(connector).getColor().getBlue()+";";
			}	
			
			
			char[] colorsOfConnectors = colorsConnectors.toCharArray();
			
			

			ArrayList<String> statesConnectors = new ArrayList<String>();
			ArrayList<String> numbersConnectors = new ArrayList<String>();	
			for (int connector=0; connector<amountConnectors;connector++){
				boolean connectorState = currentModule.getConnectors().get(connector).isConnected();
				String connectorNumber = currentModule.getConnectors().get(connector).getProperty("ussr.connector_number");
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
				
				hd.startElement("","","TYPE",emptyAtt);
				hd.characters(moduleType,0,moduleType.length);
				hd.endElement("","","TYPE");

				hd.startElement("","","NAME",emptyAtt);
				hd.characters(moduleName,0,moduleName.length);
				hd.endElement("","","NAME");

				//hd.startElement("","","ID",atts);
				//hd.characters(moduleID,0,moduleID.length);
				//hd.endElement("","","ID");
				hd.startElement("","","ROTATION",emptyAtt);
				hd.characters(moduleRotation,0,moduleRotation.length);
				hd.endElement("","","ROTATION");
				
				hd.startElement("","","ROTATION_QUATERNION",emptyAtt);
				hd.characters(moduleRotationQuaternion,0,moduleRotationQuaternion.length);
				hd.endElement("","","ROTATION_QUATERNION");
				
				hd.startElement("","","POSITION",emptyAtt);			
				hd.characters(modulePosition,0,modulePosition.length);
				hd.endElement("","","POSITION");
				
			/*	hd.startElement("","","POSITION_VECTOR",emptyAtt);			
				hd.characters(modulePositionVector,0,modulePositionVector.length);
				hd.endElement("","","POSITION_VECTOR");*/
				
				hd.startElement("","","COMPONENTS",emptyAtt);			
				hd.characters(nrComponents,0,nrComponents.length);
				hd.endElement("","","COMPONENTS");
				
				hd.startElement("","","COLORS_COMPONENTS",emptyAtt);			
				hd.characters(colorsOfComponents,0,colorsOfComponents.length);
				hd.endElement("","","COLORS_COMPONENTS");
				
				hd.startElement("","","CONNECTORS",emptyAtt);			
				hd.characters(nrConnectors,0,nrConnectors.length);
				hd.endElement("","","CONNECTORS");				
				
				hd.startElement("","","COLORS_CONNECTORS",emptyAtt);			
				hd.characters(colorsOfConnectors,0,colorsOfConnectors.length);
				hd.endElement("","","COLORS_CONNECTORS");
				
				
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
					
					NodeList secondNmElmntLst = fstElmnt.getElementsByTagName("TYPE");//type of the module			      
					Element secondNmElmnt = (Element) secondNmElmntLst.item(0);
					NodeList secondNm = secondNmElmnt.getChildNodes();
					System.out.println("TYPE: "  + ((Node) secondNm.item(0)).getNodeValue());
					String moduleType = secondNm.item(0).getNodeValue();

					NodeList thirdNmElmntLst = fstElmnt.getElementsByTagName("NAME");//Name of the module			      
					Element thirdNmElmnt = (Element) thirdNmElmntLst.item(0);
					NodeList thirdNm = thirdNmElmnt.getChildNodes();
					System.out.println("NAME: "  + ((Node) thirdNm.item(0)).getNodeValue());
					String moduleName = thirdNm.item(0).getNodeValue();

					NodeList fourthNmElmntLst = fstElmnt.getElementsByTagName("ROTATION");
					Element fourthNmElmnt = (Element) fourthNmElmntLst.item(0);
					NodeList fourthNm = fourthNmElmnt.getChildNodes();
					System.out.println("ROTATION:" + ((Node) fourthNm.item(0)).getNodeValue());
					String moduleRotation = fourthNm.item(0).getNodeValue();				
					
					System.out.println("Rotation value of X:" + extract(moduleRotation, "X"));
					System.out.println("Rotation value of Y:" + extract(moduleRotation, "Y"));
					System.out.println("Rotation value of Z:" + extract(moduleRotation, "Z"));
					
					NodeList fourth1NmElmntLst = fstElmnt.getElementsByTagName("ROTATION_QUATERNION");
					Element fourth1NmElmnt = (Element) fourth1NmElmntLst.item(0);
					NodeList fourth1Nm = fourth1NmElmnt.getChildNodes();
					System.out.println("ROTATION_QUATERNION:" + ((Node) fourth1Nm.item(0)).getNodeValue());
					String moduleRotationQuaternion = fourth1Nm.item(0).getNodeValue();
					
					
					
					NodeList fifthNmElmntLst = fstElmnt.getElementsByTagName("POSITION");
					Element fifthNmElmnt = (Element) fifthNmElmntLst.item(0);
					NodeList fifthNm = fifthNmElmnt.getChildNodes();
					System.out.println("POSITION" + ((Node) fifthNm.item(0)).getNodeValue());
					String modulePosition = fifthNm.item(0).getNodeValue();
					
				/*	NodeList fifth1NmElmntLst = fstElmnt.getElementsByTagName("POSITION_VECTOR");
					Element fifth1NmElmnt = (Element) fifth1NmElmntLst.item(0);
					NodeList fifth1Nm = fifth1NmElmnt.getChildNodes();
					System.out.println("POSITION_VECTOR" + ((Node) fifth1Nm.item(0)).getNodeValue());
					String modulePositionVector = fifth1Nm.item(0).getNodeValue();*/
					
					
					System.out.println("Position value of X:" + extract(modulePosition, "X"));
					System.out.println("Position value of Y:" + extract(modulePosition, "Y"));
					System.out.println("Position value of Z:" + extract(modulePosition, "Z"));
					

					NodeList sixthNmElmntLst = fstElmnt.getElementsByTagName("COMPONENTS");
					Element sixthNmElmnt = (Element) sixthNmElmntLst.item(0);
					NodeList sixthNm = sixthNmElmnt.getChildNodes();
					System.out.println("COMPONENTS" + ((Node) sixthNm.item(0)).getNodeValue());
					int amountComponents = Integer.parseInt(sixthNm.item(0).getNodeValue());
					
					NodeList seventhNmElmntLst = fstElmnt.getElementsByTagName("COLORS_COMPONENTS");
					Element seventhNmElmnt = (Element) seventhNmElmntLst.item(0);
					NodeList seventhNm = seventhNmElmnt.getChildNodes();
					System.out.println("COLORS_COMPONENTS" + ((Node) seventhNm.item(0)).getNodeValue());
					String colorsComponents = seventhNm.item(0).getNodeValue();
					
					String[] newColorsComponents = colorsComponents.split(";");
					LinkedList<Color> listColorsComponents = new LinkedList<Color>();
					for (int component=0;component<amountComponents;component++){
						String[] specificColors = newColorsComponents[component].split(",");
						int red =  Integer.parseInt(specificColors[0]);
						int green =  Integer.parseInt(specificColors[1]);
						int blue =  Integer.parseInt(specificColors[2]);
						Color  newColor = new Color(red,green,blue);
						listColorsComponents.add(newColor); 
					}
					
					NodeList eighthNmElmntLst = fstElmnt.getElementsByTagName("CONNECTORS");
					Element eighthNmElmnt = (Element) eighthNmElmntLst.item(0);
					NodeList eighthNm = eighthNmElmnt.getChildNodes();
					System.out.println("CONNECTORS" + ((Node) eighthNm.item(0)).getNodeValue());
					int amountConnectors = Integer.parseInt(eighthNm.item(0).getNodeValue());
					
					NodeList tenthNmElmntLst = fstElmnt.getElementsByTagName("COLORS_CONNECTORS");
					Element tenthNmElmnt = (Element) tenthNmElmntLst.item(0);
					NodeList tenthNm = tenthNmElmnt.getChildNodes();
					System.out.println("COLORS_CONNECTORS" + ((Node) tenthNm.item(0)).getNodeValue());
					String colorsConnectors = tenthNm.item(0).getNodeValue();					
					
					String[] newColorsConnectors= colorsConnectors.split(";");
					LinkedList<Color> listColorsConnectors= new LinkedList<Color>();
					for (int connector=0;connector<amountConnectors;connector++){
						String[] specificColors = newColorsConnectors[connector].split(",");
						int red =  Integer.parseInt(specificColors[0]);
						int green =  Integer.parseInt(specificColors[1]);
						int blue =  Integer.parseInt(specificColors[2]);
						Color  newColor = new Color(red,green,blue);
						listColorsConnectors.add(newColor); 
					}
					
					RotationDescription nd = new RotationDescription();
					nd.setRotation(new Quaternion(extractQuat(moduleRotationQuaternion,"X"),extractQuat(moduleRotationQuaternion,"Y"),extractQuat(moduleRotationQuaternion,"Z"),extractQuat(moduleRotationQuaternion,"W")));
					
				/*	VectorDescription vd = new VectorDescription();
					vd.set(new Vector3f(extractVector(modulePositionVector,"X"),extractVector(modulePositionVector,"Y"),extractVector(modulePositionVector,"Z")));
				*/	
					/*Solution1*///createNewModule(simulation,moduleName,moduleType,vd/*new VectorDescription(extract(modulePosition, "X"),extract(modulePosition, "Y"),extract(modulePosition, "Z"))*/,nd ,listColorsComponents,listColorsConnectors);
					/*Solution2*/createNewModule(simulation,moduleName,moduleType,new VectorDescription(extract(modulePosition, "X"),extract(modulePosition, "Y"),extract(modulePosition, "Z")),nd ,listColorsComponents,listColorsConnectors);
					
				/*	NodeList sixthNmElmntLst = fstElmnt.getElementsByTagName("CONNECTOR");
					int amountConnectorNodes = sixthNmElmntLst.getLength();
					System.out.println("amountConnectorNodes:"+amountConnectorNodes );

					for(int con=0; con<amountConnectorNodes; con++){

						Element currentElement =(Element)sixthNmElmntLst.item(con);
						NodeList currentNumber = currentElement.getChildNodes();
						System.out.println("CONNECTOR NAME=" +currentElement.getAttributes().item(0).getNodeValue()+" state:"+ ((Node) currentNumber.item(0)).getNodeValue());
					}*/
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createNewModule(JMESimulation simulation, String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors){
		Module newModule;
		if (moduleType.equalsIgnoreCase("ATRON")){
			ModulePosition modulePos= new ModulePosition(moduleName,"default",modulePosition,moduleRotation);
			newModule = simulation.createModule(modulePos,true);
			
		}else{
		
		ModulePosition modulePos= new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation);
		newModule = simulation.createModule(modulePos,true);}
		
		int newModuleID = newModule.getID();		
		simulation.getModules().get(newModuleID).setColorList(listColorsComponents);
		
		int amountConnentors  = simulation.getModules().get(newModuleID).getConnectors().size();
		
		for (int connector =0; connector< amountConnentors; connector++ ){
			simulation.getModules().get(newModuleID).getConnectors().get(connector).setColor(listColorsConnectors.get(connector));
		}
		
		
		
		//newModule.setColorList(colorsComponents);		
		//setColorsConnectors(simulation,newModule.getID(),colorsConnectors);		
	}
	
	private float extract(String textString, String coordinate){
		
		String cleanedTextString1 =textString.replace("(", "");
		String cleanedTextString2 =cleanedTextString1.replace(")", "");
		String[] temporary = cleanedTextString2.split(",");		
		
		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){
			
			System.out.println("End string:");
//FIXME STRANGE THING WITH PRECISION OF ONE OF ATRON ROTATIONS
		/*	if (temporary[0].contains("E")){
				String[] newTemp = temporary[0].split("E-");
				int amountOfZeros = Integer.parseInt(newTemp[1]);
				
				System.out.println("amountOfZeros: " +amountOfZeros);
				
				String zeros = "0.";
				for (int number =0; number<amountOfZeros-1; number++){
					zeros = zeros +"0"; 
					
		     	}
				System.out.println("End string: " +zeros);
				
				String oneMoreTemp = newTemp[0].replace(".", "");
				extractedValue = Float.parseFloat(zeros + oneMoreTemp);
				System.out.println("Float: " + extractedValue);
				 
			
				
				}else{*/
					extractedValue = Float.parseFloat(temporary[0]);
					System.out.println(""+extractedValue);
				//}
		
		
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);
			//System.out.println(""+extractedValue);
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
			//System.out.println(""+extractedValue);
		}else throw new Error ("There is no such coordinate");
		
		return extractedValue; 
	}
	
	public float extractQuat(String textString, String coordinate){
		//String cleanedTextString1 =textString.replace("[", "");
		textString =textString.replace("]", "");
		textString =textString.replace("x", "");
		textString =textString.replace("y", "");
		textString =textString.replace("z", "");
		textString =textString.replace("w", "");
		textString =textString.replace("=", ",");
		System.out.println("Text"+textString);	
		
		String[] temporary = textString.split(",");
		
		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){			
					extractedValue = Float.parseFloat(temporary[1]);
					//System.out.println(""+extractedValue);		
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[2]);
			//System.out.println(""+extractedValue);
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[3]);
			//System.out.println(""+extractedValue);
		}else if (coordinate.equalsIgnoreCase("W")){
			extractedValue = Float.parseFloat(temporary[4]);
			//System.out.println(""+extractedValue);
		}else throw new Error ("There is no such coordinate");
		
		return extractedValue; 
	}
	
	public float extractVector(String textString, String coordinate){
		//String cleanedTextString1 =textString.replace("[", "");
		textString =textString.replace("]", "");
		textString =textString.replace("[", "");
		textString =textString.replace("X", "");
		textString =textString.replace("Y", "");
		textString =textString.replace("Z", "");	
		textString =textString.replace("=", "");
		textString =textString+",";
		System.out.println("Text"+textString);	
		
		String[] temporary = textString.split(",");
		
		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){			
					extractedValue = Float.parseFloat(temporary[0]);
					//System.out.println(""+extractedValue);		
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);
			//System.out.println(""+extractedValue);
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
			//System.out.println(""+extractedValue);
		}else throw new Error ("There is no such coordinate");
		
		return extractedValue; 
	}
	


}

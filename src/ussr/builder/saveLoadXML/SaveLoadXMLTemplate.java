package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import ussr.builder.BuilderHelper;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;

/**
 * @author Konstantinas
 *
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
//  3) MORE REFACTORING  
public abstract class SaveLoadXMLTemplate implements SaveLoadXMLFileTemplate {

	/**
	 * The physical simulation
	 */
	protected JMESimulation simulation;

	public final static AttributesImpl emptyAtt = new AttributesImpl();

	public SaveLoadXMLTemplate(JMESimulation simulation){
		this.simulation = simulation;
	}


	/**
	 * TEMPLATE method in Template design pattern.
	 * Saves the data about simulation in chosen XML format file.
	 * @param fileDirectoryName, the name of directory, like for example: "C:/newXMLfile". 
	 */
	public void saveXMLfile(String fileDirectoryName) {
		TransformerHandler transformerHandler = initializeTransformer(fileDirectoryName);
		printOutXML(transformerHandler);
	}

	/**
	 * TEMPLATE method in Template design pattern. 
	 * Loads the data about simulation from chosen XML file into simulation.
	 * @param fileDirectoryName, the name of directory, like for example: "C:/newXMLfile".	 
	 */
	public void loadXMLfile(String fileDirectoryName){
		Document document = initializeDocument(fileDirectoryName);
		loadInXML(document);	
	}

	/**
	 * Initialises SAX 2.0 content handler and assigns it to newly created XML file.
	 * @param fileDirectoryName,the name of directory, like for example: "C:/newXMLfile".
	 * @return transformerHandler, the content handler used to print out XML format. 
	 */
	private TransformerHandler initializeTransformer(String fileDirectoryName) {
		File newFile = new File (fileDirectoryName +".xml");
		BufferedWriter characterWriter = null;
		try {
			characterWriter = new BufferedWriter(new FileWriter(newFile, true));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		StreamResult streamResult = new StreamResult(characterWriter);
		SAXTransformerFactory transformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		/* SAX2.0 ContentHandler*/
		TransformerHandler transformerHandler = null;
		try {
			transformerHandler = transformerFactory.newTransformerHandler();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		Transformer serializer = transformerHandler.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");		
		serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformerHandler.setResult(streamResult);		
		return transformerHandler;
	}

	/**
	 * COMMENT
	 * @param fileDirectoryName,the name of directory, like for example: "C:/newXMLfile".
	 * @return
	 */
	private Document initializeDocument(String fileDirectoryName) {
		File file = new File(fileDirectoryName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;			
	}

	/**
	 * Abstract method for defining the format of XML to print into the xml file. In other words
	 * what to save in the file about simulation.  
	 * @param transformerHandler,the content handler used to print out XML format. 
	 */
	public abstract void printOutXML(TransformerHandler transformerHandler);

	/**
	 * COMMENT
	 * @param document, 
	 */
	public abstract void loadInXML(Document document);

	/**
	 * Prints out the first start tag in the hierarchy of XML file. Now for example this is "<MODULES>".
	 * @param transformerHandler,the content handler used to print out XML format. 
	 * @param firstStartTagName, the name of the tag.
	 */
	public void printFirstStartTag(TransformerHandler transformerHandler, String firstStartTagName){
		try {
			transformerHandler.startDocument();			
			transformerHandler.startElement("","",firstStartTagName,emptyAtt);
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints out the first end tag in the hierarchy of XML file(from bottom). Now for example this 
	 * is "</MODULES>".
	 * @param transformerHandler,the content handler used to print out XML format. 
	 * @param firstEndTagName, the  name of the tag.
	 */
	public void printFirstEndTag(TransformerHandler transformerHandler, String firstEndTagName){
		try {			
			transformerHandler.endElement("","",firstEndTagName);
			transformerHandler.endDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * Prints out the start and end tags, plus the value(data) between them. This can be anything.
	 * In current version this is for example: "<ID>1</ID>" or "<TYPE>ATRON</TYPE>".
	 * @param transformerHandler,the content handler used to print out XML format. 
	 * @param tagName, the name of the tag.
	 * @param value, the value between the tags.
	 */
	public void printSubTagsWithValue(TransformerHandler transformerHandler, String tagName, char[] value){
		try {			
			transformerHandler.startElement("","",tagName,emptyAtt);
			transformerHandler.characters(value,0,value.length);
			transformerHandler.endElement("","",tagName);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * Prints out the state of connectors for the module in simulation. 
	 * @param transformerHandler, the content handler used to print out XML format. 
	 * @param statesConnectors, the states of connectors, for example "connected" or disconnected.
	 * @param numbersConnectors, the numbers of connectors, for example 1,2,3 and so on.
	 */
//	TODO REFACTOR
	public void printInfoConnectors(TransformerHandler transformerHandler,ArrayList<String> statesConnectors, ArrayList<String> numbersConnectors ){
		AttributesImpl atts1 = new AttributesImpl();
		for (int index=0; index<statesConnectors.size();index++){				
			atts1.addAttribute("","","NUMBER","first",numbersConnectors.get(index));
			try {
				transformerHandler.startElement("","","CONNECTOR",atts1);
				char[] state = statesConnectors.get(index).toCharArray(); 
				transformerHandler.characters(state,0,state.length);
				transformerHandler.endElement("","","CONNECTOR");
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
	}

	/**
	 * Returns the type of module, like for example ATRON,MTRAN and so on.
	 * @param currentModule, the module in simulation environment
	 * @return char[], the type of the module.
	 */
	public char[] getType(Module currentModule){    		
		return currentModule.getProperty(BuilderHelper.getModuleTypeKey()).toCharArray();    	
	}

	/**
	 * Returns the global ID of the module. 
	 * @param currentModule, the module in simulation environment
	 * @return char[], the global ID of the module in simulation.
	 */
	public char[] getID(Module currentModule){   	
		return String.valueOf(currentModule.getID()).toCharArray();    	
	}

	/**
	 * Returns the name of the module, which can be anything. For example: "driver","leftwheel" and so on.
	 * @param currentModule,the module in simulation environment.
	 * @return char[], the name of the module
	 */
	public char[] getName(Module currentModule){    		
		return currentModule.getProperty(BuilderHelper.getModuleNameKey()).toCharArray();    	
	}

	/**
	 * Return the rotation (RotationDescription) of the module in simulation environment, 
	 * as rotation of its component.
	 * @param currentModule,the module in simulation environment.
	 * @return char[], the rotation as RotationDescription of the module.
	 */
	public char[] getRotation(Module currentModule){    		
		return currentModule.getPhysics().get(0).getRotation().toString().toCharArray();    	
	}

	/**
	 * Returns the rotation quaternion of the module in simulation environment, as rotation of its component
	 * @param currentModule, the module in simulation environment.
	 * @return moduleRotationQuat,the rotation as quaternion of component of the module.
	 */
	public char[] getRotationQuaternion(Module currentModule){  
		String moduleRotationQuat = currentModule.getPhysics().get(0).getRotation().getRotation().toString();
		moduleRotationQuat = moduleRotationQuat.replace("com.jme.math.Quaternion:", "");
		return moduleRotationQuat.toCharArray();		
	}

	/**
	 * Returns the position(VectorDescription) of the module (component)
	 * @param currentModule, the module in simulation environment.
	 * @return modulePosition,the position(as VectorDescription) of the module (component)
	 */
	public char[] getPosition(Module currentModule){  
		char[] modulePosition;		
		if(currentModule.getProperty(BuilderHelper.getModuleTypeKey()).equalsIgnoreCase("MTRAN")){			
			modulePosition = currentModule.getPhysics().get(1).getPosition().toString().toCharArray();			
		}else {
			modulePosition = currentModule.getPhysics().get(0).getPosition().toString().toCharArray();			
		}
		return modulePosition;
	}

	/**
	 * Returns the position (Vector3f)of the module (component)
	 * @param currentModule, the module in simulation environment.
	 * @return modulePositionVect, the position (as Vector3f)of the module (component)
	 */
	public char[] getPositionVector(Module currentModule){ 
		String modulePositionVect = null; 
		if(currentModule.getProperty(BuilderHelper.getModuleTypeKey()).equalsIgnoreCase("MTRAN")){
			modulePositionVect = currentModule.getPhysics().get(1).getPosition().getVector().toString();			
		}else {
			modulePositionVect = currentModule.getPhysics().get(0).getPosition().getVector().toString();			
		}			
		modulePositionVect = modulePositionVect.replace("com.jme.math.Vector3f", "");

		return modulePositionVect.toCharArray();
	}

	/**
	 *  Returns the amount of components the module consists of.
	 * @param currentModule, the module in simulation environment.
	 * @return amountComponents, the amount of components the module consists of.
	 */
	public char[] getAmountComponents(Module currentModule){
		int amountComponents = currentModule.getNumberOfComponents();       	
		return (""+amountComponents).toCharArray();
	}

	/**
	 *  Returns the colours of the module components in RGB format.
	 * @param currentModule, the module in simulation environment.
	 * @return colorsComponents,the colours of the module components in RGB format. 
	 */
	public char[] getColorsComponents(Module currentModule){
		String colorsComponents= "";
		int amountComponents = currentModule.getNumberOfComponents();
		for (int component=0; component<amountComponents;component++){
			colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getRed()+",";//For debugging
			colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getGreen()+",";
			colorsComponents = colorsComponents + currentModule.getComponent(component).getModuleComponentColor().getBlue()+";";
		}		
		return colorsComponents.toCharArray();
	}

	/** 
	 * Returns amount of connectors on the module.
	 * @param currentModule, the module in simulation environment.
	 * @return amountConnectors, the amount of connectors on the module. 
	 */
	public char[] getAmountConnectors(Module currentModule){
		int amountConnectors = currentModule.getConnectors().size();	
		return (""+amountConnectors).toCharArray(); 
	}

	/**
	 * Returns the colours of connectors on the module.
	 * @param currentModule, the module in simulation environment.
	 * @return colorsConnectors,the colours of connectors on the module.
	 */
	public char[] getColorsConnectors(Module currentModule){
		String colorsConnectors = "";	
		int amountConnectors = currentModule.getConnectors().size();	
		for (int connector=0; connector<amountConnectors;connector++){
			colorsConnectors= colorsConnectors + currentModule.getConnectors().get(connector).getColor().getRed()+",";//For debugging
			colorsConnectors = colorsConnectors + currentModule.getConnectors().get(connector).getColor().getGreen()+",";
			colorsConnectors = colorsConnectors + currentModule.getConnectors().get(connector).getColor().getBlue()+";";
		}	 
		return colorsConnectors.toCharArray();
	}

	/**
	 * Returns states of connectors(for example:"connected" or "disconnected") or numbers of connectors of the module.
	 * @param currentModule, the module in simulation environment. 
	 * @param state, flag to indicate what should be returned. True when states of connectors should be returned.
	 * False when numbers of connectors should be returned. 
	 * @return statesConnectors or numbersConnectors. 
	 */
	public ArrayList<String> getInfoConnectors (Module currentModule, boolean state){
		int amountConnectors = currentModule.getConnectors().size();
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
		}
		if (state ==true){
			return statesConnectors;
		}else return numbersConnectors;
	}

	public String extractTagValue(Element firstElmnt,String tagName){
		NodeList firstNmElmntLst = firstElmnt.getElementsByTagName(tagName);		      
		Element firstNmElmnt = (Element) firstNmElmntLst.item(0);
		NodeList firstNm = firstNmElmnt.getChildNodes();		 
		return ((Node) firstNm.item(0)).getNodeValue();
	}

	public LinkedList<Color> extractColorsComponents(int amountComponents, String colorsComponents){
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
		return listColorsComponents;
	}


	public LinkedList<Color> extractColoursConnectors(int amountConnectors, String colorsConnectors){
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
		return listColorsConnectors;
	}	

	public float extractFromQuaternion(String textString, String coordinate){		
		textString =textString.replace("]", "");
		textString =textString.replace("x", "");
		textString =textString.replace("y", "");
		textString =textString.replace("z", "");
		textString =textString.replace("w", "");
		textString =textString.replace("=", ",");
		String[] temporary = textString.split(",");

		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){			
			extractedValue = Float.parseFloat(temporary[1]);				
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[2]);			
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[3]);			
		}else if (coordinate.equalsIgnoreCase("W")){
			extractedValue = Float.parseFloat(temporary[4]);			
		}else throw new Error ("There is no such coordinate");
		return extractedValue; 
	}




}

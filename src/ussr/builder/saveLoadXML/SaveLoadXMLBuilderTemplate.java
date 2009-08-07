package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.util.LinkedList;
import javax.xml.transform.sax.TransformerHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.jme.math.Quaternion;
import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

/**
 * This class is responsible for current definition of the XML format of saving and loading
 * for builder (QPSS).
 * @author Konstantinas
 */  
public abstract class SaveLoadXMLBuilderTemplate extends SaveLoadXMLTemplate  {

	/**
	 * The name of the first tag in XML file (first in the hierarchy).
	 */
	private final static String firstTag = "MODULES";

	/**
	 * The name of the second tag in XML file (second in the hierarchy). 
	 */
	private final static String secondTag = "MODULE";

	/**
	 * The name of ID tag (global ID of the module) in XML file (third in the hierarchy).
	 */
	private final static String idTag = "ID";

	/**
	 * The name of TYPE tag(type of the module) in XML file (third in the hierarchy).
	 */
	private final static String typeTag = "TYPE";

	/**
	 * The name of NAME tag(name of the module) in XML file (third in the hierarchy).
	 */
	private final static String nameTag = "NAME";

	/**
	 * The name of ROTATION tag(rotation of the module or component) in XML file (third in the hierarchy).
	 */
	private final static String rotationTag = "ROTATION";

	/**
	 * The name of ROTATION_QUATERNION tag(rotation of the module or component) in XML file (third in the hierarchy).
	 */
	private final static String rotationQuatTag = "ROTATION_QUATERNION";

	/**
	 * The name of POSITION tag(position of the  module or component) in XML file (third in the hierarchy).
	 */
	private final static String positionTag = "POSITION";

	/**
	 * The name of POSITION_VECTOR tag(position of the  module or component) in XML file (third in the hierarchy).
	 */
	private final static String positionVectorTag = "POSITION_VECTOR";

	/**
	 * The name of COMPONENTS tag(amount of components the module consists of) in XML file (third in the hierarchy).
	 */
	private final static String componentsTag = "COMPONENTS";

	/**
	 * The name of COLORS_COMPONENTS tag(colours of components the module consists of) in XML file (third in the hierarchy).
	 */
	private final static String coloursComponentsTag = "COLORS_COMPONENTS";
	
	/**
	 * The name of CONNECTORS tag(amount of connectors on the module) in XML file (third in the hierarchy).
	 */
	private final static String connectorsTag = "CONNECTORS";

	/**
	 * The name of COLORS_CONNECTORS tag(colours of connectors on the module) in XML file (third in the hierarchy).
	 */
	private final static String coloursConnectorsTag = "COLORS_CONNECTORS";
	
	/**
	 * The name of LABELS tag(labels of the module) in XML file (third in the hierarchy).
	 */
	private final static String labelsModuleTag = "LABELS_MODULE";

	/**
	 * The name of LABELS tag(labels of the connectors) in XML file (third in the hierarchy).
	 */
	private final static String labelsConnectorsTag = "LABELS_CONNECTORS";


	public SaveLoadXMLBuilderTemplate() {
	}

	/**
	 * Method for defining the format of XML to print into the xml file. In other words
	 * what to save in the file about simulation.  
	 * @param transformerHandler,the content handler used to print out XML format. 
	 */
	public void printOutXML(TransformerHandler transformerHandler) {		
		printFirstStartTag(transformerHandler, firstTag);
		int amountModules = numberOfSimulatedModules();
		/*For each module print out the start and end tags with relevant data*/
		for (int module=0; module<amountModules;module++){           
			Module currentModule = getModuleByIndex(module);			
				try {				
					transformerHandler.startElement("","",secondTag,EMPTY_ATT);				
					printSubTagsWithValue(transformerHandler, idTag, getID(currentModule));				
					printSubTagsWithValue(transformerHandler, typeTag, getType(currentModule));
					printSubTagsWithValue(transformerHandler, nameTag, getName(currentModule));
					printSubTagsWithValue(transformerHandler, rotationTag, getRotation(currentModule));
					printSubTagsWithValue(transformerHandler, rotationQuatTag, getRotationQuaternion(currentModule));
					printSubTagsWithValue(transformerHandler, positionTag, getPosition(currentModule));
					//printSubTagsWithValue(transformerHandler, positionVectorTag, getPositionVector(currentModule));
					printSubTagsWithValue(transformerHandler, labelsModuleTag, getLabelsModule(currentModule));
					printSubTagsWithValue(transformerHandler, componentsTag, getAmountComponents(currentModule));
					printSubTagsWithValue(transformerHandler, coloursComponentsTag, getColorsComponents(currentModule));
					printSubTagsWithValue(transformerHandler, connectorsTag, getAmountConnectors(currentModule));			
					printSubTagsWithValue(transformerHandler, coloursConnectorsTag, getColorsConnectors(currentModule));
					printSubTagsWithValue(transformerHandler,labelsConnectorsTag, getLabelsConnectors(currentModule));
					
					printInfoConnectors(transformerHandler,getInfoConnectors(currentModule, true), getInfoConnectors(currentModule, false));						
					transformerHandler.endElement("","",secondTag);
				} catch (SAXException e) {
					throw new Error ("SAX exception appeared and named as: "+ e.toString());
				}			
		}
		printFirstEndTag(transformerHandler, firstTag);		
	}

	protected abstract Module getModuleByIndex(int module);

    protected abstract int numberOfSimulatedModules();

    public void loadInXML(Document document) {
		NodeList nodeList = document.getElementsByTagName(secondTag);

		for (int node = 0; node < nodeList.getLength(); node++) {
			Node firstNode = nodeList.item(node);

			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstElmnt = (Element) firstNode;				
				//String moduleID = extractTagValue(firstElmnt,idTag);
				String moduleType = extractTagValue(firstElmnt,typeTag);
				String moduleName = extractTagValue(firstElmnt,nameTag);
				String moduleRotation = extractTagValue(firstElmnt,rotationTag);		
				String moduleRotationQuaternion = extractTagValue(firstElmnt,rotationQuatTag);
				String modulePosition = extractTagValue(firstElmnt,positionTag);
				//String modulePositionVector = extractTagValue(firstElmnt,positionVectorTag);
				String labelsModule = extractTagValue(firstElmnt,labelsModuleTag);
			/*	if (labelsModule.contains(BuilderHelper.getTempLabel())){
					labelsModule = labelsModule.replaceAll(BuilderHelper.getTempLabel(), "");
				}*/				
				
				int amountComponents = Integer.parseInt(extractTagValue(firstElmnt,componentsTag));
				String colorsComponents = extractTagValue(firstElmnt,coloursComponentsTag);				
				LinkedList<Color> listColorsComponents = extractColorsComponents(amountComponents, colorsComponents);				
				
				int amountConnectors = Integer.parseInt(extractTagValue(firstElmnt,connectorsTag));
				String colorsConnectors = extractTagValue(firstElmnt,coloursConnectorsTag);				
				LinkedList<Color> listColorsConnectors= extractColoursConnectors(amountConnectors,colorsConnectors);

				String labelsConnectors = extractTagValue(firstElmnt,labelsConnectorsTag);
				String tempLabelsConnectors[] = labelsConnectors.split(",");	
				
				RotationDescription rotationDescription = new RotationDescription();
				rotationDescription.setRotation(new Quaternion(extractFromQuaternion(moduleRotationQuaternion,"X"),extractFromQuaternion(moduleRotationQuaternion,"Y"),extractFromQuaternion(moduleRotationQuaternion,"Z"),extractFromQuaternion(moduleRotationQuaternion,"W")));

				createNewModule(moduleName,moduleType,new VectorDescription(extractFromPosition(modulePosition, "X"),extractFromPosition(modulePosition, "Y"),extractFromPosition(modulePosition, "Z")),rotationDescription ,listColorsComponents,listColorsConnectors,labelsModule,tempLabelsConnectors);
				
//FIXME IN CASE THERE IS A NEED TO EXTRACT THE STATE OF CONNECTORS
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
	}

    protected abstract void createNewModule(String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors, String labelsModule, String[] labelsConnectors);

	/**
	 * Extracts the value of specific coordinate from the string of VectorDescription.
	 * @param textString, the string  of VectorDescription. 
	 * @param coordinate, the coordinate to extract.
	 * @return the value of the coordinate.
	 */
	private float extractFromPosition(String textString, String coordinate){		
		String cleanedTextString1 =textString.replace("(", "");
		String cleanedTextString2 =cleanedTextString1.replace(")", "");
		String[] temporary = cleanedTextString2.split(",");		

		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){
			extractedValue = Float.parseFloat(temporary[0]);			
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);			
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
		}else throw new Error ("There is no such coordinate");
		return extractedValue; 
	}	

	/**
	 * Extracts the value of specific coordinate from the string of Vector3f.
	 * @param textString, the string  of Vector3f. 
	 * @param coordinate, the coordinate to extract.
	 * @return the value of the coordinate.
	 */
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

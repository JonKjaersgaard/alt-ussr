package ussr.builder.saveLoadXML;

import java.util.Map;

import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.enumerations.XMLTagsUsed;

/**
 * Supports saving and loading of data in XML format.
 * Is  based on SAX and DOM. Follows design pattern called TEMPLATE METHOD.
 * @author Konstantinas
 */
public interface SaveLoadXMLFileTemplate {
	
	/**	
	 * Saves the data about simulation in chosen XML format file.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param ussrXmlFileType, the type of XML file supported by USSR.
	 * @param fileDirectoryName, the name of directory, like for example: "C:/newXMLfile". 
	 */
	public void saveXMLfile(UssrXmlFileTypes ussrXmlFileType, String fileDirectoryName);
		
	/**  
	 * Loads the data about simulation from chosen XML file into simulation.
	 * This operation is TEMPLATE method. Operation means that it should be executed on the object.
	 * @param ussrXmlFileType, the type of XML file supported by USSR.
	 * @param fileDirectoryName, the name of directory, like for example: "C:/newXMLfile".	 
	 */
	public void loadXMLfile(UssrXmlFileTypes ussrXmlFileType, String fileDirectoryName);
	
    /**
     * Returns values of world description object taken from xml file describing simulation.
     * @return values of world description object taken from xml file describing simulation.
     */
    public Map<XMLTagsUsed, String> getSimulationWorldDescriptionValues();
	
    /**
     * Returns values of physics parameters object taken from xml file describing simulation.
	 * @return values of physics parameters object taken from xml file describing simulation.
	 */
	public Map<XMLTagsUsed, String> getSimulationPhysicsValues();
}

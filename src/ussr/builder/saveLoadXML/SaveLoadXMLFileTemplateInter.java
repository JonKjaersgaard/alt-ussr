package ussr.builder.saveLoadXML;

import java.util.Hashtable;
import java.util.Map;

import ussr.aGui.tabs.simulation.SimulationSpecification;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.enumerations.XMLTagsUsed;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;

/**
 * Supports saving and loading of data in XML format.
 * Is  based on SAX and DOM. Follows design pattern called TEMPLATE METHOD.
 * @author Konstantinas
 */
public interface SaveLoadXMLFileTemplateInter {
	
	/**
	 * The string representation of XML file extension.
	 */
	public final static String XML_EXTENSION = ".xml";
	
	/**
	 * Default directory for saving robot description xml files.
	 */
	public final static String DEFAULT_DIRECTORY_ROBOTS = "robots";
	
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
	
	 /**
     * Returns values of robot description object taken from xml file describing simulation.
     * @return values of robot description object taken from xml file describing simulation.
     */
	public  Map<XMLTagsUsed, String> getRobotDescriptionValues();
	
	public String getIdsModules();
	
	public SimulationSpecification getSimulationSpecification() ;
	
}

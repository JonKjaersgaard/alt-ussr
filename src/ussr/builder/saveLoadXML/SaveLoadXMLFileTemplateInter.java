package ussr.builder.saveLoadXML;



import ussr.builder.simulationLoader.SimulationSpecification;


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
			
		
	public SimulationSpecification getSimulationSpecification() ;
	
}

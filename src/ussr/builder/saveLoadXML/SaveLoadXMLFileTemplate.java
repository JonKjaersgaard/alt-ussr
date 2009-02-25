package ussr.builder.saveLoadXML;

import javax.xml.transform.sax.TransformerHandler;
import org.w3c.dom.Document;

/**
 * @author Konstantinas
 *
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
//  3) MORE REFACTORING   

public interface SaveLoadXMLFileTemplate {
	
	public void saveXMLfile(String fileDirectoryName);
	
	public void loadXMLfile(String fileDirectoryName);
	
	public abstract void loadInXML(Document document);	
	
	public abstract void printOutXML(TransformerHandler transformerHandler);
}

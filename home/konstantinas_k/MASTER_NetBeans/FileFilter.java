package MASTER_NetBeans;

/**
*
* @author Konstantinas
*/
public class FileFilter extends javax.swing.filechooser.FileFilter {

    /**
	* The file extension like xml, txt znd so on.
	 */
   String fileExtension;
   
   public FileFilter(String fileExtension){
       this.fileExtension =fileExtension; 
   }
           
   @Override
   public boolean accept(File f) {
       return f.isDirectory() || f.getName().toLowerCase().endsWith(fileExtension);        
   }

   @Override
   public String getDescription() {
       return "*" + fileExtension;        
   }

}


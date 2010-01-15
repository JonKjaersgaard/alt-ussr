package ussr.aGui.fileChoosing.fileDialog;


import ussr.aGui.fileChoosing.FileChoosingInter;


public interface FileDialogCustomizedInter extends FileChoosingInter  {

	
	 /**
     * todo
     */
    public final static FileDialogCustomizedInter FD_OPEN_SIMULATION = new FileDialogCustomizedOpen(null,FileDialogTypes.OPEN_SIMULATION_XML),
                                                  FD_OPEN_ROBOT =  new FileDialogCustomizedOpen(null,FileDialogTypes.OPEN_ROBOT_XML),
                                                  FD_SAVE_SIMULATION = new FileDialogCustomizedSave(null,FileDialogTypes.SAVE_SIMULATION_XML),
                                                  FD_SAVE_ROBOT = new FileDialogCustomizedSave(null,FileDialogTypes.SAVE_ROBOT_XML);
                                                  
    ;
                                             /* FC_FRAME_SAVE_SIMULATION = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_SIMULATION.getMap(),FC_XML_CONTROLLER),
                                              FC_FRAME_OPEN_ROBOT = new FileChooserCustomizedOpen(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER),
                                              FC_FRAME_SAVE_ROBOT = new FileChooserCustomizedSave(FileFilterTypes.OPEN_SAVE_ROBOT.getMap(),FC_XML_CONTROLLER)    
                                              ;*/
    public String getFileName();
    
    public String getFileDirectory();
    
    public String getTopTitle();
    
    public void setSelectedFile( String selectedFile);
	
}

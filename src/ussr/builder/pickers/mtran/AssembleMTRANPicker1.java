package ussr.builder.pickers.mtran;

import javax.swing.JOptionPane;

import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

import ussr.builder.pickers.utilities.Utilities;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 * Handles adding of randomly chosen MTRAN module from existing ones in simulation environment to the picked module.
 *  Here it is assumed that user picks(selects) the connector on MTRAN module.
 * @author Konstantinas
 *
 */
public class AssembleMTRANPicker1 extends AssembleMTRANPicker {

	/**
	 * Information about selected connector of the module in simulation environment
	 */
	private String ConnectorInfo;

	/**
	 * The connector number on  MTRAN module
	 */	
	private int connectorNumber = 1000;//just to avoid having default 0 value when the module is picked instead of connector

	/**
	 * The destination of connector on MTRAN module, for example in MTRAN connectors can be situated on one of two parts (active and passive)
	 */
	private int connectorDestination;

	/**
	 * Constructor called in  Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleMTRANPicker1(JMESimulation simulation) {
		super(simulation);
	}

	/* Handles identification of selected connector on the module
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Spatial target) {
		if(target instanceof TriMesh) {
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains("Connector")){ 
				//System.out.println("Connector: "+name);//For debugging
				this.ConnectorInfo = name;
				String [] temp = null;	         
				temp = ConnectorInfo.split("#");// Divide by #, into two parts, line describing the connector for example "Connector 1 #1"
				this.connectorNumber= Integer.parseInt(temp[1].toString());// Take only the number of connector, in above example "1" (at the end)				
				String  restOfString =  temp[0].toString();
				temp = null;// reset(empty) the array
				temp = restOfString.split(" ");
				this.connectorDestination = Integer.parseInt(temp[1].toString());                            	
				//System.out.println("connectorDestination"+ connectorDesctination);				
			}
		}
	}

	/* Handles identification of selected MTRAN module and subsequent calls to add new MTRAN module to it
	 * @see ussr.QPSS.pickers.MTRAN.AssembleMTRANPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {            
		int moduleID = component.getModel().getID();
		if (isMtran(moduleID)){	
			boolean match;
			match = checkConnectorMatch(moduleID,this.connectorNumber);
			if (match){
				Utilities u = new Utilities();
				int random = u.randomIntFromInterval(20,35);

				System.out.println("connector Nr.:"+ this.connectorNumber); //For debugging
				if (this.connectorNumber==1000){			
					//Do nothing
				}else{
					moveModuleAccording(this.connectorNumber, moduleID, random );
					this.connectorNumber =1000;//Avoid rewriting the connector value if the module is picked instead of connector
				}
			}else {
				System.out.println("There can be one of two following problems: 1)Pick the connector on the module, but not the module."); 
				System.out.println("2)There is no such connector number. Most likely the problem is in the method called pickTarget(), where the format of spatial description string is changed. Look at variable called name."); 
			}	
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an MTRAN module. The chosen picker is for MTRAN modules!");// Handle wrong user input
		}
	}
	/**
	 * Checks for match between the number of connector extracted from TriMesh(method pickTarget()) string and existing connectors of MTRAN module 
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @param connectorNumber, the picked connector number on MTRAN module
	 * @return true if pickTarget() method resulted in success with extraction of connector number from trimesh string  
	 */
	private boolean checkConnectorMatch(int pickedModuleID, int connectorNumber){

		int amountConnectors = simulation.getModules().get(pickedModuleID).getConnectors().size();

		for (int connector=0;connector<amountConnectors;connector++){
			String connectorNr = simulation.getModules().get(pickedModuleID).getConnectors().get(connector).getProperty("ussr.connector_number");
			//System.out.println("connectorNr:"+connectorNr); //For debugging
			if (Integer.parseInt(connectorNr)==connectorNumber ){
				//System.out.println("connectorNr:"+connectorNr); //For debugging
				return true;				
			}
		}		
		return false;		
	} 
}

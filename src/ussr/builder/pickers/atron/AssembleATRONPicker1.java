package ussr.builder.pickers.atron;


import javax.swing.JOptionPane;
import ussr.builder.pickers.utilities.Utilities;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;


//TODO 1)THINK ABOUT THE CASE WHEN MODULES ARE OVERLAPING, SHOULD AVOID THAT
//2)CHANGE SOLUTION TO SUPPORT DYNAMIC ADDING OF MODULES INSTEAD OF TAKING THE MODULES FROM THE ONES IN SIMULATION ENVIRONMENT 
/**
 *  Handles adding of randomly chosen ATRON module from existing ones in simulation environment to the picked  ATRON module.
 *  Here it is assumed that user picks(selects) the connector on ATRON module.
 * @author Konstantinas
 *
 */
public class AssembleATRONPicker1 extends AssembleATRONPicker {

	/**
	 * Information about selected connector of the ATRON module in simulation environment
	 */
	private String ConnectorInfo;

	/**
	 * The connector number on  ATRON module
	 */	
	private int connectorNumber = 1000;//just to avoid having default 0 value when the module is picked instead of connector

	/**
	 * The destination of connector on ATRON module, for example in ATRON connectors can be situated on one of two hemispheres
	 */
	private int connectorDestination;

	/**
	 * Constructor called in Quick Prototyping.java main window 
	 * @param simulation, the physical simulation
	 */
	public AssembleATRONPicker1(JMESimulation simulation) {
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
				this.connectorNumber= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)				
				String  restOfString =  temp[0].toString();
				temp = null;// reset(empty) the array
				temp = restOfString.split(" "); // Divide by space, into two parts, the rest of the string which is "Connector 1" in above example
				this.connectorDestination = Integer.parseInt(temp[1].toString());                            	
				//System.out.println("connectorDestination"+ connectorDestination);	//For debugging			
			}
		}

	}

	/* Handles identification of selected ATRON module and sequent calls to add new ATRON module to it
	 * @see ussr.QPSS.pickers.ATRON.AssembleATRONPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {            
		int moduleID = component.getModel().getID();

		if (isAtron(moduleID)){						
			boolean match;
			match = checkConnectorMatch(moduleID,this.connectorNumber);
			if (match){
				Utilities utilities = new Utilities();
				int randomID = utilities.randomIntFromInterval(5,24);
				//System.out.println("connector Nr.:"+ this.connectorNumber); //For debugging
				if (this.connectorNumber==1000){			
					//Do nothing
				}else{
					moveModuleAccording(this.connectorNumber, moduleID, randomID);
					this.connectorNumber =1000;//Avoid rewriting the connector value if the module is picked instead of connector
				}				
			}else {
				System.out.println("There can be one of two following problems: 1)Pick the connector on the module, but not the module."); 
				System.out.println("2)There is no such connector number. Most likely the problem is in the method called pickTarget(), where the format of spatial description string is changed. Look at variable called name."); 
			}			
		}else{
			JOptionPane.showMessageDialog(null, "This module is not an ATRON module. The chosen picker is for ATRON modules!");// Handle wrong user input
		}
	}

	/**
	 * Checks for match between the number of connector extracted from TriMesh(method pickTarget()) string and existing connectors of ATRON module 
	 * @param pickedModuleID, the ID of the module picked in simulation environment
	 * @param connectorNumber, the picked connector number on ATRON module
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

package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;

import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.Picker;

/**
 * Wrapper for a standard PhysicsSimulation allowing it to be used as a remote object.
 * (Wrapper for the simulation, used on the simulation side.)
 * 
 * Note to developers: additional methods for controlling a remote simulation can be added
 * here; RMI beginners should consider whether objects implement Serializable (and hence are
 * copied when passed as an argument to or returned from methods in this class) or should be
 * proxy objects like this one extending UnicastRemoteObject (and hence remain in the frontend
 * process when passed as argument or remain the simulator process when returned)
 * 
 * @author ups
 * @author Konstantinas, added support for controlling rendering, builder control and so on.
 */
public class RemotePhysicsSimulationImpl extends UnicastRemoteObject implements RemotePhysicsSimulation  {
    private PhysicsSimulation simulation;
    
    public RemotePhysicsSimulationImpl(PhysicsSimulation simulation) throws RemoteException {
        this.simulation = simulation;
    }

    public float getTime() throws RemoteException {
       return simulation.getTime();
    }

    public boolean isPaused() throws RemoteException {
        return simulation.isPaused();
    }
    
    public void setPause(boolean paused) throws RemoteException {
     simulation.setPause(paused);
    }

    public boolean isStopped() throws RemoteException {
        return simulation.isStopped();
    }

    public void setRobot(Robot bot) throws RemoteException {
        simulation.setRobot(bot);
    }

    public void setRobot(Robot bot, String type) throws RemoteException {
        simulation.setRobot(bot, type);
    }

    public void setWorld(WorldDescription world) throws RemoteException {
        simulation.setWorld(world);
    }

    public void start() throws RemoteException {
        simulation.start();
    }

    public void stop() throws RemoteException {
        simulation.stop();
    }
    
    public void setRealtime(boolean realtime)throws RemoteException{
    	simulation.setRealtime(realtime);
    }

	@Override
	public void setSingleStep(boolean singleStep) throws RemoteException {
		simulation.setSingleStep(singleStep);
	}


	/**
	 * Returns the object for controlling rendering of remote simulation.
	 * @return object, for controlling rendering of remote simulation. 
	 */
	public SimulationRendererControlInter getRendererControl() throws RemoteException{
		return new JMERendererControlWrapper((JMESimulation)simulation);
	}
	
	/**
	 * Returns the object for building modular robot in remote simulation.
	 * @return object, for building modular robot in remote simulation.
	 */
	public BuilderControlInter getBuilderControl()throws RemoteException{
		return new JMEBuilderControlWrapper((JMESimulation)simulation);
	}

	/**
	 * Saves the data about simulation(or only robot) in xml file.
	 * @param ussrXmlFileType, the type of xml, simulation description with reference to robot description xml file or only robot.
	 * @param fileDirectoryName, the directory to save xml file to.
	 */
	public void saveToXML(UssrXmlFileTypes ussrXmlFileType,String fileDirectoryName) throws RemoteException {
		InSimulationXMLSerializer saveXML = new InSimulationXMLSerializer((JMESimulation)simulation);
		saveXML.saveXMLfile(ussrXmlFileType, fileDirectoryName);		
	}
	
	public WorldDescription getWorldDescription()throws RemoteException{
		return ((JMESimulation) simulation).getWorldDescription();
	}

	@Override
	public WorldDescriptionControlInter getWorldDescriptionControl() throws RemoteException {
	
		return new JMEWorldDescriptionControlWrapper(((JMESimulation)simulation).getWorldDescription()) ;
	}

}

package communication.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;

import communication.filter.CommunicationContainer;

//TODO 
//1) Make printing in text area possible
//2) Create connection to JMESimulator from GUI (play, pause, and stop buttons)
//3) Extract information from simulator into GUI visualizer eg. number of modules
//4) Make customization of the size of the visualization GUI possible
//5) Create a class/interface between the GUI visualizer and the actual simulation

public class CommunicationVisualizerGUI extends JPanel implements ItemListener, Runnable {

	private JMESimulation simulation;
	private Thread visualizationThread;
	
	private static final long serialVersionUID = 1L;
	private static int numberOfModules;
	private static boolean showTransmitters;
	private static boolean showReceivers;
	private static boolean showPackets;
	private volatile boolean isSimulationRunning = false;
	private static boolean instanceFlag = false;
	
	private List<Module> modules;

	private JTextArea textArea;
	private DrawingCanvas canvas;

	private JCheckBox checkboxTransmitter;
	private JCheckBox checkboxReceiver;
	private JCheckBox checkboxPacket;

	private JLabel labelTransmitterModules;
	private JLabel labelReceiverModules;
	private JLabel labelPacketModules;
	
	private JComboBox comboboxTransmitterModules;
	private JComboBox comboboxReceiverModules;
	private JComboBox comboboxPacketModules;

	private JButton startSimulationButton;
	private JButton pauseSimulationButton;
	private JButton stopSimulationButton;

	public CommunicationVisualizerGUI(JMEBasicGraphicalSimulation simulation) {
		super(new GridLayout(4, 1));		
		this.simulation = (JMESimulation) simulation;
		visualizationThread = new Thread(this);
		modules = this.simulation.getModules();
		//showTransmitterReceiverPosition();
			
		numberOfModules = simulation.getModuleComponents().size();
		initializeComponents();
		add(createTextArea());
		add(createCanvasArea());
		add(createControllArea());
		add(createSimulationArea());
		instanceFlag = true;
		visualizationThread.start();		
	}
		
	//Consider creating the exact same loop (only the for loops) as in GenericTransmitter
	public synchronized void run() {		
		while(!isSimulationRunning) {
			try {
				System.out.println("Waiting for simulation to start...");
				wait();
				System.out.println("Simulation started...");
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			while(true) {
				for(Module m : simulation.getModules()) {
					//System.out.println("Module " + m.getID() + " has " + m.getTransmitters().size() + " transmitters");
					for(Transmitter t : m.getTransmitters()) {
						CommunicationContainer comContainer = ((GenericTransmitter) t).getCommunicationContainer();
						/*
						VectorDescription transmitterPosition = ((GenericTransmitter) t).getHardware().getPosition();
						float xPos = transmitterPosition.getX();
						float yPos = transmitterPosition.getY();
						float zPos = transmitterPosition.getZ();
						System.out.println("************************************************************************************");
						System.out.println("Module : " + m.getID());
						System.out.println("Transmitter position (x, y, z) = " + "(" + xPos + ", " + yPos + ", " + zPos + ")");
						*/
						Thread.yield();
						if(comContainer.hasMorePacketsInQueue()) {
							Packet p = comContainer.removePacketFromQueue();
							if(p != null) {
								System.out.println(comContainer.showPacketContent(p));
							}
						}						
					}
				}
				System.out.println("I am listening...");
			}						
		}
	}

	
	//private volatile Module module;
	//private volatile GenericTransmitter transmitter;
	//private volatile Receiver receiver;
	/*
	public synchronized void run() {		
		while(!isSimulationRunning) {
			try {
				System.out.println("Waiting for simulation to start...");
				wait();
				System.out.println("Simulation started...");
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			while(true) {
				for(int i = 0; i < simulation.getModules().size(); i++) {
					Module module = simulation.getModules().get(i);
					//System.out.println("Module");
					Thread.yield();
					//Thread.sleep(1000);
					for(int j = 0; j < module.getTransmitters().size(); j++) {
						GenericTransmitter transmitter = (GenericTransmitter) module.getTransmitters().get(j);
						CommunicationContainer communicationContainer = transmitter.getCommunicationContainer();
						if(communicationContainer.hasMorePacketsInQueue()) {
							System.out.println("Transmitter");
						}
						Thread.yield();
						
						for(int k = 0; k < module.getReceivers().size(); k++) {
							Receiver receiver = module.getReceivers().get(k);
							//System.out.println("Receiver");
							Thread.yield();
							if(transmitter.canSendTo(receiver) && receiver.canReceiveFrom(transmitter)) {
								System.out.println("tralala tralalala");	

							}
						}
					}
				}
				for(Module m : simulation.getModules()) {					
					for(Transmitter t : m.getTransmitters()) {
						for(Receiver r : m.getReceivers()) {
							if(t.canSendTo(r) && r.canReceiveFrom(t)) {
								CommunicationContainer comContainer = ((GenericTransmitter) t).getCommunicationContainer();
								if(comContainer.hasMorePacketsInQueue()) {
									Packet p = comContainer.removePacketFromQueue();
									if(p == null) {
										System.out.println("Packet content is zero");
										System.out.println(comContainer.showPacketContent(p));
									}
								}	
							}
						}											
					}
				}
				//System.out.println("I am listening...");
			}						
		}
	}
	*/


		
	 //Just for test - is going to be removed
	private void showTransmitterReceiverPosition() {
		Module moduleZero = modules.get(0);
		Module moduleOne = modules.get(1);
		Module moduleTwo = modules.get(2);
		Module moduleThree = modules.get(3);
		Module moduleFour = modules.get(4);
		Module moduleFive = modules.get(5);
		Module moduleSix = modules.get(6);
		
		//Module 0
		System.out.println("******************************************* Module #0 *******************************************");
		GenericTransmitter transmitterZeroModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleZero = (GenericTransmitter) moduleZero.getTransmitters().get(7);
		
		GenericReceiver receiverZeroModuleZero = (GenericReceiver) moduleZero.getReceivers().get(0);
		GenericReceiver receiverOneModuleZero = (GenericReceiver) moduleZero.getReceivers().get(1);
		GenericReceiver receiverTwoModuleZero = (GenericReceiver) moduleZero.getReceivers().get(2);
		GenericReceiver receiverThreeModuleZero = (GenericReceiver) moduleZero.getReceivers().get(3);
		GenericReceiver receiverFourModuleZero = (GenericReceiver) moduleZero.getReceivers().get(4);
		GenericReceiver receiverFiveModuleZero = (GenericReceiver) moduleZero.getReceivers().get(5);
		GenericReceiver receiverSixModuleZero = (GenericReceiver) moduleZero.getReceivers().get(6);
		GenericReceiver receiverSevenModuleZero = (GenericReceiver) moduleZero.getReceivers().get(7);
		
		VectorDescription transPosZeroModuleZero = transmitterZeroModuleZero.getHardware().getPosition();
		VectorDescription recPosZeroModuleZero = receiverZeroModuleZero.getHardware().getPosition();
		float xTransPosZeroModuleZero = transPosZeroModuleZero.getX();
		float yTransPosZeroModuleZero = transPosZeroModuleZero.getY();
		float zTransPosZeroModuleZero = transPosZeroModuleZero.getZ();
		float xRecPosZeroModuleZero = recPosZeroModuleZero.getX();
		float yRecPosZeroModuleZero = recPosZeroModuleZero.getY();
		float zRecPosZeroModuleZero = recPosZeroModuleZero.getZ();
		System.out.println("Position transmitter #0 module #0 (x, y, z) = (" + xTransPosZeroModuleZero + ", " + yTransPosZeroModuleZero + ", " + zTransPosZeroModuleZero + ")");
		System.out.println("Position receiver #0 module #0 (x, y, z) = (" + xRecPosZeroModuleZero + ", " + yRecPosZeroModuleZero + ", " + zRecPosZeroModuleZero + ")");
		
		VectorDescription transPosOneModuleZero = transmitterOneModuleZero.getHardware().getPosition();
		VectorDescription recPosOneModuleZero = receiverOneModuleZero.getHardware().getPosition();
		float xTransPosOneModuelZero = transPosOneModuleZero.getX();
		float yTransPosOneModuleZero = transPosOneModuleZero.getY();
		float zTransPosOneModuleZero = transPosOneModuleZero.getZ();
		float xRecPosOneModuleZero = recPosOneModuleZero.getX();
		float yRecPosOneModuleZero = recPosOneModuleZero.getY();
		float zRecPosOneModuleZero = recPosOneModuleZero.getZ();
		System.out.println("Position transmitter #1 module #0 (x, y, z) = (" + xTransPosOneModuelZero + ", " + yTransPosOneModuleZero + ", " + zTransPosOneModuleZero + ")");
		System.out.println("Position receiver #1 module #0 (x, y, z) = (" + xRecPosOneModuleZero + ", " + yRecPosOneModuleZero + ", " + zRecPosOneModuleZero + ")");
		
		VectorDescription transPosTwoModuleZero = transmitterTwoModuleZero.getHardware().getPosition();
		VectorDescription recPosTwoModuleZero = receiverTwoModuleZero.getHardware().getPosition();
		float xTransPosTwoModuleZero = transPosTwoModuleZero.getX();
		float yTransPosTwoModuleZero = transPosTwoModuleZero.getY();
		float zTransPosTwoModuleZero = transPosTwoModuleZero.getZ();
		float xRecPosTwoModuleZero = recPosTwoModuleZero.getX();
		float yRecPosTwoModuleZero = recPosTwoModuleZero.getY();
		float zRecPosTwoModuleZero = recPosTwoModuleZero.getZ();
		System.out.println("Position transmitter #2 module #0 (x, y, z) = (" + xTransPosTwoModuleZero + ", " + yTransPosTwoModuleZero + ", " + zTransPosTwoModuleZero + ")");
		System.out.println("Position receiver #2 module #0 (x, y, z) = (" + xRecPosTwoModuleZero + ", " + yRecPosTwoModuleZero + ", " + zRecPosTwoModuleZero + ")");
		
		VectorDescription transPosThreeModuleZero = transmitterThreeModuleZero.getHardware().getPosition();
		VectorDescription recPosThreeModuleZero = receiverThreeModuleZero.getHardware().getPosition();
		float xTransPosThreeModuleZero = transPosThreeModuleZero.getX();
		float yTransPosThreeModuleZero = transPosThreeModuleZero.getY();
		float zTransPosThreeModuleZero = transPosThreeModuleZero.getZ();
		float xRecPosThreeModuleZero = recPosThreeModuleZero.getX();
		float yRecPosThreeModuleZero = recPosThreeModuleZero.getY();
		float zRecPosThreeModuleZero = recPosThreeModuleZero.getZ();
		System.out.println("Position transmitter #3 module #0 (x, y, z) = (" + xTransPosThreeModuleZero + ", " + yTransPosThreeModuleZero + ", " + zTransPosThreeModuleZero + ")");
		System.out.println("Position receiver #3 module #0 (x, y, z) = (" + xRecPosThreeModuleZero + ", " + yRecPosThreeModuleZero + ", " + zRecPosThreeModuleZero + ")");
		
		VectorDescription transPosFourModuleZero = transmitterFourModuleZero.getHardware().getPosition();
		VectorDescription recPosFourModuleZero = receiverFourModuleZero.getHardware().getPosition();
		float xTransPosFourModuleZero = transPosFourModuleZero.getX();
		float yTransPosFourModuleZero = transPosFourModuleZero.getY();
		float zTransPosFourModuleZero = transPosFourModuleZero.getZ();
		float xRecPosFourModuleZero = recPosFourModuleZero.getX();
		float yRecPosFourModuleZero = recPosFourModuleZero.getY();
		float zRecPosFourModuleZero = recPosFourModuleZero.getZ();
		System.out.println("Position transmitter #4 module #0 (x, y, z) = (" + xTransPosFourModuleZero + ", " + yTransPosFourModuleZero + ", " + zTransPosFourModuleZero + ")");
		System.out.println("Position receiver #4 module #0 (x, y, z) = (" + xRecPosFourModuleZero + ", " + yRecPosFourModuleZero + ", " + zRecPosFourModuleZero + ")");
		
		VectorDescription transPosFiveModuleZero = transmitterFiveModuleZero.getHardware().getPosition();
		VectorDescription recPosFiveModuleZero = receiverFiveModuleZero.getHardware().getPosition();
		float xTransPosFiveModuleZero = transPosFiveModuleZero.getX();
		float yTransPosFiveModuleZero = transPosFiveModuleZero.getY();
		float zTransPosFiveModuleZero = transPosFiveModuleZero.getZ();
		float xRecPosFiveModuleZero = recPosFiveModuleZero.getX();
		float yRecPosFiveModuleZero = recPosFiveModuleZero.getY();
		float zRecPosFiveModuleZero = recPosFiveModuleZero.getZ();
		System.out.println("Position transmitter #5 module #0 (x, y, z) = (" + xTransPosFiveModuleZero + ", " + yTransPosFiveModuleZero + ", " + zTransPosFiveModuleZero + ")");
		System.out.println("Position receiver #5 module #0 (x, y, z) = (" + xRecPosFiveModuleZero + ", " + yRecPosFiveModuleZero + ", " + zRecPosFiveModuleZero + ")");
		
		VectorDescription transPosSixModuleZero = transmitterSixModuleZero.getHardware().getPosition();
		VectorDescription recPosSixModuleZero = receiverSixModuleZero.getHardware().getPosition();
		float xTransPosSixModuleZero = transPosSixModuleZero.getX();
		float yTransPosSixModuleZero = transPosSixModuleZero.getY();
		float zTransPosSixModuleZero = transPosSixModuleZero.getZ();
		float xRecPosSixModuleZero = recPosSixModuleZero.getX();
		float yRecPosSixModuleZero = recPosSixModuleZero.getY();
		float zRecPosSixModuleZero = recPosSixModuleZero.getZ();
		System.out.println("Position transmitter #6 module #0 (x, y, z) = (" + xTransPosSixModuleZero + ", " + yTransPosSixModuleZero + ", " + zTransPosSixModuleZero + ")");
		System.out.println("Position receiver #6 module #0 (x, y, z) = (" + xRecPosSixModuleZero + ", " + yRecPosSixModuleZero + ", " + zRecPosSixModuleZero + ")");
		
		VectorDescription transPosSevenModuleZero = transmitterSevenModuleZero.getHardware().getPosition();
		VectorDescription recPosSevenModuleZero = receiverSevenModuleZero.getHardware().getPosition();
		float xTransPosSevenModuleZero = transPosSevenModuleZero.getX();
		float yTransPosSevenModuleZero = transPosSevenModuleZero.getY();
		float zTransPosSevenModuleZero = transPosSevenModuleZero.getZ();
		float xRecPosSevenModuleZero = recPosSevenModuleZero.getX();
		float yRecPosSevenModuleZero = recPosSevenModuleZero.getY();
		float zRecPosSevenModuleZero = recPosSevenModuleZero.getZ();
		System.out.println("Position transmitter #7 module #0 (x, y, z) = (" + xTransPosSevenModuleZero + ", " + yTransPosSevenModuleZero + ", " + zTransPosSevenModuleZero + ")");
		System.out.println("Position receiver #7 module #0 (x, y, z) = (" + xRecPosSevenModuleZero + ", " + yRecPosSevenModuleZero + ", " + zRecPosSevenModuleZero + ")");
		System.out.println("*************************************************************************************************");
		
		//Module 1
		System.out.println("******************************************* Module #1 *******************************************");
		GenericTransmitter transmitterZeroModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleOne = (GenericTransmitter) moduleOne.getTransmitters().get(7);
		
		GenericReceiver receiverZeroModuleOne = (GenericReceiver) moduleOne.getReceivers().get(0);
		GenericReceiver receiverOneModuleOne = (GenericReceiver) moduleOne.getReceivers().get(1);
		GenericReceiver receiverTwoModuleOne = (GenericReceiver) moduleOne.getReceivers().get(2);
		GenericReceiver receiverThreeModuleOne = (GenericReceiver) moduleOne.getReceivers().get(3);
		GenericReceiver receiverFourModuleOne = (GenericReceiver) moduleOne.getReceivers().get(4);
		GenericReceiver receiverFiveModuleOne = (GenericReceiver) moduleOne.getReceivers().get(5);
		GenericReceiver receiverSixModuleOne = (GenericReceiver) moduleOne.getReceivers().get(6);
		GenericReceiver receiverSevenModuleOne = (GenericReceiver) moduleOne.getReceivers().get(7);
		
		VectorDescription transPosZeroModuleOne = transmitterZeroModuleOne.getHardware().getPosition();
		VectorDescription recPosZeroModuleOne = receiverZeroModuleOne.getHardware().getPosition();
		float xTransPosZeroModuleOne = transPosZeroModuleOne.getX();
		float yTransPosZeroModuleOne = transPosZeroModuleOne.getY();
		float zTransPosZeroModuleOne = transPosZeroModuleOne.getZ();
		float xRecPosZeroModuleOne = recPosZeroModuleOne.getX();
		float yRecPosZeroModuleOne = recPosZeroModuleOne.getY();
		float zRecPosZeroModuleOne = recPosZeroModuleOne.getZ();
		System.out.println("Position transmitter #0 module #1 (x, y, z) = (" + xTransPosZeroModuleOne + ", " + yTransPosZeroModuleOne + ", " + zTransPosZeroModuleOne + ")");
		System.out.println("Position receiver #0 module #1 (x, y, z) = (" + xRecPosZeroModuleOne + ", " + yRecPosZeroModuleOne + ", " + zRecPosZeroModuleOne + ")");
		
		VectorDescription transPosOneModuleOne = transmitterOneModuleOne.getHardware().getPosition();
		VectorDescription recPosOneModuleOne = receiverOneModuleOne.getHardware().getPosition();
		float xTransPosOneModuelOne = transPosOneModuleOne.getX();
		float yTransPosOneModuleOne = transPosOneModuleOne.getY();
		float zTransPosOneModuleOne = transPosOneModuleOne.getZ();
		float xRecPosOneModuelOne = recPosOneModuleOne.getX();
		float yRecPosOneModuleOne = recPosOneModuleOne.getY();
		float zRecPosOneModuleOne = recPosOneModuleOne.getZ();
		System.out.println("Position transmitter #1 module #1 (x, y, z) = (" + xTransPosOneModuelOne + ", " + yTransPosOneModuleOne + ", " + zTransPosOneModuleOne + ")");
		System.out.println("Position receiver #1 module #1 (x, y, z) = (" + xRecPosOneModuelOne + ", " + yRecPosOneModuleOne + ", " + zRecPosOneModuleOne + ")");
		
		VectorDescription transPosTwoModuleOne = transmitterTwoModuleOne.getHardware().getPosition();
		VectorDescription recPosTwoModuleOne = receiverTwoModuleOne.getHardware().getPosition();
		float xTransPosTwoModuleOne = transPosTwoModuleOne.getX();
		float yTransPosTwoModuleOne = transPosTwoModuleOne.getY();
		float zTransPosTwoModuleOne = transPosTwoModuleOne.getZ();
		float xRecPosTwoModuleOne = recPosTwoModuleOne.getX();
		float yRecPosTwoModuleOne = recPosTwoModuleOne.getY();
		float zRecPosTwoModuleOne = recPosTwoModuleOne.getZ();
		System.out.println("Position transmitter #2 module #1 (x, y, z) = (" + xTransPosTwoModuleOne + ", " + yTransPosTwoModuleOne + ", " + zTransPosTwoModuleOne + ")");
		System.out.println("Position receiver #2 module #1 (x, y, z) = (" + xRecPosTwoModuleOne + ", " + yRecPosTwoModuleOne + ", " + zRecPosTwoModuleOne + ")");
		
		VectorDescription transPosThreeModuleOne = transmitterThreeModuleOne.getHardware().getPosition();
		VectorDescription recPosThreeModuleOne = receiverThreeModuleOne.getHardware().getPosition();
		float xTransPosThreeModuleOne = transPosThreeModuleOne.getX();
		float yTransPosThreeModuleOne = transPosThreeModuleOne.getY();
		float zTransPosThreeModuleOne = transPosThreeModuleOne.getZ();
		float xRecPosThreeModuleOne = recPosThreeModuleOne.getX();
		float yRecPosThreeModuleOne = recPosThreeModuleOne.getY();
		float zRecPosThreeModuleOne = recPosThreeModuleOne.getZ();
		System.out.println("Position transmitter #3 module #1 (x, y, z) = (" + xTransPosThreeModuleOne + ", " + yTransPosThreeModuleOne + ", " + zTransPosThreeModuleOne + ")");
		System.out.println("Position receiver #3 module #1 (x, y, z) = (" + xRecPosThreeModuleOne + ", " + yRecPosThreeModuleOne + ", " + zRecPosThreeModuleOne + ")");
		
		VectorDescription transPosFourModuleOne = transmitterFourModuleOne.getHardware().getPosition();
		VectorDescription recPosFourModuleOne = receiverFourModuleOne.getHardware().getPosition();
		float xTransPosFourModuleOne = transPosFourModuleOne.getX();
		float yTransPosFourModuleOne = transPosFourModuleOne.getY();
		float zTransPosFourModuleOne = transPosFourModuleOne.getZ();
		float xRecPosFourModuleOne = recPosFourModuleOne.getX();
		float yRecPosFourModuleOne = recPosFourModuleOne.getY();
		float zRecPosFourModuleOne = recPosFourModuleOne.getZ();
		System.out.println("Position transmitter #4 module #1 (x, y, z) = (" + xTransPosFourModuleOne + ", " + yTransPosFourModuleOne + ", " + zTransPosFourModuleOne + ")");
		System.out.println("Position receiver #4 module #1 (x, y, z) = (" + xRecPosFourModuleOne + ", " + yRecPosFourModuleOne + ", " + zRecPosFourModuleOne + ")");
		
		VectorDescription transPosFiveModuleOne = transmitterFiveModuleOne.getHardware().getPosition();
		VectorDescription recPosFiveModuleOne = receiverFiveModuleOne.getHardware().getPosition();
		float xTransPosFiveModuleOne = transPosFiveModuleOne.getX();
		float yTransPosFiveModuleOne = transPosFiveModuleOne.getY();
		float zTransPosFiveModuleOne = transPosFiveModuleOne.getZ();
		float xRecPosFiveModuleOne = recPosFiveModuleOne.getX();
		float yRecPosFiveModuleOne = recPosFiveModuleOne.getY();
		float zRecPosFiveModuleOne = recPosFiveModuleOne.getZ();
		System.out.println("Position transmitter #5 module #1 (x, y, z) = (" + xTransPosFiveModuleOne + ", " + yTransPosFiveModuleOne + ", " + zTransPosFiveModuleOne + ")");
		System.out.println("Position receiver #5 module #1 (x, y, z) = (" + xRecPosFiveModuleOne + ", " + yRecPosFiveModuleOne + ", " + zRecPosFiveModuleOne + ")");
		
		VectorDescription transPosSixModuleOne = transmitterSixModuleOne.getHardware().getPosition();
		VectorDescription recPosSixModuleOne = receiverSixModuleOne.getHardware().getPosition();
		float xTransPosSixModuleOne = transPosSixModuleOne.getX();
		float yTransPosSixModuleOne = transPosSixModuleOne.getY();
		float zTransPosSixModuleOne = transPosSixModuleOne.getZ();
		float xRecPosSixModuleOne = recPosSixModuleOne.getX();
		float yRecPosSixModuleOne = recPosSixModuleOne.getY();
		float zRecPosSixModuleOne = recPosSixModuleOne.getZ();
		System.out.println("Position transmitter #6 module #1 (x, y, z) = (" + xTransPosSixModuleOne + ", " + yTransPosSixModuleOne + ", " + zTransPosSixModuleOne + ")");
		System.out.println("Position receiver #6 module #1 (x, y, z) = (" + xRecPosSixModuleOne + ", " + yRecPosSixModuleOne + ", " + zRecPosSixModuleOne + ")");
		
		VectorDescription transPosSevenModuleOne = transmitterSevenModuleOne.getHardware().getPosition();
		VectorDescription recPosSevenModuleOne = receiverSevenModuleOne.getHardware().getPosition();
		float xTransPosSevenModuleOne = transPosSevenModuleOne.getX();
		float yTransPosSevenModuleOne = transPosSevenModuleOne.getY();
		float zTransPosSevenModuleOne = transPosSevenModuleOne.getZ();
		float xRecPosSevenModuleOne = recPosSevenModuleOne.getX();
		float yRecPosSevenModuleOne = recPosSevenModuleOne.getY();
		float zRecPosSevenModuleOne = recPosSevenModuleOne.getZ();
		System.out.println("Position transmitter #7 module #1 (x, y, z) = (" + xTransPosSevenModuleOne + ", " + yTransPosSevenModuleOne + ", " + zTransPosSevenModuleOne + ")");
		System.out.println("Position receiver #7 module #1 (x, y, z) = (" + xRecPosSevenModuleOne + ", " + yRecPosSevenModuleOne + ", " + zRecPosSevenModuleOne + ")");
		System.out.println("*************************************************************************************************");
				
		//Module 2
		System.out.println("******************************************* Module #2 *******************************************");
		GenericTransmitter transmitterZeroModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleTwo = (GenericTransmitter) moduleTwo.getTransmitters().get(7);
		
		GenericReceiver receiverZeroModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(0);
		GenericReceiver receiverOneModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(1);
		GenericReceiver receiverTwoModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(2);
		GenericReceiver receiverThreeModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(3);
		GenericReceiver receiverFourModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(4);
		GenericReceiver receiverFiveModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(5);
		GenericReceiver receiverSixModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(6);
		GenericReceiver receiverSevenModuleTwo = (GenericReceiver) moduleTwo.getReceivers().get(7);
		
		VectorDescription transPosZeroModuleTwo = transmitterZeroModuleTwo.getHardware().getPosition();
		VectorDescription recPosZeroModuleTwo = receiverZeroModuleTwo.getHardware().getPosition();
		float xTransPosZeroModuleTwo = transPosZeroModuleTwo.getX();
		float yTransPosZeroModuleTwo = transPosZeroModuleTwo.getY();
		float zTransPosZeroModuleTwo = transPosZeroModuleTwo.getZ();
		float xRecPosZeroModuleTwo = recPosZeroModuleTwo.getX();
		float yRecPosZeroModuleTwo = recPosZeroModuleTwo.getY();
		float zRecPosZeroModuleTwo = recPosZeroModuleTwo.getZ();
		System.out.println("Position transmitter #0 module #2 (x, y, z) = (" + xTransPosZeroModuleTwo + ", " + yTransPosZeroModuleTwo + ", " + zTransPosZeroModuleTwo + ")");
		System.out.println("Position receiver #0 module #2 (x, y, z) = (" + xRecPosZeroModuleTwo + ", " + yRecPosZeroModuleTwo + ", " + zRecPosZeroModuleTwo + ")");
		
		VectorDescription transPosOneModuleTwo = transmitterOneModuleTwo.getHardware().getPosition();
		VectorDescription recPosOneModuleTwo = receiverOneModuleTwo.getHardware().getPosition();
		float xTransPosOneModuelTwo = transPosOneModuleTwo.getX();
		float yTransPosOneModuleTwo = transPosOneModuleTwo.getY();
		float zTransPosOneModuleTwo = transPosOneModuleTwo.getZ();
		float xRecPosOneModuelTwo = recPosOneModuleTwo.getX();
		float yRecPosOneModuleTwo = recPosOneModuleTwo.getY();
		float zRecPosOneModuleTwo = recPosOneModuleTwo.getZ();
		System.out.println("Position transmitter #1 module #2 (x, y, z) = (" + xTransPosOneModuelTwo + ", " + yTransPosOneModuleTwo + ", " + zTransPosOneModuleTwo + ")");
		System.out.println("Position receiver #1 module #2 (x, y, z) = (" + xRecPosOneModuelTwo + ", " + yRecPosOneModuleTwo + ", " + zRecPosOneModuleTwo + ")");
		
		VectorDescription transPosTwoModuleTwo = transmitterTwoModuleTwo.getHardware().getPosition();
		VectorDescription recPosTwoModuleTwo = receiverTwoModuleTwo.getHardware().getPosition();
		float xTransPosTwoModuleTwo = transPosTwoModuleTwo.getX();
		float yTransPosTwoModuleTwo = transPosTwoModuleTwo.getY();
		float zTransPosTwoModuleTwo = transPosTwoModuleTwo.getZ();
		float xRecPosTwoModuleTwo = recPosTwoModuleTwo.getX();
		float yRecPosTwoModuleTwo = recPosTwoModuleTwo.getY();
		float zRecPosTwoModuleTwo = recPosTwoModuleTwo.getZ();
		System.out.println("Position transmitter #2 module #2 (x, y, z) = (" + xTransPosTwoModuleTwo + ", " + yTransPosTwoModuleTwo + ", " + zTransPosTwoModuleTwo + ")");
		System.out.println("Position receiver #2 module #2 (x, y, z) = (" + xRecPosTwoModuleTwo + ", " + yRecPosTwoModuleTwo + ", " + zRecPosTwoModuleTwo + ")");
		
		VectorDescription transPosThreeModuleTwo = transmitterThreeModuleTwo.getHardware().getPosition();
		VectorDescription recPosThreeModuleTwo = receiverThreeModuleTwo.getHardware().getPosition();
		float xTransPosThreeModuleTwo = transPosThreeModuleTwo.getX();
		float yTransPosThreeModuleTwo = transPosThreeModuleTwo.getY();
		float zTransPosThreeModuleTwo = transPosThreeModuleTwo.getZ();
		float xRecPosThreeModuleTwo = recPosThreeModuleTwo.getX();
		float yRecPosThreeModuleTwo = recPosThreeModuleTwo.getY();
		float zRecPosThreeModuleTwo = recPosThreeModuleTwo.getZ();
		System.out.println("Position transmitter #3 module #2 (x, y, z) = (" + xTransPosThreeModuleTwo + ", " + yTransPosThreeModuleTwo + ", " + zTransPosThreeModuleTwo + ")");
		System.out.println("Position receiver #3 module #2 (x, y, z) = (" + xRecPosThreeModuleTwo + ", " + yRecPosThreeModuleTwo + ", " + zRecPosThreeModuleTwo + ")");
		
		VectorDescription transPosFourModuleTwo = transmitterFourModuleTwo.getHardware().getPosition();
		VectorDescription recPosFourModuleTwo = receiverFourModuleTwo.getHardware().getPosition();
		float xTransPosFourModuleTwo = transPosFourModuleTwo.getX();
		float yTransPosFourModuleTwo = transPosFourModuleTwo.getY();
		float zTransPosFourModuleTwo = transPosFourModuleTwo.getZ();
		float xRecPosFourModuleTwo = recPosFourModuleTwo.getX();
		float yRecPosFourModuleTwo = recPosFourModuleTwo.getY();
		float zRecPosFourModuleTwo = recPosFourModuleTwo.getZ();
		System.out.println("Position transmitter #4 module #2 (x, y, z) = (" + xTransPosFourModuleTwo + ", " + yTransPosFourModuleTwo + ", " + zTransPosFourModuleTwo + ")");
		System.out.println("Position receiver #4 module #2 (x, y, z) = (" + xRecPosFourModuleTwo + ", " + yRecPosFourModuleTwo + ", " + zRecPosFourModuleTwo + ")");
		
		VectorDescription transPosFiveModuleTwo = transmitterFiveModuleTwo.getHardware().getPosition();
		VectorDescription recPosFiveModuleTwo = receiverFiveModuleTwo.getHardware().getPosition();
		float xTransPosFiveModuleTwo = transPosFiveModuleTwo.getX();
		float yTransPosFiveModuleTwo = transPosFiveModuleTwo.getY();
		float zTransPosFiveModuleTwo = transPosFiveModuleTwo.getZ();
		float xRecPosFiveModuleTwo = recPosFiveModuleTwo.getX();
		float yRecPosFiveModuleTwo = recPosFiveModuleTwo.getY();
		float zRecPosFiveModuleTwo = recPosFiveModuleTwo.getZ();
		System.out.println("Position transmitter #5 module #2 (x, y, z) = (" + xTransPosFiveModuleTwo + ", " + yTransPosFiveModuleTwo + ", " + zTransPosFiveModuleTwo + ")");
		System.out.println("Position receiver #5 module #2 (x, y, z) = (" + xRecPosFiveModuleTwo + ", " + yRecPosFiveModuleTwo + ", " + zRecPosFiveModuleTwo + ")");
		
		VectorDescription transPosSixModuleTwo = transmitterSixModuleTwo.getHardware().getPosition();
		VectorDescription recPosSixModuleTwo = receiverSixModuleTwo.getHardware().getPosition();
		float xTransPosSixModuleTwo = transPosSixModuleTwo.getX();
		float yTransPosSixModuleTwo = transPosSixModuleTwo.getY();
		float zTransPosSixModuleTwo = transPosSixModuleTwo.getZ();
		float xRecPosSixModuleTwo = recPosSixModuleTwo.getX();
		float yRecPosSixModuleTwo = recPosSixModuleTwo.getY();
		float zRecPosSixModuleTwo = recPosSixModuleTwo.getZ();
		System.out.println("Position transmitter #6 module #2 (x, y, z) = (" + xTransPosSixModuleTwo + ", " + yTransPosSixModuleTwo + ", " + zTransPosSixModuleTwo + ")");
		System.out.println("Position receiver #6 module #2 (x, y, z) = (" + xRecPosSixModuleTwo + ", " + yRecPosSixModuleTwo + ", " + zRecPosSixModuleTwo + ")");
		
		VectorDescription transPosSevenModuleTwo = transmitterSevenModuleTwo.getHardware().getPosition();
		VectorDescription recPosSevenModuleTwo = receiverSevenModuleTwo.getHardware().getPosition();
		float xTransPosSevenModuleTwo = transPosSevenModuleTwo.getX();
		float yTransPosSevenModuleTwo = transPosSevenModuleTwo.getY();
		float zTransPosSevenModuleTwo = transPosSevenModuleTwo.getZ();
		float xRecPosSevenModuleTwo = recPosSevenModuleTwo.getX();
		float yRecPosSevenModuleTwo = recPosSevenModuleTwo.getY();
		float zRecPosSevenModuleTwo = recPosSevenModuleTwo.getZ();
		System.out.println("Position transmitter #7 module #1 (x, y, z) = (" + xTransPosSevenModuleTwo + ", " + yTransPosSevenModuleTwo + ", " + zTransPosSevenModuleTwo + ")");
		System.out.println("Position receiver #7 module #1 (x, y, z) = (" + xRecPosSevenModuleTwo + ", " + yRecPosSevenModuleTwo + ", " + zRecPosSevenModuleTwo + ")");
		System.out.println("*************************************************************************************************");
		
		//Module 3
		System.out.println("******************************************* Module #3 *******************************************");
		GenericTransmitter transmitterZeroModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleThree = (GenericTransmitter) moduleThree.getTransmitters().get(7);

		GenericReceiver receiverZeroModuleThree = (GenericReceiver) moduleThree.getReceivers().get(0);
		GenericReceiver receiverOneModuleThree = (GenericReceiver) moduleThree.getReceivers().get(1);
		GenericReceiver receiverTwoModuleThree = (GenericReceiver) moduleThree.getReceivers().get(2);
		GenericReceiver receiverThreeModuleThree = (GenericReceiver) moduleThree.getReceivers().get(3);
		GenericReceiver receiverFourModuleThree = (GenericReceiver) moduleThree.getReceivers().get(4);
		GenericReceiver receiverFiveModuleThree = (GenericReceiver) moduleThree.getReceivers().get(5);
		GenericReceiver receiverSixModuleThree = (GenericReceiver) moduleThree.getReceivers().get(6);
		GenericReceiver receiverSevenModuleThree = (GenericReceiver) moduleThree.getReceivers().get(7);
		
		VectorDescription transPosZeroModuleThree = transmitterZeroModuleThree.getHardware().getPosition();
		VectorDescription recPosZeroModuleThree = receiverZeroModuleThree.getHardware().getPosition();
		float xTransPosZeroModuleThree = transPosZeroModuleThree.getX();
		float yTransPosZeroModuleThree = transPosZeroModuleThree.getY();
		float zTransPosZeroModuleThree = transPosZeroModuleThree.getZ();
		float xRecPosZeroModuleThree = recPosZeroModuleThree.getX();
		float yRecPosZeroModuleThree = recPosZeroModuleThree.getY();
		float zRecPosZeroModuleThree = recPosZeroModuleThree.getZ();
		System.out.println("Position transmitter #0 module #3 (x, y, z) = (" + xTransPosZeroModuleThree + ", " + yTransPosZeroModuleThree + ", " + zTransPosZeroModuleThree + ")");
		System.out.println("Position receiver #0 module #3 (x, y, z) = (" + xRecPosZeroModuleThree + ", " + yRecPosZeroModuleThree + ", " + zRecPosZeroModuleThree + ")");
		
		VectorDescription transPosOneModuleThree = transmitterOneModuleThree.getHardware().getPosition();
		VectorDescription recPosOneModuleThree = receiverOneModuleThree.getHardware().getPosition();
		float xTransPosOneModuelThree = transPosOneModuleThree.getX();
		float yTransPosOneModuleThree = transPosOneModuleThree.getY();
		float zTransPosOneModuleThree = transPosOneModuleThree.getZ();
		float xRecPosOneModuelThree = recPosOneModuleThree.getX();
		float yRecPosOneModuleThree = recPosOneModuleThree.getY();
		float zRecPosOneModuleThree = recPosOneModuleThree.getZ();
		System.out.println("Position transmitter #1 module #3 (x, y, z) = (" + xTransPosOneModuelThree + ", " + yTransPosOneModuleThree + ", " + zTransPosOneModuleThree + ")");
		System.out.println("Position receiver #1 module #3 (x, y, z) = (" + xRecPosOneModuelThree + ", " + yRecPosOneModuleThree + ", " + zRecPosOneModuleThree + ")");
		
		VectorDescription transPosTwoModuleThree = transmitterTwoModuleThree.getHardware().getPosition();
		VectorDescription recPosTwoModuleThree = receiverTwoModuleThree.getHardware().getPosition();
		float xTransPosTwoModuleThree = transPosTwoModuleThree.getX();
		float yTransPosTwoModuleThree = transPosTwoModuleThree.getY();
		float zTransPosTwoModuleThree = transPosTwoModuleThree.getZ();
		float xRecPosTwoModuleThree = recPosTwoModuleThree.getX();
		float yRecPosTwoModuleThree = recPosTwoModuleThree.getY();
		float zRecPosTwoModuleThree = recPosTwoModuleThree.getZ();
		System.out.println("Position transmitter #2 module #3 (x, y, z) = (" + xTransPosTwoModuleThree + ", " + yTransPosTwoModuleThree + ", " + zTransPosTwoModuleThree + ")");
		System.out.println("Position receiver #2 module #3 (x, y, z) = (" + xRecPosTwoModuleThree + ", " + yRecPosTwoModuleThree + ", " + zRecPosTwoModuleThree + ")");
		
		VectorDescription transPosThreeModuleThree = transmitterThreeModuleThree.getHardware().getPosition();
		VectorDescription recPosThreeModuleThree = receiverThreeModuleThree.getHardware().getPosition();
		float xTransPosThreeModuleThree = transPosThreeModuleThree.getX();
		float yTransPosThreeModuleThree = transPosThreeModuleThree.getY();
		float zTransPosThreeModuleThree = transPosThreeModuleThree.getZ();
		float xRecPosThreeModuleThree = recPosThreeModuleThree.getX();
		float yRecPosThreeModuleThree = recPosThreeModuleThree.getY();
		float zRecPosThreeModuleThree = recPosThreeModuleThree.getZ();
		System.out.println("Position transmitter #3 module #3 (x, y, z) = (" + xTransPosThreeModuleThree + ", " + yTransPosThreeModuleThree + ", " + zTransPosThreeModuleThree + ")");
		System.out.println("Position receiver #3 module #3 (x, y, z) = (" + xRecPosThreeModuleThree + ", " + yRecPosThreeModuleThree + ", " + zRecPosThreeModuleThree + ")");
		
		VectorDescription transPosFourModuleThree = transmitterFourModuleThree.getHardware().getPosition();
		VectorDescription recPosFourModuleThree = receiverFourModuleThree.getHardware().getPosition();
		float xTransPosFourModuleThree = transPosFourModuleThree.getX();
		float yTransPosFourModuleThree = transPosFourModuleThree.getY();
		float zTransPosFourModuleThree = transPosFourModuleThree.getZ();
		float xRecPosFourModuleThree = recPosFourModuleThree.getX();
		float yRecPosFourModuleThree = recPosFourModuleThree.getY();
		float zRecPosFourModuleThree = recPosFourModuleThree.getZ();
		System.out.println("Position transmitter #4 module #3 (x, y, z) = (" + xTransPosFourModuleThree + ", " + yTransPosFourModuleThree + ", " + zTransPosFourModuleThree + ")");
		System.out.println("Position receiver #4 module #3 (x, y, z) = (" + xRecPosFourModuleThree + ", " + yRecPosFourModuleThree + ", " + zRecPosFourModuleThree + ")");
		
		VectorDescription transPosFiveModuleThree = transmitterFiveModuleThree.getHardware().getPosition();
		VectorDescription recPosFiveModuleThree = receiverFiveModuleThree.getHardware().getPosition();
		float xTransPosFiveModuleThree = transPosFiveModuleThree.getX();
		float yTransPosFiveModuleThree = transPosFiveModuleThree.getY();
		float zTransPosFiveModuleThree = transPosFiveModuleThree.getZ();
		float xRecPosFiveModuleThree = recPosFiveModuleThree.getX();
		float yRecPosFiveModuleThree = recPosFiveModuleThree.getY();
		float zRecPosFiveModuleThree = recPosFiveModuleThree.getZ();
		System.out.println("Position transmitter #5 module #3 (x, y, z) = (" + xTransPosFiveModuleThree + ", " + yTransPosFiveModuleThree + ", " + zTransPosFiveModuleThree + ")");
		System.out.println("Position receiver #5 module #3 (x, y, z) = (" + xRecPosFiveModuleThree + ", " + yRecPosFiveModuleThree + ", " + zRecPosFiveModuleThree + ")");
		
		VectorDescription transPosSixModuleThree = transmitterSixModuleThree.getHardware().getPosition();
		VectorDescription recPosSixModuleThree = receiverSixModuleThree.getHardware().getPosition();
		float xTransPosSixModuleThree = transPosSixModuleThree.getX();
		float yTransPosSixModuleThree = transPosSixModuleThree.getY();
		float zTransPosSixModuleThree = transPosSixModuleThree.getZ();
		float xRecPosSixModuleThree = recPosSixModuleThree.getX();
		float yRecPosSixModuleThree = recPosSixModuleThree.getY();
		float zRecPosSixModuleThree = recPosSixModuleThree.getZ();
		System.out.println("Position transmitter #6 module #3 (x, y, z) = (" + xTransPosSixModuleThree + ", " + yTransPosSixModuleThree + ", " + zTransPosSixModuleThree + ")");
		System.out.println("Position receiver #6 module #3 (x, y, z) = (" + xRecPosSixModuleThree + ", " + yRecPosSixModuleThree + ", " + zRecPosSixModuleThree + ")");
		
		VectorDescription transPosSevenModuleThree = transmitterSevenModuleThree.getHardware().getPosition();
		VectorDescription recPosSevenModuleThree = receiverSevenModuleThree.getHardware().getPosition();
		float xTransPosSevenModuleThree = transPosSevenModuleThree.getX();
		float yTransPosSevenModuleThree = transPosSevenModuleThree.getY();
		float zTransPosSevenModuleThree = transPosSevenModuleThree.getZ();
		float xRecPosSevenModuleThree = recPosSevenModuleThree.getX();
		float yRecPosSevenModuleThree = recPosSevenModuleThree.getY();
		float zRecPosSevenModuleThree = recPosSevenModuleThree.getZ();
		System.out.println("Position transmitter #7 module #3 (x, y, z) = (" + xTransPosSevenModuleThree + ", " + yTransPosSevenModuleThree + ", " + zTransPosSevenModuleThree + ")");
		System.out.println("Position receiver #7 module #3 (x, y, z) = (" + xRecPosSevenModuleThree + ", " + yRecPosSevenModuleThree + ", " + zRecPosSevenModuleThree + ")");
		System.out.println("*************************************************************************************************");
		
		//Module 4
		System.out.println("******************************************* Module #4 *******************************************");
		GenericTransmitter transmitterZeroModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleFour = (GenericTransmitter) moduleFour.getTransmitters().get(7);
		
		GenericReceiver receiverZeroModuleFour = (GenericReceiver) moduleFour.getReceivers().get(0);
		GenericReceiver receiverOneModuleFour = (GenericReceiver) moduleFour.getReceivers().get(1);
		GenericReceiver receiverTwoModuleFour = (GenericReceiver) moduleFour.getReceivers().get(2);
		GenericReceiver receiverThreeModuleFour = (GenericReceiver) moduleFour.getReceivers().get(3);
		GenericReceiver receiverFourModuleFour = (GenericReceiver) moduleFour.getReceivers().get(4);
		GenericReceiver receiverFiveModuleFour = (GenericReceiver) moduleFour.getReceivers().get(5);
		GenericReceiver receiverSixModuleFour = (GenericReceiver) moduleFour.getReceivers().get(6);
		GenericReceiver receiverSevenModuleFour = (GenericReceiver) moduleFour.getReceivers().get(7);
				
		VectorDescription transPosZeroModuleFour = transmitterZeroModuleFour.getHardware().getPosition();
		VectorDescription recPosZeroModuleFour = receiverZeroModuleFour.getHardware().getPosition();
		float xTransPosZeroModuleFour = transPosZeroModuleFour.getX();
		float yTransPosZeroModuleFour = transPosZeroModuleFour.getY();
		float zTransPosZeroModuleFour = transPosZeroModuleFour.getZ();
		float xRecPosZeroModuleFour = recPosZeroModuleFour.getX();
		float yRecPosZeroModuleFour = recPosZeroModuleFour.getY();
		float zRecPosZeroModuleFour = recPosZeroModuleFour.getZ();
		System.out.println("Position transmitter #0 module #4 (x, y, z) = (" + xTransPosZeroModuleFour + ", " + yTransPosZeroModuleFour + ", " + zTransPosZeroModuleFour + ")");
		System.out.println("Position receiver #0 module #4 (x, y, z) = (" + xRecPosZeroModuleFour + ", " + yRecPosZeroModuleFour + ", " + zRecPosZeroModuleFour + ")");
		
		VectorDescription transPosOneModuleFour = transmitterOneModuleFour.getHardware().getPosition();
		VectorDescription recPosOneModuleFour = receiverOneModuleFour.getHardware().getPosition();
		float xTransPosOneModuelFour = transPosOneModuleFour.getX();
		float yTransPosOneModuleFour = transPosOneModuleFour.getY();
		float zTransPosOneModuleFour = transPosOneModuleFour.getZ();
		float xRecPosOneModuelFour = recPosOneModuleFour.getX();
		float yRecPosOneModuleFour = recPosOneModuleFour.getY();
		float zRecPosOneModuleFour = recPosOneModuleFour.getZ();
		System.out.println("Position transmitter #1 module #4 (x, y, z) = (" + xTransPosOneModuelFour + ", " + yTransPosOneModuleFour + ", " + zTransPosOneModuleFour + ")");
		System.out.println("Position receiver #1 module #4 (x, y, z) = (" + xRecPosOneModuelFour + ", " + yRecPosOneModuleFour + ", " + zRecPosOneModuleFour + ")");
		
		VectorDescription transPosTwoModuleFour = transmitterTwoModuleFour.getHardware().getPosition();
		VectorDescription recPosTwoModuleFour = receiverTwoModuleFour.getHardware().getPosition();
		float xTransPosTwoModuleFour = transPosTwoModuleFour.getX();
		float yTransPosTwoModuleFour = transPosTwoModuleFour.getY();
		float zTransPosTwoModuleFour = transPosTwoModuleFour.getZ();
		float xRecPosTwoModuleFour = recPosTwoModuleFour.getX();
		float yRecPosTwoModuleFour = recPosTwoModuleFour.getY();
		float zRecPosTwoModuleFour = recPosTwoModuleFour.getZ();
		System.out.println("Position transmitter #2 module #4 (x, y, z) = (" + xTransPosTwoModuleFour + ", " + yTransPosTwoModuleFour + ", " + zTransPosTwoModuleFour + ")");
		System.out.println("Position receiver #2 module #4 (x, y, z) = (" + xRecPosTwoModuleFour + ", " + yRecPosTwoModuleFour + ", " + zRecPosTwoModuleFour + ")");
		
		VectorDescription transPosThreeModuleFour = transmitterThreeModuleFour.getHardware().getPosition();
		VectorDescription recPosThreeModuleFour = receiverThreeModuleFour.getHardware().getPosition();
		float xTransPosThreeModuleFour = transPosThreeModuleFour.getX();
		float yTransPosThreeModuleFour = transPosThreeModuleFour.getY();
		float zTransPosThreeModuleFour = transPosThreeModuleFour.getZ();
		float xRecPosThreeModuleFour = recPosThreeModuleFour.getX();
		float yRecPosThreeModuleFour = recPosThreeModuleFour.getY();
		float zRecPosThreeModuleFour = recPosThreeModuleFour.getZ();
		System.out.println("Position transmitter #3 module #4 (x, y, z) = (" + xTransPosThreeModuleFour + ", " + yTransPosThreeModuleFour + ", " + zTransPosThreeModuleFour + ")");
		System.out.println("Position receiver #3 module #4 (x, y, z) = (" + xRecPosThreeModuleFour + ", " + yRecPosThreeModuleFour + ", " + zRecPosThreeModuleFour + ")");
		
		VectorDescription transPosFourModuleFour = transmitterFourModuleFour.getHardware().getPosition();
		VectorDescription recPosFourModuleFour = receiverFourModuleFour.getHardware().getPosition();
		float xTransPosFourModuleFour = transPosFourModuleFour.getX();
		float yTransPosFourModuleFour = transPosFourModuleFour.getY();
		float zTransPosFourModuleFour = transPosFourModuleFour.getZ();
		float xRecPosFourModuleFour = recPosFourModuleFour.getX();
		float yRecPosFourModuleFour = recPosFourModuleFour.getY();
		float zRecPosFourModuleFour = recPosFourModuleFour.getZ();
		System.out.println("Position transmitter #4 module #4 (x, y, z) = (" + xTransPosFourModuleFour + ", " + yTransPosFourModuleFour + ", " + zTransPosFourModuleFour + ")");
		System.out.println("Position receiver #4 module #4 (x, y, z) = (" + xRecPosFourModuleFour + ", " + yRecPosFourModuleFour + ", " + zRecPosFourModuleFour + ")");
		
		VectorDescription transPosFiveModuleFour = transmitterFiveModuleFour.getHardware().getPosition();
		VectorDescription recPosFiveModuleFour = receiverFiveModuleFour.getHardware().getPosition();
		float xTransPosFiveModuleFour = transPosFiveModuleFour.getX();
		float yTransPosFiveModuleFour = transPosFiveModuleFour.getY();
		float zTransPosFiveModuleFour = transPosFiveModuleFour.getZ();
		float xRecPosFiveModuleFour = recPosFiveModuleFour.getX();
		float yRecPosFiveModuleFour = recPosFiveModuleFour.getY();
		float zRecPosFiveModuleFour = recPosFiveModuleFour.getZ();
		System.out.println("Position transmitter #5 module #4 (x, y, z) = (" + xTransPosFiveModuleFour + ", " + yTransPosFiveModuleFour + ", " + zTransPosFiveModuleFour + ")");
		System.out.println("Position receiver #5 module #4 (x, y, z) = (" + xRecPosFiveModuleFour + ", " + yRecPosFiveModuleFour + ", " + zRecPosFiveModuleFour + ")");
		
		VectorDescription transPosSixModuleFour = transmitterSixModuleFour.getHardware().getPosition();
		VectorDescription recPosSixModuleFour = receiverSixModuleFour.getHardware().getPosition();
		float xTransPosSixModuleFour = transPosSixModuleFour.getX();
		float yTransPosSixModuleFour = transPosSixModuleFour.getY();
		float zTransPosSixModuleFour = transPosSixModuleFour.getZ();
		float xRecPosSixModuleFour = recPosSixModuleFour.getX();
		float yRecPosSixModuleFour = recPosSixModuleFour.getY();
		float zRecPosSixModuleFour = recPosSixModuleFour.getZ();
		System.out.println("Position transmitter #6 module #4 (x, y, z) = (" + xTransPosSixModuleFour + ", " + yTransPosSixModuleFour + ", " + zTransPosSixModuleFour + ")");
		System.out.println("Position receiver #6 module #4 (x, y, z) = (" + xRecPosSixModuleFour + ", " + yRecPosSixModuleFour + ", " + zRecPosSixModuleFour + ")");
		
		VectorDescription transPosSevenModuleFour = transmitterSevenModuleFour.getHardware().getPosition();
		VectorDescription recPosSevenModuleFour = receiverSevenModuleFour.getHardware().getPosition();
		float xTransPosSevenModuleFour = transPosSevenModuleFour.getX();
		float yTransPosSevenModuleFour = transPosSevenModuleFour.getY();
		float zTransPosSevenModuleFour = transPosSevenModuleFour.getZ();
		float xRecPosSevenModuleFour = recPosSevenModuleFour.getX();
		float yRecPosSevenModuleFour = recPosSevenModuleFour.getY();
		float zRecPosSevenModuleFour = recPosSevenModuleFour.getZ();
		System.out.println("Position transmitter #7 module #4 (x, y, z) = (" + xTransPosSevenModuleFour + ", " + yTransPosSevenModuleFour + ", " + zTransPosSevenModuleFour + ")");
		System.out.println("Position receiver #7 module #4 (x, y, z) = (" + xRecPosSevenModuleFour + ", " + yRecPosSevenModuleFour + ", " + zRecPosSevenModuleFour + ")");
		System.out.println("*************************************************************************************************");
		
		//Module 5
		System.out.println("******************************************* Module #5 *******************************************");
		GenericTransmitter transmitterZeroModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleFive = (GenericTransmitter) moduleFive.getTransmitters().get(7);

		GenericReceiver receiverZeroModuleFive = (GenericReceiver) moduleFive.getReceivers().get(0);
		GenericReceiver receiverOneModuleFive = (GenericReceiver) moduleFive.getReceivers().get(1);
		GenericReceiver receiverTwoModuleFive = (GenericReceiver) moduleFive.getReceivers().get(2);
		GenericReceiver receiverThreeModuleFive = (GenericReceiver) moduleFive.getReceivers().get(3);
		GenericReceiver receiverFourModuleFive = (GenericReceiver) moduleFive.getReceivers().get(4);
		GenericReceiver receiverFiveModuleFive = (GenericReceiver) moduleFive.getReceivers().get(5);
		GenericReceiver receiverSixModuleFive = (GenericReceiver) moduleFive.getReceivers().get(6);
		GenericReceiver receiverSevenModuleFive = (GenericReceiver) moduleFive.getReceivers().get(7);
		
		VectorDescription transPosZeroModuleFive = transmitterZeroModuleFive.getHardware().getPosition();
		VectorDescription recPosZeroModuleFive = receiverZeroModuleFive.getHardware().getPosition(); 
		float xTransPosZeroModuleFive = transPosZeroModuleFive.getX();
		float yTransPosZeroModuleFive = transPosZeroModuleFive.getY();
		float zTransPosZeroModuleFive = transPosZeroModuleFive.getZ();
		float xRecPosZeroModuleFive = recPosZeroModuleFive.getX();
		float yRecPosZeroModuleFive = recPosZeroModuleFive.getY();
		float zRecPosZeroModuleFive = recPosZeroModuleFive.getZ();
		System.out.println("Position transmitter #0 module #5 (x, y, z) = (" + xTransPosZeroModuleFive + ", " + yTransPosZeroModuleFive + ", " + zTransPosZeroModuleFive + ")");
		System.out.println("Position receiver #0 module #5 (x, y, z) = (" + xRecPosZeroModuleFive + ", " + yRecPosZeroModuleFive + ", " + zRecPosZeroModuleFive + ")");
		
		VectorDescription transPosOneModuleFive = transmitterOneModuleFive.getHardware().getPosition();
		VectorDescription recPosOneModuleFive = receiverOneModuleFive.getHardware().getPosition();
		float xTransPosOneModuelFive = transPosOneModuleFive.getX();
		float yTransPosOneModuleFive = transPosOneModuleFive.getY();
		float zTransPosOneModuleFive = transPosOneModuleFive.getZ();
		float xRecPosOneModuelFive = recPosOneModuleFive.getX();
		float yRecPosOneModuleFive = recPosOneModuleFive.getY();
		float zRecPosOneModuleFive = recPosOneModuleFive.getZ();
		System.out.println("Position transmitter #1 module #5 (x, y, z) = (" + xTransPosOneModuelFive + ", " + yTransPosOneModuleFive + ", " + zTransPosOneModuleFive + ")");
		System.out.println("Position receiver #1 module #5 (x, y, z) = (" + xRecPosOneModuelFive + ", " + yRecPosOneModuleFive + ", " + zRecPosOneModuleFive + ")");
		
		VectorDescription transPosTwoModuleFive = transmitterTwoModuleFive.getHardware().getPosition();
		VectorDescription recPosTwoModuleFive = receiverTwoModuleFive.getHardware().getPosition();
		float xTransPosTwoModuleFive = transPosTwoModuleFive.getX();
		float yTransPosTwoModuleFive = transPosTwoModuleFive.getY();
		float zTransPosTwoModuleFive = transPosTwoModuleFive.getZ();
		float xRecPosTwoModuleFive = recPosTwoModuleFive.getX();
		float yRecPosTwoModuleFive = recPosTwoModuleFive.getY();
		float zRecPosTwoModuleFive = recPosTwoModuleFive.getZ();
		System.out.println("Position transmitter #2 module #5 (x, y, z) = (" + xTransPosTwoModuleFive + ", " + yTransPosTwoModuleFive + ", " + zTransPosTwoModuleFive + ")");
		System.out.println("Position receiver #2 module #5 (x, y, z) = (" + xRecPosTwoModuleFive + ", " + yRecPosTwoModuleFive + ", " + zRecPosTwoModuleFive + ")");
		
		VectorDescription transPosThreeModuleFive = transmitterThreeModuleFive.getHardware().getPosition();
		VectorDescription recPosThreeModuleFive = receiverThreeModuleFive.getHardware().getPosition();
		float xTransPosThreeModuleFive = transPosThreeModuleFive.getX();
		float yTransPosThreeModuleFive = transPosThreeModuleFive.getY();
		float zTransPosThreeModuleFive = transPosThreeModuleFive.getZ();
		float xRecPosThreeModuleFive = recPosThreeModuleFive.getX();
		float yRecPosThreeModuleFive = recPosThreeModuleFive.getY();
		float zRecPosThreeModuleFive = recPosThreeModuleFive.getZ();
		System.out.println("Position transmitter #3 module #5 (x, y, z) = (" + xTransPosThreeModuleFive + ", " + yTransPosThreeModuleFive + ", " + zTransPosThreeModuleFive + ")");
		System.out.println("Position receiver #3 module #5 (x, y, z) = (" + xRecPosThreeModuleFive + ", " + yRecPosThreeModuleFive + ", " + zRecPosThreeModuleFive + ")");
		
		VectorDescription transPosFourModuleFive = transmitterFourModuleFive.getHardware().getPosition();
		VectorDescription recPosFourModuleFive = receiverFourModuleFive.getHardware().getPosition();
		float xTransPosFourModuleFive = transPosFourModuleFive.getX();
		float yTransPosFourModuleFive = transPosFourModuleFive.getY();
		float zTransPosFourModuleFive = transPosFourModuleFive.getZ();
		float xRecPosFourModuleFive = recPosFourModuleFive.getX();
		float yRecPosFourModuleFive = recPosFourModuleFive.getY();
		float zRecPosFourModuleFive = recPosFourModuleFive.getZ();
		System.out.println("Position transmitter #4 module #5 (x, y, z) = (" + xTransPosFourModuleFive + ", " + yTransPosFourModuleFive + ", " + zTransPosFourModuleFive + ")");
		System.out.println("Position receiver #4 module #5 (x, y, z) = (" + xRecPosFourModuleFive + ", " + yRecPosFourModuleFive + ", " + zRecPosFourModuleFive + ")");
		
		VectorDescription transPosFiveModuleFive = transmitterFiveModuleFive.getHardware().getPosition();
		VectorDescription recPosFiveModuleFive = receiverFiveModuleFive.getHardware().getPosition();
		float xTransPosFiveModuleFive = transPosFiveModuleFive.getX();
		float yTransPosFiveModuleFive = transPosFiveModuleFive.getY();
		float zTransPosFiveModuleFive = transPosFiveModuleFive.getZ();
		float xRecPosFiveModuleFive = recPosFiveModuleFive.getX();
		float yRecPosFiveModuleFive = recPosFiveModuleFive.getY();
		float zRecPosFiveModuleFive = recPosFiveModuleFive.getZ();
		System.out.println("Position transmitter #5 module #5 (x, y, z) = (" + xTransPosFiveModuleFive + ", " + yTransPosFiveModuleFive + ", " + zTransPosFiveModuleFive + ")");
		System.out.println("Position receiver #5 module #5 (x, y, z) = (" + xRecPosFiveModuleFive + ", " + yRecPosFiveModuleFive + ", " + zRecPosFiveModuleFive + ")");
		
		VectorDescription transPosSixModuleFive = transmitterSixModuleFive.getHardware().getPosition();
		VectorDescription recPosSixModuleFive = receiverSixModuleFive.getHardware().getPosition();
		float xTransPosSixModuleFive = transPosSixModuleFive.getX();
		float yTransPosSixModuleFive = transPosSixModuleFive.getY();
		float zTransPosSixModuleFive = transPosSixModuleFive.getZ();
		float xRecPosSixModuleFive = recPosSixModuleFive.getX();
		float yRecPosSixModuleFive = recPosSixModuleFive.getY();
		float zRecPosSixModuleFive = recPosSixModuleFive.getZ();
		System.out.println("Position transmitter #6 module #5 (x, y, z) = (" + xTransPosSixModuleFive + ", " + yTransPosSixModuleFive + ", " + zTransPosSixModuleFive + ")");
		System.out.println("Position receiver #6 module #5 (x, y, z) = (" + xRecPosSixModuleFive + ", " + yRecPosSixModuleFive + ", " + zRecPosSixModuleFive + ")");
		
		VectorDescription transPosSevenModuleFive = transmitterSevenModuleFive.getHardware().getPosition();
		VectorDescription recPosSevenModuleFive = receiverSevenModuleFive.getHardware().getPosition();
		float xTransPosSevenModuleFive = transPosSevenModuleFive.getX();
		float yTransPosSevenModuleFive = transPosSevenModuleFive.getY();
		float zTransPosSevenModuleFive = transPosSevenModuleFive.getZ();
		float xRecPosSevenModuleFive = recPosSevenModuleFive.getX();
		float yRecPosSevenModuleFive = recPosSevenModuleFive.getY();
		float zRecPosSevenModuleFive = recPosSevenModuleFive.getZ();
		System.out.println("Position transmitter #7 module #5 (x, y, z) = (" + xTransPosSevenModuleFive + ", " + yTransPosSevenModuleFive + ", " + zTransPosSevenModuleFive + ")");
		System.out.println("Position receiver #7 module #5 (x, y, z) = (" + xRecPosSevenModuleFive + ", " + yRecPosSevenModuleFive + ", " + zRecPosSevenModuleFive + ")");
		System.out.println("*************************************************************************************************");
		
		//Module 6
		System.out.println("******************************************* Module #6 *******************************************");
		GenericTransmitter transmitterZeroModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(0);
		GenericTransmitter transmitterOneModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(1);
		GenericTransmitter transmitterTwoModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(2);
		GenericTransmitter transmitterThreeModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(3);
		GenericTransmitter transmitterFourModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(4);
		GenericTransmitter transmitterFiveModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(5);
		GenericTransmitter transmitterSixModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(6);
		GenericTransmitter transmitterSevenModuleSix = (GenericTransmitter) moduleSix.getTransmitters().get(7);
		
		GenericReceiver receiverZeroModuleSix = (GenericReceiver) moduleSix.getReceivers().get(0);
		GenericReceiver receiverOneModuleSix = (GenericReceiver) moduleSix.getReceivers().get(1);
		GenericReceiver receiverTwoModuleSix = (GenericReceiver) moduleSix.getReceivers().get(2);
		GenericReceiver receiverThreeModuleSix = (GenericReceiver) moduleSix.getReceivers().get(3);
		GenericReceiver receiverFourModuleSix = (GenericReceiver) moduleSix.getReceivers().get(4);
		GenericReceiver receiverFiveModuleSix = (GenericReceiver) moduleSix.getReceivers().get(5);
		GenericReceiver receiverSixModuleSix = (GenericReceiver) moduleSix.getReceivers().get(6);
		GenericReceiver receiverSevenModuleSix = (GenericReceiver) moduleSix.getReceivers().get(7);
				
		VectorDescription transPosZeroModuleSix = transmitterZeroModuleSix.getHardware().getPosition();
		VectorDescription recPosZeroModuleSix = receiverZeroModuleSix.getHardware().getPosition();
		float xTransPosZeroModuleSix = transPosZeroModuleSix.getX();
		float yTransPosZeroModuleSix = transPosZeroModuleSix.getY();
		float zTransPosZeroModuleSix = transPosZeroModuleSix.getZ();
		float xRecPosZeroModuleSix = recPosZeroModuleSix.getX();
		float yRecPosZeroModuleSix = recPosZeroModuleSix.getY();
		float zRecPosZeroModuleSix = recPosZeroModuleSix.getZ();
		System.out.println("Position transmitter #0 module #6 (x, y, z) = (" + xTransPosZeroModuleSix + ", " + yTransPosZeroModuleSix + ", " + zTransPosZeroModuleSix + ")");
		System.out.println("Position receiver #0 module #6 (x, y, z) = (" + xRecPosZeroModuleSix + ", " + yRecPosZeroModuleSix + ", " + zRecPosZeroModuleSix + ")");
		
		VectorDescription transPosOneModuleSix = transmitterOneModuleSix.getHardware().getPosition();
		VectorDescription recPosOneModuleSix = receiverOneModuleSix.getHardware().getPosition();
		float xTransPosOneModuelSix = transPosOneModuleSix.getX();
		float yTransPosOneModuleSix = transPosOneModuleSix.getY();
		float zTransPosOneModuleSix = transPosOneModuleSix.getZ();
		float xRecPosOneModuelSix = recPosOneModuleSix.getX();
		float yRecPosOneModuleSix = recPosOneModuleSix.getY();
		float zRecPosOneModuleSix = recPosOneModuleSix.getZ();
		System.out.println("Position transmitter #1 module #6 (x, y, z) = (" + xTransPosOneModuelSix + ", " + yTransPosOneModuleSix + ", " + zTransPosOneModuleSix + ")");
		System.out.println("Position receiver #1 module #6 (x, y, z) = (" + xRecPosOneModuelSix + ", " + yRecPosOneModuleSix + ", " + zRecPosOneModuleSix + ")");
		
		VectorDescription transPosTwoModuleSix = transmitterTwoModuleSix.getHardware().getPosition();
		VectorDescription recPosTwoModuleSix = receiverTwoModuleSix.getHardware().getPosition();
		float xTransPosTwoModuleSix = transPosTwoModuleSix.getX();
		float yTransPosTwoModuleSix = transPosTwoModuleSix.getY();
		float zTransPosTwoModuleSix = transPosTwoModuleSix.getZ();
		float xRecPosTwoModuleSix = recPosTwoModuleSix.getX();
		float yRecPosTwoModuleSix = recPosTwoModuleSix.getY();
		float zRecPosTwoModuleSix = recPosTwoModuleSix.getZ();
		System.out.println("Position transmitter #2 module #6 (x, y, z) = (" + xTransPosTwoModuleSix + ", " + yTransPosTwoModuleSix + ", " + zTransPosTwoModuleSix + ")");
		System.out.println("Position receiver #2 module #6 (x, y, z) = (" + xRecPosTwoModuleSix + ", " + yRecPosTwoModuleSix + ", " + zRecPosTwoModuleSix + ")");
		
		VectorDescription transPosThreeModuleSix = transmitterThreeModuleSix.getHardware().getPosition();
		VectorDescription recPosThreeModuleSix = receiverThreeModuleSix.getHardware().getPosition();
		float xTransPosThreeModuleSix = transPosThreeModuleSix.getX();
		float yTransPosThreeModuleSix = transPosThreeModuleSix.getY();
		float zTransPosThreeModuleSix = transPosThreeModuleSix.getZ();
		float xRecPosThreeModuleSix = recPosThreeModuleSix.getX();
		float yRecPosThreeModuleSix = recPosThreeModuleSix.getY();
		float zRecPosThreeModuleSix = recPosThreeModuleSix.getZ();
		System.out.println("Position transmitter #3 module #6 (x, y, z) = (" + xTransPosThreeModuleSix + ", " + yTransPosThreeModuleSix + ", " + zTransPosThreeModuleSix + ")");
		System.out.println("Position receiver #3 module #6 (x, y, z) = (" + xRecPosThreeModuleSix + ", " + yRecPosThreeModuleSix + ", " + zRecPosThreeModuleSix + ")");
		
		VectorDescription transPosFourModuleSix = transmitterFourModuleSix.getHardware().getPosition();
		VectorDescription recPosFourModuleSix = receiverFourModuleSix.getHardware().getPosition();
		float xTransPosFourModuleSix = transPosFourModuleSix.getX();
		float yTransPosFourModuleSix = transPosFourModuleSix.getY();
		float zTransPosFourModuleSix = transPosFourModuleSix.getZ();
		float xRecPosFourModuleSix = recPosFourModuleSix.getX();
		float yRecPosFourModuleSix = recPosFourModuleSix.getY();
		float zRecPosFourModuleSix = recPosFourModuleSix.getZ();
		System.out.println("Position transmitter #4 module #6 (x, y, z) = (" + xTransPosFourModuleSix + ", " + yTransPosFourModuleSix + ", " + zTransPosFourModuleSix + ")");
		System.out.println("Position receiver #4 module #6 (x, y, z) = (" + xRecPosFourModuleSix + ", " + yRecPosFourModuleSix + ", " + zRecPosFourModuleSix + ")");
		
		VectorDescription transPosFiveModuleSix = transmitterFiveModuleSix.getHardware().getPosition();
		VectorDescription recPosFiveModuleSix = receiverFiveModuleSix.getHardware().getPosition();
		float xTransPosFiveModuleSix = transPosFiveModuleSix.getX();
		float yTransPosFiveModuleSix = transPosFiveModuleSix.getY();
		float zTransPosFiveModuleSix = transPosFiveModuleSix.getZ();
		float xRecPosFiveModuleSix = recPosFiveModuleSix.getX();
		float yRecPosFiveModuleSix = recPosFiveModuleSix.getY();
		float zRecPosFiveModuleSix = recPosFiveModuleSix.getZ();
		System.out.println("Position transmitter #5 module #6 (x, y, z) = (" + xTransPosFiveModuleSix + ", " + yTransPosFiveModuleSix + ", " + zTransPosFiveModuleSix + ")");
		System.out.println("Position receiver #5 module #6 (x, y, z) = (" + xRecPosFiveModuleSix + ", " + yRecPosFiveModuleSix + ", " + zRecPosFiveModuleSix + ")");
		
		VectorDescription transPosSixModuleSix = transmitterSixModuleSix.getHardware().getPosition();
		VectorDescription recPosSixModuleSix = receiverSixModuleSix.getHardware().getPosition();
		float xTransPosSixModuleSix = transPosSixModuleSix.getX();
		float yTransPosSixModuleSix = transPosSixModuleSix.getY();
		float zTransPosSixModuleSix = transPosSixModuleSix.getZ();
		float xRecPosSixModuleSix = recPosSixModuleSix.getX();
		float yRecPosSixModuleSix = recPosSixModuleSix.getY();
		float zRecPosSixModuleSix = recPosSixModuleSix.getZ();
		System.out.println("Position transmitter #6 module #6 (x, y, z) = (" + xTransPosSixModuleSix + ", " + yTransPosSixModuleSix + ", " + zTransPosSixModuleSix + ")");
		System.out.println("Position receiver #6 module #6 (x, y, z) = (" + xRecPosSixModuleSix + ", " + yRecPosSixModuleSix + ", " + zRecPosSixModuleSix + ")");
		
		VectorDescription transPosSevenModuleSix = transmitterSevenModuleSix.getHardware().getPosition();
		VectorDescription recPosSevenModuleSix = receiverSevenModuleSix.getHardware().getPosition();
		float xTransPosSevenModuleSix = transPosSevenModuleSix.getX();
		float yTransPosSevenModuleSix = transPosSevenModuleSix.getY();
		float zTransPosSevenModuleSix = transPosSevenModuleSix.getZ();
		float xRecPosSevenModuleSix = recPosSevenModuleSix.getX();
		float yRecPosSevenModuleSix = recPosSevenModuleSix.getY();
		float zRecPosSevenModuleSix = recPosSevenModuleSix.getZ();
		System.out.println("Position transmitter #7 module #6 (x, y, z) = (" + xTransPosSevenModuleSix + ", " + yTransPosSevenModuleSix + ", " + zTransPosSevenModuleSix + ")");		
		System.out.println("Position receiver #7 module #6 (x, y, z) = (" + xRecPosSevenModuleSix + ", " + yRecPosSevenModuleSix + ", " + zRecPosSevenModuleSix + ")");
		System.out.println("*************************************************************************************************");		
	}
	
	
	
	
	//Just for test - is going to be removed
	private void fooBar() {
		GenericTransmitter transmitterZero = (GenericTransmitter) modules.get(0).getTransmitters().get(0);
		GenericTransmitter transmitterOne = (GenericTransmitter) modules.get(0).getTransmitters().get(1);
		GenericTransmitter transmitterTwo = (GenericTransmitter) modules.get(0).getTransmitters().get(2);
		GenericTransmitter transmitterThree = (GenericTransmitter) modules.get(0).getTransmitters().get(3);
		GenericTransmitter transmitterFour = (GenericTransmitter) modules.get(0).getTransmitters().get(4);
		GenericTransmitter transmitterFive = (GenericTransmitter) modules.get(0).getTransmitters().get(5);
		GenericTransmitter transmitterSix = (GenericTransmitter) modules.get(0).getTransmitters().get(6);
		GenericTransmitter transmitterSeven = (GenericTransmitter) modules.get(0).getTransmitters().get(7);
		
		//Transmitter 0
		for(Packet p : transmitterZero.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 0");
			}
		}		
		//Transmitter 1
		for(Packet p : transmitterOne.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 1");
			}
		}		
		//Transmitter 2
		for(Packet p : transmitterTwo.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 2");
			}
		}
		//Transmitter 3
		for(Packet p : transmitterThree.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 3");
			}
		}
		//Transmitter 4
		for(Packet p : transmitterFour.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 4");
			}
		}
		//Transmitter 5
		for(Packet p : transmitterFive.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 5");
			}
		}
		//Transmitter 6
		for(Packet p : transmitterSix.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 6");
			}
		}
		//Transmitter 7
		for(Packet p : transmitterSeven.getCommunicationContainer().getPacketList()) {			
			byte[] data = p.getData();
			if(data.length > 0) {
				System.out.println(data.length);
				System.out.println("Transmitter 7");
			}
		}		
	}

	private void initializeComponents() {
		
		canvas = new DrawingCanvas(simulation, 20, simulation.getModules().size());
	    canvas.addMouseListener(new CanvasMouseListener());    

		
		
		checkboxTransmitter = new JCheckBox("Transmitters");
		checkboxReceiver = new JCheckBox("Receivers");
		checkboxPacket = new JCheckBox("Packets");

		checkboxTransmitter.addItemListener(this);
		checkboxReceiver.addItemListener(this);
		checkboxPacket.addItemListener(this);

		labelTransmitterModules = new JLabel("Modules");
		labelReceiverModules = new JLabel("Modules");
		labelPacketModules = new JLabel("Modules");
		
		String[] items = createComboboxItems();		
		comboboxTransmitterModules = new JComboBox(items);
		comboboxReceiverModules = new JComboBox(items);
		comboboxPacketModules = new JComboBox(items);
				
		comboboxTransmitterModules.setEnabled(false);
		comboboxReceiverModules.setEnabled(false);
		comboboxPacketModules.setEnabled(false);
		
		startSimulationButton = new JButton("Start simulation");
		pauseSimulationButton = new JButton("Pause simulation");
		stopSimulationButton = new JButton("Stop simulation");

		startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startSimulationButtonAction(event);
			}
		});
		
		pauseSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pauseSimulationButtoAction(event);				
			}
		});
		
		stopSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stopSimulationButtonAction(event);
			}
		});
	}

	private synchronized void startSimulationButtonAction(ActionEvent event) {
		isSimulationRunning = true;
		notify();
		System.out.println("Start simulation button pressed");
		textArea.append("Simulation started \n");
		simulation.setPause(false);
		fooBar();
		//TODO
		//1) When the start simulation button is pressed the communication should be visualized
		//2) For each module it should be checked if it transmits data
		//3) If it transmits data the receiver(s) should be obtained
		//4) If it transmits data the packet should be obtained
		//5) The moduleID of the transmitter and the receivers are extracted 
		//6) An arrow from the transmitter module to the receiver module(s) should be drawn on the canvas
		//7) The content of the packet should be drawn above the arrow		
	}
	
	private void pauseSimulationButtoAction(ActionEvent event) {
		System.out.println("Pause simulation button pressed");
		if(simulation.isPaused() == false) {
			simulation.setPause(true);
		}
		else {
			simulation.setPause(false);
		}
	}
	
	private void stopSimulationButtonAction(ActionEvent event) {
		textArea.append("Simulation stopped \n");
		simulation.stop();
	}

	public static boolean getInstanceFlag() {
		return instanceFlag;
	}

	public static void activateCommunicationVisualizerGUI(final JMEBasicGraphicalSimulation simulator) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JFrame frame = new JFrame("USSR - Module Communication Visualizer");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JComponent newContentPane = new CommunicationVisualizerGUI(simulator);
				newContentPane.setOpaque(true); 
				frame.setContentPane(newContentPane);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private String[] createComboboxItems() {
		String[] items = new String[numberOfModules];
		int i;
		for(i = 0; i < numberOfModules; i++) {
			items[i] = "#" + i;			
		}
		return items;		
	}
	
	private JPanel createTextArea() {
		textArea = new JTextArea();
		textArea.setDragEnabled(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(600, 100));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Text Area"));
		return panel;
	}

	private JPanel createCanvasArea() {
		//canvas = new DrawingCanvas(simulation, 20, simulation.getModules().size());
		JScrollPane scrollPane = new JScrollPane(canvas);
		scrollPane.setPreferredSize(new Dimension(600, 200));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Module Communication"));
		return panel;
	}

	private JPanel createControllArea() {
		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.setPreferredSize(new Dimension(100, 100));
		panel.add(checkboxTransmitter);
		panel.add(labelTransmitterModules);
		panel.add(comboboxTransmitterModules);
		panel.add(checkboxReceiver);
		panel.add(labelReceiverModules);
		panel.add(comboboxReceiverModules);
		panel.add(checkboxPacket);
		panel.add(labelPacketModules);
		panel.add(comboboxPacketModules);
		panel.setBorder(BorderFactory.createTitledBorder("Visualization Filter"));
		return panel;
	}

	private JPanel createSimulationArea() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setPreferredSize(new Dimension(100, 100));
		panel.add(startSimulationButton);
		panel.add(pauseSimulationButton);
		panel.add(stopSimulationButton);
		return panel;
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if (source == checkboxTransmitter) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All transmitters selected");
				textArea.append("All transmitters selected \n");
				comboboxTransmitterModules.setEnabled(true);
			} else {
				System.out.println("All transmitters deselected");
				textArea.append("All transmitters deselected \n");
				comboboxTransmitterModules.setEnabled(false);
			}
		} else if (source == checkboxReceiver) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All receivers selected");
				textArea.append("All receivers selected \n");
				comboboxReceiverModules.setEnabled(true);
			} else {				
				System.out.println("All receivers deselected");
				textArea.append("All receivers deselected \n");
				comboboxReceiverModules.setEnabled(false);
			}
		} else if (source == checkboxPacket) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("All packets selected");
				textArea.append("All packets selected \n");
				comboboxPacketModules.setEnabled(true);
			} else {
				System.out.println("All packets deselected");
				textArea.append("All packets deselected \n");
				comboboxPacketModules.setEnabled(false);
			}
		}
	}
	
	class CanvasMouseListener extends MouseAdapter{
		  public void mousePressed(MouseEvent e){
			canvas.setMouseClickX(e.getX());
			canvas.setMouseClickY(e.getY());
			int mouseX = canvas.getMouseClickX();
			int mouseY = canvas.getMouseClickY();
		    System.out.println("Mouse position (x, y) = " + "(" + mouseX + ", " + mouseY + ")");
		    e.getComponent().repaint();		    
		  }
		}
}

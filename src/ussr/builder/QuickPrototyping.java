package ussr.builder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import ussr.builder.assigmentLabels.LabelingToolSpecification;
import ussr.builder.assigmentLabels.LabelingTools;
import ussr.builder.construction.ATRONOperationsTemplate;
import ussr.builder.construction.CommonOperationsTemplate;
import ussr.builder.construction.ConstructionToolSpecification;
import ussr.builder.construction.ConstructionTools;
import ussr.builder.construction.MTRANOperationsTemplate;
import ussr.builder.construction.OdinOperationsTemplate;
import ussr.builder.construction.SelectOperationsTemplate;
import ussr.builder.controllersLabels.AssignController;
import ussr.builder.genericSelectionTools.AssignRemoveLabels;
import ussr.builder.genericSelectionTools.ColorConnectors;
import ussr.builder.genericSelectionTools.MtranExperiment;
import ussr.builder.genericSelectionTools.NewSelection;
import ussr.builder.genericSelectionTools.ReadLabels;
import ussr.builder.genericSelectionTools.RemoveModule;
import ussr.builder.genericSelectionTools.RotateModuleComponents;
import ussr.builder.gui.FileChooser;
import ussr.builder.gui.GuiHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.Odin;

/**
 * The main responsibility of this class is to take care of the main GUI window for  project called
 * "Quick Prototyping of Simulation Scenarios".   
 * USER GUIDE.
 * In order to start "Quick Prototyping of Simulation Scenarios" or so called interactive builder do 
 * the following:
 * 1) Locate simulation file called "BuilderMultiRobotSimulation.java in the same package as this
 *    class".   
 * 2) Start  simulation for above file. The simulation should be in static state 
 * 	  (paused state),meaning that you should not press "P" button on keyboard. At least not yet.
 * 3) Press "Q" button on keyboard and wait a bit. On slow machines the "Quick Prototyping of Simulation 
 *    Scenarios" window is quite slow to respond. 
 * 4) In appeared "Quick prototyping of Simulation Scenarios" window choose one of the buttons(pickers,also
 *    called selection tools)in toolbars of GUI. You can identify the name of the toolbar by going to View-->
 *    Toolbars-->.... The following explanation if for each toolbar counting from the top:
 *    4.1) Simulation
 *         a) "Pause/Play" - is for starting and pausing simulation;
 *         b) "StebByStep" - is for running simulation with single execution step;
 *         c) "Save" - is for saving the data about modular robot in XML file (slow to respond, wait for it);
 *         e) "Open" - is for loading the data about modular robot from XML file into simulation (slow to respond, wait for it);
 *    4.2)Rendering
 *         a) "Physics" - starts and stops rendering physics;
 *         b) "Wireframe" - starts and stops rendering wireframe;
 *         c) "Bounds" - starts and stops rendering bounds;
 *         d) "Normals" - starts and stops rendering normals;
 *         e) "Lights" - starts and stops rendering lights;
 *    4.3)Module generic tools
 *         a) "Move" - is the selection tool, where after selecting the module with left side of the mouse
 *            it is moved with movement of the mouse to desired location; Is added as additional supports and 
 *            its main purpose is to move default modules. 
 *         b) "Delete icon" - is the selection tool, where after selecting the module with left side of the mouse
 *            it is deleted(removed)from simulation environment;
 *         c) "C"- is the selection tool, where after selecting the module with left side of the mouse
 *            its connectors are coloured with colour coding. The format is Connector-Colour: 0-Black, 1-Blue,
 *            2-Cyan, 3-Grey, 4-Green, 5-Magenta, 6-Orange, 7-Pink, 8-Red, 9-White, 10-Yellow, 11-Light Grey.
 *         d) "Cartesian coordinates (in ComboBox (x,y and z))", "TextField for angle of rotation" and "Rotate Icon"
 *            are used together to rotate components of the modules with specific angle. First choose one of the cartesian
 *            coordinates in ComboBox (for example: "x"), after that enter the angle in degrees in TextField and at last select
 *            "Rotate" icon. After all that select one of the components of the module in simulation environment.
 *            This tool is experimental and should not be used at current state. *          	
 *    4.4)Modular robot
 *         a) "ComboBox with Modular robots names" - here choose the modular robot you would like to work with.
 *         b) "Default" - adds default construction module of modular robot. If in previous ComboBox you chosen "ATRON",
 *            then ATRON module will be added into simulation at default position.
 *         c) "ComboBox with rotations" - allows to rotate the default module with standard rotations for the module.
 *         d) "Opposite" - rotates the module selected in simulation environment with opposite rotation
 *            to current rotation of the module.
 *         e) "Variation" - is a selection tool for additional properties of modular robot modules. For example in case of
 *             Odin replaces OdinMuscle with other types of modules. In case of MTRAN rotates module 90 degrees around axis. *    
 *    4.5)Construction.
 *    	   For example for ATRON (the same is for MTRAN and Odin):
 *         a) "On Connnector"- is the selection tool, where you can just click on connector of the module with the 
 *            left side of the mouse and the next module will be added to selected connector;
 *         b) "ComboBox with numbers"- is the selection tool, where after choosing the connector number
 *            (in ComboBox)where new module will be added, after that click on one of the modules in simulation
 *            environment with the left side of the mouse. As a result new module will be added to the 
 *            chosen connector on selected module.
 *         c) "All"- is the selection tool, where you just click on the module with the left side of the mouse
 *            and all possible modules will be added to the selected module connectors, except the ones which are already occupied.
 *         d) "Loop"- is the selection tool, where you just click on the module with the left side of the mouse
 *            and later(wait a bit) press on buttons "Next" or "Previous". As a result, new module will be moved from one 
 *            connector to another with increasing number of connector ("Next") and decreasing number of 
 *            connector ("Previous").
 *      4.6) LABELS                
 * 5) Using the above selection tools construct desired morphology(shape) of the modular robot.
 *    Easiest may is to start from toolBar called "Modular robot" by choosing modular robot name
 *    in comboBox and pressing "Default" button, after that choosing appropriate rotation in rotations comboBox,
 *    later shift to toolbar called "Construction". By using one of the four tools construct morphology of modular robot.
 *    There is also toolBar called Assistant, which sometimes :) displays hints what to do next. Follow it, but
 *    do not trust it 100% :).
 *     When you are done with morphology press "Test before simulation" button at the bottom of GUI. If there are no errors
 *    or exceptions start simulation by pressing "Play" button in the first toolbar from the top.
 *     
 *  
 * Additional functionality is ability to save the data about the modules in simulation
 * in XML format. Just choose File-->Save, this will open File chooser, also with quite a delay. Or select "save"
 * icon in "Quick Prototyping of Simulation Scenarios" window.
 * In order to open(load) previously saved XML file, choose File-->Open, this will open File chooser, also with 
 * quite a delay. Or select "open" icon in "Quick Prototyping of Simulation Scenarios" window.
 * There are several examples of modular robots morphologies kept in directory of USSR:
 * resources\quickPrototyping\loadXMLexamples. Just copy one of the files and paste somewhere on your
 * PC(for example desktop), after that load it by chosing File-->Open or "open" icon. Keep in mind that
 * if you started the simulation for ATRON (see points 1 and 2 above, for example "InteractiveAtronBuilderTest1.java")
 * you can load only ATRON related XML files, like for example ATRONcar.xml.
 * 
 * In case there is the need to undersand the code deeper look for the Design Class Diagram(DCD) for all the code,
 * which can be found in USSR directory named as:"doc\developer\DCD.JPG"
 * 
 *@author  Konstantinas
 */

public class QuickPrototyping extends javax.swing.JFrame  {

	/**
	 * The physical simulation
	 */	   
	private JMESimulation JME_simulation;
	
	/**
	 * FileChooser object as save dialog window
	 */
	private FileChooser fileChooserSave;
	
	/**
	 * FileChooser object as open dialog window
	 */
	private FileChooser fileChooserOpen;

	/**
	 * The Helper object for frequent GUI operations.
	 */
	private final GuiHelper guiUtil = new GuiHelper();

	/**
	 *  The name of the current modular robot
	 */
	private String chosenMRname ="ATRON"; //MR- modular robot. Default is ATRON, just do not have the case when it is empty String
	
	/**
	 *  The default modular robot and others in sequence
	 */
	private String defaultModularRobot = "ATRON", secondModularRobot = "MTRAN", thirdModularRobot = "Odin";

	/**
	 *  The default rotation of default modular robot
	 */
	private String rotationName ="EW";//Default, just do not have the case when it is empty String

	/**
	 * The current connector number one the module
	 */
	private int connectorNr;

	/**
	 * The amount of connectors on ATRON module
	 */
	private final static int amountATRONConnectors = 7;

	/**
	 * The amount of connectors on MTRAN module
	 */
	private final static int amountMTRANConnectors = 5;  

	/**
	 * The amount of connectors on OdinBall module
	 */
	private final static  int amountOdinBallConnectors = 11;

	/**
	 * The ussr directory for keeping icons used in GUI design.
	 */
	private final static String directoryForIcons = "resources/quickPrototyping/icons/";

	/**
	 * The names of icons with extensions
	 */
	private final static String pauseIcon ="pause.JPG", playIcon = "play.JPG", rotateIcon ="rotate.JPG",
	                           stepByStepIcon ="stepByStep.JPG", saveIcon = "save.JPG", openIcon = "open.JPG",
	                            moveIcon = "move.JPG", deleteIcon = "delete.JPG", colourConnectorsIcon = "colorConnectors.JPG";

	/**
	 * Initial simulation step
	 */
	private int simulationStep =0;

	private boolean allCheckBoxSelected;

/*Note*/	private static boolean instanceFlag = false;


	/**
	 * Current Cartesian coordinate (x,y or z)
	 */
	private String cartesianCoordinate;

	/**
	 * The names of the tools for contsruction or robot morphology
	 */
/*NOTE TO DELETE*//*	private final static String oppositeRotation  ="OppositeRotation", variation = "Variation", onConnector="OnConnector",
	chosenConnector = "chosenConnector", allConnectors = "AllConnectors", loop = "Loop",
	standardRotation = "StandardRotation";*/

	/**
	 * Default positions of default modules of modular robots
	 */
	private final static VectorDescription atronDefaultPosition = new VectorDescription(0,-0.441f,0.5f),
	mtranDefaultPosition = new VectorDescription(-1f,-0.4621f,0.5f),
/*NOTE*/	odinDefaultPosition = new VectorDescription(1f,-0.4646f,0.5f); 

	/** Creates new form QuickPrototyping */
	public QuickPrototyping(JMEBasicGraphicalSimulation simulation) {
		initComponents();
		//Set to generic view      
		guiUtil.changeToSetLookAndFeel(this);         
		this.JME_simulation = (JMESimulation) simulation;  
		adaptGuiToModularRobot();// Adapt GUI to modular robot existing in simulation environment
		JME_simulation.setPicker(new PhysicsPicker(true, true));//set default picker
/*NOTE*/		instanceFlag = true;// the frame is instantiate
		addWindowListener (new WindowAdapter() {
	          public void windowClosing(WindowEvent e) {
	        	  instanceFlag = false; // reset the flag after closing the frame
	        	  e.getWindow().dispose();	                     
	             }
	          }
	      );

	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
	private void initComponents() {
		simulationToolBar = new javax.swing.JToolBar();
		pauseRunButton = new javax.swing.JButton();
		stepByStepjButton = new javax.swing.JButton();
		savejButton = new javax.swing.JButton();
		openjButton = new javax.swing.JButton();
		renderjToolBar = new javax.swing.JToolBar();
		physicsjCheckBox = new javax.swing.JCheckBox();
		wireFramejCheckBox = new javax.swing.JCheckBox();
		boundsjCheckBox = new javax.swing.JCheckBox();
		normalsjCheckBox = new javax.swing.JCheckBox();
		lightsjCheckBox = new javax.swing.JCheckBox();
		moduleGenericToolsJToolBar = new javax.swing.JToolBar();
		movejButton = new javax.swing.JButton();
		deletejButton = new javax.swing.JButton();
		alljCheckBox = new javax.swing.JCheckBox();
		colourConnectorsjButton = new javax.swing.JButton();
		cartesianCoordinatejComboBox = new javax.swing.JComboBox();
		rotationAnglejTextField = new javax.swing.JTextField();
		rotateComponentjButton = new javax.swing.JButton();
		modularRoborGenericjToolBar = new javax.swing.JToolBar();
		modularRobotjComboBox = new javax.swing.JComboBox();
		defaultModulejButton = new javax.swing.JButton();
		standardRotationsjComboBox = new javax.swing.JComboBox();
		oppositeRotationjButton = new javax.swing.JButton();
		variatejButton = new javax.swing.JButton();
		constructionToolBar = new javax.swing.JToolBar();
		onConnectorjButton = new javax.swing.JButton();
		connectorsjComboBox = new javax.swing.JComboBox();
		alljButton = new javax.swing.JButton();
		loopjButton = new javax.swing.JButton();
		nextjButton = new javax.swing.JButton();
		previousjButton = new javax.swing.JButton();
		labeljToolBar = new javax.swing.JToolBar();
		labelsjLabel = new javax.swing.JLabel();
		currentLabeljTextField = new javax.swing.JTextField();
		assignLabeljButton = new javax.swing.JButton();
		removeLabeljButton = new javax.swing.JButton();
		moduleLabelsToolBar = new javax.swing.JToolBar();
		ModuleLabelsjLabel = new javax.swing.JLabel();
		moduleLabelsjComboBox = new javax.swing.JComboBox();
		readLabelsjButton = new javax.swing.JButton();
		AssistantjToolBar = new javax.swing.JToolBar();
		assistantjLabel = new javax.swing.JLabel();
		AssistantjTextField = new javax.swing.JTextField();
		testjButton = new javax.swing.JButton();
		jMenuBar1 = new javax.swing.JMenuBar();
		filejMenu = new javax.swing.JMenu();
		OpenXMLJMenuItem = new javax.swing.JMenuItem();
		saveAsjMenuItem = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		ExitjMenuItem = new javax.swing.JMenuItem();
		viewjMenu = new javax.swing.JMenu();
		toolBarsjMenu = new javax.swing.JMenu();
		simulationjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		renderjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		moduleGenericToolsCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		modularRobotsjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		constructionjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		labeljCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		moduleLabelsjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		assistantCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();

		getContentPane().setLayout(new java.awt.FlowLayout());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);	
		setTitle("Quick Prototyping of Simulation Scenarios");
		simulationToolBar.setRollover(true);
		simulationToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		//pauseRunButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\play.JPG"));
		pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + playIcon));
		pauseRunButton.setToolTipText("Play/Pause");
		pauseRunButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pauseRunButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(pauseRunButton);

		//stepByStepjButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\stepByStep.JPG"));
		stepByStepjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons+stepByStepIcon));
		stepByStepjButton.setToolTipText("StepByStep");
		stepByStepjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepByStepjButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(stepByStepjButton);

		//savejButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\save.JPG"));
		savejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + saveIcon));
		savejButton.setToolTipText("save");
		savejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				savejButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(savejButton);

		//openjButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\open.JPG"));
		openjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + openIcon));
		openjButton.setToolTipText("Open ");
		openjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openjButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(openjButton);

		getContentPane().add(simulationToolBar);

		renderjToolBar.setPreferredSize(new java.awt.Dimension(420, 30));
		renderjToolBar.setVerifyInputWhenFocusTarget(false);
		physicsjCheckBox.setText("Physics");
		physicsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		physicsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		physicsjCheckBox.setMaximumSize(new java.awt.Dimension(53, 60));
		physicsjCheckBox.setMinimumSize(new java.awt.Dimension(53, 60));
		physicsjCheckBox.setPreferredSize(new java.awt.Dimension(60, 60));
		physicsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				physicsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(physicsjCheckBox);

		wireFramejCheckBox.setText("Wireframe");
		wireFramejCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		wireFramejCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		wireFramejCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				wireFramejCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(wireFramejCheckBox);

		boundsjCheckBox.setText("Bounds");
		boundsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		boundsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		boundsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boundsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(boundsjCheckBox);

		normalsjCheckBox.setText("Normals");
		normalsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		normalsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		normalsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				normalsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(normalsjCheckBox);

		lightsjCheckBox.setText("Lights");
		lightsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		lightsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		lightsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lightsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(lightsjCheckBox);

		getContentPane().add(renderjToolBar);

		moduleGenericToolsJToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		//movejButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\move.JPG"));
		movejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + moveIcon));
		movejButton.setToolTipText("Move");
		movejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				movejButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(movejButton);

		//deletejButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\delete.JPG"));
		deletejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + deleteIcon));
		deletejButton.setToolTipText("Delete");
		deletejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deletejButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(deletejButton);

		alljCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		alljCheckBox.setText("all");
		alljCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		alljCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		alljCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				alljCheckBoxActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(alljCheckBox);

		//colourConnectorsjButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\colorConnectors.JPG"));
		colourConnectorsjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + colourConnectorsIcon));
		colourConnectorsjButton.setToolTipText("Colour conectors");
		colourConnectorsjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				colourConnectorsjButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(colourConnectorsjButton);

		cartesianCoordinatejComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		cartesianCoordinatejComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "x", "y", "z" }));
		cartesianCoordinatejComboBox.setToolTipText("Coordinate");
		cartesianCoordinatejComboBox.setPreferredSize(new java.awt.Dimension(70, 22));
		cartesianCoordinatejComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cartesianCoordinatejComboBoxActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(cartesianCoordinatejComboBox);

		rotationAnglejTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
		rotationAnglejTextField.setText("1");
		rotationAnglejTextField.setPreferredSize(new java.awt.Dimension(10, 20));
		moduleGenericToolsJToolBar.add(rotationAnglejTextField);

		//rotateComponentjButton.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\kokuz06\\My Documents\\rotate.JPG"));
		rotateComponentjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + rotateIcon));
		rotateComponentjButton.setToolTipText("Rotate component");
		rotateComponentjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rotateComponentjButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(rotateComponentjButton);

		getContentPane().add(moduleGenericToolsJToolBar);

		modularRoborGenericjToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		modularRobotjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "MTRAN", "Odin" }));
		modularRobotjComboBox.setToolTipText("supported modular robots");
		modularRobotjComboBox.setPreferredSize(new java.awt.Dimension(30, 43));
		modularRobotjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modularRobotjComboBoxActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(modularRobotjComboBox);

		defaultModulejButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		defaultModulejButton.setText("Default");
		defaultModulejButton.setToolTipText("Add default module");
		defaultModulejButton.setPreferredSize(new java.awt.Dimension(60, 43));
		defaultModulejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultModulejButtonActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(defaultModulejButton);

		standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EW", "WE", "UD", "DU", "SN", "NS" }));
		standardRotationsjComboBox.setToolTipText("standard rotations");
		standardRotationsjComboBox.setPreferredSize(new java.awt.Dimension(30, 22));
		standardRotationsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				standardRotationsjComboBoxActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(standardRotationsjComboBox);

		oppositeRotationjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		oppositeRotationjButton.setText("Opposite");
		oppositeRotationjButton.setToolTipText("Opposire rotation");
		oppositeRotationjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				oppositeRotationjButtonActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(oppositeRotationjButton);

		variatejButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		variatejButton.setText("Variation");
		variatejButton.setToolTipText("Variation of modules");
		variatejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				variatejButtonActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(variatejButton);

		getContentPane().add(modularRoborGenericjToolBar);

		constructionToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		onConnectorjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		onConnectorjButton.setText("On connector");
		onConnectorjButton.setToolTipText("Module of selected connector");
		onConnectorjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onConnectorjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(onConnectorjButton);

		connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
		connectorsjComboBox.setPreferredSize(new java.awt.Dimension(30, 22));
		connectorsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectorsjComboBoxActionPerformed(evt);
			}
		});

		constructionToolBar.add(connectorsjComboBox);

		alljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		alljButton.setText("All");
		alljButton.setToolTipText("Modules on all connectors");
		alljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				alljButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(alljButton);

		loopjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		loopjButton.setText("Loop");
		loopjButton.setToolTipText("Move module from one connector onto another");
		loopjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loopjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(loopjButton);

		nextjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		nextjButton.setText("Next");
		nextjButton.setToolTipText("On next connector");
		nextjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(nextjButton);

		previousjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		previousjButton.setText("Previous");
		previousjButton.setToolTipText("On previous connector");
		previousjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				previousjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(previousjButton);

		getContentPane().add(constructionToolBar);

		labeljToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		labelsjLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
		labelsjLabel.setText("Label:");
		labeljToolBar.add(labelsjLabel);

		currentLabeljTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
		currentLabeljTextField.setText("type in label name or chose one below in comboBox");
		labeljToolBar.add(currentLabeljTextField);

		assignLabeljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		assignLabeljButton.setText("Assign");
		assignLabeljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				assignLabeljButtonActionPerformed(evt);
			}
		});

		labeljToolBar.add(assignLabeljButton);

		removeLabeljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		removeLabeljButton.setText("Remove");
		removeLabeljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeLabeljButtonActionPerformed(evt);
			}
		});

		labeljToolBar.add(removeLabeljButton);

		getContentPane().add(labeljToolBar);

		moduleLabelsToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		ModuleLabelsjLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
		ModuleLabelsjLabel.setText("Module Labels:");
		moduleLabelsToolBar.add(ModuleLabelsjLabel);

		moduleLabelsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Labels" }));
		moduleLabelsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moduleLabelsjComboBoxActionPerformed(evt);
			}
		});

		moduleLabelsToolBar.add(moduleLabelsjComboBox);

		readLabelsjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		readLabelsjButton.setText("Read");
		readLabelsjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				readLabelsjButtonActionPerformed(evt);
			}
		});

		moduleLabelsToolBar.add(readLabelsjButton);

		getContentPane().add(moduleLabelsToolBar);

		AssistantjToolBar.setPreferredSize(new java.awt.Dimension(420, 35));
		assistantjLabel.setText("Assistant:");
		AssistantjToolBar.add(assistantjLabel);

		AssistantjTextField.setEditable(false);
		AssistantjToolBar.add(AssistantjTextField);

		getContentPane().add(AssistantjToolBar);

		testjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		testjButton.setText("Test before simulation");
		testjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				testjButtonActionPerformed(evt);
			}
		});

		getContentPane().add(testjButton);

		filejMenu.setText("File");
		filejMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				filejMenuActionPerformed(evt);
			}
		});

		OpenXMLJMenuItem.setText("Open");
		OpenXMLJMenuItem.setToolTipText("Open ");
		OpenXMLJMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OpenXMLJMenuItemActionPerformed(evt);
			}
		});

		filejMenu.add(OpenXMLJMenuItem);

		saveAsjMenuItem.setText("Save as");
		saveAsjMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAsjMenuItemActionPerformed(evt);
			}
		});

		filejMenu.add(saveAsjMenuItem);

		filejMenu.add(jSeparator1);

		ExitjMenuItem.setText("Exit");
		ExitjMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ExitjMenuItemActionPerformed(evt);
			}
		});

		filejMenu.add(ExitjMenuItem);

		jMenuBar1.add(filejMenu);

		viewjMenu.setText("View");
		toolBarsjMenu.setText("Toolbars");
		simulationjCheckBoxMenuItem.setText("Simulation");
		simulationjCheckBoxMenuItem.setSelected(true);
		simulationjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				simulationjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(simulationjCheckBoxMenuItem);

		renderjCheckBoxMenuItem.setText("Rendering");
		renderjCheckBoxMenuItem.setSelected(true);
		renderjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				renderjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(renderjCheckBoxMenuItem);

		moduleGenericToolsCheckBoxMenuItem.setText("Module generic tools");
		moduleGenericToolsCheckBoxMenuItem.setSelected(true);
		moduleGenericToolsCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moduleGenericToolsCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(moduleGenericToolsCheckBoxMenuItem);

		modularRobotsjCheckBoxMenuItem.setText("Modular robots");
		modularRobotsjCheckBoxMenuItem.setSelected(true);        
		modularRobotsjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modularRobotsjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(modularRobotsjCheckBoxMenuItem);

		constructionjCheckBoxMenuItem.setText("Construction");
		constructionjCheckBoxMenuItem.setSelected(true);
		constructionjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				constructionjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(constructionjCheckBoxMenuItem);

		labeljCheckBoxMenuItem.setText("Label");
		labeljCheckBoxMenuItem.setSelected(true);
		labeljCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				labeljCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(labeljCheckBoxMenuItem);

		moduleLabelsjCheckBoxMenuItem.setText("Module Labels");
		moduleLabelsjCheckBoxMenuItem.setSelected(true);
		moduleLabelsjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moduleLabelsjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(moduleLabelsjCheckBoxMenuItem);

		assistantCheckBoxMenuItem.setText("Assistant");
		assistantCheckBoxMenuItem.setSelected(true);
		assistantCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				assistantCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(assistantCheckBoxMenuItem);

		viewjMenu.add(toolBarsjMenu);

		jMenuBar1.add(viewjMenu);

		setJMenuBar(jMenuBar1);

		pack();
	}// </editor-fold>                        

	/**
	 * Adapts GUI to the modular robot existing in simulation environment   
	 */
	private void adaptGuiToModularRobot(){

		String modularRobotName ="";
		if (JME_simulation.worldDescription.getModulePositions().size()>0){
			modularRobotName = JME_simulation.getModules().get(0).getProperty("ussr.module.type");	
		}		
		if (modularRobotName.contains("Odin")){
			modularRobotjComboBox.setSelectedIndex(2);			
		}else if (modularRobotName.contains("ATRON")){
			modularRobotjComboBox.setSelectedIndex(0);                        
			connectorsjComboBox.setSelectedIndex(0);
		}else if (modularRobotName.contains("MTRAN")){
			modularRobotjComboBox.setSelectedIndex(1);	
			connectorsjComboBox.setSelectedIndex(0);
		}

		standardRotationsjComboBox.setSelectedIndex(0);
/*NOTE*/		cartesianCoordinatejComboBox.setSelectedIndex(0);
		nextjButton.setEnabled(false);
		previousjButton.setEnabled(false);
		guiUtil.passTo(AssistantjTextField,"Select one of MR names in comboBox(4th toolbar) ");// informing user

	}

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void assistantCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                          
		System.out.println("View-->Toolbars-->Assistant");//for debugging
		this.guiUtil.changeToolBarVisibility(assistantCheckBoxMenuItem, AssistantjToolBar); 
	}                                                         

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void moduleLabelsjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                              
		System.out.println("View-->Toolbars-->Module labels");//for debugging
		this.guiUtil.changeToolBarVisibility(moduleLabelsjCheckBoxMenuItem, moduleLabelsToolBar); 
	}                                                             

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void labeljCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                       
		System.out.println("View-->Toolbars-->Label");//for debugging
		this.guiUtil.changeToolBarVisibility(labeljCheckBoxMenuItem, labeljToolBar);
	}                                                      

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void constructionjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                              
		System.out.println("View-->Toolbars-->Construction");//for debugging
		this.guiUtil.changeToolBarVisibility(constructionjCheckBoxMenuItem, constructionToolBar);
	}                                                             

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void modularRobotsjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                               
		System.out.println("View-->Toolbars-->Modular robots");//for debugging
		this.guiUtil.changeToolBarVisibility(modularRobotsjCheckBoxMenuItem, modularRoborGenericjToolBar);
	}                                                              

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void moduleGenericToolsCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                                   
		System.out.println("View-->Toolbars-->Module generic tools");//for debugging 
		this.guiUtil.changeToolBarVisibility(moduleGenericToolsCheckBoxMenuItem, moduleGenericToolsJToolBar);
	}                                                                  

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void renderjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		System.out.println("View-->Toolbars-->Rendering");//for debugging
		this.guiUtil.changeToolBarVisibility(renderjCheckBoxMenuItem, renderjToolBar);
	}                                                       

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void simulationjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                            
		System.out.println("View-->Toolbars-->Simulation");//for debugging
		this.guiUtil.changeToolBarVisibility(simulationjCheckBoxMenuItem, simulationToolBar);

	}                                                           

	private int counter =-1;
	public void setCounter(int counter) {
		this.counter = counter;
	}

	private void testjButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.counter++;
		System.out.println("Continue to simulation button");//for debugging
		//JME_simulation.setPicker(new PhysicsPicker());
		// JME_simulation.setPause(false);
/*NOTE*/		//instanceFlag = false; 
		//this.dispose();		
/*NOTE*/ BuilderHelper.connectAllModules(JME_simulation);
		 //JME_simulation.setPicker(new MtranExperiment(JME_simulation,this, this.counter));
		
	}                                           

	private void moduleLabelsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                      
		//System.out.println("Module labels toolbar-->Labels comboBox");//for debugging

		currentLabeljTextField.setText(moduleLabelsjComboBox.getSelectedItem().toString());
	}                                                     


	private void readLabelsjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		System.out.println("Module labels toolbar-->Read");//for debugging     
		//JME_simulation.setPicker(new ReadLabels(this));
/*NOTE*/		//JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Module",LabelingTools.READ_LABELS, this));
		/*NOTE*/		JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Connector",LabelingTools.READ_LABELS, this));
	}                                                 

	private void removeLabeljButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		System.out.println("Label toolbar-->Remove");//for debugging
		//JME_simulation.setPicker(new AssignRemoveLabels(currentLabeljTextField.getText(),true));
/*NOTE*/	JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Module",currentLabeljTextField.getText(),LabelingTools.DELETE_LABEL));
		/*NOTE*/		//		 JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Connector",currentLabeljTextField.getText(),LabelingTools.DELETE_LABEL));                 
	}                                

	private void assignLabeljButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		System.out.println("Label toolbar-->Assign");//for debugging
		//JME_simulation.setPicker(new AssignRemoveLabels(currentLabeljTextField.getText(),false));
		/*NOTE*/ JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Module",currentLabeljTextField.getText(),LabelingTools.LABEL_MODULE));
		/*NOTE*/	//	JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,"Connector",currentLabeljTextField.getText(),LabelingTools.LABEL_CONNECTOR));
	}                                                  
/*NOTE*/	ConstructionToolSpecification constructionTools = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.LOOP,0);
	/**
	 * Initializes the tool for placing the modules on connector and later  moving it on the another. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */
	private void loopjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Construction toolbar-->Loop");//for debugging  
		guiUtil.passTo(AssistantjTextField,"1)Select " +this.chosenMRname +" module,2)use NEXT and BACK");
		nextjButton.setEnabled(true);
		previousjButton.setEnabled(true);
		this.connectorNr =0;
/*NOTE*/		ConstructionToolSpecification constructionToolsnew = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.LOOP,this.connectorNr);
		this.constructionTools = constructionToolsnew;
		JME_simulation.setPicker(constructionToolsnew); 
	}    
	/**
	 * Moves the module to the previous connector with decreasing number of connector. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */
	private void previousjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
		//System.out.println("Construction toolbar-->Previous");//for debugging 
		this.connectorNr--;
		if (this.chosenMRname.equalsIgnoreCase(defaultModularRobot) && this.connectorNr<0){
			this.connectorNr =7;//reset
		}else if (this.chosenMRname.equalsIgnoreCase(secondModularRobot) && this.connectorNr<0){this.connectorNr=5;}
		else if (this.chosenMRname.equalsIgnoreCase(thirdModularRobot) && this.connectorNr<0){this.connectorNr=11;}
		constructionTools.moveToNextConnector(this.connectorNr);
		guiUtil.passTo(AssistantjTextField,this.chosenMRname +" module is on connector number "+this.connectorNr);
	}                                               

	/**
	 * Moves the module to the next connector with increasing number of connector. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */
	private void nextjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Construction toolbar-->Next");//for debugging 
		this.connectorNr++;
		if (this.chosenMRname.equalsIgnoreCase(defaultModularRobot)){
			if (this.connectorNr>amountATRONConnectors){ this.connectorNr=0;}       
		}else if (this.chosenMRname.equalsIgnoreCase(secondModularRobot)){
			if (this.connectorNr>amountMTRANConnectors){ this.connectorNr=0;}
		}else if (this.chosenMRname.equalsIgnoreCase(thirdModularRobot)){
			if (this.connectorNr>amountOdinBallConnectors){ this.connectorNr=0;}
		}
		//TODO NEED TO GET BACK FOR ODIN WHICH TYPE OF MODULE IS SELECTED. 
		constructionTools.moveToNextConnector(this.connectorNr);
		//else if (this.chosenMRname.equalsIgnoreCase("Odin")){
		//if (this.connectorNr>amountOdinConnectors){ this.connectorNr=0;}
		//}
		guiUtil.passTo(AssistantjTextField,this.chosenMRname +" module is on connector number "+this.connectorNr);
	}                                           



	/**
	 * Initializes the tool for placing the modules on all connectors of the module selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void alljButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
		//System.out.println("Construction toolbar-->All");//for debugging 
/*NOTE*/	//	JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_ALL_CONNECTORS));
		JME_simulation.setPicker(new AssignController());
		guiUtil.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module");
	}                                          

	/**
	 * Initializes the tool for placing the modules on connector chosen in comboBox and later the module selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void connectorsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                    
		//System.out.println("Construction toolbar-->On chosen (comboBox) connector");//for debugging 
		int connectorNumber = Integer.parseInt(connectorsjComboBox.getSelectedItem().toString());
/*NOTE*/		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_CHOSEN_CONNECTOR,connectorNumber));
		guiUtil.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module");

	}                                                   

	/**
	 * Initializes the tool for placing the modules on connectors selected with left side of the mouse in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void onConnectorjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		//System.out.println("Construction toolbar-->On connector");//for debugging    
/*note*/		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
		guiUtil.passTo(AssistantjTextField,"Select connector on "+ this.chosenMRname +" modular robot");

	}                                                  

	/**
	 * Initializes the tool for variating the properties of modules selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void variatejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
		//System.out.println("Modular robot specific-->Variation");//for debugging 
		if (this.chosenMRname.contains(defaultModularRobot)||this.chosenMRname.contains(secondModularRobot)){ 
			guiUtil.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module to see its variations");
		}else if (this.chosenMRname.contains(thirdModularRobot)){
			guiUtil.passTo(AssistantjTextField,"Select OdinMuscle to chage it with other types of modules");
		}
/*NOTE*/		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.VARIATION));
	}                                              

	/**
	 * Initializes the tool for rotating modules selected in simulation environment with opposite rotation. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void oppositeRotationjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		//System.out.println("Modular robot generic toolbar-->Opposite");//for debugging 
/*note*/		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.OPPOSITE_ROTATION));        
		guiUtil.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module to rotate it opposite ");       
	}                                                       

	/**
	 * With each selection of cartesion coordinate in comboBox sets it as current.
	 * @param evt, selection with left side of the mouse event (jComboBox selection).     
	 */	
	private void cartesianCoordinatejComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                             
		this.cartesianCoordinate =cartesianCoordinatejComboBox.getSelectedItem().toString();         
	}                                                            

	/**
	 * Initializes the tool for rotating default modules selected in simulation environment with standard rotations. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void standardRotationsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {     
/*NOTE*/		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.STANDARD_ROTATIONS,standardRotationsjComboBox.getSelectedItem().toString()));		
		guiUtil.passTo(AssistantjTextField,"Select " +this.chosenMRname +"default module to rotate with "+ rotationName+ " rotation");

	}                                                          

	/**
	 * Adds default construction module at default position in simulation environmment.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void defaultModulejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
		//System.out.println("Modular robot specific-->Module");//for debugging     	
		CommonOperationsTemplate com = new ATRONOperationsTemplate(JME_simulation);
		CommonOperationsTemplate com1 = new MTRANOperationsTemplate(JME_simulation);
		CommonOperationsTemplate com2 = new OdinOperationsTemplate(JME_simulation);

		VectorDescription zeroPosition = new VectorDescription(0,0,0);		

		if (this.chosenMRname.equalsIgnoreCase("ATRON")&& moduleExists(atronDefaultPosition)==false ){
			com.addDefaultConstructionModule(this.chosenMRname, atronDefaultPosition);
		}else if (this.chosenMRname.equalsIgnoreCase("MTRAN")&& moduleExists(mtranDefaultPosition)==false){
			com1.addDefaultConstructionModule(this.chosenMRname,mtranDefaultPosition );
/*Note*/	//com1.addDefaultConstructionModule(this.chosenMRname,new VectorDescription(mtranDefaultPosition.getX()+0.2f,mtranDefaultPosition.getY(), mtranDefaultPosition.getZ()) );
			
		}else if (this.chosenMRname.equalsIgnoreCase("Odin")&& moduleExists(odinDefaultPosition)==false){
/*NOTE*/			Odin.setDefaultConnectorSize(0.006f);
			com2.addDefaultConstructionModule(this.chosenMRname, odinDefaultPosition);
		}else {
			//com.addDefaultConstructionModule(this.chosenMRname, zeroPosition);
			//com1.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
			//com2.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
		}

	}                                                    

	/**
	 * Checks if module already exists at current position.
	 * @param currentPosition, the position of the module to check.     
	 *@return true, if module exists at current position.
	 */	
	private boolean moduleExists(VectorDescription currentPosition ){
		int amountModules = JME_simulation.getModules().size();
		for (int module =0;module<amountModules;module++){
			Module currentModule =JME_simulation.getModules().get(module); 
			String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());
			VectorDescription modulePosition;
			if (moduleType.equalsIgnoreCase("MTRAN")){
				modulePosition = currentModule.getPhysics().get(1).getPosition(); 
			}else{
				modulePosition = currentModule.getPhysics().get(0).getPosition();
			}
			if (modulePosition.equals(currentPosition)){
				return true;
			}
		}
		return false;    	
	}

	/**
	 * Adapts GUI functionality to chosen(in jComboBox) modular robot name.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void modularRobotjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                      
		//System.out.println("Modular robot specific-->ComboBox with names of MR");//for debugging 
		this.chosenMRname = modularRobotjComboBox.getSelectedItem().toString();
		if (this.chosenMRname.equalsIgnoreCase(defaultModularRobot)){
			standardRotationsjComboBox.setEnabled(true);        
			connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5", "6","7"}));
			standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EW", "WE", "DU", "UD", "SN", "NS" }));

		}else if (this.chosenMRname.equalsIgnoreCase(secondModularRobot)){
			standardRotationsjComboBox.setEnabled(true);
			connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5"}));
			standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ORI1", "ORI1X", "ORI1Y", "ORI1XY", "ORI2", "ORI2X", "ORI2Y", "ORI2XY", "ORI3", "ORI3X", "ORI3Y", "ORI3XY" }));

		}else if (this.chosenMRname.equalsIgnoreCase(thirdModularRobot)){
			standardRotationsjComboBox.setEnabled(false);
			connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5", "6","7", "8","9", "10", "11"}));
			standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));            
		}
		nextjButton.setEnabled(false);
		previousjButton.setEnabled(false);
		guiUtil.passTo(AssistantjTextField,this.chosenMRname +" modular robot (MR) is chosen");        
	}                                                     

	/**
	 * Initializes the tool for colouring the connectors of the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void rotateComponentjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                       
		// System.out.println("Module generic toolbar-->Rotate"); //for debugging        
		guiUtil.passTo(AssistantjTextField, "Select component to rotate"+ rotationAnglejTextField.getText()+ "degrees" );// informing user
		float angle = Float.parseFloat(rotationAnglejTextField.getText()); 

		JME_simulation.setPicker(new RotateModuleComponents(this.cartesianCoordinate ,angle));        
	}                                                      

	/**
	 * Initializes the tool for colouring the connectors of the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void colourConnectorsjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		//System.out.println("Module generic toolbar-->Color connectors"); //for debugging         
		guiUtil.passTo(AssistantjTextField, "Select module to color its connectors");// informing user
		JME_simulation.setPicker(new ColorConnectors());
	}                                                       

	private void alljCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                             
		if (alljCheckBox.isSelected()==true){
			this.allCheckBoxSelected = true;
		}else{
			this.allCheckBoxSelected = false;
		}
	}                                            

	/**
	 * Initializes the tool for deleting the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void deletejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
		//System.out.println("Module generic toolbar-->Delete"); //for debugging        
		guiUtil.passTo(AssistantjTextField, "Select module to delete");// informing user       
		JME_simulation.setPicker(new RemoveModule());        
	}                                             

	/**
	 * Initializes the tool for moving the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void movejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Module generic toolbar-->Move"); //for debugging  
		JME_simulation.setPicker(new PhysicsPicker(true, true));
/*NOTE*/		//JME_simulation.setPicker(new PhysicsPicker(true, false));
		guiUtil.passTo(AssistantjTextField, "Pick and move module");// informing user   

	}                                           

	/**
	 * Renders or stops reneding the lights during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	
	private void lightsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
		//System.out.println("RenderToolbar-->Lights");//for debugging    
		if (JME_simulation.getLightState().isEnabled() == false ){                       
			lightsjCheckBox.setSelected(true);
			JME_simulation.getLightState().setEnabled(true);
			guiUtil.passTo(AssistantjTextField, "Rendering lights");// informing user
		}else {                   
			lightsjCheckBox.setSelected(false);
			JME_simulation.getLightState().setEnabled(false);
			guiUtil.passTo(AssistantjTextField, "Stopped rendering lights");// informing user
		}             
	}                                               

	/**
	 * Renders or stops reneding the normals during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	
	private void normalsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("RenderToolbar-->Normals");//for debugging       
		if ( JME_simulation.isShowNormals() == false ){            
			normalsjCheckBox.setSelected(true);
			JME_simulation.setShowNormals(true);
			guiUtil.passTo(AssistantjTextField, "Rendering normals");// informing user
		}else {            
			normalsjCheckBox.setSelected(false);
			JME_simulation.setShowNormals(false);
			guiUtil.passTo(AssistantjTextField, "Stopped rendering normals");// informing user
		}         
	}                                                

	/**
	 * Renders or stops reneding the bounds during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	
	private void boundsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
		// System.out.println("Render toolbar-->Wireframe");//for debugging        
		if ( JME_simulation.getWireState().isEnabled() == false ){        
			boundsjCheckBox.setSelected(true);
			JME_simulation.getWireState().setEnabled(true);
			guiUtil.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			boundsjCheckBox.setSelected(false);
			JME_simulation.getWireState().setEnabled(false);
			guiUtil.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}        
	}                                               

	/**
	 * Renders or stops reneding the wireframe during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	 
	private void wireFramejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		// System.out.println("Render toolbar-->Wireframe");//for debugging        
		if ( JME_simulation.getWireState().isEnabled() == false ){        
			wireFramejCheckBox.setSelected(true);
			JME_simulation.getWireState().setEnabled(true);
			guiUtil.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			wireFramejCheckBox.setSelected(false);
			JME_simulation.getWireState().setEnabled(false);
			guiUtil.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}       
	}                                                  

	/**
	 * Renders or stops reneding physics during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	 
	private void physicsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("Render toolbar-->Physics");//for debugging        
		if (JME_simulation.isShowPhysics() == false ){                             
			physicsjCheckBox.setSelected(true);
			JME_simulation.setShowPhysics(true);
			guiUtil.passTo(AssistantjTextField, "Rendering physics");// informing user
		}else {                         
			physicsjCheckBox.setSelected(false);
			JME_simulation.setShowPhysics(false);
			guiUtil.passTo(AssistantjTextField, "Stopped rendering physics");// informing user
		}        
	}                                                

	/**
	 * Opens the file chooser for loading XML file into simulation.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void openjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Simulation toolbar-->Open");//for debugging                   
		this.fileChooserOpen = new FileChooser(JME_simulation,true); 
		fileChooserOpen.activate();   
		guiUtil.passTo(AssistantjTextField, "Load XML file containing construction data");

	}                                           

	/**
	 * Opens the file chooser for saving the data about moprhology of modular robot in XML file.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void savejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Simulation toolbar-->Save");//for debugging            
		this.fileChooserSave = new FileChooser(JME_simulation,false); 
		this.fileChooserSave.activate(); 	
		guiUtil.passTo(AssistantjTextField, "Save construction  data in XML file");

	}                                           

	/**
	 * Opens the file chooser for saving the data about moprhology of modular robot in XML file.
	 * @param evt, selection with left side of the mouse event (jMenuItem selection).     
	 */	
	private void OpenXMLJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("File-->Open");//for debugging
		this.fileChooserOpen = new FileChooser(JME_simulation,true); 
		fileChooserOpen.activate();         
		guiUtil.passTo(AssistantjTextField,"Load XML file containing construction data");// informing user
	}                                                

	/**
	 *  Activates step by step simulation. This method for single step. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	   
	private void stepByStepjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		//System.out.println("Simulation toolbar-->StepByStep");//for debugging               
		simulationStep++;
		guiUtil.passTo(AssistantjTextField, "Executed simulation step Nr: "+ simulationStep);
		JME_simulation.setSingleStep(true);
	}                                                 

	/**
	 * Opens the file chooser for saving the data about moprhology of modular robot in XML file.
	 * @param evt, selection with left side of the mouse event (jMenuItem selection).     
	 */	
	private void saveAsjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
		//System.out.println("File-->Save as");//for debugging                
		this.fileChooserSave = new FileChooser(JME_simulation,false); 
		fileChooserSave.activate();
		guiUtil.passTo(AssistantjTextField,"Save construction  data in XML file");// informing user

	}                                               

	private void filejMenuActionPerformed(java.awt.event.ActionEvent evt) {                                          
		// TODO add your handling code here:
	}                                         


	/**
	 * Pauses or starts running the simulation, depending on the current state of simulation.    
	 * @param evt, selection with left side of the mouse event (Button selection).     
	 */	
	private void pauseRunButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
		//System.out.println("Simulation and Rendering toolbar -->Pause/Play");//for debugging              
		if (JME_simulation.isPaused()==false){ 
			guiUtil.passTo(AssistantjTextField, "Simulation is in static state");// informing user
			JME_simulation.setPause(true);                       
			pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + pauseIcon));

		}else{			
			guiUtil.passTo(AssistantjTextField, "Simulation running");// informing user
			pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + playIcon));
			JME_simulation.setPause(false);
		}
	}                                              

	/**
	 * Closes the window ("Quick Prototyping of Simulation scenarios").    
	 * @param evt, selection with left side of the mouse event (Menu item selection).     
	 */	    
	private void ExitjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                              
		//System.out.println("File-->Exit");//for debugging
		instanceFlag = false; // reset the flag
		this.dispose();    
	}                                             

	/**
	 * @param args the command line arguments
	 */
	/*   public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();                
                double width = screenDimension.getWidth()/2.8f;
                double height = screenDimension.getHeight()/2.2f;
                String newWidth = (width+"").replace(".","#");
                String newHeight = (height+"").replace(".","#");
                String[] splittedWidth  = newWidth.split("#");
                String[] splittedHeight  = newHeight.split("#"); 


                int windowWidth =Integer.parseInt(splittedWidth[0].toString()); 
                int windowHeight =Integer.parseInt(splittedHeight[0].toString());
                int toolBarHeight = 40;
                //constructionToolBar.setSize(windowWidth,toolBarHeight);
                //AssistantjToolBar.setSize(windowWidth,toolBarHeight);                
                QuickPrototyping quickPrototyping = new QuickPrototyping();
                quickPrototyping.setVisible(true);                
                quickPrototyping.setSize(windowWidth,windowHeight);

            }
        });
    }*/

	public static void activate(final JMEBasicGraphicalSimulation simulation) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();                
				double width = screenDimension.getWidth()/2.8f;
				double height = screenDimension.getHeight()/2.2f;
				String newWidth = (width+"").replace(".","#");
				String newHeight = (height+"").replace(".","#");
				String[] splittedWidth  = newWidth.split("#");
				String[] splittedHeight  = newHeight.split("#"); 


				int windowWidth =Integer.parseInt(splittedWidth[0].toString()); 
				int windowHeight =Integer.parseInt(splittedHeight[0].toString());
				int toolBarHeight = 40;
				//constructionToolBar.setSize(windowWidth,toolBarHeight);
				//AssistantjToolBar.setSize(windowWidth,toolBarHeight);                
				QuickPrototyping quickPrototyping = new QuickPrototyping(simulation);
				quickPrototyping.setVisible(true);                
				quickPrototyping.setSize(windowWidth,windowHeight);
			}
		});
	}
	
	public static boolean isInstanceFlag() {
		return instanceFlag;
	}

	public javax.swing.JComboBox getModuleLabelsjComboBox() {
		return moduleLabelsjComboBox;
	}

	public javax.swing.JTextField getCurrentLabeljTextField() {
		return currentLabeljTextField;
	}

	// Variables declaration - do not modify                     
	private javax.swing.JTextField AssistantjTextField;
	private javax.swing.JToolBar AssistantjToolBar;
	private javax.swing.JMenuItem ExitjMenuItem;
	private javax.swing.JLabel ModuleLabelsjLabel;
	private javax.swing.JMenuItem OpenXMLJMenuItem;
	private javax.swing.JButton alljButton;
	private javax.swing.JCheckBox alljCheckBox;
	private javax.swing.JButton assignLabeljButton;
	private javax.swing.JCheckBoxMenuItem assistantCheckBoxMenuItem;
	private javax.swing.JLabel assistantjLabel;
	private javax.swing.JCheckBox boundsjCheckBox;
	private javax.swing.JComboBox cartesianCoordinatejComboBox;
	private javax.swing.JButton colourConnectorsjButton;
	private javax.swing.JComboBox connectorsjComboBox;
	private javax.swing.JToolBar constructionToolBar;
	private javax.swing.JCheckBoxMenuItem constructionjCheckBoxMenuItem;
	private javax.swing.JTextField currentLabeljTextField;
	private javax.swing.JButton defaultModulejButton;
	private javax.swing.JButton deletejButton;
	private javax.swing.JMenu filejMenu;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JCheckBoxMenuItem labeljCheckBoxMenuItem;
	private javax.swing.JToolBar labeljToolBar;
	private javax.swing.JLabel labelsjLabel;
	private javax.swing.JCheckBox lightsjCheckBox;
	private javax.swing.JButton loopjButton;
	private javax.swing.JToolBar modularRoborGenericjToolBar;
	private javax.swing.JComboBox modularRobotjComboBox;
	private javax.swing.JCheckBoxMenuItem modularRobotsjCheckBoxMenuItem;
	private javax.swing.JCheckBoxMenuItem moduleGenericToolsCheckBoxMenuItem;
	private javax.swing.JToolBar moduleGenericToolsJToolBar;
	private javax.swing.JToolBar moduleLabelsToolBar;
	private javax.swing.JCheckBoxMenuItem moduleLabelsjCheckBoxMenuItem;
	private javax.swing.JComboBox moduleLabelsjComboBox;
	private javax.swing.JButton movejButton;
	private javax.swing.JButton nextjButton;
	private javax.swing.JCheckBox normalsjCheckBox;
	private javax.swing.JButton onConnectorjButton;
	private javax.swing.JButton openjButton;
	private javax.swing.JButton oppositeRotationjButton;
	private javax.swing.JButton pauseRunButton;
	private javax.swing.JCheckBox physicsjCheckBox;
	private javax.swing.JButton previousjButton;
	private javax.swing.JButton readLabelsjButton;
	private javax.swing.JButton removeLabeljButton;
	private javax.swing.JCheckBoxMenuItem renderjCheckBoxMenuItem;
	private javax.swing.JToolBar renderjToolBar;
	private javax.swing.JButton rotateComponentjButton;
	private javax.swing.JTextField rotationAnglejTextField;
	private javax.swing.JMenuItem saveAsjMenuItem;
	private javax.swing.JButton savejButton;
	private javax.swing.JToolBar simulationToolBar;
	private javax.swing.JCheckBoxMenuItem simulationjCheckBoxMenuItem;
	private javax.swing.JComboBox standardRotationsjComboBox;
	private javax.swing.JButton stepByStepjButton;
	private javax.swing.JButton testjButton;
	private javax.swing.JMenu toolBarsjMenu;
	private javax.swing.JButton variatejButton;
	private javax.swing.JMenu viewjMenu;
	private javax.swing.JCheckBox wireFramejCheckBox;
	// End of variables declaration                   

	

}

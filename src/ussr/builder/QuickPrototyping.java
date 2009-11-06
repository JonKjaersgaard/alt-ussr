package ussr.builder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Vector;
import ussr.builder.constructionTools.ATRONOperationsTemplate;
import ussr.builder.constructionTools.CKBotOperationsTemplate;
import ussr.builder.constructionTools.CommonOperationsTemplate;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.ConstructionTools;
import ussr.builder.constructionTools.MTRANOperationsTemplate;
import ussr.builder.constructionTools.OdinOperationsTemplate;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.experimental.MtranExperiment;
import ussr.builder.experimental.NewSelection;
import ussr.builder.genericTools.ColorConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.builder.genericTools.RotateModulesComponents;
import ussr.builder.gui.FileChooser;
import ussr.builder.gui.GuiHelper;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.labelingTools.LabeledEntities;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.builder.labelingTools.LabelingTools;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.samples.odin.modules.Odin;

/**
 * The main responsibility of this class is to take care of the main GUI window for  project called
 * "Quick Prototyping of Simulation Scenarios" (QPSS).   
 * USER GUIDE.
 * In order to start QPSS or so called interactive builder do the following:
 * 1) Locate simulation file called "BuilderMultiRobotSimulation.java in the same package as this
 *    class". It can be also used any other simulation class for any supported modular robots, like ATRON,M-Tran and Odin.
 *    However then the bugs should be expected there.   
 * 2) Start  simulation for above file. The simulation should be in static state 
 * 	  (paused state),meaning that you should not press "P" button on keyboard. At least not yet.
 * 3) Press "Q" button on keyboard and wait a bit. On slow machines the "Quick Prototyping of Simulation 
 *    Scenarios" window is quite slow to respond. 
 * 4) In appeared QPSS window choose one of the buttons(pickers,also
 *    called selection tools)in toolbars of GUI. You can identify the name of the toolbar by going to View-->
 *    Toolbars-->.... The following explanation is for each toolbar counting from the top:
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
 *         a) "ComboBox with Module item selected" - is for choosing the entity to manipulate (rotate or move);
 *         b) "Move" - is the selection tool, where after selecting the module with left side of the mouse
 *            it is moved with movement of the mouse to desired location; Is added as additional support and 
 *            its main purpose is to move default modules. The icon on the button reminds the cross. 
 *         c) "Delete " - is the selection tool, where after selecting the module with left side of the mouse
 *            it is deleted(removed)from simulation environment; The icon on the button reminds the cross rotated 45 degrees.
 *         d) "C"- is the selection tool, where after selecting the module with left side of the mouse
 *            its connectors are colored with color coding. The format is Connector-Color: 0-Black, 1-Red,
 *            2-Cyan, 3-Grey, 4-Green, 5-Magenta, 6-Orange, 7-Pink, 8-Blue, 9-White, 10-Yellow, 11-Light Grey.
 *         e) "Cartesian coordinates (in ComboBox (x,y and z))", "TextField for angle of rotation" and "Rotate Icon"
 *            are used together to rotate components of the modules with specific angle. First choose one of the cartesian
 *            coordinates in ComboBox (for example: "x"), after that enter the angle in degrees in the TextField (let say 90) and at last select
 *            "Rotate" icon. After all that select one of the components of the module in simulation environment.
 *            This tool is experimental and can be used for small adornments *          	
 *    4.4)Modular robots
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
 *      4.6) Assign Label
 *         a) "ComboBox with the names of  entities" is for choosing the entity to assign the label to.
 *            Currently are supported: Module and Connector.
 *         b) "Text field with the title :""type in label name" is for typing in the name of the label. The format
 *            for entering several labels is for example: label1,label2, label3 . Notice comma is used
 *            as separation sign. In this way it is possible to assign several labels at a time.
 *         c) "Assign" button - is for actually assigning the label to the desired entity. Execute a) and b)
 *            above, after that press this button and the entity in simulation environment.
 *         d) "Remove" - is for removing specific label from the labels assigned to the entity. First type in the
 *            label name in the text field (b) and then press "remove", after that select the entity in simulation
 *            environment for which you would like to remove specified label.
 *            NOTICE: There should not be any comma for one label.
 *       4.7) Read Labels
 *         a) "comboBox with title "Current Labels" is for displaying the labels assigned to the entity. Just expand the 
 *             the "comboBox" and see which labels are assigned. You can also choose one of them and then it will
 *             be displayed in the textField (toolBar above, b)). After that you can reassign it to any other module.
 *         b) "Read" is for reading the labels assigned to the entity. Just press this button and then select the 
 *             entity in simulation environment. As a result the labels assigned to this entity will be displayed in the
 *             comboBox above and textField (toolBar above, b)).         
 *       4.8) Controllers
 *         a)  "ComboBox with the names of the classes(controllers)" is for choosing the controller
 *            you would like assign to the module. Remember the controllers you created should be
 *            placed in the package named as "ussr.builder.controllerReassignmentTool" inherit from 
 *            "ControllerStrategy" and have activate() method implemented. Then your new controller 
 *            will be working  and displayed in the current comboBox.
 *         b) "Assign"  is for assigning chosen above controller to the module selected in simulation 
 *            environment. Press the button and select the module in simulation environment.
 *            NOTICE: The controller should be for the same modular  robot as you are selecting. 
 *       4.7) Assistant
 *            This toolbar simply gives the hints what to do next. Simple version of help.
 *            Most of the time it is reliable. However do not trust it blindly :).
 *                                                 
 * 5) Using the above selection tools construct desired morphology(shape) of  modular robot.
 *    Easiest may is to start from toolBar called "Modular robots" by choosing modular robot name
 *    in comboBox and pressing "Default" button, after that choosing appropriate rotation in rotations comboBox,
 *    later shift to toolbar called "Construction". By using one of the four tools construct morphology of modular robot.
 *    There is also toolBar called Assistant, which sometimes :) displays hints what to do next. Follow it, but
 *    do not trust it 100% :). Also you can assign labels to modules and so on. 
 *     When you are done with morphology press "Test before simulation" button at the bottom of GUI. If there are no errors
 *    or exceptions start simulation by pressing "Play" button in the first toolbar from the top.
 *    Now you can assign the controllers interactively to the modules by using the toolbar 
 *    above in the 4.8).
 *     
 *  
 * Additional functionality is ability to save the data about the modules in simulation
 * in XML format. Just choose File-->Save, this will open File chooser, also with quite a delay. Or select "save"
 * icon in "Quick Prototyping of Simulation Scenarios" window.
 * In order to open(load) previously saved XML file, choose File-->Open, this will open File chooser, also with 
 * quite a delay. Or select "open" icon in "Quick Prototyping of Simulation Scenarios" window.
 * There are several examples of modular robots morphologies kept in directory of USSR:
 * resources\quickPrototyping\loadXMLexamples. Just copy one of the files and paste somewhere on your
 * PC(for example desktop), after that load it by chosing File-->Open or "open" icon.
 * NOTICE: You can load and save the data into XML file only in the static(paused) state of simulation. 
 * 
 * In case there is a need to understand the code deeper look for the Design Class Diagram(sDCD) for all the code,
 * which can be found in USSR directory named as:"doc\developer\builderPackage"
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
	private final GuiHelper guiHelper = new GuiHelper();

	/**
	 *  The name of the current modular robot
	 */
	private SupportedModularRobots chosenMRname = SupportedModularRobots.ATRON; //MR- modular robot. Default is ATRON, just do not have the case when it is empty String

	/**
	 *  The default rotation of default modular robot
	 */
	private String rotationName ="EW";//Default, just do not have the case when it is empty String

	/**
	 * The current connector number one the module
	 */
	private int connectorNr =0;//default

	/**
	 * The amount of connectors on ATRON module
	 */
	private final static int amountATRONConnectors = 7;

	/**
	 * The amount of connectors on MTRAN module
	 */
	private final static int amountMTRANConnectors = 5;  

	/**
	 * The amount of connectors on OdinBall(Joint) module
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

	/**
	 * Instance flag for current frame, used to keep track that only one instance of the frame exists.
	 */
	private static boolean instanceFlag = false;

	/**
	 * Current Cartesian coordinate (x,y or z)
	 */
	private String cartesianCoordinate = "X"; // Default 

	/**
	 * The entity to move with the tool for moving objects in 3D simulation environment
	 */
	private String entity = "Module";// Default

	/**
	 * The default entity for labeling with labeling tools
	 */
	private LabeledEntities entityToLabel = LabeledEntities.MODULE;//default

	/**
	 * The name of the package where all the controllers are stored for interactive assignment
	 */
	private static final String packageName = "ussr.builder.controllerAdjustmentTool";

	/**
	 * The default controller name for interactive assignment 
	 */
	private String controllerName = "ATRONAxleController";

	/**
	 * Default positions of default modules of modular robots
	 */
	private final static VectorDescription atronDefaultPosition = new VectorDescription(0,-0.441f,0.5f),
	mtranDefaultPosition = new VectorDescription(-1f,-0.4621f,0.5f),	
	odinDefaultPosition = new VectorDescription(1f,-0.4646f,0.5f),	
	ckbotDefaultPosition = new VectorDescription(2f,-0.4646f,0.5f); 



	/** Creates new frame of Quick Prototyping of Simulation Scenarios */
	public QuickPrototyping(JMEBasicGraphicalSimulation simulation) {
		initComponents();
		//Set to generic view      
		guiHelper.changeToSetLookAndFeel(this);         
		this.JME_simulation = (JMESimulation) simulation;  
		adaptGuiToModularRobot();// Adapts GUI to modular robot existing in simulation environment
		JME_simulation.setPicker(new PhysicsPicker(true, true));//set default picker(selection tool)
		loadExistingControllers();// the load the names of controllers in GUI existing in the package called: "ussr.builder.controllerReassignmentTool".	
		instanceFlag = true;// the frame is instantiated
		// Overrides event for closing the frame in order for the frame to do not open several times with several selections on the button "Q" on keyboard.
		addWindowListener (new WindowAdapter() {			
			public void windowClosing(WindowEvent event) {
				instanceFlag = false; // reset the flag after closing the frame
				event.getWindow().dispose();	                     
			}
		}
		);		
	}



	/**
	 * Initializes all components and their appearance in QPSS frame(window).
	 */
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
		entityForMovingorRotatingjComboBox = new javax.swing.JComboBox();
		controllersjComboBox = new javax.swing.JComboBox();
		movejButton = new javax.swing.JButton();
		deletejButton = new javax.swing.JButton();	
		colourConnectorsjButton = new javax.swing.JButton();
		cartesianCoordinatejComboBox = new javax.swing.JComboBox();
		rotationAnglejTextField = new javax.swing.JTextField();
		rotateComponentjButton = new javax.swing.JButton();
		modularRoborGenericjToolBar = new javax.swing.JToolBar();
		modularRobotjComboBox = new javax.swing.JComboBox();
		newModulejButton = new javax.swing.JButton();
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
		specialjButton = new javax.swing.JButton();
		assignLabeljToolBar = new javax.swing.JToolBar();
		entityForLabeling= new javax.swing.JComboBox();
		labelsjLabel = new javax.swing.JLabel();
		controllersjLabel = new javax.swing.JLabel();
		currentLabeljTextField = new javax.swing.JTextField();
		assignLabeljButton = new javax.swing.JButton();
		removeLabeljButton = new javax.swing.JButton();
		moduleLabelsToolBar = new javax.swing.JToolBar();
		readLabelsjLabel = new javax.swing.JLabel();
		entityLabelsjComboBox = new javax.swing.JComboBox();
		readLabelsjButton = new javax.swing.JButton();
		AssistantjToolBar = new javax.swing.JToolBar();
		controllersjToolBar = new javax.swing.JToolBar();
		assistantjLabel = new javax.swing.JLabel();
		AssistantjTextField = new javax.swing.JTextField();
		jMenuBar1 = new javax.swing.JMenuBar();
		filejMenu = new javax.swing.JMenu();
		OpenXMLJMenuItem = new javax.swing.JMenuItem();
		saveAsjMenuItem = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();
		ExitjMenuItem = new javax.swing.JMenuItem();
		viewjMenu = new javax.swing.JMenu();
		toolBarsjMenu = new javax.swing.JMenu();
		controllersjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		simulationjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		renderjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		moduleGenericToolsCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		modularRobotsjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		constructionjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		assignLabeljCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		readLabelsjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		assistantCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();

		getContentPane().setLayout(new java.awt.FlowLayout());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);	
		setTitle("Quick Prototyping of Simulation Scenarios");
		simulationToolBar.setRollover(true);
		simulationToolBar.setPreferredSize(new java.awt.Dimension(50, 40));		
		pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + playIcon));
		pauseRunButton.setToolTipText("Play/Pause");
		pauseRunButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pauseRunButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(pauseRunButton);

		stepByStepjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons+stepByStepIcon));
		stepByStepjButton.setToolTipText("StepByStep");
		stepByStepjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepByStepjButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(stepByStepjButton);

		savejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + saveIcon));
		savejButton.setToolTipText("Save");
		savejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				savejButtonActionPerformed(evt);
			}
		});

		simulationToolBar.add(savejButton);

		openjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + openIcon));
		openjButton.setToolTipText("Open");
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
		physicsjCheckBox.setToolTipText("Physics");
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
		wireFramejCheckBox.setToolTipText("Wireframe");		
		wireFramejCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		wireFramejCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		wireFramejCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				wireFramejCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(wireFramejCheckBox);

		boundsjCheckBox.setText("Bounds");
		boundsjCheckBox.setToolTipText("Bounds");		
		boundsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		boundsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		boundsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boundsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(boundsjCheckBox);

		normalsjCheckBox.setText("Normals");
		normalsjCheckBox.setToolTipText("Normals");
		normalsjCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		normalsjCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
		normalsjCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				normalsjCheckBoxActionPerformed(evt);
			}
		});

		renderjToolBar.add(normalsjCheckBox);

		lightsjCheckBox.setText("Lights");
		lightsjCheckBox.setToolTipText("Lights");
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

		entityForMovingorRotatingjComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		entityForMovingorRotatingjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose entity","Module", "Component" }));
		entityForMovingorRotatingjComboBox.setToolTipText("Entity to Move or Rotate");
		entityForMovingorRotatingjComboBox.setPreferredSize(new java.awt.Dimension(120, 22));
		entityForMovingorRotatingjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				entityForMovingorRotatingjComboBoxActionPerformed(evt);
			}			
		});

		moduleGenericToolsJToolBar.add(entityForMovingorRotatingjComboBox);		

		movejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + moveIcon));
		movejButton.setToolTipText("Move Entity");
		movejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			//	movejButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(movejButton);

		deletejButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + deleteIcon));
		deletejButton.setToolTipText("Delete Module");
		deletejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deletejButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(deletejButton);
		moduleGenericToolsJToolBar.add(jSeparator2);

		colourConnectorsjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + colourConnectorsIcon));
		colourConnectorsjButton.setToolTipText("Colour Conectors of the Module ");
		colourConnectorsjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				colourConnectorsjButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(colourConnectorsjButton);

		cartesianCoordinatejComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		cartesianCoordinatejComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Axis","x", "y", "z" }));
		cartesianCoordinatejComboBox.setToolTipText("Coordinate Axis");
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

		rotateComponentjButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + rotateIcon));
		rotateComponentjButton.setToolTipText("Rotate Entity");
		rotateComponentjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rotateComponentjButtonActionPerformed(evt);
			}
		});

		moduleGenericToolsJToolBar.add(rotateComponentjButton);

		getContentPane().add(moduleGenericToolsJToolBar);

		modularRoborGenericjToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		modularRobotjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Choose modular robot beneath...", "ATRON", "MTRAN", "Odin","CKBotStandard" }));
		modularRobotjComboBox.setToolTipText("Supported Modular Robots");
		modularRobotjComboBox.setPreferredSize(new java.awt.Dimension(20, 43));
		modularRobotjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modularRobotjComboBoxActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(modularRobotjComboBox);

		newModulejButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		newModulejButton.setText("New Module");
		newModulejButton.setToolTipText("Add New Module of Chosen Modular Robot");
		newModulejButton.setPreferredSize(new java.awt.Dimension(80, 43));
		newModulejButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newModulejButtonActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(newModulejButton);

		standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Choose module rotation", "EW", "WE", "UD", "DU", "SN", "NS" }));
		standardRotationsjComboBox.setToolTipText("Standard Rotations of the Module");
		standardRotationsjComboBox.setPreferredSize(new java.awt.Dimension(30, 22));
		standardRotationsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				standardRotationsjComboBoxActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(standardRotationsjComboBox);

		oppositeRotationjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		oppositeRotationjButton.setText("Opposite");
		oppositeRotationjButton.setToolTipText("Opposite Rotation of the Module");
		oppositeRotationjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				oppositeRotationjButtonActionPerformed(evt);
			}
		});

		modularRoborGenericjToolBar.add(oppositeRotationjButton);

		variatejButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		variatejButton.setText("Variation");
		variatejButton.setToolTipText("Variation of the Modules");
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
		onConnectorjButton.setToolTipText("Add New Module on Selected Connector");
		onConnectorjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onConnectorjButtonActionPerformed(evt);
			}
		});		

		constructionToolBar.add(onConnectorjButton);

		connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose connector nr. beneath...","0", "1", "2", "3", "4", "5", "6", "7" }));
		connectorsjComboBox.setPreferredSize(new java.awt.Dimension(30, 22));
		connectorsjComboBox.setToolTipText("Add New Module on Specific Connector");
		connectorsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectorsjComboBoxActionPerformed(evt);
			}
		});

		constructionToolBar.add(connectorsjComboBox);

		alljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		alljButton.setText("All");
		alljButton.setToolTipText("Add New Modules on all Connectors");
		alljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				alljButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(alljButton);

		loopjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		loopjButton.setText("Loop");
		loopjButton.setToolTipText("Move Module from one Connector onto another");
		loopjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loopjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(loopjButton);

		nextjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		nextjButton.setText("Next");
		nextjButton.setToolTipText("On Next Connector");
		nextjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(nextjButton);

		previousjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		previousjButton.setText("Previous");
		previousjButton.setToolTipText("On Previous Connector");
		previousjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				previousjButtonActionPerformed(evt);
			}
		});

		constructionToolBar.add(previousjButton);

		specialjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		specialjButton.setText("Special");
		specialjButton.setToolTipText("Add New Module on Selected Connector");
		specialjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				specialjButtonActionPerformed(evt);
			}			
		});
		constructionToolBar.add(specialjButton);

		getContentPane().add(constructionToolBar);

		entityForLabeling.setFont(new java.awt.Font("Tahoma", 0, 12));
		entityForLabeling.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose entity","Module", "Connector", "Sensor"}));
		entityForLabeling.setToolTipText("Entity to Label");
		entityForLabeling.setPreferredSize(new java.awt.Dimension(120, 22));
		entityForLabeling.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				entityForLabelingjComboBoxActionPerformed(evt);
			}					
		});
		assignLabeljToolBar.add(entityForLabeling);


		assignLabeljToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		labelsjLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
		labelsjLabel.setText("Label:");
		assignLabeljToolBar.add(labelsjLabel);		
		currentLabeljTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
		currentLabeljTextField.setText("type in label name ");
		currentLabeljTextField.setToolTipText("Current Label");
		assignLabeljToolBar.add(currentLabeljTextField);	

		assignLabeljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		assignLabeljButton.setText("Assign");
		assignLabeljButton.setToolTipText("Assign Label");
		assignLabeljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				assignLabeljButtonActionPerformed(evt);
			}
		});

		assignLabeljToolBar.add(assignLabeljButton);

		removeLabeljButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		removeLabeljButton.setText("Remove");
		removeLabeljButton.setToolTipText("Remove Label");
		removeLabeljButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeLabeljButtonActionPerformed(evt);
			}
		});

		assignLabeljToolBar.add(removeLabeljButton);

		getContentPane().add(assignLabeljToolBar);

		moduleLabelsToolBar.setPreferredSize(new java.awt.Dimension(420, 40));
		readLabelsjLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
		readLabelsjLabel.setText("Current Labels:");		
		moduleLabelsToolBar.add(readLabelsjLabel);

		entityLabelsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Labels" }));
		entityLabelsjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				entityLabelsjComboBoxActionPerformed(evt);
			}
		});

		moduleLabelsToolBar.add(entityLabelsjComboBox);

		readLabelsjButton.setFont(new java.awt.Font("Tahoma", 0, 12));
		readLabelsjButton.setText("Read");
		readLabelsjButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				readLabelsjButtonActionPerformed(evt);
			}
		});

		moduleLabelsToolBar.add(readLabelsjButton);

		getContentPane().add(moduleLabelsToolBar);

		controllersjLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
		controllersjLabel.setText("Controllers:");
		controllersjToolBar.add(controllersjLabel);

		controllersjToolBar.setPreferredSize(new java.awt.Dimension(420, 35));
		controllersjComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		controllersjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Epty",  }));
		controllersjComboBox.setToolTipText("Entity");
		controllersjComboBox.setPreferredSize(new java.awt.Dimension(120, 22));
		controllersjComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				controllersjComboBoxActionPerformed(evt);
			}
		});	

		controllersjToolBar.add(controllersjComboBox);

		getContentPane().add(controllersjToolBar);

		AssistantjToolBar.setPreferredSize(new java.awt.Dimension(420, 35));
		assistantjLabel.setText("Assistant:");
		AssistantjToolBar.add(assistantjLabel);

		AssistantjTextField.setEditable(false);
		AssistantjToolBar.add(AssistantjTextField);

		getContentPane().add(AssistantjToolBar);

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

		assignLabeljCheckBoxMenuItem.setText("Assign Label");
		assignLabeljCheckBoxMenuItem.setSelected(true);
		assignLabeljCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				labeljCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(assignLabeljCheckBoxMenuItem);

		readLabelsjCheckBoxMenuItem.setText("Read Labels");
		readLabelsjCheckBoxMenuItem.setSelected(true);
		readLabelsjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moduleLabelsjCheckBoxMenuItemActionPerformed(evt);
			}
		});

		toolBarsjMenu.add(readLabelsjCheckBoxMenuItem);


		controllersjCheckBoxMenuItem.setText("Controllers");
		controllersjCheckBoxMenuItem.setSelected(true);
		controllersjCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				controllersjCheckBoxMenuItemActionPerformed(evt);
			}			
		});

		toolBarsjMenu.add(controllersjCheckBoxMenuItem);

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
			modularRobotjComboBox.setSelectedIndex(3);			
		}else if (modularRobotName.contains("ATRON")){
			modularRobotjComboBox.setSelectedIndex(1);                        
			connectorsjComboBox.setSelectedIndex(0);
			standardRotationsjComboBox.setSelectedIndex(0);
		}else if (modularRobotName.contains("MTRAN")){
			modularRobotjComboBox.setSelectedIndex(2);	
			connectorsjComboBox.setSelectedIndex(0);
			standardRotationsjComboBox.setSelectedIndex(1);
		}


		cartesianCoordinatejComboBox.setSelectedIndex(0);
		nextjButton.setEnabled(false);
		previousjButton.setEnabled(false);
		guiHelper.passTo(AssistantjTextField,"Choose one of the modular robot's names in the first comboBox of the 4th toolbar from the top ");// informing the user

	}

	/**
	 * Loads the names of the controllers existing in the package "ussr.builder.controllerReassignmentTool" into
	 * GUI comboBox called "controllersjComboBox". 
	 */
	private void loadExistingControllers(){
		Class[] classes = null;
		try {
			classes = BuilderHelper.getClasses(packageName);
		} catch (ClassNotFoundException e) {
			throw new Error ("The package named as: "+ packageName + "was not found in the directory of USSR");			
		}		

		/*Loop through the classes and take only controllers, but not the classes defining the tool*/
		Vector<String> classesOfControllers = new Vector<String>();
		classesOfControllers.add("Choose controller beneath...");// informing the user
		for (int i=0; i<classes.length;i++){
			if (classes[i].toString().contains("AssignControllerTool")||classes[i].toString().contains("ControllerStrategy")){
				//do nothing	
			}else{
				classesOfControllers.add(classes[i].toString().replace("class "+packageName+".", ""));
			}
		}	
		controllersjComboBox.setModel(new javax.swing.DefaultComboBoxModel(classesOfControllers.toArray()));
	}

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void assistantCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                          
		System.out.println("View-->Toolbars-->Assistant");//for debugging
		this.guiHelper.changeToolBarVisibility(assistantCheckBoxMenuItem, AssistantjToolBar); 
	}                                                         

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void moduleLabelsjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                              
		System.out.println("View-->Toolbars-->Module labels");//for debugging
		this.guiHelper.changeToolBarVisibility(readLabelsjCheckBoxMenuItem, moduleLabelsToolBar); 
	}                                                             

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void labeljCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                       
		System.out.println("View-->Toolbars-->Label");//for debugging
		this.guiHelper.changeToolBarVisibility(assignLabeljCheckBoxMenuItem, assignLabeljToolBar);
	}                                                      

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void constructionjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                              
		System.out.println("View-->Toolbars-->Construction");//for debugging
		this.guiHelper.changeToolBarVisibility(constructionjCheckBoxMenuItem, constructionToolBar);
	}                                                             

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void modularRobotsjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                               
		System.out.println("View-->Toolbars-->Modular robots");//for debugging
		this.guiHelper.changeToolBarVisibility(modularRobotsjCheckBoxMenuItem, modularRoborGenericjToolBar);
	}                                                              

	/**
	 * Sets toolbar visible or invisible depeding on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void moduleGenericToolsCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                                   
		System.out.println("View-->Toolbars-->Module generic tools");//for debugging 
		this.guiHelper.changeToolBarVisibility(moduleGenericToolsCheckBoxMenuItem, moduleGenericToolsJToolBar);
	}                                                                  

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void renderjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		System.out.println("View-->Toolbars-->Rendering");//for debugging
		this.guiHelper.changeToolBarVisibility(renderjCheckBoxMenuItem, renderjToolBar);
	}                                                       

	/**
	 * Sets toolbar visible or invisible depending on the current state of the toolbar. 
	 * @param evt, selection with left side of the mouse event (jCheckBoxMenuItem selection).     
	 */
	private void simulationjCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                            
		System.out.println("View-->Toolbars-->Simulation");//for debugging
		this.guiHelper.changeToolBarVisibility(simulationjCheckBoxMenuItem, simulationToolBar);

	}                              


	/**
	 * Identifies which entity was chosen for labeling  in entityForLabelingjComboBox.
	 * @param evt, selection with left side of the mouse event (entityForLabelingjComboBox selection)
	 */
	private void entityForLabelingjComboBoxActionPerformed(ActionEvent evt) {
		//System.out.println("Assign Label toolbar--> entityForLabelingjComboBox");//for debugging
		this.entityToLabel = LabeledEntities.valueOf(entityForLabeling.getSelectedItem().toString().toUpperCase());
		guiHelper.passTo(AssistantjTextField,this.entityToLabel + " is chosen for labeling. Type in the name of the label  in text field to the right and press ASSIGN button");// informing the user
	}

	/**
	 * Updates the text field with the label chosen from the entityLabelsjComboBox,
	 * which contains all the labels assigned to the entity. 
	 * @param evt, selection with left side of the mouse event (entityLabelsjComboBox selection)
	 */
	private void entityLabelsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                      
		//System.out.println("Assign Label toolbar--> entityLabelsjComboBox");//for debugging
		currentLabeljTextField.setText(entityLabelsjComboBox.getSelectedItem().toString());
		guiHelper.passTo(AssistantjTextField,entityLabelsjComboBox.getSelectedItem().toString()+ " label is chosen." + "Press ASSIGN button");// informing the user
	}                                                     


	/**
	 *  Reads and displays the labels of the chosen entity (module or connector)
	 * @param evt
	 */
	private void readLabelsjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		System.out.println("Labels toolbar-->Read");//for debugging     
		JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,this.entityToLabel,LabelingTools.READ_LABELS, this));
		guiHelper.passTo(AssistantjTextField,"Select "+ this.entityToLabel.toString().toLowerCase()+ " to display its labels");
	}  

	private void removeLabeljButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//DEAD CODE IF QPSS is removed
		/*System.out.println("Label toolbar-->Remove");//for debugging		
		JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,this.entityToLabel,currentLabeljTextField.getText(),LabelingTools.DELETE_LABEL));
		guiHelper.passTo(AssistantjTextField,"Select "+ this.entityToLabel.toString().toLowerCase()+ " to remove the label: "+  currentLabeljTextField.getText());*/
	}                                

	private void assignLabeljButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		System.out.println("Label toolbar-->Assign");//for debugging
		LabelingTools tool = null;
		if (this.entityToLabel.equals(LabeledEntities.MODULE)){
			tool = LabelingTools.LABEL_MODULE;
		}else if (this.entityToLabel.equals(LabeledEntities.CONNECTOR)){
			tool = LabelingTools.LABEL_CONNECTOR;
		}else if (this.entityToLabel.equals(LabeledEntities.SENSOR)){
			tool = LabelingTools.LABEL_SENSOR;
		}
		else throw new Error("The  name of the entity is misspelled or this entity is not yet supported");
		JME_simulation.setPicker(new LabelingToolSpecification(JME_simulation,this.entityToLabel,currentLabeljTextField.getText(),tool));

		guiHelper.passTo(AssistantjTextField,"Select "+ this.entityToLabel.toString().toLowerCase()+ " to assign to it the label: " + currentLabeljTextField.getText());
	}                                                  
	ConstructionToolSpecification constructionTools = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.LOOP,0);
	/**
	 * Initializes the tool for placing the modules on connector and later  moving it on the another. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */
	private void loopjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Construction toolbar-->Loop");//for debugging  
		guiHelper.passTo(AssistantjTextField,"1)Select " +this.chosenMRname +" module,2)use NEXT and BACK");
		nextjButton.setEnabled(true);
		previousjButton.setEnabled(true);
		this.connectorNr =0;
		ConstructionToolSpecification constructionToolsnew = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.LOOP,this.connectorNr);
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
		if (this.chosenMRname.equals(SupportedModularRobots.ATRON) && this.connectorNr<0){
			this.connectorNr =7;//reset
		}else if (this.chosenMRname.equals(SupportedModularRobots.MTRAN) && this.connectorNr<0){this.connectorNr=5;}
		else if (this.chosenMRname.equals(SupportedModularRobots.ODIN) && this.connectorNr<0){this.connectorNr=11;}

		int amountModules = JME_simulation.getModules().size();
		String lastModuleType = JME_simulation.getModules().get(amountModules-1).getProperty(BuilderHelper.getModuleTypeKey());
		if (lastModuleType.equalsIgnoreCase("OdinBall")){
			//do nothing
		}else{
			constructionTools.moveToNextConnector(this.connectorNr);
		}

		guiHelper.passTo(AssistantjTextField,this.chosenMRname +" module is on connector number "+this.connectorNr);
	}  

	int nrCounter =-1;
	private void specialjButtonActionPerformed(ActionEvent evt) {
		if (nrCounter ==1){
			nrCounter=-1;
		}
		nrCounter++;
		//experimental
		JME_simulation.setPicker(new NewSelection(JME_simulation,nrCounter));

	}

	/**
	 * Moves the module to the next connector with increasing number of connector. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */
	private void nextjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Construction toolbar-->Next");//for debugging 
		this.connectorNr++;
		if (this.chosenMRname.equals(SupportedModularRobots.ATRON)){
			if (this.connectorNr>amountATRONConnectors){ this.connectorNr=0;}       
		}else if (this.chosenMRname.equals(SupportedModularRobots.MTRAN)){
			if (this.connectorNr>amountMTRANConnectors){ this.connectorNr=0;}
		}else if (this.chosenMRname.equals(SupportedModularRobots.ODIN)){
			if (this.connectorNr>amountOdinBallConnectors){ this.connectorNr=0;}
		}
		//TODO NEED TO GET BACK FOR ODIN WHICH TYPE OF MODULE IS SELECTED. 
		int amountModules = JME_simulation.getModules().size();
		String lastModuleType = JME_simulation.getModules().get(amountModules-1).getProperty(BuilderHelper.getModuleTypeKey());
		if (lastModuleType.equalsIgnoreCase("OdinBall")){
			//do nothing
		}else{
			constructionTools.moveToNextConnector(this.connectorNr);
		}
		//else if (this.chosenMRname.equalsIgnoreCase("Odin")){
		//if (this.connectorNr>amountOdinConnectors){ this.connectorNr=0;}
		//}
		guiHelper.passTo(AssistantjTextField,this.chosenMRname +" module is on connector number "+this.connectorNr);
	}                                           



	/**
	 * Initializes the tool for placing the modules on all connectors of the module selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void alljButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
		//System.out.println("Construction toolbar-->All");//for debugging 
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_ALL_CONNECTORS));	
		guiHelper.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module");
	} 

	/**
	 * Initializes the tool for placing the modules on connector chosen in comboBox and later the module selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void connectorsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                    
		//System.out.println("Construction toolbar-->On chosen (comboBox) connector");//for debugging 

		if (connectorsjComboBox.getSelectedItem().toString().contains("Choose")){}
		else{
			this.connectorNr = Integer.parseInt(connectorsjComboBox.getSelectedItem().toString());
		}
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_CHOSEN_CONNECTOR,this.connectorNr));
		guiHelper.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module");

	}                                                   

	/**
	 * Initializes the tool for placing the modules on connectors selected with left side of the mouse in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void onConnectorjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		//System.out.println("Construction toolbar-->On connector");//for debugging    
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));
		guiHelper.passTo(AssistantjTextField,"Select connector on "+ this.chosenMRname +" modular robot");
	}                                                  

	/**
	 * Initializes the tool for variating the properties of modules selected in simulation environment. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void variatejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
		//System.out.println("Modular robot specific-->Variation");//for debugging 
		if (this.chosenMRname.equals(SupportedModularRobots.ATRON)||this.chosenMRname.equals(SupportedModularRobots.MTRAN)){ 
			guiHelper.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module to see its variations");
		}else if (this.chosenMRname.equals(SupportedModularRobots.MTRAN)){
			guiHelper.passTo(AssistantjTextField,"Select OdinMuscle to chage it with other types of modules");
		}
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.VARIATION));
	}                                              

	/**
	 * Initializes the tool for rotating modules selected in simulation environment with opposite rotation. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void oppositeRotationjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		//System.out.println("Modular robot generic toolbar-->Opposite");//for debugging 
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.OPPOSITE_ROTATION));        
		guiHelper.passTo(AssistantjTextField,"Select " +this.chosenMRname +" module to rotate it opposite ");       
	}                                                       

	/**
	 * With each selection of cartesion coordinate in comboBox sets it as current.
	 * @param evt, selection with left side of the mouse event (jComboBox selection).     
	 */	
	private void cartesianCoordinatejComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                             
		this.cartesianCoordinate =cartesianCoordinatejComboBox.getSelectedItem().toString();
		guiHelper.passTo(AssistantjTextField,this.cartesianCoordinate +" was chosen, now type in the angle to rotate "+ this.entity+ " around it in the textfield and press button Rotate. ");       
	}                                                            

	/**
	 * Initializes the tool for rotating default modules selected in simulation environment with standard rotations. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void standardRotationsjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {     
		JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.STANDARD_ROTATIONS,standardRotationsjComboBox.getSelectedItem().toString()));		
		guiHelper.passTo(AssistantjTextField,"Select " +this.chosenMRname +"default module to rotate with "+ rotationName+ " rotation");
	}                                                          

	/**
	 * Adds default construction module at default position in simulation environmment.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void newModulejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
		//System.out.println("Modular robot specific--> New Module");//for debugging     	
		CommonOperationsTemplate comATRON = new ATRONOperationsTemplate(JME_simulation);
		CommonOperationsTemplate comMTRAN = new MTRANOperationsTemplate(JME_simulation);
		CommonOperationsTemplate comOdin = new OdinOperationsTemplate(JME_simulation);
		CommonOperationsTemplate comCKBot = new CKBotOperationsTemplate(JME_simulation);

		VectorDescription zeroPosition = new VectorDescription(0,0,0);		

		if (this.chosenMRname.equals(SupportedModularRobots.ATRON)&& moduleExists(atronDefaultPosition)==false ){
			comATRON.addDefaultConstructionModule(/*this.chosenMRname.toString()*/"default", atronDefaultPosition);
		}else if (this.chosenMRname.equals(SupportedModularRobots.MTRAN)&& moduleExists(mtranDefaultPosition)==false){
			comMTRAN.addDefaultConstructionModule(this.chosenMRname.toString(),mtranDefaultPosition );
		}else if (this.chosenMRname.equals(SupportedModularRobots.ODIN)&& moduleExists(odinDefaultPosition)==false){
			Odin.setDefaultConnectorSize(0.006f);// make connector bigger in order to select them sucessfuly with "on Connector tool"
			comOdin.addDefaultConstructionModule(this.chosenMRname.toString(), odinDefaultPosition);
		}else if (this.chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)&& moduleExists(ckbotDefaultPosition)==false){			
			comCKBot.addDefaultConstructionModule(this.chosenMRname.toString(), ckbotDefaultPosition);
		}else {
			//com.addDefaultConstructionModule(this.chosenMRname, zeroPosition);
			//com1.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
			//com2.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
		}
		guiHelper.passTo(AssistantjTextField,"Now choose one of the tools in the toolbar beneath (nr 5 from the top)");        


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
		String chosenModularRobot = modularRobotjComboBox.getSelectedItem().toString();
		if (chosenModularRobot.contains("Choose")){

		}else{
			this.chosenMRname = SupportedModularRobots.valueOf(modularRobotjComboBox.getSelectedItem().toString().toUpperCase());
			if (this.chosenMRname.equals(SupportedModularRobots.ATRON)){
				standardRotationsjComboBox.setEnabled(true);        
				connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose connector nr. beneath...","0","1", "2", "3","4", "5", "6","7"}));
				standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose module rotation","EW", "WE", "DU", "UD", "SN", "NS" }));

			}else if (this.chosenMRname.equals(SupportedModularRobots.MTRAN)){
				standardRotationsjComboBox.setEnabled(true);
				connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose connector nr. beneath...", "0","1", "2", "3","4", "5"}));
				standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose module rotation","ORI1", "ORI1X", "ORI1Y", "ORI1XY", "ORI2", "ORI2X", "ORI2Y", "ORI2XY", "ORI3", "ORI3X", "ORI3Y", "ORI3XY" }));

			}else if (this.chosenMRname.equals(SupportedModularRobots.ODIN)){
				standardRotationsjComboBox.setEnabled(false);
				connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose connector nr. beneath...","0","1", "2", "3","4", "5", "6","7", "8","9", "10", "11"}));
				standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));            
			}else if (this.chosenMRname.equals(SupportedModularRobots.CKBOTSTANDARD)){
				standardRotationsjComboBox.setEnabled(true);
				connectorsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose connector nr. beneath...","0","1", "2", "3"}));
				standardRotationsjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Choose module rotation","ROTATION_0", "ROTATION_0_OPPOSITE", "ROTATION_0_90Z", "ROTATION_0_OPPOSITE_90Z", "ROTATION_0_90X", "ROTATION_0_MINUS90X", "ROTATION_0_90X_90Y", "ROTATION_0_270X_90Y", "ROTATION_0_90Y", "ROTATION_0_MINUS90Y", "ROTATION_0_90X_MINUS90Z", "ROTATION_0_MINUS90X_MINUS90Z"  }));
			}

			nextjButton.setEnabled(false);
			previousjButton.setEnabled(false);
			newModulejButtonActionPerformed(evt); //Add default construction module
			// Set default construction tool to be "On connector"
			JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,ConstructionTools.ON_SELECTED_CONNECTOR));		
			guiHelper.passTo(AssistantjTextField,this.chosenMRname +" modular robot (MR) is chosen. Now continue selecting connectors on the modules until robot is ready.");
		}
	}                                                     

	/**
	 * 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void rotateComponentjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                       
		// System.out.println("Module generic toolbar-->Rotate"); //for debugging		
		float angle = Float.parseFloat(rotationAnglejTextField.getText());
		JME_simulation.setPicker(new RotateModulesComponents(this.cartesianCoordinate ,angle, this.entity));
		guiHelper.passTo(AssistantjTextField, "Select " + this.entity+ " to rotate "+ rotationAnglejTextField.getText()+ " degrees around "+ this.cartesianCoordinate );// informing user
	}                                                      

	/**
	 * Initializes the tool for coloring the connectors of the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void colourConnectorsjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                        
		//System.out.println("Module generic toolbar-->Color connectors"); //for debugging         
		guiHelper.passTo(AssistantjTextField, "Select module to color its connectors with color coding. See JavaDoc of QuickPrototyping.java for color coding.");// informing user
		JME_simulation.setPicker(new ColorConnectors());
	}                                         

	/**
	 * Initializes the tool for deleting the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void deletejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
		//System.out.println("Module generic toolbar-->Delete"); //for debugging        
		guiHelper.passTo(AssistantjTextField, "Select module to delete");// informing user       
		JME_simulation.setPicker(new RemoveModule());        
	}                                             

	/**
	 * Identifies the entity to move and rotate.
	 * @param evt, selection with left side of the mouse event (entityForMovingjComboBox selection).
	 */
	private void entityForMovingorRotatingjComboBoxActionPerformed(ActionEvent evt) {
		this.entity = entityForMovingorRotatingjComboBox.getSelectedItem().toString();		
		if (this.entity.contains("Component")){
			deletejButton.setEnabled(false);
			colourConnectorsjButton.setEnabled(false);
		}else{
			deletejButton.setEnabled(true);
			colourConnectorsjButton.setEnabled(true);
		}
		guiHelper.passTo(AssistantjTextField, this.entity + " was chosen. Now select the button called Move or axis of rotation, type in agle and button Rotate.");// informing user 
	}
	/**
	 * Initializes the tool for moving the modules in static state of simulation environment by 
	 * selecting them with the left side of the mouse.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void movejButtonActionPerformed(java.awt.event.ActionEvent evt) throws RemoteException {                                            
		//System.out.println("Module generic toolbar-->Move"); //for debugging
			if (this.entity.equalsIgnoreCase("Module")){
			JME_simulation.setPicker(new PhysicsPicker(true, true));
		}else if (this.entity.equalsIgnoreCase("Component")){
			JME_simulation.setPicker(new PhysicsPicker(true, false));
		}		
		guiHelper.passTo(AssistantjTextField, "Pick and move "+ this.entity + " with the mouse.");// informing user   

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
			guiHelper.passTo(AssistantjTextField, "Rendering lights");// informing user
		}else {                   
			lightsjCheckBox.setSelected(false);
			JME_simulation.getLightState().setEnabled(false);
			guiHelper.passTo(AssistantjTextField, "Stopped rendering lights");// informing user
		}             
	}                                               

	/**
	 * Renders or stops reneding the normals during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	
	private void normalsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("RenderToolbar-->Normals");//for debugging       
		if ( JME_simulation.isShowingNormals() == false ){            
			normalsjCheckBox.setSelected(true);
			JME_simulation.setShowNormals(true);
			guiHelper.passTo(AssistantjTextField, "Rendering normals");// informing user
		}else {            
			normalsjCheckBox.setSelected(false);
			JME_simulation.setShowNormals(false);
			guiHelper.passTo(AssistantjTextField, "Stopped rendering normals");// informing user
		}         
	}                                                

	/**
	 * Renders or stops reneding the bounds during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	
	private void boundsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
		// System.out.println("Render toolbar-->Wireframe");//for debugging        
		if ( JME_simulation.isShowingBounds() == false ){        
			boundsjCheckBox.setSelected(true);
			JME_simulation.setShowBounds(true);
			guiHelper.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			boundsjCheckBox.setSelected(false);
			JME_simulation.setShowBounds(false);
			guiHelper.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}        
	}                                               

	/**
	 * Renders or stops reneding the wireframe during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	 
	private void wireFramejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                   
		// System.out.println("Render toolbar-->Wireframe");//for debugging        
	/*	if ( JME_simulation.getWireFrame().isEnabled() == false ){        
			wireFramejCheckBox.setSelected(true);
			JME_simulation.getWireFrame().setEnabled(true);
			guiHelper.passTo(AssistantjTextField, "Rendering wireframe");// informing user
		}else {            
			wireFramejCheckBox.setSelected(false);
			JME_simulation.getWireFrame().setEnabled(false);
			guiHelper.passTo(AssistantjTextField, "Stopped rendering wireframe");// informing user
		}  */     
	}                                                  

	/**
	 * Renders or stops reneding physics during the simulation.
	 * @param evt, selection with left side of the mouse event (jCheckBox selection).     
	 */	 
	private void physicsjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("Render toolbar-->Physics");//for debugging        
		if (JME_simulation.isShowingPhysics() == false ){                             
			physicsjCheckBox.setSelected(true);
			JME_simulation.setShowPhysics(true);
			guiHelper.passTo(AssistantjTextField, "Rendering physics");// informing user
		}else {                         
			physicsjCheckBox.setSelected(false);
			JME_simulation.setShowPhysics(false);
			guiHelper.passTo(AssistantjTextField, "Stopped rendering physics");// informing user
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
		guiHelper.passTo(AssistantjTextField, "Load XML file containing construction data");

	}                                           

	/**
	 * Opens the file chooser for saving the data about moprhology of modular robot in XML file.
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	
	private void savejButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		//System.out.println("Simulation toolbar-->Save");//for debugging            
		this.fileChooserSave = new FileChooser(JME_simulation,false); 
		this.fileChooserSave.activate(); 	
		guiHelper.passTo(AssistantjTextField, "Save construction  data in XML file");
		System.out.println("Save pressed");

	}                                           

	/**
	 * Opens the file chooser for saving the data about moprhology of modular robot in XML file.
	 * @param evt, selection with left side of the mouse event (jMenuItem selection).     
	 */	
	private void OpenXMLJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		//System.out.println("File-->Open");//for debugging
		this.fileChooserOpen = new FileChooser(JME_simulation,true); 
		fileChooserOpen.activate();         
		guiHelper.passTo(AssistantjTextField,"Load XML file containing construction data");// informing user
	}                                                

	/**
	 *  Activates step by step simulation. This method for single step. 
	 * @param evt, selection with left side of the mouse event (jButton selection).     
	 */	   
	private void stepByStepjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		System.out.println("Simulation toolbar-->StepByStep");//for debugging               
		simulationStep++;
		guiHelper.passTo(AssistantjTextField, "Executed simulation step Nr: "+ simulationStep);
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
		guiHelper.passTo(AssistantjTextField,"Save construction  data in XML file");// informing user

	}                                               

	private void filejMenuActionPerformed(java.awt.event.ActionEvent evt) {                                          
		// TODO add your handling code here:
	}                                         

	int timesPressed =0;
	/**
	 * Pauses or starts running the simulation, depending on the current state of simulation.    
	 * @param evt, selection with left side of the mouse event (Button selection).     
	 */	
	private void pauseRunButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               

		//System.out.println("Simulation and Rendering toolbar -->Pause/Play");//for debugging              
		if (JME_simulation.isPaused()==false){ 
			guiHelper.passTo(AssistantjTextField, "Simulation is in static state");// informing user
			JME_simulation.setPause(true);                       
			pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + pauseIcon));

		}else{
			timesPressed++;
			if (timesPressed ==1){ // First time is pressed connect all the modules in the morphology
				BuilderHelper.connectAllModules(JME_simulation);		
			}
			guiHelper.passTo(AssistantjTextField, "Simulation running");// informing user
			pauseRunButton.setIcon(new javax.swing.ImageIcon(directoryForIcons + playIcon));
			JME_simulation.setPause(false);


		}
	}                                              

	/**
	 * Closes the window ("Quick Prototyping of Simulation scenarios").    
	 * @param evt, selection with left side of the mouse event (Menu item selection).     
	 */	    
	private void ExitjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                              
		System.out.println("File-->Exit");//for debugging
		instanceFlag = false; // reset the flag
		this.dispose();
		
	} 

	private void controllersjComboBoxActionPerformed(ActionEvent evt) {
		this.controllerName = controllersjComboBox.getSelectedItem().toString();
		if (this.controllerName.contains("Choose")){

		}else{

			JME_simulation.setPicker(new AssignControllerTool(packageName+"."+this.controllerName));
			guiHelper.passTo(AssistantjTextField,"Select " + this.chosenMRname +" module to assign controller: " +this.controllerName);// informing user
		}
	}

	private void controllersjCheckBoxMenuItemActionPerformed(ActionEvent evt) {
		this.guiHelper.changeToolBarVisibility(controllersjCheckBoxMenuItem, controllersjToolBar);		
	}


	/**
	 * Activates the QPSS (Quick Prototyping of Simulation Scenarios) window.
	 * @param simulation, the basic graphical simulation.
	 */
	public static void activate(final JMEBasicGraphicalSimulation simulation) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
		/*		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();                
				double width = screenDimension.getWidth()/2.5f;
				double height = screenDimension.getHeight()/2.1f;
				String newWidth = (width+"").replace(".","#");
				String newHeight = (height+"").replace(".","#");
				String[] splittedWidth  = newWidth.split("#");
				String[] splittedHeight  = newHeight.split("#");*/
				//int windowWidth =Integer.parseInt(splittedWidth[0].toString()); 
				//int windowHeight =Integer.parseInt(splittedHeight[0].toString());
				int windowWidth = 620; 
				int windowHeight =480;
				int toolBarHeight = 40;
				//constructionToolBar.setSize(windowWidth,toolBarHeight);
				//AssistantjToolBar.setSize(windowWidth,toolBarHeight);                
				QuickPrototyping quickPrototyping = new QuickPrototyping(simulation);
				quickPrototyping.getAssistantjToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getControllersjToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getModularRoborGenericjToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getModuleGenericToolsJToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getModuleLabelsToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getConstructionToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getSimulationToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getLabeljToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.getRenderjToolBar().setPreferredSize(new Dimension(windowWidth-10,toolBarHeight));
				quickPrototyping.setVisible(true);				
				quickPrototyping.setSize(windowWidth,windowHeight);				
			}
		});
	}

	public static boolean isInstanceFlag() {
		return instanceFlag;
	}

	public javax.swing.JComboBox getModuleLabelsjComboBox() {
		return entityLabelsjComboBox;
	}

	public javax.swing.JTextField getCurrentLabeljTextField() {
		return currentLabeljTextField;
	}

	public javax.swing.JToolBar getAssistantjToolBar() {
		return AssistantjToolBar;
	}	

	public javax.swing.JToolBar getConstructionToolBar() {
		return constructionToolBar;
	}

	public javax.swing.JToolBar getModularRoborGenericjToolBar() {
		return modularRoborGenericjToolBar;
	}

	public javax.swing.JToolBar getModuleGenericToolsJToolBar() {
		return moduleGenericToolsJToolBar;
	}	

	public javax.swing.JToolBar getModuleLabelsToolBar() {
		return moduleLabelsToolBar;
	}

	public javax.swing.JToolBar getLabeljToolBar() {
		return assignLabeljToolBar;
	}

	public javax.swing.JToolBar getRenderjToolBar() {
		return renderjToolBar;
	}

	public javax.swing.JToolBar getSimulationToolBar() {
		return simulationToolBar;
	}

	public javax.swing.JToolBar getControllersjToolBar() {
		return controllersjToolBar;
	}

	// Variables declaration - do not modify                     
	private javax.swing.JTextField AssistantjTextField;
	private javax.swing.JToolBar AssistantjToolBar;
	private javax.swing.JToolBar controllersjToolBar;
	private javax.swing.JMenuItem ExitjMenuItem;
	private javax.swing.JLabel readLabelsjLabel;
	private javax.swing.JMenuItem OpenXMLJMenuItem;
	private javax.swing.JButton alljButton;	
	private javax.swing.JButton assignLabeljButton;
	private javax.swing.JCheckBoxMenuItem assistantCheckBoxMenuItem;
	private javax.swing.JLabel assistantjLabel;
	private javax.swing.JCheckBox boundsjCheckBox;
	private javax.swing.JComboBox cartesianCoordinatejComboBox;
	private javax.swing.JComboBox controllersjComboBox;
	private javax.swing.JButton colourConnectorsjButton;
	private javax.swing.JComboBox connectorsjComboBox;
	private javax.swing.JToolBar constructionToolBar;
	private javax.swing.JCheckBoxMenuItem constructionjCheckBoxMenuItem;
	private javax.swing.JTextField currentLabeljTextField;
	private javax.swing.JButton newModulejButton;
	private javax.swing.JButton deletejButton;
	private javax.swing.JMenu filejMenu;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JCheckBoxMenuItem assignLabeljCheckBoxMenuItem;
	private javax.swing.JToolBar assignLabeljToolBar;
	private javax.swing.JLabel labelsjLabel;
	private javax.swing.JLabel controllersjLabel;
	private javax.swing.JComboBox entityForLabeling;
	private javax.swing.JCheckBox lightsjCheckBox;
	private javax.swing.JButton loopjButton;
	private javax.swing.JToolBar modularRoborGenericjToolBar;
	private javax.swing.JComboBox modularRobotjComboBox;
	private javax.swing.JCheckBoxMenuItem modularRobotsjCheckBoxMenuItem;
	private javax.swing.JCheckBoxMenuItem moduleGenericToolsCheckBoxMenuItem;
	private javax.swing.JToolBar moduleGenericToolsJToolBar;
	private javax.swing.JToolBar moduleLabelsToolBar;
	private javax.swing.JCheckBoxMenuItem readLabelsjCheckBoxMenuItem;
	private javax.swing.JComboBox entityLabelsjComboBox;
	private javax.swing.JComboBox entityForMovingorRotatingjComboBox;
	private javax.swing.JButton movejButton;
	private javax.swing.JButton nextjButton;
	private javax.swing.JCheckBox normalsjCheckBox;
	private javax.swing.JButton onConnectorjButton;
	private javax.swing.JButton openjButton;
	private javax.swing.JButton oppositeRotationjButton;
	private javax.swing.JButton pauseRunButton;
	private javax.swing.JCheckBox physicsjCheckBox;
	private javax.swing.JButton previousjButton;
	private javax.swing.JButton specialjButton;
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
	private javax.swing.JCheckBoxMenuItem controllersjCheckBoxMenuItem;
	private javax.swing.JComboBox standardRotationsjComboBox;
	private javax.swing.JButton stepByStepjButton;
	/*private javax.swing.JButton testjButton;*/
	//private javax.swing.JButton assignControllerjButton;	
	private javax.swing.JMenu toolBarsjMenu;
	private javax.swing.JButton variatejButton;
	private javax.swing.JMenu viewjMenu;
	private javax.swing.JCheckBox wireFramejCheckBox;
	// End of variables declaration
}

package ussr.builder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JToolBar;

import ussr.builder.construction.CommonOperationsStrategy;
import ussr.builder.construction.ConstructionToolSpecification;
import ussr.builder.genericSelectionTools.AssignLabel;
import ussr.builder.genericSelectionTools.ColorConnectors;
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
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.Odin;
/**
 * The main responsibility of this class is to take care of the main GUI window for  project called
 * "Quick Prototyping of Simulation Scenarios".   
 * USER GUIDE.
 * In order to start "Quick Prototyping of Simulation Scenarios" or so called interactive builder do 
 * the following:
 * 1) Locate simulation file for one of the modular robots, currently are supported:
 *    a) "InteractiveAtronBuilderTest1.java", which can be found in package  "ussr.samples.atron.simulations";
 *    b) "InteractiveMtranBuilderTest1.java", which can be found in package "ussr.samples.mtran";
 *    c) "InteractiveOdinBuilderTest1", which can be found in package "ussr.samples.odin.simulations";
 * 2) Start  simulation (run the file) for one of the above files. The simulation should be in static state 
 * 	  (paused state),meaning that you should not press "P" button on keyboard. At least not yet.
 * 3) Press "Q" button on keyboard and wait a bit. On slow machines the "Quick Prototyping of Simulation 
 *    Scenarios" window is quite slow to respond. 
 * 4) In appeared "Quick prototyping of Simulation Scenarios" window choose one of the buttons(pickers,also
 *    called selection tools).
 *    4.1) Generic selection tools
 *         a) "Delete icon" - is the selection tool, where after selecting the module with left side of the mouse
 *            it is deleted(removed)from simulation environment;
 *         b) "C"- is the selection tool, where after selecting the module with left side of the mouse
 *            its connectors are coloured with colour coding. The format is Connector-Colour: 0-Black, 1-Blue,
 *            2-Cyan, 3-Grey, 4-Green, 5-Magenta, 6-Orange, 7-Pink, 8-Red, 9-White, 10-Yellow, 11-Light Grey.	
 *    4.2) Modular robot specific selection tools.
 *    	   For example for ATRON (the same is for MTRAN and Odin):
 *         a) "Con"- is the selection tool, where you can just click on connector of the module with the 
 *            left side of the mouse and the next module will be added to selected connector,
 *         b) "ComboBox with numbers"- is the selection tool, where after choosing the connector number
 *            (in ComboBox)where new module will be added, after that click on one of the modules in simulation
 *            environment with the left side of the mouse. As a result new module will be added to the 
 *            chosen connector on selected module.
 *         c) "All"- is the selection tool, where you just click on the module with the left side of the mouse
 *            and all possible modules will be added to the selected module connectors.
 *         d) "Loop"- is the selection tool, where you just click on the module with the left side of the mouse
 *            and later press on buttons "Next" or "Back". As a result,new module will be moved from one 
 *            connector to another with increasing number of connector ("Next") and decreasing number of 
 *            connector ("Back").
 *         e) "Opposite"- is the selection tool, where you just click on the module with the left side of the 
 *            mouse and as a result the rotation of the module changes to opposite.
 *         f) "Variation"- is the selection tool, where you just click on the module with the left side of the 
 *            mouse and as a result the rotation of the module changes to one more additional(specific) to
 *            modular robot or the module type changes to another type. Particularly useful in MTRAN and Odin
 *            case.
 *         g) "ComboBox with rotations" - is the selection tool, where after choosing the rotation name
 *            (in ComboBox), after that click on one of the modules in simulation
 *            environment with the left side of the mouse. As a result, new module will be rotated with
 *            chosen rotation. Particularly useful at start of construction process, when the first
 *            module limits the construction of final morphology.  
 * 5) Using one of the above selection tools construct desired morphology(shape) of the modular robot and
 *    after that press "P" button on keyboard to start the simulation. 
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
 * The Design Class Diagram(DCD) for all the code can be found in USSR directory named as:"doc\developer\DCD.JPG"
 * @author  Konstantinas
 */
public class QuickPrototyping extends javax.swing.JFrame {
    
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
   
   private final GuiHelper guiUtil = new GuiHelper();
   
   private String chosenMRname ="ATRON"; //MR- modular robot. Default is ATRON, just do not have the case when it is empty String
   
   private String rotationName ="EW";//Default, just do not have the case when it is empty String
   
   private int connectorNr;
   
   private static final int amountATRONConnectors = 7;
   
   private static final int amountMTRANConnectors = 5;  
   
   private static final int amountOdinBallConnectors = 11;
    
    /** Creates new form QuickPrototyping */
    public QuickPrototyping(JMEBasicGraphicalSimulation simulation) {
        initComponents();
        //Set to generic view      
        guiUtil.changeToSetLookAndFeel(this);         
        this.JME_simulation = (JMESimulation) simulation;  
        adaptGuiToModularRobot();// Adapt GUI to modular robot existing in simulation environment
        JME_simulation.setPicker(new PhysicsPicker(true));//set default picker
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jToolBar4 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jToolBar8 = new javax.swing.JToolBar();
        jComboBox1 = new javax.swing.JComboBox();
        jButton20 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        jButton9 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuBar8 = new javax.swing.JMenuBar();
        jMenuBar10 = new javax.swing.JMenuBar();
        jMenuBar13 = new javax.swing.JMenuBar();
        jToolBar7 = new javax.swing.JToolBar();
        jButton19 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jMenuBar15 = new javax.swing.JMenuBar();
        jMenu43 = new javax.swing.JMenu();
        jMenuItem57 = new javax.swing.JMenuItem();
        jMenuItem58 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JSeparator();
        jMenuItem59 = new javax.swing.JMenuItem();
        jMenu44 = new javax.swing.JMenu();
        jMenu45 = new javax.swing.JMenu();
        jCheckBoxMenuItem82 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem83 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem84 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem9 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem10 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem85 = new javax.swing.JCheckBoxMenuItem();
        jSeparator30 = new javax.swing.JSeparator();
        jMenuItem60 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quick Prototyping of Simulation Scenarios");
        setResizable(false);
        getContentPane().setLayout(new java.awt.FlowLayout());

        jToolBar1.setPreferredSize(new java.awt.Dimension(310, 41));

        //jButton1.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\play.JPG")); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/play.JPG"));
        jButton1.setToolTipText("Play/Pause");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        //jButton2.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\step_by_step.JPG")); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/step_by_step.JPG"));
        jButton2.setToolTipText("Step by step ");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        //jButton3.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\save.JPG")); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/save.JPG"));
        jButton3.setToolTipText(" Save");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        //jButton4.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\open.JPG")); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/open.JPG"));
        jButton4.setToolTipText("Open");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        getContentPane().add(jToolBar1);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        jCheckBox1.setText("Physics");
        jCheckBox1.setFocusable(false);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jToolBar3.add(jCheckBox1);

        jCheckBox2.setText("Wireframe");
        jCheckBox2.setFocusable(false);
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jToolBar3.add(jCheckBox2);

        jCheckBox3.setText("Bounds");
        jCheckBox3.setFocusable(false);
        jCheckBox3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jToolBar3.add(jCheckBox3);

        jCheckBox4.setText("Normals");
        jCheckBox4.setFocusable(false);
        jCheckBox4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jCheckBox4);

        jCheckBox5.setText("Lights");
        jCheckBox5.setFocusable(false);
        jCheckBox5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jToolBar3.add(jCheckBox5);

        getContentPane().add(jToolBar3);

        jToolBar4.setRollover(true);
        jToolBar4.setPreferredSize(new java.awt.Dimension(310, 43));

        //jButton5.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\move.JPG")); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/move.JPG"));
        jButton5.setToolTipText("Move");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton5);

        //jButton7.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\delete.JPG")); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/delete.JPG"));
        jButton7.setToolTipText("Delete");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton7);

        //jButton8.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\colorConnectors.JPG")); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/colorConnectors.JPG"));
        jButton8.setToolTipText("Color connectors");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton8);

        jCheckBox6.setText("x");
        jCheckBox6.setFocusable(false);
        jCheckBox6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });
        jToolBar4.add(jCheckBox6);

        jCheckBox7.setText("y");
        jCheckBox7.setFocusable(false);
        jCheckBox7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });
        jToolBar4.add(jCheckBox7);

        jCheckBox8.setText("z");
        jCheckBox8.setFocusable(false);
        jCheckBox8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });
        jToolBar4.add(jCheckBox8);

        jTextField2.setText("angle, deg");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jToolBar4.add(jTextField2);

        //jButton6.setIcon(new javax.swing.ImageIcon("C:\\Documents and Settings\\Konstantinas.PC428130132326\\Desktop\\FORK\\NetBeansProjects\\GUIFormExamples\\src\\THESIS\\rotateArrow.JPG")); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/rotateArrow.JPG"));
        jButton6.setToolTipText("Rotate");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton6);

        getContentPane().add(jToolBar4);

        jToolBar8.setRollover(true);
        jToolBar8.setPreferredSize(new java.awt.Dimension(310, 35));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "MTRAN", "Odin" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jToolBar8.add(jComboBox1);

        jButton20.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton20.setText("Module");
        jButton20.setFocusable(false);
        jButton20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jToolBar8.add(jButton20);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EW", "WE", "DU", "UD", "SN", "NS" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jToolBar8.add(jComboBox6);

        jButton14.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton14.setText("Opposite");
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setPreferredSize(new java.awt.Dimension(60, 30));
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jToolBar8.add(jButton14);

        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton15.setText("Variation");
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton15.setPreferredSize(new java.awt.Dimension(60, 30));
        jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jToolBar8.add(jButton15);

        getContentPane().add(jToolBar8);

        jToolBar5.setRollover(true);
        jToolBar5.setPreferredSize(new java.awt.Dimension(310, 35));

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setLabel("On connnector");
        jButton9.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton9);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(20, 20));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jToolBar5.add(jComboBox2);

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton10.setText("All");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton10);

        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton13.setText("Loop");
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setPreferredSize(new java.awt.Dimension(37, 25));
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton13);

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton11.setText("Next");
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton11);

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton12.setText("Back");
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton12);

        getContentPane().add(jToolBar5);
        getContentPane().add(jMenuBar1);
        getContentPane().add(jMenuBar8);
        getContentPane().add(jMenuBar10);
        getContentPane().add(jMenuBar13);

        jToolBar7.setRollover(true);
        jToolBar7.setPreferredSize(new java.awt.Dimension(300, 35));

        jButton19.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton19.setText("Label:");
        jButton19.setFocusable(false);
        jButton19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton19.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jToolBar7.add(jButton19);

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField3.setToolTipText("");
        jToolBar7.add(jTextField3);

        jButton17.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton17.setText("Assign");
        jButton17.setFocusable(false);
        jButton17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jToolBar7.add(jButton17);

        jButton18.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton18.setText("Use");
        jButton18.setFocusable(false);
        jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jToolBar7.add(jButton18);

        getContentPane().add(jToolBar7);

        jToolBar2.setRollover(true);
        jToolBar2.setPreferredSize(new java.awt.Dimension(310, 35));

        jLabel1.setText("Assistant:");
        jToolBar2.add(jLabel1);

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setToolTipText("");
        jToolBar2.add(jTextField1);

        getContentPane().add(jToolBar2);

        jButton16.setText("Continue to simulation");
        jButton16.setPreferredSize(new java.awt.Dimension(139, 30));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton16);

        jMenu43.setText("File");

        jMenuItem57.setText("Open");
        jMenuItem57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu43.add(jMenuItem57);

        jMenuItem58.setText("Save as ");
        jMenuItem58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu43.add(jMenuItem58);
        jMenu43.add(jSeparator29);

        jMenuItem59.setText("Exit");
        jMenuItem59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu43.add(jMenuItem59);

        jMenuBar15.add(jMenu43);

        jMenu44.setText("View");

        jMenu45.setText("Toolbars");

        jCheckBoxMenuItem82.setSelected(true);
        jCheckBoxMenuItem82.setText("Simulation");
        jCheckBoxMenuItem82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem82);

        jCheckBoxMenuItem83.setSelected(true);
        jCheckBoxMenuItem83.setText("Rendering");
        jCheckBoxMenuItem83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem3ActionPerformed(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem83);

        jCheckBoxMenuItem84.setSelected(true);
        jCheckBoxMenuItem84.setText("Module generic");
        jCheckBoxMenuItem84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem7ActionPerformed(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem84);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Modular robot specific");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed2(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem9.setSelected(true);
        jCheckBoxMenuItem9.setText("Construction ");
        jCheckBoxMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed1(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem9);

        jCheckBoxMenuItem10.setSelected(true);
        jCheckBoxMenuItem10.setText("Behaviours");
        jCheckBoxMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed1(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem10);

        jCheckBoxMenuItem85.setSelected(true);
        jCheckBoxMenuItem85.setText("Assistant");
        jCheckBoxMenuItem85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu45.add(jCheckBoxMenuItem85);
        jMenu45.add(jSeparator30);

        jMenuItem60.setText("Customize...");
        jMenuItem60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu45.add(jMenuItem60);

        jMenu44.add(jMenu45);

        jMenuBar15.add(jMenu44);

        setJMenuBar(jMenuBar15);

        pack();
    }// </editor-fold>

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
       //System.out.println("File-->Exit");//for debugging 
        this.dispose();
    }                                          
   
    private void adaptGuiToModularRobot(){
    	
    	String modularRobotName ="";
    	if (JME_simulation.worldDescription.getModulePositions().size()>0){
    		modularRobotName = JME_simulation.getModules().get(0).getProperty("ussr.module.type");	
    	}		
		if (modularRobotName.contains("Odin")){
			jComboBox1.setSelectedIndex(2);			
		}else if (modularRobotName.contains("ATRON")){
			jComboBox1.setSelectedIndex(0);
			jComboBox6.setSelectedIndex(0);
		}else if (modularRobotName.contains("MTRAN")){
			jComboBox1.setSelectedIndex(1);	
			jComboBox6.setSelectedIndex(0);
		}
                 //Odin.setDefaultConnectorSize(0.006f);
                 jComboBox2.setSelectedIndex(0);                 
         jButton11.setEnabled(false);
        jButton12.setEnabled(false);
         guiUtil.passTo(jTextField1,"Select one of MR names in comboBox(4th toolbar) ");// informing user
	}
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("File-->Save as");//for debugging                
         this.fileChooserSave = new FileChooser(JME_simulation,false); 
         fileChooserSave.activate();
         guiUtil.passTo(jTextField1,"Save simulation data to XML file");// informing user
    }                                          

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("File-->Open");//for debugging
          this.fileChooserOpen = new FileChooser(JME_simulation,true); 
         fileChooserOpen.activate();
         guiUtil.passTo(jTextField1,"Load simulation data to XML file");// informing user
    }                                          

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       //System.out.println("Simulation ToolBar-->Pause/Play simulation");//for debugging 
        System.out.println("Simulation ToolBar-->"+jButton1.getToolTipText());//for debugging        
       if (JME_simulation.isPaused()==false){        
			JME_simulation.setPause(true);			
                        guiUtil.passTo(jTextField1, "Simulation is in static state");// informing user
			jButton1.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/pause.JPG"));

		}else{			
                        guiUtil.passTo(jTextField1, "Simulation running");// informing user
			jButton1.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/play.JPG"));
			JME_simulation.setPause(false);
		}
        
    }                                        

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        //System.out.println("View-->Toolbars-->Simulation");//for debugging 
        this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem82, jToolBar1);        
    }                                                  

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
          System.out.println("View-->Toolbars-->Assistant");                 
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem85, jToolBar2);  
    }                                                  

    int simulationStep =0;
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         //System.out.println("Simulation ToolBar-->Step by step");//for debugging 
        System.out.println("Simulation ToolBar-->"+jButton2.getToolTipText());//for debugging        
        simulationStep++;
        guiUtil.passTo(jTextField1, "Executed simulation step Nr:"+simulationStep);
        JME_simulation.setSingleStep(true);
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         //System.out.println("Simulation ToolBar-->Save");//for debugging 
        System.out.println("Simulation ToolBar-->"+jButton3.getToolTipText());//for debugging     
         this.fileChooserSave = new FileChooser(JME_simulation,false); 
        this.fileChooserSave.activate(); 	 
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
           //System.out.println("Simulation ToolBar-->Open");//for debugging 
          System.out.println("Simulation ToolBar-->"+jButton4.getToolTipText());//for debugging          
           this.fileChooserOpen = new FileChooser(JME_simulation,true); 
          fileChooserOpen.activate();                       
    }                                        

    private void jCheckBoxMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
           //System.out.println("View-->Toolbars-->Rendering");//for debugging                              
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem83, jToolBar3); 
    }                                                  

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //System.out.println("Module generic toolbar-->Move"); //for debugging  
    	JME_simulation.setPicker(new PhysicsPicker(true));
        guiUtil.passTo(jTextField1, "Pick and move module");// informing user        
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // System.out.println("Module generic toolbar-->Rotate"); //for debugging        
         guiUtil.passTo(jTextField1, "Select module to rotate");// informing user
         float angle = Float.parseFloat(jTextField2.getText());
         
         if (jCheckBox6.isSelected()){        	 
        	 JME_simulation.setPicker(new RotateModuleComponents("X",angle));
         }else if (jCheckBox7.isSelected()){
        	 JME_simulation.setPicker(new RotateModuleComponents("Y",angle));
         }else if (jCheckBox8.isSelected()){
        	 JME_simulation.setPicker(new RotateModuleComponents("Z",angle));
         }         
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //System.out.println("Module generic toolbar-->Delete"); //for debugging 
        System.out.println("GenericToolbar-->"+jButton7.getToolTipText());//for debugging 
        guiUtil.passTo(jTextField1, "Select module to delete");// informing user
        JME_simulation.setPicker(new RemoveModule());
    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         //System.out.println("Module generic toolbar-->Color connectors"); //for debugging         
         guiUtil.passTo(jTextField1, "Select module to color its connectors");// informing user
        JME_simulation.setPicker(new ColorConnectors());        
    }                                        

    private void jCheckBoxMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
         System.out.println("View-->Toolbars-->Module generic");//for debugging
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem84, jToolBar4); 
    }                                                  
        
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
           //System.out.println("View-->Toolbars-->Customize");//for debugging         
           resetToolBarVisibility();
           //ToolBarDisplayer toolBarDisplayer = new ToolBarDisplayer(this);
          // toolBarDisplayer.activate();
    }                                          

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         //System.out.println("Construction toolbar-->On connector");//for debugging    
         JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"OnConnector"));
        guiUtil.passTo(jTextField1,"Select connector on "+ this.chosenMRname +" modular robot");
    }                                        

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //System.out.println("Construction toolbar-->All");//for debugging 
        JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"AllConnectors"));
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module");
    }                                         
    
    ConstructionToolSpecification constructionTools = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"Loop",0);
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
         //System.out.println("Construction toolbar-->Loop");//for debugging  
        guiUtil.passTo(jTextField1,"1)Select " +this.chosenMRname +" module,2)use NEXT and BACK");
         jButton11.setEnabled(true);
        jButton12.setEnabled(true);
         this.connectorNr =0;
         ConstructionToolSpecification constructionToolsnew = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"Loop",this.connectorNr);
        this.constructionTools = constructionToolsnew;
        JME_simulation.setPicker(constructionToolsnew);         
    }                                         

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Lights");//for debugging    
                if (JME_simulation.getLightState().isEnabled() == false ){                       
                        jCheckBox5.setSelected(true);
                        JME_simulation.getLightState().setEnabled(true);
                         guiUtil.passTo(jTextField1, "Rendering lights");// informing user
                }else {                   
                        jCheckBox5.setSelected(false);
                        JME_simulation.getLightState().setEnabled(false);
                        guiUtil.passTo(jTextField1, "Stopped rendering lights");// informing user
                }
    }                                          

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Normals");//for debugging       
   if ( JME_simulation.isShowNormals() == false ){            
            jCheckBox4.setSelected(true);
            JME_simulation.setShowNormals(true);
             guiUtil.passTo(jTextField1, "Rendering normals");// informing user
        }else {            
            jCheckBox4.setSelected(false);
            JME_simulation.setShowNormals(false);
            guiUtil.passTo(jTextField1, "Stopped rendering normals");// informing user
        }    
    }                                          

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Bounds");//for debugging        
          if ( JME_simulation.isShowBounds() == false ){            
            jCheckBox3.setSelected(true);
            JME_simulation.setShowBounds(true);
            guiUtil.passTo(jTextField1, "Rendering bounds");// informing user
        }else {         
            jCheckBox3.setSelected(false);
            JME_simulation.setShowBounds(false);
            guiUtil.passTo(jTextField1, "Stopped rendering bounds");// informing user
        }
    }                                          

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // System.out.println("RenderToolbar-->Wireframe");//for debugging        
         if ( JME_simulation.getWireState().isEnabled() == false ){        
           jCheckBox2.setSelected(true);
           JME_simulation.getWireState().setEnabled(true);
           guiUtil.passTo(jTextField1, "Rendering wireframe");// informing user
       }else {            
           jCheckBox2.setSelected(false);
           JME_simulation.getWireState().setEnabled(false);
           guiUtil.passTo(jTextField1, "Stopped rendering wireframe");// informing user
       }
    }                                          

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Physics");//for debugging        
                     if (JME_simulation.isShowPhysics() == false ){                             
                      jCheckBox1.setSelected(true);
                      JME_simulation.setShowPhysics(true);
                      guiUtil.passTo(jTextField1, "Rendering physics");// informing user
                  }else {                         
                      jCheckBox1.setSelected(false);
                      JME_simulation.setShowPhysics(false);
                      guiUtil.passTo(jTextField1, "Stopped rendering physics");// informing user
                  }
    }                                          

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("Modular robot specific-->ComboBox with names of MR");//for debugging 
        this.chosenMRname = jComboBox1.getSelectedItem().toString();
        if (this.chosenMRname.equalsIgnoreCase("ATRON")){
        jButton9.setEnabled(true);
        jComboBox6.setEnabled(true);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5", "6","7"}));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EW", "WE", "DU", "UD", "SN", "NS" }));
        
        }else if (this.chosenMRname.equalsIgnoreCase("MTRAN")){
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5"}));
            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ORI1", "ORI1X", "ORI1Y", "ORI1XY", "ORI2", "ORI2X", "ORI2Y", "ORI2XY", "ORI3", "ORI3X", "ORI3Y", "ORI3XY" }));
           jButton9.setEnabled(true);
           jComboBox6.setEnabled(true);
        }else if (this.chosenMRname.equalsIgnoreCase("Odin")){
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0","1", "2", "3","4", "5", "6","7", "8","9", "10", "11"}));
            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));
            jButton9.setEnabled(true);
            jComboBox6.setEnabled(false);
        }
        jButton11.setEnabled(false);
        jButton12.setEnabled(false);
        guiUtil.passTo(jTextField1,this.chosenMRname +" modular robot (MR)");
    }                                          

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
       //System.out.println("Construction toolbar-->Next");//for debugging 
        this.connectorNr++;
       if (this.chosenMRname.equalsIgnoreCase("ATRON")){
       if (this.connectorNr>amountATRONConnectors){ this.connectorNr=0;}       
       }else if (this.chosenMRname.equalsIgnoreCase("MTRAN")){
       if (this.connectorNr>amountMTRANConnectors){ this.connectorNr=0;}
       }else if (this.chosenMRname.equalsIgnoreCase("Odin")){
			if (this.connectorNr>amountOdinBallConnectors){ this.connectorNr=0;}
		}
      //TODO NEED TO GET BACK FOR ODIN WHICH TYPE OF MODULE IS SELECTED. 
       constructionTools.moveToNextConnector(this.connectorNr);
       /*else if (this.chosenMRname.equalsIgnoreCase("Odin")){
       if (this.connectorNr>amountOdinConnectors){ this.connectorNr=0;}
       }*/
       guiUtil.passTo(jTextField1,this.chosenMRname +" module is on connector number "+this.connectorNr);
    }                                         

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //System.out.println("Construction toolbar-->Back");//for debugging 
        this.connectorNr--;
		if (this.chosenMRname.equalsIgnoreCase("ATRON") && this.connectorNr<0){
			this.connectorNr =7;//reset
		}else if (this.chosenMRname.equalsIgnoreCase("MTRAN") && this.connectorNr<0){this.connectorNr=5;}
         else if (this.chosenMRname.equalsIgnoreCase("Odin") && this.connectorNr<0){this.connectorNr=11;}
         constructionTools.moveToNextConnector(this.connectorNr);
       guiUtil.passTo(jTextField1,this.chosenMRname +" module is on connector number "+this.connectorNr);
    }                                         

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
      //System.out.println("Modular robot specific-->Opposite");//for debugging 
        JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"OppositeRotation"));        
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module to rotate it opposite ");
    }                                         

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
       //System.out.println("Modular robot specific-->Specific rotations");//for debugging 
        this.rotationName = jComboBox6.getSelectedItem().toString();
	JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"StandardRotation",this.rotationName));		
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module to rotate with "+ rotationName+ " rotation");
    }                                          

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
      //System.out.println("Construction toolbar-->On chosen (comboBox) connector");//for debugging 
        int connectorNumber = Integer.parseInt(jComboBox2.getSelectedItem().toString());
	JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"chosenConnector",connectorNumber));
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module");
    }                                          

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //System.out.println("Modular robot specific-->Variation");//for debugging 
        if (this.chosenMRname.contains("ATRON")||this.chosenMRname.contains("MTRAN")){ 
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module to see its variations");
        }else if (this.chosenMRname.contains("Odin")){
            guiUtil.passTo(jTextField1,"Select OdinMuscle to chage it with other types of modules");
        }
        JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"Variation"));
    }                                         

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jCheckBoxMenuItem1ActionPerformed1(java.awt.event.ActionEvent evt) {                                                    
            System.out.println("View-->Toolbars-->Construction");//for debugging
           this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem1, jToolBar5); 
    }                                                   

    private void jCheckBoxMenuItem2ActionPerformed1(java.awt.event.ActionEvent evt) {                                                    
          System.out.println("View-->Toolbars-->Behaviours");//for debugging
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem10, jToolBar7); 
    }                                                   

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        System.out.println("Continue to simulation button");//for debugging
        //JME_simulation.setPicker(new PhysicsPicker());
       // JME_simulation.setPause(false);
        //this.dispose();
        if (JME_simulation.worldDescription.getModulePositions().size()>=0){
        	
         	int amountModules = JME_simulation.getModules().size();
         	ArrayList<ModulePosition> atronModulePositions = new ArrayList<ModulePosition>();
         	ArrayList<ModulePosition> mtranModulePositions = new ArrayList<ModulePosition>(); 
         	ArrayList<ModulePosition> odinAllModulePositions = new ArrayList<ModulePosition>();
         	ArrayList<ModulePosition> odinBallModulePositions = new ArrayList<ModulePosition>(); 
         	ArrayList<ModulePosition> odinOtherModulesPositions = new ArrayList<ModulePosition>();
         	
         	List<Module> atronModules = new ArrayList<Module>();
         	List<Module> mtranModules = new ArrayList<Module>();
         	List<Module> odinAllModules = new ArrayList<Module>();
         	
         	for (int i=0; i<amountModules; i++){
         		Module currentModule = JME_simulation.getModules().get(i);
         		if (currentModule.getProperty(BuilderHelper.getModuleDeletionKey())==null){// means was not deleted
         			String moduleName = currentModule.getProperty(BuilderHelper.getModuleNameKey());
             		String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());
             		VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
             		RotationDescription moduleRotation = currentModule.getPhysics().get(0).getRotation(); 
             		if (moduleType.contains("ATRON")){
             		atronModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
             		atronModules.add(currentModule);
             		}else if (moduleType.contains("MTRAN")){
             			mtranModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
                 		mtranModules.add(currentModule);
             		}else if (moduleType.contains("Odin")){            		
             			odinAllModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
                 		odinAllModules.add(currentModule);
                 		//System.out.println("dddddd");
                 		if (moduleType.contains("OdinBall")){
                 			odinBallModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
                 		}else {
                 			odinOtherModulesPositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
                 		}
             		}
         		}else{
                 // do nothing
         		}
         	}        	
         	
         	
         	 ATRONBuilder atronbuilder = new ATRONBuilder();             
         	 ArrayList<ModuleConnection> atronModuleConnection = atronbuilder.allConnections(atronModulePositions);        	 
         	 JME_simulation.setModules(atronModules);
         	 JME_simulation.worldDescription.setModulePositions(atronModulePositions);
         	 JME_simulation.worldDescription.setModuleConnections(atronModuleConnection);                          
              JME_simulation.placeModules();
              
              
             /* OdinBuilder odinBuilder = new OdinBuilder();
              odinBuilder.setBallPos(odinBallModulePositions);
              odinBuilder.setModulePos(odinOtherModulesPositions);             
         	 ArrayList<ModuleConnection> odinModuleConnection = odinBuilder.allConnections();        	 
         	 JME_simulation.setModules(odinAllModules);
         	 JME_simulation.worldDescription.setModulePositions(odinAllModulePositions);
         	 JME_simulation.worldDescription.setModuleConnections(odinModuleConnection);                          
              JME_simulation.placeModules();
              System.out.println("Balls Size is: "+ odinBallModulePositions.size()); //for debugging
              System.out.println("Modules Size is: "+ odinOtherModulesPositions.size()); //for debugging
 */             
              System.out.println("Pos Size is: "+ JME_simulation.worldDescription.getModulePositions().size()); //for debugging
          	System.out.println("Con Size is: "+ JME_simulation.worldDescription.getConnections().size()); 
               
         }       
    }                                         

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        System.out.println("Behaviours toolbar-->Assign button pressed");//for debugging        
        JME_simulation.setPicker(new AssignLabel());
         guiUtil.passTo(jTextField1," Select the module to assign the label named "+jTextField3.getText()+ " to");
         
    }                                         

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        System.out.println("Behaviours toolbar -->Label button pressed");//for debugging
        jTextField3.setEditable(true);
        guiUtil.passTo(jTextField1," Type in the name of the label in the activated field");
    }                                         

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {                                          
         System.out.println("Behaviours toolbar -->Use button pressed");//for debugging
        guiUtil.passTo(jTextField1, "Select the module to program using label");
    }                                         

    private void jCheckBoxMenuItem1ActionPerformed2(java.awt.event.ActionEvent evt) {
      System.out.println("View-->Toolbars-->Modular robot specific");//for debugging
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem1, jToolBar8);         
    }

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {
       //System.out.println("Modular robot specific-->Module");//for debugging     	
    	CommonOperationsStrategy com = new CommonOperationsStrategy(JME_simulation);
    	VectorDescription zeroPosition = new VectorDescription(0,0,0);
    	VectorDescription atronPosition = new VectorDescription(0,-0.441f,0.5f);
		VectorDescription mtranPosition = new VectorDescription(-1f,-0.4621f,0.5f);
		VectorDescription odinPosition = new VectorDescription(1f,-0.4746f,0.5f);    	
    	if (JME_simulation.getModules().size()>=0){    		
    		
        if (this.chosenMRname.equalsIgnoreCase("ATRON")&& moduleExists(atronPosition)==false ){
    	com.addDefaultConstructionModule(this.chosenMRname, atronPosition);
    	}else if (this.chosenMRname.equalsIgnoreCase("MTRAN")&& moduleExists(mtranPosition)==false){
    		com.addDefaultConstructionModule(this.chosenMRname,mtranPosition );
    	}else if (this.chosenMRname.equalsIgnoreCase("Odin")&& moduleExists(odinPosition)==false){
    		com.addDefaultConstructionModule(this.chosenMRname, odinPosition);
    	}else {
    		com.addDefaultConstructionModule(this.chosenMRname, zeroPosition);
    	}
    	}
    }
    
    private boolean moduleExists(VectorDescription assignedModulePosition ){
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
    		if (modulePosition.equals(assignedModulePosition)){
    			return true;
    		}
    	}
		return false;    	
    }
    
    
    
    private void resetToolBarVisibility(){
    jToolBar1.setVisible(true);
    jToolBar2.setVisible(true);
//    jToolBar3.setVisible(true);
    jToolBar4.setVisible(true);
    jToolBar5.setVisible(true);

    }  
   
    /**
     * @param args the command line arguments
     */
 /*   public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuickPrototyping quickPrototyping = new QuickPrototyping(JME_simulation);
                quickPrototyping.setSize(330, 390);
                quickPrototyping.setVisible(true);                
            }
        });
    }*/
   public static void activate(final JMEBasicGraphicalSimulation simulation) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				QuickPrototyping quickPrototyping = new QuickPrototyping(simulation);
                quickPrototyping.setSize(330, 390);
                quickPrototyping.setVisible(true); 
			}
		});
	}
    
    public JToolBar getToolBar1(){         
    return jToolBar1;
    }
    
      public JToolBar getToolBar2(){         
    return jToolBar2;
    }
      
       public JToolBar getToolBar3(){         
    return jToolBar3;
    }
        public JToolBar getToolBar4(){         
    return jToolBar4;
    } 
          public JToolBar getToolBar5(){         
    return jToolBar5;
    }  

    
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem10;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem82;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem83;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem84;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem85;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu43;
    private javax.swing.JMenu jMenu44;
    private javax.swing.JMenu jMenu45;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar10;
    private javax.swing.JMenuBar jMenuBar13;
    private javax.swing.JMenuBar jMenuBar15;
    private javax.swing.JMenuBar jMenuBar8;
    private javax.swing.JMenuItem jMenuItem57;
    private javax.swing.JMenuItem jMenuItem58;
    private javax.swing.JMenuItem jMenuItem59;
    private javax.swing.JMenuItem jMenuItem60;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JToolBar jToolBar7;
    private javax.swing.JToolBar jToolBar8;
    // End of variables declaration
    
}

package ussr.builder;

import javax.swing.JToolBar;
import com.jmex.physics.util.PhysicsPicker;

import ussr.builder.construction.ConstructionToolSpecification;
import ussr.builder.genericSelectionTools.ColorConnectorsSelectionTool;
import ussr.builder.genericSelectionTools.NEW;
import ussr.builder.genericSelectionTools.NEW1;
import ussr.builder.genericSelectionTools.RemoveModuleSelectionTool;
import ussr.builder.gui.FileChooser;
import ussr.builder.gui.GuiUtilities;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;


/**
 * The main responsibility of this class is to take care of the main GUI window for  project called
 * "Quick Prototyping of Simulation Scenarios".   
 * USER GUIDE.
 * In order to start "Quick Prototyping of Simulation Scenarios" or so called interactive builder do 
 * the following:
 * 1) Locate simulation file for one of the modular robots(MR), currently are supported:
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
 *    NOTE: During the construction of  MR morphology follow the hits displayed at the last toolbar of
 *    "Quick Prototyping of Simulation Scenarios" window called "Assistant".         
 * 5) Using one of the above selection tools construct desired morphology(shape) of the modular robot and
 *    after that press "P" button on keyboard to start the simulation. *    
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
   
   private final GuiUtilities guiUtil = new GuiUtilities();
   
   private String chosenMRname ="ATRON"; //MR- modular robot. Default is ATRON, just do not have the case when it is empty String
   
   private String rotationName ="EW";//Default, just do not have the case when it is empty String
   
   private int connectorNr;
   
   private static final int amountATRONConnectors = 7;
   
   private static final int amountMTRANConnectors = 5;  
   
   private static final int amountOdinBallConnectors = 11;
    
    /** Creates new form QuickPrototyping1 */
    public QuickPrototyping(JMEBasicGraphicalSimulation simulation) {
        initComponents();
        //Set to generic view      
        guiUtil.changeToSetLookAndFeel(this);         
        this.JME_simulation = (JMESimulation) simulation;  
        adaptGuiToModularRobot();// Adapt GUI to modular robot existing in simulation
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
        jToolBar5 = new javax.swing.JToolBar();
        jComboBox1 = new javax.swing.JComboBox();
        jButton9 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jToolBar6 = new javax.swing.JToolBar();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuBar8 = new javax.swing.JMenuBar();
        jMenuBar10 = new javax.swing.JMenuBar();
        jMenuBar11 = new javax.swing.JMenuBar();
        jMenu31 = new javax.swing.JMenu();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JSeparator();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenu32 = new javax.swing.JMenu();
        jMenu33 = new javax.swing.JMenu();
        jCheckBoxMenuItem67 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem68 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem69 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem73 = new javax.swing.JCheckBoxMenuItem();
        jSeparator22 = new javax.swing.JSeparator();
        jMenuItem44 = new javax.swing.JMenuItem();

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

        jToolBar5.setRollover(true);
        jToolBar5.setPreferredSize(new java.awt.Dimension(310, 35));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATRON", "MTRAN", "Odin" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jToolBar5.add(jComboBox1);

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButton9.setText("Con");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setPreferredSize(new java.awt.Dimension(31, 30));
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton9);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
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

        jToolBar6.setRollover(true);
        jToolBar6.setPreferredSize(new java.awt.Dimension(310, 35));

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
        jToolBar6.add(jButton14);

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
        jToolBar6.add(jButton15);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EW", "WE", "DU", "UD", "SN", "NS" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jToolBar6.add(jComboBox6);

        getContentPane().add(jToolBar6);

        jToolBar2.setRollover(true);
        jToolBar2.setPreferredSize(new java.awt.Dimension(310, 35));

        jLabel1.setText("Assistant:");
        jToolBar2.add(jLabel1);

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setToolTipText("");
        jToolBar2.add(jTextField1);

        getContentPane().add(jToolBar2);
        getContentPane().add(jMenuBar1);
        getContentPane().add(jMenuBar8);
        getContentPane().add(jMenuBar10);

        jMenu31.setText("File");

        jMenuItem41.setText("Open");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu31.add(jMenuItem41);

        jMenuItem42.setText("Save as ");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu31.add(jMenuItem42);
        jMenu31.add(jSeparator21);

        jMenuItem43.setText("Exit");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu31.add(jMenuItem43);

        jMenuBar11.add(jMenu31);

        jMenu32.setText("View");

        jMenu33.setText("Toolbars");

        jCheckBoxMenuItem67.setSelected(true);
        jCheckBoxMenuItem67.setText("Simulation");
        jCheckBoxMenuItem67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem67);

        jCheckBoxMenuItem68.setSelected(true);
        jCheckBoxMenuItem68.setText("Rendering");
        jCheckBoxMenuItem68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem3ActionPerformed(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem68);

        jCheckBoxMenuItem69.setSelected(true);
        jCheckBoxMenuItem69.setText(" Generic");
        jCheckBoxMenuItem69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem7ActionPerformed(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem69);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Specific to modular robots (MR)");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed1(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("Specific rotations of MR");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed1(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem2);

        jCheckBoxMenuItem73.setSelected(true);
        jCheckBoxMenuItem73.setText("Assistant");
        jCheckBoxMenuItem73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu33.add(jCheckBoxMenuItem73);
        jMenu33.add(jSeparator22);

        jMenuItem44.setText("Customize...");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu33.add(jMenuItem44);

        jMenu32.add(jMenu33);

        jMenuBar11.add(jMenu32);

        setJMenuBar(jMenuBar11);

        pack();
    }// </editor-fold>

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
       //System.out.println("File-->Exit");//for debugging
        this.dispose();
    }                                          
   
    private void adaptGuiToModularRobot(){
    	String modularRobotName ="";
    	if (JME_simulation.getModules().get(0).getProperty("ussr.module.type") == null){//Handles empty simulation
    		// do nothing
    	}else{
    		modularRobotName = JME_simulation.getModules().get(0).getProperty("ussr.module.type");
    		
    	}
		 
		if (modularRobotName.contains("Odin")){
			jComboBox1.setSelectedIndex(2);			
		}else if (modularRobotName.contains("ATRON")){
			jComboBox1.setSelectedIndex(0);			
		}else if (modularRobotName.contains("MTRAN")){
			jComboBox1.setSelectedIndex(1);			
		}
                 jComboBox2.setSelectedIndex(0);
		jComboBox6.setSelectedIndex(0);
                 
         jButton11.setEnabled(false);
        jButton12.setEnabled(false);
         guiUtil.passTo(jTextField1,"Select one of MR names in comboBox(4th toolbar) ");// informing user
	}
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("File-->Save as");//for debugging            
        //System.out.println("File-->"+jMenuItem2.getText());//for debugging 
         this.fileChooserSave = new FileChooser(JME_simulation,false); 
         fileChooserSave.activate();
         guiUtil.passTo(jTextField1,"Save simulation data to XML file");// informing user
    }                                          

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("File-->Open");//for debugging
        //System.out.println("File-->"+jMenuItem3.getText());//for debugging 
          this.fileChooserOpen = new FileChooser(JME_simulation,true); 
         fileChooserOpen.activate();
         guiUtil.passTo(jTextField1,"Load simulation data to XML file");// informing user
    }                                          

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       //System.out.println("Simulation ToolBar-->Pause/Play simulation");//for debugging 
        System.out.println("Simulation ToolBar-->"+jButton1.getToolTipText());//for debugging        
      if (JME_simulation.isPaused()==false){        
			JME_simulation.setPause(true);
			System.out.println("Pause on");//for debugging 
                        guiUtil.passTo(jTextField1, "Simulation is in paused state");// informing user
			jButton1.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/pause.JPG"));

		}else{
			System.out.println("Play"); //for debugging 
                        guiUtil.passTo(jTextField1, "Simulation running");// informing user
			jButton1.setIcon(new javax.swing.ImageIcon("resources/quickPrototyping/icons/play.JPG"));
			JME_simulation.setPause(false);
		}
        
    }                                        

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        //System.out.println("View-->Toolbars-->Simulation");//for debugging 
        //System.out.println("View-->Toolbars-->"+jCheckBoxMenuItem1.getText());//for debugging        
        this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem1, jToolBar1);        
    }                                                  

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        System.out.println("View-->Toolbars-->Assistant");        
       // System.out.println("View-->Toolbars-->"+jCheckBoxMenuItem2.getText());//for debugging     
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem2, jToolBar2);  
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
          //  System.out.println("View-->Toolbars-->"+ jCheckBoxMenuItem3.getText());//for debugging                       
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem68, jToolBar3); 
    }                                                  

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //System.out.println("GenericToolbar-->Move"); //for debugging 
         System.out.println("GenericToolbar-->"+jButton5.getToolTipText());//for debugging 
         //JME_simulation.worldDescription.getModulePositions().get(0).toString();
         //System.out.println(""+ JME_simulation.worldDescription.getModulePositions().get(0).toString() ); //for debugging 
         
         JME_simulation.worldDescription.setModulePositions(JME_simulation.worldDescription.getModulePositions());
         //JME_simulation.worldDescription.getConnections();
         JME_simulation.worldDescription.setModuleConnections(JME_simulation.worldDescription.getConnections());
         JME_simulation.placeModules();
         
        guiUtil.passTo(jTextField1, "Select and move module");// informing user
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // System.out.println("GenericToolbar-->Rotate"); //for debugging
          System.out.println("GenericToolbar-->"+jButton6.getToolTipText());//for debugging 
        //JME_simulation.setPicker(new NEW1(JME_simulation, -45.0f));
         guiUtil.passTo(jTextField1, "Select module to rotate");// informing user
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //System.out.println("GenericToolbar-->Delete"); //for debugging 
        System.out.println("GenericToolbar-->"+jButton7.getToolTipText());//for debugging 
        guiUtil.passTo(jTextField1, "Select module to delete");// informing user
        JME_simulation.setPicker(new RemoveModuleSelectionTool(JME_simulation));
    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         //System.out.println("GenericToolbar-->Color connectors"); //for debugging 
         System.out.println("GenericToolbar-->"+jButton8.getToolTipText());//for debugging 
         guiUtil.passTo(jTextField1, "Select module to color its connectors");// informing user
        JME_simulation.setPicker(new ColorConnectorsSelectionTool(JME_simulation));
    }                                        

    private void jCheckBoxMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
         System.out.println("View-->Toolbars-->Module generic");//for debugging
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem69, jToolBar4); 
    }       
   
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
           //System.out.println("View-->Toolbars-->Customize");//for debugging 
        //   System.out.println("View-->Toolbars-->"+jMenuItem8.getText());//for debugging
          // resetToolBarVisibility();
           //ToolBarDisplayer toolBarDisplayer = new ToolBarDisplayer(this);
          // toolBarDisplayer.activate();
    }                                          

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"OnConnector"));
        guiUtil.passTo(jTextField1,"Select connector on "+ this.chosenMRname +" modular robot");
    }                                        

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
       JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"AllConnectors"));
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module");
    }                                         
    
    ConstructionToolSpecification constructionTools = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"Loop",0);
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
         guiUtil.passTo(jTextField1,"1)Select " +this.chosenMRname +" module,2)use NEXT and BACK");
         jButton11.setEnabled(true);
        jButton12.setEnabled(true);
         this.connectorNr =0;
	 ConstructionToolSpecification constructionToolsnew = new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"Loop",this.connectorNr);
        this.constructionTools = constructionToolsnew;
        JME_simulation.setPicker(constructionToolsnew);
         
    }                                         

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        System.out.println("RenderToolbar-->Lights");//for debugging
        System.out.println("RenderToolbar-->"+jCheckBox5.getText());//for debugging
                if (JME_simulation.getLightState().isEnabled() == false ){
                       System.out.println("Selected");//for debugging
                        jCheckBox5.setSelected(true);
                        JME_simulation.getLightState().setEnabled(true);
                         guiUtil.passTo(jTextField1, "Rendering lights");// informing user
                }else {
                        System.out.println("De-Selected");//for debugging
                        jCheckBox5.setSelected(false);
                        JME_simulation.getLightState().setEnabled(false);
                        guiUtil.passTo(jTextField1, "Stopped rendering lights");// informing user
                }
    }                                          

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Normals");//for debugging
        System.out.println("RenderToolbar-->"+jCheckBox4.getText());//for debugging        
        if ( JME_simulation.isShowNormals() == false ){
            System.out.println("Selected");//for debugging
            jCheckBox4.setSelected(true);
            JME_simulation.setShowNormals(true);
             guiUtil.passTo(jTextField1, "Rendering normals");// informing user
        }else {
            System.out.println("De-Selected");//for debugging
            jCheckBox4.setSelected(false);
            JME_simulation.setShowNormals(false);
            guiUtil.passTo(jTextField1, "Stopped rendering normals");// informing user
        }         
    }                                          

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Bounds");//for debugging
        System.out.println("RenderToolbar-->"+jCheckBox3.getText());//for debugging
        if ( JME_simulation.isShowBounds() == false ){
            System.out.println("Selected");//for debugging
            jCheckBox3.setSelected(true);
            JME_simulation.setShowBounds(true);
            guiUtil.passTo(jTextField1, "Rendering bounds");// informing user
        }else {
            System.out.println("De-Selected");//for debugging
            jCheckBox3.setSelected(false);
            JME_simulation.setShowBounds(false);
            guiUtil.passTo(jTextField1, "Stopped rendering bounds");// informing user
        }
    }                                          

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // System.out.println("RenderToolbar-->Wireframe");//for debugging
        System.out.println("RenderToolbar-->"+jCheckBox2.getText());//for debugging
        if ( JME_simulation.getWireState().isEnabled() == false ){
          System.out.println("Selected");//for debugging
           jCheckBox2.setSelected(true);
           JME_simulation.getWireState().setEnabled(true);
           guiUtil.passTo(jTextField1, "Rendering wireframe");// informing user
       }else {
            System.out.println("De-Selected");//for debugging
           jCheckBox2.setSelected(false);
           JME_simulation.getWireState().setEnabled(false);
           guiUtil.passTo(jTextField1, "Stopped rendering wireframe");// informing user
       }
    }                                          

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //System.out.println("RenderToolbar-->Physics");//for debugging
        System.out.println("RenderToolbar-->"+jCheckBox1.getText());//for debugging
                  if (JME_simulation.isShowPhysics() == false ){
                             System.out.println("Selected");//for debugging
                      jCheckBox1.setSelected(true);
                      JME_simulation.setShowPhysics(true);
                      guiUtil.passTo(jTextField1, "Rendering physics");// informing user
                  }else {
                          System.out.println("De-Selected");//for debugging
                      jCheckBox1.setSelected(false);
                      JME_simulation.setShowPhysics(false);
                      guiUtil.passTo(jTextField1, "Stopped rendering physics");// informing user
                  }
    }                                          

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
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
            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"none"}));
            jButton9.setEnabled(false);
            jComboBox6.setEnabled(false);
        }
        jButton11.setEnabled(false);
        jButton12.setEnabled(false);
        guiUtil.passTo(jTextField1,this.chosenMRname +" modular robot (MR)");
    }                                          

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
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
    /*   else if (this.chosenMRname.equalsIgnoreCase("Odin")){
       if (this.connectorNr>amountOdinConnectors){ this.connectorNr=0;}
       }*/
       guiUtil.passTo(jTextField1,this.chosenMRname +" module is on connector number "+this.connectorNr);
    }                                         

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                          
         this.connectorNr--;
		if (this.chosenMRname.equalsIgnoreCase("ATRON") && this.connectorNr<0){
			this.connectorNr =7;//reset
		}else if (this.chosenMRname.equalsIgnoreCase("MTRAN") && this.connectorNr<0){this.connectorNr=5;}
		else if (this.chosenMRname.equalsIgnoreCase("Odin") && this.connectorNr<0){this.connectorNr=11;}
		 constructionTools.moveToNextConnector(this.connectorNr);
       guiUtil.passTo(jTextField1,this.chosenMRname +" module is on connector number "+this.connectorNr);
    }                                         

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
    JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"OppositeRotation"));        
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module to rotate it opposite ");
    }                                         

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
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
       int connectorNumber = Integer.parseInt(jComboBox2.getSelectedItem().toString());
	 JME_simulation.setPicker(new ConstructionToolSpecification(JME_simulation, this.chosenMRname,"chosenConnector",connectorNumber));
        guiUtil.passTo(jTextField1,"Select " +this.chosenMRname +" module");
    }                                          

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {                                          
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
            System.out.println("View-->Toolbars-->Module generic");//for debugging
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem1, jToolBar5); 
    }

    private void jCheckBoxMenuItem2ActionPerformed1(java.awt.event.ActionEvent evt) {
         
         this.guiUtil.changeToolBarVisibility(jCheckBoxMenuItem2, jToolBar6); 
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
/*    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuickPrototyping3 quickPrototyping = new QuickPrototyping3(JME_simulation);
                quickPrototyping.setSize(320, 320);
                quickPrototyping.setVisible(true);                
            }
        });
    }*/
   public static void activate(final JMEBasicGraphicalSimulation simulation) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				QuickPrototyping quickPrototyping = new QuickPrototyping(simulation);
                quickPrototyping.setSize(320, 320);
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
    private javax.swing.JButton jButton2;
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
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem67;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem68;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem69;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem73;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu31;
    private javax.swing.JMenu jMenu32;
    private javax.swing.JMenu jMenu33;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar10;
    private javax.swing.JMenuBar jMenuBar11;
    private javax.swing.JMenuBar jMenuBar8;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JToolBar jToolBar6;
    // End of variables declaration
    
}

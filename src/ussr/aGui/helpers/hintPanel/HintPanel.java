package ussr.aGui.helpers.hintPanel;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

/**
 * Is responsible for display of hints to the user about what is going on in simulation environment and
 * what the user can do next. Used as teaching tool at early stages of using simulator. Can be used to communicate
 * attention, error and so on information(look enumeration). In a way is a substitute to JOptionPane, in order to avoid
 * attracting to much attention to select ok, cancel and so on buttons in JOptionPane.
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class HintPanel extends JPanel implements HintPanelInter {

	/**
	 * Width and height of hint panel in the frame.
	 */
	private int width,height;

	/**
	 * The panel for giving feedback to the user about what is going on in simulation environment and what user
	 * can do next. Used as teaching tool at early stages of using simulator. 
	 * @param width, the width value.
	 * @param height,the height value.
	 */
	public HintPanel(int width, int height){
		this.width = width;
		this.height = height;
		/*Initialize components constituting it*/
		initComponents();
	}

	/**
	 * Initialize the components of the panel.
	 */
	private void initComponents() {	
		/*Size of the panel*/
		setPreferredSize(new Dimension(width,height));

		/*Instantiate components*/
		jLabelIcon =  new javax.swing.JLabel();
		jTextPaneHintDisplay = new javax.swing.JTextPane();
		jScrollPaneForHintDisplay = new javax.swing.JScrollPane();
		

		/*Define appearance*/
		/*Label without text, just so there is only icon on the label*/
		jLabelIcon.setIcon(HintPanelTypes.INFORMATION.getImageIcon());
		int  iconWidth = jLabelIcon.getIcon().getIconWidth();

		/*relative dimension to the dimension of the panel, icon and tolerance*/
		jTextPaneHintDisplay.setPreferredSize(new Dimension(width-2*iconWidth,height-(2*iconWidth))); 
		jTextPaneHintDisplay.setEditable(false);// do not allow user to edit		 

		jScrollPaneForHintDisplay.setViewportView(jTextPaneHintDisplay);		 

		/*Internal layout of the panel*/
		GroupLayout jPanelLayout = new GroupLayout(this);
		this.setLayout(jPanelLayout);

		jPanelLayout.setAutoCreateGaps(true);/* Gaps between component generated automatically.*/
		/*Position components sequentially and keep their dimensions as PreferredSize, do not allow resizing.*/
		jPanelLayout.setHorizontalGroup(
				jPanelLayout.createSequentialGroup()
				//Forces preferred side of component
				.addComponent(jLabelIcon,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPaneForHintDisplay,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)				
		);

		jPanelLayout.setVerticalGroup(
				jPanelLayout.createSequentialGroup()
				.addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jLabelIcon,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPaneForHintDisplay,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))						
		);
	}

	/**
	 * Displays title on the top of the panel.
	 * @param borderTitle, the title displayed on the top of the panel.
	 */
	public void setBorderTitle(String borderTitle){
		setBorder(javax.swing.BorderFactory.createTitledBorder(borderTitle));
	}

	/**
	 * Sets the type of icon in the panel. For example attention, information, error and so on. 
	 * @param typeOfIcon, type of icon.
	 */
	public void setType(HintPanelTypes typeOfIcon){
		jLabelIcon.setIcon(typeOfIcon.getImageIcon());
	}

	/**
	 * Displays text in the hint panel.
	 * @param hintText, the text to display in hint panel.
	 */
	public void setText(String hintText){	
		jTextPaneHintDisplay.setText(hintText);
		jTextPaneHintDisplay.revalidate();
		jTextPaneHintDisplay.repaint();
		
	}
	

	/*Declaration of components*/
	private javax.swing.JLabel jLabelIcon;
	private javax.swing.JTextPane jTextPaneHintDisplay;
	private javax.swing.JScrollPane jScrollPaneForHintDisplay;
}

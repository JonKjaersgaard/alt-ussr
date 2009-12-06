package ussr.aGui;

/**
 * Defines visual appearance of YOUR NEW MAIN FRAME implemented by YOU. Serves as an example for how to design new visual appearance for main GUI window.
 * Replicates part of design from MainFrameSeparate.
 * Please leave this class alone for future developers and use the copy of it.  
 * @author Konstantinas
 */
@SuppressWarnings("serial")
public class YourNewMainFrame extends MainFrames {

	
	/**
	 * Defines visual appearance of YOUR NEW MAIN FRAME implemented by YOU. Serves as an example for how to design new visual appearance for main GUI window.
	 * Replicates part of design from MainFrameSeparate.
	 * Please leave this class alone for future developers and use the copy of it.  
	 */
	public YourNewMainFrame(){
		initComponents();
	}
	
	
	/**
	 * Starts main GUI frame(window) separate from simulation environment, in separate thread.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {
		
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() {				
				mainFrame = new YourNewMainFrame();
				mainFrame.setVisible(true);	
			}
		});
}

	@Override
	public void activate() {
		YourNewMainFrame.main(null);	
	}

	@Override
	public void initComponents() {
		getContentPane().setLayout(new java.awt.FlowLayout());
		initJToolbarGeneralControl((int)SCREEN_SIZE.getWidth()/2,HORIZONTAL_TOOLBAR_HEIGHT);
		//setSize(600, 300);
		pack(); 
		changeToLookAndFeel(this);		
	}
}

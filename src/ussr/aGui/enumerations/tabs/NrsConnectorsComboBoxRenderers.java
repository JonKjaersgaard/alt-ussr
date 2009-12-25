package ussr.aGui.enumerations.tabs;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import ussr.aGui.MainFramesInter;
import ussr.aGui.helpers.ComboBoxRenderer;
import ussr.aGui.tabs.TabsInter;
import ussr.builder.enumerations.SupportedModularRobots;

public enum NrsConnectorsComboBoxRenderers {

	
	
	ATRON_RENDERER(new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ATRON_CONNECTORS)),
	ODIN_RENDERER(new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.ODIN_BALL_CONNECTORS)),
	MTRAN_RENDERER(new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.MTRAN_CONNECTORS)),
	CKBOT_STANDARD_RENDERER(new ComboBoxRenderer(IconsNumbersConnectors.getAllImageIcons(),SupportedModularRobots.CKBOTSTANDARD_CONNECTORS));
	
	
	private ComboBoxRenderer comboBoxRenderer;


	NrsConnectorsComboBoxRenderers(ComboBoxRenderer comboBoxRenderer){
		this.comboBoxRenderer =comboBoxRenderer;
		this.comboBoxRenderer.setPreferredSize(new Dimension(15, 15));
	}
	
	public ComboBoxRenderer getComboBoxRenderer() {
		return comboBoxRenderer;
	}	
	
	/**
	 * Contains the icons for connector numbers used in comboBox of Construct Robot tab.
	 * @author Konstantinas
	 */
	public enum IconsNumbersConnectors {

		CONNECTOR_NR0_BLACK(TabsInter.CONNECTOR_NR0_BLACK),
		CONNECTOR_NR1_RED(TabsInter.CONNECTOR_NR1_RED),
		CONNECTOR_NR2_CYAN(TabsInter.CONNECTOR_NR2_CYAN),
		CONNECTOR_NR3_GREY(TabsInter.CONNECTOR_NR3_GREY),
		CONNECTOR_NR4_GREEN(TabsInter.CONNECTOR_NR4_GREEN),
		CONNECTOR_NR5_MAGENDA(TabsInter.CONNECTOR_NR5_MAGENDA),
		CONNECTOR_NR6_ORANGE(TabsInter.CONNECTOR_NR6_ORANGE),
		CONNECTOR_NR5_PINK(TabsInter.CONNECTOR_NR5_PINK),
		CONNECTOR_NR8_BLUE(TabsInter.CONNECTOR_NR8_BLUE),
		CONNECTOR_NR9_WHITE(TabsInter.CONNECTOR_NR9_WHITE),
		CONNECTOR_NR10_YELLOW(TabsInter.CONNECTOR_NR10_YELLOW),
		CONNECTOR_NR11_LIGHT_GREY(TabsInter.CONNECTOR_NR11_LIGHT_GREY),
		;

		/**
		 * The icon created from image file.
		 */
		private ImageIcon imageIcon;

		/**
		 * The directory, where image is located in.
		 */
		private String imageDirectory;

		/**
		 * The name of image file, not including extension.
		 */
		private String imageName;

		/**
		 * Contains the icons for connector numbers used in comboBox of Construct Robot tab.
		 * @param imageName, the name of the image file.
		 */
		IconsNumbersConnectors(String imageName){
			this.imageName = imageName;
			this.imageDirectory = formatIconDirectory(imageName);
			this.imageIcon = new ImageIcon(imageDirectory);
		}

		/**
		 * Formats directory, where icon is located.
		 * @param imageName, the name of the image file.
		 * @return directory, where icon is located.
		 */
		private static String formatIconDirectory(String imageName){	
			return TabsInter.DIRECTORY_ICONS_CONNECTORS_COLORS+imageName+MainFramesInter.DEFAULT_ICON_EXTENSION1;
		}

		/**
		 * Returns all imageIcons.
		 * @return all imageIcons.
		 */
		public static ImageIcon[] getAllImageIcons(){
			ImageIcon[] imageIcons= new ImageIcon[IconsNumbersConnectors.values().length];

			for (int index=0;index<IconsNumbersConnectors.values().length;index++){			
				imageIcons[index]=IconsNumbersConnectors.values()[index].getImageIcon();
			}
			return imageIcons;
		}
		
		/**
		 * Returns image icon with respect to it's name (name of image file).
		 * @return image icon with respect to it's name (name of image file).
		 */
		public ImageIcon getImageIcon() {
			return imageIcon;
		}
	}
}

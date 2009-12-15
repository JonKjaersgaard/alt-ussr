package ussr.aGui.enumerations.tabs;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.TextureDescription;

/**
 * Contains supported texture descriptions abstracted over their instances.  
 * @author Konstantinas
 *
 */
public enum TextureDescriptions {
	
	/*Constants of texture descriptions represented in String format*/
	GRASS_TEXTURE (WorldDescription.GRASS_TEXTURE),
	GREY_GRID_TEXTURE(WorldDescription.GREY_GRID_TEXTURE),
	MARS_TEXTURE(WorldDescription.MARS_TEXTURE),
	WHITE_GRID_TEXTURE(WorldDescription.WHITE_GRID_TEXTURE),
	WHITE_TEXTURE(WorldDescription.WHITE_TEXTURE);


	/**
	 * The texture description.
	 */
	private TextureDescription textureDescription;

	/**
	 * Abstracts instance of texture description in String representation.
	 * @param textureDescription
	 */
	TextureDescriptions(TextureDescription textureDescription){
		this.textureDescription = textureDescription;
	}
	
	/**
	 * @return
	 */
	public TextureDescription getTextureDecription(){
		return this.textureDescription;
	}
	
	public String getFileName(){
		return this.textureDescription.getFileName();
	}
	
	public static String texture(WorldDescription.TextureDescription texture){
		
		for (int textureNr=0;textureNr<TextureDescriptions.values().length;textureNr++){
			TextureDescriptions.values()[textureNr].equals(texture);
			return TextureDescriptions.values()[textureNr].name();
		}		
		return null;		
	}
	
}

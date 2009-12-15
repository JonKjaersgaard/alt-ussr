package ussr.aGui.enumerations.tabs;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.TextureDescription;

/**
 * Contains supported texture descriptions abstracted over their instances. 
 * NOTE: Add new textures  descriptions here in case of new ones required. 
 * @author Konstantinas *
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
	 * Contains supported texture descriptions abstracted over their instances. 
     * NOTE: Add new textures  descriptions here in case of new ones required. 
	 * @param textureDescription, a physics-engine independent description of a texture that can be loaded from a file.
	 */
	TextureDescriptions(TextureDescription textureDescription){
		this.textureDescription = textureDescription;
	}
	
	/**
	 * Returns instance of chosen texture description.
	 * @return instance of chosen texture description.
	 */
	public TextureDescription getTextureDecription(){
		return this.textureDescription;
	}
	
	/**
	 * Returns name of the file, texture description is associated with.
	 * @return name of the file, texture description is associated with.
	 */
	public String getFileName(){
		return this.textureDescription.getFileName();
	}
	
	/**
	 * TODO
	 * Returns texture description constant from the 
	 * @param texture
	 * @return
	 */
	public static String texture(WorldDescription.TextureDescription texture){
		
		for (int textureNr=0;textureNr<TextureDescriptions.values().length;textureNr++){
			TextureDescriptions.values()[textureNr].equals(texture);
			return TextureDescriptions.values()[textureNr].name();
		}		
		return null;		
	}
	
}

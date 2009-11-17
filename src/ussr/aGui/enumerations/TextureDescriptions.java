package ussr.aGui.enumerations;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.TextureDescription;

public enum TextureDescriptions {
	
	
	GRASS_TEXTURE (WorldDescription.GRASS_TEXTURE),
	GREY_GRID_TEXTURE(WorldDescription.GREY_GRID_TEXTURE),
	MARS_TEXTURE(WorldDescription.MARS_TEXTURE),
	WHITE_GRID_TEXTURE(WorldDescription.WHITE_GRID_TEXTURE),
	WHITE_TEXTURE(WorldDescription.WHITE_TEXTURE),
	
	;


	private TextureDescription textureDescription;

	TextureDescriptions(TextureDescription textureDescription){
		this.textureDescription = textureDescription;
	}
	
	public TextureDescription getTextureDecription(){
		return this.textureDescription;
	}
	
	public String getFileName(){
		return this.textureDescription.getFileName();
	}
	
}

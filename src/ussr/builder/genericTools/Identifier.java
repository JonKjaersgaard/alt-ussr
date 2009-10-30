package ussr.builder.genericTools;

import java.awt.Color;
import java.util.List;

import com.jme.scene.Geometry;

import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;

public class Identifier extends CustomizedPicker {

	
	private List<Color> moduleComponentsColors;
	
	private TypesIdentifier typeIndentifier;
	
	private boolean moduleSelected;
	
	
	


	public Identifier(TypesIdentifier typeIndentifier){
		this.typeIndentifier = typeIndentifier;
	}
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		if (typeIndentifier.equals(TypesIdentifier.MODULE)){
		this.moduleComponentsColors = component.getModel().getColorList();
		this.moduleSelected = true;
		component.getModel().setColor(Color.WHITE);
		
		}
		
	}

	@Override
	protected void pickTarget(Geometry target) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Color> getModuleComponentsColors() {
		return moduleComponentsColors;
	}
	public boolean isModuleSelected() {
		return moduleSelected;
	}

}

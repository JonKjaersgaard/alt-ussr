package ussr.builder.genericSelectionTools;

import java.awt.Font;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.Text3D;
import com.jmex.font3d.effects.Font3DTexture;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;
import ussr.samples.atron.ATRON;

public class AssignLabel extends CustomizedPicker  {

	

	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		Module selectedModule = component.getModel();
		VectorDescription vd = component.getLocalPosition();
		RotationDescription rd = component.getRotation();
		
		float x = vd.getX();
		float y = vd.getY();
		float z = vd.getZ();
		
		Matrix3f mat =  new Matrix3f();
		mat.set(rd.getRotation());
		
		 Font3D myfont;
		 myfont = new Font3D(new Font("Arial", Font.PLAIN, 1), 0.1, true, true, true);
		Font3DTexture fonttexture = new Font3DTexture(/*TestTextureState.class.getClassLoader().getResource("jmetest/data/model/marble.bmp")*/);
	     fonttexture.applyEffect(myfont);
	    Text3D mytext = myfont.createText(selectedModule.getProperty(BuilderHelper.getModuleTypeKey()), 0.012f, 0);		 
		
         ColorRGBA fontcolor = new ColorRGBA(1, (float) Math.random(), (float) Math.random(), 1);
         mytext.setFontColor(fontcolor);
         mytext.setIsCollidable(false);
         mytext.setName("GOOD"+"");
         
         BuilderHelper help= new  BuilderHelper();
         for(DynamicPhysicsNode part: component.getNodes()){ 
        	 Matrix3f m =  mat.transpose();
        	 if(rd.getRotation().equals(ATRON.ROTATION_EW.getRotation())){
        		  //mytext.setLocalTranslation(new Vector3f(vd.getX()-0.047f,vd.getY()+0.047f,vd.getZ()-0.047f));
        		  mytext.setLocalTranslation(new Vector3f(x-0.04f,y+0.04f,z-0.015f));        		 
        	 }else if (rd.getRotation().equals(ATRON.ROTATION_WE.getRotation())){
        		 mytext.setLocalTranslation(new Vector3f(vd.getX()+0.04f,vd.getY()-0.04f,vd.getZ()+0.015f));        		 
        	 }else if (rd.getRotation().equals(ATRON.ROTATION_DU.getRotation())){
        		 mytext.setLocalTranslation(new Vector3f(x-0.06f,y-0.02f,z));
        	 }else if (rd.getRotation().equals(ATRON.ROTATION_UD.getRotation())){        		 
        		 mytext.setLocalTranslation(new Vector3f(x-0.02f,y-0.06f,z));
        	 }else if (rd.getRotation().equals(ATRON.ROTATION_SN.getRotation())){
        		 mytext.setLocalTranslation(new Vector3f(x-0.058f,y,z));        		 
        	 }else if (rd.getRotation().equals(ATRON.ROTATION_NS.getRotation())){        		 
        		 mytext.setLocalTranslation(new Vector3f(x,y-0.06f,z));
        	 }
        	 mytext.setLocalRotation(m);        	 
        	 part.attachChild(mytext);        	 
         }
		
	}

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}

}

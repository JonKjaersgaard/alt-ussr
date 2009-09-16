package ussr.samples.atron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;

public class ATRONKinematicModel {
	
	/* displacements at joint angle = 0 relative to the JME north/south hemi frames (they _do_ coincide at joint angle = 0) */
	public static List<Vector3f> connectorsRelativeDisplacement = Arrays.asList(	new Vector3f((float) ( (ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), -(ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * (-1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), -(ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * (-1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * (-1/ Math.sqrt(2))), -(ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * (-1/ Math.sqrt(2))), -(ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * ( 1/ Math.sqrt(2))),  (ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * (-1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * ( 1/ Math.sqrt(2))),  (ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * (-1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * (-1/ Math.sqrt(2))),  (ATRON.UNIT/2)),
																					new Vector3f((float) ( (ATRON.UNIT/2) * ( 1/ Math.sqrt(2))), (float) ((ATRON.UNIT/2) * (-1/ Math.sqrt(2))),  (ATRON.UNIT/2)) );
	
	/* displacements at joint angle = 0 relative to the JME north/south hemi frames (they _do_ coincide at joint angle = 0) rotated PI/4 about the z axis */
	public static List<Vector3f> normalizedAlignedConnectorsRelativeDisplacement = Arrays.asList(	new Vector3f( 0, 1,-1),
																									new Vector3f(-1, 0,-1),
																									new Vector3f( 0,-1,-1),
																									new Vector3f( 1, 0,-1),
																									new Vector3f( 0, 1, 1),
																									new Vector3f(-1, 0, 1),
																									new Vector3f( 0,-1, 1),
																									new Vector3f( 1, 0, 1) );
	/* TODO: define the scaling vector to relate the two displacement and encapsulate it in a function with the 45deg rotation */
	/* The quantity to divide the displacement for to get the normalized one */
	public static float atronNorm = ATRON.UNIT / 2;
	/* The rotation to apply to the displacement to get the aligned one */
	public static Quaternion alignmentRotation = new Quaternion().fromAngleAxis((float) (Math.PI/4), new Vector3f(0, 0, 1));
	
	public static Vector3f alignAndNormalize(Vector3f vector) {
		return alignmentRotation.mult(vector).divide(atronNorm);
	}

	public static Node getNorthHemisphereNode(Module mod) {
		return ((JMEModuleComponent)(mod.getComponent(0))).getModuleNode();
	}

	public static Node getSouthHemisphereNode(Module mod) {
		return ((JMEModuleComponent)(mod.getComponent(1))).getModuleNode();
	}

	/* That's the ground truth, as it is computed using the translation/rotation obtained directly from JME */
	public static List<Vector3f> getGlobalPosition(Module mod) {
		Node south = getSouthHemisphereNode(mod);
		Node north = getNorthHemisphereNode(mod);
		List<Vector3f> connectors = new ArrayList<Vector3f>();
		for(int i=0; i<4; i++) {
			connectors.add( i, north.getLocalTranslation().add(north.getLocalRotation().mult(connectorsRelativeDisplacement.get(i))) );
		}
		for(int i=4; i<8; i++) {
			connectors.add( i, south.getLocalTranslation().add(south.getLocalRotation().mult(connectorsRelativeDisplacement.get(i))) );
		}
		return connectors;		
	}

	/* That's the ground truth, as it is computed using the translation/rotation obtained directly from JME */
	public static List<Vector3f> getAlignedNormalizedGlobalPosition(Module mod) {
		Node south = getSouthHemisphereNode(mod);
		Node north = getNorthHemisphereNode(mod);
		List<Vector3f> connectors = new ArrayList<Vector3f>();
		for(int i=0; i<4; i++) {
			connectors.add( i, north.getLocalTranslation().add(north.getLocalRotation().mult(alignAndNormalize(connectorsRelativeDisplacement.get(i)))) );
		}
		for(int i=4; i<8; i++) {
			connectors.add( i, south.getLocalTranslation().add(south.getLocalRotation().mult(alignAndNormalize(connectorsRelativeDisplacement.get(i)))) );
		}
		return connectors;		
	}

}

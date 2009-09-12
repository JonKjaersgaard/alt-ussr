package ussr.visualization;

import ussr.physics.PhysicsParameters;

public class VisualizationParameters {

	private boolean showPhysicalModules = true ;

	public boolean getShowPhysicalModules() {
		return showPhysicalModules;
	}

	public void setShowPhysicalModules(boolean showPhysicalModules) {
		this.showPhysicalModules = showPhysicalModules;
	}
	
    private static VisualizationParameters defaultParameters;
    
    public static synchronized VisualizationParameters get() {
        if(defaultParameters==null) defaultParameters = new VisualizationParameters();
        return defaultParameters;
    }
	
}

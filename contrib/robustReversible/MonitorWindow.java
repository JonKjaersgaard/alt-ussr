package robustReversible;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MonitorWindow extends JPanel {

    private static MonitorWindow window;
    
    private static synchronized void display() {
        if(window!=null) return;
        JFrame frame = new JFrame("Monitor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Add contents to the window.
        window = new MonitorWindow();
        frame.add(window);
        //Display the window.
        frame.pack();
        frame.setLocation(10, 10);
        frame.setVisible(true);
    }
    
    private static void checkWindow() {
        if(window==null) display();
    }
    
    public static void update(int myID, int globalState, int[] pendingStates) {
        checkWindow();
        window.updateData(myID,globalState,pendingStates);
    }

    private Map<Integer,JLabel> labels = new HashMap<Integer,JLabel>();
    private synchronized void updateData(int id, int state, int[] pending) {
        JLabel label = labels.get(id);
        if(label==null) {
            label = new JLabel(format(id,state,pending));
            this.add(label);
            this.validate();
            labels.put(id, label);
        } else
            label.setText(format(id,state,pending));
    }
    private static String format(int id, int state, int[] pending) {
        return ""+id+"="+state+" "+Arrays.toString(pending);
    }
}

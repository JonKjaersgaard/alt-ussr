package ussr.aGui.tabs.additionalResources.recycleBin;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class RedirectSystemOutput{

    /**
	 * WHAT IS THAT?
	 */
	private static final long serialVersionUID = 1L;
	private boolean catchErrors;
    private boolean logFile;
    private String fileName;
    private JTextArea jTextArea;

    PrintStream aPrintStream  = new PrintStream(new FilteredStream(new ByteArrayOutputStream()));
 

    /** Creates a new RedirectFrame.
     *  From the moment it is created,
     *  all System.out messages and error messages (if requested)
     *  are diverted to this frame and appended to the log file 
     *  (if requested)
     *
     * for example:
     *  RedirectedFrame outputFrame =
     *       new RedirectedFrame
                (false, false, null, 700, 600, JFrame.DO_NOTHING_ON_CLOSE);
     * this will create a new RedirectedFrame that doesn't catch errors,
     * nor logs to the file, with the dimentions 700x600 and it doesn't 
     * close this frame can be toggled to visible, hidden by a controlling 
     * class by(using the example) outputFrame.setVisible(true|false)
     *  @param catchErrors set this to true if you want the errors to 
     *         also be caught
     *  @param logFile set this to true if you want the output logged
     *  @param fileName the name of the file it is to be logged to
     *  @param width the width of the frame
     *  @param height the height of the frame
     *  @param closeOperation the default close operation
     *        (this must be one of the WindowConstants)
     */
    public RedirectSystemOutput
       (boolean catchErrors, boolean logFile, String fileName, JTextArea jTextArea) {
        this.catchErrors = catchErrors;
        this.logFile = logFile;
        this.fileName = fileName;
        this.jTextArea = jTextArea;

        System.setOut(aPrintStream); // catches System.out messages
        if (this.catchErrors){
            System.setErr(aPrintStream); // catches error message
        }
    }



   class FilteredStream extends FilterOutputStream {
        public FilteredStream(OutputStream aStream) {
            super(aStream);
          }

        public void write(byte b[]) throws IOException {
            String aString = new String(b);
            jTextArea.append(aString);
        }

        public void write(byte b[], int off, int len) throws IOException {
        	String aString = new String(b , off , len);
            jTextArea.append(aString);
        	
        	if (fileName==null){
        	   //Do nothing        	  
           }else{        	
            if (logFile) {
                FileWriter aWriter = new FileWriter(fileName, true);
                aWriter.write(aString);
                aWriter.close();
            }
           }
        }
    }

}


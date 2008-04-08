package atron.mobileXmlControllers;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

public class XmlControllers {
    public static void writeXmlController(Object f, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
        encoder.writeObject(f);
        encoder.close();
    }


    public static XMLDecoder readXmlController(String filename) throws Exception {
        XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
        return decoder;
    }
    
}

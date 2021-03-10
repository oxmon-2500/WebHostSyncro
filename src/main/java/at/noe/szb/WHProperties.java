package at.noe.szb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

public class WHProperties{
    static final String PROPERTIES = ".webHostSyncro.properties";
    static final String LASTEXETIME = "lastExecutionTime";
    Properties properties = new Properties();
    private LocalDateTime lastExecuted;
    
    public LocalDateTime getLastExecute() {
    	return lastExecuted;
    }
    protected LocalDateTime rdLastExecutionTime() {
        try {
            InputStream in = new FileInputStream(PROPERTIES);
            properties.load(in);
        } catch (IOException a) {
            System.out.println("Couldn't find/load file "+PROPERTIES);
            return null;
        }
        lastExecuted =  LocalDateTime.parse(properties.getProperty(LASTEXETIME));
        return lastExecuted;
    }
    protected void wrLastExecutionTime() {
        // write time back
        try{
            FileOutputStream fos = new FileOutputStream(PROPERTIES);
            properties.setProperty("lastExecutionTime", ""+LocalDateTime.now());
            properties.store(fos, "Do not edit; maintained by WebHostSyncro"); //a comment will be added
            fos.flush();
        }catch(IOException e ){
            System.out.println("Couldn't find/load "+PROPERTIES);
        }
    }
}

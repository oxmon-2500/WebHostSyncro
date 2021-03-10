package at.noe.szb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SyncroTest {
    final static String PROPS = "src/test/resources/config.properties";
    private static Properties config = null;
    
    @BeforeClass
    public static void test_start() {
        config = new Properties();
    }
    
	@Test
	public void test_properties_rd() throws Exception{
        //read properties
        try {
        	InputStream in = new FileInputStream(PROPS);
            config.load(in);
        } catch (IOException a) {
            System.out.println("Couldn't find/load file!");
        }
        System.out.println(".... "+config.getProperty("ignoreFile"));
        LocalDateTime lastTime = LocalDateTime.parse(config.getProperty("lastExecutionTime"));
        System.out.println("lastTime="+lastTime);
	}
	
	@Test
	public void test_properties_wr() throws Exception{
        // write time back
        try{
            FileOutputStream fos = new FileOutputStream(PROPS);
            config.setProperty("lastExecutionTime", ""+LocalDateTime.now());
            config.store(fos, "aaaa set to bbb"); //a comment will be added
            fos.flush();
        }catch(IOException e ){
            System.out.println("Couldn't find/load "+PROPS);
        }
	}
}

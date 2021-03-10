package at.noe.szb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * syncro local files to webhost
 * 
 * uploads all modified files since last run
 *
 */

public class Syncro{
    
    private static String config, host, root, remote;
    private static WHProperties whProperties;
    private static List<Path> filesModified; // to upload
    
    public static void usage(int id) {
        //TODO use id
        System.out.println( "usage: Syncro [--config filename] [--host hostname] [--source srcDir] [--remote remoteDir]" );
        System.exit(1);
    }
    
    private static void checkFile(final Path path) { //called by Files.walk
    	File f = path.toFile();
    	long fileLastModified = f.lastModified();
    	ZonedDateTime zdt = ZonedDateTime.of(whProperties.getLastExecute(), ZoneId.systemDefault());
        long propLastModified = zdt.toInstant().toEpochMilli();
        
    	if (fileLastModified > propLastModified) {
            System.out.println(path.toString());
            filesModified.add(path);
    	}
    }
    public static void doit( String[] args ){
        for (int i=0; i<args.length-1; i+=2) {
            if (args[i].startsWith("--")) {
                switch(args[i].substring(2)) {
                case "config"   : config = args[i+1]; break;
                case "host"     : host   = args[i+1]; break;
                case "localroot": root   = args[i+1]; break;
                case "remote"   : remote = args[i+1]; break;
                default:
                    System.out.println("Unknown parameter");
                    usage(0);
                }
            }
        }//for
        if (root==null) {
            usage(1);
        }
        if (config==null) {
        	config = "webHostConfig.json";
        }
        Config cfg = Config.create(Paths.get(config));
        
        whProperties = new WHProperties();
        filesModified = new ArrayList<>();
        
        LocalDateTime lastExecutionTime = whProperties.rdLastExecutionTime();
        
        if (lastExecutionTime!=null) {
            // traverse src dir for changed files
            String rootDir = root;
            // see: https://www.techiedelight.com/traverse-directory-print-files-java-7-8/
            try {
                // using Files.walk method
                Files.walk(Paths.get(rootDir))
                        .filter(Files::isRegularFile)
                        .forEach(Syncro::checkFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // update properties
        whProperties.wrLastExecutionTime();
        
    }
    public static void main( String[] args ){
        Syncro.doit(args);
    }
}

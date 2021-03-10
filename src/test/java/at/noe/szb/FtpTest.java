package at.noe.szb;

import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

/**
 * Unit test for simple App.
 */

public class FtpTest{
	
	//must be set before:  FTP_HOST, FTP_USER, FTP_PSWD 
    //@Test
    public void test_simple() throws Exception {
        final FTPClient ftp;
        ftp = new FTPClient();
        ftp.setListHiddenFiles(true);
        
        // suppress login details
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        
        final FTPClientConfig config;
        config = new FTPClientConfig();
        config.setUnparseableEntries(true); //saveUnparseable);
        ftp.configure(config);
        
        String server  = Crypto.get("FTP_HOST");
        
        ftp.connect(server);
        
        // After connection attempt, you should check the reply code to verify
        // success.
        final int reply;
        reply = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            System.err.println("FTP server refused connection.");
            System.exit(1);
        }
        if (!ftp.login(Crypto.get("FTP_USER"), Crypto.get("FTP_PSWD"))) {
            ftp.logout();
        }
        ftp.enterLocalPassiveMode();
        
        //FTPFileFilter filter = null;
        for (final FTPFile f : ftp.mlistDir("/httpdocs/oxmon.com")) {
            //System.out.println(f.getRawListing());
            System.out.println(f.toFormattedString("AT"));
        }
        ftp.noop(); // check that control connection is working OK
        ftp.logout();
        ftp.disconnect();
    }
}

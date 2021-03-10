package at.noe.szb;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	protected final static String AES_KEY = "aLe0=juwzLtDD327"; //16 bytes
	
    public static String get(final String id) {
		final byte[] aesKey = AES_KEY.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec sc = new SecretKeySpec(aesKey, "AES");
		Cipher cipher=null;
		byte[] encryptedMsg=null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, sc);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		try {
			encryptedMsg = cipher.doFinal(Base64.getDecoder().decode(System.getenv(id)));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(encryptedMsg);
    }
}

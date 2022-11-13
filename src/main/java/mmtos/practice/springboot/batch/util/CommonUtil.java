package mmtos.practice.springboot.batch.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class CommonUtil {

	public static String encodeByMD5(String original){
		String add = "sejong";
		String beforeEncoded = original + add;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(beforeEncoded.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return Hex.encodeHexString(md.digest());
	}
}

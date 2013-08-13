package com.prl.cvto.author;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.prl.cvto.util.BlindEye;
import com.prl.cvto.util.StringUtility;

public class UserCredential implements Serializable {

    private static final long serialVersionUID = 8357037178350636132L;
    
	private final static byte[] KEY = {
		0x11, 0x38, 0x43, 0x01, 0x45, 0x45, 0x00, 0x4d, 0x0e, 0x38, 0x37
	};
	
    private byte[] mUserId = null;
    private byte[] mSecretKey = null;
    private boolean mGuestAccount = false;
    
    public UserCredential(byte[] uid, byte[] secretKey, boolean isGuest) {
    	mGuestAccount = isGuest;
    	setCredential(uid, secretKey);
    }
    
    public UserCredential(String uid, String secretKey, boolean isGuest) {
    	mGuestAccount = isGuest;
    	setCredential(uid, secretKey);
    }
    
    /**
     * Load previous saved credential
     */
    public static UserCredential load(FileInputStream credentialFileStream) {
    	UserCredential credential = null;
    	
		try {
			ObjectInputStream ois = new ObjectInputStream(credentialFileStream);
			credential = (UserCredential) ois.readObject();
			ois.close();
			
			// Decode the value
			byte[] decodeUserId = BlindEye.transform(credential.getUserIdAsBytes(), KEY);
			byte[] decodeKey = BlindEye.transform(credential.getSecretKeyAsBytes(), KEY);
            credential.setCredential(decodeUserId, decodeKey);
			
		} catch (Exception e) {
			// Well, something happened, I guess the version 
			// of the credential had been changed
			e.printStackTrace();
		}
    	
    	return credential;
    }
    
    /**
     * Save the current credential to file
     */
    public static void save(UserCredential credential, FileOutputStream credentialFileStream) {
    	// Well, if we fail to save credential, at least we have credential in memory
    	// And user may need to register again next time launch application. Fortunately,
    	// I suppose they will get the same user id from server.    	
    	try {
            // Encode first
            byte[] uid = credential.getUserIdAsBytes();
            byte[] secretKey = credential.getSecretKeyAsBytes();

            credential.setCredential(
                    BlindEye.transform(uid, KEY), BlindEye.transform(secretKey, KEY));

			ObjectOutputStream oos = new ObjectOutputStream(credentialFileStream);
			oos.writeObject(credential);
			oos.close();

            // Recover back, since we still need credential work
            credential.setCredential(uid, secretKey);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public boolean isGuestAccount() {
    	return mGuestAccount;
    }
    
    public final byte[] getUserIdAsBytes() {
    	return mUserId;
    }
    
    public final byte[] getSecretKeyAsBytes() {
    	return mSecretKey;
    }
    
    public String getUserId() {
    	return StringUtility.hexByteArrayToString(mUserId);
    }
    
    public String getSecretKey() {
    	return StringUtility.hexByteArrayToString(mSecretKey);
    }
    
    public void setCredential(byte[] uid, byte[] secretKey) {
    	mUserId = uid;
    	mSecretKey = secretKey;
    }
    
    public void setCredential(String uid, String secretKey) {
    	mUserId = StringUtility.hexStringToByteArray(uid);
    	mSecretKey = StringUtility.hexStringToByteArray(secretKey);
    }
}
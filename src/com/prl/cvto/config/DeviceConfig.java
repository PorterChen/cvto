package com.prl.cvto.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;
import android.os.Environment;

/**
 * Device related configuration
 * 
 * @OriginAuthor zillians/giggle
 * 
 */
public class DeviceConfig {
    private static final String SETTING_FOLDER = ".setting";
    
    // The reason why we define APP_NAME here instead of using R.string.app_name is because the app_name
    // may affected by locale, so if some settings depend on it, the setting may put to different folder 
    // once the locale is changed.
    private static final String APP_NAME = "cvto";
    private static final File APP_FOLDER = new File(Environment.getExternalStorageDirectory(), APP_NAME);
    private static final String UNIQUE_DEVICE_ID_FILE = "UniqueDeviceId";
    private static final String CREDENTIAL_FILE_NAME = ".ucl";
    
    private static UUID mUniqueDeviceId = null;
    
    private File mSettingFolder = null;
    
    public DeviceConfig() {
        initFileSavedDir();
        initDeviceUniqueId(false);
        
        mSettingFolder = new File(APP_FOLDER, SETTING_FOLDER);
        if (!mSettingFolder.exists()) {
            mSettingFolder.mkdir();
        }
    }
    
    public String getAppNameNoLocaleConcern() {
    	return APP_NAME;
    }
    
    public File getAppFolder() {
        return APP_FOLDER;
    }
    
    public File getSettingFolder() {
        return mSettingFolder;
    }
    
    public String getCredentialFileName() {
    	return CREDENTIAL_FILE_NAME;
    }

    public UUID getUniqueDeviceId() {
        return mUniqueDeviceId;
    }
    
    public byte[] getUniqueDeviceIdAsBytes() {
    	ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    	
    	bb.putLong(mUniqueDeviceId.getMostSignificantBits());
    	bb.putLong(mUniqueDeviceId.getLeastSignificantBits());    	
    	return bb.array();    	
    }
   
    private void initFileSavedDir() {
        if (!APP_FOLDER.exists()) {
            APP_FOLDER.mkdirs();
        }
    }
    
    private void initDeviceUniqueId(boolean second_try) {

        if (mUniqueDeviceId == null) {  
            File uidfile = new File(APP_FOLDER, UNIQUE_DEVICE_ID_FILE);
            
            boolean retry = false; 
            try {
                if (!uidfile.exists())
                    writeUidFile(uidfile);
                mUniqueDeviceId = readUidFile(uidfile);
            } catch (IOException e) {
            	e.printStackTrace();
            	retry = true;
            } catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
            
            if (retry) {
                if (second_try) {
                    // well, just assign a all-zero uuid
                    mUniqueDeviceId = new UUID(0, 0);
                }
                else {
                    uidfile.delete();
                    initDeviceUniqueId(true);
                }
            }
        }
    }

    private static UUID readUidFile(File uidfile) throws IOException, ClassNotFoundException {
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(uidfile));  
    	UUID uuid = (UUID) ois.readObject();    	
    	ois.close();
    	return uuid;
    }

    private static void writeUidFile(File uidfile) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(uidfile));        
        oos.writeObject(UUID.randomUUID());
        oos.close();
    }
    
}
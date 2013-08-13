package com.prl.cvto.author;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.util.Log;

public class AuthManager {
    
    public static class AuthResult implements Serializable {
		private static final long serialVersionUID = -3057312296269829168L;
		
		public boolean Result = false;
        public String UID = null;
        public String SecretKey = null;
    }
    
    public static class AuthServers implements Serializable {
		private static final long serialVersionUID = -5436915769594970160L;
		
		public boolean Result = false;
    	public String ProxyServer = null;
    	public String VideoServer = null;
    }
    
    // TODO: remove Log
    private static final String TAG = "CVTO.AuthManager";
    
    public static final String GUEST_UID = "517e4b38783e4a3db42aaf7eabd8387c";
    public static final String GUEST_SECRET = "7cb3523718e04ffd968b083820bc71008ac02080ffc3406babbe57c88da48c2e";
    
    public static final String AUTH_SERVER = ""; 
    public static final String PROXY_QUERY_SERVER = "";
    public static final String CHALLENGE_SERVER = ""; 

    public static String requestSmsAndChallengeCode(String phone) {
        DefaultHttpClient client = (DefaultHttpClient) NoTrustSocketFactory.createMyHttpClient();
        String challengeCode = null;
        
        // Send sms message and get challenge code
        try {
            challengeCode = postSmsRequest(phone, client);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return challengeCode;
    }
    
    public static AuthResult registerAccount(String challengeCode, String activationCode) {
        DefaultHttpClient client = (DefaultHttpClient) NoTrustSocketFactory.createMyHttpClient();
        AuthResult authResult = new AuthResult();
        
        try {
            // Get uid/secret key
            String uidSecret = postRequestUIDSecretKey(challengeCode, activationCode, client);
            if (uidSecret == null) {
                throw new Exception();
            }
            
            // Parse result
            JSONObject jsonObject = new JSONObject(uidSecret);
            authResult.Result = true;
            authResult.UID = jsonObject.getString("uid");
            authResult.SecretKey= jsonObject.getString("secret_key");   
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return authResult;
    }
    
    /**
     * Each time the application launch, we should get proxy server which will upload data later
     * @param uid the user id
     * @param secretKey user secret key
     * @param version the application version
     * @return proxy server used for upload data
     */
    public static AuthServers getServers(String uid, String secretKey, String version) {
        DefaultHttpClient client = (DefaultHttpClient) NoTrustSocketFactory.createMyHttpClient();
        HttpParams httpParameters = new BasicHttpParams();
        
        // Set the timeout in milliseconds until a connection is established.
        // The default value is zero, that means the timeout is not used.
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        HttpConnectionParams.setSoTimeout(httpParameters,5000);
        client.setParams(httpParameters);
        
        AuthServers authServers = new AuthServers(); 
        
        String host = PROXY_QUERY_SERVER + version;
        HttpGet get = new HttpGet(host);
        
        try {
            String response = httpRequestHelper(host, uid, secretKey, client, get);
            JSONObject jsonObject = new JSONObject(response);
            authServers.ProxyServer = jsonObject.getString("proxy");           
            authServers.VideoServer = jsonObject.getString("video");            
            authServers.Result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authServers;
    }
    
    private static String postSmsRequest(String phone, DefaultHttpClient client) throws IllegalStateException, IOException {
        HttpPost post = new HttpPost(AUTH_SERVER);
        post.setEntity(new StringEntity(phone));
        //Log.d(TAG, "post content: " + getStringFromInputStream(post.getEntity().getContent()));
        
        return httpRequestHelper(AUTH_SERVER, GUEST_UID, GUEST_SECRET, client, post);
    }
    
    private static String postRequestUIDSecretKey(String challengeCode, String activationCode, DefaultHttpClient client) throws IllegalStateException, IOException {
        String host = CHALLENGE_SERVER + challengeCode;
        HttpPost post = new HttpPost(host);
        post.setEntity(new StringEntity(activationCode));
        //Log.d(TAG, "post content: " + getStringFromInputStream(post.getEntity().getContent()));
        
        return httpRequestHelper(host, GUEST_UID, GUEST_SECRET, client, post);
    }
    
    private static String httpRequestHelper(String host, String uid, String password, DefaultHttpClient client, HttpRequestBase request) throws IllegalStateException, IOException {
        URL url = new URL(host);
        AuthScope as = new AuthScope(url.getHost(), url.getPort(), "**server_name**");
        
        UsernamePasswordCredentials upc = new UsernamePasswordCredentials(uid, password);
        BasicCredentialsProvider bcp = new BasicCredentialsProvider();
        bcp.setCredentials(as, upc);
        
        client.setCredentialsProvider(bcp);        
        request.setHeader("User-Agent", "CVTO");
        
        HttpResponse response = client.execute(request);
        HttpEntity result = response.getEntity();
        if (result != null) {
            InputStream contentStream = result.getContent();
            String responseString = getStringFromInputStream(contentStream);
            Log.d(TAG, "Response: " + responseString);
            
            return responseString;
        }
        else {
            Log.d(TAG, "No Response");
            return null;
        }
    }
    
    private static String getStringFromInputStream(InputStream contentStream) throws IOException {
        //
        // To convert the InputStream to String we use the
        // Reader.read(char[] buffer) method. We iterate until the
        // Reader return -1 which means there's no more data to
        // read. We use the StringWriter class to produce the string.
        //
        if (contentStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(contentStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                contentStream.close();
            }
            return writer.toString();
        } else {        
            return "";
        }
    }    
}
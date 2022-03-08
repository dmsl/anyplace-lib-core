package cy.ac.ucy.cs.anyplace.lib.legacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Preferences {
public static long CONNECT_TIMEOUT_SECS = 10;
// 30 seconds is too high.. even 10 might be..
// We'll revise this at some point. and have some defaults here,
// which will be initialized in the file
public static long READ_TIMEOUT_SECS = 30;

private static String api_key = null;
private static String host = null;
private static String port = null;
private static String cache = null;
private static String status;

public static String getAnyplaceDir(){
    return getHome()+"/.anyplace/client/";
}

public static void LoadFromFile(){
    Load(openFile(getAnyplaceDir() + "config"),
         openFile(getAnyplaceDir() + "api_key"));
}

private static void Load(File settings, File apikey) {
    Exception e= null;
    int set_file = 0;
    int api =0;
    if (!settings.exists() || !settings.canRead()) {
        set_file =1;
    }
    if (!apikey.exists() || !apikey.canRead()) {
        api = 1;
    }
    
    BufferedReader reader;
    try {
        reader = new BufferedReader(new FileReader(settings));
        setHost(reader.readLine());
        setPort(reader.readLine());
        String temp = reader.readLine();
        if (temp.charAt(temp.length()-1) !='/'){
            setCache(getHome() + "/"+ temp+ "/" );
        } else {
            setCache(getHome() + "/"+ temp );
        }
    } catch (Exception ex){
        e = ex;
        set_file =2;
    }
    
    try {
        reader = new BufferedReader(new FileReader(apikey));
        setApi_key(reader.readLine());
    } catch (Exception ex){
        e = ex;
        api =2;
    }
    
    if(set_file == 1 || api == 1){
        status = "Error in opening";
    } else if(set_file == 2 || api == 2){
        status = e.getMessage();
    } else {
        status = "OK";
    }
}

private static File openFile(String path){
    File f = new File(path);
    if (f.exists() && f.canRead()){
        return f;
    }
    throw new RuntimeException("Config file missing on: "+ path);
    
}
private static String getHome(){
    return System.getProperty("user.home") ;
}
// CA: all preference related operations should come from here.  ????
// make them private, setting, getting, reading from the file, etc.



public static String getApiKey() {
    return api_key;
}
public static void setApi_key(String api_key) { Preferences.api_key = api_key; }
public static String getPort() { return port; }
public static void setPort(String port) { Preferences.port = port; }
public static String getCache() {
    return cache;
}
public static void setCache(String cache) { Preferences.cache = cache; }
public static String getHost() { return host; }
public static void setHost(String host) { Preferences.host = host; }
}

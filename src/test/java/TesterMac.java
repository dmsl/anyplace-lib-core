import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;


import cy.ac.ucy.cs.anyplace.lib.legacy.Anyplace;
import cy.ac.ucy.cs.anyplace.lib.legacy.Preferences;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(NoConfigTests.class)
public class TesterMac {

static String response;
static String buid;
static String access_token;
static String pois_to;
static String coordinates_la;
static String coordinates_lo;
static String floor;
static String pois_from;
static String range;
static String algorithm;

@BeforeClass
public static void setUpParameters() {
    Preferences.LoadFromFile();
    access_token = "ACCESS_TOKEN";
    
    buid = "username_1373876832005";
    pois_to = "poi_064f4a01-07bd-45fa-9579-63fa197d3d90";
    coordinates_la = "35.14414934169342";
    coordinates_lo = "33.41130472719669";
    floor = "-1";
    pois_from = "poi_88a34fd5-75bd-4601-81dc-fe5aef69bd3c";
    range = "100";
    algorithm = "1";
}
public static boolean isNumeric(String str) {
    try {
        Double.parseDouble(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
@Test
public void testMacFingerprints() {
    String cmd[] = new String[3];
    cmd[0] = "/bin/sh";
    cmd[1] = "-c";
    cmd[2] = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport -s | grep ':' | tr -s ' ' | cut -d' ' -f3 -f4| tr ' ' '\n'";
    
    String aps[] = new String[200];
    Process p;
    String s, temp;
    int counter = 0;
    try {
        p = Runtime.getRuntime().exec(cmd);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((s = br.readLine()) != null && counter <= 20) {
            temp = "{\"bssid\":\"";
            temp += s;
            temp += "\",\"rss\":";
            s = br.readLine();
            if (!isNumeric(s)) {
                continue;
            }
            temp += s;
            temp += "}";
            temp = temp.toLowerCase();
            aps[counter++] = temp;
        }
        p.destroy();
        br.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    aps = Arrays.copyOf(aps, counter);
    for (int j = 0; j < counter; j++) {
        
        //System.out.println(aps[j]);
    }
    Anyplace client2 = new Anyplace("ap-dev.cs.ucy.ac.cy", "443", "res/");
    
    response = client2.estimatePosition(buid, floor, aps, algorithm);
    //System.out.println(response + "\n");
}
}

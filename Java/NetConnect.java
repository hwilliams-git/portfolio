import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NetConnect
{
    /**
    **
    ** I can write other functions for Http and Url, but I decided there was no reason--due to Https obviously being more secure.
    **
    **/
    
    public String Https(String uri, String post, boolean displayHeaders)
    {
        HttpsURLConnection con;
        try
        {
            // Open connection to the url
            con = (HttpsURLConnection) new URL(uri).openConnection();

            // Set User Agent header to simulate browser
            con.setRequestProperty("User-Agent", 
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
            // Not actually sure if this is needed. I think it is inherently done because I've made code
            // without it, and they still worked.
            con.setDoInput(true);

            // Check if user passed data to be sent to server
            if(post.length() > 0)
            {
                // Specifically set output to POST. The default is GET.
                con.setRequestMethod("POST");
                // Set output. If you don't, this will not work.
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(post);
                // Flush the writer buffer to get rid of potental lingering data
                wr.flush();
                wr.close();
            }

            // Print all recieved headers to the console
            if(displayHeaders)
            {
                Map<String, List<String>> map = con.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) 
                {
                    System.out.println(entry.getKey() 
                        + ": " + entry.getValue());
                }
                System.out.println("----------");
            }

            BufferedReader br = 
                new BufferedReader(
                    new InputStreamReader(con.getInputStream())); 
            String i; 
            String line = "";
            
            // Get the data retruned by the server
            while ((i = br.readLine()) != null)  
            { 
                line += i;
            }

            br.close();
            return line;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        // If exception is thrown, return null.
        return null;
    }
}

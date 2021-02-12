import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
 
public class DirectoryManager
{
    // Compile a list of names of all the objects in the specified directory 
    public String[] ContentArray(String path)
    {
        File dir = new File(path);

        if(dir.isDirectory())
        {
            // Return an array of just file names.
            return dir.list();
        }else
        {
            // Return null if path is not a directory.
            return null;
        }		
    }
	
    // A different version of the ContentArray(). This one returns a list of directory
    // paths at whatever recursive depth the user defines.
    public List<String> PathList(String path, List<String> dirList, int recurseDepth)
    {
        File dir = new File(path);
        File[] dirArray = null;

        if(dir.isDirectory())
        {
            dirArray = dir.listFiles();
            for(int i = 0;i < dirArray.length;i++)
            {		
                dirList.add(dirArray[i].toString());
                if(recurseDepth > 0)
                {
                    if(dir.isDirectory())
                    {
                        PathList(dirArray[i].toString(), dirList, recurseDepth - 1);
                    }
                }
            }
        }
	    
        // If path is not a directory, the list will return null. Other wise it will return what was found.
        return dirList;
    }
	
    // Create a folder
    public boolean CreateFolder(String path)
    {
        File dir = new File(path);
        if(dir.isDirectory())
        {
            // If path entered is not a directory return false.
            return false;
        }else
        {
            if(dir.mkdir())
            {
                // If folder creation is successful, return true.
                return true;
            }else
            {
                // If folder creation is not successful, return false.
                return false;
            }
        }
    }

    // Write text to a file
    public boolean WriteText(String path, String content, boolean append)
    {
        try(Writer writer = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(path, append), StandardCharsets.UTF_8)))
        {
            if(content.trim().equals(""))
            {
                // If there is noting in the input string, don't add a new line to the file.
                writer.write(content);
            }else
            {
                writer.write(content+"\r\n");
            }

            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Read a file and return its text
    public String ReadText(String path)
    {
        String contents = "";

        try(BufferedReader br = new BufferedReader(
            new FileReader((path))))
        {
            String line = "";
            while((line = br.readLine()) != null) 
            {
                // Add each line read into the content string
                contents += line;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
	    
        // If exception is thrown, content will return null. Other wise it will return what was read from the file.
        return contents;
    }
   
    // Read a file and look for a specified string
    public boolean SearchText(String path, String find)
    {
        String file = ReadText(path);
        try
        {
            file.contains(find);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

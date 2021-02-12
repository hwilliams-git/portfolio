import java.io.File;
 
public class DirectoryManager
{
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
        
		return dirList;
	}
}

package MyLib_v2;
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
import java.util.ArrayList;
import java.util.List;

public class DirMgr
{
	//DebugConsole debug = new DebugConsole(1);
	CatchLog catchLog = new CatchLog();
	
	/*public String[] contentList(String path)
	{
		System.out.println("DirMgr().stringList():\r\n");
		
		File dir = new File(path);
		//.list() returns String[] of file names
		//.listFiles() returns File[] of full path names
		if(dir.isDirectory())
		{
			return dir.list();
		}else
		{
			System.out.println("--No contents found in this dir: "+path);
			return new String[0];
		}		
	}*/
	
	public String[] NameList(String path)
	{
		System.out.println("DirMgr().NameList():\r\n");
		
		File dir = new File(path);
		
		if(dir.isDirectory())
		{
			return dir.list();
			//.list() returns String[] of file names
		}else
		{
			System.out.println("--No contents found in this dir: "+path);
			return new String[0];
		}		
	}
	
	public List<String> PathList(String path, List<String> dirList, int recurseDepth)
	{
		//System.out.println("DirMgr().PathList():\r\n");
		
		File dir = new File(path);
		File[] dirArray;

		if(dir.isDirectory())
		{
			dirArray = dir.listFiles();
			for(int i = 0;i < dirArray.length;i++)
			{		
				dirList.add(dirArray[i].toString());
				if(recurseDepth != 0)
				{
					if(dir.isDirectory())
					{
						PathList(dirArray[i].toString(), dirList, recurseDepth - 1);
					}
				}
			}
		}
			//return dir.listFiles();
			//.listFiles() returns File[] of full path names
		/*}else
		{
			//System.out.println("--No contents found in this dir: "+path);
			//return new File[0];
		}*/	
		
		return dirList;
	}
	
	public boolean createFolder(String path)
	{
		String debugText = "DirMgr().createFolder():\r\n";
		
		File dir = new File(path);
		if(dir.isDirectory())
		{
			//debug.print(debugText+"--Folder already exists: "+path);
			return false;
		}else
		{
			if(dir.mkdir())
			{
				//debug.print(debugText+"--Created folder: "+path);
				return true;
			}else
			{
				//debug.print(debugText+"--Could not create folder: "+path);
				return false;
			}
		}
	}
	
	public boolean writeText(String path, String content, boolean append)
	{
		//System.out.println("DirMgr().writeText():");
		
		try(Writer writer = new BufferedWriter(
	                new OutputStreamWriter(
	                		new FileOutputStream(path, append), StandardCharsets.UTF_8)))
		{
			if(content.equals(""))
			{
				writer.write(content);
			}else
			{
				writer.write(content+"\r\n");
			}
			//System.out.println("--Created text file: "+path);
			return true;//new String[] {"true",""};
		}catch(Exception e)
		{
			//System.out.println("--Could not create text file: "+path);
			e.printStackTrace();
			catchLog.SaveLog("C:\\Users\\John\\Desktop\\CatchLog\\", e.getMessage().toString());
			return false;//new String[] {"false","Couldn't write to file"};
		}
	}
	
	public String readText(String path)
	{
		//System.out.println("DirMgr().readText():");
		
		String contents = "";
		        
		try(BufferedReader b = new BufferedReader(
				new FileReader((path))))
		{
			String line = "";
			while((line = b.readLine()) != null) 
			{
				//System.out.println(line);
				contents += line;
			}
			//System.out.println("--Returned text from file: "+path);
		}catch(Exception e)
		{
			//System.out.println("--Could not return text from file: "+path);
			e.printStackTrace();
			catchLog.SaveLog("C:\\Users\\John\\Desktop\\CatchLog\\", e.getMessage().toString());
			contents = "";
		}
		return contents;
	}
	
	public void writeBytes()
	{
		
	}
	
	public void readBytes(String path)
	{
		byte[] getBytes;
		File file = new File(path); 
		int fileSize = (int) file.length(); 				
		getBytes = new byte[fileSize];
		
		try(InputStream is = new FileInputStream(file))
		{
			is.read(getBytes);
			for(int i = 0;i < fileSize;i++)
			{
				System.out.print((char) getBytes[i]);
			}
		}catch(Exception e)
		{
			e.printStackTrace();;
		}
	}
	
	public String[] searchText(String path, String find)
	{
		System.out.println("DirMgr().searchText():");
		
		String file = readText(path);
		try
		{
			file.contains(find);
			return new String[] {"true",""};
		}catch(Exception e)
		{
			e.printStackTrace();
			return new String[] {"false","Couldn't find match in file"};
		}
	}
	
	public String[] delete(String path)
	{
		try
		{
			File file = new File(path);
			file.delete();
			return new String[] {"true",""};
		}catch(Exception e)
		{
			e.printStackTrace();
			return new String[] {"false","Couldn't delete file"};
			//Need to work on console for error reporting
		}
	}
}

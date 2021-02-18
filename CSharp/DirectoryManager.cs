class DirMgr
{
    // Create or append text to a file.
    public bool WriteText(string path, string content, bool append)
    {
        try
        { 
            if(append)
            { 
                // Make sure file exists first.
                if(File.Exists(path))
                {
                    // Add content to existing file.
                    using(StreamWriter write = File.AppendText(path))
                    {
                        write.WriteLine(content);
                    }
                }else
                {
                    // Display error if file doesn't exist.
                    Console.WriteLine("File \"{0}\" does not exist. Content cannot be appended.", path);
                    return false;
                }
            }else
            {
                // Overwrite file with new content.
                using(StreamWriter write = File.CreateText(path))
                {
                    write.WriteLine(content);
                }
            }

            return true;
        }catch(Exception e)
        {
            Console.WriteLine(e.Message);
            return false;
        }
    }

    // Read text from an existing file.
    public string ReadText(string path)
    {
        string content = "";

        // Make sure file exists first.
        if(File.Exists(path))
        {
            // Open the file.
            using(StreamReader read = File.OpenText(path))
            {
                string line = "";
                while ((line = read.ReadLine()) != null)
                {
                    content += line;
                }
            }
        }else
        {
            Console.WriteLine("File \"{0}\" does not exist. Content cannot be read.", path);
            return null;
        }

        // The below examples are more simple ways of doing this task.

        // This one takes all the text in a file and puts it into one string.
        //string text = File.ReadAllText(path);

        // This one takes each line in a file and puts them into their own 
        // element in the array.
        //string[] lines = File.ReadAllLines(path);

        return content;
    }

    // Create or append bytes to a file.
    public bool WriteBytes(string path, byte[] content, bool append)
    {
        try
        { 
            if(append)
            {
                if(File.Exists(path))
                { 
                    using(FileStream stream = new FileStream(path, FileMode.Append))
                    { 
                        for(byte i = 0; i < content.Length; i++)
                        {
                            stream.WriteByte(i);
                        }
                        //stream.Write(content, 0, content.Length);
                    }
                }else
                {
                    // Display error if file doesn't exist.
                    Console.WriteLine("File \"{0}\" does not exist. Content bytes cannot be appended.", path);
                    return false;
                }
            }else
            {
                // Overwrite file with new byte content.
                using(FileStream stream = new FileStream(path, FileMode.Create))
                { 
                    for(byte i = 0; i < content.Length; i++)
                    {
                        stream.WriteByte(i);
                    }
                }
            }

            return true;
        }catch(Exception e)
        {
            Console.WriteLine(e.Message);
            return false;
        }
    }

    public byte[] ReadBytes(string path)
    {
        try
        {
            // Read the bytes from the given file.
            byte[] readBytes = File.ReadAllBytes(path);
            // Use loop to process each byte that was read.
            /*foreach(byte bytes in readBytes)
            {
                Console.Write(bytes + " ");
            }*/

            // Honestly, if bytes are to just be returned, this 
            // whole function may be redundant. But, for personal
            // library purposes--and future ideas--I have added 
            // this this function to this class.
            return readBytes;
        }catch(IOException e)
        {
            Console.WriteLine(e.Message);
            return null;
        }
    }
}

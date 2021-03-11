<?php
//This script was a test to see how I could manage files and folders using PHP

function WriteFile($fileName, $text, $append)
{
    if(file_exists($fileName))
    {
        // I mainly use $append for "a" to append to a file or "w" to overwrite/create a file
        // A boolean can also be used to set the action, but it would mean repeating the fopen()
        // twice, since it only takes a the strings
        $file = fopen($fileName, $append);// or die("Unable to open file!");
      
        // Run until a there aren't any more locks on the file
        // May need to find an alternative, just in case an 
        // endless loop can somehow be made
        while(true)
        {
            // Check if there is a lock on the file
            // This my occur if the file is currently 
            // being read
            if(flock($file, LOCK_EX))
            {
                fwrite($file, $text);
                fclose($file);
                return;
            }
        }
    }else
    {
        //If file doesn't exist, do another action
    }
}
function ReadFile($fileName)
{
    $file = fopen($fileName, "r");// or die("Unable to open file!");
  
    // Lock the file so the contents won't potentially be corrupted
    // trhough multiple handlings
    flock($file, LOCK_SH);
    // Print or return contents of file
    echo(fread($file,filesize($p1)));
    fclose($file);
}
?> 

import java.util.ArrayList;
import java.util.List;

public class WilSON
{
    /**
    **
    ** This is a simple JSON parser I made because the original JSON parser I downloaded couldn't handle the 
    ** the amount of data I was pushing through it.
    **
    **/
    
    // Parse through JSON and return a key/value pair in an array list
    public List<String[]> parseToList(String string)
    {
        String[] arrayList = new String[] {"",""};
        List<String[]> list = new ArrayList<String[]>();
        int arrayIndex = 0;
        int listIndex = 0;

        // Check the first and last char for bracket type
        if((string.charAt(0) == '{')&&(string.charAt(string.length()-1) == '}'))
        {
            // Set Key and Value for JSON object
            for(int i = 1;i < string.length();i++)
            {
                // Parse out the key and get the returned updated index number
                i = setKey(string, i, arrayList);
                
                // Parse out the value and get the returned updated index number
                i = setValue(string, i, arrayList);
                
                // Add array to list to create a key/value pair
                list.add(listIndex, arrayList);
                listIndex++;
                
                // Important: reset this array to a new array. Otherwise you may end up
                // having a full list of just one key/value pair.
                arrayList = new String[] {"", ""};
            }
            
            return list;
        }else if((string.charAt(0) == '[')&&(string.charAt(string.length()-1) == ']'))
        {
            // Set Key and Value for JSON array
            for(int i = 1;i < string.length();i++)
            {
                // Manualy set the key to a number
                arrayList[0] = arrayIndex+"";
                arrayIndex++;
                
                // Parse out the value and get the returned updated index number
                i = setValue(string, i, arrayList);
                
                // Add array to list to create a key/value pair
                list.add(listIndex, arrayList);
                listIndex++;
                
                arrayList = new String[] {"", ""};
            }
            return list;
        }else 
        {
            System.out.println("Check your open and close braces.");
            // If bracket type is wrong, return null
            return null;
        }
    }

    // Set the key
    private int SetKey(String json, int index, String[] jsonArray)
    {
        // Check to see what the key starts with 
        if(json.charAt(index) == '\"')
        {
            // Increment forward to avoid catching the parenthesis 
            index++;
            // Don't set the index. Used the one passed, so functions stay at the right index of the string
            for(;index < json.length();index++)
            {
                // This parenthesis check is for the ending of the key string
                if(json.charAt(index) == '\"')
                {
                    if(jsonArray[0].trim().isEmpty()||
                        (jsonArray[0] == null))
                    {
                        // If the key is empty or null for whatever reason, return the length of the json string to break 
                        // out of the parent loop.
                        System.out.println("Key started as string, but no string was detected.");
                        return json.length();
                    }
                    
                    // If key has a string, move to the next index
                    index++;
                    
                    // Make sure there is a colon after the key
                    if(json.charAt(index) == ':')
                    {
                        index++;
                        return index;
                    }else
                    {
                        // If the colon is missing, the json isn't formatted correctly
                        System.out.println("Key is a string, but it's not separated from the value with a \":\".");
                        return json.length();
                    }
                }
                
                // Add character to the key string
                jsonArray[0] += json.charAt(index);
            }
        }else if((json.charAt(index)+"").matches("[0-9]"))
        {
            // Check if key is a number
            
            for(;index < json.length();index++)
            {
                // Make sure there is a colon after the key
                if(json.charAt(index) == ':')
                {
                    index++;
                    return index;
                }else if((json.charAt(index)+"").matches("[0-9]"))
                {
                    // Double check that each char is a number, just in case.
                    // Set the char into the key string
                    jsonArray[0] += json.charAt(index);
                }else
                {
                    // If both checks fail, there's something wrong with the format
                    System.out.println("Key started with numbers, but did not continue with numbers.");
                    return json.length();
                }
            }
        }else if((json.charAt(index)+"").matches("[tf]"))
        {
            // Check if key is a true or false
            
            for(;index < json.length();index++)
            {
                // Make sure there is a colon after the key
                if(json.charAt(index) == ':')
                {
                    // Make sure the key is actually true or false
                    if((jsonArray[0].equals("true"))||
                        (jsonArray[0].equals("false")))
                    {
                        index++;
                        return index;
                    }else
                    {
                        // If both check fails, there's something wrong with the format
                        System.out.println("Key started with t or f, but did not read as true or false.");
                        System.out.println("json.charAt("+index+"): "+json.charAt(index));
                        return json.length();
                    }
                }
                
                // Add character to the key string
                jsonArray[0] += json.charAt(index);
            }
        }else
        {
            // If key doesn't start with the right character, the format isn't right.
            System.out.println("Key not formatted correctly.");
            System.out.println("json.charAt("+index+"): "+json.charAt(index));
            return json.length();
        }
        
        // This is a default return because Eclipse will throw an error even though there are 
        // multiple returns in this function.
        return index;
    }
    
    // Set the key
    private int setValue(String json, int index, String[] jsonArray)
    {
        // Check to see what the value starts with 
        if(json.charAt(index) == '\"')
        {
            // Increment forward to avoid catching the parenthesis 
            index++;
            for(;index < json.length();index++)
            {
                // This parenthesis check is for the ending of the value string
                if((json.charAt(index) == '\"')&&
                    (json.charAt(index - 1) != '\\'))
                {
                    if((jsonArray[1].trim().isEmpty())||
                        (jsonArray[1] == null))
                    {
                        // If the value is empty or null for whatever reason, set the value to an empty string.
                        jsonArray[1] = "";
                    }
                    
                    // Move to the next index
                    index++;
                    
                    // Check if one of these characters is after the parenthesis.
                    if((json.charAt(index) == ',')||
                        (json.charAt(index) == '}')||
                            (json.charAt(index) == ']'))
                    {
                        // If check is good, return current index because the parent loop will increment again.
                        return index;
                    }else
                    {
                        // If parenthesis is found, but other checks are bad, the format is bad.
                        System.out.println("-----WilSON.getValue-----");
                        System.out.println(jsonArray[1]);
                        System.out.println("Check char after value for key \""+jsonArray[0]+"\".");
                        System.out.println();
                        return json.length();
                    }
                }
                
                // Set char into value string
                jsonArray[1] += json.charAt(index);
            }
        }else if((json.charAt(index)+"").matches("[0-9]"))
        {
            // Check if value is a number
            
            for(;index < json.length();index++)
            {
                if((json.charAt(index) == ',')||
                    (json.charAt(index) == '}')||
                        (json.charAt(index) == ']'))
                {
                    // Return current index
                    return index;
                }else if((json.charAt(index)+"").matches("[0-9]"))
                {
                    // Double check that each char is a number, just in case.
                    // Set the char into the value string
                    jsonArray[1] += json.charAt(index);
                }else
                {
                    // If checks are bad, format is bad.
                    System.out.println("-----WilSON.getValue-----");
                    System.out.println(jsonArray[1]);
                    System.out.println("Value started with numbers, but did not continue with numbers.");
                    System.out.println("json.charAt("+index+"): "+json.charAt(index));
                    System.out.println();
                    return json.length();
                }
            }
        }else if((json.charAt(index)+"").matches("[tf]"))
        {
            // Check if value is a true or false
            
            for(;index < json.length();index++)
            {
                if((json.charAt(index) == ',')||
                    (json.charAt(index) == '}')||
                        (json.charAt(index) == ']'))
                {
                    // Make sure the value is actually true or false
                    if((jsonArray[1].equals("true"))||
                        (jsonArray[1].equals("false")))
                    {
                        // Return current index
                        return index;
                    }else
                    {
                        // If checks are bad, format is bad.
                        System.out.println("-----WilSON.getValue-----");
                        System.out.println("Value started with t or f, but did not read as true or false.");
                        System.out.println("json.charAt("+index+"): "+json.charAt(index));
                        System.out.println(jsonArray[0]+" -> "+jsonArray[1]);
                        System.out.println();
                        return json.length();
                    }

                }
                
                // Set char into value string
                jsonArray[1] += json.charAt(index);
            }
        }else if((json.charAt(index)+"").matches("[n]"))
        {
            // Check if value is a null
            
            for(;index < json.length();index++)
            {
                if((json.charAt(index) == ',')||
                    (json.charAt(index) == '}')||
                        (json.charAt(index) == ']'))
                {
                    if((jsonArray[1].equals("null")))
                    {
                        // Make sure the value is actually null
                        return index;
                    }else
                    {
                        // If checks are bad, format is bad.
                        System.out.println("-----WilSON.getValue-----");
                        System.out.println("Value started with n, but did not read as null.");
                        System.out.println("json.charAt("+index+"): "+json.charAt(index));
                        System.out.println(jsonArray[0]+" -> "+jsonArray[1]);
                        System.out.println();
                        return json.length();
                    }

                }
                jsonArray[1] += json.charAt(index);
            }
        }else if(json.charAt(index) == '{')
        {
            // Check if value is a nested json object
            
            int curlBrace = 0;
            for(;index < json.length();index++)
            {
                // Keep track of the braces
                if(json.charAt(index) == '{')
                {
                    curlBrace++;
                }else if(json.charAt(index) == '}')
                {
                    curlBrace--;
                }
                
                // Check if all braces are accounted for and loop found closing brace
                if((json.charAt(index) == '}')&&
                    (curlBrace == 0))
                {
                    // Add the brace to the value string
                    jsonArray[1] += json.charAt(index);
                    // Increment the index so the parent loop increments again to either end, or move to the next key.
                    index++;
                    return index;
                }
                
                // Add character to value string
                jsonArray[1] += json.charAt(index);
            }
            
            // If it makes it through the loop and reaches this point, then the closing brace was never found.
            System.out.println("-----WilSON.getValue-----");
            System.out.println("Could not find the end of nested JSON object for key \""+jsonArray[0]+"\".");
            System.out.println(jsonArray[1]);
            System.out.println();
            return json.length();
        }else if(json.charAt(index) == '[')
        {
            // Check if value is a nested json array
            
            int squareBrace = 0;
            for(;index < json.length();index++)
            {
                // Keep track of the braces
                if(json.charAt(index) == '[')
                {
                    squareBrace++;
                }else if(json.charAt(index) == ']')
                {
                    squareBrace--;
                }
                
                // Check if all braces are accounted for and loop found closing brace
                if((json.charAt(index) == ']')&&
                    (squareBrace == 0))
                {
                    // Add the brace to the value string
                    jsonArray[1] += json.charAt(index);
                    // Increment the index so the parent loop increments again to either end, or move to the next key.
                    index++;
                    return index;
                }
                
                // Add character to value string
                jsonArray[1] += json.charAt(index);
            }
            
            // If it makes it through the loop and reaches this point, then the closing brace was never found.
            System.out.println("-----WilSON.getValue-----");
            System.out.println("Could not find the end of nested JSON array for key \""+jsonArray[0]+"\".");
            System.out.println(jsonArray[1]);
            System.out.println();
            return json.length();
        }else
        {
            // If key doesn't start with the right character, the format isn't right.
            System.out.println("-----WilSON.getValue-----");
            System.out.println("Value for key \""+jsonArray[0]+"\" is not formatted correctly.");
            System.out.println(jsonArray[1]);
            System.out.println();
            return json.length();
        }
        
        // This is a default return because Eclipse will throw an error even though there are 
        // multiple returns in this function.
        return index;
    }
}

public class cipher
{
    // Build a table of characters to use for encoding and decoding.
    private void BuildTable(char[] tableArray)
    {
        // Set the first index to be a carriage return.
        // Because the chars in this table are based on 
        // the ASCII table, some chars are not within the 
        // "readable" range, which means they would need 
        // to be entered separately.
        tableArray[0] = (char)13;
        
        // Insert all char from 32 to 95
        for(int i = 1;i < 96;i++)
        {
            tableArray[i] = (char)(i + 31);
        }
    }

    // Cycle through the table to get the index of a character.
    private int GetChar(char[] tableArray, char findChar)
    {
        for(int i = 0;i < tableArray.length;i++)
        {
            if(findChar == tableArray[i])
            {
                return i;
            }
        }
        return -1;
    }

    // Encode the plain text using a single character key to shift the table index.
    public String EncodeCaesar(char key, String msg)
    {
        char[] tableArray = new char[96];
        String secret = "";
        int msgIdx = -1;
        int keyIdx = -1;

        // Build the table using the initialized array.
        BuildTable(tableArray);

        // Loop through the message and encode each char
        for(int i = 0;i < msg.length();i++)
        {
            msgIdx = GetChar(tableArray, msg.charAt(i));
            keyIdx = GetChar(tableArray, key);

            // Add encoded char to the cipher text string
            secret += tableArray[(msgIdx + keyIdx) % 96]+"";
        }
        return secret;
    }

    // Decode the cipher text using a single character key to shift the table index.
    public String DecodeCaesar(char key, String secret)
    {
        char[] tableArray = new char[96];
        int decodeIdx = -1;
        String msg = "";
        int secretIdx = -1;
        int keyIdx = -1;

        // Build the table using the initialized array.
        BuildTable(tableArray);

        // Loop through the message and decode each char
        for(int i = 0;i < secret.length();i++)
        {
            secretIdx = GetChar(tableArray, secret.charAt(i));
            keyIdx = GetChar(tableArray, key);

            // Check if the secretIdx is less than the keyIdx to determine if the secretIdx needs to be bumped up
            // to account for the mod.
            decodeIdx = (secretIdx < keyIdx ? (secretIdx + 96) - keyIdx : secretIdx - keyIdx);

            // Add encoded char to the plain text string
            msg += tableArray[decodeIdx]+"";
        }
        return msg;
    }

    // The Vigenere cipher is similar to the Caesar cipher except for the fact that it works with 
    // multiple key (index) shifts to get an encoded char that may not be shifted the same amount
    // as the previous. 
    public String EncodeVigenere(String key, String msg)
    {
        char[] tableArray = new char[96];
        int msgIdx = -1;
        int keyIdx = -1;
        String secret = "";

        // Build the table using the initialized array.
        BuildTable(tableArray);

        // Get the index of the msg char
        for(int i = 0;i < msg.length();i++)
        {
            msgIdx = GetChar(tableArray, msg.charAt(i));
            keyIdx = GetChar(tableArray, key.charAt(i % key.length()));

            // Add encoded char to the cipher text string
            secret += tableArray[(msgIdx + keyIdx) % 96]+"";
        }

        return secret;
    }

    public String DecodeVigenere(String key, String secret)
    {
        char[] tableArray = new char[96];
        int decodeIdx = -1;
        int secretIdx = -1;
        int keyIdx = -1;
        String msg = "";

        // Build the table using the initialized array.
        BuildTable(tableArray);

        // Loop through the message and decode each char
        for(int i = 0;i < secret.length();i++)
        {
            secretIdx = GetChar(tableArray, secret.charAt(i));
            keyIdx = GetChar(tableArray, key.charAt(i % key.length()));

            // Check if the secretIdx is less than the keyIdx to determine if the secretIdx needs to be bumped up
            // to account for the mod.
            decodeIdx = (secretIdx < keyIdx ? (secretIdx + 96) - keyIdx : secretIdx - keyIdx);

            // Add encoded char to the plain text string
            msg += tableArray[decodeIdx]+"";
        }

        return msg;
    }
}

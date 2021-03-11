// This is a function I created as a test to manage Cookies on the Client's side

function CookieJar(_p1, _key, _value, _days)
{
    switch(_p1)
    {
        case('set'):
            // Get the current date and add the number of days to it
            var date = new Date();
            date.setTime(date.getTime() + (_days*24*60*60*1000));

            // Format the date
            var expires = 'expires='+ date.toUTCString();

            // Create the Cookie
            //Example: document.cookie = 'username=John; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
            document.cookie = _key+'='+_value+';'+expires+';path=/';
            break;
        case('get'):
            // Set the Cookie name for reference
            var cookieName = _key+"=";

            // Parse the Cookie to use the separate pieces
            var cookieParse = document.cookie.split(';');

            for(var i = 0; i < cookieParse.length; i++) 
            {
                var cookieString = cookieParse[i];

                // Check if the character at 0 is a black space--for whatever reason this may happen
                while(cookieString.charAt(0) == ' ') 
                {
                    cookieString = cookieString.substring(1);
                }

                // Return the value of the Cookie
                if(cookieString.indexOf(cookieName) == 0) 
                {
                    return cookieString.substring(cookieName.length, cookieString.length);
                }
            }

            // If the value can't be found for whatever reason, return an empty string
            return "";
            break;
        case('check'):
            // Grab the current Cookie with the specified key name
            var user = cookieJar('get', _key, '', '');

            // Check if the value is empty and display or request Username
            if(user != "") 
            {
                alert("Welcome again " + user);
            }else 
            {
                user = prompt("Please enter your name:","");
                if(user != "" && user != null) 
                {
                    cookieJar("username", user, 30);
                }
            }
            break;
        case('del'):
            // Delete specified Cookie
            document.cookie = _key+'=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
            break;
        default:
            break;
    }
}

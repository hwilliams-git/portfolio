// This is a premade function I use when I want to add dynamic data changes to a page.

// Send and recieve data from the server to the current page
function SendData(_method, _data, _id) 
{
    // Check the compatability of the browser
    if(window.XMLHttpRequest) 
    {
        // This code is for modern browsers
        xhttp = new XMLHttpRequest();
    }else{
        // This code is for old IE browsers
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    // Get the response from the server and use the data
    xhttp.onreadystatechange = function() 
    {
        if(this.readyState == 4 && this.status == 200) 
        {
            document.getElementById(_id).innerHTML = this.responseText;
        }
    };
    
    // Choose the way you want to send the data back to the server
    switch(_method)
    {
        case('get'):
            xhttp.open("GET", "index.php"+_data, true);
            xhttp.send();
            break;
        case('post'):
            xhttp.open("POST", "index.php", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(_data); 
            break;
    }
}

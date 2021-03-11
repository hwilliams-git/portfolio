<?php
// This is a script I created to test how I could manage a database through php

// Connect to the database
function ConnectDB()
{
    // Having the values preset will prevent the need for passing them in each time
    // But, be aware that it means having the username and password exposed here if
    // someone is somehow able to see this code--for whatever reason
    $servername = "";
    $username = "";
    $password = "";
    $database = "";
    $dbport = 3306;
    
    // Create connection
    $db = new mysqli($servername, $username, $password, $database, $dbport);
    
    // Check connection
    if($db -> connect_error) 
    {
        // For security reasons, it is best not to print out the error for code put into production
        //die("Connection failed: " . $db -> connect_error);
        echo("Connection failed.");
    }else
    {
        // Return the connection
        // For future reference, do not print out the database info in production
        echo("Connected successfully (".$db- > host_info.")</br></br>");
        return $db;
    }
}

// I use this to ensure a connection is made in other requests
function verifyConn()
{
    $dbCon = ConnectDB();
}

// Retrun a list of all the databases
function ListDB()
{
    $dbCon = ConnectDB();
    $result = mysqli_query($dbCon, "SHOW DATABASES");
    
    // Set the returned data into a string
    while($row = mysqli_fetch_array($result))
    { 
        $list .= $row[0]."</br>"; 
    }
    
    return $list;
}

// Return a list of all the tables in a database
function ListTB($db)
{
    $dbCon = ConnectDB();
    $result = mysqli_query($dbCon, "SHOW TABLES FROM ".$db);
    
    // Set the returned data into a string
    while($row = mysqli_fetch_array($result))
    { 
        $list .= $row[0]."</br>"; 
    }
    
    return $list;
}

// Create a table in a specified database
function CreateTB($table)
{
    $dbCon = ConnectDB();
    
    // Build query
    // For future reference, the parameters here will need to 
    // be dynamic since each table will need to be different
    $sql = "CREATE TABLE ".$table."(
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    email VARCHAR(50),
    reg_date TIMESTAMP)";
    
    // Check status of table creation
    if($dbCon -> query($sql) === TRUE) 
    {
        echo("Table ".$table." created successfully");
    }else 
    {
        // For future reference, do not print out the error in production
        echo("Error creating table: " . $dbCon -> error);
    }
}

// Insert data into a specific table
function InsertData($table, $p1, $p2, $p3)
{
    $dbCon = ConnectDB();
    
    // Build query
    $sql = "INSERT INTO ".$table."(firstname, lastname, email)
    VALUES('".$p1."', '".$p2."', '".$p3."')";
    
    if(mysqli_query($dbCon, $sql)) 
    {
        echo("New record created successfully");
    }else 
    {
        // For future reference, do not print out the error in production
        echo("Error: " . $sql . "</br>" . mysqli_error($dbCon));
    }
}

// Specific case to check for a user's email address
function CheckUser($table, $email)
{
    $dbCon = ConnectDB();
    
    // Build query
    $result = mysqli_query($dbCon, "SELECT email FROM ".$table." where email = '".$email."'");
    
    if(mysqli_num_rows($result) > 0) 
    {
        // Get the data in each row
        while($row = mysqli_fetch_assoc($result)) 
        {
            // Get the data in the row with this user email address
            //$list .=  "Username: ".$row["firstname"]." Password: ".$row["lastname"]." Email: ".$row["email"]."<br>";

            // Check if the user email address is already registered
            if($row["email"] == $email)
            {
                $list .= "Sorry, this email is already registered.";
            }
        }
    }else
    {
        $list .= "No users with email: ".$email." found.";
    }

    return $list;
}
?>

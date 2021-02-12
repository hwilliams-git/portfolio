import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MariaDBConn
{
    /**
    ** Don't forget to import the driver into the 
    ** Reference Libraries or none of this will work.
    **/
  
    // JDBC driver name and database URL
    String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    String DB_URL = "jdbc:mariadb://localhost:3306/"; // Need to look into how to set this up for other IPs.
    
    Connection conn = null;
    Statement stmt = null;
	
    // Connect to database
    // This function be called everytime a public function needs to access the 
    // the database.
    private Statement ConnectDB(String userName, String passWord, String dbName)
    {
        try
        {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            // Need to refresh myself on user privileges again.
            conn = DriverManager.getConnection(DB_URL+dbName, userName, passWord);

            // Create statement
            stmt = conn.createStatement();

            // Return statement to use in a query
            return stmt;
        }catch(Exception e)
        {
            // I think the function calling this one will handle 
            // catching the error. Otherwise it might be printed
            // twice. It's your choice to keep or remove it.
            e.printStackTrace();
        } 

        return stmt;
    }
}

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
	
    // Need to figure out how to get all the columns when using "*" since 
    // I need to specifically tell it which columns the result set should
    // be giving me
    
    public List<String[]> query(String dbName, String query, String[] columnArray)
    {
        List<String[]> rowList = null;
        String[] rowData = new String[columnArray.length];

        try
        {
            // Run connection and execute a query 
            ResultSet rs = ConnectDB(dbName).executeQuery(query);

            // Get results
            if(columnArray.length > 0)
            {
                rowList = new ArrayList<String[]>();
                int rowCount = 0;
		    
                while(rs.next()) 
                {
                    // Get data using column name
                    // This was my first test to get data from specific column, to see if it would work.
                    //rs.getString("Username");

                    // Get data using column number
                    // This was my first test to get data by column numbers, to see if it would work.
                    //String name = rs.getObject(1).toString();
                    //String gender = rs.getObject(2).toString();

                    // The columnArray is used to retrieve data from specific columns because I can't seem to find any reference 
                    // that MariaDB can do this. Everything I've found so far says to use column numbers and names. But, how is 
                    // one to inhearently know what the order of the columns are? What is irritaing about this is that although 
                    // one defines which columns that want in their query, they still have to specify the columns again to pull  
                    // them from the ResultSet. There may be a better way to do this, but I have not found it...yet.
                    for(int i = 0;i < columnArray.length;i++)
                    {
                        rowData[i] = rs.getString(columnArray[i]);
                    }
			
                    // Add columnArray data to list
                    // This setup allows one to access retrieved data by column because each one will be in its own array and its own index in the list.
                    rowList.add(rowCount, rowData);
                    rowData = new String[columnArray.length];
                    rowCount++;
                }
            }

            return rowList;
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally 
        {
            // Close resources
            try 
            {
                conn.close();
            }catch(Exception e) 
            {
                e.printStackTrace();
            }
        }

        return rowList;
    }
	
    /*
    ** 
    **Prepared statments will be added later.
    **
    */
}

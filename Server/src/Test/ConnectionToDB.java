//The ConnectionToDB class is used so that the edge server connects to the db called event_db, through jdbc driver

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDB extends Create_and_Insert_Methods {
    private String  Host;
    private String  db_name;

    public Connection con;
    private boolean connected;


    public ConnectionToDB(String Host,String db_name){
        this.Host = Host;
        this.db_name = db_name;
    }

    //Connecting to DB
    public  void ConnectToDB(String user, String password){
        try{
            System.out.println("Attempting to connect to Database " + this.Host + ":3306/" + db_name );
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.Host + ":3306/" + db_name,user,password);
            System.out.println("Connected to Database " + this.Host + ":3306/" + db_name );
            connected=true;
        } catch(Exception exc){
            System.out.println("Unable to connect to Database " + this.Host + ":3306/" + db_name  + exc);
            exc.printStackTrace();
        }
    }

    //Disconnecting from DB
    public  void DisconnectFromDB() {
        try{
            this.con.close();
            System.out.println("Disconnected from Database " + this.Host + ":3306/" + db_name);
            connected=false;
        }catch(Exception exc){
            System.out.println("Unable to disconnect from Database " +  this.Host + ":3306/" + db_name +exc);
            exc.printStackTrace();
        }
    }

    //this boolean is used in order to check if the server is connected to the db
    public boolean isConnected() {
        return connected;
    }


}

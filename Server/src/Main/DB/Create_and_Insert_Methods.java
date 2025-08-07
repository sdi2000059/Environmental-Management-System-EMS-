package DB;
//This class implements the create and insert methods, in order to create and insert an event to the db using mysql.
import MQTT.Data.received_IoT_data;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class Create_and_Insert_Methods {

    public boolean InsertEvent(received_IoT_data data, Connection con,String danger) {
        //In this function, the duplicate events are ignored and not registered in the db
        try {
            Date date = new Date();
            Timestamp timestamp =new Timestamp(date.getTime());
            PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO Events VALUES (?,?,?,?,?,?,?,?,?)");

            ps.setTimestamp(1,timestamp);
            ps.setString(2, data.id);
            ps.setDouble(3, data.x);
            ps.setDouble(4, data.y);
            ps.setFloat(5, data.smoke_value);
            ps.setFloat(6, data.gas_value);
            ps.setFloat(7, data.temp_value);
            ps.setFloat(8, data.uv_value);
            ps.setString(9, danger);

            int r = ps.executeUpdate(); //if the returning code is 1, then the row was inserted successfully, else (if the return code is 2) the row was not inserted

            if(r == 1) {
                return true;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return false;
    }
}

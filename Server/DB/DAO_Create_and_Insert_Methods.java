package DB;
//This class implements the create and insert methods, in order to create and insert an event to the db using mysql.
import java.sql.Connection;
import java.sql.SQLException;


public class Create_and_Insert_Methods {

    public Event CreateEvent(String IoT_device_id, double longitude, double latitude, String smoke_sensor, String gas_sensor, String temp_sensor, String uv_sensor, String danger_degree) {
        try {
            Event event = new Event(timestamp, IoT_device_id, longitude, latitude, smoke_sensor, gas_sensor, temp_sensor, uv_sensor, danger_degree);
            return event;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public boolean InsertEvent(Event event, Connection con) {
        //In this function, the duplicate events are ignored and not registered in the db
        try {
          

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

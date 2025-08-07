import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException, ApiException {

        ConnectionToDB db = new ConnectionToDB("127.0.0.1", "Events_db");

        db.ConnectToDB("root","firesolaris61093");

        received_IoT_data data = new received_IoT_data();

        data.id = "asdsadasdsa";
        data.x = 0.24F;
        data.y = 0.33F;
        data.smoke_value = 0.20F;


        String danger= "No Danger";
        db.InsertEvent(data, db.con, danger);
    }

}


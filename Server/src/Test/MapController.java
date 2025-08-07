import java.io.*;

import org.json.simple.JSONObject;


public class MapController {

    public void Json_Create(String lat, String lng,String Danger, String Device_id) {

//Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
//Inserting key-value pairs into the json object

        jsonObject.put("Latitude", lat);
        jsonObject.put("Longitude", lng);
        jsonObject.put("Danger Level", Danger);
        jsonObject.put("Device", Device_id);

        try {
            FileWriter file = new FileWriter("output.json");
            file.write(jsonObject.toJSONString());
            file.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: " + jsonObject);
    }

}

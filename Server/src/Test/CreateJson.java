import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
public class CreateJson{


    public void json_creation(int id,double x,double y,String type) {
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("ID", id);
        jsonObject.put("X", x);
        jsonObject.put("Y", y);
        jsonObject.put("Type", type);
        try {
            FileWriter file = new FileWriter("C:\\Users\\at_st\\IdeaProjects\\dit\\src\\Test\\Coordinates_Data\\Devices.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
    }

}

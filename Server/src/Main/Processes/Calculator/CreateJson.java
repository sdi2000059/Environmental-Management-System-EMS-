package Processes.Calculator;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateJson {


    public void json_creation(double Android_x,double Android_y,double IoT1_X,double IoT1_Y,double IoT2_X,double IoT2_Y,int IoT1_Danger,int IoT2_Danger,int IoT1_status, int IoT2_status) {
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Android_x", Android_x);
        jsonObject.put("Android_y", Android_y);
        jsonObject.put("IoT1_X", IoT1_X);
        jsonObject.put("IoT1_Y", IoT1_Y);
        jsonObject.put("IoT2_X", IoT2_X);
        jsonObject.put("IoT2_Y", IoT2_Y);
        jsonObject.put("IoT1_Danger",IoT1_Danger);
        jsonObject.put("IoT2_Danger",IoT2_Danger);
        jsonObject.put("IoT1_status",IoT1_status);
        jsonObject.put("IoT2_status",IoT2_status);
        try {
            FileWriter file = new FileWriter("C:\\Users\\at_st\\Desktop\\Project\\alonisioti-project-2023\\src\\Main\\Map\\Devices.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
    }

//    public void json_creation(float Android_x,float Android_y,float IoT_X,float IoT_Y,String IoT_Danger) {
//        //Creating a JSONObject object
//        JSONObject jsonObject = new JSONObject();
//        //Inserting key-value pairs into the json object
//        jsonObject.put("Android_x", Android_x);
//        jsonObject.put("Android_y", Android_y);
//        jsonObject.put("IoT_X", IoT_X);
//        jsonObject.put("IoT_Y", IoT_Y);
//        jsonObject.put("IoT_Danger",IoT_Danger);
//
//        try {
//            FileWriter file = new FileWriter("C:\\Users\\user\\Desktop\\alonisioti-project-2023\\src\\Main\\Map\\Devices.json");
//            file.write(jsonObject.toJSONString());
//            file.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        System.out.println("JSON file created: "+jsonObject);
//    }

}

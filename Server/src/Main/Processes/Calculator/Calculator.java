package Processes.Calculator;

import MQTT.Data.dataToSend;
import MQTT.Data.received_IoT_data;
import MQTT.Data.received_android_data;

public class Calculator {


    public dataToSend DangerFromTwoDevices(received_android_data androidData, received_IoT_data IoT1_Data, received_IoT_data IoT2_Data, Integer f){

        dataToSend data = new dataToSend();
        data.Android_id = androidData.id;
        DangerDegreeCalc d_sensor = new DangerDegreeCalc();
        Distance_calc dis_cal = new Distance_calc();
        boolean IoT1;
        boolean IoT2;
        double IoT_distance_y;
        double IoT_distance_x;

        if (IoT1_Data.temp_flag && IoT1_Data.uv_flag) {
            data.IoT1_Danger = d_sensor.Danger_sensor(IoT1_Data.smoke_value, IoT1_Data.gas_value, IoT1_Data.temp_value, IoT1_Data.uv_value);
        }
        else{

            data.IoT1_Danger = d_sensor.Danger_sensor(IoT1_Data.smoke_value, IoT1_Data.gas_value);
        }

        if (IoT2_Data.temp_flag && IoT2_Data.uv_flag) {
            data.IoT2_Danger = d_sensor.Danger_sensor(IoT2_Data.smoke_value, IoT2_Data.gas_value, IoT2_Data.temp_value, IoT2_Data.uv_value);
        }
        else{

            data.IoT2_Danger = d_sensor.Danger_sensor(IoT2_Data.smoke_value, IoT2_Data.gas_value);
        }

        if (data.IoT1_Danger.equals("Alert: High Danger Degree") | data.IoT2_Danger.equals("Alert: High Danger Degree") ){

            data.Alert = "Alert: High Danger Degree";
        }
        else if (data.IoT1_Danger.equals("Alert: Medium Danger Degree") | data.IoT2_Danger.equals("Alert: Medium Danger Degree")){

            data.Alert = "Alert: Medium Danger Degree";
        }

        IoT1 = d_sensor.danger();
        IoT2 = d_sensor.danger();
        if (IoT1 && IoT2){

            IoT_distance_x = dis_cal.CenterOfDistanceLatitude(IoT1_Data.x, IoT2_Data.x);
            IoT_distance_y = dis_cal.CenterOfDistanceLongitude(IoT1_Data.y, IoT2_Data.y);

            data.distance = dis_cal.DistanceBetweenTwoPoints(androidData.x, androidData.y, IoT_distance_x, IoT_distance_y);
        }
        else if (IoT1){

            data.distance = dis_cal.DistanceBetweenTwoPoints(androidData.x, androidData.y, IoT1_Data.x, IoT1_Data.y);
        }
        else if (IoT2){

            data.distance = dis_cal.DistanceBetweenTwoPoints(androidData.x, androidData.y, IoT2_Data.x, IoT2_Data.y);
        }

        return data;
    }

    public dataToSend DangerFromOneDevice(received_android_data androidData, received_IoT_data IoT_Data, Integer f){

        dataToSend data = new dataToSend();
        data.Android_id = androidData.id;
        DangerDegreeCalc d_sensor = new DangerDegreeCalc();
        Distance_calc dis_cal = new Distance_calc();
        boolean IoT;

        if (IoT_Data.temp_flag && IoT_Data.uv_flag) {
            data.Alert = d_sensor.Danger_sensor(IoT_Data.smoke_value, IoT_Data.gas_value, IoT_Data.temp_value, IoT_Data.uv_value);
        }
        else{

            data.Alert = d_sensor.Danger_sensor(IoT_Data.smoke_value, IoT_Data.gas_value);
        }

        IoT = d_sensor.danger();
        if (IoT){

            data.distance = dis_cal.DistanceBetweenTwoPoints(androidData.x, androidData.y, IoT_Data.x, IoT_Data.y);
        }

        return data;
    }

}

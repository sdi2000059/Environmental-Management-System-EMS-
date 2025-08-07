package Processes.Calculator;
public class DangerDegreeCalc {

    public boolean danger = false;
    public String Danger_sensor(float smoke_sensor, float gas_sensor, int temp_sensor, int uv_sensor) {

        String danger_degree;
        if ((smoke_sensor > 0.14) && (gas_sensor > 9.15) && (temp_sensor > 50) && (uv_sensor > 6)) {
            danger_degree = "Alert: High Danger Degree";
            danger = true;
            return danger_degree;
        }
        else if ((smoke_sensor < 0.14) && (gas_sensor < 9.15) && (temp_sensor > 50) && (uv_sensor > 6)) {
            danger_degree = "Alert: Medium Danger Degree";
            danger = true;
            return danger_degree;
        }
        else if ((smoke_sensor > 0.14) && (gas_sensor > 9.15)) {
            danger_degree = "Alert: High Danger Degree";
            danger = true;
            return danger_degree;
        }
        else if (gas_sensor > 9.15) {
            danger_degree = "Alert: High Danger Degree";
            danger = true;
            return danger_degree;
        }
        else{
            danger_degree = "No Danger";
            danger = false;
            return danger_degree;
        }

        //if none of the above is true, an event did not occur, so we return No Danger

    }
    public String Danger_sensor(float smoke_sensor, float gas_sensor) {

        String danger_degree;

        if ((smoke_sensor > 0.14) && (gas_sensor > 9.15)) {
            danger_degree = "Alert: High Danger Degree";
            danger = true;
            return danger_degree;
        }
        else if (gas_sensor > 9.15) {
            danger_degree = "Alert: High Danger Degree";
            danger = true;
            return danger_degree;
        }
        else{
            danger_degree = "No Danger";
            danger = false;
            return danger_degree;
        }


        //if none of the above is true, an event did not occur, so we return No Danger

    }
    public boolean danger(){

        return danger;
    }
}

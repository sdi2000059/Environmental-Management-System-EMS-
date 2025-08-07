import java.util.Random;

public class IoT_Device {
    public int value;
    public float value2;
    public String type;
    public String battery_level = "35%";
    public double x = 23.774381;
    public double y = 37.968353;
    public int Thermal_max_value = 86;
    public float smoke_max_value = 0.25F;
    public float gas_max_value = 11F;
    public int UV_max_value = 12;

    public float Generate_Sm_value(){


        Random rand = new Random();

        value2 = rand.nextFloat()*smoke_max_value;

        return value2;

    }
    public float Generate_Gas_value(){

        Random rand = new Random();

        value2 = rand.nextFloat()*gas_max_value;

        return value2;

    }
    public int Generate_UV_value(){

        Random rand = new Random();

        value = rand.nextInt(UV_max_value);


        return value;

    }

    public int Generate_Thermal_value(){

        Random rand = new Random();

        value = rand.nextInt(Thermal_max_value)-5;


        return value;

    }
}
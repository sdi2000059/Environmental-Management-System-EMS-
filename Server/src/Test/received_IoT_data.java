import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class received_IoT_data {

    public String id;
    public float x;
    public float y;
    public float battery;
    public float gas_value;
    public boolean gas_flag = false;
    public int uv_value;
    public boolean uv_flag = false;
    public float smoke_value;
    public boolean smoke_flag = false;
    public int temp_value;
    public boolean temp_flag = false;


    public static received_IoT_data getData(String message) {

        received_IoT_data data = new received_IoT_data();

        if (message.contains("Battery:")){

            String[] SeperatebyID = message.split("Battery:");
            String[] SeperatebyDel = SeperatebyID[1].split("/");
            String Battery = SeperatebyDel[0];
            data.battery = parseFloat(Battery);

        }
        if (message.contains("ID:")){

            String[] Seperate = message.split("ID:");
            String[] SeperatebyDel = Seperate[1].split("/");
            data.id = SeperatebyDel[0];
        }
        if (message.contains("x:")){

            String[] Seperate = message.split("x:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String X = SeperatebyDel[0];
            data.x = parseFloat(X);
        }
        if (message.contains("y:")){

            String[] Seperate = message.split("y:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String Y = SeperatebyDel[0];
            data.y = parseFloat(Y);
        }
        if (message.contains("Gas Value:")){

            String[] Seperate = message.split("Gas Value:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String Gas_Value = SeperatebyDel[0];
            data.gas_value = parseFloat(Gas_Value);
            data.gas_flag = true;
        }
        if (message.contains("Smoke Value:")){

            String[] Seperate = message.split("Smoke Value:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String Smoke_Value = SeperatebyDel[0];
            data.smoke_value = parseFloat(Smoke_Value);
            data.smoke_flag = true;
        }
        if (message.contains("Temp Value:")){

            String[] Seperate = message.split("Temp Value:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String Temp_value = SeperatebyDel[0];
            data.temp_value = parseInt(Temp_value);
            data.temp_flag = true;
        }
        if (message.contains("UV Value:")){

            String[] Seperate = message.split("UV Value:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String UV_Value = SeperatebyDel[0];
            data.uv_value = parseInt(UV_Value);
            data.uv_flag = true;
        }

        return data;

    }
}

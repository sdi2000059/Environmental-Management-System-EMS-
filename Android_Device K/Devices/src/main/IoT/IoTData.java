package main.IoT;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IoTData")
public class IoTData {

    String id ;
    double temperature ;
    double gasLevel ;
    float smokelevel;
    float UVlevel;
    String x ;
    String y ;

    //Setters

//    public void setIoTData(String id, double temperature, double gasLevel, float smokelevel, float UVlevel, String x, String y){
//        this.id = id;
//        this.temperature = temperature;
//        this.gasLevel = gasLevel;
//        this.smokelevel = smokelevel;
//        this.UVlevel = UVlevel;
//        this.x = x;
//        this.y = y;
//    }

    public void set_id(String id) {
        this.id = id;
    }
    public void set_temperature(double temperature){
        this.temperature = temperature;
    }
    public void set_gasLevel(double gasLevel){
        this.gasLevel = gasLevel;
    }
    public void set_smokelevel(float smokelevel){
        this.smokelevel = smokelevel;
    }
    public void set_UVlevel(float UVlevel){
        this.UVlevel = UVlevel;
    }
    public void set_x(String  x){
        this.x = x;
    }
    public void set_y(String y){
        this.y = y;
    }


    @XmlElement
    public String get_id(){
        return id;
    }
    @XmlElement
    public double get_temperature(){
        return temperature;
    }
    @XmlElement
    public double get_gasLevel(){
        return gasLevel;
    }
    @XmlElement
    public float get_smokelevel(){
        return smokelevel;
    }
    @XmlElement
    public float get_UVlevel(){
        return UVlevel;
    }
    @XmlElement
    public String get_x(){
        return x;
    }
    @XmlElement
    public String get_y(){
        return y;
    }

}

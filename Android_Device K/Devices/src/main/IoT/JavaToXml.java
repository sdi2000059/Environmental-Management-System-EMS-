package main.IoT;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
//import javax.xml.bind.*;
import main.sensors.SensorValuesGenerator;

public class JavaToXml {
    public void ConvertionJavatoXml(String xmlfile){
        try{
            IoTData iotdata = new IoTData();

            XmlToJava Xmlconvertion = new XmlToJava();
            Xmlconvertion.FileRead(xmlfile);

            iotdata.set_id(Xmlconvertion.returnID());
            iotdata.set_x(Xmlconvertion.returnX());
            iotdata.set_y(Xmlconvertion.returnY());
//            iotdata.set_x(SensorValuesGenerator.getRandomLatitude());
//            iotdata.set_y(SensorValuesGenerator.getRandomLongitude());
            iotdata.set_temperature(SensorValuesGenerator.generateRandomTemperature());
            iotdata.set_gasLevel(SensorValuesGenerator.generateRandomGasLevel());
            iotdata.set_smokelevel(SensorValuesGenerator.generateRandomSmokeLevel());
            iotdata.set_UVlevel(SensorValuesGenerator.generateRandomUVLevel());


            JAXBContext jaxbContext = JAXBContext.newInstance(IoTData.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = new File("src\\main\\resources\\IoTData.xml");

            marshaller.marshal(iotdata, file);

        } catch (JAXBException e){
            e.printStackTrace();
        }
    }
}

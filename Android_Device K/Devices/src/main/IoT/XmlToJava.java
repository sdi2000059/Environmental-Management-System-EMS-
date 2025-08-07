package main.IoT;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlToJava {
    public String x;
    public String y;
    public String id;
    public void FileRead(String xmlfile){
        try {
            //Creating a constructor of file class and parsing an XML file change name depending of the sent
            File file = new File("C:\\Users\\kiria\\Java\\alonisioti-project-2023\\Devices\\src\\main\\resources\\" +xmlfile);
            //An instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //An instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("vehicle");
            //NodeList is not iterable, so we are using for loop
            //System.out.println("Vehicle id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                //System.out.println("\nNode Name :" + node.getNodeName());
                System.out.println("\nNode Name :" + "IoTDevice01");
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    System.out.println("Vehicle id: " + eElement.getAttribute("id"));
                    System.out.println("longitude: " + eElement.getAttribute("x"));
                    System.out.println("latitude: " + eElement.getAttribute("y"));
                    this.x = eElement.getAttribute("x");
                    this.y = eElement.getAttribute("y");
                    this.id = eElement.getAttribute("id");
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String returnX(){
        return this.x;
    }
    public String returnY(){
        return this.y;
    }

    public String returnID(){
        return this.id;
    }

}

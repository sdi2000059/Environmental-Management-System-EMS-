package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLRead {

    public void FileRead(){
        try {
//creating a constructor of file class and parsing an XML file change name depending of the sent
            File file = new File("C:\\Users\\at_st\\IdeaProjects\\dit\\src\\Test\\android_1.xml");
//an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("vehicle");
//// nodeList is not iterable, so we are using for loop
//            System.out.println("Vehicle id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    System.out.println("Vehicle id: " + eElement.getAttribute("id"));
                    System.out.println("longitude: " + eElement.getAttribute("x"));
                    System.out.println("latitude: " + eElement.getAttribute("y"));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

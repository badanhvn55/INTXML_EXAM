/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellformxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author badan
 */
public class DOMLocateError {

    public static void validationXML(File fileXML, File fileValidate) {
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = parser.parse(fileXML);

            Schema schema = SchemaFactory.
                    newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).
                    newSchema(new StreamSource(fileValidate));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc));
            System.out.println("xml file is valid");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.print("Enter File name: ");
            String xmlFile = "src/wellformxml/" + bf.readLine();
            File file = new File(xmlFile);
            if (file.exists()) {
                try {
                    // Create a new factory to create parsers 
                    DocumentBuilderFactory dBF
                            = DocumentBuilderFactory.newInstance();
                    // Use the factory to create a parser (builder) and use
                    // it to parse the document.
                    DocumentBuilder builder = dBF.newDocumentBuilder();
                    // builder.setErrorHandler(new MyErrorHandler());
                    InputSource is = new InputSource(xmlFile);
                    Document doc = builder.parse(is);
                    System.out.println(xmlFile + " is well-formed!");
                    
                    validationXML(file, new File("src/wellformxml/Employee-Detail.xsd"));
                } catch (Exception e) {
                    System.out.println(xmlFile + " isn't well-formed!");
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            } else {
                System.out.print("File not found!");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

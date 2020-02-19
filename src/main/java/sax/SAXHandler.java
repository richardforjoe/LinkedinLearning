package sax;

import dataprovider.model.Customer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import dataprovider.dataprovider.DataProvider;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SAXHandler extends DefaultHandler{

    //Streaming processor
    // SAX Simple API for XML
    //read-only
    //event based streaming parser
    //startDocument, endDocument, startElement, endElement, characters
    //Error handling - warning, error , fatalError

    //notational, ignorableWhitespace,
    //Creating a handler class - Ectend the DefaultHandler class
    //import org.xml.sa
    // x.helpers.DefaultHandler
    //public class SAXHandler extends DefaultHandler

    private List<Customer> data;
    private Customer customer;
    private String currentElement = "";
    private StringBuilder currentText;
    private Boolean IsANameSpace = true;


    private static final String XMLDATEFORMAT = "yyy-MM-dd'T'HH:mm:ss";

    public List<Customer> readDataFromXML(String filename) throws ParserConfigurationException, IOException {

        //Code that will parse document
        //Public method, readDatafromXML which receives the filename
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //To enable awareness of namespaces - needs to be before the parser object
            factory.setNamespaceAware(IsANameSpace);
            SAXParser parser = factory.newSAXParser();

            parser.parse(new File(filename), this);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start document");
        data = new ArrayList<>();

    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        System.out.println("Start element: " + qName);
        if (localName != "") {
            currentElement = localName;
        } else {
            currentElement = qName;
        }

        switch (currentElement) {
            case "customers":

                break;
            case "customer":
                customer = new Customer();
                String idAsString = attributes.getValue(Customer.ID);
                customer.setId(Integer.parseInt(idAsString));
                data.add(customer);
                break;

            default:
                currentText = new StringBuilder();

                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        System.out.println("End element: " + qName);

        if (currentElement.equals("customers") || currentElement.equals("customer")) {
            return;
        }
        // all the string based properties
        String content = currentText.toString();

        switch (currentElement) {
            case Customer.NAME:
                customer.setName(content);
                break;
            case Customer.PHONE:
                customer.setPhone(content);
                break;
            case Customer.ABOUT:
                customer.setAbout(content);
                break;
            case Customer.AGE:
                customer.setAge(Integer.parseInt(content));
                break;
            case Customer.ACTIVE:
                customer.setActive(Boolean.parseBoolean(content));
                break;
            case Customer.BALANCE:
                customer.setBalance(new BigDecimal(content));
                break;
            case Customer.JOINED:
                DateFormat df = new SimpleDateFormat(XMLDATEFORMAT);
                try {
                    customer.setJoined(df.parse(content));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        System.out.println("Characters");
        if (currentText != null) {
            currentText.append(ch, start, length);
        }


    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Warning!!");
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.out.println("Error");
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("fataError!!!");
    }
}

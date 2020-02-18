package sax;

import dataprovider.dataprovider.DataProvider;
import dataprovider.model.Customer;

import java.util.List;

public class ReadXMLWithSAX {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        String filename = DataProvider.DATADIR + "customers.xml";

        SAXHandler saxCustomerHandler = new SAXHandler();
        saxCustomerHandler.readDataFromXML(filename);

        List<Customer> data = saxCustomerHandler.readDataFromXML(filename);
        System.out.println("Number of customers: " + ((List) data).size());

        for (Customer customer : data) {
            System.out.println("Customer " + customer);
        }
    }
}

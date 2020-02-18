package XMLStrings;

import dataprovider.dataprovider.DataProvider;

import java.io.File;
import java.io.FileReader;

public class Main {


    public static void main(String[] args) throws Exception {
        //TODO
       String filename = DataProvider.DATADIR + "customers.xml";

       StringBuilder builder = new StringBuilder();
        FileReader reader = new FileReader(new File(filename));

        int content;
        while ((content = reader.read()) != -1) {
    builder.append((char)content);

        }
reader.close();
        System.out.println(builder.toString());
    }
}

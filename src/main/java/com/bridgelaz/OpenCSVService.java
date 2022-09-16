package com.bridgelaz;

import com.opencsv.CSVReader;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class OpenCSVService {
    private static final String STRING_ARRAY_SAMPLE = "addressBook.csv";

    /**
     * crete a method nme as write to Csv
     * Writing to CSV
     * @param addressBookSystem
     * @throws IOException
     */
    public void writeToCsv(Map<String, Set<ContactPerson>> addressBookSystem) throws IOException {
        List<ContactPerson> contactList = new ArrayList<ContactPerson>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> contactDeatilsList = me.getValue().stream().collect(Collectors.toList());
            contactList.addAll(contactDeatilsList);
        }
        try (Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));) {
            StatefulBeanToCsv<ContactPerson> beanToCsv = new StatefulBeanToCsvBuilder<ContactPerson>(writer).build();
            beanToCsv.write(contactList);
        } catch (CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
        } catch (CsvDataTypeMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creating a method name as readCsv
     *  Reading data from CSV
     * @throws Exception
     */
    public void readCsv() throws Exception {
        try (Reader reader = Files.newBufferedReader(Paths.get(STRING_ARRAY_SAMPLE));
             CSVReader csvReader = new CSVReader(reader);) {
            /**
             * Reading Records One by One in a String array
             */
            String[] nextRecord = csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("First Name : " + nextRecord[3]);
                System.out.println("Last Name : " + nextRecord[4]);
                System.out.println("Address: " + nextRecord[0]);
                System.out.println("City : " + nextRecord[1]);
                System.out.println("State : " + nextRecord[6]);
                System.out.println("Zip : " + nextRecord[7]);
                System.out.println("Number : " + nextRecord[5]);
                System.out.println("Email : " + nextRecord[2]);
                System.out.println("*****************");
            }
        }
    }
}

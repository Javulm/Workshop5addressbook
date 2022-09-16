package com.bridgelaz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
public class JSONService {
    private static final String STRING_ARRAY_SAMPLE = "addressBook.json";

    /**
     * crete a parameterized method name as writeJson
     * Method for write JSON file
     * @param addressBookSystem all persons data stored
     * @throws Exception
     */
    public void writeJson(Map<String, Set<ContactPerson>> addressBookSystem) throws Exception {
        List<ContactPerson> contactList = new ArrayList<ContactPerson>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            /**
             * create a list the getValue is value corresponding to this entry and collect data to list
             */
            List<ContactPerson> contactDetailsList = me.getValue().stream().collect(Collectors.toList());
            /**
             * calling add all method from contactList object
             */
            contactList.addAll(contactDetailsList);
        }
        Gson gson = new GsonBuilder().create();
        List list = contactList.stream().collect(Collectors.toList());
        String json = gson.toJson(list);
        /**
         * create object for  FileWriter class ,object name is writer
         */
        FileWriter writer = new FileWriter(STRING_ARRAY_SAMPLE);
        /**
         * calling write method from writer object
         */
        writer.write(json);
        /**
         * calling close method from writer object
         */
        writer.close();
        System.out.println("123");
    }

    /**
     * create a method name as readJson
     * Method for Read JSON file
     * @return count
     */
    public int readJson() {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(STRING_ARRAY_SAMPLE));
            /**
             * create a list and object ,object name is addressBook
             */
            List<ContactPerson> addresBook = new Gson().fromJson(reader, new TypeToken<List<ContactPerson>>() {
            }.getType());
            /**
             * Iterators allow the caller to remove elements from the
             * underlying collection during the iteration with well-defined semantics.
             */
            Iterator<ContactPerson> jsonIterator = addresBook.iterator();
            /**
             * calling hasNext method from jsonIterator object
             */
            while (jsonIterator.hasNext()) {
                count++;
                ContactPerson adressBook = jsonIterator.next();
                System.out.println("First Name : " + adressBook.getFirstName());
                System.out.println("Last Name : " + adressBook.getLastName());
                System.out.println("Address: " + adressBook.getAddress());
                System.out.println("City : " + adressBook.getCity());
                System.out.println("State : " + adressBook.getState());
                System.out.println("Zip : " + adressBook.getZip());
                System.out.println("Number : " + adressBook.getPhoneNo());
                System.out.println("Email : " + adressBook.getEmailId());
                System.out.println("*****************");
            }
            /**
             * calling close method from reader object
             */
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }
}

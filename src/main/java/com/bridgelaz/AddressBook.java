package com.bridgelaz;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddressBook {
    private Set<ContactPerson> addressBook;

    private static Map<String, Set<ContactPerson>> addressBookSystem = new TreeMap<>();

    public Map<String, Set<ContactPerson>> getAddressBookSystem() {
        return addressBookSystem;
    }

    public Set<ContactPerson> getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(Set<ContactPerson> addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * create a method name as addContactPerson
     * @param contactPerson in address book
     */
    public void addContactPerson(ContactPerson contactPerson) {
        /**
         * calling add method from addressbook object
         * add= true if this set did not already contain the specified element
         */
        addressBook.add(contactPerson);
    }

    /**
     * create a parameterized method name as addAddressBookToSystem
     * @param addressBookName
     * @param addressBook
     */
    public void addAddressBookToSystem(String addressBookName, Set<ContactPerson> addressBook) {
        /**
         * calling put method from  addressBookSystem object
         * put = the previous value associated with key, or null if there was no mapping for key
         */
        addressBookSystem.put(addressBookName, addressBook);
    }

    /**
     * crrete a method name as isPresentAddressBook, this is parameterized and boolean type method
     * Checking whether address book is present or not in system
     * @param addressBookName
     * @return is present then true return
     */
    public boolean isPresentAddressBook(String addressBookName) {
        Predicate<String> isPresent = k -> k.equals(addressBookName);
        /**
         * create list, address book name present or not in address book system, searching for filter if present
         * in addressbookSystem then collect to list and all collect in nameList
         */
        List<String> nameList = addressBookSystem.keySet().stream().filter(isPresent).collect(Collectors.toList());
        if (nameList.size() == 0)
        /**
         * if not present in address book system then showing result false
         */
            return false;
        /**
         * if present in address book system then return true
         */
        return true;
    }

    /**
     * create a method name as editContactPersonDetails this is parameterized method and boolean type
     * In this method Editing the contact person details in the address book
     * @param addressBookName in address book system
     * @param personName in address book
     * @param input user input
     * @return true
     */
    public boolean editContactPersonDetails(String addressBookName, String personName, Scanner input) {
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            String adBookName = me.getKey();
            Set<ContactPerson> addressBook = me.getValue();
            if (adBookName.equals(addressBookName)) {
                for (ContactPerson contactPerson : addressBook) {
                    /**
                     * fistname + lastname
                     * ex-Nilofar Mujawar
                     */
                    String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
                    /**
                     * if name is matched personName
                     */
                    if (name.equals(personName)) {
                        /**
                         * calling remove method from addressbook object
                         * remove into contactperson
                         */
                        addressBook.remove(contactPerson);
                        ContactPerson contactPerson1 = addContactPersonDetails(input);
                        /**
                         * calling method add
                         */
                        addressBook.add(contactPerson1);
                        addAddressBookToSystem(addressBookName, addressBook);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * create parameterized method name as deleteContactPersonDetails,this is boolean type method
     * Deleting the contact person details from the address book
     * @param addressBookName in address book system
     * @param personName in adress book
     * @return true
     */
    public boolean deleteContactPersonDetails(String addressBookName, String personName) {
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            String adBookName = me.getKey();
            Set<ContactPerson> addressBook = me.getValue();
            if (adBookName.equals(addressBookName)) {
                for (ContactPerson contactPerson : addressBook) {
                    String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
                    if (name.equals(personName)) {
                        addressBook.remove(contactPerson);
                        addAddressBookToSystem(addressBookName, addressBook);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * create a method name as searchPersonByCityOrState
     * Listing the persons based on the city and state respectively
     * @param cityOrState of person in address book
     * @return person name searched by city or state
     */
    public List<String> searchPersonByCityOrState(String cityOrState) {
        List<String> personsInCityOrState = new ArrayList<String>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<String> personsList = me.getValue().stream()
                    .filter(contactPerson -> contactPerson.getCity().equals(cityOrState)
                            || contactPerson.getState().equals(cityOrState))
                    .map(contactPerson -> contactPerson.getFirstName() + " " + contactPerson.getLastName())
                    .collect(Collectors.toList());
            personsInCityOrState.addAll(personsList);
        }
        return personsInCityOrState;
    }

    /**
     * create a method name as viewPersonByCityOrState,this is parameterized method
     * Mapping the persons by city or state
     * @param cityOrState view person by city or state in address book
     * @return person city state map
     */
    public Map<String, List<String>> viewPersonByCityOrState(String cityOrState) {
        Map<String, List<String>> personCityStateMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<String> personsInCityOrState = me.getValue().stream()
                    .filter(contactPerson -> contactPerson.getCity().equals(cityOrState)
                            || contactPerson.getState().equals(cityOrState))
                    .map(contactPerson -> contactPerson.getFirstName() + " " + contactPerson.getLastName())
                    .collect(Collectors.toList());
            personCityStateMap.put(me.getKey(), personsInCityOrState);
        }
        return personCityStateMap;
    }

    /**
     * create a method name as countPersonByCityOrState, this is parameterized method
     * In this method Counting the persons by city and state respectively
     * @param cityOrState of person in address book
     */
    public void countPersonByCityorState(String cityOrState) {
        Map<String, List<String>> personCityStateMap = viewPersonByCityOrState(cityOrState);
        personCityStateMap.entrySet().stream().forEach(me -> {
            System.out.println("The no. of persons reside in the " + cityOrState + " is " + me.getValue().size()
                    + ", as given in the " + me.getKey() + ".");
        });
        int count = personCityStateMap.entrySet().stream().map(me -> me.getValue().size()).reduce(0, (a, b) -> a + b);
        System.out.println("There are total " + count + " persons in the " + cityOrState + ".");
    }

    /**
     * create a method name as showAddressBook,this is parameterized method
     * Printing address book by its name
     * @param addressBookName in address book system showing in this method
     */
    public void showAddressBook(String addressBookName) {
        addressBookSystem.entrySet().stream().forEach(me -> {
            if (me.getKey().equals(addressBookName)) {
                if (me.getValue().size() == 0)
                    System.out.println("Sorry, there is no contact details in the " + addressBookName + ".");
                else {
                    System.out.println("The contact details of the " + addressBookName + ":");
                    me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
                }
            }
        });
    }

    /**
     * create a method name as showAddressBookSystem
     *  in this method Printing all the address books
     */
    public void showAddressBookSystem() {
        sortedByName();
        if (addressBookSystem.size() == 0)
            System.out.println("There is no address book in the system.");
        else {
            addressBookSystem.entrySet().stream().forEach(me -> {
                System.out.println("The contact details of the " + me.getKey() + ":");
                if (me.getValue().size() == 0)
                    System.out.println("Sorry, there is no contact in the " + me.getKey() + ".");
                else
                    me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
            });
        }
    }

    /**
     * create a method name as sortedByName
     * In this method Sorting the address book by name
     */
    public void sortedByName() {
        Map<String, Set<ContactPerson>> personNameSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            Set<ContactPerson> personsInCity = me.getValue().stream().sorted(Comparator.comparing(ContactPerson::getFirstName))
                    .collect(Collectors.toSet());
            personNameSortedMap.put(me.getKey(), personsInCity);
        }
        addressBookSystem = personNameSortedMap;
    }

    /**
     * create a method name as viewSortedByCity
     * in this method Sorting the address book by city
     * @return person data view sorted y city
     */
    public Map<String, List<ContactPerson>> viewSortedByCity() {
        Map<String, List<ContactPerson>> personCitySortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInCity = me.getValue().stream()
                    .sorted(Comparator.comparing(ContactPerson::getCity)).collect(Collectors.toList());
            personCitySortedMap.put(me.getKey(), personsInCity);
        }
        return personCitySortedMap;
    }

    /**
     * create a method name as viewSortedByState
     * In this method Sorting the address book by state
     * @return person view sorted by state
     */
    public Map<String, List<ContactPerson>> viewSortedByState() {
        Map<String, List<ContactPerson>> personStateSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInState = me.getValue().stream()
                    .sorted(Comparator.comparing(ContactPerson::getState)).collect(Collectors.toList());
            personStateSortedMap.put(me.getKey(), personsInState);
        }
        return personStateSortedMap;
    }

    /**
     * create a method name as viewSortedByZip
     * In this method Sorting the address book by zip
     * @return person zip sorted map
     */
    public Map<String, List<ContactPerson>> viewSortedByZip() {
        Map<String, List<ContactPerson>> personZipSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInZip = me.getValue().stream().sorted(Comparator.comparingInt(ContactPerson::getZip))
                    .collect(Collectors.toList());
            personZipSortedMap.put(me.getKey(), personsInZip);
        }
        return personZipSortedMap;
    }

    /**
     * creating method name as printSortedMap ,this is parameterized method
     * Printing the sorted map
     * @param sortedMap in address book
     */
    public void printSortedMap(Map<String, List<ContactPerson>> sortedMap) {
        if (sortedMap.size() == 0)
            System.out.println("There is no address book in the system.");
        else {
            sortedMap.entrySet().stream().forEach(me -> {
                System.out.println("The contact details of the " + me.getKey() + ":");
                if (me.getValue().size() == 0)
                    System.out.println("Sorry, there is no contact in the " + me.getKey() + ".");
                else
                    me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
            });
        }
    }

    /**
     * creating method name as addContactPersonDetails
     * Initialising the contact person object
     * @param input scanner i/p
     * @return persons details
     */
    public ContactPerson addContactPersonDetails(Scanner input) {
        System.out.println("Enter the first name:");
        String firstName = input.next();
        System.out.println("Enter the last name:");
        String lastName = input.next();
        System.out.println("Enter the address:");
        input.nextLine();
        String address = input.nextLine();
        System.out.println("Enter the city:");
        String city = input.nextLine();
        System.out.println("Enter the state:");
        String state = input.nextLine();
        System.out.println("Enter the zip:");
        int zip = input.nextInt();
        System.out.println("Enter the phoneNo:");
        long phoneNo = input.nextLong();
        System.out.println("Enter the email:");
        String emailId = input.next();
        /**
         * create object name as personDetails store all the details in it
         */
        ContactPerson personDetails = new ContactPerson(firstName, lastName, address, city, state, zip, phoneNo,
                emailId);
        return personDetails;
    }
}

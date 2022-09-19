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
    public void addContactPerson(ContactPerson contactPerson) {
        addressBook.add(contactPerson);
    }

    public void addAddressBookToSystem(String addressBookName, Set<ContactPerson> addressBook) {
        addressBookSystem.put(addressBookName, addressBook);
    }

    public boolean isPresentAddressBook(String addressBookName) {
        Predicate<String> isPresent = k -> k.equals(addressBookName);

        List<String> nameList = addressBookSystem.keySet().stream().filter(isPresent).collect(Collectors.toList());
        if (nameList.size() == 0)
            return false;
        return true;
    }
    public boolean editContactPersonDetails(String addressBookName, String personName, Scanner input) {
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            String adBookName = me.getKey();
            Set<ContactPerson> addressBook = me.getValue();
            if (adBookName.equals(addressBookName)) {
                for (ContactPerson contactPerson : addressBook) {
                    String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
                    if (name.equals(personName)) {
                        addressBook.remove(contactPerson);
                        ContactPerson contactPerson1 = addContactPersonDetails(input);
                        addressBook.add(contactPerson1);
                        addAddressBookToSystem(addressBookName, addressBook);
                        return true;
                    }
                }
            }
        }
        return false;
    }

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

    public void countPersonByCityorState(String cityOrState) {
        Map<String, List<String>> personCityStateMap = viewPersonByCityOrState(cityOrState);
        personCityStateMap.entrySet().stream().forEach(me -> {
            System.out.println("The no. of persons reside in the " + cityOrState + " is " + me.getValue().size()
                    + ", as given in the " + me.getKey() + ".");
        });
        int count = personCityStateMap.entrySet().stream().map(me -> me.getValue().size()).reduce(0, (a, b) -> a + b);
        System.out.println("There are total " + count + " persons in the " + cityOrState + ".");
    }

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
                    me.getValue().stream().forEach(System.out::println);
            });
        }
    }
    public void sortedByName() {
        Map<String, Set<ContactPerson>> personNameSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            Set<ContactPerson> personsInCity = me.getValue().stream().sorted(Comparator.comparing(ContactPerson::getFirstName))
                    .collect(Collectors.toSet());
            personNameSortedMap.put(me.getKey(), personsInCity);
        }
        addressBookSystem = personNameSortedMap;
    }

    public Map<String, List<ContactPerson>> viewSortedByCity() {
        Map<String, List<ContactPerson>> personCitySortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInCity = me.getValue().stream()
                    .sorted(Comparator.comparing(ContactPerson::getCity)).collect(Collectors.toList());
            personCitySortedMap.put(me.getKey(), personsInCity);
        }
        return personCitySortedMap;
    }

    public Map<String, List<ContactPerson>> viewSortedByState() {
        Map<String, List<ContactPerson>> personStateSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInState = me.getValue().stream()
                    .sorted(Comparator.comparing(ContactPerson::getState)).collect(Collectors.toList());
            personStateSortedMap.put(me.getKey(), personsInState);
        }
        return personStateSortedMap;
    }

    public Map<String, List<ContactPerson>> viewSortedByZip() {
        Map<String, List<ContactPerson>> personZipSortedMap = new HashMap<>();
        for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
            List<ContactPerson> personsInZip = me.getValue().stream().sorted(Comparator.comparingInt(ContactPerson::getZip))
                    .collect(Collectors.toList());
            personZipSortedMap.put(me.getKey(), personsInZip);
        }
        return personZipSortedMap;
    }

    public void printSortedMap(Map<String, List<ContactPerson>> sortedMap) {
        if (sortedMap.size() == 0)
            System.out.println("There is no address book in the system.");
        else {
            sortedMap.entrySet().stream().forEach(me -> {
                System.out.println("The contact details of the " + me.getKey() + ":");
                if (me.getValue().size() == 0)
                    System.out.println("Sorry, there is no contact in the " + me.getKey() + ".");
                else
                    me.getValue().stream().forEach(System.out::println);
            });
        }
    }

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
        ContactPerson personDetails = new ContactPerson(firstName, lastName, address, city, state, zip, phoneNo,
                emailId);
        return personDetails;
    }
}

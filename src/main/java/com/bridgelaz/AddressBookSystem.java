package com.bridgelaz;

import java.io.IOException;
import java.util.*;


public class AddressBookSystem {
    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        AddressBook addressBookMain = new AddressBook();
        AddressBookFileNIO addBookFileNIO = new AddressBookFileNIO();
        OpenCSVService openCSVService = new OpenCSVService();
        JSONService jsonService = new JSONService();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Address Book System menu\n" + "Choose number what you want to do. ");
            System.out.println("1. Add address book to the system");
            System.out.println("2. Edit contact details of the address book");
            System.out.println("3. Delete contact details of the address book");
            System.out.println("4. Show particular address book by its name");
            System.out.println("5. Show All address books in the system");
            System.out.println("6. Search person by the city or sate");
            System.out.println("7. View person by the city or state");
            System.out.println("8. Count by city or sate");
            System.out.println("9. Sort the address book by City");
            System.out.println("10. Sort the address book by State");
            System.out.println("11. Sort the address book by Zip");
            System.out.println("12. Writing data to file");
            System.out.println("13. Reading data from file");
            System.out.println("14. Writing data to CSV");
            System.out.println("15. Reading data from CSV");
            System.out.println("16. Writing data to JSON");
            System.out.println("17. Reading data from JSON");
            System.out.println("18. Exit");
            String option = input.next();
            /**
             * if we choose option 1
             * add address books to the system
             */
            if (option.equals("1")) {
                System.out.println("Enter how many Number of Address Book to be added:");
                int noOfAddressBook = input.nextInt();
                /**
                 * using for loop for count address book and enter the name of address book
                 */
                for (int i = 0; i < noOfAddressBook; i++) {
                    System.out.println("Enter the name of the Address Book");
                    input.nextLine();
                    String addressBookName = input.nextLine();
                    System.out.println(
                            "Enter the number of person's details to be added in address book: " + addressBookName);
                    int noOfPerson = input.nextInt();
                    /**
                     * create a set, create object name as phonebook
                     */
                    Set<ContactPerson> phoneBook = new HashSet<>();
                    addressBookMain.setAddressBook(phoneBook);
                    /**
                     * using for loop,it will count of how many persons is to add in the address book
                     */
                    for (int j = 0; j < noOfPerson; j++) {
                        System.out.println("Enter the details of the Contact Person");
                        ContactPerson contactPerson = addressBookMain.addContactPersonDetails(input);
                        addressBookMain.addContactPerson(contactPerson);
                        /**
                         * persons firstname and last name stored in name variable
                         * for ex -Javul Mulla
                         */
                        String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
                        System.out.println("The details of the " + name + " is added to the Address Book: "
                                + addressBookName + " successfully.");
                    }

                    /**
                     * Now set the addressbook
                     */
                    Set<ContactPerson> addressBook = addressBookMain.getAddressBook();
                    /**
                     * calling addAddressBookToSystem from addressBookMain object
                     */
                    addressBookMain.addAddressBookToSystem(addressBookName, addressBook);

                    System.out.println("Address Book: " + addressBookName + " is successfully added to the system.");
                }
                continue;
            }

            /**
             * if u choose option 2
             * Editing contact details of the address book
             */
            if (option.equals("2")) {
                System.out.println("Enter the name of the address book of which person's details to be edited:");
                input.nextLine();
                String addressBookName = input.nextLine();
                /**
                 * checking the name of given address book in the address book main, if present then
                 */
                if (addressBookMain.isPresentAddressBook(addressBookName)) {
                    System.out.println("Enter the name of the person whose details to be edited:");
                    String personName = input.nextLine();
                    if (addressBookMain.editContactPersonDetails(addressBookName, personName, input))
                        System.out.println("The contact details of the " + personName + " from " + addressBookName
                                + " is edited.");
                    else
                        System.out.println("Sorry, the contact details of the " + personName + " is not found in "
                                + addressBookName + ". We can't proceed to edit.");
                } else
                    System.out.println("Sorry, the address book: " + addressBookName
                            + " is not found in the system. We can't proceed to edit.");
                continue;
            }

            /**
             * if u choose option 3
             * Deleting contact details of the address book
             */
            if (option.equals("3")) {
                System.out.println("Enter the name of the address book from which person's details to be deleted:");
                String addressBookName = input.nextLine();
                /**
                 * if the address book name is present in the addressbook main,whose name you have written then
                 */
                if (addressBookMain.isPresentAddressBook(addressBookName)) {
                    System.out.println("Enter the name of the person whose details to be deleted:");
                    String personName = input.nextLine();
                    if (addressBookMain.deleteContactPersonDetails(addressBookName, personName))
                        System.out.println("The contact details of the " + personName + " from " + addressBookName
                                + " is deleted.");
                    else
                        System.out.println("Sorry, the contact details of the " + personName + " is not found in "
                                + addressBookName + ". We can't proceed to delete.");
                } else
                    System.out.println("Sorry, the address book: " + addressBookName
                            + " is not found in the system. We can't proceed to delete.");
                continue;
            }

            /**
             * if u choose option no 4
             * Showing particular address book by its name
             */
            if (option.equals("4")) {
                System.out.println("Enter the name of the address book:");
                input.nextLine();
                String addressBookName = input.nextLine();
                if (addressBookMain.isPresentAddressBook(addressBookName))
                    addressBookMain.showAddressBook(addressBookName);
                else
                    System.out.println("Sorry, Address Book: " + addressBookName + " is not present in the system.");
                continue;
            }

            /**
             * if u choose option 5
             * Show all address books in the system
             */
            if (option.equals("5")) {
                /**
                 * calling showAddressbookSystem method from the addressBookMain object
                 */
                addressBookMain.showAddressBookSystem();
                continue;
            }

            /**
             * if u choose option 6
             * Searching person by the city or sate
             */
            if (option.equals("6")) {
                System.out.println("Enter the state/city name to search the persons:");
                input.nextLine();
                String cityOrStateName = input.nextLine();
                /**
                 * creating list ,search a person in addressbookMain by city or state
                 */
                List<String> personsInCityOrState = addressBookMain.searchPersonByCityOrState(cityOrStateName);
                if (personsInCityOrState.size() == 0)
                    System.out.println("Sorry, there is no person in the " + cityOrStateName + ".");
                else {
                    System.out.println("The list of persons in the " + cityOrStateName + ":");
                    personsInCityOrState.stream().forEach(personName -> System.out.println(personName));
                }
                continue;
            }

            /**
             * if u choose option 7
             * View person by the city or state
             */
            if (option.equals("7")) {
                System.out.println("Enter the state/city name to view the persons:");
                input.nextLine();
                String cityOrStateName = input.nextLine();
                /**
                 * using map and list ,view person by city or state in addressbook main
                 */
                Map<String, List<String>> personCityStateMap = addressBookMain.viewPersonByCityOrState(cityOrStateName);
                if (personCityStateMap.size() == 0)
                    System.out.println("Sorry, there is no any details.");
                else {
                    System.out.println("The mapping of city/state and persons:");
                    System.out.println(personCityStateMap);
                }
                continue;
            }

            /**
             * if u choose option 8
             * Count by city or sate
             */
            if (option.equals("8")) {
                System.out.println("Enter the state/city name to count the persons:");
                input.nextLine();
                String cityOrStateName = input.nextLine();
                /**
                 * create list, search the person by city or state in addressbook main and store in personInCityOrState
                 */
                List<String> personsInCityOrState = addressBookMain.searchPersonByCityOrState(cityOrStateName);
                if (personsInCityOrState.size() == 0)
                    System.out.println("Sorry, there is no person in the " + cityOrStateName + ".");
                else {
                    /**
                     * calling countPersonByCityOrState method from the addressBookMain object
                     */
                    addressBookMain.countPersonByCityorState(cityOrStateName);
                }
                continue;
            }

            /**
             * if u choose option 9
             * Sort person details by the city or state
             */
            if (option.equals("9")) {
                /**
                 * create map and list,in this case 1st we sorted the city and then persons view by city in addressbook main
                 * and if got this person then store the data in personCitySortedMap
                 */
                Map<String, List<ContactPerson>> personCitySortedMap = addressBookMain.viewSortedByCity();
                if (personCitySortedMap.size() == 0)
                    System.out.println("Sorry, there is no any details.");
                else {
                    System.out.println("The address books are sorted by city:");
                    addressBookMain.printSortedMap(personCitySortedMap);
                }
                continue;
            }

            /**
             * if u choose option 10
             * Sort person details by the city or state
             */
            if (option.equals("10")) {
                /**
                 * create map ,list 1st sorted by state and then view the person by state in addressbook main
                 * if got this then store the data in personStateSortedMap
                 */
                Map<String, List<ContactPerson>> personStateSortedMap = addressBookMain.viewSortedByState();
                if (personStateSortedMap.size() == 0)
                    System.out.println("Sorry, there is no any details.");
                else {
                    System.out.println("The address books are sorted by state:");
                    addressBookMain.printSortedMap(personStateSortedMap);
                }
                continue;
            }

            /**
             * if choose option no 11
             * Sort person details by the city or state
             */
            if (option.equals("11")) {
                /**
                 * create map,list ,1st sorted the data by zip then view the person by zip,if got this details then this details
                 * store in personZipSortedMap
                 */
                Map<String, List<ContactPerson>> personZipSortedMap = addressBookMain.viewSortedByZip();
                if (personZipSortedMap.size() == 0)
                    System.out.println("Sorry, there is no any details.");
                else {
                    System.out.println("The address books are sorted by zip:");

                    addressBookMain.printSortedMap(personZipSortedMap);
                }
                continue;
            }

            /**
             * if u choose option no 12
             * Writing data into file
             */
            if (option.equals("12")) {
                /**
                 * use try catch block for exception handling
                 */
                try {
                    addBookFileNIO.writeToFile(addressBookMain.getAddressBookSystem());
                } catch (IOException e) {
                    e.getMessage();
                }
                continue;
            }

            /**
             * if u choose the option no 13
             * Reading data from file
             */
            if (option.equals("13")) {
                try {
                    addBookFileNIO.readFromFile();
                } catch (IOException e) {
                    e.getMessage();
                }
                continue;
            }

            /**
             * if u choose option no 14
             * Writing data into CSV
             */
            if (option.equals("14")) {
                try {
                    openCSVService.writeToCsv(addressBookMain.getAddressBookSystem());
                } catch (IOException e) {
                    e.getMessage();
                }
                continue;
            }

            /**
             * if choose the option no 15
             * Reading data from CSV
             */
            if (option.equals("15")) {
                try {
                    openCSVService.readCsv();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }

            /**
             * if u choose the option 16
             * Writing data into JSON
             */
            if (option.equals("16")) {
                try {
                    jsonService.writeJson(addressBookMain.getAddressBookSystem());
                } catch (Exception e) {
                    e.getMessage();
                }
                continue;
            }

            /**
             * if u choose option no 17
             * Reading data from JSON
             */
            if (option.equals("17")) {
                int x = 0;
                try {
                    /**
                     * calling readJson method from jsonService object and result store in x variable
                     */
                    x = jsonService.readJson();
                    System.out.println(x);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }

            /**
             * if u choose option 18
             * Exiting from the address book system
             */
            if (option.equals("18")) {
                System.out.println("Thank you.");
                break;
            } else
                System.out.print("You entered the invalid option. Please, ");
        }
    }
}

import java.util.*;
import java.util.stream.*;


public class AddressBookMain {
	public static HashMap<String, AddressBook> addressBookMap = new HashMap<String, AddressBook>();
	public static HashMap<String, ArrayList<String>> cityPersonMap = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> statePersonMap = new HashMap<String, ArrayList<String>>();
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		int choice = 0;
		do
		{
			System.out.println("1. Add Contact");
			System.out.println("2. Edit Contact");
			System.out.println("3. Delete Contact");
			System.out.println("4. Search Persons By City or State");
			System.out.println("5. Get Total Persons Count By City or State");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			choice = in.nextInt();
			in.nextLine();
			switch(choice)
			{
			case 1:
				{
					addAddressBook(in);
					break;
				}
				case 2:
				{
					editAddressBook(in);
					break;
				}
				case 3:
				{
					deleteAddressBook(in);
					break;
				}
				case 4:
				{
					viewPersonsByCityOrState(in);
					break;
				}
				case 5:
				{
					getCountOfPersonsByCityOrState(in);
				}
			}
		}while(choice!=6);
		System.out.println("Program Terminated");
		in.close();
	}
	
	/**
	 * @param in
	 * Adds a contact to the dictionary
	 */
	private static void addAddressBook(Scanner in) {
		AddressBook contact = new AddressBook();
		do {
			System.out.print("Enter first name: ");
			contact.setFirstName(in.nextLine());
		} while(mapHasDuplicates(contact) == true);
		System.out.print("Enter last name: ");
		contact.setLastName(in.nextLine());
		System.out.print("Enter address: ");
		contact.setAddress(in.nextLine());
		System.out.print("Enter city: ");
		contact.setCity(in.nextLine());
		System.out.print("Enter state: ");
		contact.setState(in.nextLine());
		System.out.print("Enter phoneNumber: ");
		contact.setPhoneNumber(in.nextLine());
		System.out.print("Enter email: ");
		contact.setEmail(in.nextLine());
		System.out.print("Enter zip: ");
		contact.setZip(in.nextLong());
		in.nextLine();
		addressBookMap.put(contact.getFirstName(),contact);
		insertTimeUpdateMaps(contact.getCity(), contact.getState(), contact.getFirstName());
	}
	
	/**
	 * @param in
	 * Edit a contact in dictionary
	 */
	private static void editAddressBook(Scanner in) {
		System.out.print("Enter the contact first name to edit: ");
		String searchName = in.nextLine();
		if(addressBookMap.containsKey(searchName))
		{
			AddressBook contact = new AddressBook();
			System.out.print("Enter first name: ");
			contact.setFirstName(in.nextLine());
			System.out.print("Enter last name: ");
			contact.setLastName(in.nextLine());
			System.out.print("Enter address: ");
			contact.setAddress(in.nextLine());
			System.out.print("Enter city: ");
			contact.setCity(in.nextLine());
			System.out.print("Enter state: ");
			contact.setState(in.nextLine());
			System.out.print("Enter phoneNumber: ");
			contact.setPhoneNumber(in.nextLine());
			System.out.print("Enter email: ");
			contact.setEmail(in.nextLine());
			System.out.print("Enter zip: ");
			contact.setZip(in.nextLong());
			in.nextLine();
			addressBookMap.remove(searchName);
			addressBookMap.put(contact.getFirstName(),contact);
			editTimeUpdateMaps(contact.getCity(), contact.getState(), searchName, contact.getFirstName());
			System.out.println("Contact modified");
		}
		else
			System.out.println("Contact not found");
	}
	
	/**
	 * @param in
	 * Delete a contact in dictionary
	 */
	private static void deleteAddressBook(Scanner in) {
		System.out.print("Enter the contact first name to delete: ");
		String searchName = in.nextLine();
		if(addressBookMap.containsKey(searchName))
		{
			addressBookMap.remove(searchName);
			deleteTimeUpdateMaps(addressBookMap.get(searchName).getCity(), addressBookMap.get(searchName).getState(), searchName);
			System.out.println("Contact deleted successfully");
		}
		else
			System.out.println("Contact not found");
	}
	
	/**
	 * @param firstName
	 * @return
	 * Checks for duplicates while adding new contact
	 */
	private static boolean mapHasDuplicates(AddressBook contact) {
		try {
			if(contact.equals(addressBookMap.entrySet().stream().filter(e -> contact.equals(e.getKey())).findFirst().get().getKey()));
			{
				System.out.println("Contact matching " + contact.getFirstName() + " already exists");
				return true;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	/**
	 * @param in
	 * Prints people in the corresponding city or state
	 */
	private static void viewPersonsByCityOrState(Scanner in) {
		System.out.println("1. City \n"
							+ "2. State");
		System.out.print("Enter your choice: ");
		int choice = in.nextInt();
		in.nextLine();
		switch(choice)
		{
			case 1:
				{
					System.out.print("Enter city: ");
					String city = in.nextLine();
					cityPersonMap.entrySet().stream().filter(e -> e.getKey().equals(city)).forEach(e -> System.out.println(e.getValue()));
					break;
				}
			case 2:
				{
					System.out.print("Enter state: ");
					String state = in.nextLine();
					statePersonMap.entrySet().stream().filter(e -> e.getKey().equals(state)).forEach(e -> System.out.println(e.getValue()));
				}
		}
	}
	
	/**
	 * @param locality
	 * @param contactFirstName
	 * @param map
	 * @param actionChoice
	 * Updates cityPersonMap and statePersonMap
	 */
	private static void updateMaps(String locality, String contactFirstName, HashMap<String, ArrayList<String>> map, int actionChoice) {
		
		switch(actionChoice)
		{
			//Add
			case 1:
			{
				ArrayList<String> firstNameList;
				if(map.containsKey(locality))
					firstNameList = map.get(locality);
				else
					firstNameList = new ArrayList<String>();
				firstNameList.add(contactFirstName);
				map.put(locality, firstNameList);
				break;
			}
			//Remove
			case 2:
			{
				ArrayList<String> firstNameList = map.get(locality);
				firstNameList.remove(contactFirstName);
			}
		}
	}
	
	private static void insertTimeUpdateMaps(String city, String state, String firstName) {
		updateMaps(city, firstName, cityPersonMap, 1); //add new firstName to cityPersonMap
		updateMaps(state, firstName, statePersonMap, 1); //add new firstName to statePersonMap
	}
	
	private static void editTimeUpdateMaps(String city, String state, String olderFirstName, String currentFirstName) {
		updateMaps(city, olderFirstName, cityPersonMap, 2); //remove old firstName from cityPersonMap
		updateMaps(city, currentFirstName, cityPersonMap, 1); //add new firstName to cityPersonMap
		updateMaps(state, olderFirstName, statePersonMap, 2); //remove old firstName from statePersonMap
		updateMaps(state, currentFirstName, statePersonMap, 1); //add new firstName to statePersonMap
	}
	
	private static void deleteTimeUpdateMaps(String city, String state, String firstName) {
		updateMaps(city, firstName, cityPersonMap, 2); //delete firstName from cityPersonMap
		updateMaps(state, firstName, statePersonMap, 2); //delete firstName from statePersonMap
	}
	
	/**
	 * @param in
	 * Displays total number of contacts in a city or state
	 */
	private static void getCountOfPersonsByCityOrState(Scanner in) {
		System.out.println("1. City \n"
						+ "2. State");
		System.out.print("Enter your choice (1/2): ");
		int choice = in.nextInt();
		in.nextLine();
		switch(choice)
		{
			case 1:
			{
				System.out.print("Enter city name: ");
				String cityName = in.nextLine();
				try {
					System.out.println("Total count: " +
							cityPersonMap.entrySet().stream().filter(e -> e.getKey().equals(cityName)).findFirst().get().getValue().size());
				} catch (NoSuchElementException e) {
					System.out.println("Total count: 0");
				}
				break;
			}
			case 2:
			{
				System.out.print("Enter state name: ");
				String stateName = in.nextLine();
				try {
					System.out.println("Total count: " + 
							statePersonMap.entrySet().stream().filter(e -> e.getKey().equals(stateName)).findFirst().get().getValue().size());
				} catch (NoSuchElementException e) {
					System.out.println("Total count: 0");
				}
				break;
			}
		}
	}
}

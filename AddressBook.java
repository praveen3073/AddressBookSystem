import java.util.*;

public class AddressBook {
	private String addressBookName;
	private ArrayList<Contact> contactList;
	public AddressBook() {
		addressBookName = "";
		contactList = new ArrayList<Contact>();
	}
	public String getAddressBookName() {
		return addressBookName;
	}
	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}
	public ArrayList<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}
}

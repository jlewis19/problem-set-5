/**
 * Just like last time, the ATM class is responsible for managing all
 * of the user interaction. This means login procedures, displaying the
 * menu, and responding to menu selections. In the enhanced version, the
 * ATM class will have the added responsibility of interfacing with the
 * Database class to write and read information to and from the database.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */
import java.util.Scanner;

public class ATM {
	Scanner in = new Scanner(System.in);
	Database db = new Database();
	
	public ATM() {
		System.out.println("Welcome to the legendary, one-and-only, ATM machine.\nYou're going to love all the incredible services offered.\n");
		welcome();
	}
	
	public void welcome() {
		System.out.print("\nWhat would you like to do?\n\t[1] Open account\n\t[2] Login\n\t[3] Quit\n > ");
		
		String selection = in.nextLine();
		while (selection.length() != 1 || selection.charAt(0) < '1' || selection.charAt(0) > '3') {
			System.out.print("\nPlease enter either 1, 2, or 3.\n > ");
			selection = in.nextLine();
		}
		
		switch (selection) {
			case "1":
				create();
				break;
			case "2":
				login();
				break;
			case "3":
				quit();
		}
	}
	
	public void create() {
		db.create();
	}
	
	public void login() {
		System.out.print("\nPlease enter your account number or press 0 to quit.\n > ");
		String accountNum = in.nextLine();
		if (accountNum.equals("0")) {
			quit();
		}
		
		for (int i = 0; i < 9; i++) {
			while (accountNum.length() != 9 || accountNum.charAt(i) < '0' || accountNum.charAt(i) > '9' || !db.checkAccountNum(Integer.valueOf(accountNum)) || db.findField(db.read("Account Status", true), Integer.valueOf(accountNum)).equals("N")) {
				if (accountNum.length() != 9) {
					System.out.println("\nYour account number must be 9 digits long.");
				} else if (accountNum.charAt(i) < '0' || accountNum.charAt(i) > '9') {
					System.out.println("\nYour account number must not contain characters.");
				} else if (db.findField(db.read("Account Status", true), Integer.valueOf(accountNum)).equals("N")) {
					System.out.println("\nThis account has been deactivated.");
				} else {
					System.out.println("\nInvalid account number.");
				}
				System.out.print("Please enter your account number or press 0 to quit.\n > ");
				
				accountNum = in.nextLine();
				if (accountNum.equals("0")) {
					quit();
				}
				i = 0;
			}
		}
		
		String pin = "";
		
		System.out.print("\nPlease enter your PIN number or press 0 to quit.\n > ");
		pin = in.nextLine();
		if (pin.equals("0")) {
			quit();
		}
		while (pin.length() != 4) {
			System.out.print("\nYour pin must have 4 digits.\nPlease enter your PIN number or press 0 to quit.\n > ");
			pin = in.nextLine();
			if (pin.equals("0")) {
				quit();
			}
		}
		while (!db.checkPin(Integer.valueOf(accountNum), pin)) {
			System.out.print("\nPIN does not match account.\nPlease enter your PIN number or press 0 to quit.\n > ");
			pin = in.nextLine();
			if (pin.equals("0")) {
				quit();
			}
			while (pin.length() != 4) {
				System.out.print("\nYour pin must have 4 digits.\nPlease enter your PIN number or press 0 to quit.\n > ");
				pin = in.nextLine();
				if (pin.equals("0")) {
					quit();
				}
			}
		}
		
		home(Integer.valueOf(accountNum));
	}
	
	public void home(int accountNum) {
		User user = new User(db.findField(db.read("First Name", true), accountNum), db.findField(db.read("Last Name", true), accountNum), db.findField(db.read("PIN", true), accountNum), Integer.valueOf(db.findField(db.read("Date of Birth", true), accountNum)), Long.valueOf(db.findField(db.read("Phone Number", true), accountNum)), db.findField(db.read("Street Address", true), accountNum), db.findField(db.read("City", true), accountNum), db.findField(db.read("State", true), accountNum), db.findField(db.read("Postal Code", true), accountNum));
		BankAccount ba = new BankAccount(accountNum, user, db.findField(db.read("Balance", true), accountNum));
		
		boolean login = true;
		while (login) {
			System.out.print("\nWhat can I help you with?\n\n\t[1] Deposit funds\n\t[2] Withdraw funds\n\t[3] Transfer funds\n\t[4] View balance\n\t[5] View personal information\n\t[6] Update personal information\n\t[7] Close account\n\t[8] Logout\n\nPlease make a selection.\n > ");
			String decision = in.nextLine();
			while (decision.length() != 1 || decision.charAt(0) < '1' || decision.charAt(0) > '8') {
				System.out.print("\nPlease enter a number from 1 through 8.\n > ");
				decision = in.nextLine();
			}
			
			switch (decision) {
				case "1":
					ba.deposit();
					db.append("Balance", user, ba);
					break;
				case "2":
					ba.withdraw();
					db.append("Balance", user, ba);
					break;
				case "3":
					ba.transfer();
					db.append("Balance", user, ba);
					break;
				case "4":
					System.out.printf("\nYour current balance is $%.2f.\n", Double.valueOf(ba.getBalance()));
					break;
				case "5":
					System.out.println("\nFirst Name:\t" + user.getFName() + "\nLast Name:\t" + user.getLName() + "\nPIN Number:\t" + user.getPin() + "\nDate of Birth:\t" + user.getBDay() + "\nPhone Number:\t" + user.getPhoneNum() + "\nAddress:\t" + user.getAddress() + "\nCity:\t\t" + user.getCity() + "\nState:\t\t" + user.getState() + "\nPostal Code:\t" + user.getZip());
					break;
				case "6":
					System.out.print("\nWhat would you like to update? (Note: first name, last name, and date of birth cannot be changed.)\n\n\t[1] PIN\n\t[2] Phone Number\n\t[3] Address\n\t[4] City\n\t[5] State\n\t[6] Postal Code\n\t[7] Exit\n\nPlease make a selection.\n > ");
					String update = in.nextLine();
					while (update.length() != 1 || decision.charAt(0) < '1' || decision.charAt(0) > '6') {
						System.out.print("\nPlease enter a number from 1 through 6.\n > ");
						update = in.nextLine();
					}
					switch (update) {
						case "1": 
							user.setPin();
							db.append("PIN", user, ba);
							break;
						case "2":
							user.setPhoneNum();
							db.append("Phone Number", user, ba);
							break;
						case "3":
							user.setAddress();
							db.append("Address", user, ba);
							break;
						case "4":
							user.setCity();
							db.append("City", user, ba);
							break;
						case "5":
							user.setState();
							db.append("State", user, ba);
							break;
						case "6":
							user.setZip();
							db.append("Postal Code", user, ba);
							break;
						case "7":
							break;
					}
					break;
				case "7":
					db.append("Status", user, ba);
					System.out.println("\nAccount closed.");
					quit();
				case "8":
					login = false;
			}
		}
		quit();
	}
	
	public void quit() {
		System.out.println("\nThank you for using the legendary, one-and-only ATM machine.\nWe hope you enjoyed your experience.\nGoodbye!");
		System.exit(0);
	}
}
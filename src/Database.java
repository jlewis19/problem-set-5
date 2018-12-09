/**
 * This class will serve as the intermediary between our ATM program and
 * the database of BankAccounts. It'll be responsible for fetching accounts
 * when users try to login, as well as updating those accounts after any
 * changes are made.
 */

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Database {
	Scanner in = new Scanner(System.in);
	
	public Database() {
		
	}
	
	public void create() {
		System.out.print("\nEnter your first name: ");
		String fName = in.nextLine();
		
		System.out.print("\nEnter your last name: ");
		String lName = in.nextLine();
		
		System.out.print("\nEnter your birthday in the format \"YYYYMMDD\": ");
		String bDayString = in.nextLine();
		for (int i = 0; i < 8; i++) {
			while (bDayString.length() != 8 || bDayString.charAt(i) < '0' || bDayString.charAt(i) > '9') {
				System.out.print("\nYour birthday should be in the format \"YYYYMMDD.\"\nEnter your birthday: ");
				bDayString = in.nextLine();
				i = 0;
			}
		}
		int bDay = Integer.valueOf(bDayString);
		
		System.out.print("\nEnter your 10-digit phone number (don't use dashes): ");
		String phoneNumString = in.nextLine();
		for (int i = 0; i < 10; i++) {
			while (phoneNumString.length() != 10 || phoneNumString.charAt(i) < '0' || phoneNumString.charAt(i) > '9' || phoneNumString.charAt(0) == '0') {
				System.out.print("\nYour phone number should have 10 digits and cannot start with 0.\nEnter your 10-digit phone number (don't use dashes): ");
				phoneNumString = in.nextLine();
				i = 0;
			}
		}
		long phoneNum = Long.valueOf(phoneNumString);
		
		System.out.print("\nEnter your street address: ");
		String address = in.nextLine();
		
		System.out.print("\nEnter the city you live in: ");
		String city = in.nextLine();
		
		System.out.print("\nEnter the state you live in: ");
		String state = in.nextLine();
		for (int i = 0; i < 2; i++) {
			while (state.length() > 2 || state.charAt(i) < 'A' || state.charAt(i) > 'Z') {
				System.out.print("\nThe state should be in the format \"NJ\".\nEnter the state you live in: ");
				state = in.nextLine();
				i = 0;
			}
		}
		
		System.out.print("\nEnter your 5 digit postal code: ");
		String zip = in.nextLine();
		for (int i = 0; i < 5; i++) {
			while (zip.length() != 5 || zip.charAt(i) < '0' || zip.charAt(i) > '9') {
				System.out.print("\nYour postal code should have 5 digits.\nEnter your 5 digit postal code: ");
				zip = in.nextLine();
				i = 0;
			}
		}
		
		int accountNum = randomAccountNum();
		while (checkAccountNum(accountNum)) {
			accountNum = randomAccountNum();
		}
		System.out.println("\nYour new account number is " + accountNum + ".\nPlease copy this number somewhere where you will remember it.");
	
		System.out.print("\nEnter your new 4 digit PIN number: ");
		String pin = in.nextLine();
		System.out.print("\nConfirm your new PIN: ");
		String checkPin = in.nextLine();
		
		for (int i = 0; i < 4; i++) {
			while (!pin.equals(checkPin) || pin.length() != 4 || pin.charAt(i) < '0' || pin.charAt(i) > '9') {
				if (!pin.equals(checkPin)) {
					System.out.println("\nPINs did not match.");
				} else if (pin.length() != 4) {
					System.out.println("\nYour PIN number must have 4 digits.");
				} else {
					System.out.println("\nYour PIN number must not contain characters.");
				}
				System.out.print("Enter your new 4 digit PIN number: ");
				pin = in.nextLine();
				System.out.print("\nConfirm your new PIN: ");
				checkPin = in.nextLine();
				i = 0;
			}
		}
		
		User newUser = new User(fName, lName, pin, bDay, phoneNum, address, city, state, zip);
		BankAccount newBA = new BankAccount(accountNum, newUser, "0.00");
		newEntry(newUser, newBA);
	}
	
	public boolean checkAccountNum(int accountNum) {
		String checker = read("Account Number", false);
		for (int i = 0; i < checker.length() - 9; i++) {
			if (checker.substring(i, i + 9).equals(String.valueOf(accountNum))) {
				return true;
			}
		}
		return false;
	}
	
	public String read(String field, boolean includeAccountNum) {
		String line;
        String list = "";
		
		try {
            FileReader reader = new FileReader("accounts-db.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            while ((line = bufferedReader.readLine()) != null) {
            	switch (field) {
            		case "All":
            			list += line;
            			break;
            		case "Account Number":
            			list += line.substring(0, 9) + "\n";
            			break;
            		case "PIN":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.substring(9, 13) + "\n";
            			break;
            		case "Balance":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			for (int i = 13; line.charAt(i - 3) != '.' && line.charAt(i) != ' '; i++) {
            				list += line.charAt(i);
            			}
            			list += "\n";
            			break;
            		case "Last Name":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			for (int i = 28; i < 48 && line.charAt(i) != ' '; i++) {
            				list += line.charAt(i);
            			}
            			list += "\n";
            			break;
            		case "First Name":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			for (int i = 48; i < 63 && line.charAt(i) != ' '; i++) {
            				list += line.charAt(i);
            			}
            			list += "\n";
            			break;
            		case "Date of Birth":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.substring(63, 71) + "\n";
            			break;
            		case "Phone Number":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.substring(71, 81) + "\n";
            			break;
            		case "Street Address":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			for (int i = 81; !line.substring(i, i + 1).equals("  ") && i < 111; i++) {
            				list += line.charAt(i);
            			}
            			list += "\n";
            			break;
            		case "City":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			for (int i = 111; !line.substring(i, i + 1).equals("  ") && i< 141; i++) {
            				list += line.charAt(i);
            			}
            			list += "\n";
            			break;
            		case "State":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.substring(141, 143) + "\n";
            			break;
            		case "Postal Code":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.substring(143, 148) + "\n";
            			break;
            		case "Account Status":
            			if (includeAccountNum) {
            				list += line.substring(0, 9) + " ";
            			}
            			list += line.charAt(148) + "\n";
            			break;
            	}
            }
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
	
	public String findField(String list, int accountNum) {
		String returnValue = "";
		for (int i = 0; i < list.length() - 9; i++) {
			if (list.substring(i, i+9).equals(String.valueOf(accountNum))) {
				int j = i+10;
				while (list.charAt(j) != '\n' && j < list.length()) {
					returnValue += list.charAt(j);
					j++;
				}
			}
		}
		return returnValue;
	}

	public void newEntry(User user, BankAccount ba) {
		try {
			FileWriter writer = new FileWriter("accounts-db.txt", true);
			
			for (int i = 0; i < 9; i++) {
				writer.write(String.valueOf(ba.getAccountNum()).charAt(i));
			}
			writer.write(user.getPin());

			if (String.valueOf(ba.getBalance()).length() > 15) {
				for (int i = 0; i < 15; i++) {
					writer.write(String.valueOf(ba.getBalance()).charAt(i));
				}
			} else {
				writer.write(String.valueOf(ba.getBalance()));
				for (int i = String.valueOf(ba.getBalance()).length(); i < 15; i++) {
					writer.write(" ");
				}
			}
					
			if (user.getLName().length() <= 20) {
				writer.write(user.getLName());
				for (int i = user.getLName().length(); i < 20; i++) {
					writer.write(" ");
				}
			} else {
				for (int i = 0; i < 20; i++) {
					writer.write(user.getLName().charAt(i));
				}
			}
			
			if (user.getFName().length() <= 15) {
				writer.write(user.getFName());
				for (int i = user.getFName().length(); i < 15; i++) {
					writer.write(" ");
				}
			} else {
				for (int i = 0; i < 15; i++) {
					writer.write(user.getFName().charAt(i));
				}
			}
			
			writer.write(String.valueOf(user.getBDay()));
			writer.write(String.valueOf(user.getPhoneNum()));
					
			if (user.getAddress().length() > 30) {
				for (int i = 0; i < 30; i++) {
					writer.write(user.getAddress().charAt(i));
				}
			} else {
				String paddedAddress = user.getAddress();
				while (paddedAddress.length() < 30) {
					paddedAddress += " ";
				}
				writer.write(paddedAddress);
			}
					
			if (user.getCity().length() > 30) {
				for (int i = 0; i < 30; i++) {
					writer.write(user.getCity().charAt(i));
				}
			} else {
				String paddedCity = user.getCity();
				while (paddedCity.length() < 30) {
					paddedCity += " ";
				}
				writer.write(paddedCity);
			}
					
			writer.write(user.getState());
			writer.write(user.getZip());
			writer.write("Y\n");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void append(String field, User user, BankAccount ba) {
		try {
			String database = read("All", false);
			String newDatabase = "";
			
			FileWriter writer = new FileWriter("accounts-db.txt", false);
			
			int placement = 0;
			
			for (int i = 0; i < database.length(); i += 149) {
				if (database.substring(i, i+9).equals(String.valueOf(ba.getAccountNum()))) {
					newDatabase += database.substring(i, i+9);
					switch(field) {
						case "Balance":
							String balance = ba.getBalance();
							
							newDatabase += database.substring(i+9, i+13);
							if (balance.length() == 15) {
								newDatabase += balance;
							} else {
								newDatabase += balance;
								for (int k = balance.length(); k < 15; k++) {
									newDatabase += " ";
								}
							}
							newDatabase += database.substring(i+28, i+149) + "\n";
						
							break;
						case "PIN":
							newDatabase += user.getPin();
							newDatabase += database.substring(i+13, i+149) + "\n";
						
							break;
						case "Phone Number":
							newDatabase += database.substring(i+9, i+71);
							newDatabase += user.getPhoneNum();
							newDatabase += database.substring(i+81, i+149) + "\n";
						
							break;
						case "Address":
							newDatabase += database.substring(i+9, i+81);
							if (user.getAddress().length() > 30) {
								for (int j = 0; j < 30; j++) {
									newDatabase += user.getAddress().charAt(j);
								}
							} else {
								newDatabase += user.getAddress();
								for (int k = user.getAddress().length(); k < 30; k++) {
									newDatabase += " ";
								}
							}
							newDatabase += database.substring(i+111, i+149) + "\n";
						
							break;
						case "City":
							newDatabase += database.substring(i+9, i+111);
							if (user.getCity().length() > 30) {
								for (int j = 0; j < 30; j++) {
									newDatabase += user.getCity().charAt(j);
								}
							} else {
								newDatabase += user.getCity();
								for (int k = user.getCity().length(); k < 30; k++) {
									newDatabase += " ";
								}
							}
							newDatabase += database.substring(i+141, i+149) + "\n";
						
							break;
						case "State":
							newDatabase += database.substring(i+9, i+141);
							newDatabase += user.getState();
							newDatabase += database.substring(i+143, i+149) + "\n";
						
							break;
						case "Postal Code":
							newDatabase += database.substring(i+9, i+143);
							newDatabase += user.getZip();
							newDatabase += database.substring(i+148, i+149) + "\n";
							break;
						case "Status":
							newDatabase += database.substring(i+9, i+148) + "N\n";
							break;
					}
				} else {
					newDatabase += database.substring(i, i+149) + "\n";
				}
				
			placement = i;
		}
			
			for (int j = placement+149; j < database.length(); j += 149) {
				newDatabase += database.substring(j, j + 149);
				if (j + 149 < database.length()) {
					newDatabase += "\n";
				}
			}
			
			writer.write(newDatabase);
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int randomAccountNum() {
		int randomNum = ThreadLocalRandom.current().nextInt(100000000, 999999999);
		return randomNum;
	}
	
	public boolean checkPin(int accountNum, String pin) {
		String value = String.valueOf(accountNum) + " " + pin;
		String checker = read("PIN", true);
		for (int i = 0; i < checker.length() - 14; i++) {
			if (checker.substring(i, i + 14).equals(value)) {
				return true;
			}
		}
		return false;
	}
}
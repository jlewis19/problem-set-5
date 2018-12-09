/**
 * Just like last time, the User class is responsible for retrieving
 * (i.e., getting), and updating (i.e., setting) user information.
 * This time, though, you'll need to add the ability to update user
 * information and display that information in a formatted manner.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

import java.util.Scanner;

public class User {
	Scanner in = new Scanner(System.in);
	
	private String fName;
	private String lName;
	private String pin;
	private int bDay;
	private long phoneNum;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	public User(String fName, String lName, String pin, int bDay, long phoneNum, String address, String city, String state, String zip) {
		this.fName = fName;
		this.lName = lName;
		this.pin = pin;
		this.bDay = bDay;
		this.phoneNum = phoneNum;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getFName() {
		return this.fName;
	}
	
	public String getLName() {
		return this.lName;
	}
	
	public String getPin() {
		return this.pin;
	}
	
	public int getBDay() {
		return this.bDay;
	}
	
	public long getPhoneNum() {
		return this.phoneNum;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	public void setPin() {
		System.out.print("\nEnter your current PIN: ");
		String checkPin = in.nextLine();
		
		while (!this.pin.equals(checkPin)) {
			System.out.print("\nIncorrect PIN.\nEnter your current PIN: ");
			checkPin = in.nextLine();
		}
			
		System.out.print("\nEnter your new PIN: ");
		String newPin = in.nextLine();
		System.out.print("\nConfirm your new PIN: ");
		String checkNewPin = in.nextLine();
			
		for (int i = 0; i < 4; i++) {
			while (!newPin.equals(checkNewPin) || newPin.length() != 4 || newPin.charAt(i) < '0' || newPin.charAt(i) > '9') {
				if (!newPin.equals(checkNewPin)) {
					System.out.println("\nPINs did not match.");
				} else if (newPin.length() != 4) {
					System.out.println("\nYour PIN number must have 4 digits.");
				} else {
					System.out.println("\nYour PIN number must not contain characters.");
				}
				System.out.print("Enter your new 4 digit PIN number: ");
				newPin = in.nextLine();
				System.out.print("\nConfirm your new PIN: ");
				checkNewPin = in.nextLine();
				i = 0;
			}
		}
		
		this.pin = newPin;
		System.out.println("\nSuccessfully changed.");
	}
	
	public void setPhoneNum() {
		System.out.print("\nEnter your new 10-digit phone number (don't use dashes): ");
		String phoneNumString = in.nextLine();
		for (int i = 0; i < 10; i++) {
			while (phoneNumString.length() != 10 || phoneNumString.charAt(i) < '0' || phoneNumString.charAt(i) > '9' || phoneNumString.charAt(0) == '0') {
				System.out.print("\nYour phone number should have 10 digits and cannot start with 0.\nEnter your 10-digit phone number (don't use dashes): ");
				phoneNumString = in.nextLine();
				i = 0;
			}
		}
		this.phoneNum = Long.valueOf(phoneNumString);
		System.out.println("\nSuccessfully changed.");
	}
	
	public void setAddress() {
		System.out.print("\nEnter your new street address: ");
		String address = in.nextLine();
		
		this.address = address;
		System.out.println("\nSuccessfully changed.");
	}
	
	public void setCity() {
		System.out.print("\nEnter the name of your new city: ");
		String city = in.nextLine();
		
		this.city = city;
		System.out.println("\nSuccessfully changed.");
	}
	
	public void setState() {
		System.out.print("\nEnter the abbreviation of the state you live in: ");
		String state = in.nextLine();
		for (int i = 0; i < 2; i++) {
			while (state.length() > 2 || state.charAt(i) < 'A' || state.charAt(i) > 'Z') {
				System.out.print("\nThe state should be in the format \"NJ\".\nEnter the state you live in: ");
				state = in.nextLine();
				i = 0;
			}
		}
		
		this.state = state;
		System.out.println("\nSuccessfully changed.");
	}
	
	public void setZip() {
		System.out.print("\nEnter your new 5 digit postal code: ");
		String zip = in.nextLine();
		for (int i = 0; i < 5; i++) {
			while (zip.length() != 5 || zip.charAt(i) < '0' || zip.charAt(i) > '9') {
				System.out.print("\nYour postal code should have 5 digits.\nEnter your 5 digit postal code: ");
				zip = in.nextLine();
				i = 0;
			}
		}
		
		this.zip = zip;
		System.out.println("\nSuccessfully changed.");
	}
}
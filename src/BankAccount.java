import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

/**
 * Just like last time, the BankAccount class is primarily responsible
 * for depositing and withdrawing money. In the enhanced version, there
 * will be the added requirement of transfering funds between accounts.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

public class BankAccount {
	private int accountNum;
	private User user;
	private double balance;
	
	Scanner in = new Scanner(System.in);
	
	public BankAccount(int accountNum, User user, double balance) {
		this.accountNum = accountNum;
		this.user = user;
		this.balance = balance;
	}
	
	public void deposit() {
		System.out.printf("\nYour current balance is $%.2f.\nHow much would you like to deposit?\n > ", this.balance);
		double deposit = in.nextDouble();
		
		while (deposit <= 0 || deposit > 999999999999.99 - this.balance) {
			if (deposit <= 0) {
				System.out.print("\nYou cannot deposit zero or a negative amount.\nHow much would you like to deposit?\n > ");
			} else {
				System.out.print("\nYou cannot hold more than $999,999,999,999.99 in your account.\nHow much would you like to deposit?\n > ");
			}
			deposit = in.nextDouble();
		}
		
		this.balance += deposit;
		System.out.printf("\nYour new balance is $%.2f.\n", this.balance);
		return;
	}
	
	public void withdraw() {
		if (this.balance == 0) {
			System.out.println("\nThere is no money in the account to withdraw.");
			return;
		}
		System.out.printf("\nYour current balance is $%.2f.\nHow much would you like to withdraw?\n > ", this.balance);
		double withdrawal = in.nextDouble();
		
		while (withdrawal <= 0 || withdrawal > balance) {
			if (withdrawal <= 0) {
				System.out.println("\nYou cannot withdraw zero or a negative amount.");
			} else {
				System.out.println("\nYou cannot withdraw more than your balance.");
			}
			System.out.print("How much would you like to withdraw?\n > ");
			withdrawal = in.nextDouble();
		}
		
		this.balance -= withdrawal;
		System.out.printf("\nYour new balance is $%.2f.", this.balance);
		return;
	}
	
	public void transfer() {
		if (this.balance == 0) {
			System.out.println("\nThere is no money in the account to transfer.");
			return;
		}
		
		System.out.printf("\nYour current balance is $%.2f.\nHow much money would you like to transfer?\n > ", this.balance);
		double transferral = in.nextDouble();
		
		while (transferral <= 0 || transferral > this.balance) {
			if (transferral <= 0) {
				System.out.println("\nYou cannot transfer zero or a negative amount.");
			} else {
				System.out.println("\nYou cannot transfer more than your balance.");
			}
			System.out.print("How much money would you like to transfer?\n > ");
			transferral = in.nextDouble();
		}
		
		System.out.print("\nEnter the account number that you would like the money to be transferred to.\n > ");
		in.nextLine();
		String newAccountNum = in.nextLine();
		Database db = new Database();
		
		for (int i = 0; i < 9; i++) {
			while (newAccountNum.length() != 9 || newAccountNum.charAt(i) < '0' || newAccountNum.charAt(i) > '9' || !db.checkAccountNum(Integer.valueOf(newAccountNum)) || db.findField(db.read("Account Status", true), Integer.valueOf(newAccountNum)).equals("N")) {
				if (newAccountNum.length() != 9) {
					System.out.println("\nThe account number must be 9 digits long.");
				} else if (newAccountNum.charAt(i) < '0' || newAccountNum.charAt(i) > '9') {
					System.out.println("\nThe account number must not contain characters.");
				} else if (db.findField(db.read("Account Status", true), Integer.valueOf(newAccountNum)).equals("N")) {
					System.out.println("\nThat account has been deactivated.");
				} else {
					System.out.println("\nInvalid account number.");
				}
				System.out.print("Please enter the account number or press 0 to quit.\n > ");
				
				newAccountNum = in.nextLine();
				if (newAccountNum.equals("0")) {
					return;
				}
				i = 0;
			}
		}
		
		User fakeUser = new User("name", "name", "pin", 12345678, 555, "address", "city", "st", "zip");
		BankAccount fakeAccount = new BankAccount(Integer.valueOf(newAccountNum), fakeUser, Double.valueOf(db.findField(db.read("Balance", true), Integer.valueOf(newAccountNum))) + transferral);
		db.append("Balance", fakeUser, fakeAccount);
		this.balance -= transferral;
		
		System.out.println("\nSuccessfully transferred.");
		
		return;
	}
	
	public int getAccountNum() {
		return this.accountNum;
	}
	
	public double getBalance() {
		return this.balance;
	}
}
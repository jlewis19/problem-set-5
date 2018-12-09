import java.text.DecimalFormat;
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
	private String balance;
	
	Scanner in = new Scanner(System.in);
	
	public BankAccount(int accountNum, User user, String balance) {
		this.accountNum = accountNum;
		this.user = user;
		this.balance = balance;
	}
	
	public void deposit() {
		if (this.balance.equals("999999999999.99")) {
			System.out.println("\nYou cannot deposit any more money.");
			return;
		}
		System.out.printf("\nYour current balance is $%.2f.", Double.valueOf(this.balance));
		String strDeposit = "0";
		double deposit = 0;
		System.out.print("\nHow much would you like to deposit?\n > ");
		strDeposit = in.nextLine();
		
		boolean isDouble = false;
		
		while (!isDouble) {
			try {
				deposit = Double.parseDouble(strDeposit);
				isDouble = true;
				while (deposit <= 0 || deposit > 999999999999.99 - Double.valueOf(this.balance) || strDeposit.length() > 15) {
					deposit = Double.parseDouble(strDeposit);
					isDouble = false;
					if (deposit <= 0) {
						System.out.print("\nYou cannot deposit zero or a negative amount.\nHow much would you like to deposit?\n > ");
					} else if(strDeposit.length() > 15) {
						System.out.print("\nInput contained too many digits.\nHow much would you like to deposit?\n > ");
					} else {
						System.out.print("\nYou cannot hold more than $999,999,999,999.99 in your account.\nHow much would you like to deposit?\n > ");
					}
					strDeposit = in.nextLine();
				}
			} catch (NumberFormatException e) {
				System.out.print("\nNot a valid number.\nHow much would you like to deposit?\n > ");
				strDeposit = in.nextLine();
				isDouble = false;
			}
		}
		
		this.balance = new DecimalFormat("##.##").format(Double.valueOf(this.balance) + deposit);
		System.out.printf("\nYour new balance is $%.2f.\n", Double.valueOf(this.balance));
		return;
	}
	
	public void withdraw() {
		if (this.balance.equals("0.0") || this.balance.equals("0.00") || this.balance.equals("0")) {
			System.out.println("\nThere is no money in the account to withdraw.");
			return;
		}
		System.out.printf("\nYour current balance is $%.2f.\nHow much would you like to withdraw?\n > ", Double.valueOf(this.balance));
		String strWithdrawal = in.nextLine();
		boolean isDouble = false;
		double withdrawal = 0;
		
		while (!isDouble) {
			try {
				withdrawal = Double.parseDouble(strWithdrawal);
				isDouble = true;
				while (withdrawal <= 0 || withdrawal > Double.valueOf(this.balance) || strWithdrawal.length() > 15) {
					withdrawal = Double.parseDouble(strWithdrawal);
					isDouble = false;
					if (withdrawal <= 0) {
						System.out.print("\nYou cannot withdraw zero or a negative amount.\nHow much would you like to withdraw?\n > ");
					} else if(strWithdrawal.length() > 15) {
						System.out.print("\nInput contained too many digits.\nHow much would you like to withdraw?\n > ");
					} else {
						System.out.print("\nYou cannot withdraw more than your current balance.\nHow much would you like to withdraw?\n > ");
					}
					strWithdrawal = in.nextLine();
				}
			} catch (NumberFormatException e) {
				System.out.print("\nNot a valid number.\nHow much would you like to withdraw?\n > ");
				strWithdrawal = in.nextLine();
				isDouble = false;
			}
		}
		
		this.balance = new DecimalFormat("##.##").format(Double.valueOf(this.balance) - withdrawal);
		System.out.printf("\nYour new balance is $%.2f.", Double.valueOf(this.balance));
		return;
	}
	
	public void transfer() {
		if (this.balance.equals("0.0") || this.balance.equals("0.00") || this.balance.equals("0")) {
			System.out.println("\nThere is no money in the account to transfer.");
			return;
		}
		
		System.out.printf("\nYour current balance is $%.2f.\nHow much money would you like to transfer?\n > ", Double.valueOf(this.balance));
		String strTransferral = in.nextLine();
		boolean isDouble = false;
		double transferral = 0;
		
		while (!isDouble) {
			try {
				transferral = Double.parseDouble(strTransferral);
				isDouble = true;
				while (transferral <= 0 || transferral > Double.valueOf(this.balance) || strTransferral.length() > 15) {
					transferral = Double.parseDouble(strTransferral);
					isDouble = false;
					if (transferral <= 0) {
						System.out.print("\nYou cannot transfer zero or a negative amount.\nHow much would you like to transfer?\n > ");
					} else if(strTransferral.length() > 15) {
						System.out.print("\nInput contained too many digits.\nHow much would you like to transfer?\n > ");
					} else {
						System.out.print("\nYou cannot transfer more than your current balance.\nHow much would you like to transfer?\n > ");
					}
					strTransferral = in.nextLine();
				}
			} catch (NumberFormatException e) {
				System.out.print("\nNot a valid number.\nHow much would you like to transfer?\n > ");
				strTransferral = in.nextLine();
				isDouble = false;
			}
		}
		
		System.out.print("\nEnter the account number that you would like the money to be transferred to.\n > ");
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
		
		String newUserBalance = new DecimalFormat("##.##").format(Double.valueOf(db.findField(db.read("Balance", true), Integer.valueOf(newAccountNum))) + transferral);
		User fakeUser = new User("name", "name", "pin", 12345678, 555, "address", "city", "st", "zip");
		BankAccount fakeAccount = new BankAccount(Integer.valueOf(newAccountNum), fakeUser, newUserBalance);
		if (Double.valueOf(db.findField(db.read("Balance", true), Integer.valueOf(newAccountNum))) + transferral > 999999999999.99) {
			System.out.println("\nThe recipent's account cannot hold more than $999,999,999,999.99.\nTransfer failed.");
			return;
		}
		db.append("Balance", fakeUser, fakeAccount);
		this.balance = new DecimalFormat("##.##").format(Double.valueOf(this.balance) - transferral);
		
		System.out.println("\nSuccessfully transferred.");
		
		return;
	}
	
	public int getAccountNum() {
		return this.accountNum;
	}
	
	public String getBalance() {
		return this.balance;
	}
}
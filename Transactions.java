package com.company;

import java.util.Scanner;

public class Transactions {

        //method to deposit money
    public static void deposit(String accountNumber) {
        Scanner scan = new Scanner(System.in);
        double currentBalance;
        double input;
        double newBalance;


        System.out.println("*************************************");
        currentBalance = FileHandling.checkBalance(accountNumber);
        System.out.println("Current balance: " + currentBalance);
        System.out.printf("Enter deposit amount: ");
        input = scan.nextDouble();
        newBalance = input + currentBalance;
        FileHandling.recordTransaction(accountNumber, "deposit +", input, newBalance);
        System.out.println("New balance: " + newBalance);


    }
    //method to withdraw money
    public static void withdraw(String accountNumber) {
        Scanner scan = new Scanner(System.in);
        double currentBalance;
        double input = 0;
        double newBalance;
        boolean enough = false;
        System.out.println("*************************************");
        currentBalance = FileHandling.checkBalance(accountNumber);
        System.out.println("Current balance: " + currentBalance);
        while (enough == false) {

            System.out.printf("Enter withdrawal amount: ");
            input = scan.nextDouble();
            if (currentBalance < input) {
                System.out.println("Not enough money in the account.");
            } else {
                enough = true;
            }
        }
        newBalance = currentBalance - input;
        FileHandling.recordTransaction(accountNumber, "withdraw -", input, newBalance);
        System.out.println("New balance: " + newBalance);

    }
    //method to transfer money between accounts
    public static void transfer(String accountNumber) {
        Scanner scan = new Scanner(System.in);
        double toTransfer = 0;
        String toAccountNumber = null;
        double fromBalance;
        boolean toAccountValid = false;
        boolean enoughToTransfer = false;
        double toNewBalance;

        //getting balance for from account
        fromBalance = FileHandling.checkBalance(accountNumber);
        System.out.println("Current balance: " + fromBalance);
        while (toAccountValid == false) {
            Scanner myReader = new Scanner(System.in);
            System.out.printf("Account number you wish to transfer money to: ");
            toAccountNumber = myReader.nextLine();
            toAccountValid = FileHandling.accountNumberSearch(toAccountNumber);
        }
        toNewBalance = FileHandling.checkBalance(toAccountNumber);


        //Ask for amount of money to be transfered
        while (enoughToTransfer == false) {
            System.out.printf("Enter amount to transfer: ");
            toTransfer = scan.nextDouble();
            if (fromBalance < toTransfer) {
                System.out.println("Insufficent Funds.");
            } else {
                enoughToTransfer = true;
            }
        }
        toNewBalance = toNewBalance + toTransfer;
        fromBalance = fromBalance - toTransfer;

        //FileHandling.recordTransaction toAccount
        FileHandling.recordTransaction(toAccountNumber, "Transfer +", toTransfer, toNewBalance);
        //FileHandling.recordTransaction fromAccount
        FileHandling.recordTransaction(accountNumber, "Transfer -", toTransfer, fromBalance);
        //update balance level
        System.out.println("Your balance is now: " + fromBalance);


    }
    //returns account balance
    public static void balance(String accountNumber) {
        double currentBalance;
        currentBalance = FileHandling.checkBalance(accountNumber);
        System.out.println("Current balance: " + currentBalance);
    }
    //method to change the customer's password
    public static void changePassword(String accountNumber) {
        Scanner myReader = new Scanner(System.in);
        String oldPassword = null;
        String newPassword1 = null;
        String newPassword2;
        boolean passwordMatch = false;
        boolean passwordValid = false;

        while (passwordValid == false) {
            System.out.printf("Enter your current password: ");
            oldPassword = myReader.nextLine();
            passwordValid = FileHandling.checkPassword(accountNumber, oldPassword);
        }

        while(passwordMatch == false) {
            System.out.printf("Enter new password: ");
            newPassword1 = myReader.nextLine();
            System.out.printf("Reenter new password: ");
            newPassword2 = myReader.nextLine();
            if(newPassword1.equals(newPassword2)) {
                passwordMatch = true;
            }
        }

        FileHandling.replacePassword(accountNumber, oldPassword, newPassword1);

    }
    //prints out customer's transaction history
    public static void transactionHistory(String accountNumber)    {

        System.out.println("Transaction history for account: " +accountNumber);
        System.out.println("*************************************");
        FileHandling.history(accountNumber);
    }
}

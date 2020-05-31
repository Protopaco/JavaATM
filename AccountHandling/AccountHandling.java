package com.company;

import java.util.Random;
import java.util.Scanner;

public class AccountHandling {

    //user interface for creating a new account, asking for customer name
    //password, and starting balance.  Then calls FileHandling.AccountCreated()
    //to create the account file
    public static boolean create()  {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        String newHolderFName;
        String newHolderLName;
        String newUsername = null;
        String newPassword1 = null;
        String newPassword2;
        String newAccountNumber = null;

        boolean uniqueUsername = false;
        boolean samePassword = false;
        boolean accountCreated = false;
        boolean accountNumLength = false;
        int attemptCounter = 0;


        System.out.println("------------------------------------------");
        System.out.println("Create a new account");
        System.out.println("------------------------------------------");

        //Collects customers real name
        System.out.printf("Enter Account Holder's First Name: ");
        newHolderFName = scan.nextLine();
        System.out.printf("Enter Account Holder's Last Name: ");
        newHolderLName = scan.nextLine();


        //Collects Username and makes sure it doesn't currently exists
        while(uniqueUsername == false)  {
            System.out.println("Username must be 4-9 characters");
            System.out.printf("Enter Username: ");
            newUsername = scan.nextLine();
            if(newUsername.length()<3 || newUsername.length()>9) {
                System.out.println("Username is not 4-9 characters");
            } else {
                if (FileHandling.existingUser(newUsername)) {
                    System.out.println("Username already exists, please try again.");

                } else {
                    uniqueUsername = true;
                }
            }
        }

        //creates a password, and makes sure the passwords match
        //we would like to add a section to this so the input is not "echoed"
        while (samePassword == false && attemptCounter < 4) {

            attemptCounter++;
            System.out.printf("Enter Password:  ");
            newPassword1 = scan.nextLine();
            System.out.printf("Re-enter Password:  ");
            newPassword2 = scan.nextLine();
            if (newPassword1.equals(newPassword2)) {
                samePassword = true;
            } else {
                System.out.println("Passwords don't match, please try again");
                attemptCounter = 0;
            }
        }

        //asks for starting balance
        System.out.printf("Enter starting balance:  ");
        double startingBalance = scan.nextDouble();

        //creates a random account number
       while(accountNumLength==false) {


           double actNum = rand.nextDouble();
           int actNumber = (int) (actNum * 10000000);
           newAccountNumber = Integer.toString(actNumber);
           if(newAccountNumber.length() == 7) {
               accountNumLength=true;
           }
       }

        accountCreated = FileHandling.createAccountFile(newHolderFName, newHolderLName, newUsername, newPassword1, startingBalance, newAccountNumber);




        if (accountCreated) {
            System.out.println("Account successfully created!");
            System.out.println("Your account number is: " + newAccountNumber);
        }

        return accountCreated;
    }

    //user interface for account sign in
    //asks for username, verifies it and returns account number
    //asks for password, verifies it and returns account number or null
    public static void signIn() {
        boolean exit = false;
        boolean validUname;
        String accountNumber;
        String enteredUsername;
        String enteredPassword;
        Scanner scan = new Scanner(System.in);

        while(exit==false) {

            //Signin Display
            System.out.println("*************************************");
            System.out.printf("Enter your username: ");
            //System.out.println("*************************************");
            enteredUsername = scan.nextLine();
            validUname = FileHandling.existingUser(enteredUsername);
            if(validUname)  {
                System.out.printf("Enter your password:  ");
                enteredPassword = scan.nextLine();
                accountNumber = FileHandling.checkUnamePassword(enteredUsername, enteredPassword);
                if(accountNumber != null) {
                    FileHandling.recordSignIN(accountNumber);
                    Menu.transactions(accountNumber);
                    exit = true;
                }
            }


        }

    }
}

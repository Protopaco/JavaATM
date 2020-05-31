package com.company;

import java.util.Scanner;

public class Menu {

    //user interface for main menu screen
    //directs program to what method is being asked for
    public static void mainMenu()   {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while (exit == false)   {
            //Main menu text
            System.out.println(System.lineSeparator());
            System.out.println("*************************************");
            System.out.println("Welcome to the Bank of Paul & Flo!");
            System.out.println("est. 2020");
            System.out.println("Member FDIC");
            System.out.println("*************************************");
            System.out.println("Main Menu:");
            System.out.println("c - Create Account");
            System.out.println("s - Sign In");
            System.out.println("x - Exit");
            System.out.println("*************************************");
            System.out.printf("> ");
            String input = scan.nextLine();

            //input directs to other operations
            if(input.equals("c") || input.equals("C")) {
                AccountHandling.create();
            } else if (input.equals("s") || input.equals("S"))    {
                AccountHandling.signIn();
            } else if (input.equals("x") || input.equals("X"))    {
                    exit = true;
            } else  {
                System.out.println("Invalid entry, please try again");
            }
        }
    }

    //once customer has signed in, this provides their possible actions
    //most lead to Transactions class
    public static void transactions(String accountNumber){
        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while(exit == false) {
            System.out.println(System.lineSeparator());
            System.out.println("*************************************");
            System.out.println("Welcome to the Bank of Paul & Flo!");
            System.out.println("est. 2020");
            System.out.println("Member FDIC");
            System.out.println("*************************************");
            System.out.println("Transactions:");
            System.out.println("d - Deposit");
            System.out.println("w - Withdraw");
            System.out.println("t - Transfer");
            System.out.println("b - Balance");
            System.out.println("c - Change Password");
            System.out.println("h - Transaction History");
            System.out.println("x - Exit");
            System.out.println("*************************************");
            System.out.printf("> ");
            String input = scan.nextLine();
            //System.out.println(input);

            //launching methods depending on user input
            if (input.equals("d") || input.equals("D")) {
                Transactions.deposit(accountNumber);
            } else if (input.equals("w") || input.equals("W")) {
                Transactions.withdraw(accountNumber);
            } else if (input.equals("t") || input.equals("T")) {
                Transactions.transfer(accountNumber);
            } else if (input.equals("b") || input.equals("B")) {
                Transactions.balance(accountNumber);
            } else if (input.equals("c") || input.equals("C")) {
                Transactions.changePassword(accountNumber);
            }else if (input.equals("h") || input.equals("H"))   {
                Transactions.transactionHistory(accountNumber);
            } else if (input.equals("x") || input.equals("X")) {
                exit = true;
            }
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to the main menu!");

    }
}

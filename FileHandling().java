package com.company;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class FileHandling {

    static String fileFolder = "c:\\ATM\\";
    static String directoryName = "Directory.txt";
    static String bootHistory = "SIHistory.txt";

    //creates/checks existence of directory when program boots up
    //directory includes account number and username of each customer
    public static boolean createDirectory() {
        boolean success;
        try {
            File directory = new File(fileFolder + directoryName);
            if (directory.createNewFile())  {
                System.out.println("File created: " + directory.getName());
                success = true;
            } else {
                System.out.println("Directory already exists.");
                success = true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred in directory creation.");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    //creates a log of every customer sign in with time stamp
    public static boolean createSIHistory()   {

        boolean success;
        try {
            File myObj = new File(fileFolder + bootHistory);
            if (myObj.createNewFile())  {
                System.out.println("Sign In History created: " + myObj.getName());
                success = true;
            } else {
                System.out.println("Sign In History exists.");
                success = true;
            }
        } catch (IOException e) {
            success = false;
        }
        return success;
    }

    //logs each instance of a customer sign in, with account number and local time
    public static void recordSignIN(String accountNumber)   {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String tTime = time.format(formatter);

        try {
            FileWriter myWriter = new  FileWriter(fileFolder + bootHistory, true);
            myWriter.write(accountNumber + " " +tTime + System.lineSeparator());
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //scans directory file for usernames
    public static boolean existingUser(String userName)    {
        boolean redundant = false;
        char[] nBuffer = new char[9];
        int stringLength = 0;
        String uname = "";
        int accountNumber = 1234567;
        try {
            File myObj = new File(fileFolder + directoryName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine() && redundant == false)  {
                String test = Encrypt.decrypt(myReader.nextLine(), accountNumber);
                stringLength = test.length();
                int f = 0;
                for(int i = 7; i < stringLength; i++)  {
                    nBuffer[f] = test.charAt(i);
                    f++;
                }
                uname = uname.copyValueOf(nBuffer,0,f);

                if(userName.equalsIgnoreCase(uname))    {
                    redundant = true;
                }
            }
            myReader.close();
        }   catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
        return redundant;
    }
    //creates file for each customer
    //file includes customer name, password, starting balance, and transaction history
    public static boolean createAccountFile(String fName, String lName, String uName, String password, double balance, String accountNumber) {
        boolean success;
        int dAccountNumber = 1234567;

        try {
            //creates new file for the account, named after account number
            FileWriter myWriter = new FileWriter(fileFolder + accountNumber);
            myWriter.write(Encrypt.encrypt(accountNumber, Integer.valueOf(accountNumber)) + System.lineSeparator());
            myWriter.write(Encrypt.encrypt(fName, Integer.valueOf(accountNumber)) + " ");
            myWriter.write(Encrypt.encrypt(lName, Integer.valueOf(accountNumber)) + System.lineSeparator());
            myWriter.write(Encrypt.encrypt(password, Integer.valueOf(accountNumber)) + System.lineSeparator());
            myWriter.write("******" + System.lineSeparator());
            myWriter.flush();
            myWriter.close();
            recordTransaction(accountNumber, "Account Creation +", balance, balance);


            //adds this account to the directory
            FileWriter writeDirectory = new FileWriter(fileFolder + directoryName, true);
            writeDirectory.write(Encrypt.encrypt(accountNumber +uName, dAccountNumber)  + System.lineSeparator());
            writeDirectory.flush();
            writeDirectory.close();

            success = true;
        } catch (IOException e) {
            System.out.println("An error occurred with creating account file");
            e.printStackTrace();
            success = false;
        }
    return success;
    }
    //verifies username and password during sign in
    public static String checkUnamePassword(String enteredUname, String enteredPassword)   {
        boolean match = false;
        boolean continueSearch = true;
        String accountNumber = "1234567";
        String currentPassword = null;
        char[] uHolder = new char[9];
        char[] nHolder = new char[7];
        String aHolder = "";
        int stringLength = 0;
        try {
            File myObj = new File(fileFolder + directoryName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine() && continueSearch == true)  {
                String buffer = Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber));
                stringLength = buffer.length();
                stringLength -= 7;
                int f = 7;
                for(int i = 0; i < stringLength; i++) {
                    uHolder[i] = buffer.charAt(f);
                   f++;
                }
                aHolder = aHolder.copyValueOf(uHolder,0, stringLength);
                if(enteredUname.equals(aHolder))    {
                    for(int i = 0; i < 7; i++) {
                        nHolder[i] = buffer.charAt(i);
                    }
                    accountNumber = accountNumber.copyValueOf(nHolder, 0, 7);
                    continueSearch = false;
                }
            }
            myReader.close();
        }   catch (FileNotFoundException e) {
            System.out.println("An error occurred searching for username & login in directory.");
            e.printStackTrace();
        }
        try {
            File myObj2 = new File(fileFolder + accountNumber);
            Scanner myReader = new Scanner(myObj2);
            for(int i = 0; i < 3; i++){
                currentPassword = Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber));
            }
            if(!currentPassword.equals(enteredPassword)) {
                accountNumber = null;
            }
            }catch (FileNotFoundException e)   {
            System.out.println("Could not find account file.");
            //e.printStackTrace();

             }
        return accountNumber;
    }

    //returns balance in customer file
    public static double checkBalance(String accountNumber)   {
        double currentBalance=0;
        String buffer = null;
        try {
            File myObj = new File(fileFolder + accountNumber);
            Scanner scan = new Scanner(myObj);
            while(scan.hasNextLine()){
                buffer = Encrypt.decrypt(scan.nextLine(), Integer.valueOf(accountNumber));
            }
        } catch (FileNotFoundException e)  {
            System.out.println("Could not find account file.");
            e.printStackTrace();
        }
        currentBalance = Double.valueOf(buffer);
    return currentBalance;
    }

    //logs each transaction in customer file, including new balance
    public static void recordTransaction(String accountNumber, String transactionType, double transactionAmount, double newBalance)    {
            String tAmount = Double.toString(transactionAmount);
            String finalBalance = Double.toString(newBalance);
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String tTime = time.format(formatter);

        try {
            FileWriter myWriter = new FileWriter(fileFolder + accountNumber, true);
            myWriter.write(Encrypt.encrypt("*** ", Integer.valueOf(accountNumber)) +System.lineSeparator());
            myWriter.write(Encrypt.encrypt(tTime, Integer.valueOf(accountNumber)) +System.lineSeparator());
            myWriter.write(Encrypt.encrypt(transactionType +transactionAmount, Integer.valueOf(accountNumber)) +System.lineSeparator());
            myWriter.write(Encrypt.encrypt(finalBalance, Integer.valueOf(accountNumber)) +System.lineSeparator());
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("error!");
            e.printStackTrace();
        }


    }

    //searches for account files during transfer process
    public static boolean accountNumberSearch(String accountNumber) {
        boolean accountFound = false;
        String buffer;

        try {
            File myObj = new File(fileFolder + accountNumber);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine() && accountFound == false){
                buffer = Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber));
                if(buffer.equals(accountNumber))    {
                    accountFound = true;
                }
            }
        } catch (FileNotFoundException e)  {
            System.out.println("Could not find account file.");
        }
        return accountFound;

    }
    //verifies password during signin process
    public static boolean checkPassword(String accountNumber, String oldPassword) {
        boolean passwordValid = false;
        String buffer=null;

        try {
            File myObj = new File (fileFolder + accountNumber);
            Scanner myReader = new Scanner(myObj);
            for(int i = 0; i < 3; i++) {
                buffer = Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber));
            }
            passwordValid = buffer.equals(oldPassword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return passwordValid;
    }

    //replaces password in account file
    //reads entire file in arraylist, replaces
    public static void replacePassword(String accountNumber, String oldPassword, String newPassword) {
        try{
            File myObj = new File(fileFolder + accountNumber);
            Scanner myReader = new Scanner(myObj);
            StringBuffer buffer = new StringBuffer();
            ArrayList<String> testBuffer = new ArrayList<String>();
            String replacer;
            boolean done = false;

            int counter = 0;
            while(myReader.hasNextLine())   {
                testBuffer.add(Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber)));
                counter++;

            }
            for(int i = 0; i < counter && done == false; i++)    {
                replacer = testBuffer.get(i);
                replacer.replaceAll(oldPassword, newPassword);
                if(replacer.equals(oldPassword)) {
                    replacer = newPassword;
                    done = true;
                }
                testBuffer.set(i, replacer);

            }
            for(int i = 0; i < counter; i++)    {
                buffer.append(Encrypt.encrypt(testBuffer.get(i), Integer.valueOf(accountNumber)) + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            FileWriter writer = new FileWriter(myObj);
            writer.append(fileContents);
            writer.flush();
            myReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Password change successful.");
    }

    //prints to the screen the transaction history for the given customer
    public static void history(String accountNumber)   {

        try {
            File myObj = new File(fileFolder + accountNumber);
            Scanner myReader = new Scanner(myObj);
            String buffer;
            while(myReader.hasNextLine())   {
                buffer = myReader.nextLine();
                if(buffer.equals("******")) {
                    while(myReader.hasNextLine())   {
                        System.out.println(Encrypt.decrypt(myReader.nextLine(), Integer.valueOf(accountNumber)));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}





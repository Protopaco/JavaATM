package com.company;

public class Main {

    public static void main(String[] args) {
        //Startup operations
        boolean directoryCheck = FileHandling.createDirectory();
        boolean bootHistoryCheck = FileHandling.createSIHistory();

        Menu.mainMenu();


    }
}

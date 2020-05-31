# JavaATM


This is my first independent project since finishing Codecademy's Intro to Java.

It is a simple ATM program that allows the user to create an account file, deposit, withdraw, transfer funds between 
customers, change their password, and retrieve account history.

Account information is stored in separate files that include password, transaction history as well as current balance.

All information account files is "encrypted" using a basic replacement cypher, using the account number as a key and 
converting them into a byte array. I'm sure it wouldn't keep real hackers at bay, but I wanted to prove the concept of 
unique keys for each user, and making the files difficult to read by a casual reader.

Features that might be fun adding in the future: - back end for bank manager to manipulate customer files, and retrieve 
information such as number of customers, bank capitalization, perhaps even incorporating a credit score aspect and a loans 
division - remove "echo" from passwords, so they won't display on screen - requiring more complicated passwords, including 
"must contain special characters" etc - allow customer to search for transactions based on date, amount, or a range of 
either - protect program from incorrect entry (text instead of numbers, etc)


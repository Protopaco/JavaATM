package com.company;

public class Encrypt {

    public static String encrypt(String data, int accountNumber) {
        byte[] input = data.getBytes();

        //creates encryption key
        int keyLength = String.valueOf(data).length();
        String[] protoKey = Integer.toString(accountNumber).split("");
        int[] key = new int[keyLength];
        int count = 0;
        //uses account number to create key the same length as the data
        //key is just account number repeating
        for(int i = 0; i < keyLength; i++) {
            if (i < 7) {
                key[i] = Integer.parseInt(protoKey[i]);
            } else if (count == 7) {
                count = 0;
            } else {
                key[i] = Integer.parseInt(protoKey[count]);
                count++;
            }
        }
        for(int i = 0; i < keyLength; i++)  {
        }
        //actual encryption, just adds key & data together in byte array
        for(int i = 0; i < data.length(); i++)    {
           if(key[i] % 2 == 0)  {
               input[i] += 1;
           } else {
               input[i] -= 1;
           }
        }

        String output = new String (input);


        return output;
    }

    public static String decrypt(String data, int accountNumber) {
        byte[] input = data.getBytes();

        //creates encryption key
        int keyLength = String.valueOf(data).length();
        String[] protoKey = Integer.toString(accountNumber).split("");
        int[] key = new int[keyLength];
        int count = 0;
        for(int i = 0; i < keyLength; i++)  {
            if(i<7) {
                key[i] = Integer.parseInt(protoKey[i]);
            } else if (count == 7){
                count=0;
            } else  {
                key[i] = Integer.parseInt(protoKey[count]);
                count++;
            }
        }

        for(int i = 0; i < keyLength; i++)  {
        }
        //undoes encryption by subtracting key from data in byte array
        for(int i = 0; i < data.length(); i++)    {
            if(key[i] % 2 == 0)  {
                input[i] -= 1;
            } else {
                input[i] += 1;
            }

        }

        String output = new String (input);


        return output;
    }
}

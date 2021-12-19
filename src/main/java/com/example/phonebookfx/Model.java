package com.example.phonebookfx;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class Model {
    private HashMap<String,String> pb;

    //TODO initialization from file
    public Model() {
        this.pb = new HashMap<>();

        File file = new File("C:\\Users\\assaf\\Desktop\\Projects\\PhoneBookFX\\src\\main\\resources\\com\\example\\phonebookfx\\contacts_text_file.txt");
        StringBuilder sb;
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                sb = new StringBuilder(scanner.nextLine());
                int index = sb.indexOf("-");
                String name = sb.substring(0,index-1);
                String number = sb.substring(index+1,sb.length());
                pb.put(name.trim(),number.trim());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }



    public void add(String name, String number){
        pb.put(name,number);
    }
    public void remove(String name){
        pb.remove(name);
    }
    public void edit(String oldName, String newName,String newNum){
        pb.remove(oldName);
        pb.put(newName,newNum);
    }
    public String[] getPbAsArray(){
        String[] keyset = pb.keySet().toArray(new String[0]);
        String[] values = pb.values().toArray(new String[0]);
        String[] arr = new String[pb.size()];
        Arrays.setAll(arr, i -> keyset[i] + " - " + values[i]);
        return arr;
    }

    public void WriteContactsToFile() throws IOException {
        File file = new File("C:\\Users\\assaf\\Desktop\\Projects\\PhoneBookFX\\src\\main\\resources\\com\\example\\phonebookfx\\contacts_text_file.txt");
        FileWriter writer = new FileWriter(file);
        PrintWriter pr = new PrintWriter(writer);
        String[] contacts = getPbAsArray();
        Arrays.stream(contacts).forEach(pr::println);
        pr.close();


    }

}

package CLI;


import java.io.IOException;
import java.util.Scanner;

import Dictionary.EnglishDictionary;
import Hashing.BatchSuceessFailure;

public class DictionaryCLI {

    private EnglishDictionary dictionary;
    private final Scanner scanner = new Scanner(System.in);

    public DictionaryCLI() throws IOException {
    }

    public void run(){

        System.out.print("Enter the type of the backend tree: (N or N2)");
        String hashType = scanner.nextLine();
        dictionary = new EnglishDictionary(hashType);
        menu();
    }

    public void menu() {
        System.out.println("Choose an operation:");
        System.out.println("1. Insert a string");
        System.out.println("2. Delete a string");
        System.out.println("3. Search for a string");
        System.out.println("4. Batch insert a list of strings");
        System.out.println("5. Batch delete a list of strings");
        System.out.println("6. Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String key, filePath;

        switch (choice) {
            case 1 -> {
                System.out.print("Enter a string to insert:");
                key = scanner.nextLine();
                if(dictionary.insert(key)){
                    System.out.println("Inserted: " + key);
                }else{
                    System.out.println("Key already exists");
                }

            }
            case 2 -> {
                System.out.print("Enter a string to delete:");
                key = scanner.nextLine();
                if(dictionary.delete(key)){
                    System.out.println("Deleted: " + key);
                }else{
                    System.out.println("Key already exists");
                }
            }
            case 3 -> {
                System.out.print("Enter a string to search:");
                key = scanner.nextLine();
                if (dictionary.search(key)) {
                    System.out.println(key + " exists in the dictionary.");
                } else {
                    System.out.println(key + " does not exist in the dictionary.");
                }
            }
            case 4 -> {
                System.out.print("Enter the file path for batch insert:");
                filePath = scanner.nextLine();
                try {
                    BatchSuceessFailure successFailure = dictionary.batchInsert(filePath);
                    System.out.println("Batch insert completed. Added "+successFailure.getSuccess() + " ---- Duplicates " + successFailure.getFailure());
                }
                catch (IOException ex){
                    System.out.println("Failed to load file");
                }

            }
            case 5 -> {
                System.out.print("Enter the file path for batch delete:");
                filePath = scanner.nextLine();
                try {
                    BatchSuceessFailure successFailure = dictionary.batchDelete(filePath);
                    System.out.println("Batch delete completed. Deleted : "+successFailure.getSuccess() + " ---- Non existing : " + successFailure.getFailure());
                }
                catch (IOException ex){
                    System.out.println("Failed to load file");
                }
            }
            case 6 -> {
                System.out.print("Exiting...");
                scanner.close();
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please choose a number between 1 and 6.");
        }
        menu();

    }
}
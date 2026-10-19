/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepartone;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePartOne {

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) {
        
        login register = new login();
        Scanner scan = new Scanner(System.in);      // ONE scanner for the whole program
        Messages msg = new Messages(scan);
        
        System.out.println("\n===== Register =====");
        
        System.out.println("Please enter username:");
        String name = scan.nextLine();
        
        System.out.println("Enter password:");
        String password = scan.nextLine();
        
        System.out.println("Enter cellphone number:");
        String cellphone = scan.nextLine();
        
        System.out.println(register.registerUser(name, password, cellphone));
        
        if(!register.registerStatus(name, password, cellphone)){
            return;
        }
        
        System.out.println("\n===== Login =====");
        
        System.out.println("Please enter first name:");
        String firstname = scan.nextLine();
        
        System.out.println("Please enter surname:");
        String lastName = scan.nextLine();
        
        // the user can only continue once the login is successful
        boolean loginResult = false;
        while(!loginResult){
            System.out.println("Enter username:");
            String loginUsername = scan.nextLine();
            
            System.out.println("Enter password:");
            String loginPassword = scan.nextLine();
            
            loginResult = register.loginUser(loginUsername, loginPassword);
            System.out.println(register.returnLoginStatus(loginResult, firstname, lastName));
        }
        
        // the user decides how many messages they want to enter
        int numberOfMessages = readInt(scan, "How many messages do you wish to enter?", 1);
        msg.setMessagesAllowed(numberOfMessages);
        
        int option;
        
        do{
            System.out.println("\n===== Select any of the options below =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Coming soon.");
            System.out.println("3) Quit");
            
            option = readInt(scan, "Enter your option:", 0);
            
            switch(option){
                case 1 -> msg.sendingmessages();
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> System.out.println("\nTotal messages sent: " + msg.returnTotalMessages()
                        + "\nGoodbye.");
                default -> System.out.println("Invalid option!!");
            }
            
        }while(option != 3);
        
    }
    
    // Reads a whole number and asks again until the input is valid
    private static int readInt(Scanner scan, String prompt, int min) {
        while (true) {
            System.out.println(prompt);
            String line = scan.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Please enter a valid whole number (" + min + " or more).");
        }
    }
        
    
}// end of class

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartone;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Messages {
    
      // Only messages that were actually SENT are kept in these lists
    // (position i in every list belongs to the same message).
    ArrayList<String>messageIds = new ArrayList<>();
    ArrayList<String>recipientNumber = new ArrayList<>();
    ArrayList<String>messages = new ArrayList<>();
    ArrayList<String>msgHash = new ArrayList<>();
    
    // Messages the user chose to "Store Message to send later"
    ArrayList<String>storedMessages = new ArrayList<>();
    
    Scanner input;
    Random random = new Random();
    
    int numMessageSent = 0;     // total messages sent
    int messagesAllowed = 0;    // how many messages the user said they want to enter
    int messagesEntered = 0;    // how many have been entered so far
    
    public Messages() {
        this(new Scanner(System.in));
    }
    
    // Use the SAME Scanner as the main class so no typed input is lost
    public Messages(Scanner input) {
        this.input = input;
    }
    
    // Called once when the application starts
    public void setMessagesAllowed(int count) {
        this.messagesAllowed = count;
        this.messagesEntered = 0;
    }
    
    public void sendingmessages() {
        
        if (messagesEntered >= messagesAllowed) {
            System.out.println("You have already entered the " + messagesAllowed
                    + " message(s) you set at the start.");
            return;
        }
        
        while (messagesEntered < messagesAllowed) {
            
            System.out.println("\nMESSAGE: " + (messagesEntered + 1) + " of " + messagesAllowed);
            
            String messageID = generateMessageID();
            System.out.println("Message ID generated: " + messageID);
            
            // ---- recipient (keep asking until valid) ----
            String recipient;
            while (true) {
                System.out.println("Enter recipient cell number (with international code e.g. +27718693002):");
                recipient = input.nextLine().trim();
                String recipientResult = checkRecipientCell(recipient);
                System.out.println(recipientResult);
                if (recipientResult.equals("Cell phone number successfully captured.")) {
                    break;
                }
            }
            
            // ---- message text (keep asking until valid) ----
            String messageText;
            while (true) {
                System.out.println("Enter your message (max 250 characters):");
                messageText = input.nextLine();
                
                if (messageText.trim().isEmpty()) {
                    System.out.println("Message cannot be empty.");
                } else if (messageText.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters.");
                    System.out.println(checkMessageLength(messageText));
                } else {
                    System.out.println(checkMessageLength(messageText));
                    break;
                }
            }
            
            // ---- send / disregard / store ----
            int choice = readChoice();
            String result = handleMessage(messageID, recipient, messageText, choice);
            System.out.println(result);
            
            if (choice == 1) {
                // show the full details of the message that was just sent
                System.out.println(messageDetails(messages.size() - 1));
            }
            
            if (choice == 2) {
                String press;
                do {
                    press = input.nextLine().trim();
                    if (!press.equals("0")) {
                        System.out.println("Press 0 to delete the message.");
                    }
                } while (!press.equals("0"));
                System.out.println("Message deleted.");
            }
            
            messagesEntered++;
        }
        
        System.out.println("\nTotal messages sent: " + returnTotalMessages());
   }
    
    // Carries out the choice: 1 = send, 2 = disregard, 3 = store
    public String handleMessage(String messageID, String recipient, String messageText, int choice) {
        
        switch (choice) {
            case 1 -> {
                String hash = createMessageHash(messageID, numMessageSent, messageText);
                messageIds.add(messageID);
                recipientNumber.add(recipient);
                messages.add(messageText);
                msgHash.add(hash);
                numMessageSent++;
                return sentMessage(1);
            }
            case 2 -> {
                return sentMessage(2);
            }
            case 3 -> {
                String hash = createMessageHash(messageID, numMessageSent, messageText);
                return storeMessage(messageID, hash, recipient, messageText);
            }
            default -> {
                return "Invalid option.";
            }
        }
    }
    
    private int readChoice() {
        while (true) {
            System.out.println("\nChoose what you want to do with this message:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.println("Enter your choice:");
            String line = input.nextLine().trim();
            if (line.equals("1") || line.equals("2") || line.equals("3")) {
                return Integer.parseInt(line);
            }
            System.out.println("Invalid option, please enter 1, 2 or 3.");
        }
    }
    
    public int returnTotalMessages(){
        return numMessageSent;
    }
    
    // Random ten-digit message ID
    public String generateMessageID(){
        
        String messageID;
        
        do{
            long number = 1000000000L + random.nextLong(9000000000L);
            
            messageID = String.valueOf(number);
        }while(messageIds.contains(messageID));
        
        return messageID;
    }
    
    // Message ID must not be more than ten characters
    public boolean checkMessageID(String messageID) {
        return messageID != null && !messageID.isEmpty() && messageID.length() <= 10;
    }
    
    // Message must not be more than 250 characters
    public String checkMessageLength(String messageText) {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }
    
    // Recipient must start with an international code (+)
    public String checkRecipientCell(String cellphone) {
        
        if (cellphone != null && cellphone.matches("^\\+[0-9]{10,12}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an "
                    + "international code, please correct the number and try again.";
        }
    }
    
    // Hash = first two digits of the ID : message number : FIRSTWORD + LASTWORD (all caps)
    public String createMessageHash(String messageID,int messageNumber,String messageText){
        
        String firstTwoNumbers = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        
        // remove punctuation so "tonight?" becomes "tonight"
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        
        String hash = firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord;
        
        return hash.toUpperCase();
    }
    
    // Text for the chosen option: 1 = send, 2 = disregard, 3 = store
    public String sentMessage(int choice){
        
        switch(choice){
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    // Stores a message (in an ArrayList) to send later
    public String storeMessage(String messageID, String hash, String recipient, String messageText) {
        storedMessages.add("Message ID: " + messageID
                + " | Message Hash: " + hash
                + " | Recipient: " + recipient
                + " | Message: " + messageText);
        return sentMessage(3);
    }
    
    // Message ID, Message Hash, Recipient, Message (in that order)
    private String messageDetails(int i) {
        return "\n=============================\n"
                + "Message ID: " + messageIds.get(i) + "\n"
                + "Message Hash: " + msgHash.get(i) + "\n"
                + "Recipient: " + recipientNumber.get(i) + "\n"
                + "Message: " + messages.get(i) + "\n"
                + "=============================";
    }
    
    // Returns all the messages sent while the program is running
    public String printMessages() {
        
        StringBuilder all = new StringBuilder();
        
        for (int i = 0; i < messages.size(); i++) {
            all.append(messageDetails(i)).append("\n");
        }
        
        return all.toString();
    }
}// end of the class message

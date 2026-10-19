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
        // TODO code application logic here
        login register = new login();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n===== Register =====");
        
        System.out.println("Please enter username:");
        String name = scan.nextLine();
        
        System.out.println("Enter password");
        String password = scan.nextLine();
        
        System.out.println("Enter cellphone number: ");
        String cellphone = scan.nextLine();
        
        System.out.println(register.registerUser(name, password, cellphone));
        
        if(!register.registerStatus(name, password, cellphone)){
            return;
        }
        
        System.out.println("\n===== login =====");
        
        System.out.println("Please enter username: ");
        String firstname = scan.nextLine();
        
        System.out.println("Please enter surname: ");
        String lastName = scan.nextLine();
        
        System.out.println("Enter username: ");
        String loginUsername = scan.nextLine();
        
        System.out.println("Enter password: ");
        String loginPassword = scan.nextLine();
        
        
        boolean loginResult = 
                register.loginUser(
                loginUsername,
                loginPassword);
        System.out.println(register.returnLoginStatus(loginResult, firstname, lastName));
        
        
        
    }// end of main
}// end of class

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartone;

/**
 *
 * @author Student
 */
public class login extends Registration{
    
    public boolean loginUser(String name, String password){
        if(name.equals(userName) && password.equals(passWord)){
            return true;
        }else{
            return false;
        }
    }
    
public String returnLoginStatus(boolean loginStatus, String firstname, String lastName){
    if(loginStatus){
        return "Welcome "+ firstname + " " + lastName + ", it is nice to see you";
    }else{
        return"Username or password is incorrect please try again.";
    }
  }
}

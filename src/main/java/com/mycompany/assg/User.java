/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;

import java.io.*;

public class User extends Auth {
    private final String AUTHFILE = config.getAuthFilePath();
    private String username;
    private String role;
    public User(String username, String role){
        this.username = username;
        this.role = role;
    }
    
    String validateUserDetails(String new_username, String new_password, String new_role){
        if (new_username.length() < 1){
            return "Username cannot be empty";
        }else if (new_password.length() < 5){
            return "Password must have a minimum length of 4";
        }else if (!new_role.equals("manager") && !new_role.equals("technician")){
            return "Invalid type of role";
        }
        return "OK";
    }
    
    String validatePassword(String new_password){
        if (new_password.length() < 5){
            return "Password must have a minimum length of 4";
        }
        return "OK";
    }
    
    String validateRole(String new_role){
        if (!new_role.equals("manager") && !new_role.equals("technician")){
            return "Invalid type of role";
        }
        return "OK";
    }
    
    void upUserList(String [][] newUserList){
        try {
            FileWriter auth_fw = new FileWriter(AUTHFILE);
            BufferedWriter auth_bw = new BufferedWriter(auth_fw);
            for (int i=0; i<newUserList.length; i++){
                auth_bw.write(newUserList[i][0]+","+newUserList[i][1]+","+newUserList[i][2]+"\n");
            }
            auth_bw.close();
            auth_fw.close();
        } catch (Exception e) {
            System.out.println("An error occurred, "+e);
        } 
    }
}
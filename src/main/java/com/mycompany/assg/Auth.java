/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;

import java.io.*;

public class Auth {
    Config config = new Config();
    private final String AUTHFILE = config.getAuthFilePath();
    private String username;
    private String password;
    public void setInput (String username, String password){
        this.username = username;
        this.password = password;
    }
   
    public String Login(){
        String [][] userList = getUserList();
        Boolean logged = false;
        String role = null;
        
        if (userList == null){
            System.out.print("No user list found");
        }else{
            for (int i=0; i<userList.length; i++){
                if (username.equals(userList[i][0]) && password.equals(userList[i][1])){
                    logged = true;
                    role = userList[i][2];
                    break;
                }
            }
            if (!logged){
                role=null;
            }
        }
        return role;
    }
    
    String[][] getUserList(){
        try {
            File file = new File(AUTHFILE);
            FileReader auth_fr = new FileReader(file);
            BufferedReader auth_br = new BufferedReader(auth_fr);
            
            String line = null;
            int count = 0, i=0;
            while ((line = auth_br.readLine()) != null){
                count++;
            }
            auth_br.close();
            auth_fr.close();
            String[][] usr_arr = new String[count][3];
            
            auth_fr = new FileReader(file);
            auth_br = new BufferedReader(auth_fr);
            while ((line = auth_br.readLine()) != null){
                String [] data = line.split(",");
                usr_arr[i][0] = data[0]; //username
                usr_arr[i][1] = data[1]; //password
                usr_arr[i][2] = data[2]; //role
                i++;
            }
            auth_br.close();
            auth_fr.close();
            return usr_arr;
                
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
            return null;
        } catch (IOException e){
            System.out.println("An IO error occurred.");
            return null;
        }
    }
}

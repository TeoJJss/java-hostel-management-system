/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;

import java.io.*;

public class Customer {
    Config config = new Config();
    private String c_name, room_no;
    private final String CUSTOMERFILE = config.getCustomerFilePath();
    private File c_file = new File(CUSTOMERFILE);
    
    Customer(){
        
    }
    
    Customer(String c_name, String room_no){
        this.c_name = c_name;
        this.room_no = room_no;
    }
    
    int registerCustomer(){
        int c_id = generateNewCustomerID(); // Customer ID
        
        try{
            if ((c_id) == -1){
                throw new Exception();
            }
            FileWriter c_fw = new FileWriter(c_file, true);
            BufferedWriter c_bw = new BufferedWriter(c_fw);
            c_bw.write(c_id + ","+c_name + ","+ room_no + "\n");
            c_bw.close();
            c_fw.close();
            return c_id;
        }catch (Exception e){
            return -1;
        }
    }
    
    int generateNewCustomerID(){
        
        try{
            FileReader c_fr = new FileReader(c_file);
            BufferedReader c_br = new BufferedReader(c_fr);
            int line = 1;
            while (c_br.readLine() != null){
                line++;
            }
            c_br.close();
            c_fr.close();
            return line;
        }catch(Exception e){
            return -1;
        }
    }
    
    String [][] getCustomerInfo(){
        try{
            FileReader c_fr = new FileReader(c_file);
            BufferedReader c_br = new BufferedReader(c_fr);
            int line = 0;
            while (c_br.readLine() != null){
                line++;
            }
            c_br.close();
            c_fr.close();
            
            String [][] customerList = new String[line][3];
            c_fr = new FileReader(c_file);
            c_br = new BufferedReader(c_fr);
            
            String row;
            int i = 0;
            while ((row=c_br.readLine()) != null){
                String [] list = row.split(",");
                customerList[i][0] = list[0]; // Customer ID
                customerList[i][1] = list[1]; // Customer Name
                customerList[i][2] = list[2]; // Room Number
                i++;
            }
            c_br.close();
            c_fr.close();
            return customerList;
        }catch (Exception e){
            return null;
        }
    }
    
    String [] getCustomerInfo(String customerID){
        try{
            String [][] customerList = getCustomerInfo();
            for (int j = 0; j<customerList.length; j++){
                if (customerList[j][0].equals(customerID)){
                    return customerList[j];
                }
            }
            throw new Exception("Invalid customer ID");
        }catch (Exception e){
            return null;
        }
    }
    
    Boolean validateCustomerID(String customerID){
        try{
            String [][] customerList = getCustomerInfo();
            for (int j = 0; j<customerList.length; j++){
                if (customerList[j][0].equals(customerID)){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return null;
        }
    }
}

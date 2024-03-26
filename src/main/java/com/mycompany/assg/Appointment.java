/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;
import java.io.*;

public class Appointment {
    private String desc;
    private String appointDateTime;
    private int c_id;
    Config config = new Config();
    private final String APPOINTMENTFILE = config.getAppointmentFilePath();
    
    private File appoint_file = new File(APPOINTMENTFILE);
    
    public Appointment(){
        
    }
    
   public Appointment(int c_id, String desc, String appointDateTime){
        this.desc = desc;
        this.c_id = c_id;
        this.appointDateTime = appointDateTime;
    }

    
    public String bookAppointment(){
        String appointID = generateNewAppointmentID();
        try{
            if (appointID == null){
                throw new Exception();
            }
            
            FileWriter appoint_fw = new FileWriter(appoint_file, true);
            BufferedWriter appoint_bw = new BufferedWriter(appoint_fw);
            appoint_bw.write(appointID + ","+ this.c_id + ","+ this.desc + "," + this.appointDateTime + "\n");
            appoint_bw.close();
            appoint_fw.close();
            return appointID;
        }catch (Exception e){
            return null;
        }
    }
    
    String generateNewAppointmentID(){
        try{
            // Validate if the customer ID is in record
            Customer customer = new Customer();
            String customerID = String.valueOf(this.c_id);
            if (!customer.validateCustomerID(customerID)){
                throw new Exception("Invalid customer ID");
            }
            
            // Generate new customer ID by new line number
            FileReader appoint_fr = new FileReader(appoint_file);
            BufferedReader appoint_br = new BufferedReader(appoint_fr);
            int line = 1;
            while (appoint_br.readLine() != null){
                line++;
            }
            appoint_br.close();
            appoint_fr.close();
            
            String newID = "A"+line;
            return newID;
        }catch(Exception e){
            return null;
        }
    }
    
    public String [][] getAppointmentList(){
        try{
            FileReader appoint_fr = new FileReader(appoint_file);
            BufferedReader appoint_br = new BufferedReader(appoint_fr);
            int line = 0;
            while (appoint_br.readLine() != null){
                line++;
            }
            appoint_br.close();
            appoint_fr.close();
            
            String [][] appointList = new String[line][4];
            appoint_fr = new FileReader(appoint_file);
            appoint_br = new BufferedReader(appoint_fr);
            
            String row;
            int i = 0;
            while ((row=appoint_br.readLine()) != null){
                String [] list = row.split(",");
                appointList[i][0] = list[0]; // Appointment ID
                appointList[i][1] = list[1]; // Customer ID
                appointList[i][2] = list[2]; // Description
                appointList[i][3] = list[3]; // Appointment datetime
                i++;
            }
            appoint_br.close();
            appoint_fr.close();
            return appointList;
            
        }catch(Exception e){
            return null;
        }
    }
    
    Boolean validateAppointID(String appointID){
        String [][] appointList = getAppointmentList();
        try{
            for (int i = 0; i<appointList.length; i++){
                if (appointList[i][0].equals(appointID)){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return null;
        }
    }
    
    String [] getAppointmentInfoByID(String appointID){
        String [][] appointList = getAppointmentList();
        try{
            for (int i = 0; i<appointList.length; i++){
                if (appointList[i][0].equals(appointID)){
                    return appointList[i];
                }
            }
            throw new Exception("Invalid appointment");
        }catch (Exception e){
            return null;
        }
    }
    
    void dltAppointment(String appointID) throws Exception{
        // Check if appointment ID is valid to be deleted
        if (!validateAppointID(appointID)){
            throw new Exception("Invalid appointment ID");
        }
        
        // Get appointment list
        String [][] appointList = getAppointmentList();
        int newLength = appointList.length-1;
        String [][] newAppointList = new String[newLength][4];
        int j = 0;
        
        for (int i=0; i<appointList.length; i++){
            if (!appointList[i][0].equals(appointID)){ // Check appointment ID
                newAppointList[j][0] = appointList[i][0];
                newAppointList[j][1] = appointList[i][1];
                newAppointList[j][2] = appointList[i][2];
                newAppointList[j][3] = appointList[i][3];
                j++;
            }
        }
        try{
            upAppointmentList(newAppointList);
        }catch (Exception e){
            throw new Exception ("An error occurs: "+e);
        }
        
    }
    
    void upAppointmentList(String [][] newAppointList) throws Exception{
        try {
            FileWriter appoint_fw = new FileWriter(appoint_file);
            BufferedWriter appoint_bw = new BufferedWriter(appoint_fw);
            for (int i=0; i<newAppointList.length; i++){
                appoint_bw.write(newAppointList[i][0]+","+newAppointList[i][1]+","+newAppointList[i][2]+","+newAppointList[i][3]+"\n");
            }
            appoint_bw.close();
            appoint_fw.close();
        } catch (Exception e) {
            throw new Exception(e);
        } 
    }
}

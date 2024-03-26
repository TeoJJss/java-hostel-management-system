/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;

import java.io.*;
import java.text.DecimalFormat;

public class Service extends Technician {
    Config config = new Config();
    private final String SERVICEFILE = config.getServiceFilePath();
    private String appointID; 
    private String feedback; 
    private String payment;
    private String serviceDate;
    
    private File service_file = new File(SERVICEFILE);
    
    Service(String technicianName){
        super(technicianName);
    }
    
    void addServiceRecord(String appointID, String feedback, Double payment) throws Exception{
        Appointment appointment = new Appointment();
        if (!appointment.validateAppointID(appointID)){
            throw new Exception("Invalid appointment ID");
        }
        
        String [][] pendingServiceList = getPendingServiceList();
        
        // Check if the appointment already serviced
        Boolean valid = false;
        for (int i = 0; i < pendingServiceList.length; i++){
            if (pendingServiceList[i][0] == null){
                continue;
            }
            if (pendingServiceList[i][0].equals(appointID)){
                valid=true;
                break;
            }
        }
        
        if (!valid){
            throw new Exception("The appointment has been serviced.");
        }
        
        // Get current date and time
        String datetimeNow = config.getCurrentTime();
        
        FileWriter service_fw = new FileWriter(service_file, true);
        BufferedWriter service_bw = new BufferedWriter(service_fw);
        DecimalFormat df = new DecimalFormat("#.00");
        String payment_str = df.format(payment);
        service_bw.write(appointID + "," + super.getTechnicianName() + "," + datetimeNow + "," + feedback + "," + payment_str + "\n");
        
        service_bw.close();
        service_fw.close();
        
        this.appointID = appointID;
        this.feedback = feedback;
        this.payment = payment_str;
        this.serviceDate = datetimeNow;
    }
    
    String [] getReceiptInfo(){
        
        // Read appointment info
        Appointment appointment = new Appointment();
        String [] appointmentInfo = appointment.getAppointmentInfoByID(appointID);
        String customerID = appointmentInfo[1]; //Customer ID
        String desc = appointmentInfo[2]; // Description
        
        // Read Customer Info
        Customer customer = new Customer();
        String [] customerInfo = customer.getCustomerInfo(customerID);
        String customerName = customerInfo[1]; // Customer Name
        String roomNumber = customerInfo[2]; // Room Number
        
        // Store all info for receipt in array
        String [] receiptInfo = {
            customerID, 
            customerName, 
            roomNumber, 
            this.appointID, 
            desc, 
            super.getTechnicianName(), 
            this.feedback, 
            this.payment,
            this.serviceDate
        };
        
        return receiptInfo;
    }
    
    String [][] getPendingServiceList(){
        // Get existing appointment list
        String [][] appointmentList = new Appointment().getAppointmentList();
        String [][] pendingAppointmentList = new String[appointmentList.length][6];
        Boolean done;
        String [] servicedList;
        
        // Get serviced appointment list
        try{
            FileReader service_fr = new FileReader(service_file);
            BufferedReader service_br = new BufferedReader(service_fr);
            int line = 0;
            while (service_br.readLine() != null){
                line++;
            }
            service_br.close();
            service_fr.close();
            
            servicedList = new String[line];
            service_fr = new FileReader(service_file);
            service_br = new BufferedReader(service_fr);
            
            String row;
            int i = 0;
            while ((row=service_br.readLine()) != null){
                String [] list = row.split(",");
                servicedList[i] = list[0]; // Appointment ID
                i++;
            }            
        }catch(Exception e){
            return null;
        }
        
        int ind=0;
        for(int k=0; k<appointmentList.length; k++){
            done = false;
            
            for (int p=0; p < servicedList.length; p++){
                if (appointmentList[k][0].equals(servicedList[p])){
                    done=true;
                    break;
                }
            }
            
            if (!done){
                pendingAppointmentList[ind][0] = appointmentList[k][0]; // Appointment ID
                pendingAppointmentList[ind][1] = appointmentList[k][1]; // Customer ID
                pendingAppointmentList[ind][2] = appointmentList[k][2]; // Description
                pendingAppointmentList[ind][3] = appointmentList[k][3]; // Appointment datetime
                
                // Get customer info
                String [] customerInfo = new Customer().getCustomerInfo(appointmentList[k][1]);
                pendingAppointmentList[ind][4] = customerInfo[1]; // Customer Name
                pendingAppointmentList[ind][5] = customerInfo[2]; // Room Number
                ind++;
            }
        }
        
        return pendingAppointmentList;
    }
}

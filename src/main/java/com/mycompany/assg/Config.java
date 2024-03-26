/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Config {
    private final String BASEDIR = "src/main/java/com/mycompany/assg/"; // Base directory, please change if needed
    
    private final String AUTHFILE = BASEDIR + "auth.txt"; // File for user details
    private final String CUSTOMERFILE = BASEDIR + "customer.txt"; // File for customer details
    private final String APPOINTMENTFILE = BASEDIR + "appointment.txt"; // File for appointments
    private final String SERVICEFILE = BASEDIR + "service.txt"; // File for service records
    
    Config(){
        
    }
    
    String getAuthFilePath(){
        return AUTHFILE;
    }
    
    String getCustomerFilePath(){
        return CUSTOMERFILE;
    }
    
    String getAppointmentFilePath(){
        return APPOINTMENTFILE;
    }
    
    String getServiceFilePath(){
        return SERVICEFILE;
    }
    
    String getCurrentTime(){
        // Get current date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        String datetimeNow = dtf.format(LocalDateTime.now());
        return datetimeNow;
    }
}

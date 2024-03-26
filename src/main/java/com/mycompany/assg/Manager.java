package com.mycompany.assg;

public class Manager extends User {
    
    Manager(String username){
        super(username, "manager");
    }
    
    String addEndUser(String new_username, String new_password, String new_role) throws Exception{
        String chk_status = super.validateUserDetails(new_username, new_password, new_role);
        if (!(chk_status).equals("OK")){
            throw new Exception(chk_status);
        }
        
        String [][] userList = super.getUserList();
        int newLength = userList.length+1;
        String [][] newUserList = new String[newLength][3];
           
        // Add existing user list
        int i;
        for (i=0; i<userList.length; i++){
            // Username must be unique
            newUserList[i][0] = userList[i][0];
            newUserList[i][1] = userList[i][1];
            newUserList[i][2] = userList[i][2];
            if (userList[i][0].equals(new_username)){ //Kill if duplicate username
                throw new Exception("Username already exists. Addition of new user failed!");
            }
        }
        
        // Add new user
        newUserList[i][0] = new_username;
        newUserList[i][1] = new_password;
        newUserList[i][2] = new_role;
        // Update file
        super.upUserList(newUserList);
        return "success";
    }
    
    void editEndUser(String target_username, String target_col, String new_value) throws Exception{
        String chk_status; int col_ind;
        switch (target_col) {
            case "username":
                throw new Exception("Username cannot be changed");
            case "password":
                col_ind=1;
                chk_status = super.validatePassword(new_value);
                break;
            case "role":
                col_ind=2;
                chk_status = super.validateRole(new_value);
                break;
            default:
                throw new Exception("Undefined error");
        }
        
        if (!(chk_status).equals("OK")){
            throw new Exception(chk_status);
        }
        
        String [][] userList = super.getUserList();
        boolean isEditted = false;
        
        for (int i=0; i<userList.length; i++){
            if (userList[i][0].equals(target_username)){
                userList[i][col_ind] = new_value;
                isEditted = true;
                break;
            }
        }
        if (!isEditted){
            throw new Exception("User not found!");
        }else{
            // Update file
            super.upUserList(userList);
        }
    }
    
    void dltEndUser(String dlt_username) throws Exception{
        String [][] userList = super.getUserList();
        int newLength = userList.length-1;
        String [][] newUserList = new String[newLength][3];
        int i, j=0;
        try{
            for (i=0; i<userList.length; i++){
                if (!userList[i][0].equals(dlt_username)){ // Skip target username
                    newUserList[j][0] = userList[i][0];
                    newUserList[j][1] = userList[i][1];
                    newUserList[j][2] = userList[i][2];
                    j++;
                }
            }
        }catch (Exception e){
            throw new Exception("The user does not exist. Deletion failed! ");
        }
        
        // Update file
        super.upUserList(newUserList);
    }
}
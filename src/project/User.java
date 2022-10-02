/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Rashique 
 *
 */

public class User {
 
        public String username;
        public String password;
        public int points;
        
        protected User(String username, String password, int points) {
            this.username = username;
            this.password = password;
            this.points = points;      
        }
    
        public String getUsername() {
            return this.username;
        }
 
        public void setUsername(String username) {
            this.username = username;
        }
 
        public String getPassword() {
            return this.password;
        }
 
        public void setPassword(String password) {
            this.password = password;
        }
        
        public int getPoints() {
            return this.points;
        }
 
        public void setPoints(int points) {
            this.points = points;
        }
 
        
    }
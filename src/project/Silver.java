/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
/**
 *
 * @author Rashique
 */
public abstract class Silver extends Status {   
    public void statusCheck() {
        System.out.println("Silver");
        getStatus();
    }
    
    public String getStatus() {
        return ("Silver");
    }
}
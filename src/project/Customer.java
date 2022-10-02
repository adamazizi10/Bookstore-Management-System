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
public class Customer {
        
        private Status customerStatus;
        protected String state;
        
        public Customer () {
            customerStatus = new Silver() {};
            this.state = "";
        }
        
        public void setStatus(Status s) {
            this.customerStatus = s;
        }
        
        public String getStatus() {
            return state;
        }
        
        public void statusCheck() {
            customerStatus.statusCheck();
            state = customerStatus.getStatus();
        }
}

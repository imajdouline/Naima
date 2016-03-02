/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

/**
 *
 * @author ilham
 */
public class Account {
    
    private String firstName;
    private String lastName;
    private String email;
    private String uuid;
    private String subscriptionStatus;
    private String editionCode;
    private String pricingDuration;

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }
    
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("firstName = ").append(this.getFirstName());
        sb.append(", lastName = ").append(this.getLastName());
        sb.append(", email = ").append(this.getEmail());
        sb.append(", subscriptionStatus = ").append(this.getSubscriptionStatus());
        sb.append(", editionCode = ").append(this.getEditionCode());
        sb.append(", uuid = ").append(this.getUuid());
        
        return sb.toString();
    }
    
}

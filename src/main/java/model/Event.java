/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import entity.Account;

/**
 *
 * @author ilham
 */
@XStreamAlias("root")
public class Event {
    
    private String type;
    private String partner;
    private String flag;
    private Marketplace marketplace;
    private Creator creator;
    private Payload payload;
    private String returnUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    public Account toAccount() {
        Account acc = new Account();
        if(this.getCreator() != null) {
           acc.setEmail(this.getCreator().getEmail());
            acc.setFirstName(this.getCreator().getFirstName());
            acc.setLastName(this.getCreator().getLastName());
            acc.setUuid(this.getCreator().getUuid()); 
        }
        if(this.getPayload() != null && this.getPayload().getOrder() !=null) {
            
            acc.setEditionCode(this.getPayload().getOrder().getEditionCode());
            acc.setPricingDuration(this.getPayload().getOrder().getPricingDuration());
        }
        acc.setSubscriptionStatus(this.getType());
        return acc;
    }
}

package com.example.svasthik_gymowner;

public class HistoryModelClass {
    String address,customerId,customerName,email,price,slot,targetGym,targetGymId,image,date;

    public HistoryModelClass(String address, String customerId, String customerName, String email, String price, String slot, String targetGym, String targetGymId,String image,String date) {
        this.address = address;
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.price = price;
        this.slot = slot;
        this.targetGym = targetGym;
        this.targetGymId = targetGymId;
        this.image=image;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getTargetGym() {
        return targetGym;
    }

    public void setTargetGym(String targetGym) {
        this.targetGym = targetGym;
    }

    public String getTargetGymId() {
        return targetGymId;
    }

    public void setTargetGymId(String targetGymId) {
        this.targetGymId = targetGymId;
    }
}

package com.example.svasthik_gymowner;

public class BookingDetailsCLass {
    String address,customerId,customerName,email,price,slot,targetGym,targetGymId,phone,otp,image,slotValue;

    public BookingDetailsCLass() {
    }

    public BookingDetailsCLass(String address, String customerId, String customerName, String email, String price, String slot, String targetGym, String targetGymId, String phone, String otp, String image,String slotValue) {
        this.address = address;
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.price = price;
        this.slot = slot;
        this.targetGym = targetGym;
        this.targetGymId = targetGymId;
        this.phone = phone;
        this.otp = otp;
        this.image = image;
        this.slotValue=slotValue;
    }

    public String getSlotValue() {
        return slotValue;
    }

    public void setSlotValue(String slotValue) {
        this.slotValue = slotValue;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

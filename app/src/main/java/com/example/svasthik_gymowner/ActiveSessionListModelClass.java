package com.example.svasthik_gymowner;

public class ActiveSessionListModelClass {

    String name,id,time,imageUrl,phone,otp,email,targetGym,address,price,image,slotValue;

    public ActiveSessionListModelClass(String name, String id, String time, String imageUrl, String phone, String otp, String email, String targetGym, String address, String price,String image,String slotValue) {
        this.name = name;
        this.id = id;
        this.time = time;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.otp = otp;
        this.email = email;
        this.targetGym = targetGym;
        this.address = address;
        this.price = price;
        this.image=image;
        this.slotValue=slotValue;
    }

    public String getSlotValue() {
        return slotValue;
    }

    public void setSlotValue(String slotValue) {
        this.slotValue = slotValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTargetGym() {
        return targetGym;
    }

    public void setTargetGym(String targetGym) {
        this.targetGym = targetGym;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

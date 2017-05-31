package auribises.com.visitorbook.Class;

import java.io.Serializable;

public class Teacher implements Serializable {

    int id;
    String name,phone,email,gender,address,purpose,date,time,room;

    public Teacher() {
    }

    public Teacher(int id, String name, String phone, String email, String gender, String address, String purpose, String date, String time, String room) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.purpose = purpose;
        this.date = date;
        this.time = time;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Details of Visitors\n" +
                "\nID is: " + id +
                "\nName is: " + name +
                "\nPhone is: " + phone +
                "\nEmail is: " + email +
                "\nGender is: " + gender +
                "\nAddress is: " + address +
                "\nPurpose is: " + purpose +
                "\nDate is: " + date +
                "\nTime is: " + time +
                "\nRoom is: " + room ;
    }
}


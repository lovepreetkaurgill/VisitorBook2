package auribises.com.visitorbook.Class;

import java.io.Serializable;

public class Vehicle implements Serializable {

    int id;
    String name,phone,email,gender,vehicle,vehiclenumber;


    public Vehicle() {

    }

    public Vehicle(int id, String name, String phone, String email, String gender, String vehicle, String vehiclenumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.vehicle = vehicle;
        this.vehiclenumber = vehiclenumber;

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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    @Override
    public String toString() {
        return "Details of Vehicles\n" +
                "\nID is: " + id +
                "\nName is: " + name +
                "\nPhone is: " + phone +
                "\nEmail is: " + email +
                "\nGender is: " + gender +
                "\nVehicle is: " + vehicle +
                "\nVehiclenumber is: " + vehiclenumber ;
    }

}






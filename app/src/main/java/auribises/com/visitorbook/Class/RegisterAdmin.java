package auribises.com.visitorbook.Class;

import java.io.Serializable;

public class RegisterAdmin implements Serializable{


    int id;
    String name,phone,email,birthdate,gender,address,qualification,experience,password;

    public RegisterAdmin() {
    }

    public RegisterAdmin(int id, String name, String phone, String email, String birthdate, String gender, String address, String qualification, String experience, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.qualification = qualification;
        this.experience = experience;
        this.password = password;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Details of Admin\n" +
                "\nID is: " + id +
                "\nName is: " + name +
                "\nPhone is: " + phone +
                "\nEmail is: " + email +
                "\nBirthdate is: " + birthdate +
                "\nGender is: " + gender +
                "\nAddress is: " + address +
                "\nQualification is: " + qualification +
                "\nExperience is: " + experience +
                "\nPassword is: " + password ;
    }
}

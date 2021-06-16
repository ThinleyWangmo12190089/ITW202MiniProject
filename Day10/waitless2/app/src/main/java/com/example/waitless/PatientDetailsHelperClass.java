package com.example.waitless;

public class PatientDetailsHelperClass {
    String name, age, cid, gender, Phone, symtoms, disease, date, time;

    public PatientDetailsHelperClass() {
    }

    public PatientDetailsHelperClass(String name, String age, String cid, String gender, String phone, String symtoms, String disease, String date, String time) {
        this.name = name;
        this.age = age;
        this.cid = cid;
        this.gender = gender;
        Phone = phone;
        this.symtoms = symtoms;
        this.disease = disease;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSymtoms() {
        return symtoms;
    }

    public void setSymtoms(String symtoms) {
        this.symtoms = symtoms;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
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
}

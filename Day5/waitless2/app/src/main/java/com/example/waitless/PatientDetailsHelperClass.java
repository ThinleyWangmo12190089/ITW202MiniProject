package com.example.waitless;

public class PatientDetailsHelperClass {
    String name, age, gender, Phone, symtoms, disease, dzongkhag;

    public PatientDetailsHelperClass() {
    }

    public PatientDetailsHelperClass(String name, String age, String gender, String phone, String symtoms, String disease, String dzongkhag) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        Phone = phone;
        this.symtoms = symtoms;
        this.disease = disease;
        this.dzongkhag = dzongkhag;
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

    public String getDzongkhag() {
        return dzongkhag;
    }

    public void setDzongkhag(String dzongkhag) {
        this.dzongkhag = dzongkhag;
    }
}

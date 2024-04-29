/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utslab.contactmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author VIVOBOOK PRO
 */
public class Contact {
    private final StringProperty id;
    private final StringProperty nama;
    private final StringProperty nim;
    private final StringProperty NoTelephone;
    private final StringProperty email;
    
    public Contact(String id ,String nama, String nim, String NoTelephone, String email){
        this.id = new SimpleStringProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.nim = new SimpleStringProperty(nim);
        this.NoTelephone = new SimpleStringProperty(NoTelephone);
        this.email = new SimpleStringProperty(email);
    }
    
    public StringProperty idProperty(){
        return id;
    }
    public StringProperty namaProperty(){
        return nama;
    }
    public StringProperty nimProperty(){
        return nim;
    }
    public StringProperty NoTelephoneProperty(){
        return NoTelephone;
    }
    public StringProperty emailProperty(){
        return email;
    }
    
    public String getId() {
        return id.get();
    }
    public String getNama() {
        return nama.get();
    }
    public String getNim() {
        return nim.get();
    }
    public String getNo() {
        return NoTelephone.get();
    }
    public String getEmail() {
        return email.get();
    }
    
    
    
}

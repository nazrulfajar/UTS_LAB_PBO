/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.utslab.contactmanager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author VIVOBOOK PRO
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colNama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        colNim.setCellValueFactory(cellData -> cellData.getValue().nimProperty());
        colNomor.setCellValueFactory(cellData -> cellData.getValue().NoTelephoneProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        
        fetchDataFromDatabase();
        
        TableContact.setItems(contactList);
        
    }    
    
    @FXML
    private TableView<Contact> TableContact;
    @FXML
    private TableColumn<Contact, String> colId;
    @FXML
    private TableColumn<Contact, String> colNama;
    @FXML
    private TableColumn<Contact, String> colNim;
    @FXML
    private TableColumn<Contact, String> colNomor;
    @FXML
    private TableColumn<Contact, String> colEmail;
    
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    
    private void fetchDataFromDatabase() {
    contactList.clear();

        String sql = "SELECT * FROM Contact";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nama = resultSet.getString("Nama");
                String nim = resultSet.getString("Nim");
                String NoTelephone = resultSet.getString("NoTelephone");
                String email = resultSet.getString("Email");
                contactList.add(new Contact(id, nama, nim, NoTelephone, email));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private TextField idTextField;
    @FXML
    private TextField namaTextField;
    @FXML
    private TextField nimTextField;
    @FXML
    private TextField noTextField;
    @FXML
    private TextField emailTextField;
    
    @FXML
    public void onAddButtonClick() {
        String Nama = namaTextField.getText();
        String Nim = nimTextField.getText();
        String NoTelephone = noTextField.getText();
        String Email = emailTextField.getText();

        if (Nama.isEmpty() || Nim.isEmpty() || NoTelephone.isEmpty() || Email.isEmpty()) {
            // Tampilkan alert warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Semua Fields harus di-isi");
            alert.showAndWait();
            return;
        }
            saveItemToDatabase(Nama, Nim, NoTelephone, Email);
            resetFields();
            fetchDataFromDatabase();
        
    }
    
    private void saveItemToDatabase(String Nama, String Nim, String NoTelephone, String Email) {

        String sql = "INSERT INTO contact (nama, nim, NoTelephone, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, Nama);
            statement.setString(2, Nim);
            statement.setString(3, NoTelephone);
            statement.setString(4, Email);

            statement.executeUpdate();
            System.out.println("Data berhasil disimpan ke dalam database.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void resetFields() {
        idTextField.clear();
        namaTextField.clear();
        nimTextField.clear();
        noTextField.clear();
        emailTextField.clear();
        TableContact.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void tampilkanKeTextField() {
        Contact selectedItem = TableContact.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            idTextField.setText(selectedItem.getId());
            namaTextField.setText(selectedItem.getNama());
            nimTextField.setText(selectedItem.getNim());
            noTextField.setText(selectedItem.getNo());
            emailTextField.setText(selectedItem.getEmail());
        } else {
            // Clear the form fields if no item is selected
            idTextField.clear();
            namaTextField.clear();
            nimTextField.clear();
            noTextField.clear();
            emailTextField.clear();
        }
    }
    
    private void updateItemToDatabase(String Nama, String Nim, String NoTelephone, String Email, String Id) {

        String sql = "update contact set nama = ?, nim = ?, NoTelephone = ?, email = ? where id = ?";

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, Nama);
            statement.setString(2, Nim);
            statement.setString(3, NoTelephone);
            statement.setString(4, Email);
            statement.setString(5, Id);

            statement.executeUpdate();
            System.out.println("Data berhasil diupdate ke dalam database.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @FXML
    public void EditButtonClick() {
        // Mendapatkan item yang dipilih dari tabel
        Contact selectedItem = TableContact.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Mendapatkan nilai baru dari field input
            String Nama = namaTextField.getText();
            String Nim = nimTextField.getText();
            String NoTelephone = noTextField.getText();
            String Email = emailTextField.getText();
            String Id = idTextField.getText();

            // Validasi input
            if (Nama.isEmpty() || Nim.isEmpty() || NoTelephone.isEmpty() || Email.isEmpty()) {
                // Tampilkan alert warning
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Semua Fields harus di-isi");
                alert.showAndWait();
                return;
            }

                // Memperbarui data di database
                updateItemToDatabase(Nama, Nim, NoTelephone, Email, Id);

                // Mengambil ulang data dari database
                fetchDataFromDatabase();
            // Membersihkan field input setelah melakukan edit
            resetFields();
        } else {
            // Tampilkan alert warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("On Edit");
            alert.setHeaderText(null);
            alert.setContentText("Pilih Baris yang akan diedit");
            alert.showAndWait();
        }
        
    }
    
    private void deleteItemFromDatabase(String Id) {

        String sql = "delete from contact where id = ?";

        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, Id);

            statement.executeUpdate();
            System.out.println("Data berhasil didelete dari database.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @FXML
    public void DeleteButtonClick() {
        // Mendapatkan item yang dipilih dari tabel
        Contact selectedItem = TableContact.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Mendapatkan nilai baru dari field input
            String Id = idTextField.getText();

                // Memperbarui data di database
                deleteItemFromDatabase(Id);

                // Mengambil ulang data dari database
                fetchDataFromDatabase();
            // Membersihkan field input setelah melakukan edit
            resetFields();
        } else {
            // Tampilkan alert warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("On Delete");
            alert.setHeaderText(null);
            alert.setContentText("Pilih Baris yang akan dihapus");
            alert.showAndWait();
        }
        
    }
    
}

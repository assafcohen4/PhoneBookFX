package com.example.phonebookfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.System.*;
import static java.util.Arrays.*;

public class Controller implements Initializable {


    public TextField current_name_field;
    @FXML
    public TextField current_number_field;
    @FXML
    public Label contact_removed_label;
    @FXML
    public Label contact_updated_label;
    @FXML
    public Label contact_added_label;
    @FXML
    private Button add_button;
    @FXML
    private ListView<String> contact_list;
    @FXML
    private Button remove_button;
    @FXML
    private TextField search_field;
    @FXML
    private Button update_button;

    Model m = new Model();



    @FXML
    void OnPressButton(ActionEvent event) throws IOException {

        if(event.getSource().equals(add_button)){
            m.add(current_name_field.getText(),current_number_field.getText());
            current_name_field.setText("");
            current_number_field.setText("");
            contact_list.getItems().clear();
            updateListView(search_field.getText());
            updateLabel("add");
            m.WriteContactsToFile();
        }
        if(event.getSource().equals(remove_button)){
            StringBuilder currentContactUntrimmed = new StringBuilder(contact_list.getSelectionModel().getSelectedItem());
            String currentContactTrimmed = trimContact(currentContactUntrimmed);
            m.remove(currentContactTrimmed);
            int currentContactIndex = contact_list.getSelectionModel().getSelectedIndex();
            //removing from listview
            contact_list.getItems().remove(currentContactIndex);

            current_name_field.setText("");
            current_number_field.setText("");
            search_field.setText("");
            updateLabel("remove");
            m.WriteContactsToFile();

        }

        if(event.getSource().equals(update_button)){
            StringBuilder currentContactUntrimmed = new StringBuilder(contact_list.getSelectionModel().getSelectedItem());
            String currentContactTrimmed = trimContact(currentContactUntrimmed);
            m.edit(currentContactTrimmed, current_name_field.getText(),current_number_field.getText());
            current_name_field.setText("");
            current_number_field.setText("");
            contact_list.getItems().clear();
            updateListView(search_field.getText());
            updateLabel("update");


            m.WriteContactsToFile();


        }
    }

    @FXML
    public void OnMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(contact_list)) {
            StringBuilder currentContactUntrimmed = new StringBuilder(contact_list.getSelectionModel().getSelectedItem());
            String currentContactTrimmed = trimContact(currentContactUntrimmed);
            String currentNumberTrimmed = trimNumber(currentContactUntrimmed);


            current_name_field.setText(currentContactTrimmed);
            current_number_field.setText(currentNumberTrimmed);
        }
            updateLabel("none");
    }

    @FXML
    void OnKeyTyped(KeyEvent event) {
        //if empty show all
        if(search_field.getText().equals("")) {
            String[] arr = m.getPbAsArray();
            stream(arr).forEach(s -> contact_list.getItems().add(s));
        }
        contact_list.getItems().clear();
        String currentlyTyped = search_field.getText();
        updateListView(currentlyTyped);
    }

    private String trimContact(StringBuilder currentContactUntrimmed) {
        int index = currentContactUntrimmed.indexOf("-");
        out.println(currentContactUntrimmed.substring(0,index-1));
        return currentContactUntrimmed.substring(0,index-1);
    }

    private String trimNumber(StringBuilder currentContactUntrimmed) {
        int index = currentContactUntrimmed.indexOf("-");
        out.println(currentContactUntrimmed.substring(0,index-1));
        return currentContactUntrimmed.substring(index+2,currentContactUntrimmed.length());
    }

    private void updateListView(String currentlyTyped) {
        String[] arr = m.getPbAsArray();
        for (String s : arr) {
            if (s.toLowerCase().contains(currentlyTyped.toLowerCase())) {
                contact_list.getItems().add(s);
            }
        }
    }

    private void updateLabel(String label){
        switch (label) {
            case "remove" -> {
                contact_removed_label.setText("Contact removed successfully");
                contact_updated_label.setText("");
                contact_added_label.setText("");
            }
            case "update" ->{
                contact_removed_label.setText("");
                contact_updated_label.setText("Contact updated successfully");
                contact_added_label.setText("");
            }
            case "add" -> {
                contact_removed_label.setText("");
                contact_updated_label.setText("");
                contact_added_label.setText("Contact added successfully");
            }
            case "none" -> {
                contact_removed_label.setText("");
                contact_updated_label.setText("");
                contact_added_label.setText("");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!Objects.isNull(contact_list)) {
            contact_list.getItems().addAll(m.getPbAsArray());
        }

    }

    //TODO read/write to file
}


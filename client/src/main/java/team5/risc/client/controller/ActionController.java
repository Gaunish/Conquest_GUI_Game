package team5.risc.client.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import team5.risc.client.Client;

public class ActionController {
    
    Client client;
    
    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
        
    }
}

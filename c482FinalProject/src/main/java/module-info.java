module main.c482finalproject {



   requires javafx.controls;
    requires javafx.fxml;


    exports main.c482finalproject.model;
    opens main.c482finalproject to javafx.fxml;

    exports main.c482finalproject.controller;
    opens main.c482finalproject.controller to javafx.fxml;

    exports main.c482finalproject;


}
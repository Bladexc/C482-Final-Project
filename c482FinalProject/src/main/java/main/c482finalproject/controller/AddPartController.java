package main.c482finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.c482finalproject.model.ErrorBuilder;
import main.c482finalproject.model.InHouse;
import main.c482finalproject.model.Inventory;
import main.c482finalproject.model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


/**
 * AddPartController is responsible for creating new parts utilizing user input and data validation to ensure parts are created correctly.
 *
 * @author Blake Heller
 *
 * FUTURE ENHANCEMENT:
 * The program should include additional fields that will contain potential discounts on parts.
 * Additionally, the part name field should stop users from entering small names, like 1 letter or 1 number names.
 *
 */
public class AddPartController implements Initializable {

    //declare Java FX scene and stage objects.
    Stage stage;
    Parent scene;

    //declare label and field objects.
    @FXML
    private Label partFlexLabel;

    @FXML
    private TextField partFlexText;

    @FXML
    private TextField partIdText;

    @FXML
    private TextField partInventoryText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partPriceText;

    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;


    //Event handler that will change the label depending on which radio button is selected.
    @FXML
    void handleRadioButtonAction(ActionEvent event) throws IOException {
        if (inhouseRBtn.isSelected()) {
            partFlexLabel.setText("Machine ID");
        }
        else if (outsourcedRBtn.isSelected()) {
            partFlexLabel.setText("Company Name");
        }
    }

    /**
     * RUNTIME ERROR:
     * NumberFormatException is the main error caught in my program as a result of attempting to parse an invalid double or integer value.
     *
     * The ErrorBuilder class utilizes these errors to provide a dialog box that is dynamically built to inform the user what fields must be corrected. If any other
     * error is thrown, it will be caught and displayed via a generic error message.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try { // try & catch block will attempt to parse the values in the text fields and will throw a NumberFormatException
            String name = partNameText.getText();
            if (name.isEmpty()) { // if the name field is empty, throw a new NumberFormatException so the ErrorBuilder Class can build an error dialog box.
                throw new NumberFormatException();
            }

            int stock = Integer.parseInt(partInventoryText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());

            if (inhouseRBtn.isSelected()) { // selector for building an inhouse part or an outsourced part
                int machineId = Integer.parseInt(partFlexText.getText());

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    // utilizing my ErrorBuilder Class, this checks for valid values for the stock, min, and max before attempting to
                    // make a new part. Both checkMinMax and checkStockMinMax will stop execution and display an error dialog.

                    Inventory.addPart(new InHouse(name, price, stock, min, max, machineId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }

            if (outsourcedRBtn.isSelected()) { // selector for building an inhouse part or an outsourced part
                String company = partFlexText.getText();

                if (company.isEmpty()) { // if the name field is empty, throw a new NumberFormatException so the ErrorBuilder Class can build an error dialog box.
                    throw new NumberFormatException();
                }

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    // utilizing my ErrorBuilder Class, this checks for valid values for the stock, min, and max before attempting to
                    // make a new part. Both checkMinMax and checkStockMinMax will stop execution and display an error dialog.

                    Inventory.addPart(new Outsourced(name, price, stock, min, max, company));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch (NumberFormatException error) {
            // after a NumberFormatException has been caught anywhere when the parse method is invoked,
            // we check each field and build an error message using the ErrorBuilder Class.
            ErrorBuilder.checkNameField(partNameText.getText());
            ErrorBuilder.checkCommonFields(partInventoryText.getText(), partPriceText.getText(), partMaxText.getText(), partMinText.getText());

            if (inhouseRBtn.isSelected()) { // after checking which radio button is checked, we will add a different error to the ErrorBuilder dialog.
                ErrorBuilder.checkMachineId(partFlexText.getText());
            }
            if (outsourcedRBtn.isSelected()) { // after checking which radio button is checked, we will add a different error to the ErrorBuilder dialog.
                ErrorBuilder.checkCompanyNameField(partFlexText.getText());
            }
            // This calls the Errorbuilder class and displays an error based on which fields failed the check.
            ErrorBuilder.displayErrorMessage("DataType Mismatch", "Invalid datatype in text fields",
                   "Please correct the following fields:\n\n" + ErrorBuilder.getErrorInfo().toString());

            // After the user clicks OK on the dialog, we reset the StringBuilder within the ErrorBuilder Class.
            ErrorBuilder.clearErrorInfo();
        }
        catch (Exception e) { // Generic catch statement that will catch any errors I didn't account for and display some information about the error.
            ErrorBuilder.displayErrorMessage("An Error has Occurred", "An error has occurred:" + e.getMessage());
        }
    }

    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        // Calling my ErrorBuilder, if the user clicks OK, True is returned, and the main screen is loaded.
        if(ErrorBuilder.displayConfirmationMessage("Exiting screen", "Are you sure you want to exit?\n\nAll Data will be lost.")) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes the controller and sets the initial state of the UI components.
     * This method is automatically called by the FXMLLoader when the associated FXML file is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle containing localized resources, or null if no resource bundle is specified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initializes the controller and automatically selects the "InHouse" radio button.
        inhouseRBtn.setSelected(true);

    }
}

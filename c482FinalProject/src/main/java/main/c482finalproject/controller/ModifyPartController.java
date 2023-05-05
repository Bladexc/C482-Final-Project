package main.c482finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.c482finalproject.model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;


/**
 * ModifyPartController is responsible for modifying parts utilizing user input and data validation to ensure parts are modified correctly, with additional logic
 * to detect if the modified part is InHouse or OutSourced.
 *
 * @author Blake Heller

 * FUTURE ENHANCEMENT:
 * The program should include additional fields that will contain potential discounts on parts.
 * Additionally, the part name field should stop users from entering small names, like 1 letter or 1 number names.
 */
public class ModifyPartController implements Initializable {

    //declare Java FX scene and stage objects.
    Stage stage;
    Parent scene;

    //declare label and field objects.
    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;

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

    /**
     * Populates the fields in the UI with the data from the specified Part object from the Main Menu.
     *
     * @param part The Part object to be displayed in the UI.
     */
    public void sendPart(Part part) {
        partIdText.setText(String.valueOf(part.getId()));
        partNameText.setText(String.valueOf(part.getName()));
        partInventoryText.setText(String.valueOf(part.getStock()));
        partPriceText.setText(String.valueOf(part.getPrice()));
        partMaxText.setText(String.valueOf(part.getMax()));
        partMinText.setText(String.valueOf(part.getMin()));

        // depending on what class of part the selected part is, we will auto select the correct radio button and populate the correct data.
        if (part instanceof InHouse) {
            inhouseRBtn.setSelected(true);
            partFlexLabel.setText("In-House");
            partFlexText.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            outsourcedRBtn.setSelected(true);
            partFlexLabel.setText("Company Name");
            partFlexText.setText(((Outsourced) part).getCompanyName());
        }
    }

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
     * RUNTIME ERROR:
     * NumberFormatException is the main error caught in my class as a result of attempting to parse an invalid double or integer value.
     * The class utilizes these errors to provide a dialog box that is dynamically built to inform the user what fields must be corrected. If any other
     * error is thrown, it will be caught and displayed via a generic error message.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        // creating a temporary part and an ID field for later use in this method.
        Part selectedPart = Inventory.lookupPart(Integer.parseInt(partIdText.getText()));
        int id = Integer.parseInt(partIdText.getText());

        try { // try & catch block will attempt to parse the values in the text fields and will throw a NumberFormatException
            String name = partNameText.getText();
            if (name.isEmpty()) { // if the name field is empty, throw a new NumberFormatException so the ErrorBuilder Class can build an error dialog box.
                throw new NumberFormatException();
            }

            double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());

            if (inhouseRBtn.isSelected() && selectedPart instanceof InHouse) { // If selected Part is InHouse and the InHouse Radio is selected:

                int machineId = Integer.parseInt(partFlexText.getText());

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    /* build a temporary part using the correct constructor, then override my auto-incrementing ID function by setting its ID equal to the
                       selected part's ID.
                       After the Part is added to the list, we sort the list so the parts appear in order in the main menu.
                     */
                    Part updatedPart = new InHouse(name, price, stock, min, max, machineId);

                    updatedPart.setId(id);
                    Inventory.updatePart(id, updatedPart);
                    Inventory.getAllParts().sort(Comparator.comparing(Part::getId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            if (outsourcedRBtn.isSelected() && selectedPart instanceof Outsourced) { // If selected Part is Outsourced and the Outsourced Radio is selected:

                String company = partFlexText.getText();
                if (company.isEmpty()) { // if the name field is empty, throw a new NumberFormatException so the ErrorBuilder Class can build an error dialog box.
                    throw new NumberFormatException();
                }

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    /* build a temporary part using the correct constructor, then override my auto-incrementing ID function by setting its ID equal to the
                       selected part's ID.
                       After the Part is added to the list, we sort the list so the parts appear in order in the main menu.
                     */
                    Part updatedPart = new Outsourced(name, price, stock, min, max, company);

                    updatedPart.setId(id);
                    Inventory.updatePart(id, updatedPart);
                    Inventory.getAllParts().sort(Comparator.comparing(Part::getId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            if (inhouseRBtn.isSelected() && selectedPart instanceof Outsourced) { // If selected Part is InHouse and the Outsourced Radio is selected:
                //execution can stop here if this Parse Statement fails.
                int machineId = Integer.parseInt(partFlexText.getText());

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    /* build a temporary part using the correct constructor, then override my auto-incrementing ID function by setting its ID equal to the
                       selected part's ID. Then we delete the old part and insert the new part.
                       After the Part is added to the list, we sort the list so the parts appear in order in the main menu.
                     */
                    Part updatedPart = new InHouse(name, price, stock, min, max, machineId);

                    updatedPart.setId(id);
                    Inventory.deletePart(selectedPart);
                    Inventory.addPart(updatedPart);
                    Inventory.getAllParts().sort(Comparator.comparing(Part::getId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            if (outsourcedRBtn.isSelected() && selectedPart instanceof InHouse) { // If selected Part is Outsourced the InHouse and Radio is selected:
                String company = partFlexText.getText();
                if (company.isEmpty()) { // if no name is entered, force a NumberFormatException and begin building an error.
                    throw new NumberFormatException();
                }

                if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                    /* build a temporary part using the correct constructor, then override my auto-incrementing ID function by setting its ID equal to the
                       selected part's ID. Then we delete the old part and insert the new part.
                       After the Part is added to the list, we sort the list so the parts appear in order in the main menu.
                     */
                    Part updatedPart = new Outsourced(name, price, stock, min, max, company);

                    updatedPart.setId(id);
                    Inventory.deletePart(selectedPart);
                    Inventory.updatePart(id, updatedPart);
                    Inventory.getAllParts().sort(Comparator.comparing(Part::getId));

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

    /**
     * Initializes the controller.
     * This method is automatically called by the FXMLLoader when the associated FXML file is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle containing localized resources, or null if no resource bundle is specified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

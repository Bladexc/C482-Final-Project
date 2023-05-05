package main.c482finalproject.controller;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.c482finalproject.model.ErrorBuilder;
import main.c482finalproject.model.Inventory;
import main.c482finalproject.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.c482finalproject.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * ModifyProductController is responsible for modifying existing products and will utilize user input and data validation to ensure products are modified correctly.
 * The class is complete with dialog boxes that will warn users if they try to exit or remove a part from their product.
 *
 * @author Blake Heller
 *
 * FUTURE ENHANCEMENT:
 * A future feature would be detecting if a part is already associated with this product and then removing it from the parts list tableview dynamically as users
 * add or remove parts from the product.
 */
public class ModifyProductController implements Initializable {

    //declare Java FX scene and stage objects.
    Stage stage;
    Parent scene;

    //declare the temporary product used for storing data while in this screen.
    Product tempProduct = new Product();

    //declare label and field objects.
    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchText;

    @FXML
    private TextField productIdText;

    @FXML
    private TextField productInventoryText;

    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

    @FXML
    private TextField productNameText;

    @FXML
    private TableView<Part> productPartTableView;

    @FXML
    private TableColumn<Part, Integer> productPartIdCol;

    @FXML
    private TableColumn<Part, Integer> productPartInventoryCol;

    @FXML
    private TableColumn<Part, String> productPartNameCol;

    @FXML
    private TableColumn<Part, Double> productPartPriceCol;

    @FXML
    private TextField productPriceText;

    /**
     * Sends a product object to the controller for display and editing.
     * Updates the UI components with the product's information.
     *
     * @param product The product to be sent and displayed.
     */
    public void sendProduct(Product product) {
        tempProduct = product;

        productIdText.setText(String.valueOf(product.getId()));
        productNameText.setText(String.valueOf(product.getName()));
        productPriceText.setText(String.valueOf(product.getPrice()));
        productInventoryText.setText(String.valueOf(product.getStock()));
        productMaxText.setText(String.valueOf(product.getMax()));
        productMinText.setText(String.valueOf(product.getMin()));

        productPartTableView.setItems(product.getAllAssociatedParts());
    }

    // This event handler will detect when the search dialog box is empty and will change the tableview data back to its original source.
    @FXML
    void onEmptyReset(KeyEvent event) {
        String searchPart = partSearchText.getText();
        if (searchPart.isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
            Inventory.getFilteredParts().clear();
        }

    }

    /**
     * RUNTIME ERROR:
     * NumberFormatException is the exception caught during a parse action and is processed by using a different search method WITHOUT an error dialog box.
     *
     * This event handler will detect if the ENTER key is pushed and then clear the list of searched parts, then change the tableview data to the filtered parts list.
     */
    @FXML
    void onKeyPressEnterSearchPart(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {   // listener for ENTER key
            Inventory.getFilteredParts().clear(); // Resets the filtered list so duplicate items no longer appear.

            String search = partSearchText.getText();

            try {   // Method will first try parsing the value in the search box and attempt to search by the ID field
                    // then when the NumberFormatException is caught, we will instead search using the name field.
                int searchId = Integer.parseInt(search);

                if (Inventory.lookupPart(searchId) == null) { // if no search results appear, display error dialog.
                    ErrorBuilder.displayErrorMessage("No Results","No results found" );
                }
                else { // Add searched item to the list, then change the source for the tableview to the filtered list, and lastly select the searched item.
                    Inventory.getFilteredParts().add(Inventory.lookupPart(searchId));

                    partTableView.setItems(Inventory.getFilteredParts());
                    partTableView.getSelectionModel().select(Inventory.lookupPart(searchId));
                }
            }
            catch (NumberFormatException error) {
                // Catch the NumberFormatException, then execute a search by name field instead.
                // This search method is my original design I created without referencing online resources or online videos.
                Inventory.lookupPart(search);

                if (Inventory.getFilteredParts().isEmpty()) { // if no search results appear, display error dialog.
                    ErrorBuilder.displayErrorMessage("No Results","No results found" );
                }
                else { // set tableview source to the filtered list.
                    partTableView.setItems(Inventory.lookupPart(search));
                }
            }
        }
    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the part the user selected and add it to TempProduct's associated part list,  catching
     * a NullPointerException and displaying an error if no selection was made.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        try {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) { // throws a NullPointerException if no selection was made.
                throw new NullPointerException();
            }
            // adds the part to the tempProduct's associated parts list.
            tempProduct.addAssociatedPart(selectedPart);
            productPartTableView.setItems(tempProduct.getAllAssociatedParts());
        }
        catch (NullPointerException error) { // catches the nullPointer error and will display a dialog box informing the user.
            ErrorBuilder.displayErrorMessage("No Selection Made", "Please select a part to add to this product");
        }
    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the part the user selected and add it to TempProduct's associated part list,  catching
     * a NullPointerException and displaying an error if no selection was made.
     *
     * In addition, a dialog box will appear confirming if the user wants to delete the selected product.
     */
    @FXML
    void onActionRemovePart(ActionEvent event) throws IOException {
        try {
            Part selectedPart = productPartTableView.getSelectionModel().getSelectedItem();

            if (selectedPart == null) { // throw NullPointerException if no select was made.
                throw new NullPointerException();
            }

            //If the user clicks OK, this statement becomes TRUE and will delete the part.
            if(ErrorBuilder.displayConfirmationMessage("Deleting Part", "Are you sure you want to delete this part?")) {
                tempProduct.deleteAssociatedPart(selectedPart);
            }

            // reset the tableview source after removing the part.
            productPartTableView.setItems(tempProduct.getAllAssociatedParts());
        }
        catch (NullPointerException error) { // Using my ErrorBuilder method, a dialog box is displayed informing the user of the error.
            ErrorBuilder.displayErrorMessage("No Selection Made", "Please select a part to remove from this product");
        }
    }

    /**
     * RUNTIME ERROR:
     * NumberFormatException is the exception caught during a parse action and is used to generate a dynamic error message informing the user which fields are incorrect.
     *
     * The ErrorBuilder class utilizes these errors to provide a dialog box that is dynamically built to inform the user what fields must be corrected. If any other
     * error is thrown, it will be caught and displayed via a generic error message.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        int id = Integer.parseInt(productIdText.getText()); // saves the selected product's ID.

        try { // try & catch block will attempt to parse the values in the text fields and will throw a NumberFormatException.
            String name = productNameText.getText();

            if (name.isEmpty()) { // if the name field is empty, throw a new NumberFormatException so the ErrorBuilder Class can build an error dialog box.
                throw new NumberFormatException();
            }

            int stock = Integer.parseInt(productInventoryText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            int min = Integer.parseInt(productMinText.getText());

            if (ErrorBuilder.checkMinMax(min, max) && ErrorBuilder.checkStockMinMax(stock, min, max)) {
                // utilizing my ErrorBuilder Class, this checks for valid values for the stock, min, and max before attempting to
                // make a new part. Both checkMinMax and checkStockMinMax will stop execution and display an error dialog.

                tempProduct.setName(name);
                tempProduct.setStock(stock);
                tempProduct.setPrice(price);
                tempProduct.setMax(max);
                tempProduct.setMin(min);

                Inventory.updateProduct(id, tempProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
        catch (NumberFormatException error) {
            // after a NumberFormatException has been caught anywhere when the parse method is invoked,
            // we check each field and build an error message using the ErrorBuilder Class.
            ErrorBuilder.checkNameField(productNameText.getText());
            ErrorBuilder.checkCommonFields(productInventoryText.getText(), productPriceText.getText(), productMaxText.getText(), productMinText.getText());

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
    void onActionDisplayMain(ActionEvent event) throws IOException { // Calling my ErrorBuilder, if the user clicks OK, True is returned, and the main screen is loaded.

        if(ErrorBuilder.displayConfirmationMessage("Exiting screen", "Are you sure you want to exit?\n\nAll Data will be lost.")) {
            // If the user clicks OK, we reset everything in the tempProduct so the next time we enter the screen, no parts are in its list.
            tempProduct.getAllAssociatedParts().clear();

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes the controller and sets up the initial state of the UI components.
     * This method is automatically called by the FXMLLoader when the associated FXML file is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle containing localized resources, or null if no resource bundle is specified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the tableview's data sources and set the CellValueFactory for each column.
        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

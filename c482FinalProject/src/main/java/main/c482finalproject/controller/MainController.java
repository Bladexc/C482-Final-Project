package main.c482finalproject.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.c482finalproject.model.ErrorBuilder;
import main.c482finalproject.model.Inventory;
import main.c482finalproject.model.Part;
import main.c482finalproject.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * MainMenuController is responsible for displaying a full list of Parts and Products in the Inventory System.
 *
 * @author Blake Heller
 *
 * FUTURE ENHANCEMENT:
 * Additional space in the UI should be created to show images of the product or the parts that are selected in the tableviews.
 *
 * RUNTIME ERROR:
 * While working on my project, I encountered an issue with my delete part and delete product command. After deleting a part from my parts list, to maintain
 * unique IDs for each part, I built a method that will update the part IDs for the parts once a part is removed, so if the 3rd part in a list of 5 parts is deleted,
 * part numbers 4 and 5 would then have IDs of 3 and 4 after the delete runs successfully. I encountered an issue after I would modify a part and then have the new part
 * added to the list.  After a modify operation was complete, now my parts list would have a part at the bottom of the list with an ID of "2" (if I modified the 2nd part
 * in the list). If I attempted to delete a part in the list, what would happen is that my tableview Part ID fields wouldn't display the correct data after my update Part ID
 * method was run.  Using Print statements, I verified my method was working properly, yet the tableviews simply wouldn't display properly.  I fixed this by
 * sorting the parts list every time a part is modified and after a delete operation is complete, I would perform a refresh() operation on the tableview.  This fixed
 * my problem.
 */
public class MainController implements Initializable {

    //declare Java FX scene and stage objects.
    Stage stage;
    Parent scene;

    //declare label and field objects.
    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchText;


    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField productSearchText;


    // This event handler will detect when the Part or Product search dialog box is empty and will change the tableview data back to its original source.
    @FXML
    void onEmptyReset(KeyEvent event) {

        String searchPart = partSearchText.getText();
        String searchProduct = productSearchText.getText();

        if (searchPart.isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
            Inventory.getFilteredParts().clear(); // reset the filtered list back to null to avoid duplicate entries in the list from previous searches.
        }
        if (searchProduct.isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
            Inventory.getFilteredProducts().clear(); // reset the filtered list back to null to avoid duplicate entries in the list from previous searches.
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
                else {  // Add searched item to the list, then change the source for the tableview to the filtered list, and lastly select the searched item.
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
     * NumberFormatException is the exception caught during a parse action and is processed by using a different search method WITHOUT an error dialog box.
     *
     * This event handler will detect if the ENTER key is pushed and then clear the list of searched products, then change the tableview data to the filtered products list.
     */
    @FXML
    void onKeyPressEnterSearchProduct(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {      // listener for ENTER key
            Inventory.getFilteredProducts().clear(); // Resets the filtered list so duplicate items no longer appear.

            String search = productSearchText.getText();

            try {   // Method will first try parsing the value in the search box and attempt to search by the ID field
                    // then when the NumberFormatException is caught, we will instead search using the name field.
                int searchId = Integer.parseInt(search);

                if (Inventory.lookupProduct(searchId) == null) { // if no search results appear, display error dialog.
                    ErrorBuilder.displayErrorMessage("No Results","No results found");
                }
                else { // Add searched item to the list, then change the source for the tableview to the filtered list, and lastly select the searched item.
                    Inventory.getFilteredProducts().add(Inventory.lookupProduct(searchId));

                    productTableView.setItems(Inventory.getFilteredProducts());
                    productTableView.getSelectionModel().select(Inventory.lookupProduct(searchId));
                }
            }
            catch (NumberFormatException error) {
                // Catch the NumberFormatException, then execute a search by name field instead.
                // This search method is my original design I created without referencing online resources or online videos.
                Inventory.lookupProduct(search);

                if (Inventory.getFilteredProducts().isEmpty()) { // if no search results appear, display error dialog.
                    ErrorBuilder.displayErrorMessage("No Results","No results found");
                }
                else {  // set tableview source to the filtered list.
                    productTableView.setItems(Inventory.lookupProduct(search));
                }
            }
        }
    }

    // This event handler will take the user to the Add Part Screen.
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the part the user selected and navigate to the Modify Part screen, catching
     * a NullPointerException and displaying an error if no selection was made.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException { // takes the data in the selected object and pushes it into the next screen.
        try {
            FXMLLoader partLoader = new FXMLLoader();
            partLoader.setLocation(getClass().getResource("/main/c482finalproject/ModifyPartMenu.fxml"));
            partLoader.load();

            ModifyPartController MPController = partLoader.getController();
            MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = partLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch (NullPointerException error) { // catches the nullPointer error and will display a dialog box informing the user.
            ErrorBuilder.displayErrorMessage("No Selection Made","Please select a part to modify.");
        }
    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the part the user selected attempt to delete it from the parts list,  catching
     * a NullPointerException and displaying an error if no selection was made.
     *
     * In addition, a dialog box will appear confirming if the user wants to delete the selected part.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {
        try {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

            if (selectedPart == null) { // throw a NullPointerException if no part was selected.
                throw new NullPointerException();
            }
            // Using the Error builder class, display an error that will result in TRUE if the user selects OK, false otherwise.
            if(ErrorBuilder.displayConfirmationMessage("Deleting Part", "Are you sure you want to delete this part?")) {

                //delete the selected part, then run the updatePartId method to reset the IDs, which will set the ID to -1 for every ID beyond the part deleted,
                //then due to some issue I was experiencing with the tableviews, refresh the tableviews so that they display the altered data correctly.
                //I was experiencing an issue where the IDs were being correctly adjusted, but duplicate values were appearing in the tableview.
                Inventory.deletePart(selectedPart);
                Inventory.updatePartId();
                partTableView.refresh();
            }
        }
        catch (NullPointerException error) { // catches the nullPointer error and will display a dialog box informing the user.
            ErrorBuilder.displayErrorMessage("No Selection Made", "Please select a part to delete");
        }
    }

    // Event handler that will navigate the user to the Add Product screen.
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/c482finalproject/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the product the user selected and navigate to the Modify product screen, catching
     * a NullPointerException and displaying an error if no selection was made.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException { // takes the data in the selected object and pushes it into the next screen.
        try {
            FXMLLoader productLoader = new FXMLLoader();
            productLoader.setLocation(getClass().getResource("/main/c482finalproject/ModifyProductMenu.fxml"));
            productLoader.load();

            ModifyProductController MPController = productLoader.getController();
            MPController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = productLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException error) { // catches the nullPointer error and will display a dialog box informing the user.
            ErrorBuilder.displayErrorMessage("No Selection Made", "Please make a selection from the products list");
        }
    }

    /**
     * RUNTIME ERROR:
     * NullPointerException This event handler will take the product the user selected attempt to delete it from the product list IF the
     * selected product has no associated parts, otherwise displaying an error informing the user where to go to correct this,
     * and finally catching a NullPointerException and displaying an error if no selection was made.
     *
     * In addition, a dialog box will appear confirming if the user wants to delete the selected product.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) { // throw a NullPointerException if no part was selected.
                throw new NullPointerException();
            }
            if (selectedProduct.getAllAssociatedParts().isEmpty()) { // checks if the product has associated parts.

                // Using the Error builder class, display an error that will result in TRUE if the user selects OK, false otherwise.
                if (ErrorBuilder.displayConfirmationMessage("Deleting Product","Are you sure you want to delete this product?")) {

                    //delete the selected product, then run the updateProductId method to reset the IDs, which will set the ID to -1 for every ID beyond the product deleted,
                    //then due to some issue I was experiencing with the tableviews, refresh the tableviews so that they display the altered data correctly.
                    //I was experiencing an issue where the IDs were being correctly adjusted, but duplicate values were appearing in the tableview.
                    Inventory.deleteProduct(selectedProduct);
                    Inventory.updateProductId();
                    productTableView.refresh();
                }
            }
            else { // If code fails the isEmpty() statement, then we will display the user informing them why they cannot delete the product.
                ErrorBuilder.displayErrorMessage("Error", "Unable to Delete Product",
                        "Product still has associated parts, please use the Modify screen to remove associated parts before deleting the product.");
            }
        }
        catch (NullPointerException error) { // catches the nullPointer error and will display a dialog box informing the user.
            ErrorBuilder.displayErrorMessage("No Selection Made","Please select a product to delete");
        }
    }

    // Exit button code that will provide a confirmation message asking the user if they really, really want to exit.
    @FXML
    void onActionExit(ActionEvent event) {
        if (ErrorBuilder.displayConfirmationMessage("Terminating Program", "Are you sure you want to exit?")) {
            System.exit(0);
        }
    }


    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param url  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set initial items for the part TableView
        partTableView.setItems(Inventory.getAllParts());

        // Configure the PropertyValueFactory for each part TableColumn
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set initial items for the product TableView
        productTableView.setItems(Inventory.getAllProducts());

        // Configure the PropertyValueFactory for each product TableColumn
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set initial items for the TableViews and sets up the Property Values for each Column.

    }
}
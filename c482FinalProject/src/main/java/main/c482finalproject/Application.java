package main.c482finalproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.c482finalproject.controller.MainController;
import main.c482finalproject.model.Inventory;
import main.c482finalproject.model.Outsourced;
import main.c482finalproject.model.Part;
import main.c482finalproject.model.Product;

import java.io.IOException;

/**
 * This class creates an Application that functions as an Inventory Management System.
 *
 * @author Blake Heller
 *
 */

public class Application extends javafx.application.Application {

    /**
     * Starts the JavaFX application by loading the main menu.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/main/c482finalproject/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("I.M.S.");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point for the application, which is an Inventory Management System.
     *
     * JavaDoc Directory:  Attached as Separate ZIP File.
     * C482FinalProject_JavaDoc/main.c482finalproject/main.c482finalproject/module-summary.html
     *
     * FUTURE_ENHANCEMENT:
     * In later revisions of the project, additional fields will be added to the Parts and Products objects so that images could be stored, which would be used to
     * display images of selected parts and products.  Additionally, more data fields would be added to further store information about parts and products, like
     * discounts, employee IDs, and more.
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
     *
     * @author Blake Heller
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        Outsourced part1, part2, part3, part4, part5, part6, part7, part8;
        //creates a list of parts to initialize the program with.

        part1 = new Outsourced("AND Processor", 200.00, 30, 10, 50, "AND");
        Inventory.addPart(part1);

        part2 = new Outsourced("AND GPU", 500.00, 5, 1, 10, "AND");
        Inventory.addPart(part2);

        part3 = new Outsourced("AND Motherboard", 175.00, 8, 1, 10, "AND");
        Inventory.addPart(part3);

        part4 = new Outsourced("AND PC Case", 80.00, 25, 5, 30, "AND");
        Inventory.addPart(part4);

        part5 = new Outsourced("Imtel Processor", 370.00, 28, 5, 30, "Imtel");
        Inventory.addPart(part5);

        part6 = new Outsourced("Imtel GPU", 700.00, 30, 5, 40, "Imtel");
        Inventory.addPart(part6);

        part7 = new Outsourced("Imtel Motherboard", 220.00, 20, 5, 25, "Imtel");
        Inventory.addPart(part7);

        part8 = new Outsourced("Imtel PC Case", 100.00, 100, 25, 150, "Imtel");
        Inventory.addPart(part8);
        //set values for all the parts while adding them to the Parts List in order so the ID can automatically Auto-Increment.

        Product amd = new Product("AND PC", 1500, 10, 1, 15);

        Inventory.addProduct(amd);
        amd.addAssociatedPart(part1);
        amd.addAssociatedPart(part2);
        amd.addAssociatedPart(part3);
        amd.addAssociatedPart(part4);
        //Create a new product, add parts to it, then add it to the products list to auto-increment the ID.

        Product intel = new Product("Imtel PC", 1850, 23, 1, 30);

        Inventory.addProduct(intel);
        intel.addAssociatedPart(part5);
        intel.addAssociatedPart(part6);
        intel.addAssociatedPart(part7);
        intel.addAssociatedPart(part8);
        //Create a new product, add parts to it, then add it to the products list to auto-increment the ID.

        launch();
    }
}




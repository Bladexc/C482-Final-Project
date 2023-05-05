package main.c482finalproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class represents a product in the inventory system.
 *
 * @author Blake Heller
 *
 * RUNTIME ERROR:
 *
 * FUTURE ENHANCEMENT:
 *
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructs a new Product with an auto-generated ID and initializes the associatedParts list.
     */
    public Product() {
        associatedParts = FXCollections.observableArrayList();
        this.id = (Inventory.getAllProducts().size() + 1);
    }

    /**
     * Constructs a new Product with the specified ID, name, price, stock, minimum, and maximum values.
     *
     * @param id    the ID of the product
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the current stock of the product
     * @param min   the minimum allowed stock of the product
     * @param max   the maximum allowed stock of the product
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a new Product with an auto-generated ID and the specified name, price, stock, minimum, and maximum values.
     *
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the current stock of the product
     * @param min   the minimum allowed stock of the product
     * @param max   the maximum allowed stock of the product
     */
    public Product (String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();

        this.id = (Inventory.getAllProducts().size() + 1);

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id the ID to set for the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set for the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the inventory of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the inventory to set for the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the minimum stock of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the minimum stock to set for the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the maximum stock of the product
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the maximum stock to set for the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part The part to be associated with the product.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deletes an associated part from the list of associated parts for the product.
     *
     * @param selectedAssociatedPart The associated part to be deleted.
     * @return True if the associated part is successfully deleted, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part associatedParts : this.getAllAssociatedParts() ) {
            if (associatedParts == selectedAssociatedPart) {
                return this.getAllAssociatedParts().remove(selectedAssociatedPart);
            }
        }
        return false;
    }

    /**
     * @return An observable list of all the associated parts for the product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

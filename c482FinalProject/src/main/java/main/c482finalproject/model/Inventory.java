package main.c482finalproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;

/**
 * The Inventory class represents the inventory of parts and products in the system.
 * It provides various methods to add, lookup, update, and delete parts and products, with additional methods to help manage the Unique ID fields for parts and products.
 *
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory parts list.
     *
     * @param newPart The part to be added.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory products list.
     *
     * @param newProduct The product to be added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part in the inventory by its ID.
     *
     * @param partId The ID of the part to be looked up.
     * @return The part with the specified ID, or null if not found.
     */
    public static Part lookupPart(int partId) {
        for (Part allParts : Inventory.getAllParts()) {
            if (allParts.getId() == partId) {
                return allParts;
            }
        }
        return null;
    }

    /**
     * Looks up a product in the inventory by its ID.
     *
     * @param productId The ID of the product to be looked up.
     * @return The product with the specified ID, or null if not found.
     */
    public static Product lookupProduct(int productId) {
        for (Product allProducts : Inventory.getAllProducts()) {
            if (allProducts.getId() == productId) {
                return allProducts;
            }
        }
        return null;
    }

    /**
     * Looks up parts in the inventory by their name.
     *
     * @param partName The name of the parts to be looked up.
     * @return The list of parts with names containing the specified partName, or the entire parts list if not found.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        if ( ! Inventory.getFilteredParts().isEmpty()) {
            Inventory.getFilteredParts().clear();
        }
        for (Part allParts : Inventory.getAllParts()) {
            if (allParts.getName().contains(partName)) {
                Inventory.getFilteredParts().add(allParts);
            }
        }

        if ((Inventory.getFilteredParts()).isEmpty()) {
            return Inventory.getAllParts();
        }
        else {
            return Inventory.getFilteredParts();
        }
    }

    /**
     * Looks up products in the inventory by their name.
     *
     * @param productName The name of the products to be looked up.
     * @return The list of products with names containing the specified productName, or the entire products list if not found.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        if ( ! Inventory.getFilteredProducts().isEmpty()) {
            Inventory.getFilteredProducts().clear();
        }

        for (Product allProducts : Inventory.getAllProducts()) {
            if (allProducts.getName().contains(productName)) {
                Inventory.getFilteredProducts().add(allProducts);
            }
        }

        if ((Inventory.getFilteredProducts().isEmpty())) {
            return Inventory.getAllProducts();
        }
        else {
            return Inventory.getFilteredProducts();
        }
    }

    /**
     * Updates a part in the inventory at the specified index.
     *
     * @param index         The index of the part to be updated.
     * @param selectedPart  The updated part.
     */
    public static void updatePart(int index, Part selectedPart) {
        int count  = -1;

        for (Part allParts : Inventory.getAllParts() ) {
            count++;

            if (allParts.getId() == index ) {
                Inventory.getAllParts().set(count, selectedPart);
            }
        }

    }

    /**
     * Updates a product in the inventory at the specified index.
     *
     * @param index             The index of the product to be updated.
     * @param selectedProduct   The updated product.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        int count  = -1;

        for (Product allProducts : Inventory.getAllProducts() ) {
            count++;

            if (allProducts.getId() == index ) {
                Inventory.getAllProducts().set(count, selectedProduct);
            }
        }
    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart  The part to be deleted.
     * @return  true if the part was successfully deleted, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part allParts : Inventory.getAllParts()) {
            if (selectedPart.equals(allParts)){
                Inventory.getAllParts().remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param selectedProduct   The product to be deleted.
     * @return  true if the product was successfully deleted, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
            for (Product allProducts : Inventory.getAllProducts() ) {
                if (selectedProduct.equals(allProducts) )   {
                    return Inventory.getAllProducts().remove(selectedProduct);
                }
            }
            return false;
    }

    /**
     * Updates the IDs of all parts in the inventory.
     * First the method sorts the list according to the ID field, then will assign a unique ID to each item in the list.
     * The method is designed to be run after a part has been removed from the list of parts.
     */
    public static void updatePartId() {
        Inventory.getAllParts().sort(Comparator.comparing(Part::getId));

        for (int count = 0; count < Inventory.getAllParts().size(); count++) {
            Inventory.getAllParts().get(count).setId(count + 1);
        }
    }

    /**
     * Updates the IDs of all products in the inventory.
     * First the method sorts the list according to the ID field, then will assign a unique ID to each item in the list.
     * The method is designed to be run after a product has been removed from the list of products.
     */
    public static void updateProductId() {
        Inventory.getAllProducts().sort(Comparator.comparing(Product::getId));

        for (int count = 0; count < Inventory.getAllProducts().size(); count++) {
            Inventory.getAllProducts().get(count).setId(count + 1);
        }
    }

    /**
     * Retrieves the list of all parts in the inventory.
     *
     * @return The list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves the list of all products in the inventory.
     *
     * @return The list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Retrieves the list of filtered parts in the inventory.
     *
     * @return The list of filtered parts.
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * Retrieves the list of filtered products in the inventory.
     *
     * @return The list of filtered products.
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }
}

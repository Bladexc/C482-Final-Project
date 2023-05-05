package main.c482finalproject.model;

/**
 * Supplied class Part.java, with additional comments added to the constuctors used in this class.
 *
 * @author Blake Heller
 *
 * FUTURE ENHANCEMENT:
 * The class member should include new data fields to hold part price discounts.
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructs a part with the specified attributes.
     *
     * @param id    The unique identifier of the part.
     * @param name  The name of the part.
     * @param price The price of the part.
     * @param stock The current stock of the part.
     * @param min   The minimum allowed stock of the part.
     * @param max   The maximum allowed stock of the part.
     */
    public Part (int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a part with an auto-generated ID and the specified attributes.
     * This is my own solution that I came up with without consulting any videos or online resources.
     *
     * @param name  The name of the part.
     * @param price The price of the part.
     * @param stock The current stock of the part.
     * @param min   The minimum allowed stock of the part.
     * @param max   The maximum allowed stock of the part.
     */
    public Part (String name, double price, int stock, int min, int max) {

        this.id = (Inventory.getAllParts().size() + 1);

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}

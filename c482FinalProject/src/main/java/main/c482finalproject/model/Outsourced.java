package main.c482finalproject.model;

/**
 * The `Outsourced` class represents an outsourced part in the inventory system. It is a subclass of the `Part` class and contains additional information about the company name.
 *
 * RUNTIME ERROR:
 *
 * FUTURE ENHANCEMENT:
 *
 */
public class Outsourced extends Part {

    /**
     * The name of the company associated with the outsourced part.
     */
    private String companyName;

    /**
     * Creates a new instance of the `Outsourced` class with the specified properties.
     *
     * @param id           The ID of the part.
     * @param name         The name of the part.
     * @param price        The price of the part.
     * @param stock        The current stock of the part.
     * @param min          The minimum allowed stock of the part.
     * @param max          The maximum allowed stock of the part.
     * @param companyName  The name of the company associated with the outsourced part.
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Creates a new instance of the `Outsourced` class with a default ID and the specified properties.
     *
     * @param name         The name of the part.
     * @param price        The price of the part.
     * @param stock        The current stock of the part.
     * @param min          The minimum allowed stock of the part.
     * @param max          The maximum allowed stock of the part.
     * @param companyName  The name of the company associated with the outsourced part.
     */
    public Outsourced(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max);
        this.companyName = companyName;
    }


    /**
     * Gets the name of the company associated with the outsourced part.
     *
     * @return The name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company associated with the outsourced part.
     *
     * @param companyName The name of the company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

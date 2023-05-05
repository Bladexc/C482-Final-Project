package main.c482finalproject.model;

/**
 * The `InHouse` class represents an in-house part in the inventory system. It is a subclass of the `Part` class and contains additional information about the machine ID.
 *
 * FUTURE ENHANCEMENT:
 * Additional fields should be included for InHouse Parts, like employee ID or Product Information.
 */
public class InHouse extends Part {

    /**
     * The ID of the machine associated with the in-house part.
     */
    private int machineId;

    /**
     * Creates a new instance of the `InHouse` class with the specified properties.
     *
     * @param id        The ID of the part.
     * @param name      The name of the part.
     * @param price     The price of the part.
     * @param stock     The current stock of the part.
     * @param min       The minimum allowed stock of the part.
     * @param max       The maximum allowed stock of the part.
     * @param machineId The ID of the machine associated with the in-house part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Creates a new instance of the `InHouse` class with a default ID and the specified properties.
     *
     * @param name      The name of the part.
     * @param price     The price of the part.
     * @param stock     The current stock of the part.
     * @param min       The minimum allowed stock of the part.
     * @param max       The maximum allowed stock of the part.
     * @param machineId The ID of the machine associated with the in-house part.
     */
    public InHouse(String name, double price, int stock, int min, int max, int machineId) {
        super(name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Gets the ID of the machine associated with the in-house part.
     *
     * @return The ID of the machine.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the ID of the machine associated with the in-house part.
     *
     * @param machineId The ID of the machine.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

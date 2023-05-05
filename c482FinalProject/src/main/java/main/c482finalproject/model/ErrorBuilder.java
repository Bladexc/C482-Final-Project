package main.c482finalproject.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * The `ErrorBuilder` class provides utility methods for building error messages and performing validation checks.
 *
 * @author Blake Heller
 *
 * FUTURE ENHANCEMENT:
 * I would like to extend this class to eventually also handle the FXML Loader code that is present in navigating through screens, or put that code
 * into a separate class.
 */
public class ErrorBuilder {

    /**
     * The StringBuilder object used to store the error information.
     */
    private static StringBuilder errorInfo = new StringBuilder();

    /**
     * Gets the error information stored in the StringBuilder object.
     *
     * @return The error information.
     */
    public static StringBuilder getErrorInfo() {
        return errorInfo;
    }

    /**
     * Sets the error information in the StringBuilder object.
     *
     * @param errorInfo The error information.
     */
    public static void setErrorInfo(StringBuilder errorInfo) {
        ErrorBuilder.errorInfo = errorInfo;
    }

    /**
     * Clears the error information stored in the StringBuilder object.
     */
    public static void clearErrorInfo() {
        errorInfo.setLength(0);
    }

    /**
     * Checks if a string represents a valid double value.
     *
     * @param text The string to check.
     * @return `true` if the string represents a valid double value, `false` otherwise.
     */
    public static boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string represents a valid integer value.
     *
     * @param text The string to check.
     * @return `true` if the string represents a valid integer value, `false` otherwise.
     */
    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks the common fields (stock, price, max, min) for validity and appends error messages to the error information if necessary.
     *
     * @param stock The stock value to check.
     * @param price The price value to check.
     * @param max   The max value to check.
     * @param min   The min value to check.
     */
    public static void checkCommonFields(String stock, String price, String max, String min) {

        if (!ErrorBuilder.isInteger(stock)) {
            errorInfo.append(" - Inventory Field -- (Requires an Integer Value)\n\n");
        }

        if (!ErrorBuilder.isDouble(price)) {
            errorInfo.append(" - Price Field -- (Requires an Integer or Double)\n\n");
        }

        if (!ErrorBuilder.isInteger(max)) {
            errorInfo.append(" - Max Field -- (Requires an Integer Value)\n\n");
        }

        if (!ErrorBuilder.isInteger(min)) {
            errorInfo.append(" - Min Field -- (Requires an Integer Value)\n\n");
        }
    }

    /**
     * Checks the name field for validity and appends an error message to the error information if necessary.
     *
     * @param name The name value to check.
     * @return A NumberFormatException object if the name field is empty, `null` otherwise.
     */
    public static NumberFormatException checkNameField(String name) {
        if (name.isEmpty()) {
            errorInfo.append(" - Name field -- (Cannot be Null)\n\n");
            return new NumberFormatException();
        }
        return null;
    }

    /**
     * Checks the company name field for validity and appends an error message to the error information if necessary.
     *
     * @param name The company name value to check.
     * @return A NumberFormatException object if the company name field is empty, `null` otherwise.
     */
    public static NumberFormatException checkCompanyNameField(String name) {
        if (name.isEmpty()) {
            errorInfo.append(" - Company Name Field -- (Cannot be Null)\n\n");
            return new NumberFormatException();
        }
        return null;
    }

    /**
     * Checks the machine ID field for validity and appends an error message to the error information if necessary.
     *
     * @param id The machine ID value to check.
     * @return A NumberFormatException object if the machine ID is not a valid integer, `null` otherwise.
     */
    public static NumberFormatException checkMachineId(String id) {
        if (!ErrorBuilder.isInteger(id)) {
            errorInfo.append(" - Machine ID Field -- (Requires an Integer Value)\n");
            return new NumberFormatException();
        }
        return null;
    }

    /**
     * Checks the minimum and maximum values for validity and displays an error message if the minimum is greater than the maximum.
     *
     * @param min The minimum value to check.
     * @param max The maximum value to check.
     * @return `true` if the minimum is less than or equal to the maximum, `false` otherwise.
     */
    public static boolean checkMinMax(int min, int max) {
        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Maximum and Minimum Values");
            alert.setContentText("The minimum inventory can not be greater than the maximum inventory");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Checks the stock value against the minimum and maximum values and displays an error message if the stock is outside the valid range.
     *
     * @param stock The stock value to check.
     * @param min   The minimum value to check against.
     * @param max   The maximum value to check against.
     * @return `true` if the stock is within the valid range, `false` otherwise.
     */
    public static boolean checkStockMinMax(int stock, int min, int max) {
        if (stock > max || stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Inventory value");
            alert.setContentText("The required Inventory must be a value between the maximum and the minimum.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Displays an error message in an alert dialog box.
     *
     * @param errorTitle   The title of the error message.
     * @param errorContext The content of the error message.
     */
    public static void displayErrorMessage(String errorTitle, String errorContext ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setContentText(errorContext);
        alert.showAndWait();
    }

    /**
     * Overloaded version of DisplayErrorMessage that includes input for setting the header text in an alert dialog box.
     *
     * @param errorTitle   The title of the error message.
     * @param errorHeader  The header of the error message.
     * @param errorContext The content of the error message.
     */
    public static void displayErrorMessage(String errorTitle, String errorHeader, String errorContext) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorHeader);
        alert.setContentText(errorContext);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation message dialog with the specified title and content.
     *
     * @param errorTitle    The title of the confirmation message dialog.
     * @param errorContent  The content of the confirmation message dialog.
     * @return              True if the user confirms the message by clicking the OK button, false otherwise.
     */
    public static boolean displayConfirmationMessage(String errorTitle, String errorContent ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(errorTitle);
        alert.setContentText(errorContent);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }
}


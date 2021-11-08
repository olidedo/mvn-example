package business;

import database.DatabaseConnection;
import database.DatabaseHelper;
import models.Customer;
import models.Employee;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int choice;
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseHelper databaseHelper = new DatabaseHelper(databaseConnection);
        showMenu();

        try {
            while (choice != -1) {
                if (choice == 1) {
                    List<Customer> customerList = databaseHelper.getAllCustomers();
                    for (Customer customer : customerList) {
                        System.out.println("FirstName: " + customer.getFirstName() + " LastName: " + customer.getLastName());
                    }
                    showMenu();
                } else if (choice == 2) {
                    System.out.println("Enter order number:");
                    int orderNumber = input.nextInt();
                    String orderStatus = databaseHelper.getOrderStatus(orderNumber);
                    System.out.println("Status: " + orderStatus);
                    showMenu();
                } else if (choice == 3) {
                    System.out.println("Employee Code:");
                    int employeeCode = input.nextInt();
                    System.out.println("First Name:");
                    String firstName = input.next();
                    System.out.println("Last Name:");
                    String lastName = input.next();
                    System.out.println("Extension:");
                    String extension = input.next();
                    System.out.println("Email:");
                    String email = input.next();
                    System.out.println("Office Code:");
                    int officeCode = input.nextInt();
                    System.out.println("Reports to:");
                    int reportsTo = input.nextInt();
                    System.out.println("Job Title:");
                    String jobTitle = input.next();
                    Employee employee = databaseHelper.addEmployee(employeeCode, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle);
                    if (employee != null) {
                        System.out.println("Successfully added!");
                    } else {
                        System.out.println("Error adding employee!");
                    }
                    showMenu();
                } else if (choice == 4) {
                    System.out.println("Enter customer number:");
                    int customerNumber = input.nextInt();
                    String address = databaseHelper.getCustomerOffice(customerNumber);
                    System.out.println("Address of Office: " + address);
                    showMenu();
                } else if (choice == 5) {
                    System.out.println("Enter date in format YYYY-MM-DD:");
                    String inputDate = input.next();
                    Date date = Date.valueOf(inputDate);
                    int numberOfOrders = databaseHelper.getNumberOfOrders(date);
                    if (numberOfOrders < 0) {
                        System.out.println("No orders in this date");
                    } else {
                        System.out.println("Number of Orders on " + inputDate + ": " + numberOfOrders);
                    }
                    showMenu();
                } else if (choice == 6) {
                    System.out.println("Enter employee number:");
                    int employeeNumber = input.nextInt();
                    int affectedRows = databaseHelper.deleteEmployee(employeeNumber);
                    if(affectedRows!=1){
                        System.out.println("Error deleting employee!");
                    }
                    else {
                        System.out.println("Employee deleted!");
                    }
                    showMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }
    private static void showMenu() {
        System.out.println("1- Get all customer's names and surnames");
        System.out.println("2- Get order status");
        System.out.println("3- Add an employee");
        System.out.println("4- Find office of customer");
        System.out.println("5- Find number of orders in a certain date");
        System.out.println("6- Delete an employee");
        System.out.println("Enter the number of an option or -1 to end:");
        choice = input.nextInt();
    }
}

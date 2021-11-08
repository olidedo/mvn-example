package database;

import models.Customer;
import models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private Connection connection;

    public DatabaseHelper(DatabaseConnection databaseConnection) {
        connection = databaseConnection.getConnection();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT customerNumber, contactFirstName, contactLastName FROM customers";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int customerNumber = resultSet.getInt("customerNumber");
                String firstName = resultSet.getString("contactFirstName");
                String lastName = resultSet.getString("contactLastName");
                Customer customer = new Customer(customerNumber, firstName, lastName);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }

    public String getOrderStatus(int orderNumber) {
        String orderStatus = "";
        String query = "SELECT status FROM orders WHERE orderNumber=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderStatus = resultSet.getString("status");
            } else {
                orderStatus = "Not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderStatus;
    }

    public Employee addEmployee(int employeeNumber, String firstName, String lastName, String extenstion, String email, int officeCode, int reportsTo, String jobTitle) {
        Employee employee = null;
        String query = "INSERT INTO employees VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        boolean officeExists = getOffice(officeCode);
        boolean employeeExists = getEmployee(reportsTo);
        if(officeExists && employeeExists) {
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, employeeNumber);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(4, extenstion);
                preparedStatement.setString(5, email);
                preparedStatement.setInt(6, officeCode);
                preparedStatement.setInt(7, reportsTo);
                preparedStatement.setString(8, jobTitle);
                employee = new Employee(employeeNumber, firstName, lastName, extenstion, email, officeCode, reportsTo, jobTitle);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return employee;
    }

    public boolean getOffice(int officeCode) {
        boolean exists = false;
        String query = "SELECT * FROM offices WHERE officeCode=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, officeCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }
    public boolean getEmployee(int employeeNumber) {
        boolean exists = false;
        String query = "SELECT * FROM employees WHERE employeeNumber=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    public String getCustomerOffice(int customerNumber) {
        String officeAddress = "";
        String query = "SELECT o.addressLine1\n" +
                "FROM customers c JOIN employees e ON e.employeeNumber = c.salesRepEmployeeNumber\n" +
                "JOIN offices o ON e.officeCode= o.officeCode\n" +
                "WHERE c.customerNumber = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                officeAddress = resultSet.getString("o.addressLine1");
            } else {
                officeAddress = "Not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return officeAddress;
    }

    public int getNumberOfOrders(Date date) {
        int numberOfOrders = -1;
        String query = "SELECT count(*) AS nrOfOrders \n" +
                "FROM orders \n" +
                "GROUP BY orderDate\n" +
                "HAVING orderDate=?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfOrders = resultSet.getInt("nrOfOrders");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfOrders;
    }

    public int deleteEmployee(int employeeNumber) {
        int affectedRows = 0;
        String query = "DELETE FROM employees WHERE employeeNumber=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeNumber);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return affectedRows;
    }
}

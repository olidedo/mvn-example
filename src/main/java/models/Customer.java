package models;

import javafx.beans.DefaultProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class Customer {
    private int customerNumber;
    private String firstName;
    private String lastName;

    public Customer(int customerNumber, String firstName, String lastName) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

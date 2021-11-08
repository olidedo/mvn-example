package models;

import lombok.*;

@Data @ToString @Getter @Setter @NoArgsConstructor
public class Employee {
    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String extension;
    private String email;
    private int officeCode;
    private int reportsTo;
    private String jobTitle;

    public Employee(int employeeNumber, String firstName, String lastName, String extension, String email, int officeCode, int reportsTo, String jobTitle) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.extension = extension;
        this.email = email;
        this.officeCode = officeCode;
        this.reportsTo = reportsTo;
        this.jobTitle = jobTitle;
    }
}

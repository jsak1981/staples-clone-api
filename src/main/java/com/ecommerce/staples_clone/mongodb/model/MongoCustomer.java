package com.ecommerce.staples_clone.mongodb.model;

import java.time.OffsetDateTime;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*### Explanation of the Added Constraints

I have added two types of constraints to the document in your Canvas:
1.  **Database-Level Constraints (`@Indexed`):**
* `@Indexed(unique = true)`: This is a Spring Data MongoDB annotation. When the application starts, it will
instruct the MongoDB server to create a **unique index** on the `customerId` and `email` fields.
This is a very powerful feature. If you try to insert a new customer with an email that already exists,
the MongoDB database itself will reject the operation. This provides the strongest guarantee of uniqueness.

2.  **Application-Level Validation (`javax.validation`):**
* `@NotEmpty` and `@NotNull`: These annotations are from the standard Java Bean Validation API.
They ensure that fields like `firstName`, `lastName`, and `email` are not submitted as empty or null.

It is critical to understand that the `@NotEmpty` and `@Email` annotations are **not** automatically
enforced. You need to enable them in your controller.

To make them work, you would:
1.  Add the `spring-boot-starter-validation` dependency to your `pom.xml`.
2.  In your `MongoCustomerController`, add the `@Valid` annotation to your `createCustomer` method like this:

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public MongoCustomer createCustomer(@Valid @RequestBody MongoCustomer customer) {
	return customerService.createCustomer(customer);
}*/

@Document(collection = "customers")
public class MongoCustomer {

  @Id private String id;

  // @Indexed(unique = true) tells MongoDB to create a unique index on this field.
  @Indexed(unique = true)
  @Field(name = "customerId")
  @NotNull
  private String customerId;

  @NotEmpty(message = "First name cannot be empty!")
  private String firstName;

  @NotEmpty(message = "Last name cannot be empty!")
  private String lastName;

  @Indexed(unique = true)
  @NotEmpty(message = "Email cannot be empty.")
  @Email(message = "Email should be valid.")
  private String email;

  private OffsetDateTime createdDate;

  private List<Address> addresses;

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }
}

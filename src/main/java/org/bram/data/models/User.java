package org.bram.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection="Users")
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
    private String password;
    private UserRole userRole;
    private boolean isLoggedIn;
    private boolean isBanned;
}

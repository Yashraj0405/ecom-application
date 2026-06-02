package com.app.ecom.entity;


import com.app.ecom.utils.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor //Useful when you want to create an instance of the class with all fields initialized.
//@Entity(name = "user_table") //This annotation is used to specify the name of the database table that this entity will be mapped to. If not specified, it defaults to the class name.
@Entity
@Table(name = "Users") //This annotation is used to specify the name of the database table that this entity will be mapped to. If not specified, it defaults to the class name.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role = UserRole.CUSTOMER; // Default role is CUSTOMER
}

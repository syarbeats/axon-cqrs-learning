package com.mts.cdc.axoncqrsapp.command;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddUserCommand {
    private final String email;
    private final String password;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final Integer id;


    public AddUserCommand(Integer id, String email, String password, String username, String firstname, String lastname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}

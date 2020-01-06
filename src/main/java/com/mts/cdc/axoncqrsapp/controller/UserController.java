package com.mts.cdc.axoncqrsapp.controller;

import com.mts.cdc.axoncqrsapp.command.NewOrderCommand;
import com.mts.cdc.axoncqrsapp.dto.UserDto;
import com.mts.cdc.axoncqrsapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/add-user")
    @Transactional
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        log.info("User Data-ID: {}", user.getId());
        log.info("User Data-Name: {}", user.getFirstname());
        commandGateway.sendAndWait(new NewOrderCommand(user.getId(), user.getEmail(), user.getPassword(), user.getUsername(), user.getFirstname(), user.getLastname()));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<UserDto> queryResult = jdbcTemplate.query("SELECT * from user_view", (rs, rowNum) -> {
            return new UserDto(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("firstname"), rs.getString("lastname"));
        });

        return ResponseEntity.ok(queryResult);
    }
}

package com.mts.cdc.axoncqrsapp.commandhandler;

import com.mts.cdc.axoncqrsapp.command.AddUserCommand;
import com.mts.cdc.axoncqrsapp.model.User;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserCommandHandler {

    @Autowired
    @Qualifier("userRepository")
    private Repository<User> userRepository;

    @CommandHandler
    public void handle(AddUserCommand addUserCommand){
        log.info("Command Handler...");
        User user = new User(addUserCommand.getId(), addUserCommand.getUsername(), addUserCommand.getPassword(), true, addUserCommand.getEmail(), addUserCommand.getFirstname(), addUserCommand.getLastname());
        userRepository.add(user);
    }
}

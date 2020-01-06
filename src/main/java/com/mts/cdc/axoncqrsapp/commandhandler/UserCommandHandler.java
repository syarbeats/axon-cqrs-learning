package com.mts.cdc.axoncqrsapp.commandhandler;

import com.mts.cdc.axoncqrsapp.command.NewOrderCommand;
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
    public void handle(NewOrderCommand newOrderCommand){
        log.info("Command Handler...");
        User user = new User(newOrderCommand.getId(), newOrderCommand.getUsername(), newOrderCommand.getPassword(), true, newOrderCommand.getEmail(), newOrderCommand.getFirstname(), newOrderCommand.getLastname());
        userRepository.add(user);
    }
}

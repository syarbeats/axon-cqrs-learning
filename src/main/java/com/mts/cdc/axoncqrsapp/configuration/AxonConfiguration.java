package com.mts.cdc.axoncqrsapp.configuration;

import com.mts.cdc.axoncqrsapp.model.User;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.repository.GenericJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AxonConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public SimpleCommandBus commandBus(){
        return new SimpleCommandBus();
    }

    @Bean
    public SimpleEventBus eventBus(){
        return new SimpleEventBus();
    }

    @Bean
    AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(){
        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus());

        return handler;
    }

    @Bean
    AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(){
        AnnotationEventListenerBeanPostProcessor listener = new AnnotationEventListenerBeanPostProcessor();
        listener.setEventBus(eventBus());

        return listener;
    }

    @Bean
    public DefaultCommandGateway commandGateway(){
        return new DefaultCommandGateway(commandBus());
    }

    @Bean
    @Qualifier("userRepository")
    public GenericJpaRepository<User> userJpaRepository(){
        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(entityManager);
        GenericJpaRepository<User> genericJpaRepository = new GenericJpaRepository<>(entityManagerProvider, User.class);
        genericJpaRepository.setEventBus(eventBus());

        return genericJpaRepository;
    }


}

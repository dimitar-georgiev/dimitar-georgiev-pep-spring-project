package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    // create new message
    public Message createMessage(Message message) throws BadRequestException{
        Account existingUser = accountService.findAccountById(message.getPostedBy());

        if (existingUser == null) {
            throw new BadRequestException("Invalid user id");
        }

        String msgTxt = message.getMessageText();

        if (msgTxt.isBlank() || msgTxt.length() > 254) {
            throw new BadRequestException("Message text must be not empty and no longer than 254 characters");
        }

        Message newMessage = messageRepository.save(message);

        return newMessage;
    }

    // get all messsges
    public List<Message> getAllMessages () {
        
        return null;
    }
}

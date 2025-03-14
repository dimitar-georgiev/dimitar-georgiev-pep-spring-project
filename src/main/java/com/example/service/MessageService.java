package com.example.service;

import java.util.List;
import java.util.Optional;

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
        return messageRepository.findAll();
    }

    // find message by id
    public Message findMessageById(String msgId) {
        Optional<Message> optinalMsg =  messageRepository.findById(Integer.parseInt(msgId));
        if (optinalMsg.isPresent()) {
            return optinalMsg.get();
        }
        return null;
    }

    // delete message by id
    public Integer deleteMessageById(String msgId) {
        Message existinMessage = findMessageById(msgId);

        if (existinMessage != null) {
            messageRepository.deleteById(Integer.parseInt(msgId));
            return 1;
        }
        return null;
    }

    // update message by id
    public Integer updateMessageById(String msgId, Message newMessage) throws BadRequestException {
        Message existinMessage = findMessageById(msgId);

        if (existinMessage == null) {
            throw new BadRequestException("Message not found");
        }

        String newMsgTxt = newMessage.getMessageText();

        if (newMsgTxt.isBlank() || newMsgTxt.length() > 254) {
            throw new BadRequestException("Message text must be not empty and no longer than 254 characters");
        }

        existinMessage.setMessageText(newMsgTxt);

        messageRepository.save(existinMessage);
        return 1;
    }

    // get all messages by account id
    public List<Message> getAllMessagesByAccountId(String accId) {
        return messageRepository.findByPostedBy(Integer.parseInt(accId));
    }
}

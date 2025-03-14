package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.UnathorizedException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    // User Registration
    // POST /register
    // success: 200 + user JSON
    // duplicate username: 409
    // invalid user input: 400
    
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) throws BadRequestException, ConflictException{
        return ResponseEntity.status(200).body(accountService.registerAccount(newAccount));
    }

    // Login
    // POST /login
    // success: 200 + user JSON
    // fail: 401
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws UnathorizedException {
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    // Create New Message
    // POST /messages
    // success: 200 + message JSON
    // fail: 400
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) throws BadRequestException {
        Message createdMessage = messageService.createMessage(newMessage);
        return ResponseEntity.status(200).body(createdMessage);
    }

    // Get All Messages
    // GET /messages
    // success: 200 + messages JSON
    // no message: 200 + empty JSON
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    // Get Message By ID
    // GET /messages/{message_id}
    // success: 200 + message JSON
    // no such message: 200 +  empty body
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable String message_id) {
        return ResponseEntity.status(200).body(messageService.findMessageById(message_id));
    }

    // Delete Message By ID
    // DELETE /messages/{message_id}
    // success: 200 + number of rows deleted
    // no such message: empty body
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById (@PathVariable String message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    // Update Message By ID
    // PATCH /messages/{message_id}
    // success: 200 + number of rows affected
    // fail: 400 + empty body
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById (@PathVariable String message_id, @RequestBody Message newMessage) throws BadRequestException {
        return ResponseEntity.status(200)
            .body(messageService.updateMessageById(message_id, newMessage));
    }

    // Get All Messages By User ID
    // GET /accounts/{account_id}/messages
    // success: 200 + messages JSON
    // no messages: 200 + empty JSON
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId (@PathVariable String account_id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(account_id));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException (BadRequestException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<String> handleUnathorizedException(UnathorizedException ex) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
}

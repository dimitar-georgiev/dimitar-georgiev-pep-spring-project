package com.example.controller;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    // User Registration
    // POST localhost:8080/register
    // success: 200 + user JSON
    // duplicate username: 409
    // fail: 400

    // Login
    // POST localhost:8080/login
    // success: 200 + user JSON
    // fail: 401

    // Create New Message
    // POST localhost:8080/messages
    // success: 200 + message JSON
    // fail: 400

    // Get All Messages
    // GET localhost:8080/messages
    // success: 200 + messages JSON
    // no message: 200 + empty JSON

    // Get Message By ID
    // GET localhost:8080/messages/{message_id}
    // success: 200 + message JSON
    // no such message: 200 +  empty body

    // Delete Message By ID
    // DELETE localhost:8080/messages/{message_id}
    // success: 200 + number of rows deleted
    // no such message: empty body

    // Update Message By ID
    // PATCH localhost:8080/messages/{message_id}
    // success: 200 + number of rows affected
    // fail: 400 + empty body

    // Get All Messages By User ID
    // GET localhost:8080/accounts/{account_id}/messages
    // success: 200 + messages JSON
    // no messages: 200 + empty JSON
}

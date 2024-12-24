package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.*;
import Model.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final AccountService accountService;
    
    private final MessageService messageService;
    private final ObjectMapper mapper = new ObjectMapper();
        
    
        public SocialMediaController(){
            this.accountService = new AccountService();
            this.messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessage);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUser(Context ctx) throws JsonProcessingException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.createAccount(account);
        if(createdAccount != null){
            ctx.json(createdAccount).status(200);
        }else{
            ctx.status(400);
        }
    }
    private void login(Context ctx)throws JsonProcessingException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account existingAccount = accountService.getAccountByUsernameAndPassword(account.username, account.password);
        if(existingAccount != null){
            ctx.json(existingAccount).status(200);
        }else{
            ctx.status(401);
        }
    }
    private void createMessage(Context ctx) throws JsonProcessingException{
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            ctx.json(createdMessage).status(200);
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessages(Context ctx){
        ctx.json(messageService.getAllMessage()).status(200);
    }
    private void getMessageById(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message == null){
            ctx.result("").status(200);
        }else{
            ctx.json(message).status(200);
        }
    }
    private void deleteMessage(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.getMessageById(messageId);
        boolean deleted = messageService.deleteMessage(messageId);
        if(!deleted){
            ctx.result("").status(200);
        }else{
            ctx.json(deletedMessage).status(200);
        }
    }
    private void updateMessage(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        message.setMessage_id(messageId);
        Message updatedMessage = messageService.updateMessage(message);
        if(updatedMessage != null){
            ctx.json(updatedMessage).status(200);
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessagesByUser(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesByUserId(accountId)).status(200);
    }



}
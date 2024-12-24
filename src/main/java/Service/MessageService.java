package Service;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService(){
        this.messageDAO = new MessageDAO();
    }
    public Message createMessage(Message message){
        if(message.message_text == null || message.message_text.isBlank() || message.message_text.length()>255){
            return null;
        }
        return messageDAO.createMessage(message);
    }
    public List<Message> getAllMessage(){
        return messageDAO.getAllMessage();
    }
    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }
    public boolean deleteMessage(int messageId){
        return messageDAO.deleteMessage(messageId);
    }
    public Message updateMessage(Message message){
        if(message.message_text == null || message.message_text.isBlank() || message.message_text.length()>255){
            return null;
        }
        Message existingMessage = messageDAO.getMessageById(message.message_id);
        if(existingMessage == null){
            return null;
        }
        message.setPosted_by(existingMessage.posted_by);
        message.setTime_posted_epoch(existingMessage.time_posted_epoch);
        boolean isUpdated = messageDAO.updateMessage(message);
        return isUpdated? message : null;

    }
    public List<Message> getAllMessagesByUserId(int userId){
        return messageDAO.getAllMessagesByUserId(userId);
    }
}

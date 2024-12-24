package DAO;

import java.sql.*;
import java.util.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(Message message){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by,message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);
            ps.setLong(3, message.time_posted_epoch);

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                int messageId = keys.getInt(1);
                return new Message(messageId,message.posted_by,message.message_text,message.time_posted_epoch);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public List<Message>getAllMessage(){
        Connection cn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
            messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
    public Message getMessageById(int messageId){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message Where message_id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            }   
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deleteMessage(int messageId){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, messageId);
            int rs = ps.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateMessage(Message message){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, message.message_text);
            ps.setInt(2, message.message_id);

            return ps.executeUpdate()>0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Message>getAllMessagesByUserId(int accountId){
        Connection cn = ConnectionUtil.getConnection();
        List <Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }   
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
}

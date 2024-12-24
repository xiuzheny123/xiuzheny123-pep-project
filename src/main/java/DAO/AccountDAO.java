package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    public Account createAccount(Account account){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username,password) VALUES(?,?)";
            PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                int accountId= keys.getInt(1);
                return new Account(accountId, account.username, account.password);
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
    public Account getAccountByUsernameAndPassword(String username,String password){
        Connection cn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("account_id"),username,password);
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
    
}

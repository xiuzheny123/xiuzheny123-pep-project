package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
    public Account createAccount(Account account){
        if(account.username == null || account.username.isBlank()){
            return null;
        }
        if(account.password == null || account.password.length()<4){
            return null;
        }
        return accountDAO.createAccount(account);
        //Not sure why there is createdAccount in here, check if there is bugs.

    }
    public Account getAccountByUsernameAndPassword(String username,String password){
        return accountDAO.getAccountByUsernameAndPassword(username,password);


    }
}

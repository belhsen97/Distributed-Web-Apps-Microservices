package tn.esprit.userservice.Services;


import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Libs.IGenericCRUD;

import java.util.List;

public interface IAccountService extends IGenericCRUD<Account,Long> {
    Account selectbyUsername(String  Username);
    List<Account> selectbyMultipleUsername(String[] usernames);
}

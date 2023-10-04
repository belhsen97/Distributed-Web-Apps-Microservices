package tn.esprit.userservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.esprit.userservice.Dtos.AccountDto;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Mappers.AccountMapper;
import tn.esprit.userservice.Services.IAccountService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/account")
public class AccountController {
    private  IAccountService iAccountService;
    @Autowired
    public AccountController(@Qualifier("account-service") IAccountService iAccountService){this.iAccountService = iAccountService;}

    @GetMapping
    public List<AccountDto> SelectAll () {
        List<Account>  accounts = iAccountService.selectAll () ;
        return accounts.stream().map(account -> AccountMapper.mapToDto(account)) .collect(Collectors.toList());
       }
    @GetMapping("{id}")
    public ResponseEntity<AccountDto> SelectById (@PathVariable long id) {
        Account account = iAccountService.selectById (id);
        return ResponseEntity.ok(AccountMapper.mapToDto(account));
    }
    @GetMapping("select-by-username/{username}")
    public  ResponseEntity<AccountDto> selectbyUsername( @PathVariable("username") String  username){
        return ResponseEntity.ok(AccountMapper.mapToDto(iAccountService.selectbyUsername(username)));}
    @GetMapping("select-by-usernames/{usernames}")
    public  List<AccountDto> selectbyMultipleUsername( @PathVariable("usernames") String[] usernames){
       final List<Account>  accounts = iAccountService.selectbyMultipleUsername(usernames) ;
        return accounts.stream().map(account -> AccountMapper.mapToDto(account)) .collect(Collectors.toList());
    }
    @PostMapping
    public AccountDto Insert(@Validated @RequestBody AccountDto accountDto) {
        Account account =  AccountMapper.mapToEntity(accountDto);
        return AccountMapper.mapToDto(  iAccountService.insert(account));}
    @PutMapping("{id}")
    public  ResponseEntity<AccountDto> update( @PathVariable long id ,@RequestBody AccountDto accountDto){
        Account account =  AccountMapper.mapToEntity(accountDto);
        return ResponseEntity.ok(
                AccountMapper.mapToDto( iAccountService.update( id,  account))
        );}
    @DeleteMapping("{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Long id ){
        iAccountService.delete( id );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); }


}


package tn.esprit.userservice.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tn.esprit.userservice.Dtos.*;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Entitys.User;
import tn.esprit.userservice.Mappers.UserMapper;
import tn.esprit.userservice.Services.FileService;
import tn.esprit.userservice.Services.IUserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/user")
public class UserController  {
    private IUserService iUserService;
    @Autowired
    public UserController(@Qualifier("user-service") IUserService iUserService){this.iUserService = iUserService;}

    @GetMapping
    public List<UserDto> SelectAll () {
        List<User>  users =    iUserService.selectAll ();
        return users.stream().map(user -> UserMapper.mapToDto(user)) .collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDto> SelectBy (@PathVariable long id) {
        return ResponseEntity.ok( UserMapper.mapToDto( iUserService.selectById (id))) ;
    }
     @PostMapping
     public UserDto Insert( @RequestBody UserDto user) {
        return UserMapper.mapToDto( iUserService.insert(UserMapper.mapToEntity( user )) );}

    @PutMapping
    public  ResponseEntity<UserDto> update( @RequestBody UserDto user){
        return  ResponseEntity.ok( UserMapper.mapToDto(  iUserService.update(2L,UserMapper.mapToEntity( user))));
    }
    @DeleteMapping("{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Long id ){
        iUserService.delete( id ); return new ResponseEntity<>(HttpStatus.NO_CONTENT); }










    @PostMapping("/register")
    public ResponseEntity<MsgReponseStatusDto> register(@Validated  @RequestBody AuthenticationRequestDto request) throws IOException, InterruptedException {
        return ResponseEntity.ok(iUserService.register(request));}

    @GetMapping ("/confirm-email/{username}/{code}")
    public RedirectView confirmEmail(@PathVariable("username") String username , @PathVariable("code")  String code) {
        if ( iUserService.confirmEmail( username , code).getStatus() == ReponseStatus.SUCCESSFUL)
        { return new RedirectView(FileService.pageHomeLink+FileService.pathSignIn); }
        return new RedirectView(FileService.pageHomeLink+FileService.pathError);  }

    @PutMapping("/update-role-and-activate/{username}/{role}/{enabled}")
    public  ResponseEntity<MsgReponseStatusDto>  updateRoleAndActivate (@PathVariable("username")String username , @PathVariable("role") Roles role, @PathVariable("enabled") boolean enabled){
        return ResponseEntity.ok( iUserService.updateRoleAndActivate(  username ,  role,  enabled));}

    @PutMapping("/mail-code-forgot-password/{username}/{email}")
    public  ResponseEntity<MsgReponseStatusDto> sendMailCodeForgotPassword( @PathVariable("username")String username , @PathVariable("email") String email) throws IOException, InterruptedException {
        return ResponseEntity.ok( iUserService.sendMailCodeForgotPassword( username ,  email));}

    @PutMapping("/update-forgot-password/{code}/{newpassword}")
    public  ResponseEntity<MsgReponseStatusDto>  updateForgotPassword ( @PathVariable("code")String code , @PathVariable("newpassword") String newPassword){
        return ResponseEntity.ok( iUserService.updateForgotPassword( code,  newPassword) );}

    @PutMapping("update-password/{username}/{currentpassword}/{newpassword}")
    public ResponseEntity<MsgReponseStatusDto>  updatePassword(@PathVariable("username")  String usename,
                                                               @PathVariable("currentpassword")  String currentPassword,
                                                               @PathVariable("newpassword")  String newPassword){
        return  ResponseEntity.ok((MsgReponseStatusDto) iUserService.updatePassword(usename,currentPassword,newPassword));}

}
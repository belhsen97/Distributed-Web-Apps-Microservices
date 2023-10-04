package tn.esprit.userservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.userservice.Configures.MyConfigInitParameters;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Dtos.ReponseStatus;
import tn.esprit.userservice.Dtos.mail.BodyContent;
import tn.esprit.userservice.Dtos.mail.Msg;
import tn.esprit.userservice.Dtos.mail.TypeBody;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Entitys.User;
import tn.esprit.userservice.Libs.GenericCRUDService;
import tn.esprit.userservice.Libs.IObjectMapperConvert;
import tn.esprit.userservice.Repositorys.UserRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


@Service("user-service")
public class UserService  extends GenericCRUDService<User,Long> implements IUserService {
    @Autowired
    IObjectMapperConvert iObjectMapperConvert;
    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAccountService  iaccountService;
    private final IFileService ifileService;
    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       IAccountService  iaccountService,
                       IFileService  ifileService){
            super(userRepository);
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.iaccountService = iaccountService;
            this.ifileService = ifileService;
    }
    @Override
    public MsgReponseStatusDto register(AuthenticationRequestDto request) throws IOException, InterruptedException {
        if( userRepository. isCorrectUserName( request.getUsername()  ))
        {   return MsgReponseStatusDto.builder()
                .title("Register User")
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("other username found").build();}
        String  code = this.getRandomNumber( 100 , 999 ) +"-"+this.getRandomNumber( 100 , 999 ) +"-"+this.getRandomNumber(  100 , 999) +"-"+this.getRandomNumber(  100 , 999  );
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true) //false
                .code(code)
                .role(Roles.STUDENT)
                .build();
        Account account = Account.builder().
                createdAt(LocalDateTime.now()).
                email(request.getEmail()).
                user(user).build();


//        String file =  ifileService.Edit_ConfirmMailPage(user.getUsername(),
//                MyConfigInitParameters.staticLinkServiceUser +"/user/confirm-email/"+user.getUsername()+"/"+code);
//        Msg msg = Msg.builder().subject("Confirmation Address Mail")
//                .email(account.getEmail())
//                .text("body")
//                .bodyContents(new ArrayList<BodyContent>(){{add(BodyContent.builder().content(file).type(TypeBody.HTML).build());}}).build();
//        WebsocketMessageModel websocketMessageModel = WebsocketMessageModel.builder()
//                .topic("mail-with-multi-body-content")
//                .senderName("user-service")
//                .destinationName("mail-service")
//                .data( iObjectMapperConvert.convertToJsonString(msg))
//                .build();
//        String content =  iObjectMapperConvert.convertToJsonString(websocketMessageModel);
//        sessionHolder.sendbinaryMessage(content);
        iaccountService.insert(account);
        return MsgReponseStatusDto.builder()
                .title("Register User")
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("To complete next step you should verify confirmation mail").build();

    }
    @Override
    public MsgReponseStatusDto confirmEmail(String username ,   String code)
    {
        User user =  userRepository.findUserByCodeAndUsername( code, username).orElse(null);
        if ( user != null ){
            user.setEnabled(true);
            user.setCode("");
            userRepository.save(user);
            return MsgReponseStatusDto.builder()
                    .title("Confirmation Email User")
                    .status(ReponseStatus.SUCCESSFUL)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("you can login authentication").build();
        }
        return MsgReponseStatusDto.builder()
                .title("Confirmation Email User")
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Perhaps we can't find  your register").build(); }
    @Override
    public MsgReponseStatusDto sendMailCodeForgotPassword(String username , String email) throws IOException, InterruptedException {
//        if ( !userRepository. isCorrectEmailAndUsername(username, email)){
//            return MsgReponseStatusDto.builder()
//                    .title("Message")
//                    .status(ReponseStatus.ERROR)
//                    .datestamp(LocalDate.now())
//                    .timestamp(LocalTime.now())
//                    .message("Your mail not correct").build();}
//        final String code =  this.getRandomNumber( 100 , 999 ) +"-"+
//                this.getRandomNumber( 100 , 999 ) +"-"+
//                this.getRandomNumber(  100 , 999) +"-"+
//                this.getRandomNumber(  100 , 999  );
//        User  user = userRepository.findUserByUsernameAndEmail( username ,  email ).orElse(null);
//        user.setCode(code);
//        userRepository.save(user);
//
//
//
//
//        String file =  ifileService.Edit_forgotPasswordPage(user.getUsername(), code );
//        Msg msg = Msg.builder().subject("Forgot Password")
//                               .email(email)
//                               .text("body")
//                               .bodyContents(new ArrayList<BodyContent>(){{add(BodyContent.builder().content(file).type(TypeBody.HTML).build());}}).build();
//        WebsocketMessageModel websocketMessageModel = WebsocketMessageModel.builder()
//                .topic("mail-with-multi-body-content")
//                .senderName("user-service")
//                .destinationName("mail-service")
//                .data( iObjectMapperConvert.convertToJsonString(msg))
//                .build();
//        String content =  iObjectMapperConvert.convertToJsonString(websocketMessageModel);
//        sessionHolder.sendbinaryMessage(content);
//
//        return MsgReponseStatusDto.builder()
//                .title("Message")
//                .status(ReponseStatus.SUCCESSFUL)
//                .datestamp(LocalDate.now())
//                .timestamp(LocalTime.now())
//                .message("Your mail is correct so you see your email for complete next step").build();
 return null;
   }
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    @Override
    public MsgReponseStatusDto updateForgotPassword(String code, String newPassword) {
        if (   userRepository.isCorrectCode( code ) && !newPassword.isEmpty()) {
            User user= userRepository.findUserByCode( code).orElse(null);
            if (user.getCode()=="" || user.getCode().isEmpty() ){
                return MsgReponseStatusDto.builder()
                        .status(ReponseStatus.ERROR)
                        .datestamp(LocalDate.now())
                        .timestamp(LocalTime.now())
                        .message("you didn't  sent  Forgot Password in last moment").build();
            }
            user.setPassword(   passwordEncoder.encode(newPassword)   );
            user.setCode("");
            userRepository.save(user);
            return MsgReponseStatusDto.builder()
                    .status(ReponseStatus.SUCCESSFUL)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("Your code is correct and we change password").build();}
        else { return MsgReponseStatusDto.builder()
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Your code is not correct or new password is empty").build();}
    }
    @Override
    @Transactional
    public MsgReponseStatusDto updatePassword(String usename,String currentPassword, String newPassword) {
        if( newPassword.isEmpty() )
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("New Password empty").build();}

        if( !userRepository. isCorrectUserName( usename ))
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Cannot found username verify you enter correct")
                .build();}

        User user = userRepository.findUserByUsername( usename ).get();
        boolean matches = passwordEncoder.matches(currentPassword , user.getPassword());
        if( !matches ){   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Verify your current password")
                .build();}

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update password")
                .build();
    }
    @Override
    @Transactional
    public MsgReponseStatusDto updateRoleAndActivate(String username , Roles role, boolean enabled) {
        if( !userRepository. isCorrectUserName( username ))
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Cannot found username verify you enter correct").build();}
        User user = userRepository.findUserByUsername( username ).orElse(null);
        user.setRole(role);
        user.setEnabled(enabled);
        userRepository.save(user);
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update role and state enable user").build();
    }













}

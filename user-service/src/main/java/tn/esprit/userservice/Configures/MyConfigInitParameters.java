package tn.esprit.userservice.Configures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tn.esprit.userservice.Mappers.AttachmentMapper;
import tn.esprit.userservice.Services.FileService;

@Configuration
public class MyConfigInitParameters {
    @Value("${myApp.file.forgotPassword_HTML}")
    private String file_forgotPassword_HTML;
    @Value("${myApp.file.ConfirmMail_HTML}")
    private String file_ConfirmMail_HTML;
    @Value("${myApp.file.defaultUserPhoto}")
    private String file_defaultUserPhoto;
    @Value("${myApp.link.WebPage}")
    private String pageHomeWebPage;
    @Value("${myApp.link.Path.signIn}")
    private String pathLinkSignIn;
    @Value("${myApp.link.Path.update_password_forgot}")
    private String pathLinkPasswordForgot;
    @Value("${myApp.link.Path.error}")
    private String pathLinkError;
    @Value("${myApp.link.Path.AttachementDowload}")
    private String pathAttachementDowload;
    @Value("${myApp.link.GlobalBackEnd}")
    private String linkGlobalBackEnd;
    @Value("${server.servlet.context-path}")
    private String pathServiceUser;
    public static String staticLinkServiceUser;

    @Bean
    public String configure() {
        AttachmentMapper.host_ContextPath = linkGlobalBackEnd + pathServiceUser +pathAttachementDowload;
        //http://localhost:8099/biochar/user-service/attachment/download/
        FileService.link_forgotPassword_HTML = file_forgotPassword_HTML;
        FileService.link_ConfirmMail_HTML = file_ConfirmMail_HTML;
        FileService.defaultUserPhoto = file_defaultUserPhoto;
        FileService.pageHomeLink = pageHomeWebPage;
        FileService.pathSignIn = pathLinkSignIn;
        FileService.pathError =pathLinkError;
        FileService.pathLinkPasswordForgot = pathLinkPasswordForgot;
        MyConfigInitParameters.staticLinkServiceUser = linkGlobalBackEnd + pathServiceUser;
        return "configured";
    }

}
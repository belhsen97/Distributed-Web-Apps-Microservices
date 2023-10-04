package tn.esprit.userservice.Services;

import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Entitys.User;
import tn.esprit.userservice.Libs.IGenericCRUD;

import java.io.IOException;

public interface IUserService extends IGenericCRUD<User,Long> {
    MsgReponseStatusDto register(AuthenticationRequestDto request)throws IOException, InterruptedException;
    MsgReponseStatusDto confirmEmail(String username , String code);
    MsgReponseStatusDto sendMailCodeForgotPassword(String username , String email) throws IOException, InterruptedException;
    MsgReponseStatusDto updateForgotPassword(String code, String newPassword);
    MsgReponseStatusDto updatePassword(String usename,String currentPassword, String newPassword);
    MsgReponseStatusDto updateRoleAndActivate(String username , Roles role, boolean enabled);
}


package tn.esprit.userservice.Services;

import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.AuthenticationResponseDto;

public interface IAuthenticationService {
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws Exception;
}

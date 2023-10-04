package tn.esprit.userservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.AuthenticationResponseDto;
import tn.esprit.userservice.Services.IAuthenticationService;

@Controller
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  private  IAuthenticationService iauthenticationService;
  @Autowired
  public AuthenticationController(@Qualifier("authentication-service") IAuthenticationService iauthenticationService){this.iauthenticationService = iauthenticationService;}


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate( @RequestBody AuthenticationRequestDto request) throws Exception {
        return ResponseEntity.ok(iauthenticationService.authenticate(request));}
}

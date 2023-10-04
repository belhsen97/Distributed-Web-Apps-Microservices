package tn.esprit.userservice.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.AuthenticationResponseDto;
import tn.esprit.userservice.Dtos.ReponseStatus;
import tn.esprit.userservice.Entitys.*;
import tn.esprit.userservice.Repositorys.TokenRepository;
import tn.esprit.userservice.Repositorys.UserRepository;
import tn.esprit.userservice.Securitys.JwtService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service("authentication-service")
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new Exception("Error findByEmail"));
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return   AuthenticationResponseDto.childBuilder()
                .token(jwtToken)
                .title("Authentication")
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .status(ReponseStatus.SUCCESSFUL)
                .message("Successful to access account")
                .build();
    }
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


}














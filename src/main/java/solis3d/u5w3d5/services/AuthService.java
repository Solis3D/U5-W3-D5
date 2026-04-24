package solis3d.u5w3d5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.NotFoundException;
import solis3d.u5w3d5.exceptions.UnauthorizedException;
import solis3d.u5w3d5.payloads.LoginDTO;
import solis3d.u5w3d5.security.TokenTools;

@Service
public class AuthService {

    private final UtentiService utentiService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtentiService utentiService, TokenTools tokenTools, PasswordEncoder bcrypt) {
        this.utentiService = utentiService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            Utente trovato = this.utentiService.findByEmail(body.email());

            if(this.bcrypt.matches(body.password(), trovato.getPassword())) {
                return this.tokenTools.generateToken(trovato);
            } else {
                throw new UnauthorizedException("Credenziali errate!");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}

package com.sda.javaremote18.spring_boot.controllers.auth;

import com.sda.javaremote18.spring_boot.config.security.JwtTokenUtil;
import com.sda.javaremote18.spring_boot.controllers.users.UsersRepository;
import com.sda.javaremote18.spring_boot.models.*;
import com.sda.javaremote18.spring_boot.models.auth.*;
import com.sda.javaremote18.spring_boot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {
    private JwtTokenUtil jwtTokenUtil;
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                          AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/register")
    public ServerResponse register(@RequestBody RegisterModel registerModel) {
        // ShowData from body request
        System.out.println(registerModel.getFirstName());
        System.out.println(registerModel.getLastName());
        System.out.println(registerModel.getEmail());

        if (!registerModel.isValid()) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Campurile sunt invalide!");
        }

        UserModel userFromDB = this.usersRepository.findByEmail(registerModel.getEmail());

        if (userFromDB != null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Acest email este deja inregistrat!");
        }

        // Salvam parola primita intr-o variabila
        String currentPassword = registerModel.getPassword();

        // Criptam parola pentru a o salva in baza de date
        String newPassword = this.bCryptPasswordEncoder.encode(currentPassword);
        System.out.println(newPassword);

        UserModel user = new UserModel();
        user.setEmail(registerModel.getEmail());
        user.setPassword(newPassword);
        user.setFirstName(registerModel.getFirstName());
        user.setLastName(registerModel.getLastName());

        // Salvam User-ul in baza de date
        this.usersRepository.save(user);

        // Returnam raspuns catre client
        return new ServerResponse(HttpStatus.OK.value(), "utilizator creat cu succes", "", user);
    }


    @PostMapping("/auth/login")
    public ServerResponse loginWithToken(@RequestBody  AuthRequestModel request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserModel user = (UserModel) authenticate.getPrincipal();

            // Pregatim obiectul pentru client
            LoginResponseModel loginResponseModel = new LoginResponseModel( user); //jwtTokenUtil.generateAccessToken(user),
            return new ServerResponse(HttpStatus.OK.value(), "Bun venit " + user.getFirstName(), loginResponseModel);

        } catch (BadCredentialsException ex) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }

//    @PostMapping("/auth/login")
//    public ServerResponse login(@RequestBody LoginModel loginModel) {
//        System.out.println(loginModel.getEmail());
//        System.out.println(loginModel.getPassword());
//
//        if (!loginModel.isValid()) {
//            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Adresa de email sau parola incorecta!");
//        }
//
//        UserModel user = this.usersRepository.findByEmail(loginModel.getEmail());
//
//
//
//        if (user != null) {
//            if (this.bCryptPasswordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
//                return new ServerResponse(HttpStatus.OK.value(), "Utilizator logat cu succes", "", user);
//            }
//            // pentru cazul in care parolele nu se potrivesc
//            return new ServerResponse(HttpStatus.OK.value(), "Parola incorecta!", "", null);
//        } else {
//            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "", "Nu am gasit utilizator", null);
//        }
//
//    }

    @PostMapping("/auth/forgot-password")
    public ServerResponse forgotPassword(@RequestBody ForgotPasswordModel forgotPasswordModel) {
        if (Utils.isValidEmail(forgotPasswordModel.getEmail())) {
            UserModel user = this.usersRepository.findByEmail(forgotPasswordModel.getEmail());
            if (user != null) {
                String generatedPassword = Utils.getRandomString(6);
                System.out.println(generatedPassword);

                String encodedPassword = this.bCryptPasswordEncoder.encode(generatedPassword);
                user.setPassword(encodedPassword);

                this.usersRepository.save(user);

                return new ServerResponse(HttpStatus.OK.value(), "Parola a fost schimbata cu succes", "", generatedPassword);
            } else {
                return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "", "Nu am gasit utilizator", null);
            }
        } else {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "", "Adresa de email invalida!", null);
        }
    }
}

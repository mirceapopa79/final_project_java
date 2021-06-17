package com.sda.javaremote18.spring_boot.controllers.users;


import com.sda.javaremote18.spring_boot.models.ServerResponse;
import com.sda.javaremote18.spring_boot.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersController(UsersRepository usersRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //    @GetMapping("/users/welcome") // in acest caz dorim sa afisam continutul paginii HTML welcome
//    public String showWelcomePage() {
//        return "welcome"; // welcome reprezinta numele paginii de HTML
//    }

    @GetMapping("/users")
    public ServerResponse findAll(){
        List<UserModel> usersList = this.usersRepository.findAll();
        return new ServerResponse(HttpStatus.OK.value(), "User list", usersList);
    }

    @PutMapping("/users/update")
    public ServerResponse update(@RequestBody UserModel user) {
        Optional<UserModel> checkUser = this.usersRepository.findById(user.getId());

        if (checkUser.isPresent()) {
            UserModel userFromDb = checkUser.get();

            userFromDb.setFirstName(user.getFirstName());
            userFromDb.setLastName(user.getLastName());

            if (!user.getEmail().equals(userFromDb.getEmail())) {
                // Verificam daca in baza de date mai avem un utilizator cu noua adresa de email
                UserModel checkEmail = this.usersRepository.findByEmail(user.getEmail());

                // Asta inseamna ca nu mai exista un cont cu aceasta adresa de email
                if (checkEmail == null) {
                    userFromDb.setEmail(user.getEmail());
                } else {
                    return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "", "Acest email este folosit de catre un alt utilizator", null);
                }
            }

            if(!user.getPassword().equals("")){
                String password = user.getPassword();
                String newPassword = this.bCryptPasswordEncoder.encode(password);
                userFromDb.setPassword(newPassword);
            }

            this.usersRepository.save(userFromDb);

            return new ServerResponse(HttpStatus.OK.value(), "Utilizator actualizat cu succes", "", userFromDb);
        } else {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "", "Nu am gasit utilizator", null);
        }

    }

    @DeleteMapping("/users/delete/{userId}")
    public ServerResponse deleteUser(@PathVariable(name = "userId", required = true) String userId) {
        System.out.println(userId);

        try {
            Integer id = Integer.parseInt(userId);

            Optional<UserModel> checkUser = this.usersRepository.findById(id);

            if (checkUser.isPresent()) {
                this.usersRepository.deleteById(id);

                return new ServerResponse(HttpStatus.OK.value(), "User sters cu succes", "", null);

            } else {
                return new ServerResponse(HttpStatus.OK.value(), "UserID invalid", "", null);
            }

        } catch (Exception e) {
            return new ServerResponse(HttpStatus.OK.value(), "UserID invalid", "", null);
        }

    }

}

package com.bootcamp.avengers.login.business;

import com.bootcamp.avengers.login.models.entity.LoginResponse;
import com.bootcamp.avengers.login.models.entity.ReniecResponse;
import com.bootcamp.avengers.login.models.entity.User;
import com.bootcamp.avengers.login.models.repository.UserRepository;
import com.bootcamp.avengers.login.models.service.IUserService;
import com.bootcamp.avengers.login.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    // @Autowired
    // private IUserService userService;

    //
    //

    public LoginResponse loginUsaurio(String dni) {

        LoginResponse loginResponse = new LoginResponse();
        Boolean bSave = true;

        // Buscarmos en la BD
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/bootcamp/v1/login/user/db/" + dni;
        ResponseEntity<User> userDB = restTemplate.getForEntity(url, User.class);

        if (userDB.getBody().getDni() == null) {
            // Buscamos en RENIEC
            restTemplate = new RestTemplate();
            url = "http://localhost:8080/bootcamp/v1/login/user/reniec/" + dni;
            ResponseEntity<ReniecResponse> userReniec = restTemplate.getForEntity(url, ReniecResponse.class);
            if (userReniec.getBody().getDni() != "" ) {
                // Grabamos BD
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                url = "http://localhost:8080/bootcamp/v1/login/user/save/" + dni;

                User userToDB = new User();
                userToDB.setDni(userReniec.getBody().getDni());
                userToDB.setNames(userReniec.getBody().getNombres() + " ");
                userToDB.setFatherlastname(userReniec.getBody().getApellidoPaterno() + " ");
                userToDB.setMotherlastname(userReniec.getBody().getApellidoMaterno() + " ");
                userToDB.setStatus(Constants.STATUS_ACTVO);

                ReniecResponse reniecResponse = new ReniecResponse();
                reniecResponse.setDni(userReniec.getBody().getDni());
                reniecResponse.setNombres(userReniec.getBody().getNombres());
                reniecResponse.setApellidoMaterno(userReniec.getBody().getApellidoMaterno());
                reniecResponse.setApellidoPaterno(userReniec.getBody().getApellidoPaterno());
                reniecResponse.setCodVerifica(userReniec.getBody().getCodVerifica());

                if (bSave) {
                    HttpEntity<ReniecResponse> requestEntity = new HttpEntity<>(reniecResponse, headers);
                    RestTemplate restTemplatePost = new RestTemplate();
                    ResponseEntity<ReniecResponse> responseEntity =
                            restTemplatePost.postForEntity(url, requestEntity, ReniecResponse.class);
                } else {
                    loginResponse.setDni(userReniec.getBody().getDni());
                    loginResponse.setFatherlastname(userReniec.getBody().getApellidoPaterno());
                    loginResponse.setMotherlastname(userReniec.getBody().getApellidoMaterno());
                    loginResponse.setNames(userReniec.getBody().getNombres() );
                    loginResponse.setStatus(Constants.STATUS_ACTVO);
                }

            }

            if (bSave) {
                // Buscarmos en la BD
                restTemplate = new RestTemplate();
                url = "http://localhost:8080/bootcamp/v1/login/user/db/" + dni;
                userDB = restTemplate.getForEntity(url, User.class);

                loginResponse.setDni(userDB.getBody().getDni());
                loginResponse.setFatherlastname(userDB.getBody().getFatherlastname());
                loginResponse.setMotherlastname(userDB.getBody().getMotherlastname());
                loginResponse.setNames(userDB.getBody().getNames() );
                loginResponse.setStatus(userDB.getBody().getStatus());

            }

        } else {

            // Retornar
            loginResponse.setDni(userDB.getBody().getDni());
            loginResponse.setFatherlastname(userDB.getBody().getFatherlastname());
            loginResponse.setMotherlastname(userDB.getBody().getMotherlastname());
            loginResponse.setNames(userDB.getBody().getNames());
            loginResponse.setStatus(userDB.getBody().getStatus());

        }



        return loginResponse;


    }


}

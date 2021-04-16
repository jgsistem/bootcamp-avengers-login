package com.bootcamp.avengers.login.models.service;

import com.bootcamp.avengers.login.models.entity.ReniecResponse;
import com.bootcamp.avengers.login.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReniecServiceImpl implements IReniecService {

    @Override
    public ReniecResponse searchReniec(String dni) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.RENIEC_URI + dni + Constants.RENIEC_TOKEN;
        ResponseEntity<ReniecResponse> persona = restTemplate.getForEntity(url, ReniecResponse.class);

        ReniecResponse reniecResponse = new ReniecResponse();
        if (persona.getStatusCode() == HttpStatus.OK) {
            reniecResponse.setDni(persona.getBody().getDni());
            reniecResponse.setApellidoMaterno(persona.getBody().getApellidoMaterno());
            reniecResponse.setApellidoPaterno(persona.getBody().getApellidoPaterno());
            reniecResponse.setNombres(persona.getBody().getNombres());
            reniecResponse.setCodVerifica(persona.getBody().getCodVerifica());
        }

        return reniecResponse;
    }

}

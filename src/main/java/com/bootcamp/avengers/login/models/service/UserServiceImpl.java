package com.bootcamp.avengers.login.models.service;

import com.bootcamp.avengers.login.models.entity.ReniecResponse;
import com.bootcamp.avengers.login.models.entity.User;
import com.bootcamp.avengers.login.models.repository.UserRepository;
import com.bootcamp.avengers.login.util.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User searchBD(String dni) {
        User user = userRepository.findByDni(dni);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void save(String dni, ReniecResponse reniecResponse) {
        User user = new User();
        user.setDni(reniecResponse.getDni());
        user.setFatherlastname(reniecResponse.getApellidoPaterno());
        user.setMotherlastname(reniecResponse.getApellidoMaterno());
        user.setNames(reniecResponse.getNombres());
        user.setStatus(Constants.STATUS_ACTVO);

        userRepository.save(user);
    }
}

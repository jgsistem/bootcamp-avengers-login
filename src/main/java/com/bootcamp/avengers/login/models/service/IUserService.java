package com.bootcamp.avengers.login.models.service;

import com.bootcamp.avengers.login.models.entity.ReniecResponse;
import com.bootcamp.avengers.login.models.entity.User;

public interface IUserService {
    public User searchBD(String dni);
    public void save(String dni, ReniecResponse reniecResponse);
}

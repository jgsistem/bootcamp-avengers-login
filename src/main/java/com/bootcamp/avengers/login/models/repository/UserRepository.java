package com.bootcamp.avengers.login.models.repository;

import com.bootcamp.avengers.login.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE LOWER(u.dni) = LOWER(:dni)")
    public User findByDni(@Param("dni") String dni);

}

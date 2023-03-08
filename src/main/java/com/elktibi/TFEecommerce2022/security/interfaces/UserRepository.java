package com.elktibi.TFEecommerce2022.security.interfaces;

import com.elktibi.TFEecommerce2022.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMail(String username);
}

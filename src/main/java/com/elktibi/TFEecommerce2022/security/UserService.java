package com.elktibi.TFEecommerce2022.security;

import com.elktibi.TFEecommerce2022.models.shop.Cart;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.repositories.CartRepository;
import com.elktibi.TFEecommerce2022.security.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String mail) {
        Optional<User> user = userRepository.findByMail(mail);
        if(!user.isPresent()) {
            throw new IllegalStateException("Invalid mail or password");
        }
        return user.get();
    }

    public void saveNewUser(User newUser)  {
        Optional<User> userOptional = userRepository.findByMail(newUser.getMail());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("This mail is already taken");
        }
        newUser.setUserRole(UserRole.ADMIN);
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        Cart newCart = new Cart();
        newCart.setUser(newUser);
        newUser.setCart(newCart);
        userRepository.save(newUser);
    }
}

package be.intecbrussel.bookapi.security;

import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // change to email <- loadUserByUsername(String username)
    // this mapped object will be used to config out SecurityConfig witch is a Configuration
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<AuthUser> authUserRepositoryByEmail = userRepository.findById(email);

        if (authUserRepositoryByEmail.isEmpty()) {
            throw new UsernameNotFoundException(email + " not found");
        }

        AuthUser authUser = authUserRepositoryByEmail.get();

        // create a User from Spring core not our User ! based on the AuthUser
        // we build this user for Spring Security
        // map email , password and isAdmin to userDetails from out authUser
        return User.builder()
                .username(authUser.getEmail())
                .password(authUser.getPassword())
                .roles(authUser.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}
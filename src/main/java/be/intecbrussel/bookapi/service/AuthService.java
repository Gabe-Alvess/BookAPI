package be.intecbrussel.bookapi.service;

import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.model.dto.LoginRequest;
import be.intecbrussel.bookapi.model.dto.LoginResponse;
import be.intecbrussel.bookapi.repository.UserRepository;
import be.intecbrussel.bookapi.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean createUser(String firstName, String lastName, String email, String password) {
        AuthUser user = new AuthUser(email, bCryptPasswordEncoder.encode(password), firstName, lastName);

        boolean exists = userRepository.existsById(email);

        if (!exists) {
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        Optional<AuthUser> optionalUser = userService.findUser(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        AuthUser user = optionalUser.get();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String email = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = "USER";

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                role = "ADMIN";
                break;
            }
        }

        String token = jwtUtil.createToken(user, role);
        LoginResponse loginResponse = new LoginResponse(email, token, role);

        return Optional.of(loginResponse);
    }
}

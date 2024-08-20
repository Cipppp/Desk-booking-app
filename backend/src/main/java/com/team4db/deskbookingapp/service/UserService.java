package com.team4db.deskbookingapp.service;

import com.team4db.deskbookingapp.jwt.payload.request.RegisterRequest;
import com.team4db.deskbookingapp.jwt.payload.response.RegisterResponse;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User findUserByEmail(String username) {
        return userRepository.findUserByEmail(username);
    }

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        User user = new User();
        String regexPattern = "^(.+)@(\\S+)$";
        user.setUserID(UUID.randomUUID().toString());
        if (!Pattern.compile(regexPattern).matcher(registerRequest.getEmail()).matches()) {
            log.error("Incorrect email format.");
            return ResponseEntity.ok("Incorrect email format");
        }
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);

        return ResponseEntity.ok(
                new RegisterResponse(
                        userRepository.findUserByEmail(registerRequest.getEmail()).getUserID(),
                        "The user was created."));
    }

    public User findUserByUserID(String userId) {
        return userRepository.findUserByUserID(userId);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}

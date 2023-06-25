package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.Exception.UserNotFoundException;
import org.binar.kamihikoukiairlines.dto.EditProfileRequest;
import org.binar.kamihikoukiairlines.model.Users;
import org.binar.kamihikoukiairlines.repository.UserRepository;
import org.binar.kamihikoukiairlines.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<Users> getUserById(Long id) {
        log.info("Get Data User By Id Success");
        return userRepository.findById(id);
    }

    public Users updateUser(Long id, EditProfileRequest user) {
        Users user1 = userRepository.findById(id).get();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        log.info("Update Data User Success");
        return userRepository.save(user1);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void changePassword( String email, String newPassword) throws UserNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User With The Email Not Found"));
        if (user == null) {
            throw new UserNotFoundException("User With The Email Not Found");
        }
        user.setPassword(encryptPassword(newPassword));
        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

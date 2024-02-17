package com.ruzzyfer.twitterlike.service;

import com.ruzzyfer.twitterlike.config.JwtService;
import com.ruzzyfer.twitterlike.dto.LoginDto;
import com.ruzzyfer.twitterlike.dto.LoginResponse;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.dto.UserSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.util.EmailService;
import com.ruzzyfer.twitterlike.util.PasswordGenerator;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;



    public UserService(UserMapper userMapper, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;

    }


    public UserDto save(UserSaveRequestDto dto) throws MessagingException {

        User user = userMapper.toEntityFromSaveRequestDto(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user = userRepository.save(user);

        UserDto getDto = userMapper.toDto(user);

        emailService.sendEmail(
                dto.getEmail(),
                "Welcome to twitterLike!",
                "Twitter'a hos geldin " + dto.getUsername());

        return getDto;

    }

    public LoginResponse login( LoginDto dto) {

        LoginResponse loginBackDto = new LoginResponse();

        User gecici = userRepository.findByUsername(dto.getUsername()).orElseThrow();

        if (passwordEncoder.matches(dto.getPassword(),gecici.getPassword())) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));


            loginBackDto.setId(gecici.getId());
            loginBackDto.setRole(gecici.getRole());
            loginBackDto.setToken(jwtService.generateToken(gecici));

            return loginBackDto;
        }
        throw  new RuntimeException("kontrol et");
    }

    public List<UserDto> listAllKullanici() {

        return userMapper.toDtoListFromEntity(userRepository.findAll());

    }


    public Boolean passwordResetEmail(String email) throws MessagingException {

        User user = userRepository.findByEmail(email);

        String baseUrl = "http://localhost:8080/user/resetpassword";

        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        // Benzersiz bir token oluştur
        String resetToken = UUID.randomUUID().toString();

        // Token'i kullanıcının hesabına kaydet
        user.setResetToken(resetToken);
        userRepository.save(user);

        // E-posta gönderme işlemi
        String resetLink = baseUrl + "?resetToken=" + resetToken;
        String subject = "Password Reset Link";
        String text = "Hi " + user.getUsername() + ",\n\n" +
                "The link to reset your password:\n" +
                resetLink;

        emailService.sendEmail(user.getEmail(), subject, text);

        return true;
    }


    public Boolean passwordReset(String resetToken) throws MessagingException {
        User user = userRepository.findByResetToken(resetToken);

        if (user == null) {
            throw new RuntimeException("Invalid token: " + resetToken);
        }

        String newPassword = PasswordGenerator.generatePassword();

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);

        String subject = "Your new Password! ";
        String text = "Hi " + user.getUsername() + ",\n\n" +
                "Your new password: " + newPassword;

        emailService.sendEmail(user.getEmail(), subject, text);


        return true;
    }



}

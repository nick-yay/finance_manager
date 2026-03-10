package fincance_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fincance_manager.dto.RegisterDTO;
import fincance_manager.model.User;
import fincance_manager.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterDTO data) {
        //Verificar se o email já existe
        if (userRepository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        //Criptografando a senha com BCrypt antes de salvar
        String encodedPassword = passwordEncoder.encode(data.password());

        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}

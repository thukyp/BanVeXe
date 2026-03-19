package com.example.banvexe.services;

import com.example.banvexe.models.entities.User;
import com.example.banvexe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;



@Service
public class UserService {


    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Tạo người dùng mới
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void processOAuthPostLogin(String email, String name) {
        User existUser = userRepository.findByEmail(email).orElse(null);

        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullName(name);
            // Sử dụng email làm username nếu hệ thống yêu cầu username
            newUser.setUsername(email); 
            // Đặt password mặc định/ngẫu nhiên vì trường này không được null
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            newUser.setRole(User.Role.CUSTOMER);
            
            userRepository.save(newUser);
        } else {
            // Cập nhật lại họ tên nếu có thay đổi từ phía Google
            existUser.setFullName(name);
            userRepository.save(existUser);
        }
    }
}
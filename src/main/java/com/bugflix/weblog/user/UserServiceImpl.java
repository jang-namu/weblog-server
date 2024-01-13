package com.bugflix.weblog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }
}

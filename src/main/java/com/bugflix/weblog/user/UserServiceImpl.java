package com.bugflix.weblog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private static UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }
    public String findNicknameByPostId(Long postId){
        User user = userRepository.findByPosts_PostId(postId);
        return user.getNickname();
    }
    public User findUserByPostId(Long postId){
        return userRepository.findByPosts_PostId(postId);
    }
}

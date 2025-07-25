package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstant;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repository.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        // UserID have to generate
        String userId = UUID.randomUUID().toString();

        user.setUserId(userId);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
//        set the user roll
        user.setRoleList(List.of(AppConstant.ROLE_USER));

        //password incoding
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2 =  userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not Found !") );

    //    Update the User
       user2.setName(user.getName());
       user2.setEmail(user.getEmail());
       user2.setPassword(user.getPassword());
       user2.setAbout(user.getAbout());
       user2.setPhoneNumber(user.getPhoneNumber());
       user2.setProfilePic(user.getProfilePic());
       user2.setEnabled(user.isEnabled());
       user2.setEmailVerified(user.isEmailVerified());
       user2.setPhoneVerified(user.isPhoneVerified());
       user2.setProvider(user.getProvider());
       user2.setProviderUserId(user.getProviderUserId());

    //    Save the user in database
        User save = userRepo.save(user2);
        return Optional.of(save);
    }

    @Override
    public void deleteUser(String id) {
        //fatch the user
       User user2 =  userRepo.findById(id)
       .orElseThrow(() -> new ResourceNotFoundException("User not Found !") );
       userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String id) {
        User user2 =  userRepo.findById(id).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByUserName(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false; 
    }

    @Override
    public List<User> getAllUsers() {
      return  userRepo.findAll();
    }
    



}

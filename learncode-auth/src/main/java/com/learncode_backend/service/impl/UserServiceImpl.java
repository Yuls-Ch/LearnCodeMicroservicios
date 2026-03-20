package com.learncode_backend.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.learncode_backend.model.User;
import com.learncode_backend.repository.UserRepository;
import com.learncode_backend.service.UserService;

@Service
public class UserServiceImpl  extends ICRUDImpl<User, UUID> implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
   
	@Override
	public JpaRepository<User, UUID> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
    @Override
    public User getOrCreateUser(Jwt jwt) {

        String email = jwt.getClaimAsString("email");
        String name  = jwt.getClaimAsString("name");
        String googleSub = jwt.getSubject();
        String pictureUrl = jwt.getClaimAsString("picture");

        return repository.findByEmail(email)
    		.map(user -> {
    		    user.setFullName(name);
    		    if (pictureUrl != null && !pictureUrl.isEmpty()) {
    		        user.setPhoto(pictureUrl);
    		    } else if (user.getPhoto() == null || user.getPhoto().isEmpty()) {

    		        user.setPhoto("https://ui-avatars.com/api/?name=" + user.getFullName().replace(" ", "+"));
    		    }
    		    return repository.save(user);
    		})
            .orElseGet(() -> {
                User user = new User();
                user.setId(UUID.randomUUID());
                user.setEmail(email);
                user.setFullName(name);
                user.setGoogleSub(googleSub);
                if (pictureUrl != null && !pictureUrl.isEmpty()) {
                    user.setPhoto(pictureUrl);
                } else {
                    user.setPhoto("https://ui-avatars.com/api/?name=" + name.replace(" ", "+"));
                }
                user.setRole("USER");
                user.setStatus("ACTIVE");
                user.setCreatedAt(LocalDateTime.now());
                return repository.save(user);
            });
    }

}

package com.example.AirbnbBackEnd.service;

import com.example.AirbnbBackEnd.config.SecurityUtils;
import com.example.AirbnbBackEnd.dto.ReadUserDTO;
import com.example.AirbnbBackEnd.entity.User;
import com.example.AirbnbBackEnd.mapper.UserMapper;
import com.example.AirbnbBackEnd.repository.UserDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static  final String UPDATED_AT_KEY = "updated_at";
    private final UserDao userDao;
    private final UserMapper userMapper;

    public UserService(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public ReadUserDTO getAuthenticatedUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = SecurityUtils.mapOauth2AttributesToUser(principal.getAttributes());
        return getByEmail(user.getEmail()).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Optional<ReadUserDTO> getByEmail(String email) {
        Optional<User> oneByEmail = userDao.findByEmail(email);
        return oneByEmail.map(userMapper::readUserDTOUser);
    }

    public void syncWithIdp(OAuth2User oAuth2User, boolean forceResync) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = SecurityUtils.mapOauth2AttributesToUser(attributes);
        Optional<User> existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (attributes.get(UPDATED_AT_KEY) != null) {
                Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if (attributes.get(UPDATED_AT_KEY) instanceof Instant instant) {
                    idpModifiedDate = instant;
                } else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get(UPDATED_AT_KEY));
                }
                if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                    updateUser(user);
                }
            }
        } else {
            userDao.saveAndFlush(user);
        }
    }

    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userDao.findByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setAuthorities(user.getAuthorities());
            userToUpdate.setImageUrl(user.getImageUrl());
            userDao.saveAndFlush(userToUpdate);
        }
    }

    public Optional<ReadUserDTO> getByPublicId(UUID publicId) {
        Optional<User> oneByPublicId = userDao.findOneByPublicId(publicId);
        return oneByPublicId.map(userMapper::readUserDTOUser);
    }


}

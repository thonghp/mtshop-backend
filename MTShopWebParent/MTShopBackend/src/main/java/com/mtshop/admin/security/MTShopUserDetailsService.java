package com.mtshop.admin.security;

import com.mtshop.admin.dao.UserRepository;
import com.mtshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MTShopUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        if (user != null) {
            return new MTShopUserDetails(user);
        }

        throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}

package com.example.ics.Security;

import com.example.ics.Repository.UserRepository;
import com.example.ics.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"+email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole())));
    }
}

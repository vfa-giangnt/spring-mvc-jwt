package gau.nau.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gau.nau.spring.dao.UserDAO;


@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserDAO userDAO;
    
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        
        gau.nau.spring.entities.User user = userDAO.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, user.getAuthorities());
    }
}

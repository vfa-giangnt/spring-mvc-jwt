package gau.nau.springbootjwt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import gau.nau.springbootjwt.entities.User;
import gau.nau.springbootjwt.service.JwtService;
import gau.nau.springbootjwt.service.UserService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    
    private final static String TOKEN_HEADER = "authorization";
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(TOKEN_HEADER);
        
        
        if (jwtService.validateTokenLogin(authToken)) {
            String username = jwtService.getUsernameFromToken(authToken);
            
            User user = userService.loadUserByUsername(username);
            if (user != null) {
                
                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
                
                UserDetails userDetail = new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, user.getAuthorities());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            }
        }
        
        chain.doFilter(request, response);
    }
}

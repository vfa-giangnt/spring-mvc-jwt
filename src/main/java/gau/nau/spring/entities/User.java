package gau.nau.spring.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", catalog = "spring_security", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Set<UserRoles> userRoles = new HashSet<UserRoles>(0);
    
    public User() {
    }
    
    public User(String username, String password, Boolean enabled, Set<UserRoles> userRoles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRoles = userRoles;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }
    
    @Column(name = "username", unique = true, length = 45)
    public String getUsername() {
        return this.username;
    }
    
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    
    @Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
    public Boolean getEnabled() {
        return this.enabled;
    }
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
    public Set<UserRoles> getUserRoles() {
        return this.userRoles;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
    
    @Transient
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (UserRoles userRoles : this.userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRoles.getRole().getName()));
        }
        return authorities;
    }
}
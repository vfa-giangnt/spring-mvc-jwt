package gau.nau.spring.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "role", catalog = "spring_security")
public class Role implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Set<UserRoles> usersRoles = new HashSet<UserRoles>(0);
    
    public Role() {
    }
    
    public Role(String name, Set<UserRoles> usersRoles) {
        this.name = name;
        this.usersRoles = usersRoles;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "name", length = 45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    public Set<UserRoles> getUsersRoles() {
        return this.usersRoles;
    }
    
    public void setUsersRoles(Set<UserRoles> usersRoles) {
        this.usersRoles = usersRoles;
    }
}

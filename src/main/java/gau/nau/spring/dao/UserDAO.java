package gau.nau.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import gau.nau.spring.entities.User;

@Repository(value = "userDAO")
@Transactional(rollbackFor = Exception.class)
public class UserDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public User loadUserByUsername(final String username) {
        List<User> users = new ArrayList<User>();
        Session session = this.sessionFactory.getCurrentSession();
        users = session.createQuery("FROM User where username=?0", User.class).setParameter(0, username).list();
        
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
        
    }
}

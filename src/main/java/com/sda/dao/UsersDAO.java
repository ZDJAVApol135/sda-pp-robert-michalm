package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAO {

    public void create(User user) {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

    public boolean delete(String username) {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, username);
        boolean exists = user != null;
        if (exists) {
            session.remove(user);
        }
        transaction.commit();
        session.close();
        return user != null;
    }

    public List<User> findAll() {
        try (Session session = HibernateUtils.openSession()) {
            return session.createQuery("SELECT u FROM User u", User.class).list();
        }
    }

    public User findByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            return session.get(User.class, username);
        }
    }

    public User update(User user) {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
        User updatedUser = session.merge(user);
        transaction.commit();
        session.close();

        return updatedUser;
    }

    public boolean exists(String username) {
        try (Session session = HibernateUtils.openSession()) {

            String query = "SELECT count(u) FROM User u WHERE u.username = :id";
            Long usersCount = session.createQuery(query, Long.class)
                    .setParameter("id", username)
                    .uniqueResult();

            return usersCount > 0;
        }
    }
}

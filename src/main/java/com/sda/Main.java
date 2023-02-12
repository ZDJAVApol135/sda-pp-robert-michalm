package com.sda;

import com.sda.db.HibernateUtils;
import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {

        Session session = HibernateUtils.openSession();
        session.close();
    }

}

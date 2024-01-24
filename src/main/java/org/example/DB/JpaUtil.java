package org.example.DB;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Properties;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory;
    private static EntityManager em;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            Properties props = new Properties();
            props.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            props.put("hibernate.hbm2ddl.auto", "create");
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        if (em == null) {
            em = getEntityManagerFactory().createEntityManager();
        }
        return em;
    }
}

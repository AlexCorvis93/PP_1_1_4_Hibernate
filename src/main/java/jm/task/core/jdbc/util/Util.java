package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateError;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.Property;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/db_task1";
    private static String USER = "admin";
    private static String PSWD = "kata123";

    private static SessionFactory sessionFactory;


    public static Connection getConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PSWD);
            connection.setAutoCommit(false);
        }catch (SQLException e) {
            System.out.println("Connection error");
        }
        return connection;
    }

    public static SessionFactory HibernateGetConnection() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL+"?useSSL=false");
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PSWD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateError e) {
                System.out.println("Error Hibernate connection");

            }
        }
        return sessionFactory;
    }
    public static void FactoryClose() {
        sessionFactory.close();
    }

}

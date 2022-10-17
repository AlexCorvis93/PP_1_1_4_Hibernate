package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private Statement statement;
    private Connection connection;
    public UserDaoJDBCImpl() {}

    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createUsersTable() {
        String create = """
                CREATE TABLE IF NOT EXISTS  Users (
                    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255),
                    age TINYINT);""";
        try (Connection connection = getConnection()){
            statement = connection.createStatement();
            statement.execute(create);
            connection.commit();
            System.out.println("таблица 'Users' создана");
        } catch (SQLException e) {
            rollback();
        }

    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS Users;";
        try (Connection connection = getConnection()){
            statement = connection.createStatement();
            statement.execute(drop);
            connection.commit();
            System.out.println("таблица 'Users' удалена");
        } catch (SQLException e) {
            rollback();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?);";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(saveUser);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println("User с именем –" + name + "  добавлен в базу данных");
        } catch (SQLException e) {
            rollback();
        }

    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM Users WHERE id=" + id + ";";
        try(Connection connection = getConnection()){
            statement = connection.createStatement();
            statement.execute(removeUser);
            connection.commit();
            System.out.println("User с id –" + id + "успешно удален");
        } catch (SQLException e) {
            rollback();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getUsers = "SELECT * FROM Users;";
        try (Connection connection = getConnection()){
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getUsers);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            rollback();
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM Users;";
        try (Connection connection = getConnection()){
            statement = connection.createStatement();
            statement.execute(cleanTable);
            connection.commit();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            rollback();

        }
    }
}

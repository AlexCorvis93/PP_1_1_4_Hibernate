package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String create = """
                CREATE TABLE IF NOT EXISTS  Users (
                    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255),
                    age TINYINT);""";
        try (Statement statement = Util.getConnection().createStatement()){
           statement.execute(create);
            System.out.println("таблица 'Users' создана");
        } catch (SQLException e) {
            System.err.println("create table error");;
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS Users;";
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute(drop);
            System.out.println("таблица 'Users' удалена");
        } catch (SQLException e) {
            System.err.println("drop table error");;
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?);";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(saveUser)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем –" + name + "  добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println(" save user error");;
        }

    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM Users WHERE id=" + id + ";";
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute(removeUser);
            System.out.println("User с id –" + id + "успешно удален");
        } catch (SQLException e) {
            System.err.println(" delete user error");;
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getUsers = "SELECT * FROM Users;";
        try (Statement statement = Util.getConnection().createStatement()){
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
            System.err.println("accept data error");;
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM Users;";
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute(cleanTable);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.err.println("clean table error");;
        }

    }
}

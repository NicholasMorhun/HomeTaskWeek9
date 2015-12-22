package com.geekhub.hw8.dao;

import com.geekhub.hw8.beans.CloudPocketUser;
import com.geekhub.hw8.utils.Utils;

import java.sql.*;

import static com.geekhub.hw8.dao.DBInfo.*;

/**
 * Class for working with
 */
public class UserDao {
    private Connection connection = null;

    private static final int USER_ID = 1;
    private static final int USER_LOGIN = 2;
    private static final int USER_PASSWORD_MD5 = 3;

    private static String GET_USER_BY_ID_PREPARED_SQL = "SELECT * FROM `Users` WHERE `id` = ?";
    private static String GET_USER_BY_LOGIN_PREPARED_SQL = "SELECT * FROM `Users` WHERE `login` = ?";
    private static String ADD_USER_PREPARED_SQL = "INSERT INTO `Users` VALUES (null, ?, ?)";

    private PreparedStatement getUserByIdPreparedStatement;
    private PreparedStatement getUserByLoginPreparedStatement;
    private PreparedStatement addUserPreparedStatement;

    private static final UserDao instance = new UserDao();

    private UserDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_DRIVER + "://" + DB_HOST  + "/" + DB_NAME, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not connect to database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not found database driver");
        }

        try {
            getUserByIdPreparedStatement = connection.prepareStatement(GET_USER_BY_ID_PREPARED_SQL);
            getUserByLoginPreparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_PREPARED_SQL);
            addUserPreparedStatement = connection.prepareStatement(ADD_USER_PREPARED_SQL, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDao getInstance() {
        return instance;
    }

    /**
     * Adds user into database
     * @param login user login
     * @param password user password
     * @return true if user was successfully added
     */
    public boolean addUser(String login, String password) {
        try {
            addUserPreparedStatement.setString(1, login);
            addUserPreparedStatement.setString(2, Utils.getMd5Hash(password));
            return addUserPreparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Finds user in database by login
     * @param login user login name
     * @return user-object if user with specified login exist and {@code null} otherwise
     */
    public CloudPocketUser getUserByLogin(String login) {
        CloudPocketUser user = null;
        try {
            getUserByLoginPreparedStatement.setString(1, login);
            ResultSet result = getUserByLoginPreparedStatement.executeQuery();
            if (result.next()) {
                user = new CloudPocketUser(result.getInt(USER_ID), result.getString(USER_LOGIN), result.getString(USER_PASSWORD_MD5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Finds user in database by user id
     * @param id user id
     * @return user-object if user with specified name exist and {@code null} otherwise
     */
    public CloudPocketUser getUserById(int id) {
        CloudPocketUser user = null;
        try {
            getUserByIdPreparedStatement.setInt(1, id);
            ResultSet result = getUserByIdPreparedStatement.executeQuery();
            if (result.next()) {
                user = new CloudPocketUser(id, result.getString(USER_LOGIN), result.getString(USER_PASSWORD_MD5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}

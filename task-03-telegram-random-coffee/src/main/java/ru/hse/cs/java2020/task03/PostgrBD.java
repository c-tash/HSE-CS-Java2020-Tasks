package ru.hse.cs.java2020.task03;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PostgrBD {

    private static Connection connection;
    private static Statement statement;

    public PostgrBD() {
        SetConnection();
    }

    public void SetConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JavaDB", "postgres", "1001");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<BDInfo> getData(String chatId) {

        String token = "";
        String orgid = "";
        String username = "";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \"token\", \"orgid\", \"username\" FROM public.\"BotTable\" WHERE \"chatid\" = '" + chatId + "'");

            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            while (resultSet.next()) {
                token = resultSet.getString("token");
                orgid = resultSet.getString("orgid");
                username = resultSet.getString("username");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.of(new BDInfo(token, orgid, username));
    }

    public void insertData(String chat_id, BDInfo userInfo) throws SQLException {
        statement = connection.createStatement();
        String req = "INSERT INTO public.\"BotTable\"(username, chatid, orgid, token) VALUES (" + "'" + userInfo.Username() +"'" + ", '" + chat_id+ "'," + "'" + userInfo.Org() + "'" + "," + "'" + userInfo.Token() + "')";
        statement.executeUpdate(req);
    }
}

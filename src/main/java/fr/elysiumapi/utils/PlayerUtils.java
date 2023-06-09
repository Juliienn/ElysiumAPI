package fr.elysiumapi.utils;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumapi.database.sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class PlayerUtils {

    public static HashMap<UUID, ElysiumPlayer> elysiumPlayer;

    public static UUID uuidFromName(String playerName){
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM players WHERE pseudo = ?")){
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String uuid = resultSet.getString("uuid");
                return UUID.fromString(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String nameFromUUID(UUID uuid){
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT pseudo FROM players WHERE uuid = ?")){
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("pseudo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
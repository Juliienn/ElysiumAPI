package fr.elysiumapi.sanctions;

import fr.elysiumapi.database.sql.DatabaseManager;
import fr.elysiumapi.sanctions.infos.MuteInfo;
import fr.elysiumapi.utils.SanctionsUtils;

import java.sql.*;
import java.util.UUID;

public class MuteManager {

    public static void mute(UUID uuid, String reason, String bannerName, Timestamp expireDate) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO muted (uuid, reason, bannername, expiredate, effectdate) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setTimestamp(4, expireDate);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unmute(UUID uuid) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM muted WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMutedList() {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, reason, bannername, effectdate, expiredate FROM muted");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String reason = resultSet.getString("reason");
                String bannerName = resultSet.getString("bannername");
                Timestamp effectDate = resultSet.getTimestamp("effectdate");
                Timestamp expireDate = resultSet.getTimestamp("expiredate");
                SanctionsUtils.getMutedList().put(uuid, new MuteInfo(uuid, reason, bannerName, effectDate, expireDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package model.files;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialBlob;
import model.Interface;

/**
 *
 * @author Kamil
 */
public class FilesInterface extends Interface {

    public ArrayList<File> getFiles(int userID) {
        ArrayList<File> files = new ArrayList<>();
        String query = "SELECT id_file, file_data, file_name FROM files WHERE id_user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);
            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    int idFile = results.getInt("id_file");
                    Blob blobData = results.getBlob("file_data");
                    String fileName = results.getString("file_name");
                    byte[] blobDataBytes = blobData.getBytes(1, (int) blobData.length());
                    File file = new File(idFile, fileName, blobDataBytes);
                    files.add(file);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }

    public void saveFile(int userID, String fileName, byte[] fileData) {
        try {
            Blob fileBlob = new SerialBlob(fileData);
            String update = "INSERT INTO files (id_file, id_user, file_name, file_data) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setInt(1, getLastFileID() + 1);
                statement.setInt(2, userID);
                statement.setString(3, fileName);
                statement.setBlob(4, fileBlob);

                statement.executeUpdate();
                System.out.println("Plik został pomyślnie dodany do bazy danych.");
            } catch (SQLException e) {
                System.err.println("Błąd podczas dodawania pliku do bazy danych: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(int fileID) {
        String deleteQuery = "DELETE FROM files WHERE id_file = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, fileID);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Plik został pomyślnie usunięty z bazy danych.");
            } else {
                System.out.println("Nie znaleziono pliku o podanym identyfikatorze.");
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas usuwania pliku z bazy danych: " + e.getMessage());
        }
    }

    public int getLastFileID() {
        try {
            String query = "SELECT MAX(id_file) AS max FROM files";
            ResultSet results = connection.createStatement().executeQuery(query);

            if (results.next()) {
                return results.getInt("max");
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}

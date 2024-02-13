package model.files;

/**
 *
 * @author Kamil
 */
public class File {

    private int id;
    private String fileName;
    private byte[] data;

    public File(int idR, String fileNameR, byte[] dataR) {
        id = idR;
        fileName = fileNameR;
        data = dataR;
    }

    public int getID() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }
}

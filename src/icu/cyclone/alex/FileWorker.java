package icu.cyclone.alex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileWorker {
    public String fileName;
    protected long size;
    private boolean isByteFile = true;
    private File file;

    public FileWorker(String fileName) {
        super();
        this.fileName = fileName;
        this.size = getFileSize(fileName);
    }

    private final long getFileSize(String fileName) {
        this.file = new File(fileName);
        return file.length();
    }

    public void setByteFile(boolean isByteFile) {
        this.isByteFile = isByteFile;
    }

    public byte[] getByteFromFile() {
        if (!isByteFile) {
            throw new IllegalArgumentException("The symbolyc file");
        }
        byte[] byteArray = new byte[(int) this.file.length()];
        try (FileInputStream fis = new FileInputStream(this.file)) {
            fis.read(byteArray);
        } catch (IOException e) {
            System.out.println(e);
        }
        return byteArray;
    }

    @Override
    public String toString() {
        return "FileWorker{" +
                "fileName='" + fileName + '\'' +
                ", size=" + size +
                ", isByteFile=" + isByteFile +
                '}';
    }
}

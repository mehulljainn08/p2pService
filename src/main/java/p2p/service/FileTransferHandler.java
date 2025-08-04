package main.java.p2p.service;

import java.io.*;
import java.net.Socket;

public class FileTransferHandler implements Runnable {

    private final Socket socket;
    private final String filePath;

    public FileTransferHandler(String filePath, Socket socket) {
        this.socket = socket;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket;
             InputStream fis = new FileInputStream(this.filePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = socket.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(os)) {

            File file = new File(filePath);
            String fileName = file.getName();
            long fileSize = file.length();

            String header = "filename:" + fileName + "\n" + "filesize:" + fileSize + "\n\n";
            os.write(header.getBytes("UTF-8"));
            os.flush();

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();

            System.out.println("File transfer completed for: " + fileName);

        } catch (IOException e) {
            System.err.println("Error during file transfer: " + e.getMessage());
        }
    }
}
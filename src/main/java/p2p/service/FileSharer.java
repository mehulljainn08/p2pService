package main.java.p2p.service;

import java.io.File;
import java.util.HashMap;

import main.java.p2p.utils.UploadUtils;

public class FileSharer {
    
    private HashMap<Integer,String> availableFiles;

    public FileSharer() {
        availableFiles = new HashMap<>();
    }

    public int offerFile(String filePath){

        while(true){
            int port=UploadUtils.getPort();
            if(!availableFiles.containsKey(port)){
                availableFiles.put(port,filePath);
                return port;
            }
        }
    }

    public void startFileServer(int port){
        String filePath = availableFiles.get(port);
        if(filePath != null) {
            try(ServerSocket serverSocket= new ServerSocket(port)){
                System.out.println("Serving File: "+ new File(filePath).getName()+ " on port: " + port);
                Socket clientSocket=serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                Thread thread=new Thread(new FileTransferHandler(filePath,clientSocket));
                thread.start();
            } catch (IOException e) {
                System.err.println("Error starting file server on port " + port + ": " + e.getMessage());
                return;
            }
        } else{
            System.err.println("No file found for port: " + port);
        }

    }

}

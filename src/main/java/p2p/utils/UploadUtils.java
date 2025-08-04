package main.java.p2p.utils;
import java.util.random.*;
public class UploadUtils {
    

    
    public static int getPort(){
        int DYNAMIC_START_PORT= 49152;
        int DYNAMIC_END_PORT = 65535;
        Random random=new Random();
        int port = random.nextInt(DYNAMIC_END_PORT - DYNAMIC_START_PORT + 1) + DYNAMIC_START_PORT;
        return port;
    }
}

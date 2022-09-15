import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) {

        Proceso1 hilo1 = new Proceso1();
        hilo1.start();
    }
}

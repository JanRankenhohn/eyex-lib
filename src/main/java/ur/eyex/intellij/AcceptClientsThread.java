package ur.eyex.intellij;

import java.io.IOException;

class AcceptClientsThread extends Thread {

    public void run() {
        java.net.ServerSocket serverSocket = null;
        try {
            serverSocket = new java.net.ServerSocket(InternalConstants.ListeningPort);
            while(true){
                java.net.Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

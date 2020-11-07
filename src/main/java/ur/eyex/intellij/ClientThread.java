package ur.eyex.intellij;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientThread extends Thread {

    private Socket socket;
    private boolean blockGazePointInput = false;
    private boolean blockFixationInput = false;

    /**
     * Receives Client Data (Gaze Points / Fixations)
     * Sends data to Gaze Data Handler with data type
     * @param socket
     */
    public ClientThread(Socket socket){
        this.socket = socket;
    }

    public void run(){
        System.out.println("MyThread running");

        while(true){
            try {
                String input = readInput(socket);
                System.out.println(input);

                JSONObject json = serializeJson(input);
                String type = json.getString("Type");

                if(type.equals("GAZEPOINTS")){
                    if(!blockGazePointInput){
                        blockGazePointInput = true;
                        GazeDataInputHandler.handleGazePointInput(json);
                        blockGazePointInput = false;
                    }
                }
                if(type.equals("FIXATIONS") || type.equals("SACCADES")){
                    if(!blockFixationInput){
                        blockFixationInput = true;
                        GazeDataInputHandler.handleFixationInput(json);
                        blockFixationInput = false;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method to read client input
     * @param socket
     * @return
     * @throws IOException
     */
    private String readInput(java.net.Socket socket) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        char[] buffer = new char[700];
        int charCount = bufferedReader.read(buffer, 0, 700);
        String input = new String(buffer, 0, charCount);
        return input;
    }

    /**
     * Helper method to serialize json data
     * @param text
     * @return
     */
    private JSONObject serializeJson(String text){
        try {
            JSONObject jsonObject = new JSONObject(text);
            return jsonObject;
        }catch (JSONException err){
            return null;
        }
    }
}
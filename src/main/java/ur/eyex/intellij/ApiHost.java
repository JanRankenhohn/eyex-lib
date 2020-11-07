package ur.eyex.intellij;

import java.awt.*;

import com.intellij.openapi.project.Project;
import ur.eyex.intellij.Constants.Apis;

public class ApiHost {

    private Apis api;
    private GazePainterFrame gazePainterFrame;

    public ApiHost(Apis api){
        this.api = api;

        // Start listening for Data Input
        AcceptClientsThread acceptClientsThread = new AcceptClientsThread();
        acceptClientsThread.start();

        loadApi();
    }

    public void startSession(Project p, Constants.GazeDataTypes dataType){
        Session.start(p, dataType);
    }

    public void stopSession(){
        Session.stop();
    }

    /**
     * Sends Request to load the API
     */
    public void loadApi(){
        // Execute HTTP GET request to Load Api
        String targetUrl = "http://" + InternalConstants.ServerIPAdress + ":" + InternalConstants.ServerPort + "/" + InternalConstants.ServerLoadApiPath;
        targetUrl = targetUrl + "?apiname=" + this.api.name();
        Utils.executeHttpRequest(targetUrl, "", "GET");
    }

    /**
     * Sends request to unload the API
     */
    public void unloadApi(){
        // Execute HTTP GET request to Unload Api
        String targetUrl = "http://" + InternalConstants.ServerIPAdress + ":" + InternalConstants.ServerPort + "/" + InternalConstants.ServerUnloadApiPath;
        Utils.executeHttpRequest(targetUrl, "", "GET");
    }

    /**
     * Starts Gaze Point Painting on screen
     */
    public void startGazePainter (){
        EventQueue.invokeLater(() -> {

            if(Session.sessionStarted){
                gazePainterFrame = new GazePainterFrame(Session.dataType);
                gazePainterFrame.setVisible(true);
            }
        });
    }

    public void stopGazePainter (){
        gazePainterFrame.dispose();
    }

}
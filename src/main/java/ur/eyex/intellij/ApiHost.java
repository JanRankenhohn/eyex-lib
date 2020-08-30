package ur.eyex.intellij;

import java.awt.*;

import com.intellij.openapi.project.Project;
import ur.eyex.intellij.Constants.Apis;

public class ApiHost {

    private Apis api;

    private GazePainterFrame gazePainterFrame;

    public ApiHost(Apis api){
        this.api = api;

        // Execute HTTP GET request to Load Api
        String targetUrl = "http://" + InternalConstants.ServerIPAdress + ":" + InternalConstants.ServerPort + "/" + InternalConstants.ServerLoadApiPath;
        targetUrl = targetUrl + "?apiname=" + this.api.name();
        Utils.executeHttpRequest(targetUrl, "", "GET");

        // Start listening for Data Input
        AcceptClientsThread acceptClientsThread = new AcceptClientsThread();
        acceptClientsThread.start();
    }

    public void startSession(Project p, Constants.GazeDataTypes dataType){
        Session.start(p, dataType);
    }

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
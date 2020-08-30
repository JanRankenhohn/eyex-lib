package ur.eyex.intellij;

import com.intellij.openapi.project.Project;

import java.awt.*;
import java.util.HashMap;

class Session {
    public static boolean sessionStarted;
    public static Project project;
    public static Constants.GazeDataTypes dataType;
    public static HashMap<String, Component> componentList;
    public static Component editorComponent;
    public static int fixationCount = 0;

    public static void start(Project p, Constants.GazeDataTypes dt){
        project = p;
        dataType = dt;
        componentList = UIElementHandler.getUIComponents();
        editorComponent = UIElementHandler.getFileEditorComponent(componentList);
        for(CodeElement codeElement : GazeDataHandler.codeElements){
            codeElement.updateScreenCoordinates();
        }
        subscribe(dataType);
        sessionStarted = true;
    }

    public static void stop(){
        // TODO: unsubscribe GET Request
        GazeDataHandler.codeElements.clear();
        sessionStarted = false;
        fixationCount = 0;
    }

    private static void subscribe(Constants.GazeDataTypes dataType){
        String targetUrl = "http://" + InternalConstants.ServerIPAdress + ":" + InternalConstants.ServerPort + "/" + InternalConstants.ServerSubscribePath;
        targetUrl = targetUrl + "?ip=" + InternalConstants.ClientIPAdress + "&port=" + InternalConstants.ListeningPort + "&datatype=" + dataType;
        Utils.executeHttpRequest(targetUrl, "", "GET");
    }
}

package ur.eyex.intellij;

import com.intellij.openapi.project.Project;
import com.jetbrains.rd.util.reactive.KeyValuePair;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Session {
    static boolean sessionStarted;
    static Project project;
    static Constants.GazeDataTypes dataType;
    static HashMap<String, Component> componentList;
    static Component editorComponent;
    static int fixationCount = 0;

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

    public int getFixationCount(){
        return fixationCount;
    }

    private static void subscribe(Constants.GazeDataTypes dataType){
        String targetUrl = "http://" + InternalConstants.ServerIPAdress + ":" + InternalConstants.ServerPort + "/" + InternalConstants.ServerSubscribePath;
        targetUrl = targetUrl + "?ip=" + InternalConstants.ClientIPAdress + "&port=" + InternalConstants.ListeningPort + "&datatype=" + dataType;
        Utils.executeHttpRequest(targetUrl, "", "GET");
    }
}

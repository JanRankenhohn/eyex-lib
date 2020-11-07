package ur.eyex.intellij;

import com.jetbrains.rd.util.reactive.KeyValuePair;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.tools.javac.jvm.Code;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class GazeDataInputHandler{

    private static int previousFixationForCode = 0;
    private static int previousFixationForUI = 0;
    private static CodeElement previousCodeElement;
    private static Component previousUIComponent;

    public static void handleGazePointInput(JSONObject json) {
        try {
            GazeData.Y_Left = json.getDouble("Y_Left");
            GazeData.X_Left = json.getDouble("X_Left");
            GazeData.Y_Right = json.getDouble("Y_Right");
            GazeData.X_Right = json.getDouble("X_Right");
            GazeData.X_Median = json.getDouble("X_Median");
            GazeData.Y_Median = json.getDouble("Y_Median");
            GazeData.GazePointValidity_Left = json.getBoolean("GazePointValidity_Left");
            GazeData.GazePointValidity_Right = json.getBoolean("GazePointValidity_Right");
            GazeData.PupilDiameter_Left = BigDecimal.valueOf(json.getDouble("PupilDiameter_Left")).floatValue();
            GazeData.PupilDiameter_Right = BigDecimal.valueOf(json.getDouble("PupilDiameter_Right")).floatValue();
            GazeData.PupilValidity_Left = json.getBoolean("PupilValidity_Left");
            GazeData.PupilValidity_Right = json.getBoolean("PupilValidity_Right");
            GazeData.Timestamp = json.getLong("Timestamp");

            // Notify Clients
            for (GazePointDataListener l : GazeDataHandler.gazePointDataListeners)
                l.gazePointDataReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleFixationInput(JSONObject json) {
        try {
            FixationData.Type = json.getString("Type");
            FixationData.EventType = json.getString("EventType");
            FixationData.X_Median = json.getDouble("X_Median");
            FixationData.Y_Median = json.getDouble("Y_Median");
            FixationData.Timestamp = json.getLong("Timestamp");

            long unixTime = System.currentTimeMillis();

            switch (FixationData.Type){
                case "FIXATIONS":
                    System.out.print("FIXATION " + FixationData.EventType + " at " + FixationData.X_Median + " - " + FixationData.Y_Median );

                    // Count Fixations and Saccades
                    if(FixationData.EventType.equals("BEGIN")){
                        Session.fixationCount += 1;
                    }

                    if(FixationData.EventType.equals("END")){
                        int o = 1;
                    }

                    // Counter is ID
                    FixationData.id = Session.fixationCount;

                    // Get Fixated UI Element
                    Map.Entry<Component, Boolean> compEntry = computeFixatedUIElement(FixationData.X_Median, FixationData.Y_Median);

                    // Get Fixated code Element
                    KeyValuePair<CodeElement, Boolean> codeElement = computeFixatedCodeElement(FixationData.X_Median, FixationData.Y_Median);

                    // Notify Clients
                    for (FixationDataListener l : GazeDataHandler.fixationDataListeners)
                        l.fixationDataReceived();
                    if(compEntry!=null) {
                        for (UIFixationListener l : GazeDataHandler.uiFixationListeners) {
                            l.uiElementFixated(compEntry.getKey());
                        }
                        if(compEntry.getValue() && previousUIComponent != compEntry.getKey()){
                            previousUIComponent = compEntry.getKey();
                            for(UIFixationCountListener l : GazeDataHandler.uiFixationCountListeners) {
                                l.uiElementFixationCounted(compEntry.getKey(), unixTime);
                            }
                        }
                    }
                    if(FixationData.EventType.equals("END") && previousCodeElement != null){
                        for(CodeFixationCountListener l : GazeDataHandler.codeFixationCountListeners) {
                            l.codeElementFixationCounted(previousCodeElement, FixationDataEventType.End, unixTime);
                            previousCodeElement = null;
                        }
                    }
                    if(codeElement != null){
                        previousCodeElement = codeElement.getKey();
                        for(CodeFixationListener l : GazeDataHandler.codeFixationListeners) {
                            l.codeElementFixated(codeElement.getKey());
                        }
                        if(codeElement.getValue()) {
                            for (CodeFixationCountListener l : GazeDataHandler.codeFixationCountListeners) {
                                l.codeElementFixationCounted(codeElement.getKey(), FixationDataEventType.Begin, unixTime);
                            }
                        }
                    }
                    break;
                case "SACCADES":
                    System.out.print("SACCADE at " + FixationData.X_Median + " - " + FixationData.Y_Median );
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map.Entry<Component, Boolean> computeFixatedUIElement(double x, double y){
        // Convert Normalized Coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        x = x*screenSize.getWidth();
        y = y*screenSize.getHeight();

        // Gaze Check each Component
        Iterator it = Session.componentList.entrySet().iterator();
        while (it.hasNext()) {
            try{
                Map.Entry pair = (Map.Entry) it.next();
                Component comp = (Component) pair.getValue();
                int compX = comp.getLocationOnScreen().x;
                int compY = comp.getLocationOnScreen().y;
                int compHeight = comp.getHeight();
                int compWidth = comp.getWidth();
                int boundX = compX + compWidth;
                int boundY = compY + compHeight;
                if (x > compX && x < boundX) {
                    if (y > compY && y < boundY) {
                        // Set boolean true if it is a new fixation
                        KeyValuePair<Component,Boolean> result = new KeyValuePair<>(comp, previousFixationForUI != FixationData.id);
                        previousFixationForUI = FixationData.id;
                        return result;
                    }
                }
            } catch (Exception e){
                //e.printStackTrace();
            }
        }
        return null;
    }

    public static KeyValuePair<CodeElement, Boolean> computeFixatedCodeElement(double x, double y){
        // Convert Normalized Coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        x = x*screenSize.getWidth();
        y = y*screenSize.getHeight();

        for(CodeElement codeElement : GazeDataHandler.codeElements){
            int boundX = codeElement.getX() + codeElement.getWidth();
            int boundY = codeElement.getY() + codeElement.getHeight();
            if(x > codeElement.getX() && x < boundX){
                if(y > codeElement.getY() && y < boundY){
                    // Set boolean true if it is a new fixation
                    KeyValuePair<CodeElement,Boolean> result = new KeyValuePair<>(codeElement, previousFixationForCode != FixationData.id);
                    previousFixationForCode = FixationData.id;
                    return result;
                }
            }
        }
        return null;
    }
}

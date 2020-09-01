package ur.eyex.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.externalSystem.service.execution.ExternalSystemOutputDispatcherFactory;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.SocketOption;
import java.util.HashMap;

class Startup_Test extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ApiHost apiHost = new ApiHost(Constants.Apis.TOBIIPRO);

        Project p = e.getDataContext().getData(PlatformDataKeys.PROJECT);

        // Start receiving Gaze Data
        apiHost.startSession(p, Constants.GazeDataTypes.FIXATIONS);

        // Visualize Gaze Data
        // apiHost.startGazePainter();

        // Add Code Elements and editor for Gaze Check
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CodeElement codeElement = new CodeElement(1,7, editor);
        GazeDataHandler.addCodeElement(codeElement);

        // Handle Gaze Point Data Stream
        GazeDataHandler.addGazePointDataListener(new GazePointDataListener() {
            @Override
            public void gazePointDataReceived() {
                double x = GazeData.X_Median;
                double y = GazeData.Y_Median;
            }
        });

        // Handle Fixation Data Stream
        GazeDataHandler.addFixationDataListener(new FixationDataListener() {
            @Override
            public void fixationDataReceived() {
                double x = FixationData.X_Median;
                double y = FixationData.Y_Median;
                int c = Session.fixationCount;
                System.out.println("X " + x + " - Y "+ y);
            }
        });

        ElementCounter uiCounter = new ElementCounter();

        // Handle UI Element Fixation Data Stream
        GazeDataHandler.addUIFixationListener(new UIFixationListener() {
            @Override
            public void uiElementFixated(Component c) {
                Component fixatedUIElement = c;
            }
        });

        // Handle UI Fixation Counts
        HashMap<Object, Integer> uiElementCounts = new HashMap<>();
        GazeDataHandler.addUIFixationCountListener(new UIFixationCountListener() {
            @Override
            public void uiElementFixationCounted(Component comp) {
                if(!uiElementCounts.containsKey(e)){
                    uiElementCounts.put(e, 1);
                }
                else{
                    uiElementCounts.replace(e, uiElementCounts.get(e) + 1);
                }
            }
        });

        // Handle Code Element Fixation Data Stream
        GazeDataHandler.addCodeFixationListener(new CodeFixationListener() {
            @Override
            public void codeElementFixated(CodeElement e) {
                CodeElement fixatedCodeElement = e;
            }
        });

        // Handle Code Element Fixation Counts
        HashMap<Object, Integer> codeElements = new HashMap<>();
        GazeDataHandler.addCodeFixationCountListener(new CodeFixationCountListener() {
            @Override
            public void codeElementFixationCounted(CodeElement e) {
                if(!codeElements.containsKey(e)){
                    codeElements.put(e, 1);
                }
                else{
                    codeElements.replace(e, codeElements.get(e) + 1);
                }
            }
        });
    }
}
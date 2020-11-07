package ur.eyex.intellij;

import java.util.ArrayList;

public class GazeDataHandler {
    static ArrayList<GazePointDataListener> gazePointDataListeners = new ArrayList<>();
    static ArrayList<FixationDataListener> fixationDataListeners = new ArrayList<>();
    static ArrayList<UIFixationListener> uiFixationListeners = new ArrayList<>();
    static ArrayList<CodeFixationListener> codeFixationListeners = new ArrayList<>();
    static ArrayList<CodeFixationCountListener> codeFixationCountListeners = new ArrayList<>();
    static ArrayList<UIFixationCountListener> uiFixationCountListeners = new ArrayList<>();

    static ArrayList<CodeElement> codeElements = new ArrayList<>();

    public static void addGazePointDataListener(GazePointDataListener listener){ gazePointDataListeners.add(listener); }

    public static void addFixationDataListener(FixationDataListener listener){
        fixationDataListeners.add(listener);
    }

    public static void addUIFixationListener(UIFixationListener listener){
        uiFixationListeners.add(listener);
    }

    public static void addCodeFixationListener(CodeFixationListener listener) { codeFixationListeners.add(listener); }

    public static void addCodeFixationCountListener(CodeFixationCountListener listener) { codeFixationCountListeners.add(listener); }

    public static void addUIFixationCountListener(UIFixationCountListener listener) { uiFixationCountListeners.add(listener); }

    public static void addCodeElement(CodeElement codeElement){
        codeElements.add(codeElement);
    }

    public static void clearAllListeners(){
        gazePointDataListeners.clear();
        fixationDataListeners.clear();
        uiFixationListeners.clear();
        codeFixationListeners.clear();
        codeFixationCountListeners.clear();
        uiFixationCountListeners.clear();
    }

}

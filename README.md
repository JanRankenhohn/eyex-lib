# EyeX IntelliJ Library
Java/IntelliJ library module of the EyeX Framework

## Features
- Connects to the ApiHost application to receive Eye-Tracking data
- Provides GazePoint and Fixation Data Streams
- Maps Eye-Gaze Data to UI and code elements
- Detects UI-Element and Code-Element fixations

## Usage
- ApiHost application needs to be running: https://github.com/JanRankenhohn/eyex-apihost
- Include and import the .jar file in your IntelliJ Plugin

<summary>Initialize the library in an Action class</summary>

```java
ApiHost apiHost = new ApiHost(Constants.Apis.TOBIICORE); // define the API to use

Project p = e.getDataContext().getData(PlatformDataKeys.PROJECT); // obtain project

apiHost.startSession(p, Constants.GazeDataTypes.FIXATIONS); // start session to obtain gaze points/fixations

apiHost.startGazePainter(); // you can test and validate the gaze data input with the built in Gaze Visualizer
```

<summary>Gaze Data Streams</summary>

```java
// Handle Gaze Point Data Stream
GazeDataHandler.addGazePointDataListener(new GazePointDataListener() {
    @Override
    public void gazePointDataReceived() {
        double x = GazeData.X_Median;
        double y = GazeData.Y_Median;
        System.out.println("GazePoint at X: " + x + " and Y: " + y);
    }
});

// Handle Fixation Data Stream
GazeDataHandler.addFixationDataListener(new FixationDataListener() {
    @Override
    public void fixationDataReceived() {
        double x = FixationData.X_Median;
        double y = FixationData.Y_Median;
        int fixationCount = Session.fixationCount;
        System.out.println("Fixation " + fixationCount + " at X: " + x + " and Y: " + y);
    }
});
```

<summary>Code Element Fixations (see also https://jetbrains.org/intellij/sdk/docs/tutorials/editor_basics/coordinates_system.html)</summary>

```java
final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR); // get current file editor
CodeElement codeElement = new CodeElement(1,7, editor);  // define code element
GazeDataHandler.addCodeElement(codeElement); // add code element

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
```

<summary>UI Element Fixations </summary>

```java
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
```


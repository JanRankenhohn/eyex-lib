package ur.eyex.intellij;

public abstract class Constants {
    public static String[] FixationDataEventTypes = {"Begin", "End", "Data"};
    public enum GazeDataTypes { FIXATIONS, GAZEPOINTS}
    public enum Apis { TOBIIPRO, TOBIICORE, EYELINK, IVIEWX}
}

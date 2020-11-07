package ur.eyex.intellij;

public interface CodeFixationCountListener {
    void codeElementFixationCounted(CodeElement codeElement, FixationDataEventType type, long timestamp);
}

package ur.eyex.intellij;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.TextRange;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents a Code Element in the Code Editor
 */
public class CodeElement {
    private int offsetStart;
    private int offsetEnd;
    private int lines;
    private String id;
    private String text;
    private int x;
    private int y;
    private int height;
    private int width;
    private Editor editor;

    /**
     * Creating a CodeElement
     * @param offsetStart Start coordinate of the element
     * @param offsetEnd End coordinate of the element
     * @param lines Lines the CodeElement comprises
     * @param id Use a description name as id as it gets logged
     * @param editor TextEditor of the IDE
     */
    public CodeElement(int offsetStart, int offsetEnd, int lines, String id, Editor editor){
        this.id = id;
        this.lines = lines;
        this.offsetStart = offsetStart;
        this.offsetEnd = offsetEnd;
        this.editor = editor;
        if(Session.sessionStarted)
            updateScreenCoordinates();
    }

    /**
     * Updates the screen coordinates of the CodeElement
     */
    void updateScreenCoordinates(){
        int lineHeight = editor.getLineHeight();
        Component editorComponent = Session.editorComponent;
        Point editorLocation =  editorComponent.getLocationOnScreen();
        int editorX = editorLocation.x + InternalConstants.fileEditorOffset;
        int editorY = editorLocation.y;
        Point p3 = editor.offsetToXY(offsetStart);
        Point p4 = editor.offsetToXY(offsetEnd);

        this.x = editorX + p3.x;
        this.y = editorY + p3.y;

        this.width = editorX + p4.x - this.x;
        this.height = lineHeight * lines;

        text = editor.getDocument().getText(new TextRange(offsetStart, offsetEnd));
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getId(){
        return id;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

package ur.eyex.intellij;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.TextRange;

import java.awt.*;
import java.awt.geom.Point2D;

public class CodeElement {
    private int offsetStart;
    private int offsetEnd;
    private String text;
    private int x;
    private int y;
    private int height;
    private int width;
    private Editor editor;

    public CodeElement(int offsetStart, int offsetEnd, Editor editor){
        this.offsetStart = offsetStart;
        this.offsetEnd = offsetEnd;
        this.editor = editor;
        if(Session.sessionStarted)
            updateScreenCoordinates();
    }

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
        this.height = editorY + lineHeight - this.y;

        text = editor.getDocument().getText(new TextRange(offsetStart, offsetEnd));

        //Graphics g = editorComponent.getGraphics();
        //g.fillRect(1, 1, 30, 14);
        //g.setColor(java.awt.Color.black);
        //g.drawRect(x,y,width,height);

        //Utils.createWindow(width, height, x, y, text);
        //Utils.createWindow(editorComponent.getWidth(), editorComponent.getHeight(), editorComponent.getLocationOnScreen().x - 5, editorComponent.getLocationOnScreen().y, "editor");

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

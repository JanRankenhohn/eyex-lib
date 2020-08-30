package ur.eyex.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class AoiSelector extends AnAction {
    @Override
    public void update(@NotNull final AnActionEvent e) {
        // Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        // Set visibility only in case of existing project and editor and if a selection exists
        e.getPresentation().setEnabledAndVisible( project != null
                && editor != null
                && editor.getSelectionModel().hasSelection() );
    }

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        // Get access to the editor and caret model.
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final CaretModel caretModel = editor.getCaretModel();
        EditorSettings s = editor.getSettings();
        int l = s.getLineCursorWidth();

        // Getting the primary caret ensures we get the correct one of a possible many.
        final Caret primaryCaret = caretModel.getPrimaryCaret();
        // Get the caret information
        LogicalPosition logicalPos = primaryCaret.getLogicalPosition();
        VisualPosition visualPos = primaryCaret.getVisualPosition();
        int caretOffset = primaryCaret.getOffset();

        int lineHeight = editor.getLineHeight();

        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        Point2D p3 = editor.offsetToXY(start);
        Point2D p4 = editor.offsetToXY(end);

        Point p = editor.visualPositionToXY(visualPos);
        Point2D p2 = editor.visualPositionToPoint2D(visualPos);

        // Build and display the caret report.
        String report = logicalPos.toString() + "\n" + visualPos.toString() + "\n" +
                "Offset: " + caretOffset;
        Messages.showInfoMessage(report.toString(), "Caret Parameters Inside The Editor");
    }

    private static void createWindow(int width, int height, int posX, int posY, String descr) {
        //Create and set up the window.
        JFrame frame = new JFrame(descr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.
        frame.setLocation(posX, posY);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
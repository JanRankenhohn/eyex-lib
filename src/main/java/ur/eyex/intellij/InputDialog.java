package ur.eyex.intellij;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class InputDialog extends DialogWrapper {

    public JTextField textField;

    public InputDialog() {
        super(true); // use current window as parent
        init();
        setTitle("Test DialogWrapper");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Participant ID");
        textField = new JTextField();
        dialogPanel.add(label, BorderLayout.NORTH);
        dialogPanel.add(textField, BorderLayout.CENTER);

        return dialogPanel;
    }
}

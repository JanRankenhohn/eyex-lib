package ur.eyex.intellij;

import javax.swing.*;
import ur.eyex.intellij.Constants.GazeDataTypes;

class GazePainterFrame extends JFrame {

    private GazeDataTypes dataType;

    public GazePainterFrame(GazeDataTypes dataType) {
        this.dataType = dataType;
        initUI();
    }

    private void initUI() {

        GazePainter gazePainter = new GazePainter(this.dataType);
        add(gazePainter);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setTitle("EyeX - Gaze Point Data Painter");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PaintThread paintThread = new PaintThread(gazePainter);
        paintThread.start();
    }
}

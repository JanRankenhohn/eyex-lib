package ur.eyex.intellij;

import javax.swing.*;
import java.awt.*;
import ur.eyex.intellij.Constants.GazeDataTypes;

class GazePainter extends JPanel {

    private GazeDataTypes dataType;

    public GazePainter(GazeDataTypes dataType){
        this.dataType = dataType;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);
        g2d.setPaint(Color.red);

        int xOnScreen = 0;
        int yOnScreen = 0;

        switch (this.dataType){
            case GAZEPOINTS:
                xOnScreen = (int) Math.round(GazeData.X_Median * 1920);
                yOnScreen = (int) Math.round(GazeData.Y_Median * 1080);
                break;
            case FIXATIONS:
                xOnScreen = (int) Math.round(FixationData.X_Median * 1920);
                yOnScreen = (int) Math.round(FixationData.Y_Median * 1080);
                break;
        }

        g2d.drawOval(xOnScreen-15,yOnScreen-15,30,30);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
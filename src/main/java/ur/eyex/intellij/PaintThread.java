package ur.eyex.intellij;

class PaintThread extends Thread{
    private GazePainter gazePainter;

    public PaintThread(GazePainter gazePainter){
        this.gazePainter = gazePainter;
    }

    public void run(){
        while(true){
            gazePainter.repaint();
        }
    }
}
import ur.eyex.intellij.ApiHost;
import ur.eyex.intellij.Constants;

public class test {
    public static void main(String[] args) {
        ApiHost apiHost = new ApiHost(Constants.Apis.TOBIICORE);
        apiHost.startGazePainter();
    }
}

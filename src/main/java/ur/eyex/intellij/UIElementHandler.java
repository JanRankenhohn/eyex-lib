package ur.eyex.intellij;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

class UIElementHandler {

    public static HashMap<String, Component> getUIComponents(){
        IdeFrame frame = WindowManager.getInstance().getIdeFrame(Session.project);
        JComponent frameComponent = frame.getComponent();
        HashMap<String, Component> uiComponents = filterUIComponents(frameComponent);
        return  uiComponents;
    }

    public static Component getFileEditorComponent(HashMap<String, Component> components){
        Iterator it = components.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getKey() == "FileEditor"){
                return (Component)pair.getValue();
            }
        }
        return null;
    }

    private static HashMap<String, Component> filterUIComponents(final Container c) {
        Component[] comps = c.getComponents();
        HashMap<String,Component> compList = new HashMap<>();
        for (Component comp : comps) {
            String name = comp.getClass().getName();
            String simpleName = comp.getClass().getSimpleName();
            Container container;
            // Main Menu Bar
            if(name.contains("CustomHeaderMenuBar")) {
                compList.put("MainMenuBar", comp);
                // Comment in for more detailed UI Components (Each Button on the MainMenuBar)
                //container = (Container) comp;
                //for (Component innerComp : container.getComponents()) {
                    //String innerCompName = innerComp.getClass().getSimpleName();
                    //if (innerCompName.contains("ActionMenu")) {
                        //innerCompName = "MenuBar_" + getField(innerComp, "text");
                    //} else {
                        //innerCompName = "MenuBar_" + innerCompName;
                    //}
                    //compList.put(innerCompName, innerComp);
                //}
            }
            // Toolbar
            //if(simpleName.contains("ActionToolbarImpl")) {
                //compList.put("Toolbar", comp);
                // Comment in for more detailed UI Components (Each Icon on the Toolbar)
                //container = (Container) comp;
                //for (Component innerComp : container.getComponents()) {
                    //String innerCompName = innerComp.getClass().getName();
                    //if (innerCompName.contains("ActionToolbarImpl")) {
                        //innerCompName = "Toolbar_" + getField(innerComp, "myAction");
                        //compList.put(innerCompName, innerComp);
                    //}
                //}
            //}
            //Navbar
            //if(name.contains("NavBarPanel")) {
                //compList.put(simpleName, comp);
            //}
            // ToolWindowBar
            //if(name.contains("Stripe")) {
                //container = (Container)comp;
                //for (Component innerComp : container.getComponents()) {
                    //String innerCompName = innerComp.getClass().getName();
                    //if(innerCompName.contains("StripeButton")){
                        //innerCompName = "ToolWindowBar_" + getField(innerComp, "text");
                        //compList.put(innerCompName, innerComp);
                    //}
                //}
            //}
            // EditorTabs
            //if(name.contains("JBTabsImpl")) {
                //container = (Container)comp;
                //for (Component innerComp : container.getComponents()) {
                    //String innerCompName = innerComp.getClass().getName();
                    //if(innerCompName.contains("EditorTabs")){
                        //innerCompName = "EditorTabs_" + getField(innerComp, "myInfo");
                        //compList.put(innerCompName, innerComp);
                    //}
                //}
            //}
            //FileEditor
            if(name.contains("EditorComponentImpl")) {
                compList.put("FileEditor", comp);
            }
            //ProjectWindow
            if(name.contains("ProjectViewImpl")){
                compList.put("ProjectWindow", comp);
            }
            if(name.contains("TerminalToolWindowPanel")){
                compList.put("Terminal", comp);
            }

            if (comp instanceof Container)
                compList.putAll(filterUIComponents((Container) comp));
        }
        return compList;
    }

    private static String getField(Object obj, String fieldName) {
        ArrayList<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, obj.getClass());
        for(Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if(name.equals(fieldName)){
                try {
                    return field.get(obj).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }
}

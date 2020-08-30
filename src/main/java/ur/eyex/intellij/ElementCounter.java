package ur.eyex.intellij;

import java.util.ArrayList;
import java.util.HashMap;

public class ElementCounter {
    public Object previousElement;
    public HashMap<Object, Integer> codeElements;

    public ElementCounter(){
        previousElement = null;
        codeElements = new HashMap<>();
    }

    public void count(Object object){
        if(previousElement != null){
            if (!previousElement.equals(object)) {
                if(object != null){
                    if(!codeElements.containsKey(object)){
                        codeElements.put(object, 1);
                    }
                    else{
                        codeElements.replace(object, codeElements.get(object) + 1);
                    }
                }
            }
        }
        else if(object != null){
            if(!object.equals(previousElement)){
                if(!codeElements.containsKey(object)){
                    codeElements.put(object, 1);
                }
                else{
                    codeElements.replace(object, codeElements.get(object) + 1);
                }
            }
        }
        previousElement = object;
    }
}

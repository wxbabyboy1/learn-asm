package tree.change;


import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

public class RemoveMethodTransformer {
    private String fieldName;  
    private String fieldDesc;  
  
    public RemoveMethodTransformer(String fieldName, String fieldDesc) {  
        this.fieldName = fieldName;  
        this.fieldDesc = fieldDesc;  
    }  
  
    public void transform(ClassNode cn) {
        Iterator<MethodNode> i = cn.methods.iterator();
        while (i.hasNext()) {  
            MethodNode mn = i.next();
            if (mn.name.equals(fieldName) ) {  
                i.remove();  
            }  
        }  
    }  
}  
package tree.generate;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.FileOutputStream;

public class GenerateClasses {
  
    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(Opcodes.ASM5);
        ClassNode cn = gen();
        cn.accept(cw);  
        File file = new File("D:\\ideaspace\\cmc-voucher-send-down\\learnasm\\target\\ChildClass.class");
        try (FileOutputStream fout = new FileOutputStream(file)) {
            fout.write(cw.toByteArray());
            fout.close();
        }
  
    }  
  
    private static ClassNode gen() {  
        ClassNode classNode = new ClassNode();  
        classNode.version = Opcodes.V1_8;  
        classNode.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT;  
        classNode.name = "asm/core/ChildClass";  
        classNode.superName = "java/lang/Object";  
        classNode.interfaces.add("asm/core/ParentInter");  
        classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                        "zero", "I", null, new Integer(0)));
        classNode.methods.add(new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT,
                "compareTo","(Ljava/lang/Object;)I", null, null));
        return classNode;  
  
    }  
}  
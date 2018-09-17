package success.changeAccess;



import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

//ClassVisitor vca = new AClassVisitor(classWriter);ClassVisitor cvb= new BClassVisitor(cva)
//也可以通过传入一个调用链数组给一个Adalter
public class MultiClassAdapter extends ClassVisitor {
    protected ClassVisitor[] cvs;
    public MultiClassAdapter(ClassVisitor[] cvs) {
        super(Opcodes.ASM4);
        this.cvs = cvs;  
    }  
    @Override public void visit(int version, int access, String name,  
                                String signature, String superName, String[] interfaces) {  
        for (ClassVisitor cv : cvs) {  
            cv.visit(version, access, name, signature, superName, interfaces);  
        }  
    }  
}  
   
package success.changeAccess;


import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ChangeAccessAdapter extends ClassVisitor {

    private int addAccess;
    private String addName;
    private String addDesc;
    private String addSignature;
    private Object addValue;

    boolean isFieldPresent;

    public ChangeAccessAdapter(ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
    }

    public ChangeAccessAdapter(int access, String addName, String desc, String signature, Object value, ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
        this.addAccess = access;
        this.addName = addName;
        this.addDesc = desc;
        this.addSignature = signature;
        this.addValue = value;
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        cv.visit(version, Opcodes.ACC_PUBLIC, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if(name.startsWith("m")){
            return null;
        }
        if (name.equals(addName)) {
            isFieldPresent = true;
        }
        return this.cv.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if(!isFieldPresent){
            FieldVisitor fv = cv.visitField(this.addAccess, addName, addDesc, addSignature, addValue);
            if(fv != null){//在visitEnd方法中我们需要判断FieldVisitor实例是否为空，因为visitField方法的实现中，是会有返回null的情况。
                fv.visitEnd();
            }
        }
        this.cv.visitEnd();
    }
}  
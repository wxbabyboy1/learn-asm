package success.generate;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * 打印字节码信息
 */
public class PrintTool {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "cc/CCC", null, "java/lang/Object", null);

        cw.visitSource("C2.java", null);

//        {
            FieldVisitor fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "toTimer", "J", null, null);
            fv.visitEnd();
//        }
//        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
//        }
//        {
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()V", null, new String[] { "java/lang/InterruptedException" });
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "cc/CCC", "toTimer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitInsn(LSUB);
            mv.visitFieldInsn(PUTSTATIC, "cc/CCC", "toTimer", "J");
            mv.visitLdcInsn(new Long(100L));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V");
            mv.visitFieldInsn(GETSTATIC, "cc/CCC", "toTimer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitInsn(LADD);
            mv.visitFieldInsn(PUTSTATIC, "cc/CCC", "toTimer", "J");
            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 1);
            mv.visitEnd();
//        }
        cw.visitEnd();

        byte[] data = cw.toByteArray();

        //打印字节码信息
        ClassReader cr = new ClassReader(data);
        ClassWriter cw2 = new ClassWriter(0);
        TraceClassVisitor cv = new TraceClassVisitor(cw2, new PrintWriter(System.out));
        CheckClassAdapter tcv = new CheckClassAdapter(cv);//检查是否生成了被虚拟机拒绝的无效类，顺序不同，指令顺序也会不同
        cr.accept(tcv, 0);
    }
}

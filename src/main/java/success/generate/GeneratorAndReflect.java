package success.generate;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import success.addField.MyClassLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;

/**
 * 写一个想要的类和字段、方法
 * 利用byCode生成字节码，去掉Lable相关，然后一个大括号是一个部分，比如：字段、方法初始化、方法
 */
public class GeneratorAndReflect {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, ACC_PUBLIC + ACC_SUPER, "cc/CCC", null, "java/lang/Object", null);

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

        String className = "cc.CCC";
        Class<?> clazz = new MyClassLoader().defineClassForName(className, data);
        //构造对象
        Object object = clazz.newInstance();
        //调用方法
        clazz.getMethods()[0].invoke(object);
        //获取字段
        Long toTimer = clazz.getField("toTimer").getLong(object);
        System.out.println(toTimer);
    }
}

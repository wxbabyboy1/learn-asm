package success.changeAccess;

import jdk.internal.org.objectweb.asm.*;
import success.addField.MyClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static jdk.internal.org.objectweb.asm.Opcodes.*;


/**
 * 写一个想要的类和字段、方法
 * 利用byCode生成字节码，去掉Lable相关，然后一个大括号是一个部分，比如：字段、方法初始化、方法
 * 修改修饰符，移除方法，添加方法
 */
public class GeneratorAndReflectAndChangeMethod {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC+ACC_ABSTRACT + ACC_SUPER, "cc/CCC", null, "java/lang/Object", null);

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
        toPrint(clazz);


        cw = new ClassWriter(0);
        //  改变class的访问修饰
//        ClassVisitor cv = new ChangeAccessAdapter(cw);
        ClassVisitor cv = new ChangeAccessAdapter(ACC_PUBLIC + ACC_STATIC, "getName", "J", null, null, cw);
        ClassReader cr = new ClassReader(data);
        cr.accept(cv, 0);
        byte[] dd = cw.toByteArray();// byt 和toByte其实是相同的数组

        clazz = new MyClassLoader().defineClassForName(className, dd);
        toPrint(clazz);
    }

    private static void toPrint(Class<?> clazz) {
        System.out.println("class:!!!!!!!!!!!!!!!!!!");
        //输出修饰符
        System.out.println(Modifier.toString(clazz.getModifiers()));
        //输出字段名
        System.out.println("field:<<<<<<<<<<<<<<<<<<<<");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field.getName());
        }
        //输出方法名
        System.out.println("method:============================");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
    }
}

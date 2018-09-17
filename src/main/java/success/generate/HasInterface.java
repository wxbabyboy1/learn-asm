package success.generate;


import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import success.addField.MyClassLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;

public class HasInterface {

    public static void main(String[] args) throws Exception {
        //生成接口class
        byte[] data = partenerDump();
        File file = new File("D:\\ideaspace\\cmc-voucher-send-down\\learnasm\\target\\classes\\cc\\Partner.class");
        try (FileOutputStream fout = new FileOutputStream(file)) {
            fout.write(data);
            fout.close();
        }

        //生成并调用实现接口的类
        invokePerson();
    }

    public static byte[] partenerDump() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "cc/Partner", null, "java/lang/Object", null);
        cw.visitSource("Partner.java", null);
//            {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "getName", "()Ljava/lang/String;", null, null);
        mv.visitEnd();
//            }
        cw.visitEnd();
        return cw.toByteArray();
    }

    private static void invokePerson() throws Exception {
        String className = "cc.Person";
        byte[] data = dump();

        Class<?> clazz = new MyClassLoader().defineClassForName(className, data);
        //构造对象
        Object object = clazz.newInstance();
        //获取字段
        Method getName = clazz.getMethod("getName");
        Object invoke = getName.invoke(object);
        System.out.println(invoke);
    }

    public static byte[] dump() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "cc/Person", null, "java/lang/Object", new String[]{"cc/Partner"});
        cw.visitSource("Person.java", null);
//        {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
//        }
//        {
        mv = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLdcInsn("Tom");
        mv.visitInsn(ARETURN);
        Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
//        }
        cw.visitEnd();
        return cw.toByteArray();
    }

}

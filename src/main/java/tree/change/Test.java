package tree.change;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import success.addField.MyClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Test {
    public static void main(String[] args) throws Exception{
        ClassReader cr = new ClassReader("tree.change.Task");
        ClassNode cn = new ClassNode();
        cr.accept(cn,0);
        RemoveMethodTransformer rt = new RemoveMethodTransformer("isNeedRemove","V");
        rt.transform(cn);
        AddFieldTransformer at= new AddFieldTransformer(Opcodes.ACC_PRIVATE,"addedField","I");
        at.transform(cn);
        ClassWriter cw = new ClassWriter(0);
        cn.accept(cw);
        byte[] toByte = cw.toByteArray();

        String className = "tree.change.Task";
        Class<?> clazz = new MyClassLoader().defineClassForName(className, toByte);
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

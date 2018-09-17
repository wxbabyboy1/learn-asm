package success.addField;


import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

/**
 * 添加字段，修改方法
 */
public class Generator {

    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader("success/addField/C");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classAdapter = new AddTimeClassVisitor(cw);
        //使给定的访问者访问Java类的ClassReader
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();

        Generator cc = new Generator();
        cc.print(data);
        
    }

    private void print(byte[] data) throws Exception {
        String className = "success.addField.C";
        Class<?> clazz = new MyClassLoader().defineClassForName(className, data);
        //构造对象
        Object object = clazz.newInstance();
        //调用方法
        clazz.getMethods()[0].invoke(object);
        //获取字段
        Long timer = clazz.getField("timer").getLong(object);
        System.out.println(timer);

    }

}
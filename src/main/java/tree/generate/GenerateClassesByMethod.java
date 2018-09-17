package tree.generate;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * tree api method 生成字节码
 */
public class GenerateClassesByMethod {

    public static void main(String[] args) throws IOException {
        ClassNode classNode = new ClassNode();
        classNode.version = Opcodes.V1_8;
        classNode.access = Opcodes.ACC_PUBLIC;
        classNode.name = "bytecode/TreeMethodGenClass";
        classNode.superName = "java/lang/Object";
        classNode.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "espresso", "I", null, null));
        // public void addEspresso(int espresso) 方法生命
        MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "addEspresso", "(I)V", null, null);
        classNode.methods.add(mn);

        InsnList il = mn.instructions;
        il.add(new VarInsnNode(Opcodes.ILOAD, 1));
        il.add(new InsnNode(Opcodes.ICONST_1));
        LabelNode label = new LabelNode();
        // if (espresso > 0) 跳转通过LabelNode标记跳转地址
        il.add(new JumpInsnNode(Opcodes.IF_ICMPLE, label));
        il.add(new VarInsnNode(Opcodes.ALOAD, 0));
        il.add(new VarInsnNode(Opcodes.ILOAD, 1));
        // this.espresso = var1;
        il.add(new FieldInsnNode(Opcodes.PUTFIELD, "bytecode/TreeMethodGenClass", "espresso", "I"));
        LabelNode end = new LabelNode();
        il.add(new JumpInsnNode(Opcodes.GOTO, end));
        // label 后紧跟着下一个指令地址
        il.add(label);
        // java7之后对stack map frame 的处理
        il.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
        // throw new IllegalArgumentException();
        il.add(new TypeInsnNode(Opcodes.NEW, "java/lang/IllegalArgumentException"));
        il.add(new InsnNode(Opcodes.DUP));
        il.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V", false));
        il.add(new InsnNode(Opcodes.ATHROW));
        il.add(end);
        // stack map 的第二次偏移记录
        il.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
        il.add(new InsnNode(Opcodes.RETURN));
        // 局部变量表和操作数栈大小的处理
        mn.maxStack = 2;
        mn.maxLocals = 2;
        mn.visitEnd();
        // 打印查看class的生成结果
        ClassWriter cw = new ClassWriter(Opcodes.ASM5);
        classNode.accept(cw);
//        File file = new File("d:\\TreeMethodGenClass.class");
        File file = new File("D:\\ideaspace\\cmc-leaguecinema-parent\\cmc-leaguecinema-task\\target\\classes\\TreeMethodGenClass.class");
        try (FileOutputStream fout = new FileOutputStream(file)) {
            fout.write(cw.toByteArray());
            fout.close();
        }
    }
}


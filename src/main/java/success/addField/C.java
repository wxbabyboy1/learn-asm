package success.addField;

/*
字节码修改：添加一个变量
public class C {
    public static long timer;
    public void m() throws InterruptedException{
        timer -= System.currentTimeMillis();
        Thread.sleep(100);
        timer += System.currentTimeMillis();
    }
}*/

public class C {
    public void m() throws InterruptedException{
        Thread.sleep(100);
        String a = "123";
    }
}
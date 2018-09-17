package success.addField;

public class C2 {
    public static long timer;
    public void m() throws InterruptedException{
        timer -= System.currentTimeMillis();
        Thread.sleep(100);
        timer += System.currentTimeMillis();
    }
}
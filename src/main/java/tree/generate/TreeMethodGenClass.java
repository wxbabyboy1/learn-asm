package tree.generate;

public class TreeMethodGenClass {
    private int espresso;

    public void addEspresso(int var1) {
        if (var1 > 1) {
            this.espresso = var1;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
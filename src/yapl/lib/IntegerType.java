package yapl.lib;

public class IntegerType extends Type {

    int value;

    public IntegerType() {
    }

    public IntegerType(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

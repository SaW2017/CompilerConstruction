package yapl.lib;

import yapl.compiler.Attrib;
import yapl.compiler.Token;

public class BooleanType extends Type {
    boolean value;

    public BooleanType() {
    }

    public BooleanType(boolean value) {
        this.value = value;
    }

}
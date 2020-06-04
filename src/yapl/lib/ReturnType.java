package yapl.lib;

import java.util.List;

public class ReturnType extends Type {

    public ReturnType(Type type) {
        this.type = type;
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

package yapl.lib;

import java.util.List;

public class ProcedureType extends Type {

    public ProcedureType(Type returnType) {
        this.returnType = returnType;
    }

    private Type returnType;
    private List<Type> parameterTypes;

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<Type> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}

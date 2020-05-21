package yapl.lib;

public class RecordType extends Type {

    private String typeName;

    public RecordType(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}

package yapl;

import java.util.LinkedList;

public class Scope {

    String name;

    public LinkedList<Scope> subScopes;

    public Scope(String name){
        this.name = name;
        subScopes = new LinkedList<Scope>();
    }
}

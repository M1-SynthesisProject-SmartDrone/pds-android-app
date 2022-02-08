package communicationAPI;

public abstract class Content {

    // --- Variables ---

    private Object value;

    // --- Methods ---

    public Content(){

    }

    public Content(Object elt){
        value = elt;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

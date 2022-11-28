package shop;

public class Item {
    private final String name;
    private final int quantity;

    public Item(String Name, int Quantity) {
        this.name = Name;
        this.quantity = Quantity;
    }

    public void check() {
        if (invalidName())
            throw new ItemNameException("Name is null or empty");

        if (invalidQuantity())
            throw new ItemQuantityException("Size is out of range");
    }
    private boolean invalidName(){
        return ((name == null) || (name.isEmpty()));
    }

    private boolean invalidQuantity(){
        return ((quantity < 1) || (quantity > 100));
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}

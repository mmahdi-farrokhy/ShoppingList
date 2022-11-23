package shop;

public class Item {
    private String Name;
    private int Quantity;

    public Item(String Name, int Quantity) {
        this.Name = Name;
        this.Quantity = Quantity;
    }

    public void check() {
        if (Name == null || Name.isEmpty())
            throw new ItemNameException("Name is null or empty");

        if (Quantity < 1 || Quantity > 100)
            throw new ItemQuantityException("Size is out of range");
    }

    public String getName() {
        return Name;
    }

    public int getQuantity() {
        return Quantity;
    }
}

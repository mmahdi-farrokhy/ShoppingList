package shop;

import java.util.List;

public class ShoppingListServiceImplementer implements ShoppingListService {
    final ShoppingListDAO shoppingListDAO;

    // Dependency Injection
    public ShoppingListServiceImplementer(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    @Override
    public List<Item> findAllItems() {
        return shoppingListDAO.findAllItems();
    }
    @Override
    public void saveItems(List<Item> items) {
        for (Item item : items) {
            item.check();
        }
        shoppingListDAO.saveItems(items);
    }
    @Override
    public int countRecords() {
        return shoppingListDAO.countRecords();
    }
    @Override
    public void clearList() {
        shoppingListDAO.clearList();
    }
}
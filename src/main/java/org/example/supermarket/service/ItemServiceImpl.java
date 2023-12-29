package org.example.supermarket.service;

import org.example.supermarket.dao.ItemDAO;
import org.example.supermarket.model.Item;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private final ItemDAO itemDAO;

    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void addItem(Item item) {
        itemDAO.addItem(item);
    }

    @Override
    public Item getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }

    @Override
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    @Override
    public void updateItem(Item item) {
        itemDAO.updateItem(item);
    }

    @Override
    public void deleteItem(int itemId) {
        itemDAO.deleteItem(itemId);
    }
}

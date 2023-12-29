package org.example.supermarket.service;

import org.example.supermarket.model.Item;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    Item getItemById(int itemId);
    List<Item> getAllItems();
    void updateItem(Item item);
    void deleteItem(int itemId);
}

package com.florin.restaurant.service;

import com.florin.restaurant.menu.Menu;

import java.util.List;


public interface MenuService {

List<Menu> getMenus();
    Menu addMenu(Menu menu);
    void deleteMenu(int id);
    Menu getMenuById(int id);
    Menu updateMenu(Menu menu);



}

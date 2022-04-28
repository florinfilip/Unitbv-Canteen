package com.florin.restaurant.service.Impl;

import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.repository.MenuRepository;
import com.florin.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(int id) {
return menuRepository.findById(id)
        .orElseThrow(()->new NotFoundException("Menu not found"));
    }


    @Override
    public Menu updateMenu(Menu menu) {
Menu savedMenu = getMenuById(menu.getId());
savedMenu.setName(Optional.ofNullable(menu.getName()).orElse(savedMenu.getName()));
savedMenu.setDescription(Optional.ofNullable(menu.getDescription()).orElse(savedMenu.getDescription()));
savedMenu.setUrl(Optional.ofNullable(menu.getUrl()).orElse(savedMenu.getUrl()));

return menuRepository.save(savedMenu);
    }

    @Override
    public void deleteMenu(int menuId) {
        Menu menu = getMenuById(menuId);
        menuRepository.delete(menu);

    }


    @Override
    public List<Menu> getMenus() {

        return menuRepository.findAll();
    }
}


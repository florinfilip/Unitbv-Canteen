package com.florin.restaurant.controller;


import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.service.MenuService;
import com.florin.restaurant.util.Mappings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(Mappings.MENUS)
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

@PostMapping(Mappings.ADD)
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu){
    Menu savedMenu = menuService.addMenu(menu);
    return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
}

@GetMapping("/menu/{menuId}")
    public ResponseEntity<Menu> getMenu(@PathVariable int menuId){
    Menu menuById= menuService.getMenuById(menuId);
    return new ResponseEntity<>(menuById,HttpStatus.OK);
}

@PutMapping (Mappings.MENU)
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu){
    Menu updatedMenu = menuService.updateMenu(menu);
    return new ResponseEntity<>(updatedMenu,HttpStatus.OK);
}

@DeleteMapping(Mappings.MENU+"/{menuId}")
public ResponseEntity<Menu> deleteMenu(@PathVariable int menuId){
    menuService.deleteMenu(menuId);
    return new ResponseEntity<>(HttpStatus.OK);
}


@GetMapping(Mappings.ALL)
    public ResponseEntity<List> getAllMenus(Model model){

    var menus = menuService.getMenus();
    if(menus==null){
        throw new NotFoundException("Empty table");
    }

    return new ResponseEntity<>(menus, HttpStatus.OK);

}

}

package com.florin.restaurant.controller;


import com.florin.restaurant.menu.Menu;

import com.florin.restaurant.menu.MenuExporter;
import com.florin.restaurant.menu.MenuImporter;
import com.florin.restaurant.service.Impl.FileUploadService;
import com.florin.restaurant.service.MenuService;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.florin.restaurant.util.ViewNames.ADMIN;
import static com.florin.restaurant.util.ViewNames.REDIRECT;

@Controller
public class AdminController {

private final MenuService menuService;
private final FileUploadService fileUploadService;

    public AdminController(MenuService menuService, FileUploadService fileUploadService) {
        this.menuService = menuService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping(ADMIN)
    public String admin(){
        return ADMIN;
    }

    @GetMapping("admin/export")
    @ResponseBody
    public String exportToExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue="attachment; filename=Menu_info.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Menu> menuList = menuService.getMenus();
        MenuExporter exp = new MenuExporter(menuList);
        exp.export(response);
        return "Export Successfully";
    }
    @PostMapping("admin/import")
    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException{
        MenuImporter excelImporter = new MenuImporter();
        List<Menu> menuList = excelImporter.excelImport(file);
        menuList.forEach(menuService::addMenu);

        fileUploadService.uploadFile(file);
        System.out.println(file.getOriginalFilename());
        return REDIRECT+"admin";
    }
}

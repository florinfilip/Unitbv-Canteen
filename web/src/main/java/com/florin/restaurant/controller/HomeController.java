package com.florin.restaurant.controller;

import com.florin.restaurant.game.Game;
import com.florin.restaurant.service.GameService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.florin.restaurant.util.AttributeNames.GAME_SERVICE;
import static com.florin.restaurant.util.Mappings.HOME;

@Controller
@RequestMapping(HOME)
public class HomeController {
private final GameService gameService;

    public HomeController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(HOME)
    public String home (Model model){
        User user = new User();
        model.addAttribute(GAME_SERVICE, gameService);
        return HOME;
    }
}

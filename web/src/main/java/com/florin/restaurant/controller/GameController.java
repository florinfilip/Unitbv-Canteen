package com.florin.restaurant.controller;


import com.florin.restaurant.service.GameService;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {


    private final GameService gameService;


    @GetMapping(Mappings.PLAY)
    public String play(Model model){
        model.addAttribute(AttributeNames.MAIN_MESSAGE,gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE,gameService.getResultMessage());

        if(gameService.isGameOver())
            return ViewNames.GAME_OVER;
        return ViewNames.PLAY;
    }

    @PostMapping(Mappings.PLAY)
    public String processMessage(@RequestParam int guess){

        gameService.checkGuess(guess);
        return Mappings.REDIRECT_PLAY;
    }




}

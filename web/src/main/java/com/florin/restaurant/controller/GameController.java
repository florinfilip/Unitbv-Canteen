package com.florin.restaurant.controller;

import com.florin.restaurant.model.Reward;
import com.florin.restaurant.service.GameService;
import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.service.RewardService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import static com.florin.restaurant.util.AttributeNames.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final RewardService rewardService;
    private final MyUserDetailsService userDetailsService;

    @GetMapping(Mappings.PLAY)
    public String play(Model model,
                       @AuthenticationPrincipal Authentication authentication){
        User loggedUser = userDetailsService.getCurrentlyLoggedUser(authentication).getUser();

        model.addAttribute(MAIN_MESSAGE, gameService.getMainMessage(loggedUser));
        model.addAttribute(RESULT_MESSAGE, gameService.getResultMessage());
        model.addAttribute(GAME_SERVICE, gameService);
        model.addAttribute(USER, loggedUser);

        if(gameService.isGameWon()){
            Reward reward = gameService.getGame().getReward();
            rewardService.saveReward(reward,loggedUser);
            loggedUser.setLastPlayed(LocalDate.now());
            userDetailsService.updateUser(loggedUser);
            return ViewNames.GAME_OVER;
}
        if(gameService.isGameLost())
        { loggedUser.setLastPlayed(LocalDate.now());
            userDetailsService.updateUser(loggedUser);
            return ViewNames.GAME_OVER;}
        return ViewNames.PLAY;
    }

    @PostMapping(Mappings.PLAY)
    public String processMessage(@RequestParam String guess){
        if(Strings.isNullOrEmpty(guess)){
            guess="0";
        }
        gameService.checkGuess(Integer.valueOf(guess));
        return Mappings.REDIRECT_PLAY;
    }
}

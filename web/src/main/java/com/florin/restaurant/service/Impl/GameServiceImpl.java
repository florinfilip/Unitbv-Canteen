package com.florin.restaurant.service.Impl;

import com.florin.restaurant.game.Game;
import com.florin.restaurant.game.MessageGenerator;
import com.florin.restaurant.service.GameService;
import com.florin.restaurant.user.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Data
public class GameServiceImpl implements GameService {

    private final Game game;
    private final MessageGenerator messageGenerator;

    @Autowired
    public GameServiceImpl(Game game, MessageGenerator messageGenerator) {
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    @PostConstruct
    public void init(){
        log.info(messageGenerator.getMainMessage());
    }

    @Override
    public boolean isGameTime(User user) {
        Optional<LocalDate> optionalLastPlayed=Optional.ofNullable(user.getLastPlayed());
        LocalDate lastPlayed = optionalLastPlayed.orElse(LocalDate.of(2021,01,01));
       return lastPlayed.compareTo(LocalDate.now()) < 0;
    }

    @Override
    public boolean isGameOver() {
       return game.isGameWon() || game.isGameLost();
    }

    @Override
    public boolean isGameWon() {
        return game.isGameWon();
    }

    @Override
    public boolean isGameLost() {
        return game.isGameLost();
    }

    @Override
    public String getMainMessage(User user) {
        if(isGameTime(user))
       return messageGenerator.getMainMessage();
        return String.format("You already had your discount code on: %s! Come back next week for more deals!",user.getLastPlayed());
    }

    @Override
    public String getResultMessage() {
        return messageGenerator.getResultMessage();
    }

    @Override
    public void checkGuess(int guess) {
        game.setGuess(guess);
        game.check();
    }

    @Override
    public void reset() {
        game.reset();
    }
}

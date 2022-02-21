package com.florin.restaurant.service.Impl;


import com.florin.restaurant.game.Game;
import com.florin.restaurant.game.MessageGenerator;
import com.florin.restaurant.service.GameService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
        log.info("Number to guess is = {}", game.getNumber());
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
    public String getMainMessage() {
       return messageGenerator.getMainMessage();
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

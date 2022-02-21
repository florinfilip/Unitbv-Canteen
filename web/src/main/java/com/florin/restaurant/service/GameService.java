package com.florin.restaurant.service;

import com.florin.restaurant.game.Game;

public interface GameService {

    boolean isGameOver();
    boolean isGameWon();
    boolean isGameLost();
    String getMainMessage();
    String getResultMessage();
    Game getGame();
    void checkGuess(int guess);
    void reset();
}

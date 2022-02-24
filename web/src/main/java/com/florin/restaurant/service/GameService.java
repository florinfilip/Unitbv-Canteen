package com.florin.restaurant.service;

import com.florin.restaurant.game.Game;
import com.florin.restaurant.user.User;

public interface GameService {

    boolean isGameTime(User user);

    boolean isGameOver();

    boolean isGameWon();

    boolean isGameLost();

    String getMainMessage(User user);

    String getResultMessage();

    Game getGame();

    void checkGuess(int guess);

    void reset();
}

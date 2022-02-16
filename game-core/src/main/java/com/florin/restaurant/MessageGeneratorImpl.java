package com.florin.restaurant;

import com.florin.restaurant.config.CodeGeneratorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MessageGeneratorImpl implements MessageGenerator {


    private final Game game;
    private final int minNumber;
    private final CodeGenerator codeGenerator = new CodeGeneratorImpl();
    final String code = codeGenerator.generateRewardCode();

    @Autowired
    public MessageGeneratorImpl(Game game, @MinNumber int minNumber, CodeGenerator codeGenerator) {
        this.game = game;
        this.minNumber = minNumber;
    }

    // == init ==
    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }
    // == public methods ==


    @Override
    public String getMainMessage() {
        return "Number is between " +
                game.getSmallest() +
                " and " +
                game.getBiggest() +
                ". Can you guess it?";
    }

    @Override
    public String getResultMessage() {

        if (game.isGameWon()) {
            return "You guessed it! The number was " + game.getNumber() + "!"
                    + "\nHere is your reward code: " + code;
        } else if (game.isGameLost()) {
            return "You lost. The number was " + game.getNumber();
        } else if (!game.isValidNumberRange()) {
            return "Invalid number range!";
        } else if (game.getRemainingGuesses() == game.getGuessCount()) {
            return "What is your first guess?";
        } else {
            String direction = "Lower";

            if (game.getGuess() < game.getNumber()) {
                direction = "Higher";
            }

            return direction + "! You have " + game.getRemainingGuesses() + " guess left";
        }
    }
}

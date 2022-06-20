package com.florin.restaurant.config;



import com.florin.restaurant.game.GuessCount;
import com.florin.restaurant.game.MaxNumber;
import com.florin.restaurant.game.MinNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@ComponentScan(basePackages = "com.florin.restaurant")
@PropertySource("classpath:config/game.properties")
public class GameConfig {

    @Value("${game.maxNumber:20}0")
    private int maxNumber;

    @Value("${game.guessCount:5}")
    private int guessCount;

    @Value("${game.minNumber:5}")
    private int minNumber;

    @Bean
    @MaxNumber
    public int maxNumber() {
        return maxNumber;
    }

    @Bean
    @GuessCount
    public int guessCount() {
        return guessCount;
    }

    @Bean
    @MinNumber
    public int minNumber() {
        return minNumber;
    }
}

package gamestudio;

import gamestudio.game.consoleui.ConsoleUI;
import gamestudio.game.core.Boxes;
import gamestudio.game.core.Field;
//import gamestudio.entity.Comment;
import gamestudio.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "gamestudio.server.*"))

public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
       // SpringApplication.run(gamestudio.SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui, Field field, Boxes boxes){
        return args -> ui.Play(field,boxes);
    }

    @Bean
    public ConsoleUI consoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService){
        return new ConsoleUI(field,scoreService,ratingService,commentService);
    }

    @Bean
    public Boxes boxes(){
        return new Boxes();
    }

    @Bean
    public Field field(Boxes boxes){
        return new Field(boxes);
    }

    @Bean
    public ScoreService scoreService(){
       // return new ScoreServiceJPA();
        return new ScoreServiceRestCLient();
    }

    @Bean
    public CommentService commentService(){
       // return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
       // return new RatingServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

package org.example.audiobook;

import org.example.audiobook.entity.*;
import org.example.audiobook.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Instant;

@SpringBootApplication
public class AudioBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AudioBookRepository repository, BookPageRepository bookPageRepository, AudioBookRepository audioBookRepository, UserRepository userRepository, PreniumPlanRepository preniumPlanRepository, UserPreniumRepository userPreniumRepository, CategoryRepository categoryRepository, RatingRepository ratingRepository, FavoriteListRepository favoriteListRepository,
                           FavoriteListBookRepository favoriteListBookRepository) {
        return args -> {
            User user = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .hashedPassword("admin")
                    .build();

            userRepository.save(user);

            System.out.println(user.toString());

            PreniumPlan preniumPlan = PreniumPlan.builder()
                    .name("Prenium plan")
                    .price(BigDecimal.valueOf(1))
                    .durationDays(1)
                    .build();

            preniumPlanRepository.save(preniumPlan);

            System.out.println(preniumPlan.toString());

            UserPrenium userPrenium = UserPrenium.builder()
                    .user(user)
                    .preniumPlan(preniumPlan)
                    .endDate(Instant.now())
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();

            userPreniumRepository.save(userPrenium);

            System.out.println(userPrenium.toString());

            Category category = Category.builder()
                    .name("Category")
                    .build();

            categoryRepository.save(category);

            System.out.println(category.toString());

            AudioBook audioBook = AudioBook.builder()
                    .user(user)
                    .category(category)
                    .title("title")
                    .author("author")
                    .coverImage("coverImage")
                    .publishedYear(2016)
                    .isFree(false)
                    .maleAudioUrl("maleAudioUrl")
                    .femaleAudioUrl("femaleAudioUrl")
                    .duration(1)
                    .build();

            audioBookRepository.save(audioBook);

            System.out.println(audioBook.toString());

            Rating rating = Rating.builder()
                    .user(user)
                    .audioBook(audioBook)
                    .rating(1)
                    .comment("comment")
                    .build();

            ratingRepository.save(rating);

            System.out.println(rating.toString());

            FavoriteList favoriteList = FavoriteList.builder()
                    .user(user)
                    .name("favoriteList")
                    .build();

            favoriteListRepository.save(favoriteList);

            System.out.println(favoriteList.toString());

            FavoriteListBook favoriteListBook = FavoriteListBook.builder()
                    .favoriteList(favoriteList)
                    .audioBook(audioBook)
                    .build();

            favoriteListBookRepository.save(favoriteListBook);

            System.out.println(favoriteListBook.toString());

            BookPage bookPage = BookPage.builder()
                    .audioBook(audioBook)
                    .textContent("textContent")
                    .pageNumber(1)
                    .build();

            bookPageRepository.save(bookPage);

            System.out.println(bookPage.toString());

        };
    }
}

package com.example.jissenapi;

import com.example.jissenapi.model.Deal;
import com.example.jissenapi.model.Issue;
import com.example.jissenapi.model.Position;
import com.example.jissenapi.repository.DealRepository;
import com.example.jissenapi.repository.IssueRepository;
import com.example.jissenapi.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DealRepository dealRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Issueの宣言
            Issue issue1 = new Issue("item01", "債券1", 0.1, 2023, 12, 31, 1);
            Issue issue2 = new Issue("item02", "債券2", 0.0, 2023, 10, 01, 4);
            Issue issue3 = new Issue("item03", "債券3", 0.3, 2027, 12, 31, 2);
            Issue issue4 = new Issue("item04", "債券4", 0.5, 2029, 01, 31, 2);
            Issue issue5 = new Issue("item05", "債券5", 1.0, 2030, 12, 31, 1);

            // Positionの宣言
            Position position1 = new Position(issue1, 100, 200);
            Position position2 = new Position(issue2, 50, 300);
            Position position4 = new Position(issue4, 300, 200);

            // Dealの宣言
            Deal deal1 = new Deal(issue1, 100, 200);
            Deal deal2 = new Deal(issue2, 100, 200);
            Deal deal3 = new Deal(issue3, 100, 200);

            // Issueの保存
            log.info("Preloading " + issueRepository.save(issue1));
            log.info("Preloading " + issueRepository.save(issue2));
            log.info("Preloading " + issueRepository.save(issue3));
            log.info("Preloading " + issueRepository.save(issue4));
            log.info("Preloading " + issueRepository.save(issue5));

            // Positionの保存
            log.info("Preloading " + positionRepository.save(position1));
            log.info("Preloading " + positionRepository.save(position2));
            log.info("Preloading " + positionRepository.save(position4));

            // Dealの保存
            log.info("Preloading " + dealRepository.save(deal1));
            log.info("Preloading " + dealRepository.save(deal2));
            log.info("Preloading " + dealRepository.save(deal3));
        };
    }

}

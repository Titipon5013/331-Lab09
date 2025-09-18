package se331.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import se331.lab.model.AuctionItem;
import se331.lab.model.Bid;
import se331.lab.repository.AuctionItemRepository;

import java.time.Instant;
import java.util.List;

@Configuration
public class InitApp {
    @Bean
    CommandLineRunner initData(AuctionItemRepository repo) {
        return args -> {
            repo.clear();

            AuctionItem i1 = new AuctionItem(null, "Vintage camera with leather case");
            i1.setBids(List.of(
                    new Bid(1L, "Alice", 50.0, Instant.now().minusSeconds(3600)),
                    new Bid(2L, "Bob", 65.0, Instant.now().minusSeconds(3200)),
                    new Bid(3L, "Charlie", 80.0, Instant.now().minusSeconds(3000))
            ));
            i1.setSuccessfulBid(i1.getBids().get(2));

            AuctionItem i2 = new AuctionItem(null, "Antique wooden chair");
            i2.setBids(List.of(
                    new Bid(4L, "Dave", 30.0, Instant.now().minusSeconds(4000)),
                    new Bid(5L, "Eve", 45.0, Instant.now().minusSeconds(3500)),
                    new Bid(6L, "Frank", 55.0, Instant.now().minusSeconds(3300))
            ));
            // no successful bid yet

            AuctionItem i3 = new AuctionItem(null, "Rare vinyl record: The Classics");
            i3.setBids(List.of(
                    new Bid(7L, "Grace", 100.0, Instant.now().minusSeconds(5000)),
                    new Bid(8L, "Heidi", 120.0, Instant.now().minusSeconds(4800)),
                    new Bid(9L, "Ivan", 135.0, Instant.now().minusSeconds(4700))
            ));
            i3.setSuccessfulBid(i3.getBids().get(1));

            AuctionItem i4 = new AuctionItem(null, "Gaming console bundle");
            i4.setBids(List.of(
                    new Bid(10L, "Judy", 200.0, Instant.now().minusSeconds(2500)),
                    new Bid(11L, "Karl", 220.0, Instant.now().minusSeconds(2400)),
                    new Bid(12L, "Leo", 250.0, Instant.now().minusSeconds(2300))
            ));
            // no successful bid yet

            AuctionItem i5 = new AuctionItem(null, "Mountain bike - lightly used");
            i5.setBids(List.of(
                    new Bid(13L, "Mallory", 300.0, Instant.now().minusSeconds(2000)),
                    new Bid(14L, "Niaj", 340.0, Instant.now().minusSeconds(1800)),
                    new Bid(15L, "Olivia", 360.0, Instant.now().minusSeconds(1700))
            ));
            i5.setSuccessfulBid(i5.getBids().get(2));

            repo.save(i1);
            repo.save(i2);
            repo.save(i3);
            repo.save(i4);
            repo.save(i5);
        };
    }
}


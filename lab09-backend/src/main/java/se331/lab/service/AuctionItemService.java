package se331.lab.service;

import se331.lab.model.AuctionItem;

import java.util.List;

public interface AuctionItemService {
    List<AuctionItem> getAll();
    List<AuctionItem> searchByDescription(String term);
    List<AuctionItem> findBySuccessfulBidLessThan(double amount);
}

package se331.lab.service;

import org.springframework.stereotype.Service;
import se331.lab.model.AuctionItem;
import se331.lab.repository.AuctionItemRepository;

import java.util.List;

@Service
public class AuctionItemServiceImpl implements AuctionItemService {
    private final AuctionItemRepository repository;

    public AuctionItemServiceImpl(AuctionItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AuctionItem> getAll() {
        return repository.findAll();
    }

    @Override
    public List<AuctionItem> searchByDescription(String term) {
        return repository.findByDescriptionContainsIgnoreCase(term);
    }

    @Override
    public List<AuctionItem> findBySuccessfulBidLessThan(double amount) {
        return repository.findBySuccessfulBidAmountLessThan(amount);
    }
}

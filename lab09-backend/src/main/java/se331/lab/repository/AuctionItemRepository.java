package se331.lab.repository;

import org.springframework.stereotype.Repository;
import se331.lab.model.AuctionItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AuctionItemRepository {
    private final Map<Long, AuctionItem> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<AuctionItem> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<AuctionItem> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<AuctionItem> findByDescriptionContainsIgnoreCase(String term) {
        if (term == null || term.isBlank()) return findAll();
        String t = term.toLowerCase(Locale.ROOT);
        return store.values().stream()
                .filter(i -> i.getDescription() != null && i.getDescription().toLowerCase(Locale.ROOT).contains(t))
                .collect(Collectors.toList());
    }

    public List<AuctionItem> findBySuccessfulBidAmountLessThan(double amount) {
        return store.values().stream()
                .filter(i -> i.getSuccessfulBid() != null && i.getSuccessfulBid().getAmount() != null
                        && i.getSuccessfulBid().getAmount() < amount)
                .collect(Collectors.toList());
    }

    public AuctionItem save(AuctionItem item) {
        if (item.getId() == null) {
            item.setId(idGen.getAndIncrement());
        }
        store.put(item.getId(), item);
        return item;
    }

    public void saveAll(Collection<AuctionItem> items) {
        items.forEach(this::save);
    }

    public void clear() {
        store.clear();
    }
}

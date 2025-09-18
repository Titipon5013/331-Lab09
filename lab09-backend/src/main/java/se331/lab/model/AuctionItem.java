package se331.lab.model;

import java.util.ArrayList;
import java.util.List;

public class AuctionItem {
    private Long id;
    private String description;
    private List<Bid> bids = new ArrayList<>();
    private Bid successfulBid; // nullable

    public AuctionItem() {}

    public AuctionItem(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Bid> getBids() { return bids; }
    public void setBids(List<Bid> bids) { this.bids = bids; }

    public Bid getSuccessfulBid() { return successfulBid; }
    public void setSuccessfulBid(Bid successfulBid) { this.successfulBid = successfulBid; }
}

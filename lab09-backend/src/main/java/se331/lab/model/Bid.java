package se331.lab.model;

import java.time.Instant;

public class Bid {
    private Long id;
    private String bidder;
    private Double amount;
    private Instant time;

    public Bid() {}

    public Bid(Long id, String bidder, Double amount, Instant time) {
        this.id = id;
        this.bidder = bidder;
        this.amount = amount;
        this.time = time;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBidder() { return bidder; }
    public void setBidder(String bidder) { this.bidder = bidder; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Instant getTime() { return time; }
    public void setTime(Instant time) { this.time = time; }
}

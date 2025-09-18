package se331.lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se331.lab.model.AuctionItem;
import se331.lab.service.AuctionItemService;

import java.util.List;

@RestController
@RequestMapping("/api/auction-items")
public class AuctionItemController {
    private final AuctionItemService service;

    public AuctionItemController(AuctionItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuctionItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<AuctionItem> searchByDescription(@RequestParam(name = "description") String description) {
        return service.searchByDescription(description);
    }

    @GetMapping("/successful-bid/less-than")
    public List<AuctionItem> successfulBidLessThan(@RequestParam(name = "value") double value) {
        return service.findBySuccessfulBidLessThan(value);
    }
}

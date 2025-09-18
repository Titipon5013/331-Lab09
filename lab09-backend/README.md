# Lab09 Backend

Simple Spring Boot (in-memory) Auction API using MVC.

## Run
Ensure JDK 17+ is installed and on PATH.

Windows (Maven Wrapper):
```bat
mvnw.cmd spring-boot:run
```

macOS/Linux:
```bash
./mvnw spring-boot:run
```

The app starts on http://localhost:8080

## Seed Data
On startup, the app seeds 5 AuctionItems. Each has 3 bids; 3 items have successful bids.

## Endpoints
- List all items:
  - GET /api/auction-items
- Search by description (contains, case-insensitive):
  - GET /api/auction-items/search?description=chair
- Items whose successfulBid amount is less than value:
  - GET /api/auction-items/successful-bid/less-than?value=200

## Examples
```bash
curl http://localhost:8080/api/auction-items | jq
curl "http://localhost:8080/api/auction-items/search?description=vinyl" | jq
curl "http://localhost:8080/api/auction-items/successful-bid/less-than?value=200" | jq
```

## Notes
- Uses in-memory repository; data resets on each run.


export type Bid = {
  id: number;
  bidder: string;
  amount: number;
  time: string; // ISO string
};

export type AuctionItem = {
  id: number;
  description: string;
  bids: Bid[];
  successfulBid: Bid | null;
};

const BASE_URL = 'http://localhost:8080/api/auction-items';

async function fetchJson<T>(url: string): Promise<T> {
  const res = await fetch(url);
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`Request failed ${res.status}: ${text || res.statusText}`);
  }
  return res.json() as Promise<T>;
}

export async function listAll(): Promise<AuctionItem[]> {
  return fetchJson<AuctionItem[]>(BASE_URL);
}

export async function searchByDescription(description: string): Promise<AuctionItem[]> {
  const url = `${BASE_URL}/search?description=${encodeURIComponent(description)}`;
  return fetchJson<AuctionItem[]>(url);
}

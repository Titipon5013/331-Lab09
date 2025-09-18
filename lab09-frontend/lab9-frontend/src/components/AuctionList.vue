<script setup lang="ts">
import type { AuctionItem } from '../services/auctionApi'

const props = defineProps<{
  items: AuctionItem[]
  loading?: boolean
  error?: string | null
}>()

function typeFromDescription(desc?: string) {
  const d = (desc || '').toLowerCase()
  if (d.includes('camera')) return { icon: '📷', label: 'Camera' }
  if (d.includes('chair')) return { icon: '🪑', label: 'Chair' }
  if (d.includes('record') || d.includes('vinyl') || d.includes('classics')) return { icon: '💿', label: 'Vinyl' }
  if (d.includes('console') || d.includes('gaming')) return { icon: '🎮', label: 'Console' }
  if (d.includes('bike')) return { icon: '🚲', label: 'Bike' }
  return { icon: '🔨', label: 'Auction' }
}

function amountFmt(n?: number) {
  return typeof n === 'number' ? n.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 }) : '-'
}
</script>

<template>
  <div class="auction-list">
    <div v-if="loading">Loading items...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <p v-if="!props.items || props.items.length === 0">No auction items found.</p>
      <ul v-else class="grid">
        <li v-for="item in props.items" :key="item.id" class="card">
          <div class="thumb" :aria-label="typeFromDescription(item.description).label">
            <span class="emoji">{{ typeFromDescription(item.description).icon }}</span>
          </div>
          <div class="body">
            <div class="top">
              <span class="badge">#{{ item.id }}</span>
              <span class="type">{{ typeFromDescription(item.description).label }}</span>
            </div>
            <h3 class="title">{{ item.description }}</h3>
            <div class="meta">
              <span class="bids">Bids: {{ item.bids?.length || 0 }}</span>
              <span v-if="item.successfulBid" class="price">
                Top bid: {{ amountFmt(item.successfulBid.amount) }}
                <span class="muted">by {{ item.successfulBid.bidder }}</span>
              </span>
              <span v-else class="muted">No successful bid yet</span>
            </div>
            <details class="bids">
              <summary>View bids</summary>
              <table v-if="item.bids && item.bids.length">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Bidder</th>
                    <th>Amount</th>
                    <th>Time</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="b in item.bids" :key="b.id">
                    <td>{{ b.id }}</td>
                    <td>{{ b.bidder }}</td>
                    <td>{{ amountFmt(b.amount) }}</td>
                    <td>{{ new Date(b.time).toLocaleString() }}</td>
                  </tr>
                </tbody>
              </table>
            </details>
          </div>
        </li>
      </ul>
    </div>
  </div>
  
</template>

<style scoped>
.auction-list { display: block; }
.grid { list-style: none; padding: 0; margin: 0; display: grid; gap: 16px; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); }
.card { display: grid; grid-template-columns: 88px 1fr; gap: 12px; border: 1px solid #e5e7eb; border-radius: 12px; padding: 12px; background: #fff; box-shadow: 0 1px 2px rgba(0,0,0,0.04); }
.thumb { display: grid; place-items: center; background: #f8fafc; border-radius: 10px; border: 1px solid #eef2f7; }
.emoji { font-size: 36px; }
.body { display: flex; flex-direction: column; gap: 6px; }
.top { display: flex; gap: 8px; align-items: center; font-size: 13px; }
.badge { background: #eef2ff; color: #3730a3; padding: 2px 8px; border-radius: 999px; border: 1px solid #c7d2fe; }
.type { color: #64748b; }
.title { margin: 0; font-size: 16px; color: #345380; }
.meta { display: flex; gap: 10px; flex-wrap: wrap; align-items: baseline; }
.price { font-weight: 600; color: #3b82f6; }
.muted { color: #9ca3af; font-weight: 400; }
table { width: 100%; border-collapse: collapse; margin-top: 8px; }
th, td { text-align: left; padding: 6px 8px; border-bottom: 1px solid #f1f5f9; font-size: 13px; }
summary { cursor: pointer; color: #64748b; }
.meta .bids { color: #6b7280; }
th { color: #94a3b8; font-weight: 600; }
td { color: #6b7280; }
@media (prefers-color-scheme: dark) {
  .card { background: #0b1220; border-color: #1f2937; }
  .thumb { background: #0f172a; border-color: #111827; }
  .title { color: #e5e7eb; }
  .type { color: #cbd5e1; }
  .muted { color: #9ca3af; }
  .price { color: #93c5fd; }
  summary { color: #cbd5e1; }
  .meta .bids { color: #cbd5e1; }
  th { color: #cbd5e1; }
  td { color: #e5e7eb; }
}
</style>

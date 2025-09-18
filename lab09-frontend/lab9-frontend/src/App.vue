<script setup lang="ts">
import { ref, watchEffect, computed } from 'vue'
import AuctionList from './components/AuctionList.vue'
import { listAll, searchByDescription, type AuctionItem } from './services/auctionApi'

const description = ref('')
const typeFilter = ref('') // not present in backend; treated as tag present in description
const loading = ref(false)
const error = ref<string | null>(null)
const items = ref<AuctionItem[]>([])
const sortKey = ref<'relevance' | 'bids' | 'topbid' | 'newest'>('relevance')

const quickChips = [
  { label: 'Camera', q: 'camera' },
  { label: 'Chair', q: 'chair' },
  { label: 'Vinyl', q: 'vinyl' },
  { label: 'Console', q: 'console' },
  { label: 'Bike', q: 'bike' },
]

async function load() {
  loading.value = true
  error.value = null
  try {
    if (description.value.trim().length > 0) {
      items.value = await searchByDescription(description.value.trim())
    } else {
      items.value = await listAll()
    }
  } catch (e: any) {
    error.value = e?.message ?? 'Failed to load items'
  } finally {
    loading.value = false
  }
}

// Fetch on mount and whenever description changes (debounce via button optional)
watchEffect(() => {
  // trigger load when description changes; could be debounced in real app
  void load()
})

const filteredItems = computed(() => {
  const t = typeFilter.value.trim().toLowerCase()
  let arr = !t ? items.value : items.value.filter(i => (i.description || '').toLowerCase().includes(t))
  // sort
  if (sortKey.value === 'bids') {
    arr = [...arr].sort((a,b) => (b.bids?.length||0) - (a.bids?.length||0))
  } else if (sortKey.value === 'topbid') {
    const top = (x: AuctionItem) => x.successfulBid?.amount ?? 0
    arr = [...arr].sort((a,b) => (top(b) - top(a)))
  } else if (sortKey.value === 'newest') {
    const ts = (x: AuctionItem) => {
      const len = x.bids?.length ?? 0
      if (len === 0) return 0
      const last = x.bids && x.bids[len - 1]
      return last ? new Date(last.time).getTime() : 0
    }
    arr = [...arr].sort((a,b) => ts(b) - ts(a))
  }
  return arr
})

function onSubmit(e: Event) {
  e.preventDefault()
  void load()
}
</script>

<template>
  <main class="container">
    <h1>Auction Items</h1>

    <form class="filters" @submit="onSubmit">
      <div class="field">
        <label>Description</label>
        <input v-model.trim="description" type="text" placeholder="Search description..." />
      </div>
      <div class="field">
        <label>Type</label>
        <input v-model.trim="typeFilter" type="text" placeholder="e.g. camera, chair, bike" />
      </div>
      <div class="field">
        <label>Sort by</label>
        <select v-model="sortKey">
          <option value="relevance">Relevance</option>
          <option value="bids">Most bids</option>
          <option value="topbid">Highest top bid</option>
          <option value="newest">Newest bid</option>
        </select>
      </div>
      <div class="actions">
        <button type="submit">Search</button>
        <button type="button" class="ghost" @click="description=''; typeFilter=''; sortKey='relevance'; load()">Reset</button>
      </div>
    </form>

    <div class="chips">
      <button class="chip" v-for="c in quickChips" :key="c.q" @click="typeFilter=c.q">{{ c.label }}</button>
    </div>

    <AuctionList :items="filteredItems" :loading="loading" :error="error" />
  </main>
</template>

<style scoped>
.container { max-width: 1040px; margin: 0 auto; padding: 24px; }
.filters { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 12px; align-items: end; margin-bottom: 8px; }
.field { display: flex; flex-direction: column; gap: 6px; }
label { font-weight: 600; }
input, select { padding: 8px 10px; border: 1px solid #cbd5e1; border-radius: 6px; min-width: 220px; background: var(--ui-input-bg, #fff); color: inherit; }
.actions { display: flex; gap: 8px; align-items: end; }
button { padding: 8px 12px; border: 1px solid #1f6feb; background: #2f81f7; color: white; border-radius: 6px; cursor: pointer; }
button.ghost { background: transparent; color: #1f6feb; }
h1 { margin: 0 0 16px; }
.chips { display: flex; gap: 8px; flex-wrap: wrap; margin: 8px 0 16px; }
.chip { border: 1px solid #e2e8f0; background: #f8fafc; color: #0f172a; border-radius: 999px; padding: 6px 10px; }
@media (prefers-color-scheme: dark) {
  :host { --ui-input-bg: #0b1220; }
  .chip { background: #0b1220; color: #e2e8f0; border-color: #1f2937; }
}
</style>

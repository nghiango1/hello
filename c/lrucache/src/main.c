#include <stdio.h>
#include <stdlib.h>

typedef struct Node {
  int key;
  int value;
  struct Node *prev;
  struct Node *next;
} Node;

typedef struct LRUCache {
  int capacity;
  int count;
  Node *head;
  Node *tail;
  Node **hashTable;
} LRUCache;

LRUCache *createCache(int capacity) {
  LRUCache *cache = (LRUCache *)malloc(sizeof(LRUCache));
  cache->capacity = capacity;
  cache->count = 0;
  cache->head = NULL;
  cache->tail = NULL;
  cache->hashTable = (Node **)calloc(capacity, sizeof(Node *));
  return cache;
}

Node *createNode(int key, int value) {
  Node *newNode = (Node *)malloc(sizeof(Node));
  newNode->key = key;
  newNode->value = value;
  newNode->prev = NULL;
  newNode->next = NULL;
  return newNode;
}

void deleteNode(LRUCache *cache, Node *node) {
  if (node->prev) {
    node->prev->next = node->next;
  } else {
    cache->head = node->next;
  }
  if (node->next) {
    node->next->prev = node->prev;
  } else {
    cache->tail = node->prev;
  }
  free(node);
}

void moveToHead(LRUCache *cache, Node *node) {
  if (node != cache->head) {
    if (node == cache->tail) {
      cache->tail = node->prev;
    }
    if (node->prev) {
      node->prev->next = node->next;
    }
    if (node->next) {
      node->next->prev = node->prev;
    }
    node->prev = NULL;
    node->next = cache->head;
    cache->head->prev = node;
    cache->head = node;
  }
}

int get(LRUCache *cache, int key) {
  if (cache->hashTable[key % cache->capacity] != NULL) {
    Node *node = cache->hashTable[key % cache->capacity];
    while (node) {
      if (node->key == key) {
        moveToHead(cache, node);
        return node->value;
      }
      node = node->next;
    }
  }
  return -1;
}

void put(LRUCache *cache, int key, int value) {
  if (cache->hashTable[key % cache->capacity] != NULL) {
    Node *node = cache->hashTable[key % cache->capacity];
    while (node) {
      if (node->key == key) {
        node->value = value;
        moveToHead(cache, node);
        return;
      }
      node = node->next;
    }
  }
  Node *newNode = createNode(key, value);
  if (cache->count < cache->capacity) {
    cache->count++;
  } else {
    Node *tailPrev = cache->tail->prev;
    deleteNode(cache, cache->tail);
    cache->tail = tailPrev;
  }
  if (cache->head) {
    newNode->next = cache->head;
    cache->head->prev = newNode;
  }
  cache->head = newNode;
  if (!cache->tail) {
    cache->tail = newNode;
  }
  cache->hashTable[key % cache->capacity] = newNode;
}

void freeCache(LRUCache *cache) {
  Node *node = cache->head;
  while (node) {
    Node *next = node->next;
    free(node);
    node = next;
  }
  free(cache->hashTable);
  free(cache);
}

int main() {
  LRUCache *cache = createCache(2);
  put(cache, 1, 1);
  put(cache, 2, 2);
  printf("%d\n", get(cache, 1)); // Output: 1
  put(cache, 3, 3);
  printf("%d\n", get(cache, 2)); // Output: -1
  put(cache, 4, 4);
  printf("%d\n", get(cache, 1)); // Output: -1
  printf("%d\n", get(cache, 3)); // Output: 3
  printf("%d\n", get(cache, 4)); // Output: 4
  freeCache(cache);
  return 0;
}

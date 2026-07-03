package com.home.zabara.playground.dsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task32TrieTest {

    @Test
    void searchOnlyMatchesExactlyInsertedWords() {
        Task32Trie trie = new Task32Trie();
        trie.insert("apple");

        assertTrue(trie.search("apple"));
        assertFalse(trie.search("app"));
        assertTrue(trie.startsWith("app"));
        assertFalse(trie.startsWith("apx"));

        trie.insert("app");
        assertTrue(trie.search("app"));
    }

    @Test
    void unrelatedPrefixesDoNotMatch() {
        Task32Trie trie = new Task32Trie();
        trie.insert("cat");
        trie.insert("car");

        assertFalse(trie.search("ca"));
        assertTrue(trie.startsWith("ca"));
        assertFalse(trie.startsWith("dog"));
        assertTrue(trie.search("cat"));
        assertTrue(trie.search("car"));
    }
}

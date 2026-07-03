package com.home.zabara.playground.dsa;

import java.util.HashMap;
import java.util.Map;

/**
 * Task E12 — Trie (prefix tree).
 *
 * Each Task32Trie instance is itself a node; `children` maps the next
 * character to the child node for that character, and `endOfWord` marks
 * whether a complete word ends at this node — you're free to restructure
 * this if you prefer a different representation.
 *
 * TODO, all in O(length of the word/prefix) time:
 *   - insert(word): add a word to the trie.
 *   - search(word): true iff `word` was previously inserted EXACTLY (not
 *     just as a prefix of something else).
 *   - startsWith(prefix): true iff any inserted word starts with `prefix`.
 */
public class Task32Trie {

    private final Map<Character, Task32Trie> children = new HashMap<>();
    private boolean endOfWord;

    public void insert(String word) {
        throw new UnsupportedOperationException("TODO: walk/create a child node per character, mark endOfWord on the last one");
    }

    public boolean search(String word) {
        throw new UnsupportedOperationException("TODO: walk the trie by character; true only if the final node has endOfWord set");
    }

    public boolean startsWith(String prefix) {
        throw new UnsupportedOperationException("TODO: walk the trie by character; true if every character has a matching child, regardless of endOfWord");
    }
}

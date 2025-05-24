package job.prod.entity;

import java.util.Set;

public class ComparisonResult {
    private int score;
    private Set<String> missedKeywords;
    private Set<String> matchedKeywords;

    public ComparisonResult(int score, Set<String> missedKeywords, Set<String> matchedKeywords) {
        this.score = score;
        this.missedKeywords = missedKeywords;
        this.matchedKeywords = matchedKeywords;
    }

    public Set<String> getMatchedWords() {
        return matchedKeywords;
    }

    public void setMatchedWords(Set<String> matchedKeywords) {
        this.matchedKeywords = matchedKeywords;
    }

    public int getScore() {
        return score;
    }

    public Set<String> getMissedKeywords() {
        return missedKeywords;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMissedKeywords(Set<String> missedKeywords) {
        this.missedKeywords = missedKeywords;
    }
}

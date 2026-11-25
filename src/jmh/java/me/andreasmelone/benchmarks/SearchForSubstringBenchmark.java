package me.andreasmelone.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(Scope.Thread)
public class SearchForSubstringBenchmark {
//    private static final String SHORTER_STRING = "1.2.x";
//    private static final String LONGER_STRING = ">=1.2.0 LOREM_IPSUM_DOLOR_SIT_AMET SOMETHING SOMETHING SOMETHING " +
//            "SADDDDDDDDDDDDDDDOIJAOIJASDSAOJDOISAJDOOAIJSDOAOIJDIJSAOIJDS.xASUDHADJAOJDAODIJASDASDAISDAJ";
//    private static final Pattern PATTERN = Pattern.compile("\\.x");
//    private static final String NEEDLE = ".x";

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890АБВГДЕЁЖЗИЙКЛМОНПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    @Param({"4", "8", "12", "16", "24", "32", "64", "128"})
    int haystackLength;

    @Param({"1", "2", "4", "8"})
    int needleLength;

    String text;
    String needle;
    Pattern needlePattern;

    @Setup
    public void setup() {
        if (needleLength > haystackLength) {
            throw new IllegalArgumentException(
                    "Needle cannot exceed haystack length"
            );
        }

        needle = randomString(needleLength);
        text = randomString(haystackLength - needleLength) + needle;
        needlePattern = Pattern.compile(needle);
    }

    @Benchmark
    public int findUsingRegex() {
        Matcher match = needlePattern.matcher(text);
        if(match.find()) {
            return match.start();
        }
        return -1;
    }

    @Benchmark
    public int findManually() {
        int length = text.length() - needle.length() + 1;
        for (int i = 0; i < length; i++) {
            boolean matches = true;
            for (int j = 0; j < needle.length(); j++) {
                if(text.charAt(i + j) != needle.charAt(j)) {
                    matches = false;
                    break;
                }
            }
            if(matches) return i;
        }
        return -1;
    }
    @Benchmark
    public int findInShorterIndexOf() {
        return text.indexOf(needle);
    }
}

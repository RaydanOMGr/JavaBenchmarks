package me.andreasmelone.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;

public class StringSplitCollectionBenchmarks {
    public static final char DELIMITER = ',';
    public static final String DELIMITER_STRING = ",";
    public static final String STRING_CONSTANT = "HELLO WORLD,THIS IS A LIST,OF SENTENCES,please,beward,there,aremul,tiple";

    public static int STARTING_CAPACITY = 4;

    @Benchmark
    public String[] splitUsingRegex() {
        return STRING_CONSTANT.split(DELIMITER_STRING);
    }

    @Benchmark
    public String[] splitUsingDoubleSubstring() {
        String[] outputStrings = new String[STARTING_CAPACITY];

        int count = 0;

        int currentIndex;
        String str = STRING_CONSTANT;
        while(!str.isEmpty()) {
            currentIndex = str.indexOf(DELIMITER);
            if(currentIndex < 0) currentIndex = str.length();

            if(count >= outputStrings.length) {
                outputStrings = Arrays.copyOf(outputStrings, outputStrings.length * 2);
            }
            outputStrings[count] = str.substring(0, currentIndex);
            str = str.substring(Math.min(currentIndex + 1, str.length()));
            count++;
        }
        if(count == outputStrings.length) return outputStrings;
        return Arrays.copyOf(outputStrings, count);
    }

    @Benchmark
    public String[] splitUsingIndexedSubstring() {
        String[] outputStrings = new String[STARTING_CAPACITY];

        int count = 0;
        int start = 0;
        String str = STRING_CONSTANT;
        while(start < str.length()) {
            int end = str.indexOf(DELIMITER, start);
            if(end < 0) end = str.length();

            if(count >= outputStrings.length) {
                outputStrings = Arrays.copyOf(outputStrings, outputStrings.length * 2);
            }
            outputStrings[count] = str.substring(start, end);
            start = end + 1;
            count++;
        }
        if(count == outputStrings.length) return outputStrings;
        return Arrays.copyOf(outputStrings, count);
    }
}

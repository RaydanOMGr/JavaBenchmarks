package me.andreasmelone.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.io.*;

public class StringSplittingBenchmarks {
    public static final char DELIMITER = ',';
    public static final String DELIMITER_STRING = ",";
    public static final String STRING_CONSTANT = "HELLO WORLD,THIS IS A LIST,OF SENTENCES,please,beward,there,aremul,tiple";

    @Benchmark
    public void splitUsingRegex(Blackhole bh) {
        for (String s : STRING_CONSTANT.split(DELIMITER_STRING)) {
            bh.consume(s);
        }
    }

    @Benchmark
    public void splitUsingDoubleSubstring(Blackhole bh) {
        int currentIndex;
        String str = STRING_CONSTANT;
        while(!str.isEmpty()) {
            currentIndex = str.indexOf(DELIMITER);
            if(currentIndex < 0) currentIndex = str.length();

            bh.consume(str.substring(0, currentIndex));
            str = str.substring(Math.min(currentIndex + 1, str.length()));
        }
    }

    @Benchmark
    public void splitUsingIndexedSubstring(Blackhole bh) {
        int start = 0;
        String str = STRING_CONSTANT;
        while(start < str.length()) {
            int end = str.indexOf(DELIMITER, start);
            if(end < 0) end = str.length();

            bh.consume(str.substring(start, end));
            start = end + 1;
        }
    }
}

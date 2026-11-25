package me.andreasmelone.benchmarks;


import org.openjdk.jmh.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

@State(Scope.Thread)
public class StringifyBenchmark {
    Object a;
    Object b;

    @Setup(Level.Trial)
    public void setup() throws MalformedURLException {
        a = new Object() {
            @Override
            public String toString() {
                int i = 1000;
                while(i > 0) {
                    --i;
                }
                return "This object has a very very stupid to string method!!!";
            }
        };
        b = new URL("file://something");
    }

    @Benchmark
    public String concatWithEmpty() {
        return "" + a + b;
    }

    @Benchmark
    public String concatUsingStringBuilder() {
        return new StringBuilder()
                .append(a)
                .append(b)
                .toString();
    }

    @Benchmark
    public String concatValueOf() {
        return String.valueOf(a) + b;
    }

    @Benchmark
    public String concatToString() {
        return a.toString() + b;
    }
}

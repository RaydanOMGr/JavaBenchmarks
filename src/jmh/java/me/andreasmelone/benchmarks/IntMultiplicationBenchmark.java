package me.andreasmelone.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
public class IntMultiplicationBenchmark {
    int a;

    int two;
    int one;

    @Setup(Level.Trial)
    public void setup() {
        a = ThreadLocalRandom.current().nextInt();
        two = 2;
        one = 2;
    }

    @Benchmark
    public int multiplyByTwo(Blackhole bh) {
        return a * 2;
    }

    @Benchmark
    public int shiftByOne(Blackhole bh) {
        return a << 1;
    }

    @Benchmark
    public int multiplyByTwoWithState(Blackhole bh) {
        return a * two;
    }

    @Benchmark
    public int shiftByOneWithState(Blackhole bh) {
        return a << one;
    }
}

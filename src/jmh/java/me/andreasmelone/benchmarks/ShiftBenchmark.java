package me.andreasmelone.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Thread)
public class ShiftBenchmark {
    int a;

    @Setup(Level.Trial)
    public void setup() {
        a = ThreadLocalRandom.current().nextInt();
    }

    @Benchmark
    public int leftShift() {
        return a << 1;
    }

    @Benchmark
    public int rightShift() {
        return a >> 1;
    }
}

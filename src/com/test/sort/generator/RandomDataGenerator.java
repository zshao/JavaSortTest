package com.test.sort.generator;

import java.nio.IntBuffer;
import java.util.Random;

public class RandomDataGenerator implements DataGenerator {
  
  @Override
  public void generate(IntBuffer ib, int N) {
    // Generate numbers
    Random r = new Random(1);
    for (int i = 0; i < N; i++) {
      ib.put(i, r.nextInt(N));
    }
  }
  
  public String toString() {
    return "random";
  }
}

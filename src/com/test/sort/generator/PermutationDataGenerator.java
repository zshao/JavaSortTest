package com.test.sort.generator;

import java.nio.IntBuffer;
import java.util.Random;

public class PermutationDataGenerator implements DataGenerator {
  
  @Override
  public void generate(IntBuffer ib, int N) {
    // Generate numbers
    for (int i = 0; i < N; i++) {
      ib.put(i, i);
    }
    
    // Randomize the order
    Random r = new Random(1);
    for (int i = 0; i < N; i++) {
      int j = i + r.nextInt(N - i);
      // swap ib[i], ib[j]
      int v = ib.get(i);
      ib.put(i, ib.get(j));
      ib.put(j, v);
    }
  }

  public String toString() {
    return "permutation";
  }
}

package com.test.sort.generator;

import java.nio.IntBuffer;

public interface DataGenerator {
  void generate(IntBuffer ib, int size);
}

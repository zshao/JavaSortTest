package com.test.sort.sorter;

import java.nio.IntBuffer;

public interface Sorter {
  
  /**
   * Both left and right are included.
   */
  void sort(IntBuffer ib, int left, int right);
}

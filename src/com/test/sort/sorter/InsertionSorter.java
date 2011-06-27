package com.test.sort.sorter;

import java.nio.IntBuffer;


/**
 * Basic Quick Sort algorithm.
 */
public class InsertionSorter implements Sorter {
  
  @Override
  public void sort(IntBuffer ib, int left, int right) {
    for (int i = left; i <= right; i++) {
      int minv = ib.get(i);
      int mini = i;
      for (int j = i + 1; j <= right; j++) {
        if (ib.get(j) < minv) {
          minv = ib.get(j);
          mini = j;
        }
      }
      if (mini != i) {
        ib.put(mini, ib.get(i));
        ib.put(i, minv);
      }
    }
  }
  
  public String toString() {
    return "insertion";
  }
}

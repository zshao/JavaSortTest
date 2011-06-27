package com.test.sort.sorter;

import java.nio.IntBuffer;


/**
 * Basic Quick Sort algorithm.
 */
public class QuickSorterWithInsertion implements Sorter {
  
  final Sorter sorter = new InsertionSorter();
  final int LIMIT;
  
  public QuickSorterWithInsertion() {
    this(12);
  }
  public QuickSorterWithInsertion(int limit) {
    LIMIT = limit;
  }
  
  @Override
  public void sort(IntBuffer ib, int left, int right) {
    if (right - left < LIMIT) {
      sorter.sort(ib, left, right);
      return;
    }
    int pivot = ib.get( (left + right) >> 1);
    int i = left;
    int j = right;
    while (i <= j) {
      while (ib.get(i) < pivot) i++;
      while (pivot < ib.get(j)) j--;
      if (i <= j) {
        int swap = ib.get(j);
        ib.put(j, ib.get(i));
        ib.put(i, swap);
        i++;
        j--;
      }
    }
    if (left < j) sort(ib, left, j);
    if (i < right) sort(ib, i, right);
  }
  
  public String toString() {
    return "quicksort_with_insertion " + LIMIT;
  }
}

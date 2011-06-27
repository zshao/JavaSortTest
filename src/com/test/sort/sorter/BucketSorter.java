package com.test.sort.sorter;

import java.nio.IntBuffer;
import java.util.Arrays;


/**
 * Basic Bucket Sorter algorithm.
 */
public class BucketSorter implements Sorter {
  
  QuickSorterWithInsertion sorter = new QuickSorterWithInsertion();
  
  final int BUCKET_BITS;
  final int BUCKETS;
  final int TOTAL_BITS;
  
  BucketSorter() {
    this(Byte.SIZE, Integer.SIZE);
  }
  
  public String toString() {
    return "Bucket sort (bucket_bits=" + BUCKET_BITS + " total_bits=" + TOTAL_BITS + ")";
  }
  
  public BucketSorter(int BucketBits, int TotalBits) {
    BUCKET_BITS = BucketBits;
    BUCKETS = (1 << BUCKET_BITS);
    TOTAL_BITS = TotalBits;
  }

  int getBucket(int v) {
    return v >> (TOTAL_BITS - BUCKET_BITS);
  }
  
  @Override
  public void sort(IntBuffer ib, int left, int right) {
    // Bucketize to BUCKETS, and then call quick sorter
    
    long start = System.currentTimeMillis();
    
    int[] counts = new int[BUCKETS];
    for (int i = left; i <= right; i++) {
        counts[ getBucket(ib.get(i)) ] ++;
    }
    
    int[] limits = new int[BUCKETS];
    int[] pos = new int[BUCKETS];
    for (int i = 0; i < BUCKETS; i++) {
      pos[i] = (i > 0 ? limits[i-1] : left);
      limits[i] = counts[i] + pos[i]; 
    }
    int[] startPos = Arrays.copyOf(pos, BUCKETS);

    // put each element into bucket
    for (int b = 0; b < BUCKETS; b++) {
      while (pos[b] < limits[b]) {
        int v = ib.get(pos[b]);
        int newB = getBucket(v);
        if (newB == b) {
          pos[b] ++;
          continue;
        }
        int newV = ib.get(pos[newB]);
        // swap newV and v
        ib.put(pos[newB], v);
        pos[newB] ++;
        ib.put(pos[b], newV);
      }
    }

    long bucketEnd = System.currentTimeMillis();
    System.out.println("(Bucketing took " + (bucketEnd - start) + " ms)");
    // sort each bucket
    for (int b = 0; b < BUCKETS; b++) {
      // System.out.println("Quick sort range: " + startPos[b] + " " + (limits[b] - 1));
      sorter.sort(ib, startPos[b], limits[b] - 1);
    }
  }
  
}

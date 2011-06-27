package com.test.sort;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

import com.test.sort.generator.DataGenerator;
import com.test.sort.generator.PermutationDataGenerator;
import com.test.sort.generator.RandomDataGenerator;
import com.test.sort.sorter.BucketSorter;
import com.test.sort.sorter.QuickSorter;
import com.test.sort.sorter.QuickSorterWithInsertion;
import com.test.sort.sorter.Sorter;

public class JavaSortTest {
  
  /**
   * This test shows that in some cases, bucket sort can be 3 times faster than quicksort.
   * The best speed for bucket sort is  
   */
  public static void main(String[] args) {
    
    int N = 1 * 1000 * 1000 ;
    int TOTAL_BITS = (int)Math.ceil(Math.log(N) / Math.log(2));
    System.out.println("Total bits: " + TOTAL_BITS);
    
    int INT_BYTE_SIZE = Integer.SIZE / Byte.SIZE; 
    int TOTAL_BYTES = N * INT_BYTE_SIZE; 
    ByteBuffer bb = ByteBuffer.allocateDirect(TOTAL_BYTES);
    
    // Test the performance difference between sort algo
    for (Sorter sorter: Arrays.asList(new Sorter[]{
        new QuickSorter(),
        new QuickSorter(),
        new QuickSorterWithInsertion(),
        new QuickSorterWithInsertion(16),
        new QuickSorterWithInsertion(20),
        new BucketSorter(Math.min(20, TOTAL_BITS), TOTAL_BITS),
        new BucketSorter(Math.min(18, TOTAL_BITS), TOTAL_BITS),
        new BucketSorter(16, TOTAL_BITS),
        new BucketSorter(14, TOTAL_BITS),
        new BucketSorter(12, TOTAL_BITS),
        new BucketSorter(10, TOTAL_BITS),
        new BucketSorter(8, TOTAL_BITS),
        })) {
      // Test the performance difference between byte order
      for (ByteOrder order: Arrays.asList(new ByteOrder[]{
          ByteOrder.BIG_ENDIAN,
          ByteOrder.LITTLE_ENDIAN,
          })) {
        for (DataGenerator dg: Arrays.asList(new DataGenerator[]{
            new PermutationDataGenerator(),
            new RandomDataGenerator(),
            })) {
          System.out.println("Sorter=" + sorter.toString() + " ByteOrder=" + order
              + " DataGenerator=" + dg);
          
          bb.order(order);
          
          // Generate numbers
          IntBuffer ib = bb.asIntBuffer();
          dg.generate(ib, N);
          
          // Quick sort
          long start = System.currentTimeMillis();
          sorter.sort(ib, 0, N-1);
          long end = System.currentTimeMillis();
          System.out.println("Total time: " + (end - start) + " ms" );
          for (int i = 0; i < N - 1; i++) {
            if (ib.get(i) > ib.get(i+1)) {
              System.out.println("Result is wrong: ib[" + i + "] = " + ib.get(i));
              System.out.println("Result is wrong: ib[" + (i+1) + "] = " + ib.get(i+1));
              System.exit(1);
            }
          }
        }
      }
    }    
  }
  
}

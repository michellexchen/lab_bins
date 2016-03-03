import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }
    
    public void printInfo(int total, PriorityQueue<Disk> pq) {
    	 System.out.println("total size = " + total / 1000000.0 + "GB");
         System.out.println();
         System.out.println("worst-fit method");
         System.out.println("number of pq used: " + pq.size());
         while (!pq.isEmpty()) {
             System.out.println(pq.poll());
         }
         System.out.println();
    	
    }
    
    public void consolidation(Disk disky, int size, PriorityQueue<Disk> pq) {
    	disky.add(size);
    	pq.add(disky);
    	
    }
    
    public void heuristicOne(List<Integer> datax) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));

        int diskId = 1;
        int total = 0;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() > size) {
                pq.poll();
                consolidation(d, size, pq);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                consolidation(d2, size, pq);
            }
            total += size;
        }
        
        printInfo(total, pq); 
    }
    	
    public List<Integer> fitDisksAndPrint(List<Integer> myList, Function<List<Integer>,List<Integer>> func){
    	List list  = func.apply(myList);
    	return list;
    }
    
    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));

        int diskId = 1;
        int total = 0;
        

        Collections.sort(data, Collections.reverseOrder());
        pq.add(new Disk(0));

        diskId = 1;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() >= size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }

        System.out.println();
        System.out.println("worst-fit decreasing method");
        System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }
}

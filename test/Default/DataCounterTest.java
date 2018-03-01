/*
 *
 */
package Default;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe di test per DataCounter.
 * 
 * @author mc
 */
public class DataCounterTest {
    
    public static void main(String[] args) {
        String s1 = "lorem";
        String s2 = "ipsum";
        String s3 = "dolor";
        DataCounter<String> dt = new DataCounterTreeImpl<>();
        Double d1 = 3.2;
        Double d2 = 5.6;
        Double d3 = 1.1; 
        DataCounter<Double> dh = new DataCounterHashImpl<>();
               
        dt.incCount(s1);
        for(int i=0; i<10; i++)
            dt.incCount(s2);
        dt.incCount(s3);
        
        String s4 = "elemento non incrementato";
        System.out.println(dt.getCount(s4));
        
        Iterator<String> ittree = dt.getIterator();
        while(ittree.hasNext())
        {
            String temp = ittree.next();
            System.out.println(temp + " ---> " + dt.getCount(temp));
        }
        
        try {
            ittree.next();
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        
        try {
            ittree.remove();
        } catch(UnsupportedOperationException e) {
            e.printStackTrace();
        }
        
        //------------------------------------------------------------
        
        dh.incCount(d1);
        for(int i=0; i<12; i++)
            dh.incCount(d2);
        dh.incCount(d3);
        dh.incCount(d3);
        
       System.out.println(dh.getSize());
       
       Iterator<Double> ithash = dh.getIterator();
       while(ithash.hasNext())
       {
            Double temp = ithash.next();
            System.out.println(temp + " ---> " + dh.getCount(temp));
       }
       
       try {
           dh.incCount(null);
       }
       catch(NullPointerException e) {
           e.printStackTrace();
       }
       
       
    }
}

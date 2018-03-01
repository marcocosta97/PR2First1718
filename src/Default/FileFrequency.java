/*
 * 
 */
package Default;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * OVERVIEW: FileFrequency è una classe non modificabile che permette il 
           conteggio del numero di occorrenze di una parola, il numero di
           caratteri e il numero di parole totali in un file di testo.
 * @author mc
 */
public class FileFrequency {
    
    private Scanner mainFile;
    private DataCounter<String> table;
    private int totalChars;
    private int noSpaceChars;
    private int totalWords;
    private String output; 
    
    /**
     * EFFECTS: costruttore della classe con argomento il filepath (assoluto o relativo)
     *          del File di testo da aprire, se possibile richiama la procedura 
     *          <code>read()</code> per l'analisi del File.
     * @throws NullPointerException se <tt>filepath == null</tt> (ecc. Java, unchecked)
     * @throws FileNotFoundException se il file non è stato trovato (ecc. disp
     *          in Java, checked)
     *
     * @param filepath abs path
     */
    public FileFrequency(String filepath) throws FileNotFoundException, NullPointerException {     
        if(filepath == null) throw new NullPointerException("param is null");
        
        try {
            mainFile = new Scanner(new File(filepath));
        }
        catch(FileNotFoundException e) {
            throw new FileNotFoundException(filepath + " non trovato");
        }
        
        //table = new DataCounterTreeImpl<>();
        table = new DataCounterHashImpl<>();
        totalChars = 0;
        noSpaceChars = 0;
        totalWords = 0;
        output = null;
        read();
    }
    
    /**
     * EFFECTS: procedura per la lettura e analisi del File.<p>
     * MODIFIES: table, noSpaceChars, totalChars, totalWords.
     */
    private void read() {     
        
        String curr = "";
        String[] split;
        
        while(mainFile.hasNext())
        {
            String s = mainFile.nextLine();
            curr += s;
            s = s.replaceAll("\\s+", "");
            noSpaceChars += s.length();
        }
        
        mainFile.close();
        
        totalChars = curr.length();
        split = curr.replaceAll("[^a-zA-Zà-ù ]", " ").toLowerCase().split("\\s+");
        
        for(String temp : split)
        {   
            table.incCount(temp);
            totalWords++;
        } 
    }
    
    /**
     * EFFECTS: restituisce un vettore di stringhe ordinato per numero di 
     *          occorrenze (e in caso di parità, in ordine alfabetico) dove
     *          ogni posizione è formattata come "pos chiave -&gt; occorrenze"
     * 
     * @return String vector
     */
    private String[] compute() {
        
        class Wrapper implements Comparable<Wrapper> {
            String K;
            Integer V;

            public Wrapper(String K, Integer V) {
                this.K = K;
                this.V = V;
            }
            
            @Override
            public int compareTo(Wrapper o) {
                return V.compareTo(o.V) * -1;
            }
        }
        
        Iterator<String> it = table.getIterator();
        ArrayList<Wrapper> currList= new ArrayList<>();
               
        while(it.hasNext())
        {
            String temp = it.next();
            currList.add(new Wrapper(temp, table.getCount(temp)));
        }      
        Collections.sort(currList);
        
        String[] s = new String[currList.size()];
        int i = 0;
        for(Wrapper w : currList)
        {
            s[i] = i+1 + ". " + w.K + " -> " + w.V;
            i++;
        }
            
        return s;   
    }
    
    /**
     * EFFECTS: restituisce una stringa contenente la rappresentazione formale
     *          della classe
     * 
     * @return toString
     */
    @Override
    public String toString() {
        if(output != null) return output;
        
        String s = "Number of characters (including spaces): " +  totalChars + "\n" +
                    "Number of characters (without spaces): " + noSpaceChars + "\n" +
                    "Number of words: " + totalWords + "\n";
        
        String[] tableRap = compute();
        for(String t : tableRap)
            s += t + "\n";
        
        output = s;
        
        return output;
    } 
    
    /**
     * Semplice <tt>main</tt> di test per la classe.
     * Si richiede l'inserimento del filepath assoluto o relativo tramite 
     * console e ne viene stampato il risultato.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        
        FileFrequency f;
        System.out.print("Inserire nome file: ");
        String input = reader.nextLine();
        
        try {
            f = new FileFrequency(input);
            System.out.println(f.toString());   
        } catch (FileNotFoundException ex) {
            System.err.println("File " + input + " non trovato!");
        }    
    }   
}

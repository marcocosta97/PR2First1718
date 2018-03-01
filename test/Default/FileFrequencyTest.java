/*
 *
 */
package Default;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe di test per FileFrequency.
 * 
 * @author mc
 */
public class FileFrequencyTest {
    public static void main(String[] args) {
        try {
            FileFrequency ff = new FileFrequency("sample.txt");
            System.out.println(ff);
        } catch (FileNotFoundException ex) {
            System.err.println("sample.txt non trovato");
        }     
    }
}

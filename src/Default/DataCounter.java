/*
 * 
 */
package Default;

import java.util.Iterator;

/**
 * DataCounter rappresenta una collezione modificabile di dimensione dinamica.
 * Permette di associare ad ogni oggetto chiave di tipo E un valore numerico.
 * Non sono ammessi duplicati di oggetti chiave.<p>
 * 
 * Typical Element: <tt>f: V -&gt; N</tt> con f funzione parziale definita sul 
 *                  dominio V (Values) e codominio N (Number) tale che 
 *                  <tt>{f(v)|v member V} è definita.</tt>
 * 
 * @author mc 
 */
public interface DataCounter<E> {
    
    /**
     * MODIFIES: this<p>
     * EFFECTS: incrementa di 1 il valore associato all'elemento data, se questo
     *          non è presente viene aggiunto e incrementato ad 1
     * @throws NullPointerException se <tt>data = null</tt> (ecc. disponibile in
     *         Java, unchecked)
     * 
     * @param data element 
     */
    public void incCount(E data) throws NullPointerException;
    
    /**
     * EFFECTS: restituisce il numero totale di elementi presenti nella 
     *          collezione (#V), 0 se non c'è nessun elemento presente
     * 
     * @return size
     */
    public int getSize();
    
    /**
     * EFFECTS: restituisce il valore corrente associato al parametro data,
     *           e 0 se data non appartiene alla collezione
     * @throws NullPointerException se <tt>data = null</tt> (ecc. disponibile in
     *         Java, unchecked)
     * 
     * @param data element
     * @return count
     */
    public int getCount(E data) throws NullPointerException;
    
    /**
     * EFFECTS: restituisce un iteratore sull'insieme 
     * delle chiavi per l'oggetto corrente.
     * 
     * @return iterator
     */
    public Iterator<E> getIterator();
    
}

/*
 *
 */
package Default;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementazione dell'interfaccia <tt>DataCounter</tt>.
 * Si richiede che il tipo generico &lt;E&gt; estenda <tt>Comparable</tt> in
 * quanto l'iteratore della classe è ordinato rispetto all'ordinamento 
 * predefinito delle chiavi.
 * 
 * @author mc
 */
public class DataCounterHashImpl<E extends Comparable<E>> implements DataCounter<E> {
    /**
     *  A(c) = <tt>f: V -&gt; N</tt> funzione parziale tale che <tt>f(el) = c.table.get(el) <b>iff</b>
     *        c.table.containsKey(el) == true</tt> (el member of V)<p>
     * 
     * Inv rep = <tt>table != null &amp;&amp; 
     *           forall i member of {table.keySet()} =&gt; (table.get(i) != null &amp;&amp;
     *           table.get(i) &gt; 0)</tt>
     */   
    private final Hashtable<E, Integer> table;
    
    /**
     * EFFECTS: costruttore, inizializza un nuovo oggetto <tt>table</tt> vuoto.
     */
    public DataCounterHashImpl() {
        table = new Hashtable<>();
    }
    
    /**
     * MODIFIES: this<p>
     * REQUIRES: <code>table.containsKey(data) == false</code><p>
     * EFFECTS: aggiunge l'elemento data all'oggetto sse <code>!(table.contains(data))</code>
     *          con valore associato 1
     * @throws NullPointerException se <code>data == null</code> (unchecked)
     * 
     * @param data element 
     */
    private void add(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("param is null");
        
        table.put(data, 1);
        
    }
    
    /**
     * MODIFIES: this<p>
     * EFFECTS: incrementa di 1 il valore associato a data se questo è 
     *          presente, altrimenti richiama il metodo <code>add(data)</code>
     * @throws NullPointerException se <code>data == null</code> 
     *         (ecc. disponibile in Java, unchecked)
     * 
     * @param data element 
     */
    @Override
    public void incCount(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("param is null");
        if(!table.containsKey(data)) 
            add(data);
        else
            table.replace(data, table.get(data) + 1);
    }

    /**
     * EFFECTS: restituisce il numero di chiavi presenti in this, 0 se this è vuoto
     * 
     * @return size
     */
    @Override
    public int getSize() {
        return table.size();
    }

    /**
     * EFFECTS: restituisce il valore corrente associato alla chiave data,
     *           e 0 se data non appartiene a this
     * @throws NullPointerException se <code>data = null</code> (ecc. disponibile in
     *         Java, unchecked)
     * 
     * @param data element
     * @return count
     */
    @Override
    public int getCount(E data) throws NullPointerException {
        if(data == null) throw new NullPointerException("param is null");
        if(!table.containsKey(data)) return 0;
        
        return table.get(data);
    }

    /**
     * EFFECTS: restituisce un iteratore (senza metodo remove) che permette di 
     *          scorrere il dominio delle chiavi in modo ordinato rispetto
     *          all'ordinamento predefinito della classe E
     *          
     * @return iterator
     */
    @Override
    public Iterator<E> getIterator() {
        return new DataCounterGen(table.keySet());
    }
    
    /**
     * DataCounterGen è una classe non modificabile, statica privata. 
     * Implementa i metodi per l'iterazione ordinata rispetto all'ordinamento
     * naturale delle chiavi della classe.
     * Non è permesso il metodo <tt>remove</tt>.
     * 
     * @param <E> tipo generico 
     */
    private static class DataCounterGen<E extends Comparable<E>> implements Iterator<E> {
        
        /**
         * A(obj, curr) = <tt>{(obj.get(0)), ..., (obj.get(obj.size())}</tt><p>
         * 
         * Inv rep = <tt>obj != null &amp;&amp; 
         *               0 &lt;= curr &lt;= obj.size() &amp;&amp; 
         *               sorted(obj)</tt>           
         */
        private List<E> obj;
        private int curr;
        
        /**
         * REQUIRES: <code>obj != null</code>
         * EFFECTS: metodo costruttore, l'argomento viene copiato in una 
         *          struttura di tipo <tt>ArrayList</tt> e ordinato 
         *          mediante un algoritmo di ordinamento stabile
         * 
         * @param obj generic comparable Set
         */
        public DataCounterGen(Set<E> obj) {
            this.obj = new ArrayList<>(obj);
            this.curr = 0;
            
            Collections.sort(this.obj); 
        }
        
        /**
         * EFFECTS: restituisce true se <tt>obj</tt> ha altri elementi,
         *          false altrimenti
         * 
         * @return hasNext
         */
        @Override
        public boolean hasNext() {
            return (curr < obj.size());
        }

        /**
         * MODIFIES: <tt>curr</tt>
         * EFFECTS: restituisce l'elemento alla posizione <tt>curr</tt> e 
         *          incrementa <tt>curr</tt>
         * @throws NoSuchElementException se <code>curr &gt;= obj.size()</code>
         *          (ecc. disponibile in Java, unchecked)
         * 
         * @return element
         */
        @Override
        public E next() throws NoSuchElementException {
            if(curr >= obj.size()) throw new NoSuchElementException("no more elements");
            curr++;
            return obj.get(curr-1);
        }
        
        /**
         * EFFECTS: metodo <tt>remove</tt> per l'iteratore
         * 
         * @throws UnsupportedOperationException l'operazione non è supportata
         *          (unchecked)
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("remove not allowed");
        }
    }
    
}

package upo.graph.base;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Interfaccia che modella i metodi pubblici di un oggetto di tipo Grafo, nel quale i vertici sono 
 * identificati univocamente da un indice compreso
 * tra 0 e n-1 (dove n e' il numero di vertici del grafo). 
 * In caso di rimozione di un vertice di indice i, gli indici di tutti i vertici compresi tra i+1 e 
 * n-1 devono essere decrementati di 1, in modo che nel grafo dopo la rimozione, contenente n-1 vertici,
 * gli indici siano compresi tra 0 e n-2.
 * Questa interfaccia non deve essere modificata. Se trovate errori contattate il docente.
 * <p>
 * Nota Bene: Alcuni metodi non sono applicabili a tutti i tipi di grafo (ad es., la visita BFS e'
 * applicabile solo a grafi non pesati). In tali casi, il metodo deve lanciare una UnsupportedOperationException.  
 * 
 * @author Luca Piovesan
 * @version 2.2
 *
 */
public interface Graph {

	/**
	 * Restituisce l'indice interno del vertice identificato da <code>label</code>.
	 * @param label il label del vertice.
	 * @return l'indice interno del vertice identificato da <code>label</code>.
	 */
	int getVertexIndex(String label);

	/**
	 * Restituisce il label del vertice identificato internamente da <code>index</code>.
	 * @param index l'indice interno del vertice.
	 * @return il label del vertice.
	 */
	String getVertexLabel(Integer index);
	
	/** 
	 * Aggiunge un nuovo vertice a <code>this</code>, assegnandogli come indice <code>this.size()</code>. 
	 * Restituisce l'indice del vertice appena aggiunto.
	 *
	 * @param label il nome del vertice da aggiungere (es. "A")
	 * @return l'indice del vertice appena aggiunto.
	 */
	int addVertex(String label);
	
	/** 
	 * Restituisce <tt>true</tt> se <code>this</code> contiene un vertice identificato da <code>label</code>,
	 * <tt>false</tt> altrimenti.
	 * 
	 * @param label il label del vertice che si sta cercando nel grafo.
	 * @return <tt>true</tt> se il vertice e' contenuto nel grafo, <tt>false</tt> altrimenti.
	 */
	boolean containsVertex(String label);
	
	/**
	 * Rimuove il vertice specificato dal grafo. Decrementa di 1 l'indice dei vertici di indice superiore,
	 * in modo che gli indici del grafo siano sempre 0...<code>this.size()-1</code>.
	 * @param label il label del del vertice che si vuole cancellare.
	 * @throws NoSuchElementException se il vertice specificato non appartiene al grafo.
	 */
	void removeVertex(String label) throws NoSuchElementException;
	
	/**
	 * Aggiunge un arco tra i due vertici specificati, se non esiste. Se esiste gia' un arco tra i due vertici,
	 * non fa nulla.
	 * @param sourceVertex il vertice da cui esce l'arco.
	 * @param targetVertex il vertice nel quale entra l'arco.
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 */
	void addEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException;
	
	/**
	 * Controlla se un arco appartiene al grafo. Restituisce <tt>true</tt> se l'arco tra 
	 * <code>sourceVertex</code> e <code>targetVertex</code> esiste, <tt>false</tt> altrimenti.
	 * <p>
	 * NB: se il grafo e' diretto, ne considera anche la direzione.
	 * @param sourceVertex il vertice da cui esce l'arco.
	 * @param targetVertex il vertice nel quale entra l'arco.
	 * @return <tt>true</tt> se l'arco esiste, <tt>false</tt> altrimenti.
	 * @throws IllegalArgumentException  se uno dei due vertici non appartiene al grafo.
	 */
	boolean containsEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException;
	
	/**
	 * Rimuove l'arco specificato dal grafo. 
	 * @param sourceVertex il vertice da cui esce l'arco.
	 * @param targetVertex il vertice nel quale entra l'arco.
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 * @throws NoSuchElementException se l'arco non appartiene al grafo.
	 */
	void removeEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException;
	
	/**
	 * Restituisce un <code>Set</code> che contiene i label di tutti i vertici adiacenti a <code>vertexIndex</code>.
	 * @param vertex il vertice selezionato.
	 * @return un <code>Set</code> che contiene i vertici adiacenti a quello dato.
	 * @throws NoSuchElementException se il vertice non appartiene al grafo.
	 */
	Set<String> getAdjacent(String vertex) throws NoSuchElementException;
	
	/**
	 * Controlla se due vertici sono adiacenti. Restituisce <tt>true</tt> se <code>targetVertex</code>
	 * e' adiacente a <code>sourceVertex</code>, <tt>false</tt> altrimenti.
	 * @param targetVertex il vertice di arrivo.
	 * @param sourceVertex il vertice di partenza.
	 * @return <tt>true</tt> se i due vertici sono adiacenti, <tt>false</tt> altrimenti.
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 */
	boolean isAdjacent(String targetVertex, String sourceVertex) throws IllegalArgumentException;
	
	
	/**
	 * Restituisce il numero di vertici del grafo.
	 * @return il numero di vertici del grafo.
	 */
	int size();
	
	/**
	 * Restituisce <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
	 * @return <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
	 */
	boolean isDirected();
	
	/**
	 * Restituisce <tt>true</tt> se il grafo contiene dei cicli, <tt>false</tt> altrimenti.
	 * @return <tt>true</tt> se il grafo contiene dei cicli, <tt>false</tt> altrimenti.
	 */
	boolean isCyclic();
	
	/**
	 * Restituisce <tt>true</tt> se il grafo e' un Directed Acyclic Graph, <tt>false</tt> altrimenti.
	 * @return <tt>true</tt> se il grafo e' un DAG, <tt>false</tt> altrimenti.
	 */
	boolean isDAG();
	
	
	/**
	 * Esegue una visita in ampiezza (BFS) singola del grafo, e restituisce un oggetto di tipo 
	 * <code>VisitForest</code> che rappresenta l'albero di visita.
	 * @param startingVertex il source vertex.
	 * @return l'albero di visita.
	 * @see VisitForest
	 * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene a <code>this</code>.
	 */
	VisitForest getBFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/**
	 * Esegue una visita in profondita' (DFS) singola del grafo, e restituisce un oggetto di tipo 
	 * <code>VisitForest</code> che rappresenta l'albero di visita.
	 * @param startingVertex il source vertex.
	 * @return l'albero di visita.
	 * @see VisitForest
	 * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene a <code>this</code>.
	 */
	VisitForest getDFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/**
	 * Esegue una visita in profondita' (DFS-TOT) di tutti i vertici del grafo, e restituisce un oggetto di tipo 
	 * <code>VisitForest</code> che rappresenta la foresta di visita.
	 * @param startingVertex il source vertex.
	 * @return la foresta di visita.
	 * @see VisitForest
	 * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene a <code>this</code>.
	 */
	VisitForest getDFSTOTForest(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/**
	 * Esegue una visita in profondita' (DFS-TOT) di tutti i vertici del grafo, visitando i vertici nell'ordine
	 * indicato da <code>vertexOrdering</code>, e restituisce un oggetto di tipo <code>VisitForest</code> 
	 * che rappresenta la foresta di visita.
	 * @param vertexOrdering l'ordine di visita dei vertici.
	 * @return la foresta di visita.
	 * @see VisitForest
	 * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene a <code>this</code> o
	 * se <code>vertexOrdering</code> contiene dei vertici non presenti in <code>this</code>.
	 */
	VisitForest getDFSTOTForest(String[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException;
	
	/**
	 * Calcola e restituisce un ordinamento topologico di <code>this</code>.
	 * @return un array contenente i vertici di <code>this</code> ordinati secondo l'ordine topologico.
	 * @throws UnsupportedOperationException se <code>this</code> non supporta l'ordinamento topologico. 
	 */
	String[] topologicalSort() throws UnsupportedOperationException;
	
	/**
	 * Calcola e restituisce le componenti fortemente connesse di <code>this</code>. 
	 * @return un insieme di insiemi che contiene le CFC di <code>this</code>. Ogni sottinsieme del risultato
	 * corrisponde ad una CFC e contiene gli indici di tutti i suoi vertici.
	 * @throws UnsupportedOperationException se <code>this</code> non supporta questa operazione.
	 */
	Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException;
	
	/**
	 * Calcola e restituisce le componenti connesse di <code>this</code>. 
	 * @return un insieme di insiemi che contiene le CC di <code>this</code>. Ogni sottinsieme del risultato
	 * corrisponde ad una CC e contiene tutti i suoi vertici.
	 * @throws UnsupportedOperationException se <code>this</code> non supporta questa operazione.
	 */
	Set<Set<String>> connectedComponents() throws UnsupportedOperationException;
	
	/**
	 * Compara <code>this</code> con <code>o</code> per uguaglianza. Restituisce <tt>true</tt> se i due
	 * oggetti rappresentano due grafi semanticamente uguali, quindi con lo stesso numero di vertici,
	 * gli stessi indici e gli stessi archi. Restituisce <tt>false</tt> altrimenti.
	 * @param o l'oggetto da comparare con <code>this</code>.
	 * @return <tt>true</tt> se <code>this</code> e <code>o</code> rappresentano due grafi uguali,
	 * <tt>false</tt> altrimenti.
	 */
	boolean equals(Object o);
	
}

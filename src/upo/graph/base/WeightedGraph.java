package upo.graph.base;

import java.util.NoSuchElementException;

/**
 * Interfaccia che modella i metodi pubblici di un oggetto di tipo Grafo Pesato.
 * Questa interfaccia non deve essere modificata. Se trovate errori contattate il docente.
 * 
 * @author Luca Piovesan
 *
 */
public interface WeightedGraph extends Graph {
	
	/**
	 * Rappresenta il peso di default di un arco, appena � stato inserito.
	 */
    double defaultEdgeWeight = 1.0;
	
	/**
	 * Restituisce il peso di un arco, identificato dal vertice di partenza e da quello di arrivo.
	 * 
	 * @param sourceVertex il vertice da cui esce l'arco.
	 * @param targetVertex il vertice nel quale entra l'arco.
	 * @return il peso dell'arco (sourceVertex, targetVertex).
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 * @throws NoSuchElementException se l'arco non appartiene al grafo.
	 */
    double getEdgeWeight(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException;
	
	/**
	 * Assegna un peso ad un arco, identificato dal vertice di partenza e da quello di arrivo.
	 * 
	 * @param sourceVertex il vertice da cui esce l'arco.
	 * @param targetVertex il vertice nel quale entra l'arco.
	 * @param weight il peso da assegnare all'arco (sourceVertex, targetVertex).
	 * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
	 * @throws NoSuchElementException se l'arco non appartiene al grafo.
	 */
    void setEdgeWeight(String sourceVertex, String targetVertex, double weight) throws IllegalArgumentException, NoSuchElementException;

	/** Calcola i cammini minimi da sorgente singola <code>startingVertex</code> utilizzando l'algoritmo 
	 * di Bellman-Ford. 
	 * 
	 * @param startingVertex il vertice sorgente.
	 * @return un <code>WeightedGraph</code> che rappresenta l'albero dei cammini minimi.
	 * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi 
	 * con questo algoritmo o se l'implementazione non e' richiesta dal compito.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene al grafo.
	 */
    WeightedGraph getBellmanFordShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/** Calcola i cammini minimi da sorgente singola <code>startingVertex</code> utilizzando l'algoritmo 
	 * di Dijkstra. 
	 * </br>CONSIGLIO: implementate una PriorityQueue dall'interfaccia in upo.additionalstructures.
	 * 
	 * @param startingVertex il vertice sorgente.
	 * @return un <code>WeightedGraph</code> che rappresenta l'albero dei cammini minimi.
	 * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi 
	 * con questo algoritmo o se l'implementazione non e' richiesta dal compito.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene al grafo.
	 */
    WeightedGraph getDijkstraShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/** Calcola il Minimi Albero Ricoprente utilizzando l'algoritmo 
	 * di Prim. 
	 * </br>CONSIGLIO: implementate una PriorityQueue dall'interfaccia in upo.additionalstructures.
	 * 
	 * @param startingVertex il vertice sorgente.
	 * @return un <code>WeightedGraph</code> che rappresenta il Minimo Albero Ricoprente.
	 * @throws UnsupportedOperationException se <code>this</code> non permette di trovare il MAR 
	 * con questo algoritmo o se l'implementazione non e' richiesta dal compito.
	 * @throws IllegalArgumentException se <code>startingVertex</code> non appartiene al grafo.
	 */
    WeightedGraph getPrimMST(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException;
	
	/** Calcola il Minimi Albero Ricoprente utilizzando l'algoritmo 
	 * di Kruskal. 
	 * </br>CONSIGLIO: implementate una UnionFind dall'interfaccia in upo.additionalstructures.
	 * 
	 * @return un <code>WeightedGraph</code> che rappresenta il Minimo Albero Ricoprente.
	 * @throws UnsupportedOperationException se <code>this</code> non permette di trovare il MAR 
	 * con questo algoritmo o se l'implementazione non e' richiesta dal compito.
	 */
    WeightedGraph getKruskalMST() throws UnsupportedOperationException;
	
	/** Calcola i cammini minimi tra tutte le coppie di vertici utilizzando l'algoritmo 
	 * di Floyd-Warshall. 
	 * </br>CONSIGLIO: non usate la matrice di adiacenza per eseguire l'algoritmo, ma fatene una copia. Altrimenti
	 * "distruggereste" il grafo.
	 * 
	 * @return un <code>WeightedGraph</code> che rappresenta i cammini minimi.
	 * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi 
	 * con questo algoritmo o se l'implementazione non e' richiesta dal compito.
	 */
    WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException;
}

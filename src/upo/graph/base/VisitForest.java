package upo.graph.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Classe che rappresenta una foresta di visita (o albero di visita, per determinati tipi di visite).
 * Deve essere usata per rappresentare l'output di una visita.
 * Questa classe non deve essere modificata (ma potete estenderla).
 * Se trovate errori contattate il docente.
 * 
 * @author Luca Piovesan
 *
 */
public class VisitForest {
	
	private final Graph graph;
	
	public enum VisitType {BFS, DFS, DFS_TOT}
	public final VisitType visitType;
	
	public enum Color {WHITE, GRAY, BLACK}
	private Color[] vertexColor;
	
	private Integer[] parent; //pi greco
	private Double[] distance; //d
	
	private Integer[] startTime; //d
	private Integer[] endTime; //f
	
	/**
	 * Costruisce una nuova foresta di visita.
	 * 
	 * @param graph il grafo visitato.
	 * @param visitType il tipo di visita.
	 */
	public VisitForest(Graph graph, VisitType visitType) {
		this.graph = graph;
		this.visitType = visitType;
		this.initialize();
	}
	
	private void initialize() {
		vertexColor = new Color[graph.size()];
		Arrays.fill(vertexColor, Color.WHITE);
		parent = new Integer[graph.size()];
		Arrays.fill(parent, null);
		distance = new Double[graph.size()];
		Arrays.fill(distance, null);
		startTime = new Integer[graph.size()];
		Arrays.fill(startTime, -1);
		endTime = new Integer[graph.size()];
		Arrays.fill(endTime, -1);
	}
	
	/**
	 * Restituisce tutti i vertici radice.
	 * @return un insieme di indici che rappresentano i vertici che non hanno un predecessore.
	 */
	public Set<String> getRoots() {
		Set<String> res = new HashSet<String>();
		for(int i = 0; i < parent.length; i++) {
			if(parent[i] == null) res.add(this.graph.getVertexLabel(i));
		}
		return res;
	}
	
	/**
	 * Restituisce il colore del vertice vertex
	 * @param vertex il vertice di interesse
	 * @return il colore del vertice vertex in questa visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo.
	 */
	public Color getColor(String vertex) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		return vertexColor[this.graph.getVertexIndex(vertex)];
	}
	
	/**
	 * Setta il colore del vertice vertex
	 * @param vertex il vertice di interesse
	 * @param color il nuovo colore
	 * @throws NoSuchElementException se vertex non appartiene al grafo
	 * @throws IllegalArgumentException se non e' possibile effettuare questa operazione con i parametri dati
	 */
	public void setColor(String vertex, Color color) throws NoSuchElementException, IllegalArgumentException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		if(vertexColor[this.graph.getVertexIndex(vertex)].compareTo(color) > 0) throw new IllegalArgumentException("Il colore di un vertice non puo' passare da GRAY a WHITE o da BLACK a GRAY o WHITE");
		vertexColor[this.graph.getVertexIndex(vertex)] = color;
	}
	
	/**
	 * Restituisce il padre del vertice vertex
	 * @param vertex il vertice di interesse
	 * @return il padre del vertice vertex in questa visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo.
	 */
	public String getPartent(String vertex) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		return graph.getVertexLabel(this.parent[this.graph.getVertexIndex(vertex)]);
	}
	
	/**
	 * Setta il padre del vertice vertex
	 * @param vertex il vertice di interesse
	 * @param parent il nuovo padre
	 * @throws NoSuchElementException se vertex non appartiene al grafo
	 * @throws IllegalArgumentException se non e' possibile effettuare questa operazione con i parametri dati
	 */
	public void setParent(String vertex, String parent) throws NoSuchElementException, IllegalArgumentException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		if(this.graph.getVertexIndex(parent) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+parent+" non appartiene al grafo");
		this.parent[this.graph.getVertexIndex(vertex)] = this.graph.getVertexIndex(parent);
	}
	
	/**
	 * Restituisce la distanza del vertice vertex dalla sorgente (se l'attuale visita prevede di calcolare la distanza)
	 * @param vertex il vertice di interesse
	 * @return la distanza di vertex dalla sorgente in questa visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo o se la visita non calcola la distanza.
	 */
	public Double getDistance(String vertex) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		return distance[this.graph.getVertexIndex(vertex)];
	}
	
	/**
	 * Setta la distanza stimata del vertice vertex dalla sorgente
	 * @param vertex il vertice di interesse
	 * @param distance la nuova distanza
	 * @throws NoSuchElementException se vertex non appartiene al grafo
	 * @throws IllegalArgumentException se non e' possibile effettuare questa operazione con i parametri dati
	 */
	public void setDistance(String vertex, double distance) throws NoSuchElementException, IllegalArgumentException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		this.distance[this.graph.getVertexIndex(vertex)] = distance;
	}
	
	/**
	 * Restituisce il tempo di inizio visita del vertice vertex
	 * @param vertex il vertice di interesse
	 * @return il tempo di inizio visita del vertice vertex in questa visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo.
	 */
	public Integer getStartTime(String vertex) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		return startTime[this.graph.getVertexIndex(vertex)];
	}
	
	/**
	 * Setta il tempo di inizio visita del vertice vertex
	 * @param vertex il vertice di interesse
	 * @param startTime il tempo di inizio visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo
	 * @throws IllegalArgumentException se non e' possibile effettuare questa operazione con i parametri dati
	 */
	public void setStartTime(String vertex, Integer startTime) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		this.startTime[this.graph.getVertexIndex(vertex)] = startTime;
	}
	
	/**
	 * Restituisce il tempo di fine visita del vertice vertex
	 * @param vertex il vertice di interesse
	 * @return il tempo di fine visita del vertice vertex in questa visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo.
	 */
	public Integer getEndTime(String vertex) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		return endTime[this.graph.getVertexIndex(vertex)];
	}
	
	/**
	 * Setta il tempo di fine visita del vertice vertex
	 * @param vertex il vertice di interesse
	 * @param endTime il tempo di fine visita
	 * @throws NoSuchElementException se vertex non appartiene al grafo
	 * @throws IllegalArgumentException se non e' possibile effettuare questa operazione con i parametri dati
	 */
	public void setEndTime(String vertex, Integer endTime) throws NoSuchElementException {
		if(this.graph.getVertexIndex(vertex) >= graph.size()) throw new NoSuchElementException("Il vertice di indice "+vertex+" non appartiene al grafo");
		this.endTime[this.graph.getVertexIndex(vertex)] = endTime;
	}

	
}

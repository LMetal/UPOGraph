package upo.graph.impl;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.impl.visite.VisitForestBFS;
import upo.graph.impl.visite.VisitForestDFS;
import upo.graph.impl.visite.VisitForestFrangia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static upo.graph.base.VisitForest.Color.*;
import static upo.graph.base.VisitForest.VisitType.DFS;

public class AdjMatrixUndir extends AdjMatrixDir implements Graph {
    public AdjMatrixUndir(){
        super();
    }

    /**
     * Ritorna tutti gli archi presenti nel grafo
     *
     * @return arraylist di string[source, target]
     */
    @Override
    public ArrayList<String[]> getAllEdges(){
        ArrayList<String[]> list = new ArrayList<>();

        for (String v: labels){
            for(String u: labels){
                if(getVertexIndex(u) > getVertexIndex(v))
                    if(containsEdge(v, u)) list.add(new String[]{v, u});
            }
        }
        return list;
    }

    /**
     * Aggiunge un arco tra i due vertici specificati, se non esiste. Se esiste gia' un arco tra i due vertici,
     * non fa nulla. Utilizza solo la metà superiore della matrice in quanto non direzionale
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     */
    @Override
    public void addEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException {
        if(!containsVertex(sourceVertex) || !containsVertex(targetVertex)) throw new IllegalArgumentException();

        if(getVertexIndex(sourceVertex) > getVertexIndex(targetVertex)){
            String tmp = sourceVertex;
            sourceVertex = targetVertex;
            targetVertex = tmp;
        }

        if(matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] == Double.POSITIVE_INFINITY) {
            matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] = 1;
        }
        else throw new IllegalArgumentException();
    }

    /**
     * Controlla se un arco appartiene al grafo. Restituisce <tt>true</tt> se l'arco tra
     * <code>sourceVertex</code> e <code>targetVertex</code> esiste, <tt>false</tt> altrimenti.
     * <p>
     * NB: se il grafo e' diretto, ne considera anche la direzione.
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @return <tt>true</tt> se l'arco esiste, <tt>false</tt> altrimenti.
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     */
    @Override
    public boolean containsEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException {
        if(!containsVertex(sourceVertex) || !containsVertex(targetVertex)) throw new IllegalArgumentException();

        if(getVertexIndex(sourceVertex) > getVertexIndex(targetVertex)){
            String tmp = sourceVertex;
            sourceVertex = targetVertex;
            targetVertex = tmp;
        }

        double edgeValue = matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)];
        return edgeValue != Double.POSITIVE_INFINITY && edgeValue != 0;
    }

    /**
     * Rimuove l'arco specificato dal grafo.
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     * @throws NoSuchElementException   se l'arco non appartiene al grafo.
     */
    @Override
    public void removeEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException {
        if(getVertexIndex(sourceVertex) > getVertexIndex(targetVertex)){
            String tmp = sourceVertex;
            sourceVertex = targetVertex;
            targetVertex = tmp;
        }
        if(containsEdge(sourceVertex, targetVertex)){
            matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] = Double.POSITIVE_INFINITY;
        }
        else throw new NoSuchElementException();
    }

    /**
     * Restituisce <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
     *
     * @return <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
     */
    @Override
    public boolean isDirected() {
        return false;
    }

    /**
     * Restituisce <tt>true</tt> se il grafo contiene dei cicli, <tt>false</tt> altrimenti.
     *
     * @return <tt>true</tt> se il grafo contiene dei cicli, <tt>false</tt> altrimenti.
     */
    @Override
    public boolean isCyclic() {
        VisitForest G = new VisitForest(this, DFS);

        for(String label: labels){
            if(G.getColor(label) == WHITE && visitaRicCiclo(G, label)){
                return true;
            }
        }
        return false;
    }

    private boolean visitaRicCiclo(VisitForest G, String u){
        G.setColor(u, GRAY);

        var adj = getAdjacent(u);
        for(String v: adj){
            if(G.getColor(v) == WHITE){
                G.setParent(v, u);
                if(visitaRicCiclo(G, v)) return true;
            }
            else if(!v.equals(G.getPartent(u))) return true;
        }
        G.setColor(u, BLACK);
        return false;
    }

    /**
     * Calcola e restituisce le componenti fortemente connesse di <code>this</code>.
     *
     * @return un insieme di insiemi che contiene le CFC di <code>this</code>. Ogni sottinsieme del risultato
     * corrisponde ad una CFC e contiene gli indici di tutti i suoi vertici.
     * @throws UnsupportedOperationException se <code>this</code> non supporta questa operazione.
     */
    @Override
    public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calcola e restituisce le componenti connesse di <code>this</code>.
     *
     * @return un insieme di insiemi che contiene le CC di <code>this</code>. Ogni sottinsieme del risultato
     * corrisponde ad una CC e contiene tutti i suoi vertici.
     * @throws UnsupportedOperationException se <code>this</code> non supporta questa operazione.
     */
    @Override
    public Set<Set<String>> connectedComponents() throws UnsupportedOperationException {
        Set<Set<String>> CC = new HashSet<>();
        //VisitForest G = new VisitForestBFS(this);
        VisitForestFrangia D = new VisitForestDFS(this);

        for(String u: labels){
            if(D.getColor(u) == WHITE){
                VisitForest cc = getDFSTree(u);

                CC.add(labels.stream()
                        .filter(l -> (cc.getPartent(l) != null || l.equals(u)))
                        .collect(Collectors.toSet())
                );
            }
        }

        return CC;
    }
}

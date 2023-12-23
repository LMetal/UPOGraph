package upo.graph.impl;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.impl.visite.VisitForestBFS;
import upo.graph.impl.visite.VisitForestDFS;
import upo.graph.impl.visite.VisitForestFrangia;

import java.util.*;
import java.util.stream.Collectors;


import static upo.graph.base.VisitForest.Color.*;
import static upo.graph.base.VisitForest.VisitType.DFS;

public class AdjMatrixDir implements Graph {

    protected double [][] matrix;
    protected final ArrayList<String> labels;

    public AdjMatrixDir() {
        matrix = new double[0][0];
        labels = new ArrayList<>();
    }

    private static double[][] increaseMatrix(double[][] matrix) {
        int newSize = matrix.length + 1;
        double[][] newMatrix = new double[newSize][newSize];
        int oldSize = matrix.length;
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                if (i < oldSize && j < oldSize) {
                    //Copio i valori del matrix originale
                    newMatrix[i][j] = matrix[i][j];
                } else {
                    //Riempo gli spazi vuoti con distanza infinita
                    newMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        return newMatrix;
    }

    private static double[][] decreaseMatrix(double[][] matrix, int vertexIndex) {
        int newSize = matrix.length - 1;
        double[][] newMatrix = new double[newSize][newSize];
        int oldSize = matrix.length;
        int flag_i=0, flag_j;

        for (int i = 0; i < oldSize; i++) {
            flag_j=0;
            for (int j = 0; j < oldSize; j++) {
                if(i == vertexIndex){
                    flag_i = 1;
                    break;
                }
                if(j == vertexIndex){
                    flag_j = 1;
                    continue;
                }
                newMatrix[i-flag_i][j-flag_j] = matrix[i][j];
            }
        }
        return newMatrix;
    }


    public ArrayList<String[]> getAllEdges(){
        ArrayList<String[]> list = new ArrayList<>();

        for (String v: labels){
            for(String u: labels){
                if(containsEdge(v, u)) list.add(new String[]{v, u});
            }
        }
        return list;
    }


    /**
     * Restituisce l'indice interno del vertice identificato da <code>label</code>.
     *
     * @param label il label del vertice.
     * @return l'indice interno del vertice identificato da <code>label</code>.
     * @throws IllegalArgumentException se label non identifica un vertice del grafo
     */
    @Override
    public int getVertexIndex(String label) {
        if(!labels.contains(label)) throw new IllegalArgumentException();
        return labels.indexOf(label);
    }

    /**
     * Restituisce il label del vertice identificato internamente da <code>index</code>.
     *
     * @param index l'indice interno del vertice.
     * @return il label del vertice.
     * @throws IllegalArgumentException se index non identifica un vertice del grafo
     */
    @Override
    public String getVertexLabel(Integer index) throws IllegalArgumentException {
        try{
            if(index < 0 || index >= this.size()) throw new IllegalArgumentException();
        } catch (IllegalArgumentException | NullPointerException e){
            return null;
        }

        return labels.get(index);
    }

    /**
     * Aggiunge un nuovo vertice a <code>this</code>, assegnandogli come indice <code>this.size()</code>.
     * Restituisce l'indice del vertice appena aggiunto.
     *
     * @param label il nome del vertice da aggiungere (es. "A")
     * @return l'indice del vertice appena aggiunto.
     * @throws IllegalArgumentException se esiste già un vertice con nome label
     */
    @Override
    public int addVertex(String label) throws IllegalArgumentException{
        if(labels.contains(label)) throw new IllegalArgumentException();
        labels.add(label);
        matrix = increaseMatrix(matrix);
        matrix[matrix.length-1][matrix.length-1] = 0;
        return labels.indexOf(label);
    }

    /**
     * Restituisce <tt>true</tt> se <code>this</code> contiene un vertice identificato da <code>label</code>,
     * <tt>false</tt> altrimenti.
     *
     * @param label il label del vertice che si sta cercando nel grafo.
     * @return <tt>true</tt> se il vertice e' contenuto nel grafo, <tt>false</tt> altrimenti.
     */
    @Override
    public boolean containsVertex(String label) {
        return labels.contains(label);
    }

    /**
     * Rimuove il vertice specificato dal grafo. Decrementa di 1 l'indice dei vertici di indice superiore,
     * in modo che gli indici del grafo siano sempre 0...<code>this.size()-1</code>.
     *
     * @param label il label del del vertice che si vuole cancellare.
     * @throws NoSuchElementException se il vertice specificato non appartiene al grafo.
     */
    @Override
    public void removeVertex(String label) throws NoSuchElementException {
        matrix = decreaseMatrix(matrix, getVertexIndex(label));
        labels.remove(label);
    }

    /**
     * Aggiunge un arco tra i due vertici specificati, se non esiste. Se esiste gia' un arco tra i due vertici,
     * non fa nulla.
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     */
    @Override
    public void addEdge(String sourceVertex, String targetVertex) throws IllegalArgumentException {
        if(!containsVertex(sourceVertex) || !containsVertex(targetVertex)) throw new IllegalArgumentException();

        if(matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] == Double.POSITIVE_INFINITY) {
            matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] = 1;
        }
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
        if(containsEdge(sourceVertex, targetVertex)){
            matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] = Double.POSITIVE_INFINITY;
        }
        else throw new NoSuchElementException();
    }

    /**
     * Restituisce un <code>Set</code> che contiene i label di tutti i vertici adiacenti a <code>vertexIndex</code>.
     *
     * @param vertex il vertice selezionato.
     * @return un <code>Set</code> che contiene i vertici adiacenti a quello dato.
     * @throws NoSuchElementException se il vertice non appartiene al grafo.
     */
    @Override
    public Set<String> getAdjacent(String vertex) throws NoSuchElementException {
        Set<String> adjacent = new HashSet<>();

        try{
            for(int j=0; j< matrix.length; j++){
                if(containsEdge(vertex, getVertexLabel(j))) adjacent.add(getVertexLabel(j));
            }
        } catch (IllegalArgumentException | NoSuchElementException e){
            throw new NoSuchElementException();
        }

        return adjacent;
    }

    /**
     * Controlla se due vertici sono adiacenti. Restituisce <tt>true</tt> se <code>targetVertex</code>
     * e' adiacente a <code>sourceVertex</code>, <tt>false</tt> altrimenti.
     *
     * @param targetVertex il vertice di arrivo.
     * @param sourceVertex il vertice di partenza.
     * @return <tt>true</tt> se i due vertici sono adiacenti, <tt>false</tt> altrimenti.
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     */
    @Override
    public boolean isAdjacent(String targetVertex, String sourceVertex) throws IllegalArgumentException {
        if(!containsVertex(targetVertex) || !containsVertex(sourceVertex)) throw new IllegalArgumentException();
        return getAdjacent(sourceVertex).contains(targetVertex);
    }

    /**
     * Restituisce il numero di vertici del grafo.
     *
     * @return il numero di vertici del grafo.
     */
    @Override
    public int size() {
        return labels.size();
    }

    /**
     * Restituisce <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
     *
     * @return <tt>true</tt> se il grafo e' diretto, <tt>false</tt> altrimenti.
     */
    @Override
    public boolean isDirected() {
        return true;
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

        for(String v: getAdjacent(u)){
            if(G.getColor(v) == WHITE){
                if(visitaRicCiclo(G, v)) return true;
            }
            else if(G.getColor(v) == GRAY) return true;
        }
        G.setColor(u, BLACK);
        return false;
    }

    /**
     * Restituisce <tt>true</tt> se il grafo e' un Directed Acyclic Graph, <tt>false</tt> altrimenti.
     *
     * @return <tt>true</tt> se il grafo e' un DAG, <tt>false</tt> altrimenti.
     */
    @Override
    public boolean isDAG() {
        return isDirected() && !isCyclic();
    }

    /**
     * Esegue una visita in ampiezza (BFS) singola del grafo, e restituisce un oggetto di tipo
     * <code>VisitForest</code> che rappresenta l'albero di visita.
     *
     * @param startingVertex il source vertex.
     * @return l'albero di visita.
     * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene a <code>this</code>.
     * @see VisitForest
     */
    @Override
    public VisitForest getBFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        if(!labels.contains(startingVertex)) throw new IllegalArgumentException();

        VisitForestBFS D = new VisitForestBFS(this);

        return visitaGenerica(startingVertex, D);
    }

    /**
     * Esegue una visita in profondita' (DFS) singola del grafo, e restituisce un oggetto di tipo
     * <code>VisitForest</code> che rappresenta l'albero di visita.
     *
     * @param startingVertex il source vertex.
     * @return l'albero di visita.
     * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene a <code>this</code>.
     * @see VisitForest
     */
    @Override
    public VisitForest getDFSTree(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        if(!labels.contains(startingVertex)) throw new IllegalArgumentException();

        VisitForestDFS D = new VisitForestDFS(this);

        return visitaGenerica(startingVertex, D);
    }

    protected VisitForest visitaGenerica(String startingVertex, VisitForestFrangia D){
        String u, v;

        D.setColor(startingVertex, GRAY);
        D.setStartTime(startingVertex, D.getVisitTime());
        D.increaseVisitTime();
        D.addVertexFrangia(startingVertex);

        while(!D.isFrangiaEmpty()){
            u = D.getVertexFrangia();
            v = getAdjacentWhite(u, D);

            if(v != null){
                D.setColor(v, GRAY);
                D.setStartTime(v, D.getVisitTime());
                D.increaseVisitTime();
                D.setParent(v, u);
                D.addVertexFrangia(v);
            }
            else{
                D.setColor(u, BLACK);
                D.setEndTime(u, D.getVisitTime());
                D.increaseVisitTime();
                D.removeVertexFrangia();
            }
        }

        return D;
    }

    private String getAdjacentWhite(String vertex, VisitForest D){
        Set<String> s = getAdjacent(vertex);
        for(String label: s){
            if(D.getColor(label) == WHITE) return label;
        }
        return null;
    }
    

    /**
     * Esegue una visita in profondita' (DFS-TOT) di tutti i vertici del grafo, e restituisce un oggetto di tipo
     * <code>VisitForest</code> che rappresenta la foresta di visita.
     *
     * @param startingVertex il source vertex.
     * @return la foresta di visita.
     * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene a <code>this</code>.
     * @see VisitForest
     */
    @Override
    public VisitForest getDFSTOTForest(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        if(!labels.contains(startingVertex)) throw new IllegalArgumentException();
        VisitForestDFS D = new VisitForestDFS(this);
        visitaGenerica(startingVertex, D);

        for(String label: D.getRoots()){
            if(D.getColor(label) != BLACK) visitaGenerica(label, D);
        }

        return D;
    }

    /**
     * Esegue una visita in profondita' (DFS-TOT) di tutti i vertici del grafo, visitando i vertici nell'ordine
     * indicato da <code>vertexOrdering</code>, e restituisce un oggetto di tipo <code>VisitForest</code>
     * che rappresenta la foresta di visita.
     *
     * @param vertexOrdering l'ordine di visita dei vertici.
     * @return la foresta di visita.
     * @throws UnsupportedOperationException se <code>this</code> non supporta una visita di questo tipo.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene a <code>this</code> o
     *                                       se <code>vertexOrdering</code> contiene dei vertici non presenti in <code>this</code>.
     * @see VisitForest
     */
    @Override
    public VisitForest getDFSTOTForest(String[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException {
        if(Arrays.stream(vertexOrdering).anyMatch(v -> !this.containsVertex(v))) throw new IllegalArgumentException();

        VisitForestDFS D = new VisitForestDFS(this);
        for(String label: vertexOrdering){
            visitaGenerica(label, D);
        }
        return D;
    }

    /**
     * Calcola e restituisce un ordinamento topologico di <code>this</code>.
     *
     * @return un array contenente i vertici di <code>this</code> ordinati secondo l'ordine topologico.
     * @throws UnsupportedOperationException se <code>this</code> non supporta l'ordinamento topologico.
     */
    @Override
    public String[] topologicalSort() throws UnsupportedOperationException {
        if(!this.isDAG()) throw new UnsupportedOperationException();

        VisitForest D;
        Random rand = new Random();

        // comincia la visita in un vertice casuale
        D = getDFSTOTForest(labels.get(rand.nextInt(labels.size())));

        ArrayList<String> order = new ArrayList<>(labels);

        order.sort(Comparator.comparing(D::getEndTime).reversed());

        return order.toArray(new String[0]);
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
        Set<Set<String>> CFC = new HashSet<>();

        for(String vertex: labels){
            // se vertex e' un vertice non ancora appartenente a una CFC
            if(CFC.stream().noneMatch(set -> set.contains(vertex))){
                // visito i discendenti e antenati di vertex
                VisitForest vDiscendenti = getDFSTree(vertex);
                VisitForest vAntenati = this.trasposto().getDFSTree(vertex);

                // filtro tra tutti i label quelli che sono stati visitati
                // in entrambe le visite, aggiungo anche vertex al set
                CFC.add(labels.stream()
                        .filter(l -> (vDiscendenti.getPartent(l) != null && vAntenati.getPartent(l) != null || l.equals(vertex)))
                        .collect(Collectors.toSet())
                );
            }
        }
        return CFC;
    }

    private Graph trasposto() {
        Graph trasposto = new AdjMatrixDir();
        //copio i nodi
        for(String label: this.labels){
            trasposto.addVertex(label);
        }
        //per ogni vertice ciclo sugli adiacenti, cioè archi uscenti
        for(String vertex: this.labels){
            for(String adj: this.getAdjacent(vertex)){
                // inverto la direzione dell'arco
                trasposto.addEdge(adj, vertex);
            }
        }
        return trasposto;
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
        throw new UnsupportedOperationException();
    }
}

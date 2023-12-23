package upo.graph.impl;

import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;
import upo.graph.impl.visite.VisitForestBFS;
import upo.graph.impl.visite.VisitForestDij;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class AdjMatrixDirWeight extends AdjMatrixDir implements WeightedGraph {
    public AdjMatrixDirWeight(){
        super();
    }

    /**
     * Restituisce il peso di un arco, identificato dal vertice di partenza e da quello di arrivo.
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @return il peso dell'arco (sourceVertex, targetVertex).
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     * @throws NoSuchElementException   se l'arco non appartiene al grafo.
     */
    @Override
    public double getEdgeWeight(String sourceVertex, String targetVertex) throws IllegalArgumentException, NoSuchElementException {
        if(!labels.contains(sourceVertex) || !labels.contains(targetVertex)) throw new IllegalArgumentException();

        return matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)];
    }


    /**
     * Assegna un peso ad un arco, identificato dal vertice di partenza e da quello di arrivo.
     *
     * @param sourceVertex il vertice da cui esce l'arco.
     * @param targetVertex il vertice nel quale entra l'arco.
     * @param weight       il peso da assegnare all'arco (sourceVertex, targetVertex).
     * @throws IllegalArgumentException se uno dei due vertici non appartiene al grafo.
     * @throws NoSuchElementException   se l'arco non appartiene al grafo.
     */
    @Override
    public void setEdgeWeight(String sourceVertex, String targetVertex, double weight) throws IllegalArgumentException, NoSuchElementException {
        if(!labels.contains(sourceVertex) || !labels.contains(targetVertex)) throw new IllegalArgumentException();

        matrix[getVertexIndex(sourceVertex)][getVertexIndex(targetVertex)] = weight;
    }

    /**
     * Calcola i cammini minimi da sorgente singola <code>startingVertex</code> utilizzando l'algoritmo
     * di Bellman-Ford.
     *
     * @param startingVertex il vertice sorgente.
     * @return un <code>WeightedGraph</code> che rappresenta l'albero dei cammini minimi.
     * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi
     *                                       con questo algoritmo o se l'implementazione non e' richiesta dal compito.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene al grafo.
     */
    @Override
    public WeightedGraph getBellmanFordShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        WeightedGraph S = new AdjMatrixDirWeight();
        VisitForest D = new VisitForestBFS(this);
        String u, v;

        for(String vertex: this.labels){
            if(startingVertex.equals(vertex)) D.setDistance(vertex, 0);
            else D.setDistance(vertex, Double.POSITIVE_INFINITY);
        }

        for(int i=0; i<labels.size()-1; i++){
            for(String[] edge: getAllEdges()){
                u = edge[0];
                v = edge[1];
                //System.out.println("if " + D.getDistance(v) +"("+ v +") > "+ D.getDistance(u) + "("+ u +") + " + this.getEdgeWeight(u, v));
                if(D.getDistance(v) > D.getDistance(u) + this.getEdgeWeight(u, v)){
                    D.setParent(v, u);
                    D.setDistance(v, D.getDistance(u) + this.getEdgeWeight(u, v));
                    //System.out.println("Rilasso da " + u +" a "+v);
                }
            }
        }

        // creo l'albero di visita (grafo pesato)
        for(String nodo: labels){
            S.addVertex(nodo);
        }
        //System.out.println("D\n\n"+ D.getVisited());

        for(String vertex: labels){
            if(D.getPartent(vertex) != null){
                //System.out.println(D.getPartent(vertex)+" -> "+vertex);
                S.addEdge(D.getPartent(vertex), vertex);
                S.setEdgeWeight(D.getPartent(vertex), vertex, getEdgeWeight(D.getPartent(vertex), vertex));
            }
        }

        return S;
    }

    /**
     * Calcola i cammini minimi da sorgente singola <code>startingVertex</code> utilizzando l'algoritmo
     * di Dijkstra.
     * </br>CONSIGLIO: implementate una PriorityQueue dall'interfaccia in upo.additionalstructures.
     *
     * @param startingVertex il vertice sorgente.
     * @return un <code>WeightedGraph</code> che rappresenta l'albero dei cammini minimi.
     * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi
     *                                       con questo algoritmo o se l'implementazione non e' richiesta dal compito.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene al grafo.
     */
    @Override
    public WeightedGraph getDijkstraShortestPaths(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        if(!labels.contains(startingVertex)) throw new IllegalArgumentException();

        VisitForestDij D = new VisitForestDij(this);
        WeightedGraph S = new AdjMatrixDirWeight();
        String u;
        
        D.addAllVertexFrangia();
        D.setDistance(startingVertex, 0);


        while(!D.isFrangiaEmpty()){
            u = D.getVertexFrangia();
            //System.out.println("Estratto: "+u);
            D.removeVertexFrangia();

            for(String adj: getAdjacent(u)){
                if(D.getDistance(adj).compareTo(D.getDistance(u) + getEdgeWeight(u, adj)) > 0){
                    //System.out.println(u+" genitore "+adj);
                    D.setDistance(adj, D.getDistance(u) + getEdgeWeight(u, adj));
                    D.setParent(adj, u);
                }
            }
        }


        //visitaGenerica(startingVertex, D);


        // creo l'albero di visita (grafo pesato)
        for(String nodo: labels){
            S.addVertex(nodo);
        }
        //System.out.println("D\n\n"+ D.getVisited());

        for(String vertex: labels){
            if(D.getPartent(vertex) != null){
                //System.out.println(D.getPartent(vertex)+" -> "+vertex);
                S.addEdge(D.getPartent(vertex), vertex);
                S.setEdgeWeight(D.getPartent(vertex), vertex, getEdgeWeight(D.getPartent(vertex), vertex));
            }
        }

        return S;
    }

    /**
     * Calcola il Minimi Albero Ricoprente utilizzando l'algoritmo
     * di Prim.
     * </br>CONSIGLIO: implementate una PriorityQueue dall'interfaccia in upo.additionalstructures.
     *
     * @param startingVertex il vertice sorgente.
     * @return un <code>WeightedGraph</code> che rappresenta il Minimo Albero Ricoprente.
     * @throws UnsupportedOperationException se <code>this</code> non permette di trovare il MAR
     *                                       con questo algoritmo o se l'implementazione non e' richiesta dal compito.
     * @throws IllegalArgumentException      se <code>startingVertex</code> non appartiene al grafo.
     */
    @Override
    public WeightedGraph getPrimMST(String startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException();
        /*if(!labels.contains(startingVertex)) throw new IllegalArgumentException();

        VisitForestDij D = new VisitForestDij(this);
        WeightedGraph S = new AdjMatrixDirWeight();
        String u;

        D.addAllVertexFrangia();
        D.setDistance(startingVertex, 0);


        while(!D.isFrangiaEmpty()){
            u = D.getVertexFrangia();
            System.out.println("Estratto: "+u);
            D.removeVertexFrangia();

            for(String adj: getAdjacent(u)){
                System.out.println("commparo"+u+" "+adj);
                if(D.getDistance(adj).compareTo(getEdgeWeight(u, adj)) > 0){
                    System.out.println(u+" genitore "+adj);
                    D.setDistance(adj, getEdgeWeight(u, adj));
                    D.setParent(adj, u);
                }
            }
        }


        //visitaGenerica(startingVertex, D);


        // creo l'albero di visita (grafo pesato)
        for(String nodo: labels){
            S.addVertex(nodo);
        }
        //System.out.println("D\n\n"+ D.getVisited());

        for(String vertex: labels){
            if(D.getPartent(vertex) != null){
                //System.out.println(D.getPartent(vertex)+" -> "+vertex);
                S.addEdge(D.getPartent(vertex), vertex);
                S.setEdgeWeight(D.getPartent(vertex), vertex, getEdgeWeight(D.getPartent(vertex), vertex));
            }
        }

        return S;*/
    }

    /**
     * Calcola il Minimi Albero Ricoprente utilizzando l'algoritmo
     * di Kruskal.
     * </br>CONSIGLIO: implementate una UnionFind dall'interfaccia in upo.additionalstructures.
     *
     * @return un <code>WeightedGraph</code> che rappresenta il Minimo Albero Ricoprente.
     * @throws UnsupportedOperationException se <code>this</code> non permette di trovare il MAR
     *                                       con questo algoritmo o se l'implementazione non e' richiesta dal compito.
     */
    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calcola i cammini minimi tra tutte le coppie di vertici utilizzando l'algoritmo
     * di Floyd-Warshall.
     * </br>CONSIGLIO: non usate la matrice di adiacenza per eseguire l'algoritmo, ma fatene una copia. Altrimenti
     * "distruggereste" il grafo.
     *
     * @return un <code>WeightedGraph</code> che rappresenta i cammini minimi.
     * @throws UnsupportedOperationException se <code>this</code> non permette di trovare i cammini minimi
     *                                       con questo algoritmo o se l'implementazione non e' richiesta dal compito.
     */
    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        WeightedGraph D = new AdjMatrixDirWeight();
        for(String label: labels) D.addVertex(label);

        for(String i: labels){
            for(String j: labels){
                if(!containsEdge(i, j)) D.addEdge(i, j);
                if(i.equals(j)) D.setEdgeWeight(i, j, 0);
                else D.setEdgeWeight(i, j, getEdgeWeight(i, j));
            }
        }


        for(String k: labels){
            for(String i: labels){
                for(String j: labels){
                    if(D.getEdgeWeight(i, j) > D.getEdgeWeight(i, k) + D.getEdgeWeight(k, j)){
                        D.setEdgeWeight(i, j, D.getEdgeWeight(i, k) + D.getEdgeWeight(k, j));
                    }
                    if(i.equals(j) && D.getEdgeWeight(i, j) < 0) {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        }

        return D;
    }
}

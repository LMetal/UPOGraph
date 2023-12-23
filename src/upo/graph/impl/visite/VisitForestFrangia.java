package upo.graph.impl.visite;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;

import java.util.Set;



public class VisitForestFrangia extends VisitForest {
    private int visitTime;
    protected Graph graph;

    /**
     * Costruisce una nuova foresta di visita.
     *
     * @param graph     il grafo visitato.
     * @param visitType il tipo di visita.
     */
    public VisitForestFrangia(Graph graph, VisitType visitType) {
        super(graph, visitType);
        this.visitTime=1;
        this.graph = graph;
    }

    public void increaseVisitTime(){
        visitTime++;
    }

    public int getVisitTime(){
        return this.visitTime;
    }


    public void addVertexFrangia(String label) {
        throw new UnsupportedOperationException();
    }

    public String getVertexFrangia() {
        throw new UnsupportedOperationException();
    }

    public void removeVertexFrangia(){
        throw new UnsupportedOperationException();
    }

    public boolean isFrangiaEmpty(){
        throw new UnsupportedOperationException();
    }

    public Set<String> getAdjacentToVisit(String u) {
        throw new UnsupportedOperationException();
    }
}

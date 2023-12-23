package upo.graph.impl.visite;

import upo.graph.base.Graph;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static upo.graph.base.VisitForest.Color.WHITE;
import static upo.graph.base.VisitForest.VisitType.BFS;

public class VisitForestBFS extends VisitForestFrangia {
    private final ArrayList<String> frangia;
    private final Graph graph;

    /**
     * Costruisce una nuova foresta di visita.
     *
     * @param graph     il grafo visitato.
     */
    public VisitForestBFS(Graph graph) {
        super(graph, BFS);
        frangia = new ArrayList<>();
        this.graph = graph;
    }

    @Override
    public void addVertexFrangia(String label){
        frangia.add(label);
    }

    @Override
    public String getVertexFrangia(){
        return frangia.get(0);
    }

    @Override
    public void removeVertexFrangia(){
        frangia.remove(0);
    }

    @Override
    public boolean isFrangiaEmpty(){
        return frangia.isEmpty();
    }

    @Override
    public Set<String> getAdjacentToVisit(String u){
        Set<String> adjacent = graph.getAdjacent(u);

        adjacent = adjacent.stream()
                .filter(v -> getColor(v) == WHITE)
                .collect(Collectors.toSet());

        return adjacent;
    }
}

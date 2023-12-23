package upo.graph.impl.visite;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static upo.graph.base.VisitForest.Color.WHITE;
import static upo.graph.base.VisitForest.VisitType.DFS;

public class VisitForestDFS extends VisitForestFrangia {
    protected final ArrayList<String> frangia;
    /**
     * Costruisce una nuova foresta di visita.
     *
     * @param graph     il grafo visitato.
     */
    public VisitForestDFS(Graph graph) {
        super(graph, DFS);
        frangia = new ArrayList<>();
    }

    @Override
    public void addVertexFrangia(String label){
        frangia.add(label);
    }

    @Override
    public String getVertexFrangia(){
        //System.out.println(frangia);
        return frangia.get(frangia.size()-1);
    }

    @Override
    public void removeVertexFrangia(){
        //System.out.println("Rimuovo: "+ frangia.get(0));
        frangia.remove(frangia.size()-1);
    }

    @Override
    public boolean isFrangiaEmpty(){
        return frangia.isEmpty();
    }

    /*@Override
    public Set<String> getAdjacentToVisit(String u){
        Set<String> adjacent = graph.getAdjacent(u);
        Set<String> toVisit = new HashSet<>();

        adjacent = adjacent.stream()
                .filter(v -> getColor(v) == WHITE)
                .collect(Collectors.toSet());

        if(adjacent.size() != 0){
            toVisit.add(adjacent.iterator().next());
        }
        return toVisit;

    }*/
}

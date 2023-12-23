package upo.graph.impl.visite;

import upo.graph.base.WeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

import static upo.graph.base.VisitForest.Color.BLACK;
import static upo.graph.base.VisitForest.Color.WHITE;
import static upo.graph.base.VisitForest.VisitType.BFS;

public class VisitForestDij extends VisitForestFrangia {
    ArrayList<String> frangia;
    Set<String> nodes;
    WeightedGraph graph;

    /**
     * Costruisce una nuova foresta di visita.
     *
     * @param graph il grafo visitato.
     */
    public VisitForestDij(WeightedGraph graph) {
        super(graph, BFS);
        this.graph = graph;
        frangia = new ArrayList<>();
        nodes = new HashSet<>();
    }

    private void sortFrangia(){
        frangia.sort((v1, v2) -> this.getDistance(v1).compareTo(this.getDistance(v2)));
    }

    @Override
    public void addVertexFrangia(String label){}

    public void addAllVertexFrangia(){
        for(int i=0; i<graph.size(); i++){
            frangia.add(graph.getVertexLabel(i));
            nodes.add(graph.getVertexLabel(i));

            setDistance(graph.getVertexLabel(i), Double.POSITIVE_INFINITY);
        }

        sortFrangia();
    }

    @Override
    public String getVertexFrangia(){
        sortFrangia();
        return frangia.get(0);
    }

    @Override
    public void removeVertexFrangia(){
        sortFrangia();
        frangia.remove(0);
    }

    @Override
    public boolean isFrangiaEmpty(){
        return frangia.isEmpty();
    }


    public Set<String> getVisited() {
        return nodes.stream()
                .filter(v -> getDistance(v) != Double.POSITIVE_INFINITY)
                .collect(Collectors.toSet());
    }

    /*@Override
    public Set<String> getAdjacentToVisit(String u){
        Set<String> adjacent = graph.getAdjacent(u);

        adjacent = adjacent.stream()
                .filter(v -> getColor(v) != BLACK)
                .collect(Collectors.toSet());

        return adjacent;
    }*/

    /*@Override
    public void setParent(String adj, String u) throws NoSuchElementException, IllegalArgumentException {
        if(getDistance(adj).compareTo(getDistance(u) + graph.getEdgeWeight(u, adj)) > 0){
            System.out.println(u+" genitore "+adj);
            setDistance(adj, getDistance(u) + graph.getEdgeWeight(u, adj));
            setParent(adj, u);
        }
    }*/
}

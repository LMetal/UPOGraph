package upo.graph.impl.test;

import org.junit.jupiter.api.Test;
import upo.graph.base.WeightedGraph;
import upo.graph.impl.AdjMatrixUndirWeight;

import static org.junit.jupiter.api.Assertions.*;

public class testUndirWeight {

    @Test
    void testGetWeight(){
        /*

        A --- B
           2

        */
        WeightedGraph g1 = new AdjMatrixUndirWeight();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addEdge("A", "B");
        g1.setEdgeWeight("A", "B", 2);

        assertEquals(2, g1.getEdgeWeight("A", "B"));
        assertEquals(0, g1.getEdgeWeight("A", "A"));
        assertEquals(0, g1.getEdgeWeight("B", "B"));

        assertThrows(IllegalArgumentException.class, () -> g1.getEdgeWeight("A", "C"));
        assertThrows(IllegalArgumentException.class, () -> g1.getEdgeWeight("C", "A"));
        assertThrows(IllegalArgumentException.class, () -> g1.getEdgeWeight("C", "D"));
    }

    @Test
    void testDijkstra(){
        WeightedGraph g1 = new AdjMatrixUndirWeight();

        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");
        g1.addEdge("C", "B");
        g1.setEdgeWeight("A", "B", 10);
        g1.setEdgeWeight("A", "C", 4);
        g1.setEdgeWeight("C", "B", 3);

        WeightedGraph Sg1 = g1.getDijkstraShortestPaths("A");
        assertTrue(Sg1.containsVertex("A"));
        assertTrue(Sg1.containsVertex("B"));
        assertTrue(Sg1.containsVertex("C"));

        assertTrue(Sg1.containsEdge("A", "C"));
        assertTrue(Sg1.containsEdge("C", "A"));
        assertTrue(Sg1.containsEdge("C", "B"));
        assertTrue(Sg1.containsEdge("B", "C"));

        assertFalse(Sg1.containsEdge("B", "A"));
        assertFalse(Sg1.containsEdge("A", "B"));

        assertEquals(4, Sg1.getEdgeWeight("A", "C"));
        assertEquals(3, Sg1.getEdgeWeight("C", "B"));


        /*
             D
             |
          /  |
        A -- B -- C

         */
        WeightedGraph g2 = new AdjMatrixUndirWeight();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addEdge("A", "B");
        g2.addEdge("A", "D");
        g2.addEdge("B", "D");
        g2.addEdge("B", "C");
        g2.setEdgeWeight("A", "B", 2);
        g2.setEdgeWeight("A", "D", 10);
        g2.setEdgeWeight("B", "D", 5);
        g2.setEdgeWeight("B", "C", 7);

        WeightedGraph Sg2 = g2.getDijkstraShortestPaths("A");

        assertTrue(Sg2.containsVertex("A"));
        assertTrue(Sg2.containsVertex("B"));
        assertTrue(Sg2.containsVertex("C"));
        assertTrue(Sg2.containsVertex("D"));
        assertTrue(Sg2.containsEdge("A", "B"));
        assertTrue(Sg2.containsEdge("B", "C"));
        assertTrue(Sg2.containsEdge("B", "D"));


        WeightedGraph g3 = new AdjMatrixUndirWeight();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addEdge("A", "B");
        g3.setEdgeWeight("A", "B", 7);

        WeightedGraph Sg3 = g3.getDijkstraShortestPaths("A");

        assertTrue(Sg3.containsVertex("A"));
        assertTrue(Sg3.containsVertex("B"));
        assertTrue(Sg3.containsVertex("C"));
        assertTrue(Sg3.containsEdge("A", "B"));

        /*

            esempio slide

         */

        WeightedGraph g4 = new AdjMatrixUndirWeight();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addVertex("C");
        g4.addVertex("D");
        g4.addVertex("E");
        g4.addEdge("A", "B");
        g4.addEdge("A", "C");
        g4.addEdge("A", "D");
        g4.addEdge("B", "E");
        g4.addEdge("C", "D");
        g4.addEdge("D", "E");
        g4.setEdgeWeight("A", "B", 3);
        g4.setEdgeWeight("A", "C", 2);
        g4.setEdgeWeight("A", "D", 4);
        g4.setEdgeWeight("B", "E", 3);
        g4.setEdgeWeight("C", "D", 3);
        g4.setEdgeWeight("D", "E", 1);

        WeightedGraph Sg4 = g4.getDijkstraShortestPaths("A");

        assertTrue(Sg4.containsVertex("A"));
        assertTrue(Sg4.containsVertex("B"));
        assertTrue(Sg4.containsVertex("C"));
        assertTrue(Sg4.containsVertex("D"));
        assertTrue(Sg4.containsVertex("E"));
        assertEquals(2, Sg4.getEdgeWeight("A", "C"));
        assertEquals(3, Sg4.getEdgeWeight("A", "B"));
        assertEquals(4, Sg4.getEdgeWeight("A", "D"));
        assertEquals(1, Sg4.getEdgeWeight("D", "E"));


        /*
             D <- F
             |
             v
        C -> A <- B
             ^
             |
             E
         */

        WeightedGraph g5 = new AdjMatrixUndirWeight();
        g5.addVertex("A");
        g5.addVertex("B");
        g5.addVertex("C");
        g5.addVertex("D");
        g5.addVertex("E");
        g5.addVertex("F");
        g5.addEdge("B", "A");
        g5.addEdge("C", "A");
        g5.addEdge("D", "A");
        g5.addEdge("E", "A");
        g5.addEdge("F", "D");

        WeightedGraph Sg5 = g5.getDijkstraShortestPaths("A");
        assertTrue(Sg5.containsVertex("A"));
        assertTrue(Sg5.containsVertex("B"));
        assertTrue(Sg5.containsVertex("C"));
        assertTrue(Sg5.containsVertex("D"));
        assertTrue(Sg5.containsVertex("E"));

    }

    @Test
    void testBellmanFord(){
        WeightedGraph g1 = new AdjMatrixUndirWeight();

        //      A
        //   10/ \4
        //    B---C
        //      3

        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");
        g1.addEdge("C", "B");
        g1.setEdgeWeight("A", "B", 10);
        g1.setEdgeWeight("A", "C", 4);
        g1.setEdgeWeight("C", "B", 3);

        AdjMatrixUndirWeight Sg1 = (AdjMatrixUndirWeight) g1.getBellmanFordShortestPaths("A");
        assertTrue(Sg1.containsVertex("A"));
        assertTrue(Sg1.containsVertex("B"));
        assertTrue(Sg1.containsVertex("C"));

        assertEquals(2, Sg1.getAllEdges().size());

        assertTrue(Sg1.containsEdge("A", "C"));
        assertFalse(Sg1.containsEdge("B", "A"));
        assertTrue(Sg1.containsEdge("B", "C"));

        assertEquals(4, Sg1.getEdgeWeight("A", "C"));
        assertEquals(3, Sg1.getEdgeWeight("C", "B"));


        /*
             D
             ^
          /  |
        A -> B -> C

         */
        WeightedGraph g2 = new AdjMatrixUndirWeight();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addEdge("A", "B");
        g2.addEdge("A", "D");
        g2.addEdge("B", "D");
        g2.addEdge("B", "C");
        g2.setEdgeWeight("A", "B", 2);
        g2.setEdgeWeight("A", "D", 10);
        g2.setEdgeWeight("B", "D", 5);
        g2.setEdgeWeight("B", "C", 7);

        WeightedGraph Sg2 = g2.getBellmanFordShortestPaths("A");

        assertTrue(Sg2.containsVertex("A"));
        assertTrue(Sg2.containsVertex("B"));
        assertTrue(Sg2.containsVertex("C"));
        assertTrue(Sg2.containsVertex("D"));
        assertTrue(Sg2.containsEdge("A", "B"));
        assertTrue(Sg2.containsEdge("B", "C"));
        assertTrue(Sg2.containsEdge("B", "D"));


        WeightedGraph g3 = new AdjMatrixUndirWeight();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addEdge("A", "B");
        g3.setEdgeWeight("A", "B", 7);

        WeightedGraph Sg3 = g3.getBellmanFordShortestPaths("A");

        assertTrue(Sg3.containsVertex("A"));
        assertTrue(Sg3.containsVertex("B"));
        assertTrue(Sg3.containsVertex("C"));
        assertTrue(Sg3.containsEdge("A", "B"));

        /*

            esempio slide

         */

        WeightedGraph g4 = new AdjMatrixUndirWeight();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addVertex("C");
        g4.addVertex("D");
        g4.addVertex("E");
        g4.addEdge("A", "B");
        g4.addEdge("A", "C");
        g4.addEdge("A", "D");
        g4.addEdge("B", "E");
        g4.addEdge("C", "D");
        g4.addEdge("D", "E");
        g4.setEdgeWeight("A", "B", 3);
        g4.setEdgeWeight("A", "C", 2);
        g4.setEdgeWeight("A", "D", 4);
        g4.setEdgeWeight("B", "E", 3);
        g4.setEdgeWeight("C", "D", 3);
        g4.setEdgeWeight("D", "E", 1);

        WeightedGraph Sg4 = g4.getBellmanFordShortestPaths("A");

        assertTrue(Sg4.containsVertex("A"));
        assertTrue(Sg4.containsVertex("B"));
        assertTrue(Sg4.containsVertex("C"));
        assertTrue(Sg4.containsVertex("D"));
        assertTrue(Sg4.containsVertex("E"));
        assertEquals(2, Sg4.getEdgeWeight("A", "C"));
        assertEquals(3, Sg4.getEdgeWeight("A", "B"));
        assertEquals(4, Sg4.getEdgeWeight("A", "D"));
        assertEquals(1, Sg4.getEdgeWeight("D", "E"));


        /*
             D <- F
             |
             v
        C -> A <- B
             ^
             |
             E
         */

        WeightedGraph g5 = new AdjMatrixUndirWeight();
        g5.addVertex("A");
        g5.addVertex("B");
        g5.addVertex("C");
        g5.addVertex("D");
        g5.addVertex("E");
        g5.addVertex("F");
        g5.addEdge("B", "A");
        g5.addEdge("C", "A");
        g5.addEdge("D", "A");
        g5.addEdge("E", "A");
        g5.addEdge("F", "D");

        WeightedGraph Sg5 = g5.getBellmanFordShortestPaths("A");
        assertTrue(Sg5.containsVertex("A"));
        assertTrue(Sg5.containsVertex("B"));
        assertTrue(Sg5.containsVertex("C"));
        assertTrue(Sg5.containsVertex("D"));
        assertTrue(Sg5.containsVertex("E"));

    }

    @Test
    void floydWarshall(){
        /*
             D
             ^
          /  |
        A -> B -> C

         */
        WeightedGraph g1 = new AdjMatrixUndirWeight();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addVertex("D");
        g1.addEdge("A", "B");
        g1.addEdge("A", "D");
        g1.addEdge("B", "D");
        g1.addEdge("B", "C");
        g1.setEdgeWeight("A", "B", 2);
        g1.setEdgeWeight("A", "D", 10);
        g1.setEdgeWeight("B", "D", 5);
        g1.setEdgeWeight("B", "C", 7);

        WeightedGraph D1 = g1.getFloydWarshallShortestPaths();

        assertEquals(2, D1.getEdgeWeight("A", "B"));
        assertEquals(9, D1.getEdgeWeight("A", "C"));
        assertEquals(7, D1.getEdgeWeight("A", "D"));
        assertEquals(Double.POSITIVE_INFINITY, D1.getEdgeWeight("D", "A"));


        WeightedGraph g2 = new AdjMatrixUndirWeight();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addEdge("A", "B");
        g2.addEdge("A", "C");
        g2.addEdge("B", "A");
        g2.addEdge("C", "A");
        g2.addEdge("C", "B");
        g2.setEdgeWeight("A", "B", 1);
        g2.setEdgeWeight("A", "C", -1);
        g2.setEdgeWeight("B", "A", -1);
        g2.setEdgeWeight("C", "A", 5);
        g2.setEdgeWeight("C", "B", 2);

        WeightedGraph D2 = g2.getFloydWarshallShortestPaths();

        assertEquals(0, D2.getEdgeWeight("A", "A"));
        assertEquals(1, D2.getEdgeWeight("A", "B"));
        assertEquals(-1, D2.getEdgeWeight("A", "C"));
        assertEquals(-1, D2.getEdgeWeight("B", "A"));
        assertEquals(0, D2.getEdgeWeight("B", "B"));
        assertEquals(-2, D2.getEdgeWeight("B", "C"));
        assertEquals(1, D2.getEdgeWeight("C", "A"));
        assertEquals(2, D2.getEdgeWeight("C", "B"));
        assertEquals(0, D2.getEdgeWeight("C", "C"));


        // ciclo a peso negativo
        WeightedGraph g3 = new AdjMatrixUndirWeight();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("B", "A");
        g3.addEdge("C", "A");
        g3.addEdge("C", "B");
        g3.setEdgeWeight("A", "B", 1);
        g3.setEdgeWeight("A", "C", -2);
        g3.setEdgeWeight("B", "A", -1);
        g3.setEdgeWeight("C", "A", 5);
        g3.setEdgeWeight("C", "B", 2);

        assertThrows(UnsupportedOperationException.class, () -> g3.getFloydWarshallShortestPaths());


        WeightedGraph g4 = new AdjMatrixUndirWeight();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addEdge("A", "B");
        g4.addEdge("B", "A");
        g4.setEdgeWeight("A", "B", 1);
        g4.setEdgeWeight("B", "A", -2);

        assertThrows(UnsupportedOperationException.class, () -> g4.getFloydWarshallShortestPaths());


        WeightedGraph g5 = new AdjMatrixUndirWeight();
        g5.addVertex("A");
        g5.addVertex("B");
        g5.addEdge("A", "B");
        g5.addEdge("B", "A");
        g5.setEdgeWeight("A", "B", 1);
        g5.setEdgeWeight("B", "A", -1);

        assertEquals(0, g5.getEdgeWeight("A", "A"));
        assertEquals(1, g5.getEdgeWeight("A", "B"));
        assertEquals(0, g5.getEdgeWeight("B", "B"));
        assertEquals(-1, g5.getEdgeWeight("B", "A"));
    }

    //@Test
    void testPrim(){
        WeightedGraph g1 = new AdjMatrixUndirWeight();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addVertex("D");
        g1.addVertex("E");
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");
        g1.addEdge("B", "D");
        g1.addEdge("D", "E");
        g1.addEdge("C", "E");
        g1.setEdgeWeight("A", "B", 2);
        g1.setEdgeWeight("A", "C", 4);
        g1.setEdgeWeight("B", "D", 3);
        g1.setEdgeWeight("D", "E", 2);
        g1.setEdgeWeight("C", "E", 2);

        WeightedGraph Sg1 = g1.getPrimMST("A");

        assertTrue(Sg1.containsVertex("A"));
        assertTrue(Sg1.containsVertex("B"));
        assertTrue(Sg1.containsVertex("C"));
        assertTrue(Sg1.containsVertex("D"));
        assertTrue(Sg1.containsVertex("E"));
        assertTrue(Sg1.containsEdge("A", "B"));
        //assertFalse(Sg1.containsEdge("A", "C"));
        assertTrue(Sg1.containsEdge("B", "D"));
        assertTrue(Sg1.containsEdge("D", "E"));
        assertTrue(Sg1.containsEdge("C", "E"));
    }
}

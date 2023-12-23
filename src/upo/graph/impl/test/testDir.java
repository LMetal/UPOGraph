package upo.graph.impl.test;

import org.junit.jupiter.api.Test;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.impl.AdjMatrixDir;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testDir {
    @Test
    void testAddVertex(){
        Graph g = new AdjMatrixDir();

        g.addVertex("A");
        assertEquals(1, g.size());
        g.addVertex("B");
        assertEquals(2, g.size());

        assertEquals("A", g.getVertexLabel(0));
        assertEquals(0, g.getVertexIndex("A"));
        assertEquals("B", g.getVertexLabel(1));
        assertEquals(1, g.getVertexIndex("B"));

        g.addVertex("C");
        assertEquals("A", g.getVertexLabel(0));
        assertEquals(0, g.getVertexIndex("A"));
        assertEquals("B", g.getVertexLabel(1));
        assertEquals(1, g.getVertexIndex("B"));
        assertEquals("C", g.getVertexLabel(2));
        assertEquals(2, g.getVertexIndex("C"));
        assertThrows(IllegalArgumentException.class, () -> g.getVertexIndex("D"));

        assertTrue(g.containsVertex("A"));
        assertTrue(g.containsVertex("B"));
        assertFalse(g.containsVertex("D"));


        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        assertEquals(1, g2.size());
        assertThrows(IllegalArgumentException.class, () -> g2.addVertex("A"));
        assertEquals(1, g2.size());
        assertThrows(IllegalArgumentException.class, () -> g2.getVertexIndex("B"));
    }

    @Test
    void testAddEdge(){
        Graph g = new AdjMatrixDir();

        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        assertTrue(g.containsEdge("A", "B"));
        assertFalse(g.containsEdge("B", "A"));

        g.addEdge("B", "A");
        assertTrue(g.containsEdge("A", "B"));
        assertTrue(g.containsEdge("B", "A"));

        g.addVertex("C");
        g.addEdge("C", "A");
        assertTrue(g.containsEdge("C", "A"));


        assertThrows(IllegalArgumentException.class, () -> g.addEdge("Z", "B"));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "Z"));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("Y", "Z"));
    }

    @Test
    void testRemoveVertex(){
        Graph g = new AdjMatrixDir();
        g.addVertex("A");
        g.removeVertex("A");
        assertFalse(g.containsVertex("A"));

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        assertEquals(4, g.size());
        g.removeVertex("B");
        assertEquals(3, g.size());

        assertTrue(g.containsVertex("A"));
        assertTrue(g.containsVertex("C"));


        /*
          A  B  C  D
        A 0  1  1  1
        B s  0  1  s
        C s  s  0  1
        D 1  s  0  0


          A  B  D
        A 0  1  1
        B s  0  s
        D 1  s  0

         */
        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");

        g2.addEdge("A", "B");
        g2.addEdge("A", "C");
        g2.addEdge("A", "D");

        g2.addEdge("B", "C");

        g2.addEdge("C", "D");

        g2.addEdge("D", "A");


        g2.removeVertex("C");

        assertFalse(g2.containsEdge("A", "A"));
        assertTrue(g2.containsEdge("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> g2.containsEdge("A", "C"));
        assertTrue(g2.containsEdge("A", "D"));

        assertFalse(g2.containsEdge("B", "A"));
        assertFalse(g2.containsEdge("B", "B"));
        assertThrows(IllegalArgumentException.class, () -> g2.containsEdge("B", "C"));
        assertFalse(g2.containsEdge("B", "D"));

        assertThrows(IllegalArgumentException.class, () -> g2.containsEdge("C", "C"));

        assertTrue(g2.containsEdge("D", "A"));
        assertFalse(g2.containsEdge("D", "B"));
        assertThrows(IllegalArgumentException.class, () -> g2.containsEdge("D", "C"));
        assertFalse(g2.containsEdge("D", "D"));


    }

    @Test
    void testRemoveEdge(){
        Graph g  = new AdjMatrixDir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        g.removeEdge("A", "B");
        assertFalse(g.containsEdge("A", "B"));
        assertTrue(g.containsEdge("A", "C"));
        assertTrue(g.containsEdge("B", "C"));
        assertTrue(g.containsEdge("C", "A"));

        g.removeEdge("A", "C");
        assertFalse(g.containsEdge("A", "C"));
        assertTrue(g.containsEdge("B", "C"));
        assertTrue(g.containsEdge("C", "A"));

        g.removeEdge("C", "A");
        assertTrue(g.containsEdge("B", "C"));
        assertFalse(g.containsEdge("C", "A"));

        g.removeEdge("B", "C");
        assertFalse(g.containsEdge("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> g.removeEdge("Z", "Y"));
        assertThrows(NoSuchElementException.class, () -> g.removeEdge("A", "B"));
    }

    @Test
    void testGetSetAdjacent(){
        Graph g  = new AdjMatrixDir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        Set<String> adj = g.getAdjacent("A");
        Set<String> exp = new HashSet<>();
        exp.add("B");
        exp.add("C");
        assertEquals(exp, adj);

        adj = g.getAdjacent("B");
        exp = new HashSet<>();
        exp.add("C");
        assertEquals(exp, adj);

        adj = g.getAdjacent("C");
        exp = new HashSet<>();
        exp.add("A");
        assertEquals(exp, adj);


        assertThrows(NoSuchElementException.class, () -> g.getAdjacent("Z"));
    }

    @Test
    void testIsAdjacent(){
        Graph g  = new AdjMatrixDir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        assertTrue(g.isAdjacent("B", "A"));
        assertTrue(g.isAdjacent("C", "A"));
        assertTrue(g.isAdjacent("C", "B"));
        assertTrue(g.isAdjacent("A", "C"));

        assertFalse(g.isAdjacent("A", "B"));
        assertFalse(g.isAdjacent("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> g.isAdjacent("Z", "Y"));
    }

    @Test
    void testCyclicDAG(){
        Graph g = new AdjMatrixDir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        assertFalse(g.isCyclic());
        g.addEdge("C", "A");
        assertTrue(g.isCyclic());


        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addEdge("A", "B");
        g2.addEdge("B", "A");
        assertTrue(g2.isCyclic());

        Graph g3 = new AdjMatrixDir();
        assertFalse(g3.isCyclic());
        g3.addVertex("A");
        assertFalse(g3.isCyclic());
        g3.addVertex("B");
        assertFalse(g3.isCyclic());
        g3.addEdge("A", "B");
        assertFalse(g3.isCyclic());
        g3.addVertex("C");
        assertFalse(g3.isCyclic());
        g3.addEdge("A", "C");
        assertFalse(g3.isCyclic());
        g3.addEdge("C", "B");
        assertFalse(g3.isCyclic());
        g3.addEdge("C", "A");
        assertTrue(g3.isCyclic());

        g3.removeEdge("C", "B");
        assertTrue(g3.isCyclic());
        assertFalse(g3.isDAG());
        g3.removeEdge("C", "A");
        assertFalse(g3.isCyclic());
        assertTrue(g3.isDAG());

        Graph g4 = new AdjMatrixDir();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addEdge("A", "B");
        g4.addEdge("B", "A");
        assertTrue(g4.isCyclic());
    }

    @Test
    void testBFS(){

        /*
          B
         /
        A
         \
          C
         */

        Graph g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");

        VisitForest v = g1.getBFSTree("A");

        //assertThrows(IllegalArgumentException.class, () -> v.getPartent("A"));
        assertNull(v.getPartent("A"));
        assertEquals("A", v.getPartent("C"));
        assertEquals("A", v.getPartent("B"));


        /*
          B ------ F
         / \      / \
        A   D -- E   G
         \ /      \ /
          C ------ H
         */

        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addVertex("G");
        g2.addVertex("H");
        g2.addEdge("A", "B");
        g2.addEdge("A", "D");
        g2.addEdge("B", "C");
        g2.addEdge("D", "C");
        g2.addEdge("C", "E");
        g2.addEdge("B", "F");
        g2.addEdge("D", "H");
        g2.addEdge("F", "G");
        g2.addEdge("H", "G");
        g2.addEdge("E", "F");
        g2.addEdge("E", "H");

        VisitForest v2 = g2.getBFSTree("A");

        assertNull(v2.getPartent("A"));
        assertEquals("A", v2.getPartent("B"));
        assertEquals("A", v2.getPartent("D"));
        assertEquals("B", v2.getPartent("C"));
        assertEquals("C", v2.getPartent("E"));
        assertEquals("B", v2.getPartent("F"));
        assertEquals("D", v2.getPartent("H"));
        assertEquals("F", v2.getPartent("G"));

        /*

        A - > B
         \     \
          C < - D

         */

        Graph g3 = new AdjMatrixDir();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addVertex("D");
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("B", "D");
        g3.addEdge("D", "C");

        VisitForest v3 = g3.getBFSTree("A");

        assertNull(v3.getPartent("A"));
        assertEquals("A", v3.getPartent("B"));
        assertEquals("A", v3.getPartent("C"));
        assertEquals("B", v3.getPartent("D"));


    }

    @Test
    void testDFS(){

        /*
          B
         /
        A
         \
          C
         */

        Graph g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");

        VisitForest v = g1.getDFSTree("A");

        assertNull(v.getPartent("A"));
        assertEquals("A", v.getPartent("C"));
        assertEquals("A", v.getPartent("B"));


        /*
          B ------ H
         / \      / \
        A   C -- D   E
         \ /      \ /
          G ------ F
         */

        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addVertex("G");
        g2.addVertex("H");
        g2.addEdge("A", "B");
        g2.addEdge("A", "G");
        g2.addEdge("B", "C");
        g2.addEdge("G", "C");
        g2.addEdge("C", "D");
        g2.addEdge("B", "H");
        g2.addEdge("G", "F");
        g2.addEdge("D", "H");
        g2.addEdge("D", "F");
        g2.addEdge("H", "E");
        g2.addEdge("F", "E");

        VisitForest v2 = g2.getDFSTree("A");

        assertNull(v2.getPartent("A"));
        assertEquals("A", v2.getPartent("B"));
        assertEquals("B", v2.getPartent("C"));
        assertEquals("C", v2.getPartent("D"));
        assertEquals("D", v2.getPartent("F"));
        assertEquals("F", v2.getPartent("E"));
        assertEquals("D", v2.getPartent("H"));
        assertEquals("A", v2.getPartent("G"));

        /*

        A - > B
         \     \
          C < - D

         */

        Graph g3 = new AdjMatrixDir();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addVertex("D");
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("B", "D");
        g3.addEdge("D", "C");

        VisitForest v3 = g3.getDFSTree("A");

        assertNull(v3.getPartent("A"));
        assertEquals("A", v3.getPartent("B"));
        assertEquals("D", v3.getPartent("C"));
        assertEquals("B", v3.getPartent("D"));
    }

    @Test
    void testVisitForest(){
        /*

        A -> B <- C

         */

        Graph g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("C", "B");

        VisitForest v11 = g1.getDFSTOTForest("A");

        assertNull(v11.getPartent("A"));
        assertNull(v11.getPartent("C"));
        assertEquals("A", v11.getPartent("B"));

        assertEquals("[A, C]", v11.getRoots().toString());


        VisitForest v12 = g1.getDFSTOTForest("B");

        assertNull(v12.getPartent("A"));
        assertNull(v12.getPartent("B"));
        assertNull(v12.getPartent("C"));

        assertEquals("[A, B, C]", v12.getRoots().toString());

        /*

        A -> D -> E     F -> G
             |
        B -> C

         */

        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addVertex("G");
        g2.addEdge("A", "D");
        g2.addEdge("B", "C");
        g2.addEdge("C", "D");
        g2.addEdge("D", "C");
        g2.addEdge("D", "E");
        g2.addEdge("F", "G");

        VisitForest v21 = g2.getDFSTOTForest("A");

        assertEquals("[A, B, F]", v21.getRoots().toString());
        assertEquals("A", v21.getPartent("D"));
        assertEquals("D", v21.getPartent("C"));
        assertEquals("D", v21.getPartent("E"));
        assertEquals("F", v21.getPartent("G"));


        VisitForest v22 = g2.getDFSTOTForest("B");

        assertEquals("[A, B, F]", v22.getRoots().toString());
        assertEquals("B", v22.getPartent("C"));
        assertEquals("C", v22.getPartent("D"));
        assertEquals("D", v22.getPartent("E"));
        assertEquals("F", v22.getPartent("G"));

        // array overload yet to be tested

    }

    @Test
    void CFC(){
        /*

        A <-> B <-> C

         */
        Graph g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("B", "A");
        g1.addEdge("B", "C");
        g1.addEdge("C", "B");

        Set<Set<String>> CFC1 = g1.stronglyConnectedComponents();
        assertEquals(1, CFC1.size());

        Iterator<Set<String>> iter1 = CFC1.iterator();
        assertEquals("[A, B, C]", iter1.next().toString());
        assertFalse(iter1.hasNext());


        /*

        A -> B

         */
        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addEdge("A", "B");

        Set<Set<String>> CFC2 = g2.stronglyConnectedComponents();
        assertEquals(2, CFC2.size());

        Iterator<Set<String>> iter2 = CFC2.iterator();
        assertEquals("[A]", iter2.next().toString());
        assertEquals("[B]", iter2.next().toString());
        assertFalse(iter2.hasNext());

        /*

        A -> B <- E <-> D
          \  |
            C
         */

        Graph g3 = new AdjMatrixDir();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addVertex("D");
        g3.addVertex("E");
        g3.addEdge("A", "B");
        g3.addEdge("B", "C");
        g3.addEdge("C", "A");
        g3.addEdge("E", "B");
        g3.addEdge("E", "D");
        g3.addEdge("D", "E");

        Set<Set<String>> CFC3 = g3.stronglyConnectedComponents();
        assertEquals(2, CFC3.size());

        Iterator<Set<String>> iter3 = CFC3.iterator();
        assertEquals("[A, B, C]", iter3.next().toString());
        assertEquals("[D, E]", iter3.next().toString());
        assertFalse(iter3.hasNext());


        /*
        A   B   C
        */
        Graph g4 = new AdjMatrixDir();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addVertex("C");

        Set<Set<String>> CFC4 = g4.stronglyConnectedComponents();
        assertEquals(3, CFC4.size());

        Iterator<Set<String>> iter4 = CFC4.iterator();
        assertEquals("[A]", iter4.next().toString());
        assertEquals("[B]", iter4.next().toString());
        assertEquals("[C]", iter4.next().toString());
        assertFalse(iter4.hasNext());


        assertThrows(UnsupportedOperationException.class, g1::connectedComponents);
        assertThrows(UnsupportedOperationException.class, g2::connectedComponents);
        assertThrows(UnsupportedOperationException.class, g3::connectedComponents);
        assertThrows(UnsupportedOperationException.class, g4::connectedComponents);

    }

    @Test
    void testTopologicalOrder(){
        /*

        A -> B -> C

         */
        Graph g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("B", "C");

        String[] topOrd1 = g1.topologicalSort();

        assertEquals(3, g1.size());
        assertEquals("[A, B, C]", Arrays.toString(topOrd1));


        /*
                         F
                         |
                         v
        A -> B      C -> E
                    |
                    v
                    D

         */

        Graph g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addEdge("A", "B");
        g2.addEdge("C", "D");
        g2.addEdge("C", "E");
        g2.addEdge("F", "E");

        String[] topOrd2;

        for(int i=0; i<100; i++){
            topOrd2 = g2.topologicalSort();

            assertEquals(6, topOrd2.length);
            List<String> list = Arrays.stream(topOrd2).toList();
            assertTrue(list.indexOf("A") < list.indexOf("B"));
            assertTrue(list.indexOf("C") < list.indexOf("D"));
            assertTrue(list.indexOf("C") < list.indexOf("E"));
            assertTrue(list.indexOf("F") < list.indexOf("E"));
        }

        /*
        NOT DAG
         */
        Graph g3 = new AdjMatrixDir();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addEdge("A", "B");
        g3.addEdge("B", "C");
        g3.addEdge("C", "A");

        assertThrows(UnsupportedOperationException.class, g3::topologicalSort);

    }

    @Test
    void testGetAllEdges(){
        AdjMatrixDir g1 = new AdjMatrixDir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("B", "C");

        ArrayList<String[]> edges = g1.getAllEdges();

        assertEquals(2, edges.size());
        assertEquals("[A, B]", Arrays.toString(edges.get(0)));
        assertEquals("[B, C]", Arrays.toString(edges.get(1)));



        /*
                         F
                         |
                         v
        A -> B      C -> E
                    |
                    v
                    D

         */

        AdjMatrixDir g2 = new AdjMatrixDir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");
        g2.addVertex("E");
        g2.addVertex("F");
        g2.addEdge("A", "B");
        g2.addEdge("C", "D");
        g2.addEdge("C", "E");
        g2.addEdge("F", "E");

        edges = g2.getAllEdges();

        assertEquals(4, edges.size());
        assertEquals("[A, B]", Arrays.toString(edges.get(0)));
        assertEquals("[C, D]", Arrays.toString(edges.get(1)));
        assertEquals("[C, E]", Arrays.toString(edges.get(2)));
        assertEquals("[F, E]", Arrays.toString(edges.get(3)));
    }

}

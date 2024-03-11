package upo.graph.impl.test;

import org.junit.jupiter.api.Test;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;
import upo.graph.impl.AdjMatrixUndir;
import upo.graph.impl.AdjMatrixUndirWeight;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class testUndir {
    @Test
    void testAddVertex(){
        Graph g = new AdjMatrixUndir();

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


        Graph g2 = new AdjMatrixUndir();
        g2.addVertex("A");
        assertEquals(1, g2.size());
        assertThrows(IllegalArgumentException.class, () -> g2.addVertex("A"));
        assertEquals(1, g2.size());
        assertThrows(IllegalArgumentException.class, () -> g2.getVertexIndex("B"));
    }

    @Test
    void testAddEdge(){
        Graph g = new AdjMatrixUndir();

        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        assertTrue(g.containsEdge("A", "B"));
        assertTrue(g.containsEdge("B", "A"));

        assertThrows(IllegalArgumentException.class, () -> g.addEdge("B", "A"));
        assertTrue(g.containsEdge("A", "B"));
        assertTrue(g.containsEdge("B", "A"));

        g.addVertex("C");
        g.addEdge("C", "A");
        assertTrue(g.containsEdge("C", "A"));
        assertTrue(g.containsEdge("A", "C"));


        assertThrows(IllegalArgumentException.class, () -> g.addEdge("Z", "B"));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "Z"));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("Y", "Z"));
    }

    @Test
    void testRemoveVertex(){
        Graph g = new AdjMatrixUndir();
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
        B .  0  1  s
        C .  .  0  1
        D .  .  .  0


          A  B  D
        A 0  1  1
        B .  0  s
        D .  .  0

         */
        Graph g2 = new AdjMatrixUndir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");
        g2.addVertex("D");

        g2.addEdge("A", "B");
        g2.addEdge("A", "C");
        g2.addEdge("A", "D");

        g2.addEdge("B", "C");

        g2.addEdge("C", "D");



        g2.removeVertex("C");

        assertFalse(g2.containsEdge("A", "A"));
        assertTrue(g2.containsEdge("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> g2.containsEdge("A", "C"));
        assertTrue(g2.containsEdge("A", "D"));

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
        Graph g  = new AdjMatrixUndir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");

        g.removeEdge("A", "B");
        assertFalse(g.containsEdge("A", "B"));
        assertFalse(g.containsEdge("B", "A"));
        assertTrue(g.containsEdge("A", "C"));
        assertTrue(g.containsEdge("B", "C"));
        assertTrue(g.containsEdge("C", "A"));

        g.addEdge("A", "B");
        assertTrue(g.containsEdge("A", "B"));
        g.removeEdge("B", "A");
        assertFalse(g.containsEdge("A", "B"));


        g.removeEdge("A", "C");
        assertFalse(g.containsEdge("A", "C"));
        assertTrue(g.containsEdge("B", "C"));
        assertFalse(g.containsEdge("C", "A"));


        g.removeEdge("B", "C");
        assertFalse(g.containsEdge("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> g.removeEdge("Z", "Y"));
        assertThrows(NoSuchElementException.class, () -> g.removeEdge("A", "B"));
    }

    @Test
    void testGetSetAdjacent(){

        /*
        A-----B
         \   /
          \ /
           C

         */

        Graph g  = new AdjMatrixUndir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");

        Set<String> adj = g.getAdjacent("A");
        Set<String> exp = new HashSet<>();
        exp.add("B");
        exp.add("C");
        assertEquals(exp, adj);

        adj = g.getAdjacent("B");
        exp = new HashSet<>();
        exp.add("A");
        exp.add("C");
        assertEquals(exp, adj);

        adj = g.getAdjacent("C");
        exp = new HashSet<>();
        exp.add("A");
        exp.add("B");
        assertEquals(exp, adj);


        assertThrows(NoSuchElementException.class, () -> g.getAdjacent("Z"));
    }

    @Test
    void testIsAdjacent(){
        Graph g  = new AdjMatrixUndir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "C");

        assertTrue(g.isAdjacent("B", "A"));
        assertTrue(g.isAdjacent("C", "A"));
        assertTrue(g.isAdjacent("C", "B"));
        assertTrue(g.isAdjacent("A", "C"));
        assertTrue(g.isAdjacent("A", "B"));
        assertTrue(g.isAdjacent("B", "C"));

        Graph g2 = new AdjMatrixUndir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addEdge("A", "B");
        assertTrue(g2.isAdjacent("A", "B"));
        assertTrue(g2.isAdjacent("B", "A"));

        assertThrows(IllegalArgumentException.class, () -> g.isAdjacent("Z", "Y"));
    }

    @Test
    void testCyclicDAG(){
        Graph g0 = new AdjMatrixUndir();
        g0.addVertex("A");
        g0.addVertex("B");
        g0.addEdge("A", "B");
        assertFalse(g0.isCyclic());

        Graph g = new AdjMatrixUndir();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        assertFalse(g.isCyclic());
        g.addEdge("C", "A");
        assertTrue(g.isCyclic());


        /*
        A-----B
         \   /
          \ /
           C
         */

        Graph g3 = new AdjMatrixUndir();
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
        assertTrue(g3.isCyclic());


        g3.removeEdge("B", "C");
        assertFalse(g3.isCyclic());
        assertFalse(g3.isDAG());
        g3.removeEdge("C", "A");
        assertFalse(g3.isCyclic());
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

        Graph g1 = new AdjMatrixUndir();
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

        Graph g2 = new AdjMatrixUndir();
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

        Graph g3 = new AdjMatrixUndir();
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

        Graph g1 = new AdjMatrixUndir();
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

        Graph g2 = new AdjMatrixUndir();
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
        assertEquals("E", v2.getPartent("H"));
        assertEquals("F", v2.getPartent("G"));

        /*

        A - > B
         \     \
          C < - D

         */

        Graph g3 = new AdjMatrixUndir();
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

        A -- B -- C

         */

        Graph g1 = new AdjMatrixUndir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("C", "B");

        VisitForest v11 = g1.getDFSTOTForest("A");

        assertNull(v11.getPartent("A"));
        assertEquals("A", v11.getPartent("B"));
        assertEquals("B", v11.getPartent("C"));

        assertEquals("[A]", v11.getRoots().toString());


        VisitForest v12 = g1.getDFSTOTForest("B");

        assertEquals("B", v12.getPartent("C"));
        assertEquals("B", v12.getPartent("A"));
        assertNull(v12.getPartent("B"));

        assertEquals("[B]", v12.getRoots().toString());

        /*

        A -- D -- E     F -- G
             |
        B -- C

         */

        Graph g2 = new AdjMatrixUndir();
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
        g2.addEdge("D", "E");
        g2.addEdge("F", "G");

        VisitForest v21 = g2.getDFSTOTForest("A");

        assertEquals("[A, F]", v21.getRoots().toString());
        assertEquals("A", v21.getPartent("D"));
        assertEquals("D", v21.getPartent("C"));
        assertEquals("C", v21.getPartent("B"));
        assertEquals("D", v21.getPartent("E"));
        assertEquals("F", v21.getPartent("G"));

        assertNull(v21.getPartent("A"));
        assertNull(v21.getPartent("F"));


        VisitForest v22 = g2.getDFSTOTForest("B");

        assertEquals("[B, F]", v22.getRoots().toString());
        assertEquals("B", v22.getPartent("C"));
        assertEquals("C", v22.getPartent("D"));
        assertEquals("D", v22.getPartent("E"));
        assertEquals("D", v22.getPartent("A"));
        assertEquals("F", v22.getPartent("G"));

    }

    @Test
    void testCC(){
        /*

        A --- B --- C

         */
        Graph g1 = new AdjMatrixUndir();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addVertex("C");
        g1.addEdge("A", "B");
        g1.addEdge("B", "C");

        Set<Set<String>> CC1 = g1.connectedComponents();
        assertEquals(1, CC1.size());

        Iterator<Set<String>> iter1 = CC1.iterator();
        assertEquals("[A, B, C]", iter1.next().toString());


        /*

        A -- B

         */
        Graph g2 = new AdjMatrixUndir();
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addEdge("A", "B");

        Set<Set<String>> CFC2 = g2.connectedComponents();
        assertEquals(1, CFC2.size());

        Iterator<Set<String>> iter2 = CFC2.iterator();
        assertEquals("[A, B]", iter2.next().toString());

        /*

        A -- B    E --- D
          \  |
            C
         */

        Graph g3 = new AdjMatrixUndir();
        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addVertex("D");
        g3.addVertex("E");
        g3.addEdge("A", "B");
        g3.addEdge("B", "C");
        g3.addEdge("C", "A");

        g3.addEdge("E", "D");

        Set<Set<String>> CFC3 = g3.connectedComponents();
        assertEquals(2, CFC3.size());

        Iterator<Set<String>> iter3 = CFC3.iterator();
        assertEquals("[A, B, C]", iter3.next().toString());
        assertEquals("[D, E]", iter3.next().toString());


        /*
        A   B   C
        */
        Graph g4 = new AdjMatrixUndir();
        g4.addVertex("A");
        g4.addVertex("B");
        g4.addVertex("C");

        Set<Set<String>> CFC4 = g4.connectedComponents();
        assertEquals(3, CFC4.size());

        Iterator<Set<String>> iter4 = CFC4.iterator();
        assertEquals("[A]", iter4.next().toString());
        assertEquals("[B]", iter4.next().toString());
        assertEquals("[C]", iter4.next().toString());
        assertFalse(iter4.hasNext());


        assertThrows(UnsupportedOperationException.class, g1::stronglyConnectedComponents);

    }

    @Test
    void testTopologicalOrder(){
        Graph g = new AdjMatrixUndir();
        assertThrows(UnsupportedOperationException.class, g::topologicalSort);

    }

    @Test
    void testGetAllEdges(){
        AdjMatrixUndir g1 = new AdjMatrixUndir();
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

        AdjMatrixUndir g2 = new AdjMatrixUndir();
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
        assertEquals("[E, F]", Arrays.toString(edges.get(3)));

        AdjMatrixUndir g3 = new AdjMatrixUndirWeight();

        //      A
        //     / \
        //    B---C
        //

        g3.addVertex("A");
        g3.addVertex("B");
        g3.addVertex("C");
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("C", "B");

        assertEquals(3, g3.getAllEdges().size());
    }

}

/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package dk.aaue.sna.alg.centrality;

import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.LinearGraphGenerator;
import org.jgrapht.generate.RingGraphGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import static dk.aaue.sna.alg.JGraphTTests.generate;
import static junit.framework.Assert.assertEquals;

/**
 * @author Soren <soren@tanesha.net>
 */
public class EdmondKarpMaximumFlowTest {


    @Test
    public void testMaximumFlow_01_Linear_one_path() throws Exception {

       WeightedGraph<String, DefaultWeightedEdge> g = generate(new LinearGraphGenerator(10));

        EdmondsKarpMaximumFlow m = new EdmondsKarpMaximumFlow(g, 1);

        for (String s : g.vertexSet()) {

            for (String t : g.vertexSet()) {

                if (s == t)
                    continue;

                assertEquals(1, m.maximumFlow(s, t));
            }
        }
    }

    @Test
    public void testMaximumFlow_02_Circular_2_paths() throws Exception {

        WeightedGraph<String, DefaultWeightedEdge> g = generate(new RingGraphGenerator(10));

        EdmondsKarpMaximumFlow m = new EdmondsKarpMaximumFlow(g, 1);

        for (String s : g.vertexSet()) {

            for (String t : g.vertexSet()) {

                if (s == t)
                    continue;

                assertEquals(2, m.maximumFlow(s, t));
            }
        }
    }

}

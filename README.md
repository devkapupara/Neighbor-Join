<h1>Neighbor Join</h1>
<p>A simple program to compute the phylogenetic trees, whose input is the distance matrix for all the taxons. Performs in O(n<sup>3</sup>), based off of the algorithm by Naruya Saitou and Masatoshi Nei.
Better than the UPGMA and WPGMA because it preserves the distances given in the distance matrix.</p>

<h3>Output</h3>
<p>The program labels the vertex from 'a' through 'z' and computes the intermediate ancestors labelled as u1, u2, u3, ... , u<sub>n</sub>. The program outputs a hashmap with the following structure: "X:Y"=Z, read as distance between taxa X and Y is Z. From this, you can compute the full Phylogenetic tree back by connecting nodes with the distances. </p>

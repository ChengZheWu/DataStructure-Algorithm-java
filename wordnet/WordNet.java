/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private HashMap<Integer, String> syHashMap;
    private HashMap<String, ArrayList<Integer>> wordMap;
    private HashMap<Integer, ArrayList<Integer>> hyMap;
    private In syinput;
    private In hyinput;
    private Digraph dg;
    private ShortestCommonAncestor s;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        this.syHashMap = new HashMap<>();
        this.wordMap = new HashMap<>();
        this.hyMap = new HashMap<>();
        syinput = new In(synsets);
        hyinput = new In(hypernyms);

        while (syinput.hasNextLine()) {
            String[] currline = syinput.readLine().split(",");
            int id = Integer.parseInt(currline[0]);
            syHashMap.put(id, currline[1]);
            for (String word : currline[1].split(" ")) {
                if (wordMap.containsKey(word)) wordMap.get(word).add(id);
                else {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(id);
                    wordMap.put(word, ids);
                }
            }
        }

        dg = new Digraph(syHashMap.size());
        while (hyinput.hasNextLine()) {
            String[] currline = hyinput.readLine().split(",");
            int id = Integer.parseInt(currline[0]);
            ArrayList<Integer> parentids = new ArrayList<>();
            for (int i = 1; i < currline.length; i++) {
                int parentid = Integer.parseInt(currline[i]);
                parentids.add(parentid);
                dg.addEdge(id, parentid);
            }
            hyMap.put(id, parentids);
        }

        Topological topological = new Topological(dg);
        if (topological.hasOrder())
            s = new ShortestCommonAncestor(dg);
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return wordMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return wordMap.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException();
        ArrayList<Integer> noun1ids = wordMap.get(noun1);
        ArrayList<Integer> noun2ids = wordMap.get(noun2);
        return syHashMap.get(s.ancestorSubset(noun1ids, noun2ids));
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException();
        ArrayList<Integer> noun1ids = wordMap.get(noun1);
        ArrayList<Integer> noun2ids = wordMap.get(noun2);
        return s.lengthSubset(noun1ids, noun2ids);
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

}

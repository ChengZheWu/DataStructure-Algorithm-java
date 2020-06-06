import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    private Term[] termarr;
    private Term[] matches;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new IllegalArgumentException();
        termarr = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            termarr[i] = terms[i];
        }
        Arrays.sort(termarr);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException();
        Term search = new Term(prefix, 0);
        int first = BinarySearchDeluxe
                .firstIndexOf(termarr, search, Term.byPrefixOrder(prefix.length()));
        int last = BinarySearchDeluxe
                .lastIndexOf(termarr, search, Term.byPrefixOrder(prefix.length()));
        int len = last - first + 1;
        Term[] matchitems;
        if (first >= 0 && last >= 0) matchitems = new Term[len];
        else matchitems = new Term[0];
        if (matchitems.length != 0) {
            for (int i = 0; i < len; i++) {
                matchitems[i] = termarr[first + i];
            }
        }
        Arrays.sort(matchitems, Term.byReverseWeightOrder());
        this.matches = matchitems;
        return matchitems;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException();
        return matches.length;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}

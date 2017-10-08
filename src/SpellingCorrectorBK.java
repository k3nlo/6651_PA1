import main.java.edu.gatech.gtri.bktree.*;
import main.java.edu.gatech.gtri.bktree.BkTreeSearcher.Match;
import java.util.Set;

//BK Tree implementation from https://github.com/gtri/bk-tree

public class SpellingCorrectorBK {
    // The Hamming distance is a simple metric that counts the number
// of positions on which the strings (of equal length) differ.
    Metric<String> hammingDistance = new Metric<String>() {
        @Override
        public int distance(String x, String y) {

            if (x.length() != y.length())
                throw new IllegalArgumentException();

            int distance = 0;

            for (int i = 0; i < x.length(); i++)
                if (x.charAt(i) != y.charAt(i))
                    distance++;

            return distance;
        }
    };

    MutableBkTree<String> bkTree = new MutableBkTree<>(hammingDistance);
bkTree.addAll("berets", "carrot", "egrets", "marmot", "packet", "pilots", "purist");

    BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);

    Set<Match<? extends String>> matches = searcher.search("parrot", 2);

for (Match<? extends String> match : matches)
            System.out.println(String.format(
            "%s (distance %d)",
            match.getMatch(),
            match.getDistance()
            ));

}


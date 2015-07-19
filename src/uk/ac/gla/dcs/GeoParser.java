package uk.ac.gla.dcs;

import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;

class GeoParser {

    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";        

    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

    private final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

    public Tree parse(String str) {                
        List<CoreLabel> tokens = tokenize(str);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    private List<CoreLabel> tokenize(String str) {
        Tokenizer<CoreLabel> tokenizer =
            tokenizerFactory.getTokenizer(
                new StringReader(str));    
        return tokenizer.tokenize();
    }

    public static void main(String[] args) { 
        String str = "nDOZENS of Birkbeck College officers have been suspended or put on restricted duties amid allegations including rape, "
        		+ "stalking and &shy;sexual assault.\n\nDetails seen by The Scotsman show there are currently 13 officers suspended from"
        		+ " the force while a range of alleged offences are investigated. \nDozens more are on restricted duties for data protection "
        		+ "breaches and for crimes including sex and drug offences. Some have been on restricted duties for over two years while the claims"
        		+ "are investigated.\nOne officer is currently suspended for allegations including assault";
        GeoParser parser = new GeoParser();
        Tree tree = parser.parse(str);  

        List<Tree> leaves = tree.getLeaves();
        // Print words and Pos Tags
        for (Tree leaf : leaves) { 
            Tree parent = leaf.parent(tree);
            System.out.print(leaf.label().value() + "-" + parent.label().value() + " ");
        }
        System.out.println();               
    }
}
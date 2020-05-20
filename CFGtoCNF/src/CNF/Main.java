package CNF;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CNFRules grammar = new CNFRules();

            if (grammar.importFile("/Users/janegarciu/Downloads/CFGtoCNF/src/grammar.txt")) {
                System.out.println(grammar.toString());

                grammar.printCfg();

                // Check recursive initial symbol
                if (grammar.verifyInitialRecursion()) {
                    grammar.removeInitialRecusion();
                } else
                    System.out.println("No initial recursive rule");

                // Check Lambda Symbols
                if (grammar.verifyLambdaRule()) {
                    grammar.removeLambda();
                } else
                    System.out.println("No Lambda rule");

                // Check chain rule
                if (grammar.verifyChainRule()) {
                    grammar.removeChainRule();
                } else
                    System.out.println("No chain rule");


                grammar.printResult();
        }

    }

}

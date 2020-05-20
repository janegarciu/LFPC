import java.util.*;

public class Nfa {
    String start;
    Set<String> ends;
    Map<String, Map<Character, List<String>>> transitions; // state -> (character -> [next states])
    // note the difference from DFA: can have multiple different transitions from state for character


    Nfa(String[] ss, String[] ts) {
        ends = new TreeSet<String>();
        transitions = new TreeMap<String, Map<Character,List<String>>>();

        // States
        for (String v : ss) {
            String[] pieces = v.split(",");
            if (pieces.length>1) {
                if (pieces[1].equals("S")) start = pieces[0];
                else if (pieces[1].equals("E")) ends.add(pieces[0]);
            }
        }

        // Transitions
        for (String e : ts) {
            String[] pieces = e.split(",");
            String from = pieces[0],
                    to = pieces[1];
            if (!transitions.containsKey(from)) transitions.put(from, new TreeMap<Character,List<String>>());
            for (int i=2; i<pieces.length; i++) {
                char c = pieces[i].charAt(0);
                // difference from DFA: list of next states
                if (!transitions.get(from).containsKey(c)) transitions.get(from).put(c, new ArrayList<String>());
                transitions.get(from).get(c).add(to);
            }
        }

        System.out.println("start:"+start);
        System.out.println("end:"+ends);
        System.out.println("transitions:"+transitions);
    }

    /* Returns whether or not the DFA accepts the string --
      follows transitions according to its characters, landing in an end state at the end of the string
     */
    public boolean match(String s) {
        // difference from DFA: multiple current states
        Set<String> currStates = new TreeSet<String>();
        currStates.add(start);
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            Set<String> nextStates = new TreeSet<String>();
            // transition from each current state to each of its next states
            for (String state : currStates)
                if (transitions.get(state).containsKey(c))
                    nextStates.addAll(transitions.get(state).get(c));
            if (nextStates.isEmpty()) return false; // no way forward for this input
            currStates = nextStates;
        }
        // end up in multiple states -- accept if any is an end state
        for (String state : currStates) {
            if (ends.contains(state)) return true;
        }
        return false;
    }

    /* Helper method to test matching against a bunch of strings, printing the results
     */
    public void test(String name, String[] inputs) {
        System.out.println("***" + name);
        for (String s : inputs)
            System.out.println(s + ":" + match(s));
    }

    public static void main(String[] args) {


        String[] ss = { "S,S", "A", "B", "C", "X,E" };
        String[] ts = { "S,A,a", "A,S,b", "A,B,a", "B,C,b", "C,A,a", "C,X,b" };
        Nfa nfa2 = new Nfa(ss, ts);

        String[] testJ = { "abaabb", "aabaabb", "ababb"};
        nfa2.test("testJ", testJ);

    }

}

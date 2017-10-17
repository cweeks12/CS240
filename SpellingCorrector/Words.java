package spell;

import java.lang.StringBuilder;

public class Words implements spell.ITrie{

    public class Node implements spell.ITrie.INode{
        
        public Node[] nodes;
        private int count;

        public Node(){
            count = 0;
            nodes = new Node[26];
        }

        public int getValue(){
            return count;
        }

        public void incrementCount(){
            count++;
        }

        public void addWords(StringBuilder sb, StringBuilder base){
            for (int i = 0; i < nodes.length; i++){
                base.append((char)(i+97));
                if (nodes[i] != null && nodes[i].getValue() > 0){
                    sb.append(base.toString());
                    sb.append("\n");
                } 
                if (nodes[i] != null){
                    nodes[i].addWords(sb, base); 
                }
                base.deleteCharAt(base.length()-1);
            }
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            addWords(sb, new StringBuilder());
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }

        @Override
        public int hashCode(){
            int code = 0;
            for (Node n : nodes){
                if (n != null){
                    code++;
                }
            }
            code *= 17;
            code += count;
            return code;
        }

        @Override
        public boolean equals(Object o){

            if (this == o){
                return true;
            }
            if (o == null){
                return false;
            }
            if(this.getClass() != o.getClass()){
                return false;
            }

            Node p = (Node) o;

            if (this.getValue() != p.getValue()){
                return false;
            }

            for (int i = 0; i < nodes.length; i++){
                if (this.nodes[i] == null && p.nodes[i] == null){
                    continue;
                }


                else if ((this.nodes[i] == null && p.nodes[i] != null)
                      || (this.nodes[i] != null && p.nodes[i] == null)){
                    return false;
                }
                else if(!this.nodes[i].equals(p.nodes[i])){
                    return false;
                }
            }
            return true;
        }

    }

    private int wordCount;
    private int nodeCount;
    public Node root;

    public Words(){
        wordCount = 0;
        nodeCount = 1;
        root = new Node();
    }

    public void add(String word){
        Node currentNode = root;
        word = word.toLowerCase();

        for (char ch : word.toCharArray()){
            int index = ch-97;
            if (currentNode.nodes[index] == null)
            {
                currentNode.nodes[index] = new Node();
                nodeCount++;        
            }

            currentNode = currentNode.nodes[index];
        }
        
        if (currentNode.getValue() == 0){
            wordCount++;
        }

        currentNode.incrementCount();
    }

    public Node find(String word){
        Node currentNode = root;
        word = word.toLowerCase();

        for (char ch : word.toCharArray()){
            int index = ch-97;
            if (currentNode.nodes[index] == null)
            {
                return null;
            }

            currentNode = currentNode.nodes[index];
        }
        if (currentNode.getValue() > 0){
            return currentNode;
        }
        return null;
    }

    public int getWordCount(){
        return wordCount;
    }

    public int getNodeCount(){
        return nodeCount;
    }
    
    @Override
    public String toString(){
        return root.toString();
    }

    @Override
    public int hashCode(){
        return wordCount*31 + nodeCount;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }

        Words p = (Words) o;
        return this.root.equals(p.root);
    }
}

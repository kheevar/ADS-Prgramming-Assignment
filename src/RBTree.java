import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class RBTree {

    private RBTNode root;

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public class RBTNode {
        boolean color;       
        String key;
        String value;
        RBTNode left; 
        RBTNode right;   
        RBTNode parent;    

        public RBTNode(String key, String value, boolean color, RBTNode parent, RBTNode left, RBTNode right) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public String getKey() {
            return key;
        }

        public String toString() {
            return "" + key + (this.color == RED ? "(R)" : "B");
        }
    }

    public RBTree() {
        root = null;
    }

    private void setvalue(RBTNode node, String value) {
        if (node != null)
            node.value = value;
    }

    private RBTNode parentOf(RBTNode node) {
        return node != null ? node.parent : null;
    }

    private boolean colorOf(RBTNode node) {
        return node != null ? node.color : BLACK;
    }

    private boolean isRed(RBTNode node) {
        return ((node != null) && (node.color == RED)) ? true : false;
    }

    private boolean isBlack(RBTNode node) {
        return !isRed(node);
    }

    private void setBlack(RBTNode node) {
        if (node != null)
            node.color = BLACK;
    }

    private void setRed(RBTNode node) {
        if (node != null)
            node.color = RED;
    }

    private void setParent(RBTNode node, RBTNode parent) {
        if (node != null)
            node.parent = parent;
    }

    private void setColor(RBTNode node, boolean color) {
        if (node != null)
            node.color = color;
    }


    private void dump(RBTNode tree) {
        if (tree != null) {
            dump(tree.left);
            System.out.println(tree.key + "means " + tree.value + " ");
            dump(tree.right);
        }
    }

    public void dump() {
        dump(root);
    }


 
    private RBTNode search(RBTNode x, String key) {
        if (x == null) {
            System.out.print("key mising");
            return x;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return search(x.left, key);
        else if (cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    public RBTNode search(String key) {
        return search(root, key);
    }

    
    private RBTNode iterativeSearch(RBTNode x, String key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x;
        }

        return x;
    }

    public RBTNode iterativeSearch(String key) {
        return iterativeSearch(root, key);
    }

   
    private RBTNode minimum(RBTNode tree) {
        if (tree == null)
            return null;

        while (tree.left != null)
            tree = tree.left;
        return tree;
    }


    
    private RBTNode maximum(RBTNode tree) {
        if (tree == null)
            return null;

        while (tree.right != null)
            tree = tree.right;
        return tree;
    }

    public String maximum() {
        RBTNode p = maximum(root);
        if (p != null)
            return p.key;

        return null;
    }


    private void leftRotate(RBTNode x) {
        RBTNode y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
        } else {
            if (x.parent.left == x)
                x.parent.left = y;
            else
                x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }


    private void rightRotate(RBTNode y) {
        
        RBTNode x = y.left;

        
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        
        x.parent = y.parent;

        if (y.parent == null) {
            this.root = x;          
        } else {
            if (y == y.parent.right)
                y.parent.right = x;    
            else
                y.parent.left = x;    
        }

       
        x.right = y;

       
        y.parent = x;
    }

    private void insertFixUp(RBTNode node) {
        RBTNode parent, gparent;

        
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);

            
            if (parent == gparent.left) {
               
                RBTNode uncle = gparent.right;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

               
                if (parent.right == node) {
                    RBTNode tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    
                RBTNode uncle = gparent.left;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                
                if (parent.left == node) {
                    RBTNode tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        
        setBlack(this.root);
    }

    
    private void insert(RBTNode node) {
        int cmp;
        RBTNode y = null;
        RBTNode x = this.root;

        
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        node.parent = y;
        if (y != null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        } else {
            this.root = node;
        }

       
        node.color = RED;

        
        insertFixUp(node);
    }


    public void insert(String key, String value) {
        if (search(key) != null) {
            System.out.println("already exist");
            return;
        }
        RBTNode node = new RBTNode(key, value, BLACK, null, null, null);

        
        if (node != null)
            insert(node);
    }


    private void deleteFixUp(RBTNode node, RBTNode parent) {
        RBTNode other;

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right == null || isBlack(other.right)) {
                        
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                   
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                   
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left == null || isBlack(other.left)) {
                      
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                   
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }

        if (node != null)
            setBlack(node);
    }

  
    private void delete(RBTNode node) {
        RBTNode child, parent;
        boolean color;

        
        if ((node.left != null) && (node.right != null)) {
           
            RBTNode replace = node;

            
            replace = replace.right;
            while (replace.left != null)
                replace = replace.left;

           
            if (parentOf(node) != null) {
                if (parentOf(node).left == node)
                    parentOf(node).left = replace;
                else
                    parentOf(node).right = replace;
            } else {
               
                this.root = replace;
            }

           
            child = replace.right;
            parent = parentOf(replace);
            
            color = colorOf(replace);

           
            if (parent == node) {
                parent = replace;
            } else {
              
                if (child != null)
                    setParent(child, parent);
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                deleteFixUp(child, parent);

            node = null;
            return;
        }

        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        
        color = node.color;

        if (child != null)
            child.parent = parent;

       
        if (parent != null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.root = child;
        }

        if (color == BLACK)
            deleteFixUp(child, parent);
        node = null;
    }

  
    public void delete(String key) {
        RBTNode node;
        if ((node = search(root, key)) != null) {
            delete(node);
        } else {
            System.out.println("error:key missing");
        }
    }


    public void load(RBTree rbTree) {
        try {
            File filename = new File("unix.txt");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line;
            line = br.readLine();
            while (line != null) {
                String key = line;
                line = br.readLine();
                String value = line;
                rbTree.put(key, value, rbTree);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void put(String key, String value, RBTree rbTree) {
        if (rbTree.search(key) == null) {
            insert(key, value);
        } else {
            rbTree.setvalue(rbTree.search(key), value);
        }
    }

    public static void main(String args[]) {
        RBTree tree = new RBTree();
        tree.insert("c", "e");
        tree.insert("b", "d");
        tree.insert("a", "s");
        tree.insert("h", "f");
        tree.insert("d", "e");
       
        System.out.println(tree.search("a").value);
        tree.put("a", "c", tree);
        tree.delete("a");
        tree.dump();

        System.out.println("please enter the order:'INSERT','PUT','GET','DEL','LOAD','DUMP'，'END'");
        Scanner scanner2 = new Scanner(System.in);
        String order = scanner2.next();

        while (!order.equals("END")) {
            switch (order) {
                case "INSERT": {
                    System.out.println("key,plz");
                    Scanner scanner3 = new Scanner(System.in);
                    String thekey = scanner3.next();
                    System.out.println("value,plz");
                    Scanner scanner4 = new Scanner(System.in);
                    String value = scanner4.next();
                    tree.insert(thekey, value);
                    break;
                }
                case "PUT": {
                    System.out.println("key,plz");
                    Scanner scanner3 = new Scanner(System.in);
                    String thekey = scanner3.next();
                    System.out.println("value,plz");
                    Scanner scanner4 = new Scanner(System.in);
                    String value = scanner4.next();
                    tree.put(thekey, value, tree);
                    break;
                }
                case "GET": {
                    System.out.println("key,plz");
                    Scanner scanner3 = new Scanner(System.in);
                    String thekey = scanner3.next();
                    System.out.println(tree.search(thekey).value);
                    break;
                }
                case "DEL": {
                    System.out.println("key,plz");
                    Scanner scanner3 = new Scanner(System.in);
                    String thekey = scanner3.next();
                    tree.delete(thekey);
                    break;
                }
                case "LOAD": {
                    tree.load(tree);
                    break;
                }
                case "DUMP": {
                    tree.dump();
                    break;
                }
                default: {
                    System.out.println("please enter 'INSERT','PUT','GET','DEL','LOAD' or 'DUMP，'END'");
                    break;
                }
            }
            System.out.println("please enter the order:'INSERT','PUT','GET','DEL','LOAD','DUMP'，'END'");
            scanner2 = new Scanner(System.in);
            order = scanner2.next();
        }
    }
}
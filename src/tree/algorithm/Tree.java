package tree.algorithm;

public class Tree {

    /***
     * 后序遍历
     * 
     * @param node
     */
    public static void postTravelTree(Node node) {
        if (node == null) {
            return;
        }
        postTravelTree(node.left);
        postTravelTree(node.right);
        System.out.print(node.data);
    }

    /***
     * 前序遍历
     * 
     * @param node
     */
    public static void preTravelTree(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data);
        preTravelTree(node.left);
        preTravelTree(node.right);
    }

    /***
     * 中序遍历
     * 
     * @param node
     */
    public static void midTravelTree(Node node) {
        if (node == null) {
            return;
        }
        midTravelTree(node.left);
        System.out.print(node.data);
        midTravelTree(node.right);
    }

    /***
     * 创建树通过前序和中序序列
     * 
     * @param preTravel 前序序列
     * @param midTravel 中序序列
     * @return
     */
    public static void createByPreMid(Node root, String preTravel, String midTravel) {
        if (preTravel.length() <= 0 || root == null) {
            return;
        }
        char newRoot = preTravel.charAt(0);
        root.data = newRoot;
        int mid = midTravel.indexOf(newRoot);

        String leftMidTravel = midTravel.substring(0, mid);
        String rightMidTravel = midTravel.substring(mid + 1);

        String leftPreTravel = preTravel.substring(1, leftMidTravel.length() + 1);
        String rightPreTravel = preTravel.substring(leftMidTravel.length() + 1);

        if (leftPreTravel.length() > 0) {
            Node left = new Node();
            root.left = left;
            createByPreMid(left, leftPreTravel, leftMidTravel);
        }
        if (rightPreTravel.length() > 0) {
            Node right = new Node();
            root.right = right;
            createByPreMid(right, rightPreTravel, rightMidTravel);
        }
    }

    /***
     * 创建树通过后序和中序序列
     * 
     * @param postTravel 后序序列
     * @param midTravel 中序序列
     * @return
     */
    public static void createByPostMid(Node root, String postTravel, String midTravel) {
        if (postTravel.length() <= 0 || root == null) {
            return;
        }
        char newRoot = postTravel.charAt(postTravel.length() - 1);
        root.data = newRoot;
        int mid = midTravel.indexOf(newRoot);

        String leftMidTravel = midTravel.substring(0, mid);
        String rightMidTravel = midTravel.substring(mid + 1);
        String leftPostTravel = postTravel.substring(0, leftMidTravel.length());
        String rightPostTravel = postTravel.substring(leftMidTravel.length(), postTravel.length() - 1);
        if (leftPostTravel.length() > 0) {
            Node left = new Node();
            root.left = left;
            createByPostMid(left, leftPostTravel, leftMidTravel);
        }
        if (rightPostTravel.length() > 0) {
            Node right = new Node();
            root.right = right;
            createByPostMid(right, rightPostTravel, rightMidTravel);
        }
    }

    public static void main(String[] args) {
        String preTravel = "ABDHLEKCFG";
        String midTravel = "HLDBEKAFCG";
        String postTravel = "LHDKEBFGCA";
        Node root = new Node();
        Tree.createByPreMid(root, preTravel, midTravel);
        Tree.postTravelTree(root);
        System.out.println();
        Node root1 = new Node();
        Tree.createByPostMid(root1, postTravel, midTravel);
        Tree.postTravelTree(root1);

    }
}

class Node {
    public Node left;
    public Node right;
    public char data;
}

package tree.algorithm;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;

/***
 * 树结构遍历应用：用lambda表达式，将子节点生产、节点消费、中断遍历等逻辑外置，使得遍历算法抽象，不依赖于具体的节点的结构
 * TreeUtil: TreeUtil.java.
 *
 * <br>==========================
 * <br> 创建时间：2018年1月9日
 * <br> 修改时间：2018年1月9日
 * <br> 版本：1.0
 * <br> JDK版本：1.8
 * <br>==========================
 */
public class TreeUtil {
    /****
     * 递归遍历
     * 
     * @param root 根节点
     * @param newRoot 当前根节点
     * @param producer 由父节点查询子节点的操作
     * @param consumer 对符合节点的业务（统计、收集等）
     */
    public static <T> void recursion(T root,T newRoot,BiFunction<T, T, List<T>> childsProducer, Consumer<T> nodeConsumer) {
        if (newRoot == null) {
            return;
        } else {
            nodeConsumer.accept(newRoot);
            List<T> childs = childsProducer.apply(root,newRoot);
            if (CollectionUtils.isNotEmpty(childs)) {
                for (T child : childs) {
                    recursion(root,child, childsProducer, nodeConsumer);
                }
            }

        }
        return;
    }
    
    /***
     * 非递归遍历
     * @param root 根节点
     * @param childsProducer 由父节点查询子节点的操作
     * @param nodeConsumer 对符合节点的业务（统计、收集等）
     */
    public static <T> void notRecursion(T root,Function<T,List<T>> childsProducer, Consumer<T> nodeConsumer) {
        if (root == null) {
            return;
        }
        ConcurrentLinkedQueue<T> nodes = new ConcurrentLinkedQueue<>();
        nodes.offer(root);
        while (CollectionUtils.isNotEmpty(nodes)) {
            T newRoot = nodes.poll();
            nodeConsumer.accept(newRoot);
            List<T> childs = childsProducer.apply(newRoot);
            if (CollectionUtils.isNotEmpty(childs)) {
                nodes.addAll(childs);
            }
        }
    }
    
    /***
     * 非递归遍历
     * @param root 根节点
     * @param childsProducer 由父节点查询子节点的操作
     * @param nodeConsumer 对符合节点的业务（统计、收集等）
     */
    public static <T> void notRecursion(T root,BiFunction<T, T, List<T>> childsProducer, Consumer<T> nodeConsumer) {
        if (root == null) {
            return;
        }
        ConcurrentLinkedQueue<T> nodes = new ConcurrentLinkedQueue<>();
        nodes.offer(root);
        while (CollectionUtils.isNotEmpty(nodes)) {
            T newRoot = nodes.poll();
            nodeConsumer.accept(newRoot);
            List<T> childs = childsProducer.apply(root,newRoot);
            if (CollectionUtils.isNotEmpty(childs)) {
                nodes.addAll(childs);
            }
        }
    }

    /***
     * 非递归遍历:可中断，predicate返回true则中断迭代，返回false则继续迭代
     * @param root 根节点
     * @param childsProducer 由父节点查询子节点的操作
     * @param nodeConsumer 对符合节点的业务（统计、收集等）
     */
    public static <T> void notRecursionWithInterrupt(T root,Function<T,T> childsProducer, Predicate<T> predicate) {
        if (root == null) {
            return;
        }
        ConcurrentLinkedQueue<T> nodes = new ConcurrentLinkedQueue<>();
        nodes.offer(root);
        while (CollectionUtils.isNotEmpty(nodes)) {
            T newRoot = nodes.poll();
            //返回true则中断遍历，false则继续
            if(predicate.test(newRoot)){
                return;
            }
            T child = childsProducer.apply(newRoot);
            if (child != null) {
                nodes.offer(child);
            }
        }
    }
    
}

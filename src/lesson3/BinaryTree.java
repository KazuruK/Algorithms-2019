package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/* Для удобства, в ходе комментирования буду опускать название и авторство книги из которой взял почти все алгоритмы.
 Буду оставлять комментарии с главой и страницей, где эти алгоритмы изложены в псевдокоде.
 Переписанные на java алгоритмы из "Алгоритмы построение и анализ 3 издание",
 авторы: Томас Кормен, Чарльз Лэйзерсон.*/

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {
    // добавил переменную родителя
    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }


/* Создал функции нахождения минимального и максимального элемента в дереве
 Они нужны для удаления элемента из дерева
 глава 12.2, стр 324*/

        Node<T> minimum() {
            Node<T> current = this;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        Node<T> maximum() {
            Node<T> current = this;
            while (current.right != null) {
                current = current.right;
            }
            return current;
        }

// Сгенерировал equals() и hashCode()

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value) &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right) &&
                    Objects.equals(parent, node.parent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right, parent);
        }
    }

    private Node<T> root = null;

    private int size = 0;

    // исправил с наличием переменной под родителя
    @Override
    public boolean add(T t) {
        Node<T> closest = findNearest(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
            newNode.parent = closest;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
            newNode.parent = closest;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
/* Определяем функцию трансплант, которая заменяет поддерево,
 являющееся дочернем по отношению к поддереву, другим родителем.
 глава 12.3, стр 330.*/
    private void transplant(Node<T> to, Node<T> from) {
        if (to.parent == null) {
            root = from;
        } else if (to.equals(to.parent.left)) {
            to.parent.left = from;
        } else {
            to.parent.right = from;
        }
        if (from != null) {
            from.parent = to.parent;
        }
    }

/* Для реализации удаления в дереве переданного объекта, создадим функцию удаления Nod'а
 глава 12.3, стр 331.*/

    private boolean deleteNode(Node<T> node) {
        // Добавим в исходный алгоритм проверку на null
        if (node == null) return false;
        // Для случая отсутствия левого дочернего узла
        if (node.left == null) {
            transplant(node, node.right);
            // Левый существует, но нет правого
        } else if (node.right == null) {
            transplant(node, node.left);
            // Когда существуют оба дочерних узла
        } else {
            Node<T> y = node.right.minimum();
            if (y.parent != node) {
                // Если y не правый дочерний node
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
            // Если y правый дочерний узел node
            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
        }
        // уменьшаем размер дерева
        size--;
        return true;
    }

// Находим node соответствующий поданному объекту и используем deleteNode();
   /* Память: O(1)
    Сложность: O(lg n)*/

    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        Node<T> node = find(value);
        return deleteNode(node);

    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }
    // Реализация поиска следующего элемента в дереве.
// глава 12.2 стр 325.
    private Node<T> findNext(Node<T> x) {
        // Проверка что корень не null
        if (root == null) return null;
        // Если x = null, берется самое младшее значение дерева.
        if (x == null) return root.minimum();
        // Дальше алгоритм по книге
        // Наличие правого поддерева узла
        if (x.right != null) {
            return x.right.minimum();
        }
        /* Если правое поддерево узла x пустое и у x есть следующий за ним y,
         то y является наименьшим предком x*/
        Node<T> y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }
    // find переписана в findNearest, для поиска ближайшего объекта
    private Node<T> findNearest(T value) {
        if (root == null) return null;
        return findNearest(root, value);
    }

    private Node<T> findNearest(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return findNearest(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return findNearest(start.right, value);
        }
    }
    // Написан find для нахождения определенного значения в дереве
    private Node<T> find(T value) {
        return find(root, value);
    }

    private Node<T> find(Node<T> node, T value) {
        while (node != null && !value.equals(node.value)) {
            if (value.compareTo(node.value) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> next;
        private Node<T> now = null;

        private BinaryTreeIterator() {
            // Добавьте сюда инициализацию, если она необходима
            next = findNext(null);
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
         /*
        Память: O(1)
        Сложность: O(1)
         */
        @Override
        public boolean hasNext() {
            // Просто проверяем что next не null.
            return next != null;
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
          /*
        Память: O(1)
        Сложность: O(lgn)
         */

        @Override
        public T next() {
            // Переназначаем переменные, таким образом делая шаг вперед.
            // С помощью ранее написанного findNext ищем следующий next.
            now = next;
            // Если "нынешнего" элемента не существует, бросаем исключение
            if (now == null) throw new NoSuchElementException();
            next = findNext(now);
            return now.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            if (now == null) throw new IllegalStateException();
            BinaryTree.this.deleteNode(now);
            now = null;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}

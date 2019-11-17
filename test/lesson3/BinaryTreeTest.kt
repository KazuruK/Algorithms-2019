package lesson3

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        assertEquals(0, tree.size)
        assertFalse(tree.contains(5))
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

//    @Test
//    @Tag("Example")
//    fun testAddKotlin() {
//        testAdd { createKotlinTree() }
//    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val binarySet = create()
            assertFalse(binarySet.remove(42))
            for (element in list) {
                binarySet += element
            }
            val originalHeight = binarySet.height()
            val toRemove = list[random.nextInt(list.size)]
            val oldSize = binarySet.size
            assertTrue(binarySet.remove(toRemove))
            assertEquals(oldSize - 1, binarySet.size)
            println("Removing $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
            assertTrue(
                binarySet.height() <= originalHeight,
                "After removal of $toRemove from $list binary tree height increased"
            )
        }
    }

    // Мой тест отсюда ----------------------------------------------------------------------------------------------
    private fun testRemoveSecond(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        val list = mutableListOf<Int>()
        for (i in 1..100) {
            list.add(random.nextInt(100))
        }
        list.add(43, 43)
        list.add(1, 1)
        list.add(99, 99)
        // Создаем пустое бинарное дерево
        val binarySet = create()
        // Проверяем что поиск заданного элемента выводит False (т.к. дерево пустое)
        assertFalse(binarySet.remove(random.nextInt(100)))
        // Заносим элементы из списка в дерево
        for (element in list) {
            binarySet += element
        }
        // Самостоятельно (без рандомного цикла) добавил пару элементов, для проверки

        val originalHeight = binarySet.height()
        // Переменная рандомной цели для удаления
        val toRemove = list[random.nextInt(list.size)]
        var oldSize = binarySet.size

        // Проверка удаления 43
        assertTrue(binarySet.remove(list[43]))
        oldSize--
        assertEquals(oldSize, binarySet.size)
        println("Removing 43 from $list My own test")
        // Удалилось ли?
        assertEquals(
            false, list[43] in binarySet,
            "43 should be not in tree"
        )
        // Проверка удаления 1
        assertTrue(binarySet.remove(list[1]))
        oldSize--
        assertEquals(oldSize, binarySet.size)
        println("Removing 1 from $list My own test")
        // Удалилось ли?
        assertEquals(
            false, list[1] in binarySet,
            "1 should be not in tree"
        )
        // Проверка удаления 99
        assertTrue(binarySet.remove(list[99]))
        oldSize--
        assertEquals(oldSize, binarySet.size)
        println("Removing 99 from $list My own test")
        // Удалилось ли?
        assertEquals(
            false, list[99] in binarySet,
            "99 should be not in tree"
        )
        // Провиряем что после всех удалений дерево было изменено
        assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")

    }

    // Заканчивается тут ------------------------------------------------------------------------------------------------
//    @Test
//    @Tag("Normal")
//    fun testRemoveKotlin() {
//        testRemove { createKotlinTree() }
//    }

    // Запуск моего теста
    @Test
    @Tag("Normal")
    fun testRemoveJavaSecond() {
        testRemoveSecond { createJavaTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
        }
    }

    // Мой тест начинается тут --------------------------------------------------------------------------------------
    private fun testIteratorSecond(create: () -> CheckableSortedSet<Int>) {

        val list = mutableListOf<Int>()
        val treeSet = TreeSet<Int>()
        list.add(43)
        list.add(3)
        list.add(76)
        list.add(9)
        list.add(12)
        list.add(1)

        val binarySet = create()
        // Пустое дерево возвращает false на hasNext
        assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")

        // Заносим элементы из списка в treeSet и наше дерево
        for (element in list) {
            treeSet += element
            binarySet += element
        }

        // Проверка паралельного обхода treeSet и бинарного дерева,
        // оба должны одинаковое кол-во раз на next() возвращать значение.
        val treeIt = treeSet.iterator()
        val binaryIt = binarySet.iterator()
        println("Обход $list")
        while (treeIt.hasNext()) {
            assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
        }
        // Создаем два одинаковых итератора для дерева
        val iterator1 = binarySet.iterator()
        val iterator2 = binarySet.iterator()
        println("Проверка что при вызове Next оба итератора возвращают одинаковые значения")

        // hasNext call should not affect iterator position
        while (iterator1.hasNext()) {
            assertEquals(
                iterator2.next(), iterator1.next(),
                "Call of iterator.hasNext() changes its state while iterating $treeSet"
            )
        }
    }
    // Заканчивается тут --------------------------------------------------------------------------------------------

//    @Test
//    @Tag("Normal")
//    fun testIteratorKotlin() {
//        testIterator { createKotlinTree() }
//    }

    // Запуск моего теста
    @Test
    @Tag("Normal")
    fun testIteratorJavaSecond() {
        testIteratorSecond { createJavaTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            assertEquals(
                0, counter,
                "Iterator.remove() of $toRemove from $list changed iterator position: " +
                        "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
            )
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        }
    }

    // Мой тест начинается тут ----------------------------------------------------------------------------------------
    private fun testIteratorRemoveSecond(create: () -> CheckableSortedSet<Int>) {
        // Создаем лист из которого будем брать значения
        val list = mutableListOf<Int>()
        list.add(3)
        list.add(99)
        list.add(12)
        list.add(0)
        list.add(1, 56)

        // Создание treeSet и бинарного дерева
        val treeSet = TreeSet<Int>()
        val binarySet = create()

        // Добавление в treeSet и binarySet элементов из list
        for (element in list) {
            treeSet += element
            binarySet += element
        }
        // Удаление из treeSet 56
        treeSet.remove(list[1])
        println("Removing 56 from $list")
        // Создаем итератор и счетчик для бинарного дерева
        val iterator = binarySet.iterator()
        var counter = binarySet.size

        // Осуществляем перебор итератором дерева с удаленикм 56
        // Таким образом проверяем что не возникнет ошибки при удалении элемента во время обхода
        while (iterator.hasNext()) {
            val element = iterator.next()
            counter--
            print("$element ")
            if (element == list[1]) {
                iterator.remove()
            }
        }
        // Проверяем что после прохода, кол-во оставшихся шагов равно 0,
        // Отсюда следует что удаление работает без нарушения порядка прохода итератором дерева
        assertEquals(
            0, counter,
            "Iterator.remove() of 56 from $list changed iterator position: " +
                    "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
        )

        println()
        // Утверждаемся что после удаления 56 из treeSet и нами написанного remov'а для дерева,
        // treeSet и binarySet идентичны как по элементам,
        assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of 56 from $list")
        // Так и по размеру коллекций
        assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of 56 from $list")
        for (element in list) {
            val inn = element != list[1]
            // Проверяем что элемент, взятый из list, если он не является 56 - находится в дереве
            // А если является, то не находится
            assertEquals(
                inn, element in binarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        // Проверка на то что дерево изменилось после удаления
        assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        println(binarySet.checkInvariant())
    }
    // Заканчивается тут ----------------------------------------------------------------------------------------------

//    @Test
//    @Tag("Hard")
//    fun testIteratorRemoveKotlin() {
//        testIteratorRemove { createKotlinTree() }
//    }

    // Запуск моего теста
    @Test
    @Tag("Hard")
    fun testIteratorRemoveJavaSecond() {
        testIteratorRemoveSecond { createJavaTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}
package lesson2

import org.junit.jupiter.api.Tag
import kotlin.test.Test

class AlgorithmsTestsJava : AbstractAlgorithmsTests() {
/*Undone*/
    @Test
    @Tag("Easy")
    fun testOptimizeBuyAndSell() {
        optimizeBuyAndSell { JavaAlgorithms.optimizeBuyAndSell(it) }
    }
/**Done**/
    @Test
    @Tag("Easy")
    fun testJosephTask() {
        josephTask { menNumber, choiceInterval -> JavaAlgorithms.josephTask(menNumber, choiceInterval) }
    }
/*Undone*/
    @Test
    @Tag("Normal")
    fun testLongestCommonSubstring() {
        longestCommonSubstring { first, second -> JavaAlgorithms.longestCommonSubstring(first, second) }
    }
/**Done**/
    @Test
    @Tag("Easy")
    fun testCalcPrimesNumber() {
        calcPrimesNumber { JavaAlgorithms.calcPrimesNumber(it) }
    }
/*Undone*/
    @Test
    @Tag("Hard")
    fun testBaldaSearcher() {
        baldaSearcher { inputName, words -> JavaAlgorithms.baldaSearcher(inputName, words) }
    }
}
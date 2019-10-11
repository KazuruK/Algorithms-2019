package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
/** T(n)=O(n), R(n)=O(1) **/
 public static void main(String[] args) {
        sortTemperatures("input//temp_in1.txt", "output//outputName.txt");
    }

    static public void sortTemperatures(String inputName, String outputName) {
        Map<Double, Integer> tempWithCounts = parseSortTemperaturesFile(inputName);
        writeMapTempInFile(tempWithCounts, outputName);
    }



    private static Map<Double, Integer> parseSortTemperaturesFile(String inputFileName){

        try (BufferedReader input = new BufferedReader(new FileReader(new File(inputFileName)))) {

            String temperaturesAtString = input.readLine();
            Map<Double, Integer> quantityToUniqueTemperature = new TreeMap<>();

            while (temperaturesAtString != null) {

                //System.out.println(temperaturesAtString + "ЗНАЧЕНИЯ ДОЛЖНЫ БЫТЬ ТУТАЧКИ");
                Double temperatures = Double.parseDouble(temperaturesAtString);

                if (quantityToUniqueTemperature.get(temperatures) == null) {
                    quantityToUniqueTemperature.put(temperatures, 1);
                } else {
                    quantityToUniqueTemperature.merge(temperatures, 1, Integer::sum);
                }

                temperaturesAtString = input.readLine();
            }
            return quantityToUniqueTemperature;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeMapTempInFile(Map<Double, Integer> mapTemp, String outputFileName){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))){

            for (Double key : mapTemp.keySet()) {
                for (int i = 0; i < mapTemp.get(key); i++) {
                    writer.write(key.toString() + System.lineSeparator());
                    //System.out.println(key.toString());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
 /** T(n)=O(n), R(n)=O(n)**/
   /*public static void main(String[] args) {
        sortSequence("input//seq_in3.txt", "output//outputName.txt");
    }*/

    static public void sortSequence(String inputName, String outputName) {

        writeListNumInFile(parseSortSequenceFile(inputName), outputName);

    }


    private static List<Integer> parseSortSequenceFile(String inputFileName){

        try (BufferedReader input = new BufferedReader(new FileReader(new File(inputFileName)))) {

            String numAtString = input.readLine();
            List<Integer> numbers = new ArrayList<>();
            Map<Integer,Integer> numbersWithCounts = new HashMap<>();

            while (numAtString != null) {
                Integer num = Integer.parseInt(numAtString);
                numbers.add(num);

                if (numbersWithCounts.get(num) == null) {
                    numbersWithCounts.put(num, 1);
                } else {
                    numbersWithCounts.merge(num, 1, Integer::sum);
                }

                numAtString = input.readLine();
            }
            int maxValue = 0;
            int highestValueKey = -1;

            for (Map.Entry<Integer, Integer> entry : numbersWithCounts.entrySet()) {
                int value = entry.getValue();
                int key =  entry.getKey();

                if (value > maxValue) { maxValue = value; highestValueKey = key;}
                if (value == maxValue) {if (highestValueKey > key) highestValueKey = key;}
            }

            //Попробовать итерироваться
            List<Integer> newNumbers = new ArrayList<>();

            for (int i : numbers) {
                if (i != highestValueKey) newNumbers.add(i);
            }
            for (int i = 0; i < maxValue; i++) {
                newNumbers.add(highestValueKey);
            }
            return newNumbers;

            /**Итератор работает в среднем на 20-40 миллисекунд дольше

             /*Iterator<Integer> i = numbers.iterator();
             while (i.hasNext()) {
             int s = i.next(); // must be called before you can call i.remove()
             // Do something
             if(s == highestValueKey)
             i.remove();
             }

             for (int j = 0; j < maxValue; j++) {
             numbers.add(highestValueKey);
             }
             Stopwatch.stop("general");
             return numbers;**/


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeListNumInFile(List <Integer> list, String outputFileName){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))){

            for (int i : list) {
                writer.write(i + System.lineSeparator());
                //System.out.println(i);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */

/** T(n)=O(n log n), R(n)=O(1) **/
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {

        int i = 0;

        while (second[i] == null){
            second[i] = first[i];
            i++;
        }

        Arrays.sort(second);

    }
}

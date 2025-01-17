@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import lesson3.task1.digitNumber
import java.lang.Integer.max
import java.lang.StringBuilder


// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        if (!line.startsWith('_')) {
            writer.write(line)
            writer.newLine()
        }
    }
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> = TODO()


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLength = 0
    File(inputName).bufferedReader().forEachLine { line ->
        val clearLineLength = line.dropWhile { it == ' ' }.dropLastWhile { it == ' ' }.length
        maxLength = max(clearLineLength, maxLength)
    }
    File(outputName).bufferedWriter().use { writer ->
        File(inputName).bufferedReader().forEachLine { line ->
            val clearLine = line.dropWhile { it == ' ' }.dropLastWhile { it == ' ' }
            writer.write(" ".repeat((maxLength - clearLine.length) / 2) + clearLine + '\n')
        }
    }
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    var maxLength = 0
    File(inputName).bufferedReader().forEachLine { line ->
        val clearLineLength = line
            .dropWhile { it == ' ' }
            .dropLastWhile { it == ' ' }
            .replace(Regex(" {2,}"), " ")
            .length
        maxLength = max(clearLineLength, maxLength)
    }
    File(outputName).bufferedWriter().use { writer ->
        File(inputName).bufferedReader().forEachLine { line ->
            val clearLine = line
                .dropWhile { it == ' ' }
                .dropLastWhile { it == ' ' }
                .replace(Regex(" {2,}"), " ")
            val words = clearLine.split(' ')
            if (words.size < 2)
                writer.write(clearLine)
            else {
                val totalSpaces = maxLength - clearLine.count { it != ' ' } // Кол-во пробелов между всеми словами
                val defaultSpaces = totalSpaces / (words.size - 1) // Кол-во пробелов между двумя последними словами
                val additionalSpaces = totalSpaces % (words.size - 1)
                for (i in 0 until words.lastIndex) {
                    writer.write(words[i] + " ".repeat(defaultSpaces))
                    if (i < additionalSpaces)
                        writer.write(" ")
                }
                writer.write(words.last())
            }
            writer.newLine()
        }
        writer.close()
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val topWords = mutableMapOf<String, Int>()
    val word = StringBuilder()
    val reader = File(inputName).bufferedReader()
    do {
        val value = reader.read()
        if (value == -1)
            continue
        val symbol = value.toChar().lowercaseChar()
        if (symbol in 'а'..'я' || symbol == 'ё' || symbol in 'a'..'z')
            word.append(symbol)
        else if (word.isNotEmpty()) {
            topWords[word.toString()] = topWords[word.toString()]?.plus(1) ?: 1
            word.clear()
        }
    } while (value != -1)
    reader.close()

    val res = mutableMapOf<String, Int>()
    var count = 0
    var prevNumber = 0
    for ((key, value) in topWords.toList().sortedByDescending { it.second }) {
        if (count >= 20 && prevNumber > value)
            break
        res[key] = value
        prevNumber = value
        count++
    }
    return res
}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).bufferedReader()
    var value = reader.read()
    while (value != -1) {
        val symbol = value.toChar()
        val stringToWrite = when {
            symbol.uppercaseChar() in dictionary.keys -> dictionary[symbol.uppercaseChar()]!!
            symbol.lowercaseChar() in dictionary.keys -> dictionary[symbol.lowercaseChar()]!!
            else -> symbol.toString()
        }
        if (symbol.isUpperCase() && stringToWrite != "")
            writer.write(stringToWrite[0].uppercase() + stringToWrite.substring(1).lowercase())
        else
            writer.write(stringToWrite.lowercase())
        value = reader.read()
    }
    reader.close()
    writer.close()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val res = (lhv * rhv).toString()
    val width = res.length + 1
    val writer = File(outputName).bufferedWriter()
    writer.write(" ".repeat(width - digitNumber(lhv)) + lhv.toString() + '\n')
    writer.write('*' + " ".repeat(width - digitNumber(rhv) - 1) + rhv.toString() + '\n')
    writer.write("-".repeat(width) + '\n')
    val multipliers = rhv.toString().reversed().map { it - '0' }
    for (i in multipliers.indices) {
        val multiplicationStr = (multipliers[i] * lhv).toString()
        val length = multiplicationStr.length
        if (i == 0)
            writer.write(" ".repeat(width - length) + multiplicationStr)
        else
            writer.write('+' + " ".repeat(width - length - 1 - i) + multiplicationStr)
        writer.newLine()
    }
    writer.write("-".repeat(width) + '\n')
    writer.write(" $res")
    writer.close()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    // 1) Обработаем 3 первых строки
    val quotient = lhv / rhv // Частное без остатка

    // 1.1) Вычислим вычитаемое и уменьшаемое
    var subtrahend = quotient.toString().take(1).toInt() * rhv // Вычитаемое (пример: x - subtrahend)
    var minuend = 0 // Уменьшаемое (пример: minuend - x)

    // Если subtrahend != 0, то берём НАИМЕНЬШИЙ возможный minuend
    if (subtrahend != 0)
        for (digit in lhv.toString().map { it.digitToInt() }) {
            minuend = minuend * 10 + digit
            if (minuend >= subtrahend)
                break
        }
    // Если subtrahend == 0, то берём НАИБОЛЬШИЙ возможный minuend
    else minuend = lhv

    // 1.2) Введём переменный отступ и длину последний индекс lhv, пошедший в minuend
    var margin = 0 // Отступ, накапливающийся в процессе сдвига чисел вправо
    // Эта величина понадобится на 2 этапе для того, чтобы правильно считывать следующий разряд из lhv:
    val startIndex = digitNumber(minuend) - 1

    // 1.3) Выведем первые 3 строки
    val writer = File(outputName).bufferedWriter()
    if (digitNumber(subtrahend) == digitNumber(minuend)) {
        writer.write(" $lhv | $rhv\n") // Требуется первый пробел над минусом
        margin += 1 // Тот самый пробел над минусом
        // В строке ниже 3 для " | "
        writer.write("-$subtrahend" + " ".repeat(digitNumber(lhv) - digitNumber(subtrahend) + 3) + "$quotient\n")
        writer.write("-".repeat(1 + digitNumber(subtrahend))) // 1 дефис ставим под минус
    }
    // Уменьшаемое длиннее вычитаемого => минус слева не выпирает
    else {
        writer.write("$lhv | $rhv\n") // Первого пробела над минусом не требуется
        // Требуется сдвинуть выражение "-$subtrahend" к младшему разряду minuend
        val spacesBeforeSubtrahend = digitNumber(minuend) - (1 + digitNumber(subtrahend)) // 1 для минуса
        writer.write(
            " ".repeat(spacesBeforeSubtrahend) + "-$subtrahend" +
                    " ".repeat(digitNumber(lhv) - digitNumber(minuend) + 3) +
                    "$quotient\n"
        )
        writer.write("-".repeat(digitNumber(minuend)))
    }
    writer.newLine()

    // 1.4) Найдём разность, которая станет предыдущей для первой итерации цикла
    var prevDifference = minuend

    // 2) Циклически выведем все остальные строки, кроме последней
    for (i in 1 until digitNumber(quotient)) {
        // Найдём разность _предыдущих_ уменьшаемого и вычитаемого
        val difference = minuend - subtrahend

        // Вычислим отступ перед _новым_ уменьшаемым на основе предыдущего отступа, который остаётся неизменным
        /** Примечание: margin на 1 больше, когда предыдущая разность была равна 0, т.к. **\
        \** фактически на предыдущем шаге было выведено 2 цифры, первая из которых -- 0  **/
        margin +=
            if (prevDifference == 0) 1
            else digitNumber(minuend) - digitNumber(difference)

        // Возьмём следующий разряд из lhv, чтобы приписать его справа к разности
        val nextDigit = lhv.toString()[startIndex + i].digitToInt()

        // ВЫВЕДЕМ новое уменьшаемое до его вычисления
        // (так мы сможем сделать вывод правильным в случае, если difference = 0)
        writer.write(" ".repeat(margin) + "$difference$nextDigit\n")

        // Вычислим новые уменьшаемое и вычитаемое
        minuend = difference * 10 + nextDigit
        subtrahend = quotient.toString()[i].digitToInt() * rhv

        // Вычислим кол-во пробелов перед выражением "-$subtrahend" ОТНОСИТЕЛЬНО minuend
        /**  Примечание: эта величина на 1 больше, **\
        \** если на этой итерации разность равна 0 **/
        var spacesBeforeSubtrahend =
            if (digitNumber(minuend) == digitNumber(subtrahend))
                -1 // Так мы компенсируем margin в случае, если разрядов в уменьшаемом и вычитаемом поровну
            else
                digitNumber(minuend) - (1 + digitNumber(subtrahend)) // 1 для минуса
        if (difference == 0) spacesBeforeSubtrahend += 1

        // ВЫВЕДЕМ новое вычитаемое
        writer.write(" ".repeat(margin + spacesBeforeSubtrahend) + "-$subtrahend\n")

        // ВЫВЕДЕМ подчёркивание
        if (digitNumber(minuend) == digitNumber(subtrahend))
            writer.write(" ".repeat(margin + spacesBeforeSubtrahend) + "-".repeat(1 + digitNumber(subtrahend)))
        else
            writer.write(" ".repeat(margin) + "-".repeat(digitNumber(minuend)))
        writer.newLine()

        // Текущая разность становится прошлой разностью для следующей итерации
        prevDifference = difference
    }

    // 3) Выведем последнюю строку
    margin +=
        if (prevDifference == 0) 1
        else digitNumber(minuend) - digitNumber(lhv % rhv)
    writer.write(" ".repeat(margin) + "${lhv % rhv}")

    writer.close()
}
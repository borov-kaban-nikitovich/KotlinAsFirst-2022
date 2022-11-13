@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException
import java.lang.Integer.max

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

private val MONTHS = listOf(
    "января",
    "февраля",
    "марта",
    "апреля",
    "мая",
    "июня",
    "июля",
    "августа",
    "сентября",
    "октября",
    "ноября",
    "декабря"
)
private val DAYSINMONTH = mapOf(
    1 to 31,
    2 to 29,
    3 to 31,
    4 to 30,
    5 to 31,
    6 to 30,
    7 to 31,
    8 to 31,
    9 to 30,
    10 to 31,
    11 to 30,
    12 to 31
)
private val ARABICNUMBERS = mapOf(
    "M" to 1000,
    "CM" to 900,
    "D" to 500,
    "CD" to 400,
    "C" to 100,
    "XC" to 90,
    "L" to 50,
    "XL" to 40,
    "X" to 10,
    "IX" to 9,
    "V" to 5,
    "IV" to 4,
    "I" to 1
)

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

fun isNumber(x: String): Boolean = x.all { it.isDigit() } && x != ""

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3)
        return ""
    if (!isNumber(parts[0]) || parts[1] !in MONTHS || !isNumber(parts[2]))
        return ""
    val day = parts[0].toInt()
    val monthStr = parts[1]
    val month = MONTHS.indexOf(monthStr) + 1
    val year = parts[2].toInt()
    if (monthStr == "февраля") {
        val isLeap = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
        if (!isLeap && day > 28)
            return ""
    }
    if (day > DAYSINMONTH[month]!!)
        return ""
    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3 || parts.any { !isNumber(it) })
        return ""
    val (day, month, year) = parts.map { it.toInt() }
    if (month !in 1..12)
        return ""
    if (month == 2) {
        val isLeap = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
        if (!isLeap && day > 28)
            return ""
    }
    if (day > DAYSINMONTH[month]!!)
        return ""
    return "$day ${MONTHS[month - 1]} $year"
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String =
    if (phone.matches(Regex("""\+?[\d -]*(\([\d -]+\))?[\d -]*""")) && phone != "+")
        phone.filter { it in '0'..'9' || it == '+' }
    else
        ""

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(' ')
    var bestJump = -1
    for (part in parts) {
        if (isNumber(part))
            bestJump = max(bestJump, part.toInt())
        else if (part != "%" && part != "-")
            return -1
    }
    return bestJump
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+ %*[+-]? )*\d+ %*[+-]?""")))
        return -1
    val parts = jumps.split(' ')
    var bestJump = -1
    for (i in parts.indices step 2) {
        if ('+' in parts[i + 1])
            bestJump = max(bestJump, parts[i].toInt())
    }
    return bestJump
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int =
    if (expression.matches(Regex("""\d+( [+-] \d+)*"""))) {
        val parts = expression.split(' ')
        var res = parts[0].toInt()
        for (i in 1 until parts.size step 2)
            if (parts[i] == "+")
                res += parts[i + 1].toInt()
            else
                res -= parts[i + 1].toInt()
        res
    } else
        throw IllegalArgumentException()

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.split(' ')
    var strIndex = 0
    for (partIndex in 0 until parts.size - 1) {
        if (parts[partIndex].lowercase() == parts[partIndex + 1].lowercase())
            return strIndex
        strIndex += parts[partIndex].length + 1
    }
    return -1
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String =
    if (description.matches(Regex("""([^\s;]+ \d+(\.\d+)?; )*[^\s;]+ \d+(\.\d+)?"""))) {
        var maxCost = 0.0
        var maxName = ""
        for (product in description.split("; ").reversed()) {
            val name = product.split(' ')[0]
            val cost = product.split(' ')[1].toDouble()
            if (cost >= maxCost) {
                maxCost = cost
                maxName = name
            }
        }
        maxName
    } else ""

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int =
    if (roman.matches(Regex("""M*(CM|DC{0,3}|CD|C{0,3})(XC|LX{0,3}|XL|X{0,3})(IX|VI{0,3}|IV|I{0,3})""")) &&
        roman.isNotEmpty()
    ) {
        val singleCharNumbers =
            ARABICNUMBERS.filter { it.key.length == 1 }.toList().sortedByDescending { it.second }.map { it.first }
        val doubleCharNumbers =
            ARABICNUMBERS.filter { it.key.length == 2 }.toList().sortedByDescending { it.second }.map { it.first }
        var res = 0
        var i = 0
        outerCycle@ while (i < roman.length) {
            if (i < roman.length - 1)
                for (el in doubleCharNumbers)
                    if (el == roman.substring(i, i + 2)) {
                        res += ARABICNUMBERS[el]!!
                        i += 2
                        continue@outerCycle
                    }
            for (el in singleCharNumbers)
                if (el == roman[i].toString()) {
                    res += ARABICNUMBERS[el]!!
                    i += 1
                    continue@outerCycle
                }
        }
        res
    } else -1

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    // Проверим соответствие строки commands шаблону
    var openBracketCount = 0
    var closeBracketCount = 0
    for (command in commands)
        when (command) {
            '[' -> openBracketCount++
            ']' -> {
                if (openBracketCount == 0)
                    throw IllegalArgumentException()
                closeBracketCount++
            }

            !in "+-<>[] " -> throw IllegalArgumentException()
        }
    if (openBracketCount != closeBracketCount)
        throw IllegalArgumentException()

    // Пройдём по командам
    val conveyor = MutableList(cells) { 0 }
    var step = 0
    var i = cells / 2   // Индекс ячейки
    var j = 0           // Индекс команды
    while (step < limit && j in commands.indices) {
        when (commands[j]) {
            ' ' -> {}
            '>' -> i += 1
            '<' -> i -= 1
            '+' -> conveyor[i]++
            '-' -> conveyor[i]--
            '[' -> {
                if (conveyor[i] == 0) {
                    var openCount = 0
                    var closeCount = 0
                    while (openCount != closeCount || openCount == 0) {
                        if (commands[j] == '[')
                            openCount++
                        if (commands[j] == ']')
                            closeCount++
                        j++
                    }
                    j-- // Вернёмся к '['
                }
            }

            ']' -> {
                if (conveyor[i] != 0) {
                    var openCount = 0
                    var closeCount = 0
                    while (openCount != closeCount || closeCount == 0) {
                        if (commands[j] == '[')
                            openCount++
                        if (commands[j] == ']')
                            closeCount++
                        j--
                    }
                    j++ // Вернёмся к '['
                }
            }

            else -> throw IllegalArgumentException()
        }
        if (i < 0 || i >= cells)
            throw IllegalStateException()
        j++
        step++
    }
    return conveyor
}

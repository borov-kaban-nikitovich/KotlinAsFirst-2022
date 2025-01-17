@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая (2 балла)
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean =
    number / 1000 + number / 100 % 10 == number / 10 % 10 + number % 10

/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean =
    x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y1 - y2)


/**
 * Простая (2 балла)
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    // Если февраль
    if (month == 2) {
        val isLeap = year % 4 == 0 && year % 100 != 0 || year % 400 == 0 // Високосный ли год?
        return if (isLeap) 29 else 28
    }
    // Если не февраль
    return if (month <= 7) 30 + month % 2 else 31 - month % 2
}

/**
 * Простая (2 балла)
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean {
    val p1p2 = sqrt(sqr(x1 - x2) + sqr(y1 - y2)) // Расстояние между центрами окружностей
    return r1 + p1p2 <= r2
}

/**
 * Средняя (3 балла)
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    // Введём переменные для каждой стороны кирпича и каждой стороны отверстия
    var b1 = a
    var b2 = b
    var b3 = c
    var h1 = r
    var h2 = s
    // Отсортируем b1, b2, b3 по возрастанию
    var temp: Int
    if (b1 > b2) {
        temp = b1
        b1 = b2
        b2 = temp
    }
    if (b2 > b3) {
        temp = b2
        b2 = b3
        b3 = temp
    }
    if (b1 > b2) {
        temp = b1
        b1 = b2
        b2 = temp
    }
    // Отсортируем r, s по возрастанию
    if (h1 > h2) {
        temp = h1
        h1 = h2
        h2 = temp
    }
    return h1 >= b1 && h2 >= b2
}

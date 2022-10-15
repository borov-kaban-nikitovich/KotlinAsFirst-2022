@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.PI
import lesson1.task1.sqr

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int = if (n < 10) 1 else digitNumber(n / 10) + 1

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 1 || n == 2) return 1
    var x1 = 1
    var x2 = 1
    var x3 = 0
    for (i in 3..n) {
        x3 = x1 + x2
        x1 = x2
        x2 = x3
    }
    return x3
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv = n
    for (i in 2..sqrt(n.toFloat()).toInt()) {
        if (n % i == 0) {
            minDiv = i
            break
        }
    }
    return minDiv
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int = when {
    x == 1 -> 0
    x % 2 == 0 -> collatzSteps(x / 2) + 1
    else -> collatzSteps(3 * x + 1) + 1
}

/**
 * Напишем функцию, реализующую алгоритм Евклида, суть
 * которого заключается в поиске наибольшего общего делителя.
 *
 * Это поможет нам в решении сразу двух следующих задач
 */
fun gcd(m: Int, n: Int): Int = when {
    m == 0 -> n
    n == 0 -> m
    else -> gcd(n, m % n)
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int = m / gcd(m, n) * n

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = gcd(m, n) == 1

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var x = n / 10
    var ans = n % 10
    while (x != 0) {
        ans = ans * 10 + x % 10
        x /= 10
    }
    return ans
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val lastDigit = n % 10
    var x = n / 10 // Число n без последней цифры
    while (x != 0) {
        if (x % 10 != lastDigit) return true
        x /= 10
    }
    return false
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    val newX = x % (2 * PI) // 2 * PI -- период функции sin
    var exp = 3 // Показатель степени
    var ans = newX // Число, которое будем преобразовывать
    var nextNumber = newX.pow(exp) / factorial(exp)
    while (nextNumber > eps) {
        if (exp % 4 == 1)
            ans += nextNumber
        else
            ans -= nextNumber
        exp += 2
        nextNumber = newX.pow(exp) / factorial(exp)
    }
    return ans
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    val newX = x % (2 * PI) // 2 * PI -- период функции cos
    var exp = 2 // Показатель степени
    var ans = 1.0 // Число, которое будем преобразовывать
    var nextNumber = newX.pow(exp) / factorial(exp)
    while (nextNumber > eps) {
        if (exp % 4 == 0)
            ans += nextNumber
        else
            ans -= nextNumber
        exp += 2
        nextNumber = newX.pow(exp) / factorial(exp)
    }
    return ans
}

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var cnt = 0 // Счётчик количества цифр полученных квадратов
    var currentNum = 1
    var currentSqr: Int
    var digits = 0 // Количество цифр в текущем квадрате
    do {
        cnt += digits
        currentSqr = sqr(currentNum)
        digits = digitNumber(currentSqr)
        currentNum++
    } while (n !in cnt..cnt + digits)
    // Количество цифр справа от нужной цифры в текущем квадрате:
    val extraDigits = digits - (n - cnt)
    var ans = currentSqr
    for (i in 1..extraDigits)
        ans /= 10
    return ans % 10
}

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var cnt = 0 // Счётчик количества цифр полученных чисел Фибоначчи
    var currentNum = 1
    var currentFib: Int
    var digits = 0 // Количество цифр в текущем числе Фибоначчи
    do {
        cnt += digits
        currentFib = fib(currentNum)
        digits = digitNumber(currentFib)
        currentNum++
    } while (n !in cnt..cnt + digits)
    // Количество цифр справа от нужной цифры в текущем числе Фибоначчи:
    val extraDigits = digits - (n - cnt)
    var ans = currentFib
    for (i in 1..extraDigits)
        ans /= 10
    return ans % 10
}

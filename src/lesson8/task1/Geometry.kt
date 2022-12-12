@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.lang.IllegalArgumentException
import kotlin.math.*

// Урок 8: простые классы
// Максимальное количество баллов = 40 (без очень трудных задач = 11)

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая (2 балла)
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val x = center.distance(other.center) - (radius + other.radius)
        return if (x > 0) x else 0.0
    }

    /**
     * Тривиальная (1 балл)
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius

    override fun equals(other: Any?) = other is Circle && radius == other.radius && center == other.center

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + radius.hashCode()
        return result
    }
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    val length = begin.distance(end)
    val middle = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)

    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Средняя (3 балла)
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2)
        throw IllegalArgumentException("At least 2 points required")
    var maxSegment = Segment(points[0], points[1])
    for (i in points.indices)
        for (j in i + 1..points.lastIndex)
            if (points[i].distance(points[j]) > maxSegment.length)
                maxSegment = Segment(points[i], points[j])
    return maxSegment
}

/**
 * Простая (2 балла)
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(diameter.middle, diameter.length / 2)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя (3 балла)
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        if (angle == other.angle)
            throw IllegalArgumentException("Lines are parallel or equal")
        val x: Double
        val y: Double
        when {
            angle == PI / 2 -> {
                x = -b
                y = other.b / cos(other.angle)
            }

            other.angle == PI / 2 -> {
                x = -other.b
                y = b / cos(angle)
            }

            angle == 0.0 -> {
                y = b
                x = (b * cos(other.angle) - other.b) / sin(other.angle)
            }

            other.angle == 0.0 -> {
                y = other.b
                x = (other.b * cos(angle) - b) / sin(angle)
            }

            else -> {
                x = (other.b / cos(other.angle) - b / cos(angle)) / (tan(angle) - tan(other.angle))
                y = (x * sin(angle) + b) / cos(angle)
            }
        }
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя (3 балла)
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = when {
    s.begin == s.end ->
        throw IllegalArgumentException("More than 1 line is possible")

    s.begin.x == s.end.x ->
        Line(s.begin, PI / 2)

    else ->
        Line(s.begin, (atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x)) + PI) % PI)
}

/**
 * Средняя (3 балла)
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная (5 баллов)
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val segmentSlope = tan(lineBySegment(Segment(a, b)).angle)
    val middle = Segment(a, b).middle
    return when {
        segmentSlope == 0.0 -> Line(middle, PI / 2)
        // "% PI" в строке ниже предотвращает передачу аргументом числа, округлённого до PI,
        // в том случае, когда segmentSlope имеет очень большое значение
        segmentSlope > 0.0 -> Line(middle, (atan(-1 / segmentSlope) + PI) % PI)
        else -> Line(middle, atan(-1 / segmentSlope))
    }
}

/**
 * Средняя (3 балла)
 *
 * Задан список из n окружностей на плоскости.
 * Найти пару наименее удалённых из них; расстояние между окружностями
 * рассчитывать так, как указано в Circle.distance.
 *
 * При наличии нескольких наименее удалённых пар,
 * вернуть первую из них по порядку в списке circles.
 *
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.toSet().size < 2)
        throw IllegalArgumentException("At least 2 unique circles required")
    var leastDistantCircles = Pair(
        Circle(Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), 0.0),
        Circle(Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), 0.0),
    )
    for (i in circles.toList().indices)
        for (j in i + 1..circles.lastIndex)
            if (circles[i] != circles[j])
                if (leastDistantCircles.first.distance(leastDistantCircles.second) > circles[i].distance(circles[j]))
                    leastDistantCircles = Pair(circles[i], circles[j])
    return leastDistantCircles
}

/**
 * Сложная (5 баллов)
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    // Вычислим радиус по формуле R = AB * BC * AC / (4 * S)
    val ab = a.distance(b)
    val bc = b.distance(c)
    val ac = a.distance(c)
    val s = Triangle(a, b, c).area()
    val r = ab / 4 * bc / s * ac // Порядок изменён для предотвращения переполнения
    // Вычислим центр координатным методом (материал из Википедии)
    val d = 2 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y))
    val ux = ((sqr(a.x) + sqr(a.y)) * (b.y - c.y) +
            (sqr(b.x) + sqr(b.y)) * (c.y - a.y) +
            (sqr(c.x) + sqr(c.y)) * (a.y - b.y)) / d
    val uy = ((sqr(a.x) + sqr(a.y)) * (c.x - b.x) +
            (sqr(b.x) + sqr(b.y)) * (a.x - c.x) +
            (sqr(c.x) + sqr(c.y)) * (b.x - a.x)) / d
    return Circle(Point(ux, uy), r)
    /** Примечание: мы могли бы вычислить центр как точку пересечения серединных перпендикуляров
     ** к двум из сторон треугольника, но это значительно увеличило бы погрешность расчётов, что
     ** помешало бы использовать эту функцию при решении следующей задачи. **/
}

/**
 * Очень сложная (10 баллов)
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty())
        throw IllegalArgumentException("At least 1 point required")
    if (points.size == 1)
        return Circle(points[0], 0.0)
    val diameter = diameter(*points)
    // Окружность, построенная на отрезке между самыми удалёнными точками
    // множества, как на радиусе, точно содержит все точки множества
    var minCircle = Circle(diameter.begin, diameter.length)
    for (i in points.indices)
        for (j in i + 1..points.lastIndex)
            for (k in j + 1..points.lastIndex) {
                val circle = circleByThreePoints(points[i], points[j], points[k])
                if (circle.radius < minCircle.radius && points.all { circle.contains(it) })
                    minCircle = circle
            }
    val circleByTwoPoints = circleByDiameter(diameter)
    if (circleByTwoPoints.radius < minCircle.radius && points.all { circleByTwoPoints.contains(it) })
        minCircle = circleByTwoPoints
    return minCircle
    // Примечание: функция не проходит некоторые тесты из-за погрешностей вычислений. Чтобы увеличить их
    // точность, для предыдущих задач нужно реализовать менее очевидные решения, что усложнит их восприятие.
}
import kotlin.random.Random
import matrix.Matrix

operator fun Int.times(matrix: Matrix) : Matrix {
    val result = Matrix(matrix.rows, matrix.cols)
    for (i in 0 until matrix.rows) {
        for (j in 0 until matrix.cols) {
            result[i, j] = matrix[i, j] * this
        }
    }
    return result
}

fun main(args: Array<String>) {
    val matrix1 = Matrix(2,3)
    val matrix2 = Matrix(2,3)
    println("matrix1: $matrix1")
    println("matrix2: $matrix2")
    println("matrix1 == matrix2: ${matrix1 == matrix2}")
    matrix1[1, 2] = 4
    println("matrix1[1, 2]: ${matrix1[1, 2]}")
    println("matrix1: $matrix1")
    println("matrix1 == matrix2: ${matrix1 == matrix2}")
    println("matrix1 rows: ${matrix1.rows}, columns: ${matrix1.cols}\n")

    val matrix3 = Matrix(Array(4){ Array(3) {Random.nextInt(0, 10)}})
    val matrix4 = Matrix(Array(4){ Array(3) {Random.nextInt(0, 10)}})
    val matrix5 = Matrix(Array(3){ Array(3) {Random.nextInt(0, 10)}})
    println("matrix3: $matrix3")
    println("matrix4: $matrix4")
    println("matrix5: $matrix5")
    println("matrix3 + matrix4: ${matrix3 + matrix4}")
    println("matrix3 - matrix4: ${matrix3 - matrix4}")
    println("matrix4 * matrix5: ${matrix4 * matrix5}")
    println("4 * matrix5: ${4 * matrix5}")
    println("matrix5 * 4: ${matrix5 * 4}")
    println("matrix5.det: ${matrix5.det}")
}
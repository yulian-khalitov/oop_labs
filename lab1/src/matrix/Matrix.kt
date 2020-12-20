package matrix

class Matrix(val rows: Int, val cols: Int) {
    private val matrix = Array(rows) {Array(cols) {0} }

    constructor(matrix: Array<Array<Int>>) : this(matrix.size, matrix[0].size)  {
        val width = matrix[0].size
        for(i in 1 until matrix.size) {
            if (width != matrix[i].size) throw IllegalArgumentException("All rows of a matrix must be the same length")
        }
        matrix.copyInto(this.matrix)
    }

    private fun cofactor(p : Int, q: Int) : Matrix {
        val result = Matrix(rows-1, cols-1)
        var i = 0
        var j = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (row != p && col != q) {
                    result[i, j++] = this[row, col]
                    if (j == cols - 1) {
                        j = 0
                        i++
                    }
                }
            }
        }
        return result
    }

    val det : Int
        get(): Int {
            if (rows != cols) {
                throw IllegalArgumentException("The determinant can be calculated only for a square matrix")
            }
            if (rows == 1) return matrix[0][0]
            var d = 0
            var sign = 1
            for (i in 0 until cols) {
                val cofactor = this.cofactor(0, i)
                d += sign * matrix[0][i] * cofactor.det
                sign = -sign
            }
            return d
        }

    operator fun set(i: Int, j: Int, value: Int) {
        matrix[i][j] = value
    }

    operator fun get(i: Int, j: Int) : Int {
        return matrix[i][j]
    }

    operator fun plus(other: Matrix) : Matrix {
        if (rows != other.rows || cols != other.cols) {
            throw IllegalArgumentException("The sum can only be calculated for matrices of the same size")
        }
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i, j] = this[i, j] + other[i, j]
            }
        }
        return result
    }

    operator fun minus(other: Matrix) : Matrix {
        if (rows != other.rows || cols != other.cols) {
            throw IllegalArgumentException("The difference can only be calculated for matrices of the same size")
        }
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i, j] = this[i, j] - other[i, j]
            }
        }
        return result
    }

    operator fun times(other: Matrix) : Matrix {
        if (cols != other.rows)
            throw IllegalArgumentException(
                "The product can be calculated only if left matrix columns same as right matrix rows")
        val result = Matrix(rows, other.cols)
        for (i in 0 until rows) {
            for (j in 0 until other.cols) {
                for (k in 0 until cols) {
                    result[i, j] += this[i, k] * other[k, j]
                }
            }
        }
        return result
    }

    operator fun times(number: Int) : Matrix {
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i, j] = this[i, j] * number
            }
        }
        return result
    }

    override fun toString() : String {
        return matrix.contentDeepToString()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null ||
            other !is Matrix ||
            !matrix.contentDeepEquals(other.matrix)) return false
        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }
}

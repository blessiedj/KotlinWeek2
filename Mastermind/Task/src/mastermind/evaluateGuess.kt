package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val existingRightPositions = evaluateRightPositions(secret, guess)
    val existingWrongPositions = mutableListOf<Triple<Char, Int, Int>>()
    iloop@ for ((i, s) in secret.withIndex()) {
        jloop@ for ((j, g) in guess.withIndex()) {
            if (i == j && s == g) {
                continue@iloop
            } else if (i != j && s == g) {
                when {
                    isEvaluatedRight(s, i, existingRightPositions) -> continue@iloop
                    isEvaluatedRight(s, j, existingRightPositions) -> continue@jloop
                    isEvaluatedWrongI(s, i, existingWrongPositions) -> continue@iloop
                    isEvaluatedWrongJ(s, j, existingWrongPositions) -> continue@jloop
                    }
                if (!isAlreadyFoundChar(s, existingRightPositions, existingWrongPositions) ||
                        isNotEvaluatedWrongPosition(s, i, j, existingWrongPositions) ||
                        isNotEvaluatedRightPosition(s, i, j, existingRightPositions)) {
                    existingWrongPositions.add(Triple(s, i, j))
                    continue@iloop
                }
            }
        }
    }
    return Evaluation(existingRightPositions.size, existingWrongPositions.size)
}

fun evaluateRightPositions(secret: String, guess: String): List<Pair<Char, Int>> {
    val existingRightPositions = mutableListOf<Pair<Char, Int>>()
    iloop@ for ((i, s) in secret.withIndex()) {
        for ((j, g) in guess.withIndex()) {
            if (i == j && s == g) {
                existingRightPositions.add(s to i)
                continue@iloop
            }
        }
    }
    return existingRightPositions
}

fun isAlreadyFoundChar(s: Char,
                       existingRightPositions: List<Pair<Char, Int>>,
                       existingWrongPositions: List<Triple<Char, Int, Int>>): Boolean {
    var x = false
    var y = false
    existingRightPositions.listIterator().forEach { pair ->
        if (s == pair.first) {
            x = true
            return@forEach
        }
    }
    existingWrongPositions.listIterator().forEach { pair ->
        if (s == pair.first) {
            y = true
            return@forEach
        }
    }
    return x || y
}

fun isEvaluatedRight(s: Char, i: Int,
                     existingRightPositions: List<Pair<Char, Int>>): Boolean {
    var res = false;
    existingRightPositions.forEach { x ->
        if ((s == x.first && i == x.second)) {
            res = true
            return@forEach
        }
    }
    return res;
}

fun isEvaluatedWrongJ(s: Char, i: Int,
                     existingWrongPositions: List<Triple<Char, Int, Int>>): Boolean {
    var res = false;
    existingWrongPositions.forEach { x ->
        if ((s == x.first && i == x.third)) {
            res = true
            return@forEach
        }
    }
    return res;
}
fun isEvaluatedWrongI(s: Char, i: Int,
                      existingWrongPositions: List<Triple<Char, Int, Int>>): Boolean {
    var res = false;
    existingWrongPositions.forEach { x ->
        if ((s == x.first && i == x.second)) {
            res = true
            return@forEach
        }
    }
    return res;
}

fun isNotEvaluatedRightPosition(s: Char, i: Int, j: Int,
                                existingRightPositions: List<Pair<Char, Int>>): Boolean {
    var res = false;
    existingRightPositions.forEach { x ->
        if (s == x.first && i != x.second && j != x.second) {
            res = true
            return@forEach
        }
    }
    return res;
}

fun isNotEvaluatedWrongPosition(s: Char, i: Int, j: Int,
                                existingWrongPositions: List<Triple<Char, Int, Int>>): Boolean {
    var res = false;
    existingWrongPositions.forEach { x ->
        if (s == x.first && i != x.second && j != x.third) {
            res = true
            return@forEach
        }
    }
    return res;
}
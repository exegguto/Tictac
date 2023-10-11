import java.util.*

import kotlin.random.Random

class Player {
    val uniqueId = UUID.randomUUID().toString() // id юзера
    val move = IntArray(9) //ход

    fun stepMy(xy: Int, player2: Player): Boolean{
        println("stepMy $xy")
        move[xy] = 1
        player2.move[xy] = 2
        return testWin()
    }

    fun testWin() = when{
        (move[0] == 1 && move[1] == 1 && move[2] == 1) -> true
        (move[3] == 1 && move[4] == 1 && move[5] == 1) -> true
        (move[6] == 1 && move[7] == 1 && move[8] == 1) -> true
        (move[0] == 1 && move[3] == 1 && move[6] == 1) -> true
        (move[1] == 1 && move[4] == 1 && move[7] == 1) -> true
        (move[2] == 1 && move[5] == 1 && move[8] == 1) -> true
        (move[0] == 1 && move[4] == 1 && move[8] == 1) -> true
        (move[2] == 1 && move[4] == 1 && move[6] == 1) -> true
        else -> false
    }

    fun testXY(xy: Int) = (move[xy] != 0)

    fun stepAI(player2: Player): Int{
        var randomNumber = Random.nextInt(0, 9)
        while(testXY(randomNumber)) {
            randomNumber = Random.nextInt(0, 9)
            println("randomNumber $randomNumber")
        }
        move[randomNumber] = 1
        player2.move[randomNumber] = 2
        return randomNumber
    }

    fun testEnd(): Boolean {
        for(i in move) {
            if (i == 0) return false
        }
        return true
    }
}
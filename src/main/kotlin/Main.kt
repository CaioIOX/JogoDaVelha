import kotlin.random.Random

val board = arrayListOf<ArrayList<String>>()

fun main(args: Array<String>) {

    for (i in 0..2) {
        val row = arrayListOf<String>()
        for (j in 0..2)
            row.add("")
        board.add(row)
    }
    printBoard()

    var continueGame = true

    //Loop para receber escolha do jogador e marcar no tabuleiro
    do {
        println("Digite uma posição no formato linha coluna, exemplo: 1,1 ")
        val input = readLine() ?: ""
        var x: Int
        var y: Int
        try {
            val position = input.split(",")
            x = position[0].trim().toInt()
            y = position[1].trim().toInt()
            var skipRound = false

            if (board[x - 1][y - 1] != "") {
                println("Essa posição já foi escolhida, tente outra posição!")
                skipRound = true
            } else {
                board[x - 1][y - 1] = "X"
                printBoard()
            }

            //Verificação de vitória!
            if (!skipRound) {
                val playerWon = checkWinner(true)
                if (playerWon) {
                    println("Parabéns, você venceu!!!")
                    continueGame = false
                }
                // Verificação de empate!
                val boardFull = checkBoardFull()
                if (!playerWon && boardFull) {
                    println("Empate!")
                    continueGame = false
                }
                if (continueGame) {
                    placeComputerMove()
                    printBoard()
                    val computerWon = checkWinner(false)
                    if (computerWon) {
                        println("Computador venceu, tente novamente!")
                        continueGame = false
                    }
                }
            }
        } catch (e: Exception) {
            println("Entrada inválida, tente novamente!")

        }
    } while (continueGame)
}

// Construção do tabuleiro
fun printBoard() {
    println("-------------")
    for (i in 0..2) {
        for (j in 0..2) {
            when (board[i][j]) {
                "X" -> print("| X ")
                "O" -> print("| O ")
                else -> print("|   ")
            }
        }
        println("|")
        println("-------------")
    }
}

//Função para verificar o vencedor
fun checkWinner(player: Boolean): Boolean {
    var won = false
    val checkSymbol = if (player) "X" else "O"
    for (i in 0..2) {
        //Verificação de vitórias horizontais
        if (board[i][0] == checkSymbol && board[i][1] == checkSymbol && board[i][2] == checkSymbol) {
            won = true
            break
        }
        //Verificação de vitórias verticais
        if (board[0][i] == checkSymbol && board[1][i] == checkSymbol && board[2][i] == checkSymbol) {
            won = true
            break
        }
    }
    //Verificação de vitórias diagonais
    if (board[0][0] == checkSymbol && board[1][1] == checkSymbol && board[2][2] == checkSymbol) {
        won = true
    }
    if (board[2][0] == checkSymbol && board[1][1] == checkSymbol && board[0][2] == checkSymbol) {
        won = true
    }
    return won
}

//Função para checkar se o tabuleiro está cheio
fun checkBoardFull(): Boolean {
    var boardFull = true
    for (i in 0..2) {
        for (j in 0..2) {
            if (board[i][j] == "") {
                boardFull = false
                break
            }
        }
    }
    return boardFull
}

//Função que escolhe aleatoriamente as jogadas para o computador
fun placeComputerMove() {

    var i: Int
    var j: Int
    do {
        i = Random.nextInt(3)
        j = Random.nextInt(3)
    } while (board[i][j] != "")
    board[i][j] = "O"
}
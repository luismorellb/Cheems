package mx.itson.cheems

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameOverCard = 0
    var safeCardsRevealed = 0
    var gameFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        start()
    }
    fun start(){

        safeCardsRevealed = 0
        gameFinished = false

        for (i in 1..9) {
            val btnCard = findViewById<View>(
                resources.getIdentifier("card$i" , "id", this.packageName)

            ) as ImageButton
            btnCard.setOnClickListener(this)
            btnCard.setBackgroundResource(R.drawable.cheems_question)

            btnCard.tag = null

            val btnStart = findViewById<View>(R.id.reset)
            btnStart.setOnClickListener(this)
        }

        gameOverCard = (1..9).random()

        Log.d("Valor de la carta perdedora", "La cara perdedora es ${gameOverCard.toString()}")
    }

    fun flip(card :Int){

        if (gameFinished) return

        val btnCard = findViewById<View>(
            resources.getIdentifier("card$card", "id", this.packageName)
        ) as ImageButton

        if (btnCard.tag == "flipped") return
        btnCard.tag = "flipped"

        if (card == gameOverCard){
            Toast.makeText(this,
                "¡Haz perdido, intenta de nuevo!",
                Toast.LENGTH_LONG).show()

            for(i in 1..9){
                val btnCard = findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton

                if (i == card){
                    btnCard.setBackgroundResource(R.drawable.cheems_bad)
                } else {
                    btnCard.setBackgroundResource(R.drawable.cheems_ok)
                }
            }
        } else {
            btnCard.setBackgroundResource(R.drawable.cheems_ok)
            safeCardsRevealed++

            if (safeCardsRevealed == 8){
                gameFinished = true

                Toast.makeText(this,
                    "¡Haz ganado!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.card1 -> { flip(1)}
            R.id.card2 -> { flip(2)}
            R.id.card3 -> { flip(3)}
            R.id.card4 -> { flip(4)}
            R.id.card5 -> { flip(5)}
            R.id.card6 -> { flip(6)}
            R.id.card7 -> { flip(7)}
            R.id.card8 -> { flip(8)}
            R.id.card9 -> { flip(9)}
            R.id.reset -> start()
        }
    }
}
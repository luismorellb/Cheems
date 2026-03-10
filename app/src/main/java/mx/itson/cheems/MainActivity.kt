package mx.itson.cheems

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.cheems.entities.Winner


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameOverCard = 0
    var cheemsGoodRevealed = 0
    var cheemsMasterRevealed = 0
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

        Winner().save(this, "Pedro Robles Martínez", "pedrin")
        Winner().getAll(this)

        start()
    }
    fun start(){

        cheemsGoodRevealed = 0
        cheemsMasterRevealed = 0
        gameFinished = false

        for (i in 1..12) {
            val btnCard = findViewById<View>(
                resources.getIdentifier("card$i" , "id", this.packageName)

            ) as ImageButton
            btnCard.setOnClickListener(this)
            btnCard.setBackgroundResource(R.drawable.cheems_question)

            btnCard.tag = null

            val btnStart = findViewById<View>(R.id.reset)
            btnStart.setOnClickListener(this)
        }

        gameOverCard = (1..12).random()

        Log.d("Valor de la carta perdedora", "La carta perdedora es ${gameOverCard.toString()}")
        Log.d("Valor de la carta Master", "La carta Master es ${cheemsMasterRevealed.toString()}")
    }

    fun vibratePhone(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Si la versión del sistema operativo viene instalado en el teléfono es igual o mayor a Android 12
            val vibratorAdmin = applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorAdmin.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1500)
        }
    }
    fun flip(card :Int){

        if (gameFinished) return

        val btnCard = findViewById<View>(
            resources.getIdentifier("card$card", "id", this.packageName)
        ) as ImageButton

        if (btnCard.tag == "flipped") return
        btnCard.tag = "flipped"

        if (card == gameOverCard){

            vibratePhone()

            if (card == cheemsGoodRevealed){

                vibratePhone()

            Toast.makeText(this,
                "¡Haz perdido, intenta de nuevo!",
                Toast.LENGTH_LONG).show()

            for(i in 1..12){
                val btnCard = findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton

                if (i == card){
                    btnCard.setBackgroundResource(R.drawable.cheems_bad)
                } else if (i == cheemsMasterRevealed){
                    btnCard.setBackgroundResource(R.drawable.cheems_master)
                } else {
                    btnCard.setBackgroundResource(R.drawable.cheems_ok)
                }
            }
        } else {
            btnCard.setBackgroundResource(R.drawable.cheems_ok)
            cheemsGoodRevealed++

            btnCard.setBackgroundResource(R.drawable.cheems_master)
            cheemsMasterRevealed++

            if (cheemsGoodRevealed == 11){
                gameFinished = true

                Toast.makeText(this,
                    "¡Haz ganado!",
                    Toast.LENGTH_LONG).show()
            } else if (cheemsMasterRevealed == 1){
                gameFinished = true

                    Toast.makeText(this,
                        "¡Ganaste por Comodín!",
                        Toast.LENGTH_LONG).show()
                }
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
            R.id.card10 -> { flip(10)}
            R.id.card11 -> { flip(11)}
            R.id.card12 -> { flip(12)}
            R.id.reset -> start()
        }
    }
}
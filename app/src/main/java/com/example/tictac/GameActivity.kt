package com.example.tictac

import Player
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

//    // Создайте Retrofit-клиент
//    val retrofit = Retrofit.Builder()
//        .baseUrl("rain.db.elephantsql.com") // Установите базовый URL вашего сервера
//        .addConverterFactory(GsonConverterFactory.create()) // Используйте Gson для сериализации/десериализации данных
//        .client(OkHttpClient()) // Инициализируйте клиент OkHttp
//        .build()
//
//    // Создайте экземпляр службы API
//    val apiService = retrofit.create(ApiService::class.java)
    private val player1 = Player()
    private val player2 = Player()
    private var player = player1
    private var win = false
    private var gameMode = 0
    private var winName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent = intent
        gameMode = intent.getStringExtra("my_result_key")?.toInt() ?: -1

        val buttonXY00 = findViewById<Button>(R.id.xy00)
        val buttonXY01 = findViewById<Button>(R.id.xy01)
        val buttonXY02 = findViewById<Button>(R.id.xy02)
        val buttonXY10 = findViewById<Button>(R.id.xy10)
        val buttonXY11 = findViewById<Button>(R.id.xy11)
        val buttonXY12 = findViewById<Button>(R.id.xy12)
        val buttonXY20 = findViewById<Button>(R.id.xy20)
        val buttonXY21 = findViewById<Button>(R.id.xy21)
        val buttonXY22 = findViewById<Button>(R.id.xy22)

        buttonXY00.setOnClickListener { step(0, buttonXY00) }
        buttonXY01.setOnClickListener { step(1, buttonXY01) }
        buttonXY02.setOnClickListener { step(2, buttonXY02) }
        buttonXY10.setOnClickListener { step(3, buttonXY10) }
        buttonXY11.setOnClickListener { step(4, buttonXY11) }
        buttonXY12.setOnClickListener { step(5, buttonXY12) }
        buttonXY20.setOnClickListener { step(6, buttonXY20) }
        buttonXY21.setOnClickListener { step(7, buttonXY21) }
        buttonXY22.setOnClickListener { step(8, buttonXY22) }

    }

    private fun step(xy: Int, it: Button){
        if (win) {
            exit(when(winName){
                "Player1" -> 1
                "Player2" -> 2
                else -> 0
            })
            return
        }
        if(player.testXY(xy)) return
        when(player){
            player1 -> stepPlayer1(xy, it)
            else -> stepPlayer2(xy, it)
        }

        if (win) Toast.makeText(applicationContext, "$winName WIN!", Toast.LENGTH_SHORT).show()
        return
    }

    private fun stepPlayer1(xy: Int, it: Button){
        win = player1.stepMy(xy, player2)
        player = player2
        it.text = "X"
        winName = "Player1"
        if(player.testEnd() && !win) {
            win = true
            winName = "Friendship"
            return
        }
        if(gameMode == 1 && !win){ stepPlayer2(-1, null) }
    }

    private fun stepPlayer2(xy: Int, it: Button?){
        when(gameMode) {
            0 -> {
                win = player2.stepMy(xy, player1)
                it?.text = "0"
            }
            1 -> {
                val xyAi = player2.stepAI(player1)
                val rootView = findViewById<ViewGroup>(R.id.container)
                val buttonXY = rootView.findViewWithTag<Button>("but$xyAi")
                buttonXY.text = "0"
                win = player2.testWin()
            }
            else -> win = false
        }

        if (player.testEnd()) {
            win = true
            winName = "Friendship"
        } else {
            winName = "Player2"
            player = player1
        }
    }

    private fun exit(result: Int){
        intent.putExtra("my_result_key", result)
        setResult(RESULT_OK, intent)
        finish()
    }
//    fun getCall(){
//        // Выполните запрос
//        val call = apiService.fetchData()
//        call.enqueue(object : Callback<Player> {
//            override fun onResponse(call: Call<Player>, response: Response<Player>) {
//                if (response.isSuccessful) {
//                    val data = response.body() // Получите данные из ответа
//                    // Обработайте данные
//                } else {
//                    // Обработайте ошибку
//                }
//            }
//
//            override fun onFailure(call: Call<Player>, t: Throwable) {
//                // Обработайте ошибку
//            }
//        })
//    }

}

// Создайте интерфейс, описывающий API
//interface ApiService {
//    @GET("/ybjgznma/{room_id}") // Замените на URL вашего сервера и конечной точки
//    fun fetchData(): Call<Player>

//    @GET("data") // Замените на путь к эндпоинту, предоставляющему данные
//    suspend fun getData(): List<Data>
//
//    @POST("data")
//    suspend fun sendData(@Body data: Data): Response<Void>
//
//    @PUT("data/{id}")
//    suspend fun updateData(@Path("id") id: Int, @Body data: Data): Response<Void>
//
//    @DELETE("data/{id}")
//    suspend fun deleteData(@Path("id") id: Int): Response<Void>

//}
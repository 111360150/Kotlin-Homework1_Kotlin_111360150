package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText // 玩家姓名輸入框
    private lateinit var tvText: TextView // 顯示提示或結果的文字框
    private lateinit var radioGroup: RadioGroup // 用於選擇猜拳的選項
    private lateinit var btnMora: Button // 猜拳按鈕
    private lateinit var tvName: TextView // 顯示玩家姓名的文字框
    private lateinit var tvWinner: TextView // 顯示勝利者的文字框
    private lateinit var tvMyMora: TextView // 顯示玩家出拳的文字框
    private lateinit var tvTargetMora: TextView // 顯示電腦出拳的文字框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // 設置布局文件

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件
        initViews()

        // 設定按鈕的點擊事件
        btnMora.setOnClickListener { handleMoraGame() }
    }

    // 初始化元件
    private fun initViews() {
        edName = findViewById(R.id.edName) // 連結玩家姓名輸入框
        tvText = findViewById(R.id.tvText) // 連結提示和結果的顯示框
        radioGroup = findViewById(R.id.radioGroup) // 連結單選按鈕組（剪刀、石頭、布）
        btnMora = findViewById(R.id.btnMora) // 連結猜拳按鈕
        tvName = findViewById(R.id.tvName) // 連結顯示玩家姓名的文字框
        tvWinner = findViewById(R.id.tvWinner) // 連結顯示勝利者的文字框
        tvMyMora = findViewById(R.id.tvMyMora) // 連結顯示玩家出拳的文字框
        tvTargetMora = findViewById(R.id.tvTargetMora) // 連結顯示電腦出拳的文字框
    }

    // 處理猜拳遊戲
    private fun handleMoraGame() {
        // 獲取玩家輸入的姓名
        val playerName = edName.text.toString()

        // 如果玩家沒有輸入姓名，則顯示提示並返回
        if (playerName.isEmpty()) {
            tvText.text = "請輸入玩家姓名"
            return
        }

        // 獲取玩家選擇的出拳方式
        val myMora = getPlayerMora()
        // 產生隨機的電腦出拳，範圍為0到2
        val targetMora = (0..2).random()

        // 更新遊戲界面，顯示玩家和電腦的出拳
        updateUI(playerName, myMora, targetMora)
        // 顯示遊戲結果
        displayGameResult(playerName, myMora, targetMora)
    }

    // 根據玩家選擇的按鈕，返回相應的出拳
    private fun getPlayerMora(): Int {
        // 判斷哪一個按鈕被選中，返回相應的出拳
        return when (radioGroup.checkedRadioButtonId) {
            R.id.btnScissor -> 0 // 剪刀
            R.id.btnStone -> 1 // 石頭
            R.id.btnPaper -> 2 // 布
            else -> -1 // 未選擇任何選項
        }
    }

    // 更新遊戲界面，顯示玩家和電腦的出拳結果
    private fun updateUI(playerName: String, myMora: Int, targetMora: Int) {
        tvName.text = "名字\n$playerName" // 顯示玩家姓名
        tvMyMora.text = "我方出拳\n${getMoraString(myMora)}" // 顯示玩家出拳
        tvTargetMora.text = "電腦出拳\n${getMoraString(targetMora)}" // 顯示電腦出拳
    }

    // 根據雙方的出拳結果，顯示遊戲結果
    private fun displayGameResult(playerName: String, myMora: Int, targetMora: Int) {
        when {
            // 如果雙方出拳相同，顯示平手
            myMora == targetMora -> {
                tvWinner.text = "勝利者\n平手"
                tvText.text = "平局，請再試一次！"
            }
            // 如果玩家贏了，顯示玩家姓名
            isPlayerWin(myMora, targetMora) -> {
                tvWinner.text = "勝利者\n$playerName"
                tvText.text = "恭喜你獲勝了！！！"
            }
            // 否則顯示電腦獲勝
            else -> {
                tvWinner.text = "勝利者\n電腦"
                tvText.text = "可惜，電腦獲勝了！"
            }
        }
    }

    // 判斷玩家是否獲勝，根據剪刀、石頭、布的規則
    private fun isPlayerWin(myMora: Int, targetMora: Int): Boolean {
        return (myMora == 0 && targetMora == 2) ||
                (myMora == 1 && targetMora == 0) ||
                (myMora == 2 && targetMora == 1)
    }

    // 根據出拳的數值返回對應的文字描述
    private fun getMoraString(mora: Int): String {
        return when (mora) {
            0 -> "剪刀"
            1 -> "石頭"
            2 -> "布"
            else -> "未知" // 若未選擇任何出拳，返回"未知"
        }
    }
}

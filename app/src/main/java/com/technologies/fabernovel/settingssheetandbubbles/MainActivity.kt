package com.technologies.fabernovel.settingssheetandbubbles

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Icon
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val preferences: SharedPreferences
        get() = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val channel = makeNotificationChannel()
        createNotificationChannel(channel)
        initNightMode()
        setupListeners()
    }

    private fun initNightMode() {
        val nightMode = preferences.getBoolean(NIGHT_MODE_KEY, false)
        dark.isChecked = nightMode
        toggleNightMode(nightMode)
    }

    private fun createNotificationChannel(channel: NotificationChannel) {
        NotificationManagerCompat.from(this@MainActivity)
            .createNotificationChannel(channel)
    }

    private fun makeNotificationChannel(): NotificationChannel {
        val name = "Bubble channel"
        val description = "Display a bubble"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        return channel
    }

    private fun setupListeners() {
        internet.displaySettingsOnClick(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        wifi.displaySettingsOnClick(Settings.Panel.ACTION_WIFI)
        nfc.displaySettingsOnClick(Settings.Panel.ACTION_NFC)
        volume.displaySettingsOnClick(Settings.Panel.ACTION_VOLUME)
        bubble.displayBubbleOnClick()
        dark.switchDarkMode()
    }

    private fun Button.displaySettingsOnClick(panel: String) {
        setOnClickListener {
            startActivity(Intent(panel))
        }
    }

    private fun Button.displayBubbleOnClick() {
        setOnClickListener {
            // Create bubble intent
            val target = Intent(context, BubbleActivity::class.java)
            val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0 /* flags */)

            // Create bubble metadata
            val bubbleData = Notification.BubbleMetadata.Builder()
                .setDesiredHeight(600)
                // Note: although you can set the icon is not displayed in Q Beta 2
                .setIcon(Icon.createWithResource(context, R.drawable.ic_launcher_foreground))
                .setIntent(bubbleIntent)
                .build()

            // Create notification
            val chatBot = Person.Builder()
                .setBot(true)
                .setName("BubbleBot")
                .setImportant(true)
                .build()

            val builder = Notification.Builder(context, CHANNEL_ID)
                // classic notification intent
                .setContentIntent(bubbleIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setBubbleMetadata(bubbleData)
                .addPerson(chatBot)

            NotificationManagerCompat.from(this@MainActivity)
                .notify(0, builder.build())
        }
    }

    private fun Switch.switchDarkMode() {
        setOnCheckedChangeListener { _, checked ->
            preferences.edit()
                .putBoolean(NIGHT_MODE_KEY, checked)
                .apply()
            toggleNightMode(checked)
            refreshActivity()
        }
    }

    private fun toggleNightMode(checked: Boolean) {
        val nightMode = if (checked) {
            MODE_NIGHT_YES
        } else {
            MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    private fun refreshActivity() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    companion object {
        private const val CHANNEL_ID: String = "BUBBLE_CHANNEL"
        private const val SHARED_PREF_NAME: String = "BUBBLE_AND_PANEL"

        private const val NIGHT_MODE_KEY = "night_mode_key"

    }

}

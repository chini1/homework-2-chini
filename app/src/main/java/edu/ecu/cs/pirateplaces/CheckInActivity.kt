package edu.ecu.cs.pirateplaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_NAME = "edu.ecu.cs.pirateplaces.name"
const val EXTRA_CHECKED_IN = "edu.ecu.cs.pirateplaces.checked_in"

class EditPiratePlacesActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var checkedInTextView: TextView
    private lateinit var checkinButton: Button

    private var name: String = ""
    private var checkedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        name = intent.getStringExtra(EXTRA_NAME)

        nameTextView = findViewById(R.id.name_text_view)
        checkedInTextView = findViewById(R.id.checked_in_text_view)
        checkinButton = findViewById(R.id.checkin_button)

        checkinButton.setOnClickListener {
            setCheckedIn(true)
            updateUI()
        }

        updateUI()
    }

    private fun updateUI() {
        nameTextView.setText(name)

        val messageId = if(checkedIn) {
            R.string.checked_in
        } else {
            R.string.not_checked_in
        }

        checkedInTextView.setText(messageId)
    }

    private fun setCheckedIn(checkedIn: Boolean) {
        this.checkedIn = true
        val data = Intent().apply {
            putExtra(EXTRA_CHECKED_IN, checkedIn)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, name: String): Intent {
            return Intent(packageContext, EditPiratePlacesActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
            }
        }
    }
}

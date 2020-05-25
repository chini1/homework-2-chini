package edu.ecu.cs.pirateplaces

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHECK_IN = 0

class PiratePlacesListActivity : AppCompatActivity() {

    private lateinit var nameTextView : TextView
    private lateinit var visitedWithTextView : TextView
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    private val piratePlacesViewModel : PiratePlacesViewModel by lazy {
        ViewModelProviders.of(this).get(PiratePlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pirate_places_list)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        piratePlacesViewModel.currentIndex = currentIndex

        nameTextView = findViewById(R.id.name_text_view)
        visitedWithTextView = findViewById(R.id.visited_with_text_view)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)

        nextButton.setOnClickListener {
            if (piratePlacesViewModel.canMoveToNext) {
                piratePlacesViewModel.moveToNext()
                updateUI()
            } else {
                Toast.makeText(this, R.string.already_at_end, Toast.LENGTH_SHORT).show()
            }
        }

        prevButton.setOnClickListener {
            if (piratePlacesViewModel.canMoveToPrev) {
                piratePlacesViewModel.moveToPrev()
                updateUI()
            } else {
                Toast.makeText(this, R.string.already_at_start, Toast.LENGTH_SHORT).show()
            }
        }

        nameTextView.setOnClickListener {
            // Launch new activity
            val intent = EditPiratePlacesActivity.newIntent(this, piratePlacesViewModel.currentName)
            startActivityForResult(intent, REQUEST_CODE_CHECK_IN)
        }

        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, piratePlacesViewModel.currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHECK_IN) {
            Toast.makeText(this, R.string.checked_in_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        nameTextView.setText(piratePlacesViewModel.currentName)
        visitedWithTextView.setText(piratePlacesViewModel.currentVisitedWith)
    }
}

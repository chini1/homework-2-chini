package edu.ecu.cs.pirateplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.nio.file.Files.size
private const val TAG = "PiratePlaceListragment"
class PiratePlacesListFragment : Fragment() {
    private var adapter: PirateAdapter? = null
    private lateinit var pirateplaceRecyclerView: RecyclerView
    private  lateinit var pirateplace : PiratePlace
    private val pirateListViewModel: PiratePlacesViewModel by lazy {
        ViewModelProviders.of(this).get(PiratePlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: 100")
    }
        companion object {
            fun newInstance(): PiratePlacesListFragment {
                return PiratePlacesListFragment()
            }
        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pirate_places_list, container, false)

        pirateplaceRecyclerView =
            view.findViewById(R.id.pirate_recycler_view) as RecyclerView
        pirateplaceRecyclerView.layoutManager = LinearLayoutManager(context)
updateUI()
        return view
    }

    private fun updateUI() {
        val pirates = pirateListViewModel.piratePlaceList
        adapter = PirateAdapter(pirates)
        pirateplaceRecyclerView.adapter = adapter
    }

    private inner class PiratePlaceHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val piratetextplace: TextView = itemView.findViewById(R.id.pirate_place)
        val piratetextperson: TextView = itemView.findViewById(R.id.pirate_person)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            Toast.makeText(context, "Visited ${piratetextplace.text} with ${piratetextperson.text}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private inner class PirateAdapter(var pirateplaces: List<PiratePlace>) :
        RecyclerView.Adapter<PiratePlaceHolder>() {


               override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : PiratePlaceHolder {
            val view = layoutInflater.inflate(R.layout.list_item_place, parent, false)
            return PiratePlaceHolder(view)
        }

        override fun getItemCount() = pirateplaces.size



        override fun onBindViewHolder(holder: PiratePlaceHolder, position: Int) {
            val pirate = pirateplaces[position]
            holder.apply {
                piratetextplace.text =pirate.name
                    piratetextperson.text = pirate.visitedWith
            }
        }
    }
}
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.R
import com.example.cricscorer.ui.Batsman

class LIveMatchesAdapter(private val list:ArrayList<Match>,private var listener:SelectListener):RecyclerView.Adapter<LIveMatchesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.live_matches_listitem,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val  currentItem=list[position]
        holder.teamAName.text=currentItem.teamA
        holder.teamBName.text=currentItem.teamB
        holder.matchid.text=currentItem.id.toString()
        holder.mainContainer.setOnClickListener{
            view-> listener.onItemClick(list.get(position))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val matchid: TextView =itemView.findViewById(R.id.match_id)
        val teamAName:TextView =itemView.findViewById(R.id.teamAName)
        val teamBName:TextView =itemView.findViewById(R.id.teamBName)
        val vs:TextView=itemView.findViewById(R.id.vs)
        val mainContainer:CardView=itemView.findViewById(R.id.main_container)


    }
}
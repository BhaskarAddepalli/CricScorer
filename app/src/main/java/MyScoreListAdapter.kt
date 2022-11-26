import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricscorer.R
import com.example.cricscorer.ui.Batsman

class MyScoreListAdapter(private val list:ArrayList<Batsman>):RecyclerView.Adapter<MyScoreListAdapter.MyViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.scorecardlistitem,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val  currentItem=list[position]
        holder.name.text=currentItem.name
        holder.runs.text=currentItem.Runs
        holder.balls.text=currentItem.Balls
        holder.fours.text=currentItem.fours
        holder.sixes.text=currentItem.sixes
        holder.sr.text=currentItem.sr

    }

    override fun getItemCount(): Int {
       return list.size
    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val name: TextView =itemView.findViewById(R.id.textView9)
        val runs:TextView =itemView.findViewById(R.id.textView10)
        val balls:TextView =itemView.findViewById(R.id.textView11)
        val fours:TextView =itemView.findViewById(R.id.textView12)
        val sixes:TextView =itemView.findViewById(R.id.textView13)
        val sr:TextView=itemView.findViewById(R.id.textView14)




    }
}
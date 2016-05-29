package idv.david.flexiblefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoFragment extends Fragment {
    private int position;

    public InfoFragment() {

    }

    public int getPosition() {
        return position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) {
            return null;
        }

        Bundle bundle = this.getArguments();
        position = bundle.getInt("position");
        MyTeam.TeamVO team = MyTeam.TEAMS[position];
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        tvName.setText(team.getName());
        ImageView ivLogo = (ImageView)view.findViewById(R.id.ivLogo);
        ivLogo.setImageResource(team.getLogo());
        TextView tvInfo = (TextView)view.findViewById(R.id.tvInfo);
        tvInfo.setText(team.getInfo());
        return view;
    }
}

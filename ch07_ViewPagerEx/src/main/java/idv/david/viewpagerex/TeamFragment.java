package idv.david.viewpagerex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class TeamFragment extends Fragment {
    private TeamVO teamVO;

    public TeamFragment() {

    }

    public static TeamFragment newInstance(TeamVO teamVO) {
        TeamFragment fragment = new TeamFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("teamVO", teamVO);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            teamVO = (TeamVO)getArguments().getSerializable("teamVO");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        ImageView ivLogo = (ImageView)view.findViewById(R.id.ivLogo);
        ivLogo.setImageResource(teamVO.getLogo());
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        tvName.setText(teamVO.getName());
        return view;
    }
}

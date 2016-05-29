package idv.david.flexiblefragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragment extends ListFragment { // 跟listActivity用法一樣
    private boolean isDual;
    private int position;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //將所有球隊名字取出放進一個ArrayList集合裡
        ArrayList<String> teamNames = new ArrayList<>();
        for (MyTeam.TeamVO team : MyTeam.TEAMS) {
            teamNames.add(team.getName());
        }

        setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.fragment_main, teamNames));
        View infoFrameLayout = getActivity().findViewById(R.id.info); // 找MainActivity裡的這個元件id
        /*
        isDual為true條件：
        1. 透過findViewById有取得framelayout物件 (若是讀取activity_main(port)就沒有framelayout)
        2. 此framelayout是否可以被看見，沒有被隱藏起來
         */
        isDual = infoFrameLayout != null && infoFrameLayout.getVisibility() == View.VISIBLE; // getVisibility()取得是否能變看見

        if (savedInstanceState != null) { // 如果savedInstanceState有資料
            position = savedInstanceState.getInt("position", 0);
        }

        if (isDual) { // 水平
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 只能單項顯示
            showInfo();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.position = position;
        showInfo();
    }

    private void showInfo() {
        if (isDual) {
            InfoFragment infoFragment = (InfoFragment) getFragmentManager().findFragmentById(R.id.info);
            if(infoFragment == null || infoFragment.getPosition() != position) {
                infoFragment = new InfoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                infoFragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.info, infoFragment);
                //加入過場動畫
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // 過場動畫  淡入淡出
                ft.commit();
            }
        } else {
            //無法同時顯示兩個fragment情況下，就需要產生InfoActivity，再將InfoFragment依附上去
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

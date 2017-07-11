package edu.bluejack16_2.familysdaily;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {


    public MemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        ListView listView = (ListView) view.findViewById(R.id.lvMember);
        final MemberListViewAdapter mLVA = new MemberListViewAdapter(getContext());

        mLVA.add("Fidelis", "5m ago");
        mLVA.add("Michael", "1h ago");
        mLVA.add("Daniel", "1h ago");
        mLVA.add("Yakub", "1h ago");
        mLVA.add("Regina", "1h ago");
        mLVA.add("Kevin", "1h ago");
        listView.setAdapter(mLVA);

        return view;
    }

}

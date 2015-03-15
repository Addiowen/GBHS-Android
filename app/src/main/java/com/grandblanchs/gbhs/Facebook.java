package com.grandblanchs.gbhs;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import com.facebook.Session;
    import com.facebook.*;
    import com.facebook.model.GraphUser;


public class Facebook extends Fragment {
    TextView hello;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.facebook, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onStart() {
        super.onStart();

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}

    /*public void connect(){
        Session.openActiveSession(this, true, new Session.StatusCallback(){
            @Override
            public void call(Session session, SessionState state, Exception exception){
                if(session.isOpened()){
                    //make request to the API
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback(){
                        @Override
                        public void onCompleted(GraphUser user, Response response){
                            if (user != null){
                                hello = (TextView) getView().findViewById(hello);
                                hello.setText("Hello " + user.getName() + "!");
                            }
                        }
                    });
                }
            }
        });
    }
*/

}

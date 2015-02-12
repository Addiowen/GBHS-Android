package com.grandblanchs.gbhs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class announce extends Fragment {

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;
    ListView lstAnnounce;
    ArrayAdapter adapter;

    String announceText;
    String[] announceTextArray;
    String[] displayArray;

    Context context;

    public static announce newInstance() {
        announce fragment = new announce();
        return fragment;
    }

    public announce() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.announce, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        prog = (ProgressBar) getView().findViewById(R.id.prog);
        lstAnnounce = (ListView) getView().findViewById(R.id.lstAnnounce);
        new AnnounceScrape().execute();
    }

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //Scrape the daily announcements into a list.
            Document announce = null;
            Document rss = null;
            context = getActivity().getApplicationContext();
            List<String> list;

            try {
                announce = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=22922").get();
                Elements announceClass = announce.getElementsByClass("MsoNormal").tagName("li");

                //Split by class/bullet point
                announceTextArray = announceClass.toString().split("</li>");

                if (announceClass.isEmpty()) {
                    list = new ArrayList<String>();

                    //Add "No Announcements."
                    list.add(0, "No announcements.");

                }else{
                    displayArray = new String[announceTextArray.length];
                    for (int i = 0; i < announceTextArray.length; i++) {
                        //Populate the array, removing the class and style tags

                        displayArray[i] = announceTextArray[i].substring(85, announceTextArray[i].length());

                        //Overwrite undesired HTML characters
                        displayArray[i] = displayArray[i].replaceAll("&nbsp;", "");
                        displayArray[i] = displayArray[i].replaceAll("&amp;", "");
                        displayArray[i] = displayArray[i].replaceAll("<br>", "");
                        displayArray[i] = displayArray[i].replaceAll("</strong></span>", "");
                        displayArray[i] = displayArray[i].replaceAll("ext-align: center;\">  <span style=\"font-size:16px;\"><strong>", "");
                    }


                    //Convert to ArrayList for easy item removal
                    list = new ArrayList<String>(Arrays.asList(displayArray));

                    //Remove the first three garbage entries
                    list.remove(0);
                    list.remove(1);
                    list.remove(2);
                }

                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (NullPointerException e) {
                list = new ArrayList<String>();

                //Add "No Announcements."
                list.add(0, "No announcements.");

                Toast.makeText(context, "NullPointer warning", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lstAnnounce.setAdapter(adapter);
            prog.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}

package a4tay.xyz.brokebandslookingforhome;


import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.EventRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.EventLoader;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class EventList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Event>> {

    private final static String LOG_TAG = EventList.class.getSimpleName();
    private ArrayList<Event> eventList;
    RecyclerView eventRecyclerView;
    private String url = "http://192.168.1.98:4567/eventsByID/1";
    //private String url = "http://dev.4tay.xyz:4567/eventsByID/1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.event_list_activity, container, false);
        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_event_list);
        getLoaderManager().initLoader(1,null,this).forceLoad();

        return rootView;
    }
    private void updateUI() {

        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter() {
            @Override
            public Event getItem(int position) {
                return eventList.get(position);
            }

            @Override
            public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
                return new EventHolder(view);
            }

            @Override
            public void onBindViewHolder(EventHolder holder, int position) {
                final Event event = eventList.get(position);

                if(event != null) {
                    holder.title.setText(event.getEventTitle());
                    holder.time.setText(event.getEventDate());
                }

            }

            @Override
            public int getItemCount() {
                if(eventList != null && eventList.size() > 0){
                    return eventList.size();
                } else {
                    return 0;
                }
            }
        };

        eventRecyclerView.setAdapter(eventRecyclerAdapter);
        eventRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        eventRecyclerView.setLayoutManager(cardLayoutManager);
    }

    @Override
    public Loader<ArrayList<Event>> onCreateLoader(int id, Bundle args) {


        return new EventLoader(getContext(),url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Event>> loader, ArrayList<Event> data) {
        eventList = data;

        Log.d(LOG_TAG,"eventList is " + String.valueOf(eventList.size()) + " long!");
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Event>> loader) {
        eventList = new ArrayList<>();

    }
}
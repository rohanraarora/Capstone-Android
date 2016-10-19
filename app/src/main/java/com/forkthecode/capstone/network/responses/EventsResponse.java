package com.forkthecode.capstone.network.responses;

import com.forkthecode.capstone.data.models.Event;

import java.util.List;

/**
 * Created by rohanarora on 19/10/16.
 */

public class EventsResponse extends ApiResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }

}

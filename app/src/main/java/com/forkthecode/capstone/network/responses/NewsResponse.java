package com.forkthecode.capstone.network.responses;

import com.forkthecode.capstone.data.models.News;

import java.util.List;

/**
 * Created by rohanarora on 19/10/16.
 */

public class NewsResponse extends ApiResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<News> news;

        public List<News> getNews() {
            return news;
        }

        public void setNews(List<News> news) {
            this.news = news;
        }
    }

}

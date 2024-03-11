package com.t1r2340.spotifystats.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Spotify API helper class
 *
 * A Consumer<JsonObject> is essentially a method that has a JSONObject as a parameter
 * to allow fro easy use of the JSON object returned from the API without having to deal with
 * the async nature of the API call
 */
public class SpotifyApi {

    private String accessToken;
    private FailureCallback failureCallback;
    private OkHttpClient okHttpClient;

    // TODO: Determine if more time ranges can be used
    public enum TimeRange {
        /** 1 month */
        SHORT_TERM("short_term"),
        /** 6 months */
        MEDIUM_TERM("medium_term"),
        /** Several years */
        LONG_TERM("long_term");

        private final String value;

        TimeRange(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public SpotifyApi(FailureCallback failureCallback, String accessToken, OkHttpClient okHttpClient) {
        this.failureCallback = failureCallback;
        this.accessToken = accessToken;
        this.okHttpClient = okHttpClient;
    }

    /**
     * Gets user profile
     * @param successConsumer consumer for successful response
     */
    public void getProfile(Consumer<JSONObject> successConsumer) {
        getJson(successConsumer, "https://api.spotify.com/v1/me");
    }

    /**
     * Gets user top artists
     * @param successConsumer consumer for successful response
     * @param limit number of artists to retrieve
     * @param timeRange time range for top artists
     */
    public void getTopArtists(Consumer<JSONObject> successConsumer, int limit, TimeRange timeRange) {
        getJson(
                successConsumer,
                "https://api.spotify.com/v1/me/top/artists?limit=" + limit + "&time_range=" + timeRange.getValue()
        );
    }

    /**
     * Gets user top tracks
     * @param successConsumer consumer for successful response
     * @param limit number of tracks to retrieve
     * @param timeRange time range for top tracks
     */
    public void getTopTracks(Consumer<JSONObject> successConsumer, int limit, TimeRange timeRange) {
        getJson(
                successConsumer,
                "https://api.spotify.com/v1/me/top/tracks?limit=" + limit + "&time_range=" + timeRange.getValue()
        );
        // TODO: Extract soundbite
    }


    /**
     * Creates request for API data and retrieves JSON
     * @param successConsumer consumer for successful response
     * @param url url for API request
     */
    private void getJson(Consumer<JSONObject> successConsumer, String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failureCallback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    // TODO: Convert JSON to object
                    successConsumer.accept(jsonObject);
                } catch (JSONException e) {
                    failureCallback.onFailure(e);
                }
            }
        });
    }

}

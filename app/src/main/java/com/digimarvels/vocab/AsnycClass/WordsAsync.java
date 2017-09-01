package com.digimarvels.vocab.AsnycClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.digimarvels.vocab.Model.WordsModel;
import com.digimarvels.vocab.networksUtils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.digimarvels.vocab.networksUtils.Constant.BASE_URL;
import static com.digimarvels.vocab.networksUtils.Constant.FINAL_URL;

/**
 * Created by Rakeshk on 16-08-2017.
 */

public class WordsAsync extends AsyncTask<Object, Object, ArrayList<WordsModel>> {

    JSONParser jParser = new JSONParser();
    Context context;
    private ProgressDialog pDialog;
    private Activity activity;
    ArrayList<WordsModel> wordsList = new ArrayList<>();
    SharedPreferences preferences;

    public interface AsyncResponse {
        void processFinish(ArrayList<WordsModel> output);
    }

    public AsyncResponse delegate = null;

    public WordsAsync(AsyncResponse delegate){
        this.delegate = delegate;
        this.activity = (Activity) delegate;
        context = activity;
        pDialog = new ProgressDialog(context);
    }

    @Override
    protected ArrayList<WordsModel> doInBackground(Object... params) {
        try {
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("first", "words"));

            FINAL_URL = BASE_URL + "?type=words";
            JSONObject json = jParser.makeHttpRequest(FINAL_URL, "POST", param);

            preferences = context.getApplicationContext().getSharedPreferences("Vocab",0);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("wordJson",json.toString());
            edit.commit();

            try {
                JSONObject jsonObject = new JSONObject(json.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("wordlist");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    WordsModel wModel = new WordsModel();

                    String word = obj.getString("word");
                    String define = obj.getString("definition");

                    wModel.setStrWord(word);
                    wModel.setStrDefine(define);

                    wordsList.add(wModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wordsList;
    }

    @Override
    protected void onPostExecute(ArrayList<WordsModel> output) {
        super.onPostExecute(output);

        delegate.processFinish(output);
    }
}
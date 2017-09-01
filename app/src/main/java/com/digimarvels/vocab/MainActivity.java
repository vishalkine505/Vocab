package com.digimarvels.vocab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.digimarvels.vocab.Adapter.WordAdapter;
import com.digimarvels.vocab.AsnycClass.WordsAsync;
import com.digimarvels.vocab.Model.WordsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements WordsAsync.AsyncResponse{

    ArrayList<WordsModel> wordsList;
    ListView listWords;
    SharedPreferences preferences;
    String strJson, strTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());

        new WordsAsync(this).execute();
/*
        preferences = getApplicationContext().getSharedPreferences("Vocab",0);
        strJson = preferences.getString("wordJson","");
        if(strJson != ""){
            setWordsList();
        }
*/
    }

    public void setWordsList(){
        try {
            JSONObject jsonObject = new JSONObject(strJson.toString());
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

            listWords = (ListView) findViewById(R.id.list_words);
            listWords.setAdapter(new WordAdapter(this,wordsList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processFinish(ArrayList<WordsModel> output) {
        wordsList = output;

        listWords = (ListView) findViewById(R.id.list_words);
        listWords.setAdapter(new WordAdapter(this,wordsList));
    }
}
package studio.dinhduc.doctruyen.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.dinhduc.doctruyen.R;
import studio.dinhduc.doctruyen.model.SearchResult;
import studio.dinhduc.doctruyen.ui.adapter.SearchResultAdapter;
import studio.dinhduc.doctruyen.ui.constant.Const;
import studio.dinhduc.doctruyen.util.CommonUtils;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActivity";

    @BindView(R.id.tool_bar) Toolbar mToolBar;
    @BindView(R.id.lv_search_result) ListView mLvSearchResult;

    private ArrayList<SearchResult> mSearchResults = new ArrayList<>();
    private SearchResultAdapter mAdapter;
    private String mNovelDirPath;
    private ArrayList<String> mChapterNames;
    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initView();
        getWidgetControl();
    }

    private void initView() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mSearchQuery = intent.getStringExtra(Const.KeyIntent.KEY_SEARCH_QUERY);
        mChapterNames = intent.getStringArrayListExtra(Const.KeyIntent.KEY_LIST_CHAPTER_NAME);
        mNovelDirPath = intent.getStringExtra(Const.KeyIntent.KEY_NOVEL_DIR_PATH);
        final ProgressDialog dialog = CommonUtils.showProgressLoadingDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mChapterNames.size(); i++) {
                    int progress = ((int) ((float) i / mChapterNames.size() * 100));
                    Log.d(TAG, "run: " + progress);
                    dialog.setProgress(progress);
                    String chapterName = mChapterNames.get(i);
                    String chapterPath = mNovelDirPath + File.separator + chapterName;
                    String chapterContent = CommonUtils.readFileTxt(chapterPath);
//                    int index = chapterContent.toLowerCase().indexOf(mSearchQuery.toLowerCase());
//                    if (index != -1) {
//                        String resultContent;
//                        int endQueryIndex = index + mSearchQuery.length();
//                        if (chapterContent.length() < endQueryIndex + 50) {
//                            resultContent = chapterContent.substring(endQueryIndex - 50, endQueryIndex);
//                        } else if (chapterContent.length() > endQueryIndex + 50 && 0 < endQueryIndex - 50) {
//                            resultContent = chapterContent.substring(endQueryIndex - 25, endQueryIndex + 25);
//                        } else {
//                            resultContent = chapterContent.substring(index, endQueryIndex + 50);
//                        }
//                        Log.d(TAG, chapterName);
//                        String highlightResult = CommonUtils.hiLightQueryInText(
//                                getBaseContext(), mSearchQuery, resultContent);
//                        SearchResult searchResult = new SearchResult();
//                        searchResult.setChapterName(chapterName);
//                        searchResult.setResultContent(highlightResult);
//                        mSearchResults.add(searchResult);
//                    }

                    String sentence = CommonUtils.getSentence(chapterContent, mSearchQuery);
                    if (sentence != null) {
                        sentence = sentence.replaceAll("<br>", "");
                        String highlightSentence = CommonUtils.hiLightQueryInText(
                                getBaseContext(), mSearchQuery, sentence);
                        SearchResult searchResult = new SearchResult();
                        searchResult.setChapterName(chapterName);
                        searchResult.setResultContent(highlightSentence);
                        mSearchResults.add(searchResult);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        mAdapter = new SearchResultAdapter(
                                getBaseContext(), R.layout.item_search_result, mSearchResults);
                        mLvSearchResult.setAdapter(mAdapter);
                    }
                });
            }
        }).start();
    }

    private void getWidgetControl() {
        mLvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), ChapterContentActivity.class);
                intent.putExtra(
                        Const.KeyIntent.KEY_CHAPTER_PATH,
                        mNovelDirPath + File.separator + mSearchResults.get(position).getChapterName()
                );
                intent.putExtra(Const.KeyIntent.KEY_SEARCH_QUERY, mSearchQuery);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
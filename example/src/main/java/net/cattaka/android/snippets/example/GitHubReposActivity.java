package net.cattaka.android.snippets.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.factory.RepoViewHolderFactory;
import net.cattaka.android.snippets.example.core.MyApplication;
import net.cattaka.android.snippets.example.data.Repo;
import net.cattaka.android.snippets.example.retrofit.GitHubService;
import net.cattaka.android.snippets.example.tracker.IScreen;
import net.cattaka.android.snippets.example.tracker.TrackEvent;
import net.cattaka.android.snippets.example.tracker.Tracker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static net.cattaka.android.snippets.example.tracker.TrackParam.ITEM_ID;
import static net.cattaka.android.snippets.example.tracker.TrackParam.ITEM_LIST;
import static net.cattaka.android.snippets.example.tracker.TrackParam.VIEW_NAME;
import static net.cattaka.android.snippets.example.tracker.TrackParamBundle.toParamsMap;

/**
 * Created by cattaka on 16/07/10.
 */
public class GitHubReposActivity extends RxAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, IScreen {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view) {
            if (adapter == mAdapter) {
                Repo repo = mAdapter.getItemAt(viewHolder.getAdapterPosition());
                if (view.getId() == R.id.button_owner) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(ITEM_ID, repo.getName(), ITEM_LIST, "git_hub_repos", VIEW_NAME, "button_owner"));
                    showOwner(repo);
                } else if (view.getId() == R.id.button_open) {
                    Tracker.getInstance().recordAction(me, TrackEvent.ACTION_CLICK, toParamsMap(ITEM_ID, repo.getName(), ITEM_LIST, "git_hub_repos", VIEW_NAME, "button_open"));
                    showUrl(repo);
                }
            }
        }
    };

    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    MySQLiteOpenHelper mMySQLiteOpenHelper;
    @Inject
    GitHubService mGitHubService;

    GitHubReposActivity me = this;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    ScrambleAdapter<Repo> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_repos);
        {   // inject components
            ((MyApplication) getApplication()).getAppComponent().inject(this);
        }
        {   // find views
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        }
        {   // setup mSwipeRefreshLayout
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        {   // setup mRecyclerView
            mAdapter = new ScrambleAdapter<Repo>(this, new ArrayList<Repo>(), mListenerRelay, new RepoViewHolderFactory());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestRepos();
    }

    @Override
    public void onRefresh() {
        requestRepos();
    }

    private void requestRepos() {
        mGitHubService.listRepos("cattaka")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<Repo>>bindToLifecycle())
                .subscribe(new Action1<List<Repo>>() {
                    @Override
                    public void call(List<Repo> repos) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showRepos(repos);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Snackbar.make(mRecyclerView, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRepos(List<Repo> repos) {
        mAdapter.getItems().clear();
        mAdapter.getItems().addAll(repos);
        mAdapter.notifyDataSetChanged();
    }

    private void showOwner(Repo repo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(repo.getOwner().getHtmlUrl()));
        startActivity(intent);
    }

    private void showUrl(Repo repo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(repo.getHtmlUrl()));
        startActivity(intent);
    }
}

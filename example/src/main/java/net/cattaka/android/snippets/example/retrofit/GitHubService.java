package net.cattaka.android.snippets.example.retrofit;

import net.cattaka.android.snippets.example.data.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cattaka on 16/07/10.
 */
public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}

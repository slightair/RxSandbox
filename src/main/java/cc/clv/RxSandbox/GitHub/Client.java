package cc.clv.RxSandbox.GitHub;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class Client {
    private static final String API_ROOT = "https://api.github.com";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public Observable<List<Repository>> getRepos(final String userName) {
        return Observable.create(new Observable.OnSubscribe<List<Repository>>() {
            @Override
            public void call(Subscriber<? super List<Repository>> subscriber) {
                Request request = new Request.Builder()
                        .url(API_ROOT + "/users/" + userName + "/repos")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        subscriber.onError(new IOException("Unexpected code: " + response));
                        return;
                    }

                    Type collectionType = new TypeToken<Collection<Repository>>() {
                    }.getType();
                    List<Repository> repos = gson.fromJson(response.body().string(), collectionType);

                    subscriber.onNext(repos);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<List<Commit>> getRepoCommits(final String repoFullName) {
        return Observable.create(new Observable.OnSubscribe<List<Commit>>() {
            @Override
            public void call(Subscriber<? super List<Commit>> subscriber) {
                Request request = new Request.Builder()
                        .url(API_ROOT + "/repos/" + repoFullName + "/commits")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        subscriber.onError(new IOException("Unexpected code: " + response));
                        return;
                    }

                    Type collectionType = new TypeToken<Collection<Commit>>() {
                    }.getType();
                    List<Commit> commits = gson.fromJson(response.body().string(), collectionType);

                    subscriber.onNext(commits);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}

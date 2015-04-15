package cc.clv.RxSandbox;

import cc.clv.RxSandbox.GitHub.Client;
import cc.clv.RxSandbox.GitHub.Commit;
import cc.clv.RxSandbox.GitHub.Repository;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.util.List;

public class RxSandbox {
    public static void main(String[] args) {
        final Client client = new Client();
        client.getRepos("slightair")
                .flatMap(new Func1<List<Repository>, Observable<Repository>>() {
                    @Override
                    public Observable<Repository> call(List<Repository> repositories) {
                        return Observable.from(repositories);
                    }
                })
                .take(3)
                .map(new Func1<Repository, RepositoryInfo>() {

                    @Override
                    public RepositoryInfo call(Repository repository) {
                        List<Commit> commits = client.getRepoCommits(repository.getFullName()).toBlocking().single();
                        return new RepositoryInfo(repository, commits);
                    }
                })
                .subscribe(new Observer<RepositoryInfo>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("\nComplete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(RepositoryInfo repositoryInfo) {
                        System.out.println(repositoryInfo.getRepository().getName());

                        Observable.from(repositoryInfo.getCommits())
                                .take(5)
                                .subscribe(commit -> {
                                    Commit.Info.Author author = commit.getCommit().getAuthor();
                                    System.out.println(String.format("\t %s %s - %s", author.getName(), author.getDate(), commit.getSha()));
                                });
                    }
                });
    }
}

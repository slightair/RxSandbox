package cc.clv.RxSandbox;

import cc.clv.RxSandbox.GitHub.Commit;
import cc.clv.RxSandbox.GitHub.Repository;

import java.util.List;

@lombok.Value
public class RepositoryInfo {
    Repository repository;
    List<Commit> commits;
}

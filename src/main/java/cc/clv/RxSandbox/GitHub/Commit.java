package cc.clv.RxSandbox.GitHub;

@lombok.Value
public class Commit {
    @lombok.Value
    public class Info {
        @lombok.Value
        public class Author {
            String name;
            String email;
            String date;
        }

        Author author;
    }

    String sha;
    Info commit;
}

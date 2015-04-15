package cc.clv.RxSandbox.GitHub;

import com.google.gson.annotations.SerializedName;

@lombok.Value
public class Repository {
    int id;
    String name;
    @SerializedName("full_name")
    String fullName;
    String description;
    String url;
}

package ysfcyln.com.acviewmodel.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ysfcyln.com.acviewmodel.model.Repo;

/**
 * Created by Yusuf on 02.01.2018.
 */

public interface RepoService {

    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();

    @GET("repos/{owner}/{name}")
    Call<Repo> getRepo(@Path("owner") String repoOwner, @Path("name") String repoName);

}

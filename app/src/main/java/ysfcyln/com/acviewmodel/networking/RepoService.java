package ysfcyln.com.acviewmodel.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ysfcyln.com.acviewmodel.model.Repo;

/**
 * Created by Yusuf on 02.01.2018.
 */

public interface RepoService {

    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();

}

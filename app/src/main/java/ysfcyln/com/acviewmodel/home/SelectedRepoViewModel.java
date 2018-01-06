package ysfcyln.com.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ysfcyln.com.acviewmodel.model.Repo;
import ysfcyln.com.acviewmodel.networking.RepoApi;

/**
 * Created by Yusuf on 04.01.2018.
 */

public class SelectedRepoViewModel extends ViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private Call<Repo> repoCall;

    public LiveData<Repo> getSelectedRepo(){
        return selectedRepo;
    }

    public void setSelectedRepo(Repo repo){
        selectedRepo.setValue(repo);
    }


    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            outState.putStringArray("repo_details",
                    new String[]{selectedRepo.getValue().owner.login, selectedRepo.getValue().name});
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        // we want it if selected repo value is null because
        // if it has a value its already been set so we dont need to restore anything
        if (selectedRepo.getValue() == null){
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")){
                loadRepo(savedInstanceState.getStringArray("repo_details"));
            }
        }
    }

    private void loadRepo(String[] repoDetails) {
        repoCall = RepoApi.getInstance().getRepo(repoDetails[0], repoDetails[1]);
        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                // update the live data
                selectedRepo.setValue(response.body());
                repoCall = null;
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error Loading Repo", t);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        // super.onCleared();
        if (repoCall != null){
            repoCall.cancel();
            repoCall = null;
        }
    }
}

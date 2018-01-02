package ysfcyln.com.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ysfcyln.com.acviewmodel.model.Repo;
import ysfcyln.com.acviewmodel.networking.RepoApi;

/**
 * Created by Yusuf on 02.01.2018.
 */

public class ListViewModel extends ViewModel{

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private Call<List<Repo>> repoCall;

    // Need this data when the viewModel is created so
    // rather having a method for the view call to tell us to load data
    // we will do it in the constructor
    public ListViewModel(){
        fetchData();
    }


    LiveData<List<Repo>> getRepos(){
        return repos;
    }

    LiveData<Boolean> getError(){
        return repoLoadError;
    }

    LiveData<Boolean> getLoading(){
        return loading;
    }

    private void fetchData() {
        loading.setValue(true);
        repoCall = RepoApi.getInstance().getRepositories();
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repos.setValue(response.body());
                loading.setValue(false);
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading repos", t);
                repoLoadError.setValue(true);
                loading.setValue(false);
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

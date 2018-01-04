package ysfcyln.com.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ysfcyln.com.acviewmodel.model.Repo;

/**
 * Created by Yusuf on 04.01.2018.
 */

public class SelectedRepoViewModel extends ViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();

    public LiveData<Repo> getSelectedRepo(){
        return selectedRepo;
    }

    public void setSelectedRepo(Repo repo){
        selectedRepo.setValue(repo);
    }


}

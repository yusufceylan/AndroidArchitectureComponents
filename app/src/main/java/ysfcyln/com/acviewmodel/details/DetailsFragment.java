package ysfcyln.com.acviewmodel.details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ysfcyln.com.acviewmodel.R;
import ysfcyln.com.acviewmodel.home.SelectedRepoViewModel;

/**
 * Created by Yusuf on 03.01.2018.
 */

public class DetailsFragment extends Fragment {

    @BindView(R.id.tv_repo_name)
    TextView repoNameTextView;

    @BindView(R.id.tv_repo_description)
    TextView repoDescriptionTextView;

    @BindView(R.id.tv_forks)
    TextView forksTextView;

    @BindView(R.id.tv_stars)
    TextView starsTextView;

    private Unbinder unbinder;
    private SelectedRepoViewModel selectedRepoViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        selectedRepoViewModel = ViewModelProviders.of(getActivity()).get(SelectedRepoViewModel.class);
        selectedRepoViewModel.restoreFromBundle(savedInstanceState);
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedRepoViewModel.saveToBundle(outState);
    }

    // it shows single repo
    private void displayRepo() {
        selectedRepoViewModel.getSelectedRepo().observe(this, repo -> {
            repoNameTextView.setText(repo.name);
            repoDescriptionTextView.setText(repo.description);
            forksTextView.setText(String.valueOf(repo.forks));
            starsTextView.setText(String.valueOf(repo.stars));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}

package ysfcyln.com.acviewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ysfcyln.com.acviewmodel.details.DetailsFragment;
import ysfcyln.com.acviewmodel.home.ListViewModel;
import ysfcyln.com.acviewmodel.home.RepoListAdapter;
import ysfcyln.com.acviewmodel.home.RepoSelectedListener;
import ysfcyln.com.acviewmodel.home.SelectedRepoViewModel;
import ysfcyln.com.acviewmodel.model.Repo;

/**
 * Created by Yusuf on 01.01.2018.
 */

public class ListFragment extends Fragment implements RepoSelectedListener {

    @BindView(R.id.recycler_view)
    RecyclerView listView;

    @BindView(R.id.tv_error)
    TextView errorTextView;

    @BindView(R.id.loading_view)
    View loadingView;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // if the view model has already been created for this fragment
    // it will return that instance rather then creating new one
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // Set recyclerview
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        // listen live data objects exposed by our view model
        observeViewModel();
    }

    // Interface method for selecting repo
    @Override
    public void onRepoSelected(Repo repo) {
        // We pass Activity rather than fragment because it makes this model scoped activity
        // rather than fragment so we can access one view model with multiple fragments
        SelectedRepoViewModel selectedRepoViewModel = ViewModelProviders.of(getActivity()).get(SelectedRepoViewModel.class);
        selectedRepoViewModel.setSelectedRepo(repo);
        // Push to new details screen
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_container, new DetailsFragment())
                .addToBackStack(null)
                .commit();
    }


    // Observe datas
    private void observeViewModel() {

        // Observe repos
        viewModel.getRepos().observe(this, repos -> {
            if(repos != null){
                listView.setVisibility(View.VISIBLE);
            }
        });

        // Observe error
        viewModel.getError().observe(this, isError -> {
            //noinspection ConstantConditions
            if(isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText(R.string.api_error_repos);
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
                // other view handle in the other callbacks
            }
        });

        // Observe loading
        viewModel.getLoading().observe(this, isLoading -> {
            //noinspection ConstantConditions
            loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading){
                errorTextView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
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

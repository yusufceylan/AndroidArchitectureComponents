package ysfcyln.com.acviewmodel.home;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ysfcyln.com.acviewmodel.R;
import ysfcyln.com.acviewmodel.model.Repo;

/**
 * Created by Yusuf on 03.01.2018.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>{

    private final List<Repo> data = new ArrayList<>();

    // Constructor
    public RepoListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner){
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            data.clear();
            if(repos != null){
                data.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return data.get(position).id;
    }

    // View holder class
    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name)
        TextView repoNameTextView;

        @BindView(R.id.tv_repo_description)
        TextView repoDescriptionTextView;

        @BindView(R.id.tv_forks)
        TextView forksTextView;

        @BindView(R.id.tv_stars)
        TextView starsTextView;

        private RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Method for binding data
        void bind(Repo repo){
            repoNameTextView.setText(repo.name);
            repoDescriptionTextView.setText(repo.description);
            forksTextView.setText(String.valueOf(repo.forks));
            starsTextView.setText(String.valueOf(repo.stars));
        }
    }


}

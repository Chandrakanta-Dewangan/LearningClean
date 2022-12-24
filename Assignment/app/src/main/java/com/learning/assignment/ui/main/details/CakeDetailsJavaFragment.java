package com.learning.assignment.ui.main.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.learning.domain.model.Cakes;
import com.learning.assignment.R;
import com.learning.assignment.databinding.DetailsFragmentBinding;
import com.learning.assignment.ui.main.base.BaseFragment;

public class CakeDetailsJavaFragment extends BaseFragment<DetailsFragmentBinding, CakeDetailsViewModel> {
    @NonNull
    @Override
    public DetailsFragmentBinding setupViewBinding(@NonNull View view) {
        return DetailsFragmentBinding.bind(view);
    }

    @NonNull
    @Override
    public CakeDetailsViewModel setupViewModel() {
        return new ViewModelProvider(this).get(CakeDetailsViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.details_fragment;
    }

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, @NonNull View view, @Nullable Bundle savedInstanceState) {
        CakeDetailsJavaFragmentArgs args = CakeDetailsJavaFragmentArgs.fromBundle(getArguments());
        Cakes ad = args.getCake();
        viewBinding.titleTv.setText(ad.getTitle());
        viewBinding.detailsTv.setText(ad.getDesc());

        setImage(ad);
    }

    private void setImage(Cakes ad) {
        CircularProgressDrawable drawable = new CircularProgressDrawable(requireContext());
        drawable.setColorSchemeColors(R.color.purple_200,
                R.color.purple_700,
                R.color.purple_500);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        drawable.start();

        Glide.with(requireContext())
                .load(ad.getImage())
                .placeholder(drawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_default_ad)
                .into(viewBinding.imageView);
    }
}

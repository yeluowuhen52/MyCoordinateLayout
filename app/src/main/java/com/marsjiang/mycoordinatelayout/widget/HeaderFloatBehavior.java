package com.marsjiang.mycoordinatelayout.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.marsjiang.mycoordinatelayout.R;

import java.lang.ref.WeakReference;

/**
 * Created by cyandev on 2016/12/14.
 */
public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<View> {

    private WeakReference<View> dependentView;
    //    private WeakReference<View> dependentViewRL;
    private ArgbEvaluator argbEvaluator;

    public HeaderFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header) {
            dependentView = new WeakReference<>(dependency);
            return true;
        }
//        if (dependency != null && dependency.getId() == R.id.rlHeader) {
//            dependentViewRL = new WeakReference<>(dependency);
//            return true;
//        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Resources resources = getDependentView().getResources();

        Log.d("dependencyHeight", dependency.getHeight() + "");
        Log.d("dependencyPercent", 1.f -
                Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height))) + "");

        Log.d("dependencyTranslationY", dependency.getTranslationY() + "");

        final float progress = 1.f -
                Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height)));

        float startHeight =
                resources.getDimension(R.dimen.collapsed_header_height) + resources.getDimension(R.dimen.init_search_height);

        Log.d("startHeight", startHeight + "");

        float transDiss = dependency.getTranslationY() + startHeight;
        final float transProgress = 1.f -
                (Math.abs(transDiss) / startHeight);

        Log.d("transProgress", transProgress + "");
        Log.d("transDiss", dependency.getTranslationY() + startHeight + "");

        // Translation
        final float collapsedOffset = resources.getDimension(R.dimen.collapsed_float_offset_y);
        final float initOffset = resources.getDimension(R.dimen.init_float_offset_y);
        final float translateY = collapsedOffset + (initOffset - collapsedOffset) * transProgress;

        final float dependencyHeight = dependency.getTranslationY();

        child.setTranslationY(resources.getDimension(R.dimen.collapsed_header_height));

        if (transDiss < 0) {
            Log.d("translateY", translateY + "");
            child.setTranslationY(translateY);
        }
        // Background
        child.setBackgroundColor((int) argbEvaluator.evaluate(
                progress,
                resources.getColor(R.color.colorCollapsedFloatBackground),
                resources.getColor(R.color.colorInitFloatBackground)));

//        dependentViewRL.get().setBackgroundColor((int) argbEvaluator.evaluate(
//                progress,
//                resources.getColor(R.color.colorCollapsedFloatBackground),
//                resources.getColor(R.color.colorInitFloatBackground)));

        // Margins
        final float collapsedMargin = resources.getDimension(R.dimen.collapsed_float_margin);
        final float initMargin = resources.getDimension(R.dimen.init_float_margin);
        final int margin = (int) (collapsedMargin + (initMargin - collapsedMargin) * progress);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.setMargins(0, 0, margin, 0);
        child.setLayoutParams(lp);

        return true;
    }

    private View getDependentView() {
        return dependentView.get();
    }

}

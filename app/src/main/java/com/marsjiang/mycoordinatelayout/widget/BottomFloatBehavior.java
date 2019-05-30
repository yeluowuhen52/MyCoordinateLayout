package com.marsjiang.mycoordinatelayout.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.marsjiang.mycoordinatelayout.R;

import java.lang.ref.WeakReference;

/**
 * 底部导航栏Behavior
 * Created by Jiang on 2019/5/30.
 */
public class BottomFloatBehavior extends CoordinatorLayout.Behavior<ViewGroup> {

    private WeakReference<View> dependentView;
    //    private WeakReference<View> dependentViewRL;
    private ArgbEvaluator argbEvaluator;

    private float initPos;
    private float newTranslateY;
    private float progress;

    public BottomFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ViewGroup child, @NonNull View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header) {
            dependentView = new WeakReference<>(dependency);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ViewGroup child, @NonNull View dependency) {
        dependency.getTranslationY();
        initPos = parent.getHeight() - child.getHeight();

        progress = Math.abs(dependency.getTranslationY() / dependency.getHeight());

        if (progress == 0) {
            child.setTranslationY(parent.getHeight() - child.getHeight());
        }else{
            newTranslateY = getDependentView().getTranslationY();
            Log.d("newTranslateY", newTranslateY + "");
            child.setTranslationY(initPos - newTranslateY * progress);
        }

        return true;
    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewGroup child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//    }

//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewGroup child
//            , @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        Log.d("aaady", dy + "");
//        if (dy < 0) {
//            return;
//        }
////        float initPos = parent.getHeight() - child.getHeight();
//        newTranslateY = getDependentView().getTranslationY() - dy;
//        Log.d("newTranslateY", newTranslateY + "");
//        child.setTranslationY(initPos - newTranslateY * progress);
//
//    }

//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
//
//
//        View dependentView = getDependentView();
//        float newTranslateY = dependentView.getTranslationY() - dy;
//        float minHeaderTranslate = -(dependentView.getHeight() - getDependentViewCollapsedHeight());
//
//        if (newTranslateY > minHeaderTranslate) {
//            dependentView.setTranslationY(newTranslateY);
//            consumed[1] = dy;
//        }
//    }

//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewGroup child, @NonNull View target
//            , int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        if (dyUnconsumed > 0) {
//            return;
//        }
//        float newTranslateY = getDependentView().getTranslationY() - dyUnconsumed;
//        child.setTranslationY(initPos - newTranslateY * progress);
//    }

//    @Override
//    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        if (dyUnconsumed > 0) {
//            return;
//        }
//
//        View dependentView = getDependentView();
//        float newTranslateY = dependentView.getTranslationY() - dyUnconsumed;
//        final float maxHeaderTranslate = 0;
//
//        if (newTranslateY < maxHeaderTranslate) {
//            dependentView.setTranslationY(newTranslateY);
//        }
//    }


    private View getDependentView() {
        return dependentView.get();
    }

}

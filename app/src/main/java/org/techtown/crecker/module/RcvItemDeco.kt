package org.techtown.crecker.module

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RcvItemDeco(context : Context, isGrid : Boolean = false, size : Int = 20) : RecyclerView.ItemDecoration() {
    private val size_space : Int
    private val checking = isGrid

    init {
        size_space = dpToPx(context,size)
    }

    private fun dpToPx(context : Context, dp : Int) : Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        Log.d("good","goodItemOffsets")
        var position : Int = parent.getChildAdapterPosition(view) // 아이템의 포지션(왼쪽 오른쪽)을 받아옴
        var itemCount : Int = state.itemCount // 전체 아이템 개수를 받아옴

        // 상하 설정

        outRect.bottom = size_space



        // 그리드 뷰일 경우만 사용
        if (checking == true){
            val lp = view.layoutParams as GridLayoutManager.LayoutParams
//        spanIndex = 0 -> 그리드뷰에서 왼편
//        sapnIndex = 1 -> 그리드뷰에서 오른편
            val spanIndex = lp.spanIndex

            if (spanIndex == 0) {
                outRect.right = size_space / 2
            } else {
                outRect.left = size_space / 2
            }
        }
    }

}
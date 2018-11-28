package com.example.admin.constructionsite.secondpagepofadmin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.constructionsite.R;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;

import java.util.ArrayList;
import java.util.List;

public class siteadapter extends ArrayAdapter<SiteObject> {


    customButtonListener customListner;

    public interface customButtonListener {
         void onButtonClickListner(int position,String supervisorName);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }


    private Context context;
    private ArrayList<SiteObject> data = new ArrayList<SiteObject>();

    public siteadapter(Context context, ArrayList<SiteObject> dataItem) {
        super(context, R.layout.list_itemofsecondpage, dataItem);
        this.data = dataItem;
        this.context = context;
    }


    public class ViewHolder {

        TextView namofsitTextView;
        TextView areTextView;
        TextView supTextView;
        AllAngleExpandableButton button;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_itemofsecondpage, null);

            viewHolder = new ViewHolder();
            viewHolder.namofsitTextView =  convertView
                    .findViewById(R.id.siname);
            viewHolder.areTextView =  convertView
                    .findViewById(R.id.aname);
            viewHolder.supTextView =  convertView
                    .findViewById(R.id.supername);

            viewHolder.button = (AllAngleExpandableButton) convertView
                    .findViewById(R.id.button_expandable);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final SiteObject currentWord = getItem(position);

//
//        TextView catTextView = listItemView.findViewById(R.id.txtforcategory);
//        catTextView.setText(currentWord.getCategoryName());


        viewHolder.namofsitTextView.setText(currentWord.getNameOfSite());


        viewHolder.areTextView.setText(currentWord.getArea());


        viewHolder.supTextView.setText(currentWord.getSupervisorName());

        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.more,R.drawable.laboricon,R.drawable.equipment,R.drawable.todo,R.drawable.requirement,R.drawable.report};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(context, drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        viewHolder.button.setButtonDatas(buttonDatas);
        viewHolder.button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                //do whatever you want,the param index is counted from startAngle to endAngle,
                //the value is from 1 to buttonCount - 1(buttonCount if aebIsSelectionMode=true)

                if (customListner != null) {

                    String supervisorName =currentWord.getSupervisorName();

                    customListner.onButtonClickListner(index,supervisorName);
                }

            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });








        setTvZoomInOutAnimation(viewHolder.areTextView);
        setTvZoomInOutAnimation(viewHolder.supTextView);

        return convertView;
    }

    private void setTvZoomInOutAnimation(final TextView tv) {



        final float startSize = 22;
        final float middleSize = 26;
        final float endSize = 22;
        final int animationDuration = 1750; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, middleSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (Float) valueAnimator.getAnimatedValue();
                tv.setTextSize(animatedValue);

            }
        });

        //animator.setRepeatCount(ValueAnimator.INFINITE);  // Use this line for infinite animations
        animator.setRepeatCount(0);
        animator.start();
    }
}

package com.svjadhavcontractors.secondpagepofadmin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.svjadhavcontractors.R;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

public class siteadapter extends ArrayAdapter<SiteObject> {


    customButtonListener customListner;
    public static String area,Nameofsite,supervisorName;
    private ArrayList<Integer> imageresourceid=new ArrayList<>();
    private ArrayList<String> stringresourceid=new ArrayList<>();

    public interface customButtonListener {
         void onButtonClickListner(int position,String supervisorName);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }


    private Context context;
    private ArrayList<SiteObject> data = new ArrayList<>();

    public siteadapter(Context context, ArrayList<SiteObject> dataItem) {
        super(context, R.layout.list_itemofsecondpage, dataItem);
        this.data = dataItem;
        this.context = context;
    }


    public class ViewHolder {

        TextView namofsitTextView;
        TextView areTextView;
        TextView supTextView;
        BoomMenuButton button;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_itemofsecondpage,parent, false);

            viewHolder = new ViewHolder();
            viewHolder.namofsitTextView =  convertView
                    .findViewById(R.id.siname);
            viewHolder.areTextView =  convertView
                    .findViewById(R.id.aname);
            viewHolder.supTextView =  convertView
                    .findViewById(R.id.supername);

            viewHolder.button = (BoomMenuButton) convertView
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

        setdata();
        viewHolder.button.clearBuilders();
        for (int i = 0; i < viewHolder.button.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(imageresourceid.get(i))
                    .normalText(stringresourceid.get(i))
                    .textSize(18)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (customListner != null) {
                                area =currentWord.getArea();
                                Nameofsite =currentWord.getNameOfSite();
                                supervisorName =currentWord.getSupervisorName();

                                customListner.onButtonClickListner(index,supervisorName);
                            }

                        }
                    });

            viewHolder.button.addBuilder(builder);
        }



//      I will add here another animation.
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

    public void setdata() {
        imageresourceid.add(R.drawable.laboricon);
        imageresourceid.add(R.drawable.equipment);
        imageresourceid.add(R.drawable.todo);
        imageresourceid.add(R.drawable.requirement);
        imageresourceid.add(R.drawable.report);
        imageresourceid.add(R.drawable.ic_store);
        stringresourceid.add("Manpower Used Today");
        stringresourceid.add("Equipment Info");
        stringresourceid.add("Task To Engineer");
        stringresourceid.add("Requirement From Engineer");
        stringresourceid.add("Today's Report");
        stringresourceid.add("Material Used");
    }
}

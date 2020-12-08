package com.example.btvn4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.btvn4.R;
import com.example.btvn4.model.Email;

import java.util.ArrayList;
import java.util.List;

public class EmailAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<Email> list;
    List<Email> listfull;

    private SearchFilter searchFilter;

    public EmailAdapter(Context context, List<Email> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_email, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleImage = convertView.findViewById(R.id.titleImage);
            viewHolder.nameEmail = convertView.findViewById(R.id.nameEmail);
            viewHolder.titleContent = convertView.findViewById(R.id.titleContent);
            viewHolder.content = convertView.findViewById(R.id.content);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.star = convertView.findViewById(R.id.star);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        final Email email = list.get(position);
        viewHolder.titleImage.setText(email.getNameEmail().substring(0,1));
        viewHolder.nameEmail.setText(email.getNameEmail());
        viewHolder.content.setText(email.getContent());
        viewHolder.time.setText(email.getTime());
        viewHolder.titleContent.setText(email.getTitleContent());

        if(viewHolder.star.isChecked()==true){
            viewHolder.star.setButtonDrawable( R.drawable.ic_baseline_star_24);
        }else
            viewHolder.star.setButtonDrawable(R.drawable.ic_baseline_star_border_24);

        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setCheck(viewHolder.star.isChecked());
            }
        });





        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(searchFilter==null) {
            searchFilter=new SearchFilter();
        }
        return searchFilter;
    }

    private class SearchFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            //listfull
            if(constraint == null || constraint.length() == 0){
                result.values = listfull;
                result.values = listfull.size();
            }else {
                ArrayList<Email> filterlist = new ArrayList<>();
                for(Email j: listfull ){
                    if(j.getTitleContent().toLowerCase().contains(constraint) ||j.getNameEmail().contains(constraint)){
                        filterlist.add(j);
                    }
                }
                result.values = filterlist;
                result.count = filterlist.size();
            }
            return result;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count == 0) {
                notifyDataSetChanged();
            }else {
                list = (ArrayList<Email>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder{
        TextView titleImage, nameEmail, titleContent, content, time;
        CheckBox star;
        //boolean checkStar;
    }
}

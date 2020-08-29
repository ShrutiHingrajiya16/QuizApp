package com.example.quizapp_saasjoy.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_saasjoy.Bean.Result;
import com.example.quizapp_saasjoy.Bean.SelectedOptions;
import com.example.quizapp_saasjoy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.Myholder> {

    List<Result> arrayList;
    ArrayList<SelectedOptions> arrayListAnswers;

    private Context c;

    public QuestionListAdapter(Context c, List<Result> arrayList, ArrayList<SelectedOptions> arrayListAnswers) {
        this.c = c;
        this.arrayList = arrayList;
        this.arrayListAnswers = arrayListAnswers;
    }

    public ArrayList<SelectedOptions> getSelectedOptions() {
        return arrayListAnswers;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_question_item, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        holder.txtQuestionIndex.setText("Q." + (position + 1) + ".");
        holder.txtQuestion.setText(Html.fromHtml(arrayList.get(position).getQuestion()).toString());
        holder.rdoOption1.setText(Html.fromHtml(arrayList.get(position).getCorrectAnswer()).toString());
        holder.rdoOption2.setText(Html.fromHtml(arrayList.get(position).getIncorrectAnswers().get(0)).toString());
        holder.rdoOption3.setText(Html.fromHtml(arrayList.get(position).getIncorrectAnswers().get(1)).toString());
        holder.rdoOption4.setText(Html.fromHtml(arrayList.get(position).getIncorrectAnswers().get(2)).toString());

        holder.txtQuestionIndex.getBackground().setColorFilter(c.getResources().getColor(R.color.color7), PorterDuff.Mode.SRC_ATOP);

        if (arrayListAnswers.get(position).getValue() == 1) {
            holder.txtQuestionIndex.getBackground().setColorFilter(c.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
        } else if (arrayListAnswers.get(position).getValue() > 1) {
            holder.txtQuestionIndex.getBackground().setColorFilter(c.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.txtQuestionIndex.getBackground().setColorFilter(c.getResources().getColor(R.color.color7), PorterDuff.Mode.SRC_ATOP);
        }

        holder.rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdoOption1) {

                    SelectedOptions item = arrayListAnswers.get(position);
                    item.setPosition(item.getPosition());
                    item.setValue(1);
                    arrayListAnswers.set(item.getPosition(), item);

                } else if (checkedId == R.id.rdoOption2) {

                    SelectedOptions item = arrayListAnswers.get(position);
                    item.setPosition(item.getPosition());
                    item.setValue(2);
                    arrayListAnswers.set(item.getPosition(), item);

                } else if (checkedId == R.id.rdoOption3) {

                    SelectedOptions item = arrayListAnswers.get(position);
                    item.setPosition(item.getPosition());
                    item.setValue(3);
                    arrayListAnswers.set(item.getPosition(), item);

                } else if (checkedId == R.id.rdoOption4) {

                    SelectedOptions item = arrayListAnswers.get(position);
                    item.setPosition(item.getPosition());
                    item.setValue(4);
                    arrayListAnswers.set(item.getPosition(), item);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtQuestion)
        TextView txtQuestion;
        @BindView(R.id.rdoOption1)
        RadioButton rdoOption1;
        @BindView(R.id.rdoOption2)
        RadioButton rdoOption2;
        @BindView(R.id.rdoOption3)
        RadioButton rdoOption3;
        @BindView(R.id.rdoOption4)
        RadioButton rdoOption4;
        @BindView(R.id.rdoGroup)
        RadioGroup rdoGroup;
        @BindView(R.id.txtQuestionIndex)
        TextView txtQuestionIndex;

        public Myholder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

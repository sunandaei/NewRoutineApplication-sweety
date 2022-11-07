package com.sunanda.newroutine.application.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;

import java.util.List;

public class SanitarySurveyQuesAns_Adapter extends RecyclerView.Adapter<SanitarySurveyQuesAns_Adapter.ViewHolder> {

    private List<CommonModel> questionModels;


    public void setOnOptionSelected(OnOptionSelected onOptionSelected) {
        this.onOptionSelected = onOptionSelected;
    }

    private OnOptionSelected onOptionSelected;


    public List<CommonModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(List<CommonModel> questionModels) {
        this.questionModels = questionModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestion;
        RadioButton op1, op2;

        ViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            op1 = view.findViewById(R.id.radoptionOne);
            op2 = view.findViewById(R.id.radoptionTwo);
            op1.setOnClickListener(this);
            op2.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radoptionOne:
                    onOptionSelected.onOptionSelected(getAdapterPosition(), 1);
                    break;

                case R.id.radoptionTwo:
                    onOptionSelected.onOptionSelected(getAdapterPosition(), 2);
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        // create a normal view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_qus_ans_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvQuestion.setText(questionModels.get(position).getQuestions());

        viewHolder.op1.setChecked(questionModels.get(position).isOp1Sel());
        viewHolder.op2.setChecked(questionModels.get(position).isOp2Sel());
    }

    @Override
    public int getItemCount() {
        if (questionModels != null) {
            return questionModels.size();
        }
        return 0;
    }
}
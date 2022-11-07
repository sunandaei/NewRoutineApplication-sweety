package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class ExsitingSanitarySurveyQuesAns_Adapter extends RecyclerView.Adapter<ExsitingSanitarySurveyQuesAns_Adapter.ViewHolder> {

    private ArrayList<CommonModel> questionModels;
    private Context cContext;
    private SampleModel sampleModel;
    String sTaskIdx = "";


    public void setOnOptionSelected(OnOptionSelected onOptionSelected) {
        this.onOptionSelected = onOptionSelected;
    }

    private OnOptionSelected onOptionSelected;


    public ArrayList<CommonModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(Context context, ArrayList<CommonModel> questionModels) {
        this.questionModels = questionModels;
        this.cContext = context;
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

        sampleModel = new SampleModel();
        int id = CGlobal.getInstance().getPersistentPreference(cContext)
                .getInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, 0);
        DatabaseHandler databaseHandler = new DatabaseHandler(cContext);
        /*try {
            sTaskIdx = databaseHandler.getTaskIdOne();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String sFCID = CGlobal.getInstance().getPersistentPreference(cContext)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
        sampleModel = databaseHandler.getSanitarySurveyEdit(id, sFCID);


        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_1())) {
            if (sampleModel.getQuestionsid_1().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_1())) {
                    if (sampleModel.getAns_W_S_Q_1().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_2())) {
            if (sampleModel.getQuestionsid_2().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_2())) {
                    if (sampleModel.getAns_W_S_Q_2().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_3())) {
            if (sampleModel.getQuestionsid_3().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_3())) {
                    if (sampleModel.getAns_W_S_Q_3().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_4())) {
            if (sampleModel.getQuestionsid_4().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_4())) {
                    if (sampleModel.getAns_W_S_Q_4().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_5())) {
            if (sampleModel.getQuestionsid_5().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_5())) {
                    if (sampleModel.getAns_W_S_Q_5().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_6())) {
            if (sampleModel.getQuestionsid_6().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_6())) {
                    if (sampleModel.getAns_W_S_Q_6().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_7())) {
            if (sampleModel.getQuestionsid_7().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_7())) {
                    if (sampleModel.getAns_W_S_Q_7().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_8())) {
            if (sampleModel.getQuestionsid_8().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_8())) {
                    if (sampleModel.getAns_W_S_Q_8().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_9())) {
            if (sampleModel.getQuestionsid_9().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_9())) {
                    if (sampleModel.getAns_W_S_Q_9().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_10())) {
            if (sampleModel.getQuestionsid_10().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_10())) {
                    if (sampleModel.getAns_W_S_Q_10().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getQuestionsid_11())) {
            if (sampleModel.getQuestionsid_11().equalsIgnoreCase(questionModels.get(position).getQuestionid())) {
                if(!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_11())) {
                    if (sampleModel.getAns_W_S_Q_11().equalsIgnoreCase("1")) {
                        viewHolder.op1.setChecked(true);
                    } else {
                        viewHolder.op2.setChecked(true);
                    }
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        if (questionModels != null) {
            return questionModels.size();
        }
        return 0;
    }
}
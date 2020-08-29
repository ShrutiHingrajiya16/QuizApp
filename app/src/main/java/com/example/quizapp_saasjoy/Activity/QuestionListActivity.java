package com.example.quizapp_saasjoy.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_saasjoy.Adapter.QuestionListAdapter;
import com.example.quizapp_saasjoy.Bean.QuestionListMain;
import com.example.quizapp_saasjoy.Bean.Result;
import com.example.quizapp_saasjoy.Bean.SelectedOptions;
import com.example.quizapp_saasjoy.Dialog.ResultDialog;
import com.example.quizapp_saasjoy.R;
import com.example.quizapp_saasjoy.Retrofit.ApiClient;
import com.example.quizapp_saasjoy.Retrofit.ApiInterface;
import com.example.quizapp_saasjoy.Utils.CommonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.quizapp_saasjoy.Utils.CommonUtils.showSnackBar;

public class QuestionListActivity extends AppCompatActivity {

    @BindView(R.id.rcvQuestions)
    RecyclerView rcvQuestions;
    @BindView(R.id.btnReset)
    TextView btnReset;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    QuestionListAdapter adapter;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.lnMainData)
    LinearLayout lnMainData;
    @BindView(R.id.lnNoData)
    LinearLayout lnNoData;

    ArrayList<SelectedOptions> arrayListAnswers = new ArrayList<>();
    List<Result> arrayListQuestions = new ArrayList<>();
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        ButterKnife.bind(this);

        this.setUp();
        this.setListeners();
    }

    private void setListeners() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCallForGetQuestionsList();
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int counterCorrect = 0;
                int counterWrong = 0;
                for (int i = 0; i < adapter.getSelectedOptions().size(); i++) {
                    if (adapter.getSelectedOptions().get(i).getValue() == 1) {
                        counterCorrect++;
                    } else if (adapter.getSelectedOptions().get(i).getValue() > 1) {
                        counterWrong++;
                    }
                }

                ResultDialog dialog = new ResultDialog();
                dialog.openDialog(QuestionListActivity.this, adapter.getSelectedOptions().size(), counterCorrect, counterWrong);

                adapter.notifyDataSetChanged();

                btnSubmit.setVisibility(View.GONE);
            }
        });
    }

    private void setUp() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiCallForGetQuestionsList();
    }

    private void apiCallForGetQuestionsList() {
        if (!CommonUtils.isNetworkAvailable(QuestionListActivity.this)) {
            showSnackBar(QuestionListActivity.this, getResources().getString(R.string.INTERNET_ERROR));
            showNoData();

        } else {

            lnNoData.setVisibility(View.GONE);
            lnMainData.setVisibility(View.GONE);
            ProgressDialog pd = ProgressDialog.show(QuestionListActivity.this, "", "Loading...");

            Call<QuestionListMain> call = apiService.questionList("10", "18", "medium", "multiple");
            call.enqueue(new Callback<QuestionListMain>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(Call<QuestionListMain> call, Response<QuestionListMain> response) {
                    pd.dismiss();
                    Log.e("Response_Que_List_", new Gson().toJson(response.body()) + "___");

                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 0) {
                            if (response.body().getResults().size() > 0) {
                                arrayListQuestions = new ArrayList<>();
                                arrayListQuestions.addAll(response.body().getResults());
                                setData(arrayListQuestions);
                                showMainData();
                            } else {
                                showNoData();
                            }
                        } else {
                            showNoData();
                        }
                    } else {
                        showNoData();
                    }
                }

                @Override
                public void onFailure(Call<QuestionListMain> call, Throwable t) {
                    Log.e("Error", t.getMessage() + "");
                    pd.dismiss();
                    showNoData();
                    showSnackBar(QuestionListActivity.this, t.getMessage() + "");
                }
            });
        }
    }

    private void setData(List<Result> arrayListQuestions) {

        arrayListAnswers = new ArrayList<>();
        for (int i = 0; i < arrayListQuestions.size(); i++) {

            SelectedOptions item = new SelectedOptions();
            item.setPosition(i);
            item.setValue(0);
            arrayListAnswers.add(item);
        }

        adapter = new QuestionListAdapter(QuestionListActivity.this, arrayListQuestions, arrayListAnswers);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(QuestionListActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvQuestions.setLayoutManager(linearLayoutManager2);
        rcvQuestions.setAdapter(adapter);
    }

    private void showMainData() {
        lnMainData.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);
    }

    private void showNoData() {
        lnMainData.setVisibility(View.GONE);
        lnNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}

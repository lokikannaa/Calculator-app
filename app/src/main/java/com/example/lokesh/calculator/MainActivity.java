package com.example.lokesh.calculator;
/*
Assignment: Homework 01
FileName: HW1.zip
Group: 19
Name: Lokesh Kannan, 800941529
      Umasankar Srinivas Varma Pusapati, 800960736
 */
import android.icu.text.DecimalFormat;
import android.renderscript.Double2;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int[] numericButtonsID = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    int[] operatorButtonsID = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};
    TextView tvDisplay;
    String result;
    String tempResult;
    boolean hasDot;
    boolean hasDot2;
    boolean isLastNmber;
    boolean isSecondNumber;
    boolean initialState = true;

    boolean addFlag;
    boolean subtractFlag;
    boolean multiplyFlag;
    boolean divideFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        setOnClickListnerNumericButtons();
        setOnClickListnerOperatorButtons();
    }

    private void setOnClickListnerNumericButtons() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (initialState) {
                    tvDisplay.setText(btn.getText());
                    initialState = false;
                    isLastNmber = true;
                } else if (isLastNmber) {
                    tvDisplay.append(btn.getText().toString());
                    isLastNmber = true;
                } else if (!isLastNmber && (addFlag || subtractFlag || divideFlag || multiplyFlag)) {
                    tvDisplay.setText(btn.getText());
                    isLastNmber = true;
                    //isSecondNumber = false;
                }
            }
        };
        for (int id : numericButtonsID) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOnClickListnerOperatorButtons() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (!initialState) {
                    if (btn.getId() == R.id.btnAdd) {
                        addFlag = true;
                        subtractFlag = false;
                        multiplyFlag = false;
                        divideFlag = false;
                        isLastNmber = false;
                        hasDot = false;
                    } else if (btn.getId() == R.id.btnSubtract) {
                        addFlag = false;
                        subtractFlag = true;
                        multiplyFlag = false;
                        divideFlag = false;
                        isLastNmber = false;
                        hasDot = false;
                    } else if (btn.getId() == R.id.btnDivide) {
                        addFlag = false;
                        subtractFlag = false;
                        multiplyFlag = false;
                        divideFlag = true;
                        isLastNmber = false;
                        hasDot = false;
                    } else if (btn.getId() == R.id.btnMultiply) {
                        addFlag = false;
                        subtractFlag = false;
                        multiplyFlag = true;
                        divideFlag = false;
                        isLastNmber = false;
                        hasDot = false;
                    }
                } else {
                    if (btn.getId() == R.id.btnSubtract) {
                        tvDisplay.setText("-");
                        isLastNmber = true;
                        initialState = false;
                    }
                }
                tempResult = tvDisplay.getText().toString();
            }
        };
        for (int id : operatorButtonsID) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLastNmber && !initialState && !hasDot) {
                    tvDisplay.append(".");
                    hasDot = true;
                    isLastNmber = true;
                }
            }
        });

        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isLastNmber && tempResult != null && (addFlag || subtractFlag || multiplyFlag || divideFlag)) {
                        String secondNumber = tvDisplay.getText().toString();
                        int inputsize = Math.max(tempResult.length(), secondNumber.length());
                        if (addFlag) {
                            result = String.valueOf(Double.parseDouble(tempResult) + Double.parseDouble(secondNumber));
                        }
                        if (subtractFlag) {
                            result = String.valueOf(Double.parseDouble(tempResult) - Double.parseDouble(secondNumber));
                        }
                        if (multiplyFlag) {
                            result = String.valueOf(Double.parseDouble(tempResult) * Double.parseDouble(secondNumber));
                        }
                        if (divideFlag) {
                            result = String.valueOf(Double.parseDouble(tempResult) / Double.parseDouble(secondNumber));
                        }
                    }
                    if (result != null && !result.equals("")) {
                        double d = Double.parseDouble(result);
                        String str = String.format("%.8f%n", d).trim();
                        while (str.charAt(str.length() - 1) == '0') {
                            str = str.substring(0, str.length() - 1);
                        }
                        if ((str.charAt(str.length() - 1) == '.')) {
                            str = str.substring(0, str.length() - 1);
                        }
                        int count = str.length();
                        if (str.contains(".")) {
                            count--;
                        }
                        if (count > 14) {
                            str = String.format("%.5E", Double.parseDouble(str));
                        }
                        tvDisplay.setText(str.trim());

                        hasDot = false;
                        isLastNmber = false;
                        addFlag = false;
                        subtractFlag = false;
                        multiplyFlag = false;
                        divideFlag = false;
                    }
                } catch (Exception ex) {
                    tvDisplay.setText("Error");
                }
            }
        });

        findViewById(R.id.btnAC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "";
                tvDisplay.setText("0");
                hasDot = false;
                isLastNmber = false;
                initialState = true;
                addFlag = false;
                subtractFlag = false;
                multiplyFlag = false;
                divideFlag = false;
            }
        });
    }
}

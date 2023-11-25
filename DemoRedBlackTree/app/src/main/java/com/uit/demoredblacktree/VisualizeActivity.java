package com.uit.demoredblacktree;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


enum PRINT_OPTION {
    PREORDER, INORDER, POSTORDER, PRINT2D
}

public class VisualizeActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    EditText number;
    TextView result;
    Button insert;
    Button remove;
    RadioGroup print;
    CheckBox colors;

    PRINT_OPTION print_option = PRINT_OPTION.PRINT2D;
    boolean color = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);
        init();

        number = findViewById(R.id.number);
        result = findViewById(R.id.result);
        insert = findViewById(R.id.insertbtn);
        remove = findViewById(R.id.removebtn);
        print = findViewById(R.id.print);
        colors = findViewById(R.id.colors);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(number.getText().toString());
                insert(x);
                print();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(number.getText().toString());
                delete(x);
                print();
            }
        });

        colors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                color = isChecked;
                print();
            }
        });

        print.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.printin) {
                    print_option = PRINT_OPTION.INORDER;
                } else if(checkedId == R.id.printpre) {
                    print_option = PRINT_OPTION.PREORDER;
                } else if(checkedId == R.id.printpost) {
                    print_option = PRINT_OPTION.POSTORDER;
                } else if(checkedId == R.id.print2d) {
                    print_option = PRINT_OPTION.PRINT2D;
                } else  {
                    print_option = PRINT_OPTION.PRINT2D;
                }
                print();
            }
        });
    }

    /*
     * ---------------------------------------------------------
     * Native Methods
     * ---------------------------------------------------------
     */

    public void print() {
        String s = "";
        switch (print_option) {
            case INORDER:
                if(color) {
                    s = printInorder(0);
                } else  {
                    s = printInorder(1);
                }
                break;
            case PREORDER:
                if(color) {
                    s = printPreorder(0);
                } else  {
                    s = printPreorder(1);
                }
                break;
            case POSTORDER:
                if(color) {
                    s = printPostorder(0);
                } else  {
                    s = printPostorder(1);
                }
                break;
            case PRINT2D:
                if(color) {
                    s = print2d(0);
                } else  {
                    s = print2d(1);
                }
                break;
        }
        result.setText(s);
    }

    public native int init();
    public native String insert(int n);
    public native String delete(int n);
    public native String print2d(int n);
    public native String printInorder(int n);
    public native String printPreorder(int n);
    public native String printPostorder(int n);
}

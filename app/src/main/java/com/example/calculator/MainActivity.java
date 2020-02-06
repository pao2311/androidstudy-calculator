package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
            {
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
            }
            break;
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_add:
            case R.id.btn_sub:
            case R.id.btn_mul:
            case R.id.btn_div:
            case R.id.btn_dot:{
                Button btn = (Button) view;
                String strAdded = btn.getText().toString();
                TextView formula = (TextView) findViewById(R.id.formula_area);
                String strContent = formula.getText().toString();
                String strNewContent = strContent + strAdded;
                formula.setText(strNewContent);
            }
            break;

            case R.id.btn_c:{
                TextView formula = (TextView) findViewById(R.id.formula_area);
                formula.setText("");

                TextView result = (TextView) findViewById(R.id.result_area);
                result.setText("");
            }
            break;

            case R.id.btn_del:{
                TextView formula = (TextView) findViewById(R.id.formula_area);
                String strContent = formula.getText().toString();
                if(strContent.length() > 0){
                    strContent = strContent.substring(0, strContent.length() - 1);
                    formula.setText(strContent);
                }
            }
            break;
            case R.id.btn_equ:{
                TextView formula = (TextView) findViewById(R.id.formula_area);
                String strContent = formula.getText().toString();

                try{
                    Symbols s = new Symbols();
                    double res = s.eval(strContent);

                    TextView result = (TextView) findViewById(R.id.result_area);
                    result.setText(String.valueOf(res));

                    Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
                    result.startAnimation(fadeIn);

                    Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
                    result.startAnimation(fadeOut);

                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            TextView formula = (TextView) findViewById(R.id.formula_area);
                            formula.setText("");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
                catch (SyntaxException e){
                    String str = MainActivity.this.getString(R.string.error_info);
                    Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView formula = (TextView) findViewById(R.id.formula_area);
        String strFormula = formula.getText().toString();
        outState.putString("KEY_FORMULA_AREA", strFormula);

        TextView result = (TextView) findViewById(R.id.result_area);
        String strResult = result.getText().toString();
        outState.putString("KEY_RESULT_AREA", strResult);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        TextView formula = (TextView) findViewById(R.id.formula_area);
        String strFormula = savedInstanceState.getString("KEY_FORMULA_AREA");
        formula.setText(strFormula);

        TextView result = (TextView) findViewById(R.id.result_area);
        String strResult = savedInstanceState.getString("KEY_RESULT_AREA");
        result.setText(strResult);
    }


}

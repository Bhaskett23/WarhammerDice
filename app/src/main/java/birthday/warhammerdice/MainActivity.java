package birthday.warhammerdice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int toHit;
    private int toWound;
    private int numberOfDice;
    private String error;

    private MathEngine mathEngine;
    EditText txtNumberOfDice;
    EditText txtToHit;
    EditText txtToWound;
    CheckBox chkReroll1Hits;
    CheckBox chkRerollAllHits;
    CheckBox chkReroll1Wounds;
    CheckBox chkRerollAllWounds;
    FloatingActionButton fab;
    TextView txtResultDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        chkReroll1Hits = findViewById(R.id.chkReroll1Hits);
        chkRerollAllHits = findViewById(R.id.chkRerollAllHits);
        chkReroll1Wounds = findViewById(R.id.chkReroll1Wound);
        chkRerollAllWounds = findViewById(R.id.chkRerollAllWounds);
        txtNumberOfDice = findViewById(R.id.txtNumberOfDice);
        txtToHit = findViewById(R.id.txtHit);
        txtToWound = findViewById(R.id.txtWound);
        txtResultDisplay = findViewById(R.id.txtResult);
        setSupportActionBar(toolbar);
        mathEngine = new MathEngine();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getResults();
                Snackbar.make(view, "Calculating Results", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getResults() {

        if(checkInput())
        {
            boolean reRollOnesToHit = chkReroll1Hits.isChecked();
            boolean reRollOnesToWound = chkReroll1Wounds.isChecked();
            boolean reRollAllHits = chkRerollAllHits.isChecked();
            boolean reRollAllWounds = chkRerollAllWounds.isChecked();

            RollResultModel result =  mathEngine.getRollResult(numberOfDice, toHit, toWound, reRollOnesToHit, reRollAllHits, reRollOnesToWound, reRollAllWounds);
            txtResultDisplay.setTextColor(Color.BLACK);

            String z = "Number of Hits: " + result.getHits() + "\n" + "Number of Wounds: " + result.getWounds();

            txtResultDisplay.setText(z);
        }
        else
        {
            txtResultDisplay.setTextColor(Color.RED);
            txtResultDisplay.setText(error);
        }

    }

    private boolean checkInput() {

        boolean isValid = true;

        if (txtNumberOfDice.getText().toString().isEmpty()) {
            isValid = false;
            error = "Number of dice can not be empty";
        }
        else {
            numberOfDice = Integer.parseInt(txtNumberOfDice.getText().toString());
            if(numberOfDice == 0)
            {
                error = "Number of dice can not be 0";
                isValid = false;
            }
        }

        if(txtToHit.getText().toString().isEmpty())
        {
            error += ", to hit must have value";
            isValid = false;
        }
        else {
            toHit = Integer.parseInt(txtToHit.getText().toString());
            if (toHit > 6)
            {
                error += ", to hit must be 6 or less";
                isValid = false;
            }
        }

        if(txtToWound.getText().toString().isEmpty())
        {
            error += ", to wound must have value";
            isValid = false;
        }
        else
        {
            toWound = Integer.parseInt(txtToWound.getText().toString());
            if(toWound > 6)
            {
                error += ", to wound must be 6 or less";
                isValid = false;
            }
        }

        return isValid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

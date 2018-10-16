package fr.thatmuch.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PongActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TextView myText = new TextView(this);
        myText.setText("Toto");
        setContentView(myText);
    }
}

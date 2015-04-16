package library;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.zoof.zoofzoof.R;

public class PhotoTimer {
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

    public void run() {
            startTimer();
        }
    };

    /** Called when the activity is first created. */

    @Override
    public void onCreate() {
        runnable.run();
    }

    public void startTimer(){

        handler.postDelayed(runnable, 1000);
    }
}

}

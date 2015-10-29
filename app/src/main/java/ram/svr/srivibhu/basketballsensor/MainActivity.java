package ram.svr.srivibhu.basketballsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager senSensorManager;
    private Sensor senaccelorometer;
    long lastUpdate = 0;
    float x,y,z;

    @Override
    public void onSensorChanged (SensorEvent event){
        Sensor mysensor = event.sensor;

        if (mysensor.getType()==Sensor.TYPE_ACCELEROMETER){
            long Curtime = System.currentTimeMillis();
            if ((Curtime - lastUpdate)>1000){
                lastUpdate = Curtime;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

            }
            senSensorManager.registerListener(this, senaccelorometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int accuracy){

    }

    public void start_bt (View View){

        senSensorManager.registerListener(this, senaccelorometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause_bt (View View){
        TextView xval = (TextView) findViewById(R.id.xval);
        TextView yval = (TextView) findViewById(R.id.yval);
        TextView zval = (TextView) findViewById(R.id.zval);
        senSensorManager.unregisterListener(this);
        xval.setText(Float.toString(x));
        yval.setText(Float.toString(y));
        zval.setText(Float.toString(z));

        if ((x>0 & x<5) && (y>2 & y< 10)){
            Intent i = new Intent (this, BBall.class);
            startActivity(i);
        }
        else {
            Intent i2 = new Intent(this, BasketMiss.class);
            startActivity(i2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senaccelorometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senaccelorometer, SensorManager.SENSOR_DELAY_NORMAL);
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

    @Override
    public void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        senSensorManager.registerListener(this, senaccelorometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}

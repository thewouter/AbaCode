package nl.wouter.Abacode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
  private static String[] PASSWORD = {"xxx", "yyy"};
  
  private TextView code;
  
  private DatePicker datePicker;
  
  private EditText password;
  
  static  {
  
  }
  
  private int hash(int paramInt, String password) {
    int i;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(password);
    String str = stringBuilder.toString();
    byte b = 0;
    try {
      byte[] arrayOfByte = MessageDigest.getInstance("SHA-256").digest(str.getBytes(Charset.forName("UTF-8")));
      int j = arrayOfByte.length;
      paramInt = 0;
      while (true) {
        i = paramInt;
        if (b < j) {
          i = arrayOfByte[b];
          try {
            i = Math.abs(i * i);
            paramInt += i;
            b++;
            continue;
          } catch (Exception e) {
            // Byte code: goto -> 114
          } 
        } 
        break;
      } 
    } catch (NoSuchAlgorithmException e) {
      paramInt = 0;
      e.printStackTrace();
      i = paramInt;
    } 
    return i % 10000;
  }
  
  protected void dateChanged(Calendar paramCalendar) {
    String str = this.password.getText().toString();
    int i = paramCalendar.get(Calendar.WEEK_OF_YEAR);
    List<String> list = Arrays.asList(PASSWORD);
    if (list.contains(str)) {
      i = hash(i,str);
      this.code.setText(String.format("%04d", Integer.valueOf(i)));
      return;
    } 
    this.code.setText(getResources().getString(R.string.defaultCode));
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    this.datePicker = findViewById(R.id.date);
    this.code = findViewById(R.id.code);
    this.password = findViewById(R.id.password);
    Calendar calendar = Calendar.getInstance(Locale.US);
    this.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
          public void onDateChanged(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(param1Int1, param1Int2, param1Int3);
            gregorianCalendar.setMinimalDaysInFirstWeek(7);
            MainActivity.this.dateChanged(gregorianCalendar);
          }
        });
  }
}


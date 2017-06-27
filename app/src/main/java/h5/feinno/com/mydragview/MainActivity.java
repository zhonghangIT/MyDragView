package h5.feinno.com.mydragview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DragViewContainer mDragViewContainer;
    private DragView mDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDragView = (DragView) findViewById(R.id.drag);
        mDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDragView.setChecked(true);
                Toast.makeText(MainActivity.this, "测试点击事件", Toast.LENGTH_SHORT).show();
            }
        });
        mDragViewContainer = (DragViewContainer) findViewById(R.id.dragcontainer);
        mDragViewContainer.setBackgroundResource(R.mipmap.ic_launcher);
        mDragViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDragView.setChecked(false);
                Toast.makeText(MainActivity.this, "容器点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

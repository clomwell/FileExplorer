package com.example.tainio.fileexplorer;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<String> item = new ArrayList<>();
    private List<String> path = new ArrayList<>();
    private String mroot;
    private TextView myPath;
    private ListView filelv;
    private ArrayAdapter<String> fileList;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPath = (TextView)findViewById(R.id.path);
        filelv = (ListView) findViewById(R.id.lv_file);
        mroot = Environment.getExternalStorageDirectory().getParent();
        moveDir(mroot);
    }

    private void nowDir(final String dirPath) throws IOException {
        item.clear();//item = new ArrayList<String>();
        path.clear();//path = new ArrayList<String>();
        file = new File(dirPath);
        File[] mfilelist = file.listFiles();

        myPath.setText(dirPath);
        if(!dirPath.equals(Environment.getExternalStorageDirectory().getParent())){
            item.add("..");
        }

        for(int i=0; i < mfilelist.length; i++)
        {
            file = mfilelist[i];
            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    item.add("/" + file.getName());
                }else{
                    item.add(file.getName());
                }
            }
        }

        fileList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);

        filelv.setAdapter(fileList);
        filelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView ftv = (TextView) view;
                //Toast.makeText(getApplicationContext(), file.getAbsolutePath() + ftv.getText() , Toast.LENGTH_SHORT).show();
                mroot = dirPath + ftv.getText();
                moveDir(mroot);
                //Toast.makeText(getApplicationContext(), mroot , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveDir(String dirPath){
        try {
            nowDir(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
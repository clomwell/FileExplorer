package com.example.tainio.fileexplorer;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<String> item = null;
    private List<String> path = null;
    private String mroot;
    private TextView myPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPath = (TextView)findViewById(R.id.path);
        mroot = Environment.getExternalStorageDirectory().getParent();
        getDir(mroot);
    }

    private void getDir(String dirPath){
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File file = new File(dirPath);
        File[] mfilelist = file.listFiles();


        for(int i=0; i < mfilelist.length; i++)
        {
            file = mfilelist[i];

            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    item.add(file.getName() + "/");
                }else{
                    item.add(file.getName());
                }
            }
        }

        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        ListView listView = (ListView) findViewById(R.id.lv_file);
        listView. setAdapter(fileList);
        myPath.setText(file.getAbsolutePath());
    }

}
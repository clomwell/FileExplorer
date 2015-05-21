package com.example.tainio.fileexplorer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
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
    private String nowPath;
    private TextView myPath;
    private ListView filelv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPath = (TextView)findViewById(R.id.path);
        filelv = (ListView) findViewById(R.id.lv_file);
        nowPath = Environment.getExternalStorageDirectory().getParent();
        moveDir(nowPath);
    }

    private void nowDir(final String dirPath) throws IOException {
        item.clear();//item = new ArrayList<String>();
        File file = new File(dirPath);
        File[] mfilelist = file.listFiles();

        myPath.setText(dirPath);
        if(!dirPath.equals(Environment.getExternalStorageDirectory().getParent())){
            item.add("..");
        }

        for(int i=0; i < mfilelist.length; i++)        {
            file = mfilelist[i];
            if(!file.isHidden() && file.canRead()){
                if(file.isDirectory()){
                    item.add("/" + file.getName());
                }else{
                    item.add(file.getName());
                }
            }
        }

        ArrayAdapter<String> fileList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);

        filelv.setAdapter(fileList);
        filelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView ftv = (TextView) view;
                String[] dirDepth = nowPath.split("/");
                String selType;
                selType = String.valueOf(ftv.getText());
                if (selType.equals("..")) {
                    nowPath = "";
                    for (int i = 1; i < dirDepth.length - 1; i++) {
                        nowPath = nowPath + "/" + dirDepth[i];
                    }
                    moveDir(nowPath);
                } else {
                    File file = new File(dirPath + selType);
                    if (file.isDirectory()) {
                        nowPath = dirPath + selType;
                        moveDir(nowPath);
                    } else {
                        String fullPath = dirPath + "/" + selType;
                        fileExecute(fullPath);
                    }
                }
                Toast.makeText(getApplicationContext(), nowPath, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fileExecute(String fullPath) {
        File file = new File(fullPath);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fullPath.toString());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        Toast.makeText(getApplicationContext(), mimeType ,Toast.LENGTH_SHORT).show();

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.parse(fullPath.toString()),mimeType);
            intent.setDataAndType(Uri.fromFile(file),mimeType);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "���� �� ���� �����ϴ�.", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
            finish();
        }
    }

    private void moveDir(String dirPath){
        try {
            nowDir(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
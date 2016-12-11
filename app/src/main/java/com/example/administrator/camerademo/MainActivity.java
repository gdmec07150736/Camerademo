package com.example.administrator.camerademo;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private ImageView imagec;
    private File file;
    private Camera camera;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView msv=(SurfaceView) this.findViewById(R.id.sv);
        SurfaceHolder msh=msv.getHolder();
        msh.addCallback(this);
        msh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    public void takephoto(View v){
        camera.takePicture(null,null,pictureCallback);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera=Camera.open();
        android.hardware.Camera.Parameters param=camera.getParameters();
        param.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            camera.release();
            camera=null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    Camera.PictureCallback pictureCallback=new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if(data!=null){
                save(data);
            }
        }
    };
    public void save(byte[] data){
        try{
            String image=System.currentTimeMillis()+"";
            String path=android.os.Environment.getExternalStorageDirectory().getPath()+"/";
            File file=new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            path+=image+"jpeg";
            file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(data);
            fos.close();
            Toast.makeText(this,"保存在："+path,Toast.LENGTH_LONG).show();
        }catch(Exception e){

        }
    }
}

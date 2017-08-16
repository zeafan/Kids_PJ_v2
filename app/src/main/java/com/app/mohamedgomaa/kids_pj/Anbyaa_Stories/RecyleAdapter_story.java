package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.app.mohamedgomaa.kids_pj.CheckConnection_Internet;
import com.app.mohamedgomaa.kids_pj.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class RecyleAdapter_story extends RecyclerView.Adapter<RecyleAdapter_story.Holder> implements View.OnLongClickListener {
    List<item_story> _myList = new ArrayList<>();
    Context _context;
    ProgressDialog progressDialog;
    public RecyleAdapter_story(List<item_story> _myList, Context _context) {
        this._myList = _myList;
        this._context = _context;
    }
    void Initiazation_PBar()
    {
        progressDialog=new ProgressDialog(_context);
        progressDialog.setMessage("جارى التحميل .. ");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity__regition__anbyaa_story_item, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }
    public File mkFolder(String file_name) {
        File  Folder =new File("sdcard/Kids_folder");
        if(!Folder.exists())
        {
            Folder.mkdir();
        }
        File file=new File(Folder,file_name);
        return file;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.image.setImageResource(_myList.get(position)._img_id);
        if (_myList.get(position)._downlod == 0) {
            holder.child_Linear.setVisibility(View.VISIBLE);
            holder.Parent_Linear.setBackgroundResource(R.drawable.story_download1);
        } else if (_myList.get(position)._downlod == 1) {
            holder.child_Linear.setVisibility(View.GONE);
            holder.Parent_Linear.setBackgroundResource(R.drawable.story_download2);
        }
        holder.Parent_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_myList.get(position)._downlod==0) {
                    if (new CheckConnection_Internet(_context).IsConnection()) {
                        String Path = "https://zeafancom.000webhostapp.com/kids_story_pdf/file_" + String.valueOf(position) + ".pdf";
                        final Download_file download_file = new Download_file(_context, mkFolder("file_" + position + ".pdf"), position);
                        Initiazation_PBar();
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                download_file.cancel(true);
                            }
                        });
                        download_file.execute(Path);
                    } else {
                        Toast toast=new Toast(_context);
                        ImageView img=new ImageView(_context);
                        img.setImageResource(R.drawable.connect_internet);
                        toast.setView(img);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();                      }
                }
                else if(_myList.get(position)._downlod==1) {
                    File file = mkFolder("file_" + position + ".pdf");
                    if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(path, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        _context.startActivity(intent);
                    }
                    else
                    {
                        if (new CheckConnection_Internet(_context).IsConnection()) {
                            String Path = "https://zeafancom.000webhostapp.com/kids_story_pdf/file_" + String.valueOf(position) + ".pdf";
                            final Download_file download_file = new Download_file(_context, mkFolder("file_" + position + ".pdf"), position);
                            Initiazation_PBar();
                            download_file.execute(Path);
                        }
                        else {
                            Toast toast=new Toast(_context);
                            ImageView img=new ImageView(_context);
                            img.setImageResource(R.drawable.connect_internet);
                            toast.setView(img);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();                        }
                    }
                }

            }

        });
    }


    @Override
    public int getItemCount() {
        return _myList.size();
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
    public static class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout Parent_Linear, child_Linear;
        RelativeLayout relativeLayout;

        public Holder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.img_story_id);
            Parent_Linear = (LinearLayout) view.findViewById(R.id.Linear_story_item);
            child_Linear = (LinearLayout) view.findViewById(R.id.id_Linear_front);
            relativeLayout=(RelativeLayout)view.findViewById(R.id.relative);
        }
    }
    private class  Download_file extends AsyncTask<String,Integer,String>
    {
        int position;
        private Context con;
        private File _file;
        PowerManager.WakeLock myWakeLock;
        public Download_file(Context con,File file,int pos)
        {
            this._file=file;
            this.con = con;
            this.position=pos;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm= (PowerManager) con.getSystemService(con.POWER_SERVICE);
            myWakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
            myWakeLock.acquire();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("أنتهى التحميل بنجاح")) {
                item_story item = new item_story(_myList.get(position)._id, 1, _myList.get(position)._img_id);
                _myList.set(position, item);
                SharedPreferences db_download = _context.getSharedPreferences("my_File", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = db_download.edit();
                edit.putInt("db_file_" + String.valueOf(position), 1);
                edit.apply();
                progressDialog.dismiss();
                Activity_Regition_Anbyaa_story.recyleAdapter_story.notifyDataSetChanged();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream=null;
            OutputStream outputStream=null;
            HttpURLConnection connection=null;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                {
                    return "Server return hTTP "+connection.getResponseCode()+""+connection.getResponseMessage();
                }
                int fileLenght=connection.getContentLength();
                inputStream=connection.getInputStream();
                outputStream=new FileOutputStream(_file);
                byte[]data=new byte[4000];
                long total=0;
                int count;
                while ((count=inputStream.read(data))!=-1)
                {
                    total+=count;
                    if(fileLenght>0)
                    {
                        publishProgress((int)(total*100/fileLenght));
                        outputStream.write(data,0,count);
                    }
                }



            } catch (Exception e) {
                return e.getMessage();
            } finally {

                try {
                    if(outputStream!=null)
                        outputStream.close();
                    if (inputStream!=null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(connection!=null)
                {
                    connection.disconnect();
                }


            }
            return "أنتهى التحميل بنجاح";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
             progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }
    }

}

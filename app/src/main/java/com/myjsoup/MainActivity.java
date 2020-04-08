package com.myjsoup;

import android.app.Activity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.view.View;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.net.Uri;
import android.content.Intent;

public class MainActivity extends Activity 
{
	Document all;
	int type_都市=3;
	int type_玄幻=1;
	ListView lv;
	List<Bean> datalist=new ArrayList();
	int ispage=1;
	MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		adapter=new MyListAdapter(datalist,this);
		lv=findViewById(R.id.mainListView1);
		lv.setAdapter(adapter);
		doo(null);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				private Intent intent;

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					Uri uri = Uri.parse(datalist.get(p3).get跳转());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					
				}
			});
    }
	public void doo(View v){
		ispage=1;
		datalist.clear();
		getweb(type_都市,ispage);
	}
	public void las(View v){
		ispage++;
		getweb(type_都市,ispage);
	}
	String content;
	
	public void getweb(final int type,final int page){
		final String isgeturl="https://m.52bqg.com/wapsort/"+type+"_"+page+".html";
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					try{
						URL url = new URL(isgeturl);
						HttpURLConnection conn=(HttpURLConnection)url.openConnection();
						conn.setRequestMethod("POST");
						conn.setConnectTimeout(5000);
						conn.setDoOutput(true);
						InputStream is = conn.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is,"GBK"));
						final StringBuilder sb = new StringBuilder();
						String line = "";
						while((line=reader.readLine()) != null){
							sb.append(line);
						}
						content=sb.toString();
						

					} catch(final Exception e){
						}
					all=Jsoup.parse(content);
				
		
					Elements datas=all.getElementsByClass("list-item");
					datas.size();//返回数量
					for (int i=0;i < datas.size();i++) {
                        Bean onebean=new Bean();
						Element one=datas.get(i);//这个0是第几个，0代表第一个，最后一个是size-1
						Element count=one.getElementsByClass("count").get(0);//阅读量
						Element aruthor=one.getElementsByClass("mr15").get(0);//作者
						Element intro=one.getElementsByClass("fs12 gray").get(1);//简介
						Element img=one.getElementsByTag("img").get(0);
						Element nameandherf=one.getElementsByClass("article").get(0);
						Element nameandherf2=nameandherf.getElementsByTag("a").get(0);
						onebean.set跳转("http://m.52bqg.com"+nameandherf2.attr("href"));
						onebean.set书名(nameandherf2.text());
						onebean.set作者(aruthor.text());
						onebean.set图片(img.attr("src"));
						onebean.set简介(intro.text());
						onebean.set阅读(count.text());
						datalist.add(onebean);
					}
					 runOnUiThread(new Runnable(){

							@Override
							public void run()
							{
								adapter.notifyDataSetChanged();
							}
						});
					}
			}).start();
	}
	
	
}

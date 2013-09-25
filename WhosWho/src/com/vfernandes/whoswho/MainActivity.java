package com.vfernandes.whoswho;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new ParsingHTML().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private class ParsingHTML extends AsyncTask<Void, Void, String> {

		  @Override
		  protected String doInBackground(Void... params) {
		    String title ="";
		    Document doc;
		    try {
		   	 
		    	// need http protocol
				doc = Jsoup.connect("http://theappbusiness.net/people/").get();
		 
				// get page title
				title = doc.title();
				System.out.println("title : " + title);
		 
				// get all data
				Elements rows = doc.select("div[class=row]");
				System.out.println("ROWS SIZE : " + rows.size());
				
				//Elements H3
				Elements h3 = doc.select("div[class=row] h3");
				System.out.println("H3 SIZE h3: " + h3.size());
				
				//Elements P
				Elements p = doc.select("div[class=row] p");
				System.out.println("P SIZE p: " + p.size());
				//the first is not a person, is about the company
				p.remove(0);
				
				Element name;
				for (int i=0; i<h3.size(); i++) {
					name = h3.get(i);
					System.out.println("NAME : " + name.toString());
					name = h3.get(++i);
					System.out.println("AREA : " + name.toString());

					//System.out.println("AREA : " + h3.iterator().next().toString());
					
				}
				
				String link;
				for (Element row : rows) {
					link = row.select("[src]").attr("src");
					System.out.println("LINK : " + link);
			
				}
				
				for (Element biography : p) {

					System.out.println("BIOGRAPHY : " + biography.toString());
				}

		 
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return title;   
		  } 


		  @Override
		  protected void onPostExecute(String result) {        
		   // ((TextView)findViewById (R.id.myTextView)).setText (result);
		  }
		}
	
	
}




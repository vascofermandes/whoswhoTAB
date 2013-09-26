package com.vfernandes.whoswho;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class MainActivity extends ListActivity {

	private ArrayList<Person> peopleList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new ParsingHTML().execute();
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		peopleList = new ArrayList<Person>();
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private class ParsingHTML extends AsyncTask<Void, Void, String> {

		  @Override
		  protected String doInBackground(Void... params) {
		    String title ="";
		    Document doc;
		    try {
		   	 
		    	// get the Html from the website
				doc = Jsoup.connect("http://theappbusiness.net/people/").get();
		 
				// get all data
				Elements rows = doc.select("div[class=row]");
			
				// get all data
				Elements images = doc.select("div[class=row] img");
				
				//Elements H3
				Elements h3 = doc.select("div[class=row] h3");
				
				//Elements P
				Elements bios = doc.select("div[class=row] p");
				//the first is not a person, is about the company
				bios.remove(0);
				
				for (int i=0, j=0; i<bios.size(); i++, j++) {
					Person per = new Person();
					
					per.setName(h3.get(j).toString());
					per.setPosition(h3.get(++j).toString());
					per.setImageLink(images.get(i).attr("src").toString());
					per.setBiography(bios.get(i).toString());
					
					peopleList.add(per);
					
				}
				
				  for(Person per : peopleList){
					  System.out.println("L_NAME: " + per.getName());
					  System.out.println("L_POS: " + per.getPosition());
					  System.out.println("LINK: " + per.getImageLink());
					  System.out.println("BIO: " + per.getBiography());
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




package com.vfernandes.whoswho;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {


	 private ProgressDialog m_ProgressDialog = null;
	 private ArrayList<Person> m_people = null;
	 private PersonAdapter m_adapter;
	 private Runnable viewLanguages;
	 private Button refresh;
	    Thread thread;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	setContentView(R.layout.activity_main);
		initViews();
	}
	
 void initViews(){  
	refresh = (Button)findViewById(R.id.android_refresh);        
    refresh.setOnClickListener(refreshListener); // Register the onClick listener with the implementation above

 	m_people = new ArrayList<Person>();
   
	new ParsingHTML(this).execute();
}
 
	//Create an anonymous implementation of OnClickListener
    private OnClickListener refreshListener = new OnClickListener() {
        public void onClick(View v) {  
    		initViews();
        }
   
    };
    private class PersonAdapter extends ArrayAdapter<Person> {

        private ArrayList<Person> items;

        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> m_people) {
                super(context, textViewResourceId, m_people);
                this.items = m_people;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.person_row, null);
                }
                Person p = items.get(position);
                if (p != null) {
                        TextView name = (TextView) v.findViewById(R.id.name);
                        TextView pos = (TextView) v.findViewById(R.id.position);
                        TextView bio = (TextView) v.findViewById(R.id.biography);
                        ImageView image = (ImageView) v.findViewById(R.id.image);

                        //writting the name, pos and bios on the list roe
                        name.setText(p.getName());
                        pos.setText(p.getPosition());
                        bio.setText(p.getBiography());
                        image.setImageBitmap(p.getPhoto());
                        
                }
                return v;
        }
    }
	
	private class ParsingHTML extends AsyncTask<Void, Integer, String> {
		private ArrayList<Person> peopleList;
		private Activity activity;
		private ProgressDialog progressDialog;

		
		public ParsingHTML(Activity activity) {
		    this.activity = activity;
		    this.progressDialog = new ProgressDialog(activity);
		}
		
		   @Override
		    protected void onPreExecute()
		    {
			   super.onPreExecute();
			   this.progressDialog.setMessage("Getting the Data from Web...");
		        this.progressDialog.show();
		        this.progressDialog.setCancelable(false);
		        this.progressDialog.setCanceledOnTouchOutside(false);
		       // progressDialog = ProgressDialog.show(this.activity, "Progress Dialog Title Text","Process Description Text", true);
		    };    
		
		  @Override
		  protected String doInBackground(Void... params) {
		    String title ="";
		    peopleList=new ArrayList<Person>();
		    Document doc;
		    try {
		   	 
		    	// get the Html from the website
				doc = Jsoup.connect("http://theappbusiness.net/people/").get();
		 	
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
					
					per.setName(h3.get(j).unwrap().toString());
					per.setPosition(h3.get(++j).unwrap().toString());
					per.setImageLink("http://theappbusiness.net/people/" + images.get(i).attr("src").toString());
					per.setBiography(bios.get(i).unwrap().toString());
					
					//get the Image from internet
					Bitmap bitmap;
					URL url;
					try {
						url = new URL(per.getImageLink());
					} catch (MalformedURLException e) {
						url = null;
						e.printStackTrace();
					}
					try {
						bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					} catch (IOException e) {
						bitmap = null;
						e.printStackTrace();
					}
					
					if(bitmap!=null)
					per.setPhoto(getCroppedBitmap(bitmap));
					
					//add person to the Cache
					peopleList.add(per);
					
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return title;   
		  } 


		  @Override
		  protected void onPostExecute(String result) {    
			  m_people = peopleList;
//		  m_adapter.notifyDataSetChanged();
//			  initViews();
			  m_adapter = new PersonAdapter(getApplicationContext(), R.layout.person_row, m_people);
		        setListAdapter(m_adapter);
		        m_adapter.notifyDataSetChanged();
		      progressDialog.dismiss();
		  }
		}
	
	
	
	public Bitmap getCroppedBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
	            bitmap.getWidth() / 2, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
	}
	

}




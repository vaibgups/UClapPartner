package your.service.com.partner.singleton;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;




public class MyVolleySingleton {
    private static final String TAG = MyVolleySingleton.class.getSimpleName();
    private static MyVolleySingleton myVolleySingleton;
    private RequestQueue requestQueue;
    private Context context;
    private VolleyResponseInterface volleyResponseInterface;

    private MyVolleySingleton(Context context, VolleyResponseInterface volleyResponseInterface) {
        this.context = context;
        this.volleyResponseInterface = volleyResponseInterface;

    }



    public static synchronized MyVolleySingleton getInstance(Context context, VolleyResponseInterface volleyResponseInterface) {
        if (myVolleySingleton == null) {
            myVolleySingleton = new MyVolleySingleton(context,volleyResponseInterface);
        }
        return myVolleySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public Response.Listener<String> response(final int _case) {

         Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volleyResponseInterface.onResponse(response, _case);

                Log.d(TAG, "onResponse: "+response);


            }
        };
         return listener;

    }

    public Response.ErrorListener error(final int _case){
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseInterface.onErrorResponse(error, _case);
                Log.d(TAG, "onErrorResponse: "+error);
            }
        };
        return errorListener;
    }

    public interface VolleyResponseInterface{
        void onResponse(String response, int _case);
        void onErrorResponse(VolleyError error, int _case);
    }

}

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;


public class GoogleMapsAPI {

        public void Apicoordinates() throws IOException, InterruptedException, ApiException {

                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey("AIzaSyDFxveWhV_2EGMOk77tn9ocHqh9nWJSICk")
                        .build();

                GeocodingResult[] results = GeocodingApi.geocode(context, "20.764181,29.965313").await();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println();
                System.out.println(gson.toJson(results[0].addressComponents));

                context.shutdown();
        }
}
package ru.spicedevelopers.igorpavlov.kioskmode;

import java.io.DataOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by igorpavlov on 23.07.16.
 */
public class ApplicantsPusher {

    static final String UrlForPushing = "http://requestb.in/1dz7m9b1";

    public ApplicantsPusher() {

    }

    public ArrayList<Applicant> getApplicants() {
        return Applicant.FindUnsent();
    }

    public void Run() {
        ArrayList<Applicant> requests = getApplicants();
        for (Applicant request:
             requests) {
            sendPost(request);
        }
    }

    private void sendPost(Applicant applicant) {
        try {
            URL obj = new URL(UrlForPushing);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = applicant.toString();

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + UrlForPushing);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);
            if (responseCode == 200) {
                applicant.Delete();
            }


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}

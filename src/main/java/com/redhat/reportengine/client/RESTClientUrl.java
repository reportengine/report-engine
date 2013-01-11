/**
 * 
 */
package com.redhat.reportengine.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBException;
/**
 * @author jkandasa
 * Dec 28, 2012
 */
public class RESTClientUrl {
	
	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, JAXBException {
		try {
			//URL url = new URL("https://mercury.lab.eng.pnq.redhat.com:8080/re/resteasy/services/users");
			URL url = new URL("https://localhost:8080/re/resteasy/testresults/testsuite/124");
			// configure the SSLContext with a TrustManager
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			SSLContext.setDefault(ctx);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();


			conn.setRequestMethod("GET");
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});

			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			/*JAXBContext context = JAXBContext.newInstance(TestSuite.class);
			// parse the XML and return an instance of the StorageConsoleConfig class
			TestSuite testSuite = (TestSuite) context.createUnmarshaller().unmarshal(new File("/home/jkandasa/Desktop/users.xml"));
			System.out.println("OBJ: "+testSuite);
			System.out.println("Size: "+testSuite.getTestHost());
			//System.out.println("Name: "+users.getUsers().get(0).getName()+", Age: "+users.getUsers().get(0).getAge());
			 */
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}


/*
 * Anyplace: A free and open Indoor Navigation Service with superb accuracy!
 *
 * Anyplace is a first-of-a-kind indoor information service offering GPS-less
 * localization, navigation and search inside buildings using ordinary smartphones.
 *
 * Author(s): Christakis Achilleos, Constandinos Demetriou, Marcos Antonios Charalambous
 *
 * Co-supervisor: Paschalis Mpeis
 *
 * Supervisor: Demetrios Zeinalipour-Yazti
 *
 * URL: http://anyplace.cs.ucy.ac.cy
 * Contact: anyplace@cs.ucy.ac.cy
 *
 * Copyright (c) 2019, Data Management Systems Lab (DMSL), University of Cyprus.
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

package cy.ac.ucy.cs.anyplace.lib;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

/*
 * This class makes the requests to the server using HTTP POST
 */
public class RestClient {

	/**
	 * Here we build the POST request for the server api
	 * 
	 * @param map  This is a HashMap of the api parameters to be placed in the
	 *             payload
	 * @param host This is the url of the server api
	 * @param path This is the path for the api request
	 * @return Returns a String of the response body from the server
	 */
	public String doPost(Map<String, String> map, String host, String path) {

		String query = makeRequestBody(map);

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor
				(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.readTimeout
				(Preferences.READ_TIMEOUT_SECS, TimeUnit.SECONDS).
            connectTimeout(Preferences.CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, query);
		Request request = new Request.Builder().url("https://" + host + path).post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Host", host)
				.addHeader("Accept-Encoding", "application/json")
				.addHeader("Content-Length", "45")
				.addHeader("Connection", "keep-alive")
				.addHeader("cache-control", "no-cache").build();



		try {

			Response response = client.newCall(request).execute();


			return response.body().string();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String doPostUpload(String access_token, String host, String path, String file) {

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.
				readTimeout(Preferences.READ_TIMEOUT_SECS, TimeUnit.SECONDS).
				connectTimeout(Preferences.CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS).
				build();

		MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
		RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"json\"\r\n\r\n{\"access_token\":\"" + access_token +"\"}\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"radiomap\"; filename=\""+file+"\"\r\nContent-Type: application/octet-stream\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
		Request request = new Request.Builder()
				.url("https://" + host + path).post(body)
				.addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
				.addHeader("Content-Type", "multipart/form-data")
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Host", host)
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Content-Length", "993541")
				.addHeader("Connection", "keep-alive")
				.addHeader("cache-control", "no-cache").build();
		try {
			Response response = client.newCall(request).execute();

			return response.body().string();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public  String uploadFile(String host, String path, File file, String access_token ) {

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.
				readTimeout(Preferences.READ_TIMEOUT_SECS, TimeUnit.SECONDS).
				connectTimeout(Preferences.CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS).
				build();

			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("access_token", access_token)
					.addFormDataPart("radiomap", file.getName(),
							RequestBody.create(MediaType.parse("application/octet-stream"), file))
					.addFormDataPart("json","{}")
					.build();

			Request request = new Request.Builder()
					.url("https://" + host + path)
					.post(requestBody)
					.build();



		try {
			Response response = client.newCall(request).execute();

			return response.body().string();


		} catch (Exception ex) {
			// Handle the error
			return null;
		}

	}




	/**
	 * @param host The name of the server
	 * @param url  The full url for the file we wish to request
	 * @return Returns a byte[] of the file
	 */
	public byte[] getFileWithGet(String host, String url) {


		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.
            readTimeout(Preferences.READ_TIMEOUT_SECS, TimeUnit.SECONDS).
            connectTimeout(Preferences.CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS).
            build();

		Request request = new Request.Builder().url(url).get().addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", host)
				.addHeader("Accept-Encoding", "gzip, deflate").addHeader("Connection", "keep-alive")
				.addHeader("cache-control", "no-cache").build();

		try {
			Response response = client.newCall(request).execute();

			return response.body().bytes();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param host The name of the server
	 * @param url  The full url for the file we wish to request
	 * @return Returns a byte[] of the file
	 */
	public byte[] getFileWithPost(String host, String url) {
		String query = makeRequestBody(null);

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
        OkHttpClient client = clientBuilder.
            connectTimeout(Preferences.CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS).
            readTimeout(Preferences.READ_TIMEOUT_SECS, TimeUnit.SECONDS).
            build();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, query);


		Request request = new Request.Builder().url(url).post(body).addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", host)
				.addHeader("Accept-Encoding", "gzip, deflate").addHeader("Connection", "keep-alive")
				.addHeader("cache-control", "no-cache").build();



		try {
			Response response = client.newCall(request).execute();

			return response.body().bytes();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Here we make the payload that contains all the necessary parameters
	 */
	private String makeRequestBody(Map<String, String> map) {

		String body = "{";

		if (map != null) {
			for (Entry<String, String> str : map.entrySet()) {

				body = body + "\"" + str.getKey() + "\":\"" + str.getValue() + "\",";

			}

			int length = body.length();
			body = body.substring(0, length - 1) + "}";
		} else {
			body = body + "}";
		}

		return body;
	}

}

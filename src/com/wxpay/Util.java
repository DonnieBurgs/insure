package com.wxpay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Util {

	private static final String TAG = "wxpay.Util";
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TAG);

	public static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	public static String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		System.out.println(sb.toString());
		String packageSign = MD5.getMessageDigest(sb.toString().trim().getBytes()).toUpperCase();
		return packageSign;
	}
	
	public static 	List<NameValuePair> packageParams(String nonceStr , String outTradeNo, String subject, String body, String price, String ip){
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
		packageParams.add(new BasicNameValuePair("body", outTradeNo));
		packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("notify_url", "http://www.lifeub.com/pay/wx/notify"));
		packageParams.add(new BasicNameValuePair("out_trade_no", outTradeNo));
		packageParams.add(new BasicNameValuePair("spbill_create_ip", ip));
		packageParams.add(new BasicNameValuePair("total_fee", price));
		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		return packageParams;
	}
	
	//退款
	public static 	List<NameValuePair> packageParamsRefund(String nonceStr, String out_refund_no, String outTradeNo, String price){
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
		packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("op_user_id", Constants.MCH_ID));
		packageParams.add(new BasicNameValuePair("out_refund_no", out_refund_no));
		packageParams.add(new BasicNameValuePair("out_trade_no", outTradeNo));
		packageParams.add(new BasicNameValuePair("refund_fee", price));
		packageParams.add(new BasicNameValuePair("total_fee", price));
//		packageParams.add(new BasicNameValuePair("transaction_id", ""));
			
		return packageParams;
	}
/*
 *    <appid>wx2421b1c4370ec43b</appid>
   <mch_id>10000100</mch_id>
   <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
   <op_user_id>10000100</op_user_id>
   <out_refund_no>1415701182</out_refund_no>
   <out_trade_no>1415757673</out_trade_no>
   <refund_fee>1</refund_fee>
   <total_fee>1</total_fee>
   <transaction_id></transaction_id>
   <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>

 */
	public static String genProductArgs(String nonceStr , String outTradeNo, String subject, String body, String price, String ip) {

		try {

			List<NameValuePair> packageParams = packageParams(nonceStr, outTradeNo , subject, body, price,  ip);

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = Util.toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			return null;
		}

	}

	public static Map<String, String> decodeXml(String content) {
		Map<String, String> xml = new HashMap<String, String>();

		try {
			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new StringReader(content));
			Element root = document.getRootElement();
			List<Element> childList = root.elements();
			for (Element element : childList) {
				xml.put(element.getName(), element.getStringValue());
			}

		} catch (Exception e) {
			log.error("wx orion", e);
		}
		return xml;

	}

	public static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		return sb.toString();
	}

	public static byte[] httpGet(final String url) {
		if (url == null || url.length() == 0) {
			log.error("httpGet, url is null");
			return null;
		}

		HttpClient httpClient = getNewHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse resp = httpClient.execute(httpGet);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				log.error("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());

		} catch (Exception e) {
			log.error("httpGet exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] httpPost(String url, String entity) {
		if (url == null || url.length() == 0) {
			log.error("httpPost, url is null");
			return null;
		}

		HttpClient httpClient = getNewHttpClient();

		HttpPost httpPost = new HttpPost(url);

		try {
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				log.error("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			log.error("httpPost exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	private static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
	
	public static byte[] httpPostP12(String url, String entity) {
        try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File("/root/weixinpay.p12"));
	        try {
	        	keyStore.load(instream, "1269885501".toCharArray());
	        }catch (Exception e) {
	        	System.out.println("store: " + e.toString()) ;
	        } finally {
	            instream.close();
	        }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "1269885501".toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            		CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

    		HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse resp = httpclient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				log.error("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());
//            System.out.println("executing request" + httpget.getRequestLine());

//            CloseableHttpResponse response = httpclient.execute(httpget);
        }catch(Exception e) {
        	System.out.println("point1.2");
        	e.printStackTrace();
        } finally {
            //httpclient.close();
        }
        return null;
	}	


	public static byte[] httpPostP12a(String url, String entity) {
		if (url == null || url.length() == 0) {
			log.error("httpPost, url is null");
			return null;
		}

		HttpClient httpClient = getNewHttpClientP12();

		HttpPost httpPost = new HttpPost(url);

		try {
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				log.error("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			log.error("httpPost exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	private static HttpClient getNewHttpClientP12() {
		try {
			KeyStore trustStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File("/root/weixinpay.p12"));
	        try {
	        	trustStore.load(instream, "1269885501".toCharArray());
	        }catch (Exception e) {
	        	System.out.println("store: " + e.toString()) ;
	        } finally {
	            instream.close();
	        }

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public static String sha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes());

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> stringsToList(final String[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		for (int i = 0; i < src.length; i++) {
			result.add(src[i]);
		}
		return result;
	}
}

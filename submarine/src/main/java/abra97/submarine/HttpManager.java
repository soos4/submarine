package abra97.submarine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpManager {

	private static HttpURLConnection connection = null;
	private static String defaultURL = "http://195.228.45.100:8080/jc16-srv/";

	private static void newConnection(String target) {
		try {
			URL url = new URL(target);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setRequestProperty("TEAMTOKEN", "EB95C67B8708BF24BEDC7BBB6A7DC3ED");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void setDefaultURL(String URL) {
		defaultURL = "http://" + URL + "/";
	}

	private static void sendGET() {
		//connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
		DataOutputStream us = null;
		
		try {
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			us = new DataOutputStream(connection.getOutputStream());
			//us.writeBytes(urlParameters);
			us.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	private static void sendPOST(String urlParameters) {
		connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
		connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
		DataOutputStream us = null;
		
		try {
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			us = new DataOutputStream(connection.getOutputStream());
			us.writeBytes(urlParameters);
			us.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String read() {
		InputStream is = null;
		StringBuilder response = null;
		try {
			is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	public static String newGame() {
		try {
			newConnection(defaultURL + "game");
			sendPOST("");
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String gameList() {
		try {
			newConnection(defaultURL + "game");
			sendGET();
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String connectGame(Long gameId) {
		try {
			newConnection(defaultURL + "game/" + gameId);
			sendPOST("");
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getGameInfo(Long gameId) {
		try {
			newConnection(defaultURL + "game/" + gameId);
			sendGET();
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getSubmarines(Long gameId) {
		try {
			newConnection(defaultURL + "game/" + gameId + "/submarine");
			sendGET();
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String move(Long gameId, int submarineId, String request) {
		System.out.println("Move("+submarineId+"): " + request);
		try {
			newConnection(defaultURL + "game/" + gameId + "/submarine/" + submarineId + "/move");
			sendPOST(request);
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String shoot(Long gameId, int submarineId, String request) {
		System.out.println("Shoot("+submarineId+"): " + request);
		try {
			newConnection(defaultURL + "game/" + gameId + "/submarine/" + submarineId + "/shoot");
			sendPOST(request);
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getSonarData(Long gameId, int submarineId) {
		try {
			newConnection(defaultURL + "game/" + gameId + "/submarine/" + submarineId + "/sonar");
			sendGET();
			return read();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
 
}

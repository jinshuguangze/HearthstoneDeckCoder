package com.hearthstoneDeckCoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Updater {
	public static String path = "cards.collectible.json";
	public static String urlHomepage = "https://api.hearthstonejson.com/v1";
	public static String urlContent = "/zhCN/cards.collectible.json";

	public Updater() {
	}

	public static String readUrl(String urlString) {
		try {
			URL url = new URL(urlString);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String currentLine;
			String urlContent = "";
			while ((currentLine = br.readLine()) != null)
				urlContent += currentLine;
			return urlContent;
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean update() {
		Document docHomepage;
		try {
			docHomepage = Jsoup.connect(urlHomepage).get();
			String version = docHomepage.body().select("a").get(1).text();
			String content = Updater.readUrl(urlHomepage + "/" + version + urlContent);
			Gson gson = new Gson();
			List<Card> cards = gson.fromJson(content, new TypeToken<List<Card>>() {
			}.getType());
			CardHandler cr = new CardHandler();
			if (!cr.cardWrite(path, cards))
				return false;
			return true;
		} catch (IOException | NullPointerException e) {
			return false;
		}
	}
}

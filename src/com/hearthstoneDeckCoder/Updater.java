package com.hearthstoneDeckCoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class Updater {
	public static String path = "cards.collectible.json";
	public static String urlHomepage = "https://api.hearthstonejson.com/v1";
	public static String urlContent = "/zhCN/cards.collectible.json";

	public Updater() {
	}

	public static String readUrl(String urlString) {
		/**
		 * 读取URL内数据
		 */
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

	public static String getVersion() {
		/**
		 * 得到最新版本号
		 */
		Document docHomepage;
		try {
			// 坑爹的JDK11只支持TLSv1.3了，想支持以前的版本只能手动
			System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
			docHomepage = Jsoup.connect(urlHomepage).get();
			String content[] = docHomepage.body().select("a").text().replaceAll("[^0-9 ]", "").split("\\s+");
			Arrays.sort(content);
			String version = content[content.length - 1];
			if (version.isEmpty()) {
				return null;
			} else {
				return version;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean update(String version) {
		/**
		 * 更新卡牌数据
		 */
		try {
			// 将内容卡牌数据保存在本地
			String content = Updater.readUrl(urlHomepage + "/" + version + urlContent);
			Gson gson = new Gson();
			List<Card> cards = gson.fromJson(content, new TypeToken<List<Card>>() {
			}.getType());
			CardHandler cr = new CardHandler();
			if (!cr.cardWrite(path, cards))
				return false;
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static boolean checkUpdate(boolean enforce) {
		/**
		 * 检查卡牌数据是否是最新，当前仅当成功更新时，返回true
		 */
		// 读取版本号并将版本号写进配置文件
		File file = new File("config.cfg");
		String configVersion = null;
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (!file.exists() || enforce) {
				file.delete();
				file.createNewFile();
			} else {
				Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				Gson gsonInput = new Gson();
				Config config = gsonInput.fromJson(reader, new TypeToken<Config>() {
				}.getType());
				configVersion = config.version;
				if (config.updateTime != null) {
					try {
						Date lastUpdateTime = df.parse(config.updateTime);
						long betweenDays = (long) ((now.getTime() - lastUpdateTime.getTime()) 
						/ (1000 * 60 * 60 * 24) + 0.5);
						if (betweenDays >= 0 && betweenDays < 1) {
							return false;
						}
					} catch (ParseException pe) {
					}
				}
			}
			String version = Updater.getVersion();
			CardHandler cr = new CardHandler();
			// 当且仅当保存版本配置成功，且云版本和本地版本不全为空，且云版本和本地版本不相等，且更新成功才返回true
			if(Updater.saveVersion(file, df.format(now), version != null ? version : configVersion)
				&& (version != null || configVersion != null) && !version.equals(configVersion)
				&& Updater.update((version != null) ? version : configVersion) && cr.cardRead(Updater.path)
				&& cr.cardsSort() && cr.cardsClassify())
				{
					return true;
				}
		} catch (Exception e) {
		}
		return false;
	}

	private static boolean saveVersion(File file, String updateTime, String version) {
		/**
		 * 将版本号存储在某个特定文件中 TOD:在未来版本里，会大更新配置文件的读取与写入，现在先搞出半成品。
		 */
		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
			JsonWriter jsonWriter = new JsonWriter(writer);
			jsonWriter.setIndent("  ");
			jsonWriter.beginObject();
			jsonWriter.name("updateTime").value(updateTime);
			jsonWriter.name("version").value(version);
			jsonWriter.endObject();
			jsonWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}

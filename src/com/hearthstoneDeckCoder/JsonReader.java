package com.hearthstoneDeckCoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class JsonReader {
	/**
	 * Json文件读取者
	 */
	public static String path = "cards.collectible.json";
	public static String url = "https://api.hearthstonejson.com/v1/x/zhCN/cards.collectible.json";
	public static int version = 25770;
	public List<Card> cards;

	public JsonReader(String fileName, boolean update) throws IOException {
		/**
		 * 构造函数
		 */
		File file = new File(fileName);
		if (!file.exists() || update) {
			// URL httpUrl=new URL(url.replace("x", String.valueOf(version)));
			// TODO:从网络url读取并下载文件至本地
			// 若失败，搜寻https://api.hearthstonejson.com/v1此网址的可用文件
			// 每次失败又成功后，将version存储至单独文件中
			JsonReader jr = new JsonReader(path, false);
			jr.cardsSort();
			jr.cardsClassify();
		}
		Gson gsonInput = new Gson();
		Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		List<Card> cards = gsonInput.fromJson(reader, new TypeToken<List<Card>>() {
		}.getType());
		this.cards = cards;
		reader.close();
	}

	public void cardsSort() {
		/**
		 * 按照dbfId从小到大排序
		 */
		cards.sort(new Comparator<Card>() {
			@Override
			public int compare(Card a, Card b) {
				if (a.dbfId > b.dbfId)
					return 1;
				else {
					return -1;
				}
			}
		});
	}

	public void cardsClassify() throws IOException {
		/**
		 * 将所有可收集卡牌分类并存储于Json文件中
		 */
		// 分离出九大职业与中立卡池，分离出英雄卡池
		List<List<Card>> splitCards = new ArrayList<>(Collections.nCopies(11, new ArrayList<>()));
		ListIterator<Card> lIterator = cards.listIterator();

		while (lIterator.hasNext()) {
			Card temp = lIterator.next();
			for (int i = 0; i < CardClass.getCount(); i++) {
				int fileState = -1;
				if (temp.type.toLowerCase().equals(CardClass.getName(0))// 英雄牌
						&& (temp.set.toLowerCase().equals(CardSet.getName(0))
								|| temp.set.toLowerCase().equals(CardSet.getName(1)))) {
					fileState = 0;
				} else if (temp.cardClass.toLowerCase().equals(CardClass.getName(i))) {// 其他牌
					fileState = i;
				}
				if (fileState != -1) {
					List<Card> loopList = new ArrayList<>();
					loopList.addAll(splitCards.get(fileState));
					loopList.add(temp);
					splitCards.set(fileState, loopList);
					break;
				}
			}
		}

		// 将分离出来的卡牌写入json文件
		for (int i = 0; i < CardClass.getCount(); i++) {
			File file = new File(System.getProperty("user.dir") + "/cards." + CardClass.getName(i) + ".json");
			if (!file.exists())
				file.createNewFile();
			Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
			JsonWriter jsonWriter = new JsonWriter(writer);
			jsonWriter.setIndent("  ");
			jsonWriter.beginArray();
			for (Card splitCard : splitCards.get(i)) {
				Gson gsonOutput = new Gson();
				gsonOutput.toJson(splitCard, Card.class, jsonWriter);
			}
			jsonWriter.endArray();
			jsonWriter.close();
		}
	}
}

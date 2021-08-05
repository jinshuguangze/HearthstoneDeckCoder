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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class CardHandler {
	/**
	 * 卡牌处理者
	 */
	public List<Card> cards;

	public CardHandler() {
		/**
		 * 构造函数
		 */
	}

	public boolean cardRead(String path) {
		/**
		 * 从Json文件读取卡牌并检测更新，成功读取则为true
		 */
		File file = new File(path);
		if (!file.exists()) {
			return Updater.checkUpdate(true) & cardRead(path);// 强制更新
		}
		try {
			Gson gsonInput = new Gson();
			Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			List<Card> cards = gsonInput.fromJson(reader, new TypeToken<List<Card>>() {
			}.getType());
			this.cards = cards;
			reader.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean cardWrite(String path, List<Card> cards) {
		/**
		 * 将卡牌写入Json文件中
		 */
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
			JsonWriter jsonWriter = new JsonWriter(writer);
			jsonWriter.setIndent("  ");
			jsonWriter.beginArray();
			for (Card aCard : cards) {
				Gson gsonOutput = new Gson();
				gsonOutput.toJson(aCard, Card.class, jsonWriter);
			}
			jsonWriter.endArray();
			jsonWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean cardsSort() {
		/**
		 * 按照dbfId从小到大排序
		 */
		try {
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
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean cardsClassify() {
		/**
		 * 将所有可收集卡牌分类并存储于Json文件中
		 */
		// 分离出十大职业与中立卡池，分离出英雄皮肤卡池
		List<List<Card>> splitCards = new ArrayList<>(Collections.nCopies(CardClass.getCount() + 1, new ArrayList<>()));
		ListIterator<Card> lIterator = cards.listIterator();

		while (lIterator.hasNext()) {
			Card temp = lIterator.next();
			List<String> tempClasses = new ArrayList<>();
			if (CardSet.isHeroSkin(temp.set.toLowerCase())) {// 英雄皮肤卡牌
				List<Card> loopList = new ArrayList<>();
				loopList.addAll(splitCards.get(0));
				loopList.add(temp);
				splitCards.set(0, loopList);
				continue;
			}
			if (temp.classes != null)// 多职业卡牌
			{
				tempClasses = Arrays.asList(temp.classes);
			} else {// 单职业卡牌
				tempClasses.add(temp.cardClass);
			}
			for (int i = 0; i < tempClasses.size(); i++) {
				int index = CardClass.getIndex(tempClasses.get(i).toLowerCase());
				if (index != -1) {
					List<Card> loopList = new ArrayList<>();
					loopList.addAll(splitCards.get(index + 1));
					loopList.add(temp);
					splitCards.set(index + 1, loopList);
				}
			}
		}

		// 将分离出来的卡牌写入json文件
		if (!cardWrite(System.getProperty("user.dir") + "/cards." + CardSet.getName(0) + ".json", splitCards.get(0)))
			return false;
		for (int i = 0; i < CardClass.getCount(); i++)
			if (!cardWrite(System.getProperty("user.dir") + "/cards." + CardClass.getName(i) + ".json",
					splitCards.get(i + 1)))
				return false;
		return true;
	}
}

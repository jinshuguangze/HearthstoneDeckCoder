package com.hearthstoneDeckCoder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DeckBuilder {
	public static double careerRate = 0.4;
	public static double doubleCardMean = 5;
	public static double doubleCardVariance = 10;
	public static double multiCardMean = 0;
	public static double multiCardVariance = 0;

	public boolean isStandardMode = false;
	public List<Integer> deckList = Arrays.asList(new Integer[] { 0, 1, 1, 1, 46116, 30, 336, 374, 570, 581, 834, 890,
			912, 1914, 2078, 2727, 2883, 2948, 2949, 38312, 38758, 39119, 39225, 39941, 40636, 41289, 41841, 42818,
			43406, 45340, 45366, 46263, 46403, 46449, 47298, 48107, 0 });
	public List<String> deckName = Arrays.asList(new String[] { "使用英雄：露娜拉", "迦顿男爵*1", "炎魔之王拉格纳罗斯*1", "工匠大师欧沃斯巴克*1",
			"阿莱克丝塔萨*1", "死亡之翼*1", "提里奥·弗丁*1", "哈里森·琼斯*1", "洛欧塞布*1", "砰砰博士*1", "纯洁者耶德瑞克*1", "雷诺·杰克逊*1", "芬利·莫格顿爵士*1",
			"布莱恩·铜须*1", "亚煞极*1", "光耀之主拉格纳罗斯*1", "深渊滑行者索苟斯*1", "馆长*1", "巴内斯*1", "燃鬃·自走炮*1", "灵魂歌者安布拉*1", "郭雅夫人*1",
			"巫妖王*1", "黑锋骑士乌瑟尔*1", "凯雷塞斯王子*1", "阿尔福斯", "瓦兰奈尔*1", "蛇发女妖佐拉*1", "欧克哈特大师*1", "人偶大师多里安*1", "玻璃骑士*1" });

	public DeckBuilder() {
	}

	public List<Integer> randomBuild() throws IOException {
		// 初始化变量
		Random random = new Random();
		List<Integer> deckList = new ArrayList<>();
		List<String> deckName = new ArrayList<>();
		int sigleCount = 0;
		int doubleCount = 0;
		int multiCount = 0;

		// 卡组模式，狂野为1，标准为2
		if (random.nextBoolean()) {
			this.isStandardMode = false;
			deckList.add(1);
		} else {
			this.isStandardMode = true;
			deckList.add(2);
		}

		// 选择职业
		JsonReader heroCards = new JsonReader("cards." + CardClass.getName(0).toLowerCase() + ".json", false);
		Card hero = heroCards.cards.get(random.nextInt(heroCards.cards.size()));
		deckList.add(hero.dbfId);
		deckName.add("使用英雄：" + hero.name);

		// 决定单卡，双卡，多卡的数量
		do {
			doubleCount = Integer.parseInt(new DecimalFormat("0")
					.format(Math.sqrt(doubleCardVariance) * random.nextGaussian() + doubleCardMean));
			multiCount = Integer.parseInt(new DecimalFormat("0")
					.format(Math.sqrt(multiCardVariance) * random.nextGaussian() + multiCardMean));
			sigleCount = 30 - 2 * doubleCount - 0 * multiCount;
		} while (sigleCount * doubleCount < 0);

		// 加入卡牌,BUG:加基森的暗金教信使等牌只有特定角色能用
		//BUG:传说卡只能一张
		//BUG:单卡不能重复
		JsonReader careerCards = new JsonReader("cards." + hero.cardClass.toLowerCase() + ".json", false);
		JsonReader neutralCards = new JsonReader("cards.neutral.json", false);
		for (int i = 0; i < sigleCount + doubleCount; i++) {
			// 加入卡牌数量
			if (i == 0) {
				deckList.add(sigleCount);
			} else if (i == sigleCount) {
				deckList.add(doubleCount);
			}

			// 加入卡牌
			Card card = new Card();
			do {
				if (random.nextDouble() < careerRate) {
					card = careerCards.cards.get(random.nextInt(careerCards.cards.size()));
				} else {
					card = neutralCards.cards.get(random.nextInt(neutralCards.cards.size()));
				}
			} while (this.isStandardMode && !CardSet.isStandardSet(card.set.toLowerCase()));
			deckList.add(card.dbfId);
			deckName.add(card.name + "*" + (i < sigleCount ? 1 : 2));
		}

		// 代码标准化
		deckList = deckStander(deckList);
		this.deckList = deckList;
		this.deckName = deckName;
		return deckList;
	}

	private List<Integer> deckStander(List<Integer> deckList) {
		deckList.add(0, 0);// 代码头，固定为0
		deckList.add(1, 1);// 卡组代码版本，目前为1
		deckList.add(3, 1);// 使用英雄数量，固定为1
		deckList.add(deckList.size(), 0);// 代码尾，固定为0
		return deckList;
	}
}

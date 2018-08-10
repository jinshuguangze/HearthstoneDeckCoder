package com.hearthstoneDeckCoder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeckBuilder {
	/**
	 * 套牌构筑者
	 */
	public static double careerRate = 0.4;// 出现职业卡概率
	public static double doubleCardMean = 5;// 出现双卡的数学期望
	public static double doubleCardVariance = 10;// 出现双卡的方差
	public static double multiCardMean = 0;// 出现多卡的数学期望
	public static double multiCardVariance = 0;// 出现多卡的方差

	public int cardsMode;// 套牌使用模式
	public Card cardsHero;// 套牌使用英雄卡牌
	public List<Integer> cardsList;// 套牌ID列表
	public List<Integer> cardsCount;// 套牌张数列表
	public List<Integer> cardsCost;// 套牌费用列表
	public List<String> cardsName;// 套牌名称列表

	public DeckBuilder() {
		/**
		 * 构造函数
		 */
	}

	public void randomBuild() throws IOException {
		/**
		 * 构建随机套牌
		 */
		// 初始化变量
		int cardsMode;
		Card cardsHero = new Card();
		List<Integer> cardsList = new ArrayList<>();
		List<Integer> cardsCount = new ArrayList<>();
		List<Integer> cardsCost = new ArrayList<>();
		List<String> cardsName = new ArrayList<>();

		int sigleCount;
		int doubleCount;
		int multiCount;
		Random random = new Random();

		// 卡组模式，狂野为1，标准为2
		if (random.nextBoolean()) {
			cardsMode = 1;
			cardsList.add(1);
		} else {
			cardsMode = 2;
			cardsList.add(2);
		}

		// 选择职业
		JsonReader heroes = new JsonReader("cards." + CardClass.getName(0).toLowerCase() + ".json", false);
		cardsHero = heroes.cards.get(random.nextInt(heroes.cards.size()));
		cardsList.add(cardsHero.dbfId);

		// 决定单卡，双卡，多卡的数量
		do {
			doubleCount = Integer.parseInt(new DecimalFormat("0")
					.format(Math.sqrt(doubleCardVariance) * random.nextGaussian() + doubleCardMean));
			multiCount = Integer.parseInt(new DecimalFormat("0")
					.format(Math.sqrt(multiCardVariance) * random.nextGaussian() + multiCardMean));
			sigleCount = 30 - 2 * doubleCount - 0 * multiCount;
		} while (sigleCount * doubleCount < 0);

		// 加入卡牌
		JsonReader careerCards = new JsonReader("cards." + cardsHero.cardClass.toLowerCase() + ".json", false);
		JsonReader neutralCards = new JsonReader("cards." + CardClass.getName(10) + ".json", false);
		for (int i = 0; i < sigleCount + doubleCount; i++) {
			Card card = new Card();
			do {
				if (random.nextDouble() < careerRate) {
					card = careerCards.cards.get(random.nextInt(careerCards.cards.size()));
				} else {
					card = neutralCards.cards.get(random.nextInt(neutralCards.cards.size()));
				}
			} while (cardsList.contains(card.dbfId)// 列表重复限制
					|| cardsMode == 2 && !CardSet.isStandardSet(card.set.toLowerCase()) // 标准环境限制
					|| (i >= sigleCount && card.rarity.toLowerCase().equals(CardRarity.getName(4)))// 传说卡张数限制
					|| !(card.classes == null || Arrays.asList(card.classes).contains(cardsHero.cardClass)));// 卡牌教派限制
			cardsList.add(card.dbfId);
			cardsCount.add(i < sigleCount ? 1 : 2);
			cardsCost.add(card.cost);
			cardsName.add(card.name);
		}

		// 代码标准化
		cardsList.add(2, sigleCount);
		cardsList.add(sigleCount + 3, doubleCount);
		cardsList = deckStandardize(cardsList);
		this.cardsMode = cardsMode;
		this.cardsHero = cardsHero;
		this.cardsList = cardsList;
		this.cardsCount = cardsCount;
		this.cardsCost = cardsCost;
		this.cardsName = cardsName;
		deckSort();
	}

	public void deckPrint(boolean showCardsName) throws IOException {
		/**
		 * 打印卡组
		 */
		System.out.println("### 随机卡组");
		System.out.println("# 职业：" + CardClass.getChineseName(cardsHero.cardClass.toLowerCase()));
		if (cardsMode == 1) {
			System.out.println("# 模式：狂野模式");
		} else {
			System.out.println("# 模式：标准模式");
			System.out.println("# 渡鸦年");
		}
		if (showCardsName) {
			System.out.println("#");
			for (int i = 0; i < cardsCount.size(); i++) {
				System.out.println("# " + cardsCount.get(i) + "x (" + cardsCost.get(i) + ") " + cardsName.get(i));
			}
		}
		System.out.println("# ");
		System.out.println(CodeAnalyzer.list2code(cardsList));
		System.out.println("# ");
		System.out.println("# 想要使用这副套牌，请先复制到剪贴板，然后在游戏中点击“新套牌”进行粘贴。");
	}

	private List<Integer> deckStandardize(List<Integer> cardsList) {
		/**
		 * 对卡组代码进行标准化
		 */
		cardsList.add(0, 0);// 代码头，固定为0
		cardsList.add(1, 1);// 卡组代码版本，目前为1
		cardsList.add(3, 1);// 使用英雄数量，固定为1
		cardsList.add(cardsList.size(), 0);// 代码尾，固定为0
		return cardsList;
	}

	public void deckSort() {
		/**
		 * 对套牌类的卡牌进行排序
		 */
		// 获取卡组信息
		List<Integer> cardsCount = this.cardsCount;
		List<Integer> cardsCost = this.cardsCost;
		List<String> cardsName = this.cardsName;
		int singleCardCount = cardsList.get(5);
		int doubleCardCount = cardsList.get(singleCardCount + 6);

		// 提取核心列表
		List<Integer> coreList = new ArrayList<>() {
			// 自动生成的序列化版本号
			private static final long serialVersionUID = 6783350014225170237L;
			{
				addAll(cardsList.subList(6, singleCardCount + 6));
				addAll(cardsList.subList(singleCardCount + 7, cardsList.size() - 1));
			}
		};

		// 依据卡牌的费用和ID排序
		for (int i = 0; i < coreList.size() - 1; i++) {
			for (int j = i + 1; j < coreList.size(); j++) {
				if (cardsCost.get(i) > cardsCost.get(j)
						|| (cardsCost.get(i) == cardsCost.get(j) && coreList.get(i) > coreList.get(j))) {
					Collections.swap(coreList, i, j);
					Collections.swap(cardsCount, i, j);
					Collections.swap(cardsCost, i, j);
					Collections.swap(cardsName, i, j);
				}
			}
		}

		// 重新生成序列
		ArrayList<Integer> cardsList = new ArrayList<>() {
			// 自动生成的序列化版本号
			private static final long serialVersionUID = 3860324031377400266L;
			{
				addAll(coreList);
				add(0, singleCardCount);
				add(singleCardCount + 1, doubleCardCount);
				add(0, cardsHero.dbfId);
				add(0, cardsMode);
			}
		};
		this.cardsList = deckStandardize(cardsList);
		this.cardsCount = cardsCount;
		this.cardsCost = cardsCost;
		this.cardsName = cardsName;
	}
}

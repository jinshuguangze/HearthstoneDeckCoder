package com.hearthstoneDeckCoder;

public enum CardSet {
	HERO_SKINS("hero_skins", 0, false), // 英雄皮肤系列
	CORE("core", 1, true), // 基本系列
	EXPERT1("expert1", 2, true), // 经典系列
	HOF("hof", 3, false), // 荣耀室系列
	NAXX("naxx", 4, false), // 纳克萨玛斯的诅咒系列
	GVG("gvg", 5, false), // 地精大战侏儒系列
	BRM("brm", 6, false), // 黑石山的火焰系列
	TGT("tgt", 7, false), // 冠军的试炼系列
	LOE("loe", 8, false), // 探险者协会系列
	OG("og", 9, false), // 上古之神的低语系列
	KARA("kara", 10, false), // 卡拉赞之夜
	GANGS("gangs", 11, false), // 龙争虎斗加基森系列
	UNGORO("ungoro", 12, true), // 勇闯安戈洛
	ICECROWN("icecrown", 13, true), // 冰封王座的骑士
	LOOTAPALOOZA("lootapalooza", 14, true), // 狗头人与地下世界系列
	GILNEAS("gilneas", 15, true), // 女巫森林系列
	BOOMSDAY("boomsday", 16, false);// 砰砰计划系列

	public String name;
	public int index;
	boolean isStandardSet;

	private CardSet(String name, int index, boolean isStandardSet) {
		this.name = name;
		this.index = index;
		this.isStandardSet = isStandardSet;
	}

	public static int getCount() {
		return CardSet.values().length;
	}

	public static int getIndex(String name) {
		for (CardSet temp : CardSet.values()) {
			if (name.equals(temp.name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getName(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static boolean isStandardSet(String name) {
		for (CardSet temp : CardSet.values()) {
			if (name.equals(temp.name)) {
				return temp.isStandardSet;
			}
		}
		return false;
	}

	public static boolean isStandardSet(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.isStandardSet;
			}
		}
		return false;
	}
}
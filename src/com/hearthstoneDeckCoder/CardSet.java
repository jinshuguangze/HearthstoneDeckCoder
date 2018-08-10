package com.hearthstoneDeckCoder;

public enum CardSet {
	HERO_SKINS("hero_skins", "英雄皮肤", 0, false),
	CORE("core", "基本", 1, true),
	EXPERT1("expert1", "经典", 2, true),
	HOF("hof", "荣耀室", 3, false),
	NAXX("naxx", "纳克萨玛斯的诅咒", 4, false),
	GVG("gvg", "地精大战侏儒", 5, false),
	BRM("brm", "黑石山的火焰", 6, false),
	TGT("tgt", "冠军的试炼", 7, false),
	LOE("loe", "探险者协会", 8, false),
	OG("og", "上古之神的低语", 9, false),
	KARA("kara", "卡拉赞之夜", 10, false),
	GANGS("gangs", "龙争虎斗加基森", 11, false),
	UNGORO("ungoro", "勇闯安戈洛", 12, true),
	ICECROWN("icecrown", "冰封王座的骑士", 13, true),
	LOOTAPALOOZA("lootapalooza", "狗头人与地下世界", 14, true),
	GILNEAS("gilneas", "女巫森林", 15, true),
	BOOMSDAY("boomsday", "砰砰计划", 16, true);

	private String name;
	private String chineseName;
	private int index;
	private boolean isStandardSet;

	private CardSet(String name, String chineseName, int index, boolean isStandardSet) {
		this.name = name;
		this.chineseName = chineseName;
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

	public static String getChineseName(String name) {
		for (CardSet temp : CardSet.values()) {
			if (name.equals(temp.name)) {
				return temp.chineseName;
			}
		}
		return null;
	}

	public static String getChineseName(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.chineseName;
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
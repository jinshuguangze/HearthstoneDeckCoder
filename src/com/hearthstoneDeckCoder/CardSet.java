package com.hearthstoneDeckCoder;

public enum CardSet {
	HERO_SKINS("hero_skins", "英雄皮肤", 0, false),
	CORE("core", "核心", 1, true),
	VANILLA("vanilla", "经典", 2, false),
	EXPERT1("expert1", "怀旧", 3, false),
	NAXX("naxx", "纳克萨玛斯的诅咒", 4, false),
	GVG("gvg", "地精大战侏儒", 5, false),
	BRM("brm", "黑石山的火焰", 6, false),
	TGT("tgt", "冠军的试炼", 7, false),
	LOE("loe", "探险者协会", 8, false),
	OG("og", "上古之神的低语", 9, false),
	KARA("kara", "卡拉赞之夜", 10, false),
	GANGS("gangs", "龙争虎斗加基森", 11, false),
	UNGORO("ungoro", "勇闯安戈洛", 12, false),
	ICECROWN("icecrown", "冰封王座的骑士", 13, false),
	LOOTAPALOOZA("lootapalooza", "狗头人与地下世界", 14, false),
	GILNEAS("gilneas", "女巫森林", 15, false),
	BOOMSDAY("boomsday", "砰砰计划", 16, false),
	TROLL("troll", "拉斯塔哈的大乱斗", 17, false),
	DALARAN("dalaran", "暗影崛起", 18, false),
	ULDUM("uldum", "奥丹姆奇兵", 19, false),
	DRAGONS("dragons", "巨龙降临", 20, false),
	YEAR_OF_THE_DRAGON("year_of_the_dragon", "迦拉克隆的觉醒", 21, false),
	DEMON_HUNTER_INITIATE("demon_hunter_initiate", "恶魔猎手新兵", 22, false),
	BLACK_TEMPLE("black_temple", "外域的灰烬", 23, true),
	SCHOLOMANCE("scholomance", "通灵学院", 24, true),
	DARKMOON_FAIRE("darkmoon_faire", "疯狂的暗月马戏团", 25, true),
	THE_BARRENS("the_barrens", "贫瘠之地的锤炼", 26, true),
	STORMWIND("stormwind", "暴风城下的集结", 27, true);

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

	public static String getName(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static int getIndex(String name) {
		for (CardSet temp : CardSet.values()) {
			if (name.equals(temp.name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getChineseName(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.chineseName;
			}
		}
		return null;
	}

	public static String getChineseName(String name) {
		return getChineseName(getIndex(name));
	}

	public static boolean isHeroSkin(int index) {
		return index == 0;
	}

	public static boolean isHeroSkin(String name) {
		return getIndex(name) == 0;
	}

	public static boolean isWildSet(int index) {
		return index > 0 && index != 2 && index < CardSet.getCount();
	}

	public static boolean isWildSet(String name) {
		return getIndex(name) > 0 && getIndex(name) != 2 && getIndex(name) < CardSet.getCount();
	}

	public static boolean isStandardSet(int index) {
		for (CardSet temp : CardSet.values()) {
			if (index == temp.index) {
				return temp.isStandardSet;
			}
		}
		return false;
	}

	public static boolean isStandardSet(String name) {
		return isStandardSet(getIndex(name));
	}

	public static boolean isClassicSet(int index) {
		return index == 2;
	}

	public static boolean isClassicSet(String name) {
		return getIndex(name) == 2;
	}
}
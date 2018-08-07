package com.hearthstoneDeckCoder;

public enum CardClass {
	HERO("hero", 0), // 英雄
	WARRIOR("warrior", 1), // 战士
	SHAMAN("shaman", 2), // 萨满祭司
	ROGUE("rogue", 3), // 潜行者
	PALADIN("paladin", 4), // 圣骑士
	HUNTER("hunter", 5), // 猎人
	DRUID("druid", 6), // 德鲁伊
	WARLOCK("warlock", 7), // 术士
	MAGE("mage", 8), // 法师
	PRIEST("priest", 9), // 牧师
	NEUTRAL("neutral", 10);// 中立

	public String name;
	public int index;

	private CardClass(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static int getCount() {
		return CardClass.values().length;
	}

	public static int getIndex(String name) {
		for (CardClass temp : CardClass.values()) {
			if (name.equals(temp.name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getName(int index) {
		for (CardClass temp : CardClass.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}
}

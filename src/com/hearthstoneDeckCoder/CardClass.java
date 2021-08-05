package com.hearthstoneDeckCoder;

public enum CardClass {
	NEUTRAL("neutral", "中立", 0),
	WARRIOR("warrior", "战士", 1),
	SHAMAN("shaman", "萨满祭司", 2),
	ROGUE("rogue", "潜行者", 3),
	PALADIN("paladin", "圣骑士", 4),
	HUNTER("hunter", "猎人", 5),
	DRUID("druid", "德鲁伊", 6),
	WARLOCK("warlock", "术士", 7),
	MAGE("mage", "法师", 8),
	PRIEST("priest", "牧师", 9),
	DEMONHUNTER("demonhunter", "恶魔猎手", 10);
	

	private String name;
	private String chineseName;
	private int index;

	private CardClass(String name, String chineseName, int index) {
		this.name = name;
		this.chineseName = chineseName;
		this.index = index;
	}

	public static int getCount() {
		return CardClass.values().length;
	}

	public static String getName(int index) {
		for (CardClass temp : CardClass.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static int getIndex(String name) {
		for (CardClass temp : CardClass.values()) {
			if (temp.name.equals(name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getChineseName(int index) {
		for (CardClass temp : CardClass.values()) {
			if (index == temp.index) {
				return temp.chineseName;
			}
		}
		return null;
	}

	public static String getChineseName(String name) {
		return getChineseName(getIndex(name));
	}
}

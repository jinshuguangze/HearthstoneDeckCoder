package com.hearthstoneDeckCoder;

public enum CardType {
	HERO("hero", "英雄", 0),
	MINION("minion", "随从", 1),
	SPELL("spell", "法术", 2),
	WEAPON("weapon", "武器", 3);

	private String name;
	private String chineseName;
	private int index;

	private CardType(String name, String chineseName, int index) {
		this.name = name;
		this.chineseName = chineseName;
		this.index = index;
	}

	public static int getCount() {
		return CardType.values().length;
	}

	public static String getName(int index) {
		for (CardType temp : CardType.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static int getIndex(String name) {
		for (CardType temp : CardType.values()) {
			if (name.equals(temp.name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getChineseName(int index) {
		for (CardType temp : CardType.values()) {
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

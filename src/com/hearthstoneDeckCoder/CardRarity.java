package com.hearthstoneDeckCoder;

public enum CardRarity {
	FREE("free", "免费", 0),
	COMMON("common", "普通", 1),
	RARE("rare", "稀有", 2),
	EPIC("epic", "史诗", 3),
	LEGENDARY("legendary", "传说", 4);

	private String name;
	private String chineseName;
	private int index;

	private CardRarity(String name, String chineseName, int index) {
		this.name = name;
		this.chineseName = chineseName;
		this.index = index;
	}

	public static int getCount() {
		return CardRarity.values().length;
	}

	public static int getIndex(String name) {
		for (CardRarity temp : CardRarity.values()) {
			if (name.equals(temp.name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getName(int index) {
		for (CardRarity temp : CardRarity.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static String getChineseName(String name) {
		for (CardRarity temp : CardRarity.values()) {
			if (name.equals(temp.name)) {
				return temp.chineseName;
			}
		}
		return null;
	}

	public static String getChineseName(int index) {
		for (CardRarity temp : CardRarity.values()) {
			if (index == temp.index) {
				return temp.chineseName;
			}
		}
		return null;
	}
}

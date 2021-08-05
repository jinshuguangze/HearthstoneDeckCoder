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

	public static String getName(int index) {
		for (CardRarity temp : CardRarity.values()) {
			if (index == temp.index) {
				return temp.name;
			}
		}
		return null;
	}

	public static int getIndex(String name) {
		for (CardRarity temp : CardRarity.values()) {
			if (temp.name.equals(name)) {
				return temp.index;
			}
		}
		return -1;
	}

	public static String getChineseName(int index) {
		for (CardRarity temp : CardRarity.values()) {
			if (index == temp.index) {
				return temp.chineseName;
			}
		}
		return null;
	}
	
	public static String getChineseName(String name) {
		return getChineseName(getIndex(name));
	}

	public static boolean isFree(int index) {
		return index == 0;
	}

	public static boolean isFree(String name) {
		return getIndex(name) == 0;
	}

	public static boolean isCommon(int index) {
		return index == 1;
	}

	public static boolean isCommon(String name) {
		return getIndex(name) == 1;
	}

	public static boolean isRare(int index) {
		return index == 2;
	}

	public static boolean isRare(String name) {
		return getIndex(name) == 2;
	}

	public static boolean isEpic(int index) {
		return index == 3;
	}

	public static boolean isEpic(String name) {
		return getIndex(name) == 3;
	}

	public static boolean isLegendary(int index) {
		return index == 4;
	}

	public static boolean isLegendary(String name) {
		return getIndex(name) == 4;
	}
}

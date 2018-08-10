package com.hearthstoneDeckCoder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws IOException {
		//测试单卡双卡数量
		//Random random=new Random();
		//int doubleCount=Integer.parseInt(new DecimalFormat("0").format(Math.sqrt(10)*random.nextGaussian()+5));
		//System.out.println(doubleCount);
		//System.out.println(30-2*doubleCount);
		
		//测试json分类器
		JsonReader jr=new JsonReader(JsonReader.path,false);
		jr.cardsSort();
		jr.cardsClassify();
		
		//测试编码转数组
		//String aString=
		//		"AAEBAbSKAxDIBfEFmAfTD54QphCEEtoTxRWgwQLIxwKZ0wLE7AKa7gKM7wKrgAMHiwjkFYWtAv+tAq7CAtfrAuT7AgA=";
		//System.out.println(CodeAnalyzer.code2list(aString));
		
		//测试数组转编码
		//Integer[] aIntegers=new Integer[] { 
		//		0, 1, 1, 1, 46116, 30, 336, 374, 570, 581, 834, 890,
		//		912, 1914, 2078, 2727, 2883, 2948, 2949, 38312, 38758, 39119, 39225, 39941, 40636, 41289, 41841, 42818,
		//		43406, 45340, 45366, 46263, 46403, 46449, 47298, 48107, 0,0
		//		};
		//System.out.println(CodeAnalyzer.llist2code(Arrays.asList(aIntegers)));
		
		//测试随机卡组生成
		//BUG:偶尔出现不适合这个模式
		DeckBuilder db = new DeckBuilder();
		db.randomBuild();
		db.deckPrint(true);
	}
}

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
		//JsonReader jr=new JsonReader(JsonReader.path,false);
		//jr.cardsClassify();
		
		//测试编码转数组
		//CodeAnalyzer ca1 = new CodeAnalyzer();
		//String aString=
		//		"AAEBAaIHHp4BmQLtAo0DpAP+A6wE3QSQB8oI8wyXDZAVyxbnFv6sAsuvApS2Atm6Ap+9Asm/Asq/Atm/AuC/AsLDAsbLAtjNArHOApTQAvfzAgAA";
		//System.out.println(ca1.code2list(aString));
		
		//测试数组转编码
		//CodeAnalyzer ca2 = new CodeAnalyzer();
		//Integer[] aIntegers=new Integer[] { 
		//		0, 1, 1, 1, 930, 30, 158, 281, 365, 397, 420, 510, 556, 605, 912, 1098, 1651, 1687, 2704, 2891, 2919, 38526, 38859, 39700, 40281, 40607, 40905, 40906, 40921, 40928, 41410, 42438, 42712, 42801, 43028, 47607, 0,0
		//		};
		//System.out.println(ca2.list2code(Arrays.asList(aIntegers)));
		
		//测试随机卡组生成
		DeckBuilder db = new DeckBuilder();
		CodeAnalyzer ca3 = new CodeAnalyzer();
		db.randomBuild();
		System.out.println("### RandomDeckByJinshu\n"+ca3.list2code(db.deckList));
		System.out.println(db.deckName);
		System.out.println(db.deckList);
	}
}

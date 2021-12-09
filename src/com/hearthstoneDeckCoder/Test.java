package com.hearthstoneDeckCoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws IOException {
		//TOD:
		//-遇到更新弹出窗口询问下载是否下载xKB数据包
		//-windows版本的UI，采用JavaFX制作
		//-高级自定义，元素系列卡组，机械系列等
		//BUG：似乎不能随机英雄皮肤

		//每日首次打开检测更新
		//TOD:写个东西每日检测更新，并存储在config文件夹内
		Updater.checkUpdate(false);

		//测试单卡双卡数量
		//Random random=new Random();
		//int doubleCount=Integer.parseInt(new DecimalFormat("0").format(Math.sqrt(10)*random.nextGaussian()+5));
		//System.out.println(doubleCount);
		//System.out.println(30-2*doubleCount);
		
		//测试json分类器
		//JsonReader jr=new JsonReader(JsonReader.path,false);
		//jr.cardsSort();
		//jr.cardsClassify();
		
		//测试编码转数组
		//String aString=
		//		"AAECAd35AwynywOczQPr0QP83wOh6AOa6wPZ+QOjnwSynwS1nwTwnwTOoAQJrNQDtOED++MDkegDiPQDv/kDwPkD0/kDn6AEAA==";
		//System.out.println(CodeAnalyzer.code2list(aString));
		
		//测试数组转编码
		//Integer[] aIntegers=new Integer[] { 
		//		0, 1, 1, 1, 46116, 30, 336, 374, 570, 581, 834, 890,
		//		912, 1914, 2078, 2727, 2883, 2948, 2949, 38312, 38758, 39119, 39225, 39941, 40636, 41289, 41841, 42818,
		//		43406, 45340, 45366, 46263, 46403, 46449, 47298, 48107, 0,0
		//		};
		//System.out.println(CodeAnalyzer.llist2code(Arrays.asList(aIntegers)));
		
		//测试随机卡组生成
		DeckBuilder db = new DeckBuilder();
		db.randomBuild();
		db.deckPrint(true);
	}
}

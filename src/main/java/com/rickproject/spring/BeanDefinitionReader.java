package com.rickproject.spring;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class BeanDefinitionReader {

	public BeanDefinitionReader(String cfgFile) {
		//1.獲取文件對應的流對象
		InputStream in = ClassLoader.getSystemResourceAsStream(cfgFile);
		//2.處理流對象
		handleStream(in);
	}

	/**
	 * 處理流中數據
	 */
	private void handleStream(InputStream in) {	
		try {
			//1.構建一個解析器對象
			DocumentBuilder builder = DocumentBuilderFactory
					.newInstance()	//工廠
					.newDocumentBuilder();	//解析器
			//2.解析流對象
			Document doc = builder.parse(in);
			//3.處理document對象(通過此對象可以獲取文件中節點信息)
			handleDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void handleDocument(Document doc) {
		//1.獲取所有bean元素
		NodeList list = doc.getElementsByTagName("bean");
		//2.迭代bean元素
		for(int i = 0; i < list.getLength(); i++) {
			//2.1獲取i對應元素(node)
			Node node = list.item(i);
			//System.out.println(node.getNodeName());
			//2.2處理Node對象
			BeanDefinition bd = handleNode(node);
			//System.out.println(bd);
			//2.3將bean的定義信息存儲到map
			//重構之後在BeanDefinitionReader裡存取不到map=>定義一個抽象方法交由使用這個類的人來定義如何存bean
			//beanMap.put(bd.getId(), bd);
			registerBean(bd);
			//2.4假如此bean中的lazy值為false則創建實例存instanceMap
		}	
	}

	/**
	 * 將bean對象添加到map容器(讓建構這個對象的人去決定如何去存bean)
	 */
	abstract void registerBean(BeanDefinition bd);

	private BeanDefinition handleNode(Node node) {
		//1.構建BeanDefinition對象
		BeanDefinition bd = new BeanDefinition();
		//2.獲取Node節點中相關屬性的值
		NamedNodeMap attributes = node.getAttributes();
		String id = attributes.getNamedItem("id").getNodeValue();
		//System.out.println(id);
		String targetCls = attributes.getNamedItem("class").getNodeValue();
		String lazy = attributes.getNamedItem("lazy").getNodeValue();
		//3.將數據封裝到BeanDefinition對象
		bd.setId(id);
		bd.setTargetClass(targetCls);
		bd.setLazy(Boolean.valueOf(lazy));

		return bd;
	}
}

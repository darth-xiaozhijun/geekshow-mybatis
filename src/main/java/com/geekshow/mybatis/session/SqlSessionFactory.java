package com.geekshow.mybatis.session;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.geekshow.mybatis.config.Configuration;
import com.geekshow.mybatis.config.MappedStatement;

/**
 * 把配置文件加载到内存
 * 工厂类生产SqlSession
 * @author Administrator
 *
 */
public class SqlSessionFactory {
	
	Configuration configuration = new Configuration();
	
	//记录mapper.xml文件存放的位置
	public static final String MAPPER_CONFIG_LOCATION = "mappers";
	
	//记录数据库连接信息文件存放位置
	public static final String DB_CONFIG_FILE = "db.properties";

	public SqlSessionFactory() {
		
		loadDBInfo();
		loadMappersInfo();
	}

	/**
	 * 加载指定文件夹下的所有mapper.xml
	 */
	private void loadMappersInfo() {

		URL resources = null;
		resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
		File mappers = new File(resources.getFile());//获取指定的文件夹信息
		if (mappers.isDirectory()) {
			
			File[] listFiles = mappers.listFiles();
			//遍历文件夹下所有的mapper.xml，解析信息后，注册到configuration对象中
			for (File file : listFiles) {
				loadMapperInfo(file);
			}
		}
	}

	/**
	 * 加载指定的mapper.xml文件
	 * @param file
	 */
	private void loadMapperInfo(File file) {

		//创建saxReaderduix 
		SAXReader reader = new SAXReader();
		//通过read方法读取一个文件转换成Document对象
		Document document = null;
		try {
			document = reader.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//获取根节点元素对象<mapper>
		Element root = document.getRootElement();
		//获取命名空间
		String namespace = root.attribute("namespace").getData().toString();
		//获取select子节点列表
		List<Element> selects = root.elements("select");
		//遍历select节点，将信息记录到MappedStatement对象，并登记到confi对象中
		for (Element element : selects) {
			MappedStatement mappedStatement = new MappedStatement();
			String id = element.attribute("id").getData().toString();
			String resultType = element.attribute("resultType").getData().toString();
			String sql = element.getData().toString();
			String sourceId = namespace + "." + id;
			
			//给mappedStatement对象赋值
			mappedStatement.setSourceId(sourceId);
			mappedStatement.setNamespace(namespace);
			mappedStatement.setResultType(resultType);
			mappedStatement.setSql(sql);
			
			//注册到configuration对象中
			configuration.getMappedStatements().put(sourceId, mappedStatement);
		}
	}

	/**
	 * 加载数据库配置信息
	 */
	private void loadDBInfo() {
		
		//加载数据库配置信息
		InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
		Properties p = new Properties();

		try {
		
			//将配置信息写入到properties对象中
			p.load(dbIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//将数据库配置信息写入到configuration对象
		configuration.setJdbcDriver(p.get("jdbc.driver").toString());
		configuration.setJdbcPassword(p.get("jdbc.password").toString());
		configuration.setJdbcUrl(p.get("jdbc.url").toString());
		configuration.setJdbcUsername(p.get("jdbc.username").toString());
	}
	
	public SqlSession openSession(){
		return new DefaultSqlSession(configuration);
	}
}

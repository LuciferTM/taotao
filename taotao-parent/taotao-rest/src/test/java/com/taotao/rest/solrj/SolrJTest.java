package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/2
 */
public class SolrJTest {

    @Test
    public void addDocument() throws Exception{
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
        //创建文档对象
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", "test001");
        solrInputDocument.addField("item_title", "测试商品1");
        solrInputDocument.addField("item_price", 12345);
        //将文档对象写入索引库
        solrServer.add(solrInputDocument);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
        solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

}

package org.lkpnotice.lucener;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;

/**
 * Created by liujinpeng on 2018/12/19.
 */
public class Main {
    static String PATH = "tmp";

    public static void main(String[] args) throws IOException {
        //indexDoc();
        //search();

        indexDocs();
    }


    public static void indexDocs() throws IOException {
        String index_path=PATH;
        IndexWriter indexwriter=
                new IndexWriter(index_path,new StandardAnalyzer(),true);

        for (int i = 0; i< 1000000;i++){
            String mtext= String.format("This is the text to be indexed %s.", i);

            Field mfield=
                    new Field("textcontent",mtext,Field.Store.YES,Field.Index.TOKENIZED);

            Document mdoc=new Document();
            mdoc.add(mfield);

            //向索引中加入Document对象
            indexwriter.addDocument(mdoc);
            if (i%1000 == 0){
                System.out.println("count is " + i);
            }
        }

        indexwriter.close();
    }

    public static void indexDoc() throws IOException {
        String mtext="This is the text to be indexed.";
        String index_path=PATH;
        Field mfield=
                new Field("textcontent",mtext,Field.Store.YES,Field.Index.TOKENIZED);

        Document mdoc=new Document();
        mdoc.add(mfield);

        IndexWriter indexwriter=
                new IndexWriter(index_path,new StandardAnalyzer(),true);

        //向索引中加入Document对象
        indexwriter.addDocument(mdoc);
        indexwriter.close();
    }


    public static void search() throws IOException {
        //初始化一个IndexSearcher
        IndexSearcher indexsearcher=new IndexSearcher(PATH);
//构建一个Term对象
        Term term=new Term("textcontent","text");
//构建一个Query对象
        Query query=new TermQuery(term);
//检索
        Hits hits=indexsearcher.search(query);
//输出查询结果

        System.out.println("size is " + hits.length());
        for(int i=0;i<hits.length();i++)
            System.out.println( hits.doc(i).get("textcontent"));
    }
}

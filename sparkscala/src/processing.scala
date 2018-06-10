import scala.collection.Map
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.spark.sql.SQLImplicits

object processing {
  def main(args: Array[String]) {
    import org.apache.log4j.Logger
    import org.apache.log4j.Level

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("processing.scala").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val url = "https://reqres.in/api/users?page=2"

    val httpClient = HttpClients.createDefault();
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }

    println(content)
    import sqlContext.implicits._
    val df = sqlContext.read.json(Seq(content).toDS)
    df.createOrReplaceTempView("people")
    df.printSchema()
    val dfdata = df.select($"data".getItem(0))
        dfdata.printSchema();
        dfdata.show();
   val idk = df.sqlContext.sql("SELECT data.id FROM people")
   
    idk.show()
    
    var app = List(2, 3, 4, 5, 6);
    val apple = sc.parallelize(app)
    var pineapple = apple.map(item => item + 1).foreach(item => print(item))

  }
}
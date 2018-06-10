import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;

object test {
  def main(args: Array[String]){
    print("hello")
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
   

		print(content)
  }
}
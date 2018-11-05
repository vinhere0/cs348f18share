import pyspark.sql.functions as psf

lines = spark.read.text("gutenberg")
#lines.show()

words1 = lines.select(psf.split("value","\s")) \
              .toDF("wordlist")

words2 = words1.select(psf.explode("wordlist")) \
              .toDF("word")

results = words2.groupBy("word"). \
                .agg(psf.count("word"))
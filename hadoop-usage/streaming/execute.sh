#!/bin/sh

hadoop jar /Users/heyaoyu/hadoop-2.7.3/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
-input /user/words.txt \
-output /user/pystreaming.output \
-files /Users/heyaoyu/Projects/java/hadoop/streaming/*.py \
-mapper "python map.py" \
-reducer "python reduce.py" \
-jobconf mapred.reduce.tasks=1 \
-jobconf mapred.job.name="pystreamin_test"

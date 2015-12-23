# elasticsearch-indexer
### If sbt package not working due to not enough heap size, set the following sbt options.
```
export SBT_OPTS="-Xmx2G -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -Xss50M -Duser.timezone=GMT"
```

# Generate slick model
```
sbt/sbt console
helper.DBHelper.gen_model
```